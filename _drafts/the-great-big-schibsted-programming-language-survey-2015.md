---
layout: post
comments: true
date: 2015-10-02 13:24:32+0200
authors: mortenlj
title: The great big Schibsted programming language survey 2015
tags:
- developers
- java
- javascript
- clojure
- code
- language
---

During September, I have been collecting answers on a survey about programming languages from people who work in one of the many companies that make up the Schibsted family. The survey is neither requested, sanctioned, approved or even suggested by anyone in management, in any Schibsted company. It is entirely my own personal pet-project, because I'm interested in these things.

The survey opens with the following introduction:

> In 2012, there was a discussion about technology choices, and
> particulary programming languages to use in FINN. As always in such
> discussions, claims like "It's impossible to hire people who know
> language X, so we need to always use Java" or "We can't expect
> programmers to learn a new language just because we think language Y
> would be a better fit" were put forward.
> 
> Personally, I've never liked those arguments, because they contradict
> what I think defines a "good programmer", and I'm optimistically
> hoping that we only hire "good programmers". Also, instead of
> opinions, beliefs, rumours and hearsay, let's be data driven in these
> discussions! So I decided I wanted to put it to the test, and created
> a survey and posted it for all the company to see.
> 
> The results from that survey were blogged about here:
> [http://tech.finn.no/2012/09/04/leaving-the-tower-of-babel/][babel]
> 
> Now, almost three years later, a lot of changes are happening. FINN
> is becoming more close with it's Schibsted brethren, Schibsted itself
> is turning towards a more technological outlook, and I felt it was
> time to get an updated view, and why not involve all of Schibsted
> while we're at it.
> 
> So, "The great big Schibsted programming language survey 2015" was
> created, and hopefully it will make the rounds in all of Schibsted
> and gather some interesting, fun, surprising and (with a bit of luck)
> useful insights.

About the responses
-------------------

309 people responded to the survey, which is more than many of my co-workers thought would respond.

Over 50% of respondents are in their thirties, and if we look at the ages from 26 to 40, we cover over 75% of respondents. On gender, it's even worse, with a whopping 93.5% of respondents admitting to being male. We also seem to shun inexperienced people, with 42% having 10 years of experience or more, and only 2.3% of respondents fresh from school.

It seems we have some work to do with regards to diversity.

According to Arena (The Schibsted-wide intranet), there are just under 125 companies in the Schibsted familiy. In those companies, 9355 people are employed.

That means somewhere between 20% and 30% response rate in the target group, which isn't too bad.

Unfortunately, our results are not representative. FINN is by no means the largest company, but supplied the most answers with 80 respondents. The second largest group was the various incarnations of Schibsted Product & Technology (SPT), which supplied 53 responses if we combine them all. Those two supplied over a third of all responses. Third place belongs to Schibsted Tech Polska (16 responses), which beat Wilhaben (15), Le Bon Coin (13), Blocket (12) and Aftonbladet (10) to close out the top 7.

This probably means that the results are more a description of FINN and SPT than a description of Schibsted in general.

Those 309 people listed 103 different companies, which is about 20 less than the total number of companies. That sounds great! Except I'm fairly certain that's not what I have. Trying to normalize the data entered here, fixing variations in spelling and other issues I'll get back to in a minute, I reduced it to 45 companies, one of which was Schibsted itself. 

Those issues are companies that don't exist (atleast according to Arena)! I've tried my best to guess which company was really intended, but might have made mistakes here and there. In some cases, I had no idea which company to use instead, so I just left it as is, even if the company is not listed as part of Schibsted. Some examples: Schibsted Tech Madrid, SPT - Madrid, SCM Spain, SCM Central, Schibsted Norge Tech, Plan3 and Schibsted ATE. None of these were listed anywhere, but I've tried my best to place them some logical place.

Another problem was SPT aka Schibsted Products & Technology. SPT is techically four companies, SPT Norway, SPT Sweden, SPT Spain and SPT UK. Most people in SPT simply answered SPT, but they estimated as little as 20 people working in SPT, and as high as 250. I've disregarded their disagreement, and combined them all to just SPT.

Two people said they work in Schibsted, but they had wildly differing opinions about how many people work in Schibsted. One said 10000 total, 1500 technical, the other said 1000 total, 200 technical.

Disregarding the problems here, let's look at the numbers entered. Using the averages for each company and summing up, we arrive at roughly 4500 people working in those 47 companies. For technical positions, the sum of averages is roughly 1600. Since we have responses from about half the companies, that fits nicely with the total being just under 10000.


Languages in regular use
------------------------

The most commonly used language, if you can call it that, is Bash. 52.1% of respondents use Bash regularly. Close behind, at 50.2% is JavaScript. Neither of these are surprising, considering the business we're in. Java clocks in at 49.2%, and from there it's quite a drop to Python at 28.2% and PHP at 24.9%. C manages 18.1%, Ruby and Scala are both used by 17.8%, and Groovy (11.7%) and Go (10.7%) conclude the top 10.

Of the previously mentioned top 7 companies with regards to number of responses, Le Bon Coin and Blocket are the only two companies that are not predominantly Java shops. Those two have a more wide array of languages in common use, with high numbers for multiple languages. When we combine those two companies, 88% use C, 80% uses PHP, 72% uses Bash and sh, 64% uses Python and only 56% uses Java. Ruby, R are popular in Le Bon Coin, while Blockets own template language and JavaScript are popular in Blocket.

Singling out FINN and SPT in a similar way, it's a different story. Java is used by 76.7% in these companies, JavaScript and Bash/sh have similar numbers at 48.1% and 46.6% respectively. Scala is popular in SPT, and have a share of 33.8% in the two companies. Next in line is Python at 24%, Groovy and R both at 21.8%. Ruby is used by 18%, and a group of PHP developers in SPT brings PHP into the top 10 with 6%.


### Basics

- Bash, JavaScript and Java in a league of their own when it comes to regular use
- Missing categories in "I see myself as": Data Scientist, Ops/Sysadmin, Test/QA, UX, Manager, Database
- Java is known by almost 90%. Runner ups: JavaScript (85.1%) and Bash (75.7%)
- PostgreSQL and MySQL most popular, Oracle and Sybase/MS trailing
- Most common languages have a almost normal variation in skills
- "Common" langauges that happen to not be in (much) use in Schibsted have slope from most not knowing
- Very few people consider themselves experts in C++...
- Clojure is the only common language with no experts
- Java is the only language where the slope goes from most being experts
- Popular choices for next project: Java, JavaScript, Go, Scala, Python
- Nobody wants to use Perl for their next project \o/
- If you know 3-5 programming languages, most people think you know enough
- Knowing 9 languages is just plain WRONG! (Noone thinks 9 is the right number)
- Advertising works! Answers are mostly collected on days when the link was posted to slack.

### Ideas

- Which companies prefer which languages?
- Where are the Java experts, the JavaScript experts and the PHP experts?
- Why so few experts in other languages? Java easy, or the others too hard?

[babel]: {% post_url 2012-09-04-leaving-the-tower-of-babel %}
