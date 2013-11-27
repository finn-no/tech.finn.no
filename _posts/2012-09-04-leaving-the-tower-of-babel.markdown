---
author: mortenlj
comments: true
date: 2012-09-04 09:48:05+00:00
layout: post
slug: leaving-the-tower-of-babel
title: Leaving the Tower of Babel
wordpress_id: 1720
categories:
- Behind the scenes
- Systems development
tags:
- developers
- java
- javascript
- language
---

Here at FINN we use mostly Java for our day-to-day work, but we do have some applications and modules written in other languages. We currently have code in Java, Ruby, JavaScript, Objective-C and Scala, supporting things as diverse as testing frameworks and iOS-apps.

Recently there has been some discussion with regards to what we should be using for new projects, as each team of developers start suggesting that they should use something they think would be easier and more effective. The arguments against are based on how easy it would be to recruit new developers who know these new technologies, how well it would perform under our kinds of loads, and wheter the assumption that we can be more effective is actually true.

Our strategy is firm on this point, that experiments can be done, but for major applications serving the public, we should be using Java for the time being. We are however considering if it's time to take a second look at our chosen languages, and see if we should expand our portfolio. This strategy is founded on a wish to reduce the size of our technology portfolio, so that we don't have to spend time and effort to bring developers up to speed when they switch teams. There's also a higher chance that new hires will be familiar with Java, while other technologies would often require a period of training when they first start.

During these discussions, we created a quick poll and sent it out on our internal discussionboard. The poll asked questions like "How long have you worked as a developer?", "Which language do you use for your day-to-day work?", "If you were free to chose, which language would you use for your next project?" and "Which programming languages do you know?". Our definition of "know" was very open in this poll, lowering the bar to allow people who had maybe written a single example program, and understands a bit of code could check the box.

Out of the nearly 100 people that work with development, 56 answered. We can assume that the people who did answer, were the people who were interested in the topic, and might not be a representative selection, but the results are interesting none the less.

So.. what did we learn?


### Experience


Being a poll made in the midst of a discussion, mostly for fun, this part of the poll wasn't framed in a way that gives us much useful information. We can say that the average developer who answered has worked for around seven or eight years, but with what looks like a reasonably fair spread across all groups. Almost as many with a couple years experience, as there are people with 10 to 15 years.


### Primary language in day-to-day work


Being a predominatly Java shop, most of the respondents were using Java for their daily work. 35 out of 56 were Java-developers. 11 respondents worked with JavaScript daily, while four worked with Scala, three with Ruby, two with Objective-C, and finally one database-developer who worked mostly in T-SQL (The SQL-variant used in Sybase).


### Most popular choice if allowed to chose freely


The most interesting part of the poll, with many interesting insights. This is also the most loaded part, as the results could easily short-circuit any discussion about future choice in language.

The bad news (or good, depending on your point of view), is that there was no clear answer from this section. Keep in mind that around 40 people didn't answer this poll, and could be assumed to be content with the current status-quo.

The biggest group was Java, not surprisingly. The surprising part is that it was only 16 people who would chose Java if they could chose freely. This is much lower than expected, but still make up the largest group. Of these, 13 people are already using Java today. 20 Java developers would like to use something else.

So, if Java was the largest group, at 16 people, what does that mean for the remainder of the results? Well, 10 people would have chosen Scala, while JavaScript, Ruby, Clojure and Groovy clock in at about four or five respondents wanting to use them. At the bottom of the pack we have Python and Objective-C with three and two respondents chosing them.

Six people were interested enough to answer the poll, but chose the non-commitant "I don't care, as long as I'm making good stuff for our users" option.

The number of possible answers here was a rather large selection of popular and not-so-popular-but-quite-well-known languages, so the fact that the list only includes eight languages is a sign that it's not a completely random selection. Still, quite a lot of discussion is needed before any one of those languages gets center stage.

A couple curious details found in this part of the poll includes the fact that the only people who would chose JavaScript are people who are already working in JavaScript every day. Groovy, Clojure, Objective-C and Scala have all managed to be chosen by a person who doesn't actually know the langauge. There's only three people who would switch *to* Java, if allowed to chose.


### Which languages do we "know"?


As mentioned earlier, the bar for "knowing" a language was set quite low in this poll, to get more diversity in answers. This resulted in some interesting numbers.

Being primarily a Java-shop, it might not surprise anyone that a full 100% knows Java. A little over three fourths know JavaScript, while Ruby and T-SQL was known to about half. These are the main languages most of us have some sort of dealings with in our daily work, so that they score high is not surprising.

Next on the list is PHP and Python, with around 40% knowing them.

We run primarily Sybase for our databases, with a small number of MySQL and PostgreSQL servers for smaller applications and modules. We are trying to standardise on PostgreSQL, but this isn't reflected in knowledge, with 37.5% knowing how to code MySQL-procedures, 35.7% knowing how to code for Oracle, and only 25% knowing their way around a PostgreSQL codebase.

Our operations-department have had a "vendetta" against Perl for several years, but there are still more people who knows Perl than there are people who knows Scala, with the score 18 to 15. Groovy has quite a following too, with 13 respondents knowing Groovy.

We have a small Apps team working with iOS-development, but Objective-C has a reach far outside that team, with 10 respondents knowing Objective-C.

Common Lisp is known to five respondents, while Clojure suprisingly was only known to seven respondents. As you can read elsewhere in this blog, we had a Clojure workshop at our Technology Day earlier this summer, and these results might be telling us something about the language or our teachers when it didn't have a better ability to "stick" than this.

We also have people who know some Erlang, Smalltalk, Lua, Haskell, Scheme, Eiffel and ML/SML.


### How many languages do we "know"?


On average, we know 6.8 languages each. The most knowledgeable person knows 16 languages, while the least knowledgeable knows only one. The high experience respondents, 16 years or more, have a higher average than the rest of us, with 9.4, while the rest of the "experience brackets" know somewhere between six and seven languages.

Several programmer-gurus seem to think you should stribe to teach yourself one new language every year, and it would seem atleast one of our respondents have been able to follow this advice. For those of us with less extra time, a less ambitious strategy might be more compatible, but that we should stribe to learn something new every once in a while seems to be good advice.


### Now what?


As mentioned previously, this poll was not a serious attemt at gaining actionable insights. The results should be taken with a large helping of salt, and at most used as a basis for discussions. On the other hand, I know we will be discussing this topic going forward, because the fact that only 16 of 56 wanted to use Java is telling us it's time to start the discussion. There are possibly 40 developers who would prefer Java, who didn't respond, so it's too early to draw conclusions, but we have a place to start.

There are other questions falling out of this too:



	
  * There are two developers who wants to work with Objective-C, and eight Java-developers who wants to work with Scala, so why do we have so few internal applicants when we have openings on the teams working with these languages?

	
  * What is it that makes developers want to work with languages they don't even know?

	
  * Of the respondents, more than half the Java-developers wants to use something else. Why is that?



