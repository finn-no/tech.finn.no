---
authors: espen
comments: true
date: 2011-01-07 14:16:09+00:00
layout: post
title: Graded browser support at FINN.no
redirect_from: /graded-browser-support-at-finn-no/
tags:
- browsers
- browsersupport
- front-end
- interface development
---

Inspired by the kick-ass developers of Yahoo! and their work on Graded Browser Support (GBS) we at FINN.no decided to adopt graded browser support as a way to communicate what level of support we have for different combinations of browsers and platforms.


## Creating the support matrix


FINN is the largest site in Norway when it comes to traffic and we have a good framework for statistics. In order to create the support matrix we took the statistics for the most popular browsers and platform combinations and put them in a grid. This gave us a matrix showing us which browser/platform combinations we need to consider from a business perspective. Although this was a good start, we also needed to figure out what level of support we should provide each combination. Numbers and usage alone does not provide a good enough basis upon setting support levels.


## The cost of support


Having read the early drafts of the awesome book [Secrets of a JavaScript ninja](http://jsninja.com/) by [John Resig](http://ejohn.org/) we decided to follow his approach on creating a GBS matrix and perform a cost-benefit-analysis. We did was to do a quick survey among our developers in order to figure out what the costs where for supporting different user agents on certain platforms. The results are displayed in the chart below and shows our subjective opinions on how much effort is required to support a certain browser. Note that this chart will not look the same for everyone. It is will vary based on the skills of your developers, what browsers they work in, etc, etc.

<img src="/images/2011-01-07-graded-browser-support-at-finn-no/kostnyttenettleser.png" alt="Kostnyttenettleser">


## Support levels


Our levels of support are slightly more simple than those of Yahoo! and we only provide three levels:




  * A-support: no visual or functional errors, all errors are reported and all features should be tested for each release.


  * B-support: no functional errors, core features should be tested for each release, visual errors are only reported with a low priority


  * C-support: no functional errors in core features, all other errors are not reported.


This is how we created our GDS matrix and we hope this will inspire you to do the same for your shop. We are going to do a similar exercise when it comes to mobile, but this is still work in progress.

The matrix is available on labs.finn.no (the url is no longer available).
