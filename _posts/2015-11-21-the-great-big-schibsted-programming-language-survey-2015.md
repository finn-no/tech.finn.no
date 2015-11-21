---
layout: post
comments: true
date: 2015-11-21 22:28:28+0100
authors: Morten Lied Johansen
title: The great big Schibsted programming language survey 2015
image_dir: /images/the-great-big-schibsted-programming-language-survey-2015
tags:
- developers
- java
- javascript
- clojure
- code
- language
---

During September, I collected answers on a survey about programming languages from people who work in one of the many companies that make up the Schibsted family. The survey is neither requested, sanctioned, approved or even suggested by anyone in management, in any Schibsted company. It is entirely my own personal pet-project, because I'm interested in these things.

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

![responses]{: .expandable-image .expandable-image-small}

309 people responded to the survey, which is more than many of my co-workers thought would respond.

Over 50% of respondents are in their thirties, and if we look at the ages from 26 to 40, we cover over 75% of respondents. On gender, it's even worse, with a whopping 93.5% of respondents admitting to being male. We also seem to shun inexperienced people, with 42% having 10 years of experience or more, and only 2.3% of respondents fresh from school.

![age]{: .expandable-image .expandable-image-small}

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

![current_work]{: .expandable-image .expandable-image-small}

The most commonly used language, if you can call it that, is Bash. 52.1% of respondents use Bash regularly. Close behind, at 50.2% is JavaScript. Neither of these are surprising, considering the business we're in. Java clocks in at 49.2%, and from there it's quite a drop to Python at 28.2% and PHP at 24.9%. C manages 18.1%, Ruby and Scala are both used by 17.8%, and Groovy (11.7%) and Go (10.7%) conclude the top 10.

Of the previously mentioned top 7 companies with regards to number of responses, Le Bon Coin and Blocket are the only two companies that are not predominantly Java shops. Those two have a more wide array of languages in common use, with high numbers for multiple languages. When we combine those two companies, 88% use C, 80% uses PHP, 72% uses Bash and sh, 64% uses Python and only 56% uses Java. Ruby, R are popular in Le Bon Coin, while Blockets own template language and JavaScript are popular in Blocket.

Singling out FINN and SPT in a similar way, it's a different story. Java is used by 76.7% in these companies, JavaScript and Bash/sh are around the same, at 48.1% and 46.6% respectively. Scala is popular with a share of 33.8% in the two companies. Next in line is Python at 24%, Groovy and R both at 21.8%. Ruby is used by 18%, and a group of PHP developers in SPT brings PHP into the top 10 with 6%.



What kind of developers are we?
-------------------------------

The most popular self-description is "a backend developer" (124), followed closely by the "full stack developer" (82). There are slightly more devops people (29) than "frontend developers" (25). Of course, the most interesting fact about this question, is all the various roles I failed to think of. Over 40 other descriptions were entered in the "Other" box, some of them even by more than one person! Most notably the 10 or 11 data scientists (depending on how you interpret descriptions). Mobile developer or App developer is another one that should have been one of the choices.


The languages we know
---------------------

![languages-you-know]{: .expandable-image .expandable-image-small}

Only 13 people do not know any Java at all, the remaining 296 respondents knows enough to tick the box. This makes Java the most known language, but it is closely followed by C and JavaScript. 268 respondents know C, while 263 know JavaScript. If ever we wanted to use [bashttpd] for anything, hardly anyone would need to be retrained, with 234 people already knowning enough bash to get around. 

PHP and Python follow, at 212 and 204 respectively. Personally I'm pleased to see so many knowing Python, and can only blame childhood mistakes for so many people knowing PHP. Dropping below 200 respondents we find R at 188 and C++ at 185. Ruby and Perl close out the top 10 languages, with 164 and 146. Hopefully all these people have learned Perl as a defence mechanism: Know thy enemy and all that...

Of the more hip languages on the JVM, Scala comes out on top, with 117 people claiming to know it, while Groovy at 94 and Clojure at 53 haven't convinced quite as many people that they are useful to know. Clojure isn't even in the top 20.

