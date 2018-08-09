---
layout: post
comments: true
date: 2018-08-09 10:00:00+0200
authors: Joakim Rishaug
title: "Preparing your product for machine learning"
tags:
- Data science machine learning
---

At many companies, there are few data scientists and many projects which may be very interesting for the business to
apply machine learning on. For most of these companies, starting greenfield projects with a data scientist on the team
might be either a difficult political battle (to get assigned resources) or impossible, if there are no data scientists yet.

But how can a team prepare their project for the day when there will be a data scientist
around, and in the process, maybe improve their current product as well?

In [Google’s “Rules of ML”](https://developers.google.com/machine-learning/guides/rules-of-ml/),
they make some good suggestions for this in their introduction:
> Do machine learning like the great engineer you are, not the great machine learning expert you aren’t.
Most of the problems you will face, are in fact engineering problems. Most of the gains come from great features, not great machine learning algorithms.

This means, the team already has great potential to improve their product in ways that the data
scientist might help them do in a more structured way later. I believe as long as the engineers and programmers
know how machine learning algorithms like having the data shaped, they can take advantage of this to move forward confidently.

## What kind of data does a machine learning algorithm like?
Computers like things that are quantifiable by numbers, and this is probably obvious to many programmers,
but it’s easy to forget this when we have tools like Google that can find and seemingly understand what we want just by typing a couple of words.

According to a Kaggle survey from 2017, [“dirty data” is the biggest problem faced by machine learning practitioners](https://www.theverge.com/2017/11/1/16589246/machine-learning-data-science-dirty-data-kaggle-survey-2017).
This means data being unstructured in some form, either by being severely skewed in one direction or the other, being
full of holes (only subsets of users even have a given feature), or data that has to go through processing before
actually becoming a usable feature. Making sure that this friction is minimized is important later for the progress
of machine learning functionality, but it also enables teams to make more use of heuristics right away if the data
is better structured.

So, what's important to keep in mind when tracking or saving user information that you plan on using to predict their behavior?

### Is the data numerical?
Again, this cannot be understated, **computers like numbers**. So if at all possible, allow users to input data such as:
* Q: “How many years have you studied at university?” A: “4”
* Q: “How much do you like cookies?” A: "0-100"


This is easily malleable, and very easy to interpret for ML algorithms.

### Is the data categorical?
If you can’t make your data into a number, the second best thing is to make it into a category,
which can be almost like a number from a computer’s perspective.
Turning our previous numerical example into a category could look something like this
* Q: “What is your highest achieved degree from University/College?” A: “Bachelor’s”.

From the ML model’s perspective,
we can simply represent each category as a number. For example, if we have “Bachelor’s, Master’s and Ph.D.”
as possible category answers here, it would be representable by “0, 1, 2” if we keep these ordered in the same way going forward.

As long as you properly restrict your categories, and provide some nice UI/UX for the user to interact with, this will
do just as well as a numerical approach. Just don’t let users type in whatever they like as their categorical answer,
such as “Bachelor” “B.A.”, “About f0ur years” etc, as this will doom you to forever clean up data afterwards before using it.
(if you think the last option was a joke and didn’t fit the question at all, you’re right, but users will eventually write something similar)

#### UI - Making categorical/numerical inputs work ####
There are some very simple, yet powerful things you can do with your UI to facilitate the usage of categorical variables.
* Use auto-complete for your inputs, instead of free text inputs. This allows the user to write in a natural way, but only select from the subset you have defined.
* Use sliders for scales from 0-X for numerical inputs.
* Allow the user to connect via an integration to some external service to retrieve metrics if possible.

### Can we process the data to make it numerical?
This mostly involves using [NLP(Natural Language Processing)](https://en.wikipedia.org/wiki/Natural_language_processing)
to process text information, and [CV(Computer Vision)](https://en.wikipedia.org/wiki/Computer_vision)
to process images. For most teams and organizations this is more of a last resort, as it involves an extra layer of
complexity, required competency and up-front work before actually starting to make use of the data.

<figure>
    <img class="center-block" src="/images/2018-08-09-preparing-your-product-for-ml/object_detection.jpg" alt="object detection" title="object detection" />
    <figcaption style="text-align:right; font-style:italic;">Object detection in action</figcaption>
</figure>

Image recognition is getting to the point of almost being able to recognize anything in a picture that you yourself
can recognize in it, such as “Is there an orange in this picture?”, “how many people are in the image?” and
“how are the people standing?”. NLP however still has a ways to go before interpreting texts in the same way as humans,
so for most teams I would highly advise against this.

If you’re thinking about adding a text field because it might give you good heuristics to model your
users behavior with normal programming, or allow something to be searched, remember that it can probably
not be used in machine learning. Which means you’ll have to redo the work of capturing that feature some other way, and your data scientist might be sad.

## Good to go?
As long as you can shape your data into something like this, you are pretty much good to go in terms to passing
the data to a machine learning algorithm. Keep in mind that you should already have a close relationship with which
 indicators you are hoping to improve on as well. This could mean instrumenting the collection of
 data for things such as click through rate, revenue or number of conversions. This will help the data scientist
 that comes on board (and your team) to easily gain confidence in that ML is the right thing for you.
 For some teams, plain old programming might be enough, and better (depending on your needs / resources / data availability).

## A few caveats
Also remember you need to have enough data to be able to tell something about the true distribution of what you’re trying to describe.
If I have the sales prices for 3 Hondas, and I’m trying to predict the estimated sales price of a [Honda CR-V 2015 edition](https://en.wikipedia.org/wiki/Honda_CR-V),
I hardly have enough information to reliably tell what it should be worth. Here you might find traditional
statistics helpful to figure out when you have enough data. However if you have millions upon millions of examples
of something, usually you should be fine, no matter what you’re trying to figure out about the world.

Another smart thing to keep an eye out for is, “Can you yourself execute the task of what you want ML to do for you,
given your available data?” (on a small sub-sample of course).
If I have only the brand name, and edition year for the Honda that I’m trying to predict the proper sales price for,
I probably don’t have enough data, as most people are interested in the milage of the car, what extras are included, color of the paint-job, and so on.

Is the feature you have selected affected by time? Then you might need more complex models to model it, and proper time-keeping at the data-collection stage.

Is the feature affected only by you, and nobody else? Or is it dependent on the way your UI works,
how another team tracks info to their database, or is it depending on something your other models already affects?
These are important things to keep in mind before drawing too decisive conclusions about what the data tells you, and might also bias your model later.

And how much data should a team save? I would say, save as much as you can without annoying users and trampling privacy laws such as GDPR.
It’s a well known fact that [more data, means better results in the long run.](https://youtu.be/yvDCzhbjYWs?t=551)
Just don’t reduce the user experience to collect data you don’t know you’ll need later.
Don’t set out to track everything about users right away dismissively thinking that as long as you have the data, AI can solve all your problems later.
Remember to make this an iterative process your team or domain experts are a part of.

## In conclusion
Normal programming can usually get you close to your goal, or even completely to your goal depending
on what you want to achieve. However even if you end up replacing parts of your approach later when you get a data scientist,
it is not in vain as you will have discovered useful features, and have a mountain of data the data scientist can sift through.

Just remember to format the data in a way which makes it easily usable by computers,
and keep track of the metrics you want to improve ahead of time! Without this, even the normal programming approach will be hampered.

If this is something that interests you, or that your team is struggling with
I recommend checking out [Rules of ML by Google authors](https://developers.google.com/machine-learning/guides/rules-of-ml/)
and/or [Why you need to improve your training data by Pete Warden](https://petewarden.com/2018/05/28/why-you-need-to-improve-your-training-data-and-how-to-do-it/).
Facebook even dedicated a purely to data to it in [their new machine learning field guide](https://research.fb.com/the-facebook-field-guide-to-machine-learning-video-series/).

Also, if you're interested in machine learning and programming, I usually tweet whatever interesting stuff I stumble upon at [@JoakimRi](https://twitter.com/JoakimRi).

Thanks to [Thorkild Stray](https://twitter.com/thorkilds) and [Simen Eide](https://twitter.com/simeneide) for reviewing earlier drafts of the post, and providing feedback!
