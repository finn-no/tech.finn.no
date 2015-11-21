#!/usr/bin/env python
# -*- coding: utf-8

"""
Generate graphs for article.

Requires the following libraries in addition to the standard library:
 - pandas
 - numpy
 - matplotlib
 - python-slugify
"""

import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
from slugify import slugify

from shutil import rmtree
import os
import os.path
import sys

LEVEL = {
    "pro": lambda language, languages: language in languages,
    "amateur": lambda language, languages: language not in languages,
    "all": lambda language, languages: True
}


class Experts(object):
    def __init__(self, df):
        self.df = df
        rmtree("expert", ignore_errors=True)

    def plot(self):
        for level in LEVEL:
            os.makedirs(os.path.join("expert", level))
            for l in self.df.iloc[:0, 13:29]:
                self._make_plot(l, level)

    def _make_plot(self, language, level):
        def predicate(cell):
            languages = [l.strip() for l in cell.split(",")]
            return LEVEL[level](language, languages)
        criterion = self.df['Current work'].map(predicate)
        df2 = self.df[criterion][language]
        filename = "expert/{}/{}.png".format(level, language)
        if not df2.empty:
            plot = df2.plot(kind='hist', title="{} - {}".format(language, level), range=(1, 6), bins=5, align='left')
            plot.get_figure().savefig(filename)
            print filename


class MultiColumn(object):
    def __init__(self, df, column_name, title=None):
        self.df = df
        self.column_name = column_name
        self.title = title if title is not None else column_name
        self.filename = "{}.png".format(slugify(self.title).encode(sys.getfilesystemencoding()))
        if os.path.exists(self.filename):
            os.unlink(self.filename)

    def plot(self):
        print self.filename
        df2 = self.df[self.column_name]
        df3 = df2.str.split(",").apply(pd.Series)
        df4 = df3.unstack().dropna().str.strip()
        counts = df4.value_counts()[:10]
        color_list = plt.cm.Paired(np.linspace(0, 1, len(counts)))
        plot = counts.plot(kind="bar", title="Top {} in {}".format(len(counts), self.title), color=color_list, rot=25)
        plot.get_figure().savefig(self.filename)


def main():
    plt.hold(False)
    df = pd.read_csv("results.csv")
    plots = [
        MultiColumn(df, "Current work"),
        MultiColumn(df, "Which programming languages do you know?", "Languages you know"),
        MultiColumn(df, "Do you know any SQL-dialects? [Normalized]", "SQL-dialects you know"),
        Experts(df)
    ]
    for p in plots:
        p.plot()

if __name__ == "__main__":
    main()