Outside the JVM, the top three contenders are all in the Microsoft family: C# (106), Basic (104) and Visual Basic (103). From far out in left field comes AWK at 98 respondents, while Pascal, the old favourite of so many, are known by only 86 people. Go has a sizeable following, at 81, narrowly beating Objective-C at 77. To close out the top 20, Haskell lays claim to the twentieth spot, with 61 people knowing what the heck Monads are.

Moving into the twenties, CoffeeScript ties with Clojure and Prolog at 53, while Common Lisp has managed to stick in the head of 47 people. Lua is known by 42, while Erlang and Scheme are tied at 31. Microsoft hasn't quite managed to get their PowerShell into as many heads as Bash, but 30 people are willing to admit knowing it. Swift (29) and Tcl (27) close out the top 30.

Fortran (26) and PostScript (21) are starting to fall out of fashion, and we can point at 20 people working in various norwegian companies who probably got their education at the University of Oslo, starting with Simula as their first language. The story behind the single non-norwegian Simula-proficient person at Aftonbladet would be interesting to hear I imagine (or is it simply a norwegian who has moved to Sweden?).

Other notable mentions are 19 people who know Smalltalk, 11 people knows D, a whopping 10 people have managed to learn Brainfuck, while 9 people know Rust and F# (not the same 9 though). The tail is quite long, and old heroes like Forth (8), OCaml (8), Standard ML (4), Delphi (2), Modula2 (2) and Ada (1) are still hanging on. There's even a handful of people who knows some dialect of assembler.


What can we use?
----------------

Let's take a close look at which languages some of the larger companies (in terms of number of respondents) could and should use.

When starting their next project, SPT could choose Java, C, Python or JavaScript, and send less than a third of their people back to school. Allowing half the company to re-train, they could select from Scala, C++ or PHP, while Ruby, Perl, Groovy or C# would require sending close to two thirds of the company to school. Other popular choices are Go and Clojure, but that would require around 80% of the company to hit the books, so maybe not just yet.

Blocket is fortunate enough to have four languages known by all respondents: Java, C, Python and PHP. If they want to use JavaScript, C++ or Perl, they are still quite prepared, while Ruby and Go haven't quite the same following. If they want to be on the JVM, but not use Java, Groovy is their only choice, but they would still need to train 11 out of 12 people.

At FINN, some people would be surprised to see that we are better prepared for a switch to C or Ruby than we are prepared for a switch to Scala. With 48 people knowing Scala, and 42 knowing Groovy, any of those two would be doable. We'd be stuck quite quickly if we go for Go, with only 16 of 80 knowing anything about it, and Clojure isn't doing much better, at 26 out of 80. Half the company already knows C++, Python or PHP, so we could use those, while Perl, Pascal and Basic requires training more than half the company. Last year we had both a Clojure course (again) and a Haskell course for those interested, and 26 and 24 people managed to pick those up enough to click the right boxes.

Le Bon Coin have a story similar to Blocket. They can hardly go wrong, with most of the company knowlegeable in Java, JavaScript, PHP, C and Python. A few don't know Ruby or C++, while Go and Perl are only known by half the respondents. Groovy is the top alternate JVM language, but with only 2 out of 13, it's not really a good choice. Scala and Clojure aren't known at all, so functional programming on the JVM clearly isn't a "thing" at LBC.

Schibsted Tech Polska delivers to a number of other companies, so they need to know a bit of everything. This is reflected in their knowledge, with Java, C and JavaScript being known by almost all. Python and C++ are known by half the company, while a third of the company is able to hold their own in Groovy, Scala, Clojure, Ruby and PHP. a few of them even know Perl.

Willhaben in Austria also know a few languages, with Java, C, JavaScript and C++ being known by almost all. PHP and Python is known by two thirds of the company, with Perl and Ruby edging onto the "known by half" list. Groovy, Scala just missed the 50% mark, along with Haskell.


SQL and it's many variations
----------------------------

![sql-you-know]{: .expandable-image .expandable-image-small}

When talking about databases, it's easy to think SQL is just "one thing", and if you know SQL, you can work with any database. And it's somewhat true, but each database has it's idiosyncrasies. This becomes especially clear when writing stored procedures, where basic SQL is lacking in control structures. So which database should we be using, really?

PL/pgSQL, the dialect used in PostgreSQL, is known by 193 respondents, so PostgreSQL seems like a good choice. SQL/PSM, which is used by MySQL and a few others, is known by 159 people, is a good second choice.

Of the large commercial databases, Oracle is clear out in front, known by 143 people, while runner up T-SQL used by both Microsoft SQL Server and Sybase is at 106. Any other contenders in the fight can just pack it in and go to bed. Fifth place goes to SQL PL, used by IBM DB2, known by only 32 respondents!

PSQL, used in Interbase and Firebird, makes a surprise showing at 18, while PL/PSM, the less used dialect supported by PostgreSQL has 17 users. Of the remainders only Watcom-SQL, used in some versions of Sybase, manages double digits, at 10 respondents.


So, how good are we at what we do?
----------------------------------

![experts]{: .expandable-image .expandable-image-small}

The results here are somewhat skewed by the fact that people will naturally be more skilled in the languages they use regulary, and the companies with the most number of respondents use only a few of the languages regulary. As a result, there are more Java-experts than any other kind of experts. 102 respondents consider themselves Java-experts. Java is also the only language where there are more experts than any of the other "ranks".

<div style="clear: both;">
<img src="{{ page.image_dir }}/expert/all/Java.png" title="Java" alt="Java" class="expandable-image expandable-image-small"/>
<img src="{{ page.image_dir }}/expert/all/JavaScript.png" title="JavaScript" alt="JavaScript" class="expandable-image expandable-image-small"/>
<img src="{{ page.image_dir }}/expert/all/PHP.png" title="PHP" alt="PHP" class="expandable-image expandable-image-small"/>
</div>
<div style="clear: both;"/>

Other languages we feel we know well are JavaScript and PHP. Just under 50 people consider themselves experts in those languages. Next is Python, with 22 experts. Objective-C and Scala have 11 experts each, and there are 7 Groovy-experts and 6 Ruby-experts in all of Schibsted.

Languages we don't really know are C#, Clojure, CoffeeScript, Go, Groovy, Objective-C, Scala and Swift. In fact, Clojure is also the only language where nobody considers them selves experts. 

Most people consider themselves above average when it comes to JavaScript (205 respondents scored 3 or higher), while only 15 don't know it at all. Similary, 255 people consider themselves above average in Java, and only 13 don't know it at all. Of the three most used languages, Bash is the only one where the skills are concentrated below average. 223 people scored themselves at 3 or less for Bash knowledge, of which 18 don't know it at all. At the other end, a single person considers themselves a Bash-expert.

<div style="clear: both;">
<img src="{{ page.image_dir }}/expert/all/Python.png" title="Python" alt="Python" class="expandable-image expandable-image-small"/>
<img src="{{ page.image_dir }}/expert/all/Objective-C.png" title="Objective-C" alt="Objective-C" class="expandable-image expandable-image-small"/>
<img src="{{ page.image_dir }}/expert/all/Scala.png" title="Scala" alt="Scala" class="expandable-image expandable-image-small"/>
</div>
<div style="clear: both;"/>

The numbers are perhaps more useful if we separate out the people who don't use a language in their current work, and then look at the distribution of skills in each group.

I'll pick a few interesting ones, graphs for all are available at the end.

Java is almost symetrical among the "amateurs". Most amateurs are about average, but of the rest, less and more knowledge is more or less equally distributed. This is in sharp contrast to the "pros", where a large majority consider themselves experts. While it's easy to think that in the group of "pros", it will always be many experts, Java, Objective-C and PHP are the only languages where the experts are the largest group.

<div style="clear: both;">
<img src="{{ page.image_dir }}/expert/pro/Java.png" title="Java" alt="Java" class="expandable-image expandable-image-small"/>
<img src="{{ page.image_dir }}/expert/pro/Objective-C.png" title="Objective-C" alt="Objective-C" class="expandable-image expandable-image-small"/>
<img src="{{ page.image_dir }}/expert/pro/PHP.png" title="PHP" alt="PHP" class="expandable-image expandable-image-small"/>
</div>
<div style="clear: both;"/>

JavaScript is a similar story to Java, except there are fewer experts in JavaScript. It's good to notice that everyone who works with JavaScript, actually do know the language, which was not the case with Java.

Apart from Java and JavaScript, C and PHP are the only languages where a significant amount of amateurs consider themselves above average in skills. At the other end, Clojure, CoffeeScript, Go, Groovy, Objective-C, Scala and Swift are all examples of languages where a large portion of the amateurs consider themselves to either not know it at all, or know less than average.

When comparing pro and amateurs, Objective-C really stands out. Most of the pros are experts, but among the amateurs it's virtually an unknown language.


What next?
----------

![favorites]{: .expandable-image .expandable-image-small}

The most popular choice for the next project is Java. 55 people would go with Java if allowed to choose freely. 43 people prefer JavaScript. Go, Scala and Python are in the next group at 37, 36 and 31. 17 people would select Ruby, 12 would go with Swift or PHP, 11 thinks Clojure is a good choice, and 10 people would go with C.

In this kind of survey, there are always some smartypants. Five people avoided the question with variations of "I'm not able to conceive of a project where the language I like the most is the perfect choice, so I'll say it depends on some hypotetical project specification that I have no say over". Other clever answers are Fortran, Visual Fox, and T-SQL. Some people prefer the simple over the complicated, and would go with languages usually reserved for small utility-scripts like AWK and Bash.


Self-improvement
----------------

![motivations]{: .expandable-image .expandable-image-small}

73.6% of us will sit down and learn a new language just to learn something new. 65.1% will accept learning something new if they need it for work, while only 41.4% are willing to do the same for non-work.

If we are so easy to motivate, what holds us back?

![languages]{: .expandable-image .expandable-image-small}

It could be the number of languages we think is "neccessary" to know. Knowing more than 6 languages is not neccessary in most peoples view, only 9.9% of us thinks it's worth knowing 7 or more languages. 29% of us think 5 languages is good enough, while 6.8% thinks knowing one language well enough is all you need to be a good programmer.

The majority seems to think between 3 and 5 languages should be our target. 70% think knowing 3, 4 or 5 languages defines a good programmer.

Nobody thinks you're any good if you know 9 languages, so if that's you, you should set out to learn atleast one more as soon as possible. Or just forget one. :-D


There's probably more here
--------------------------

I initially said I would blog about the results a couple weeks after the end of the survey. That ended up being a couple months instead. For that reason I have skipped looking at a few questions that could be interesting, but time consuming to look at. 

  * How many languages do you know vs. How many languages does a good programmer know
  * Which language currently not in use in a company, is most popular in the company?
  * Does experience correlate with number of languages?
  * What else does experience correlate with, if anything?
  * Skill level in your favorite language

I'm sure there are a number of other interesting things to look at, so I'm hoping someone else wants to take a look at the results and see what they find. The link is below. The first sheet is the raw, unedited responses. The second sheet is where I have tried to manually clean up some of the answers to get more sense out of the results. And all the other sheets are various views and tables I've used to make this post.

Let me know if you blog about these results, and I'll add a link here. Or just post in the comments below.


### Resources

[The results in full][results]
[Script to generate graphs][gen_graphs]

#### Skill levels, currently using the language at work

[Bash]({{page.image_dir}}/expert/pro/Bash.png), [C]({{page.image_dir}}/expert/pro/C.png), [C#]({{page.image_dir}}/expert/pro/C%23.png), [C++]({{page.image_dir}}/expert/pro/C++.png), [Clojure]({{page.image_dir}}/expert/pro/Clojure.png), [CoffeeScript]({{page.image_dir}}/expert/pro/CoffeeScript.png), [Go]({{page.image_dir}}/expert/pro/Go.png), [Groovy]({{page.image_dir}}/expert/pro/Groovy.png), [Java]({{page.image_dir}}/expert/pro/Java.png), [JavaScript]({{page.image_dir}}/expert/pro/JavaScript.png), [Objective-C]({{page.image_dir}}/expert/pro/Objective-C.png), [PHP]({{page.image_dir}}/expert/pro/PHP.png), [Python]({{page.image_dir}}/expert/pro/Python.png), [Ruby]({{page.image_dir}}/expert/pro/Ruby.png), [Scala]({{page.image_dir}}/expert/pro/Scala.png), [Swift]({{page.image_dir}}/expert/pro/Swift.png)

#### Skill levels, not using the language at work

[Bash]({{page.image_dir}}/expert/amateur/Bash.png), [C]({{page.image_dir}}/expert/amateur/C.png), [C#]({{page.image_dir}}/expert/amateur/C%23.png), [C++]({{page.image_dir}}/expert/amateur/C++.png), [Clojure]({{page.image_dir}}/expert/amateur/Clojure.png), [CoffeeScript]({{page.image_dir}}/expert/amateur/CoffeeScript.png), [Go]({{page.image_dir}}/expert/amateur/Go.png), [Groovy]({{page.image_dir}}/expert/amateur/Groovy.png), [Java]({{page.image_dir}}/expert/amateur/Java.png), [JavaScript]({{page.image_dir}}/expert/amateur/JavaScript.png), [Objective-C]({{page.image_dir}}/expert/amateur/Objective-C.png), [PHP]({{page.image_dir}}/expert/amateur/PHP.png), [Python]({{page.image_dir}}/expert/amateur/Python.png), [Ruby]({{page.image_dir}}/expert/amateur/Ruby.png), [Scala]({{page.image_dir}}/expert/amateur/Scala.png), [Swift]({{page.image_dir}}/expert/amateur/Swift.png)

#### Skill levels, all respondents

[Bash]({{page.image_dir}}/expert/all/Bash.png), [C]({{page.image_dir}}/expert/all/C.png), [C#]({{page.image_dir}}/expert/all/C%23.png), [C++]({{page.image_dir}}/expert/all/C++.png), [Clojure]({{page.image_dir}}/expert/all/Clojure.png), [CoffeeScript]({{page.image_dir}}/expert/all/CoffeeScript.png), [Go]({{page.image_dir}}/expert/all/Go.png), [Groovy]({{page.image_dir}}/expert/all/Groovy.png), [Java]({{page.image_dir}}/expert/all/Java.png), [JavaScript]({{page.image_dir}}/expert/all/JavaScript.png), [Objective-C]({{page.image_dir}}/expert/all/Objective-C.png), [PHP]({{page.image_dir}}/expert/all/PHP.png), [Python]({{page.image_dir}}/expert/all/Python.png), [Ruby]({{page.image_dir}}/expert/all/Ruby.png), [Scala]({{page.image_dir}}/expert/all/Scala.png), [Swift]({{page.image_dir}}/expert/all/Swift.png)

{% comment %}
    links
{% endcomment %}

[results]: https://docs.google.com/spreadsheets/d/1naKVK5YnUb0suEFyNIZlN6p2ils1rUGVI0zdFn6jGgM/edit?usp=sharing
[gen_graphs]: {{ page.image_dir }}/gen_graphs.py
[babel]: {% post_url 2012-09-04-leaving-the-tower-of-babel %}
[bashttpd]: https://github.com/avleen/bashttpd

{% comment %}
    images
{% endcomment %}

[responses]: {{ page.image_dir }}/responses_by_company.png
[age]: {{ page.image_dir }}/age_chart.png
[current_work]: {{ page.image_dir }}/current-work.png
[languages-you-know]: {{ page.image_dir }}/languages-you-know.png
[sql-you-know]: {{ page.image_dir }}/sql-dialects-you-know.png
[experts]: {{ page.image_dir }}/experts.png
[favorites]: {{ page.image_dir }}/favorites.png
[languages]: {{ page.image_dir }}/languages_to_know.png
[motivations]: {{ page.image_dir }}/motivations.png

