---
layout: post
comments: true
date: 2017-02-02 15:14:30+0100
authors: Tor Arne Kvaløy
title: "Category guessing"
tags:
- javascript
- machine learning
---

## The problem
**Making it easy to fill out forms is essential for getting data into our system at [FINN småjobber](https://www.finn.no/smajobber)**, which is a service to get help with tasks like house cleaning or renovation, where the user submits a job that non-professionals bid on.

Submitting a job is done by filling out a form with a title, a short description, and selecting the category the job belongs to. The category hierarchy has two levels: a main category and a subcategory. For example the main category *domestic help*, has the subcategories cleaning and *babysitting*. There are 6 main categories and 26 subcategories, and our assumption was that it was a hassle for the user to go through this long list. **We wanted to simplify this by guessing the category.** Ideally on the given title.


## The solution

### Machine learning
A typical solution to this kind of problem is to use machine learning, where the goal is to classify job titles into categories. **We had a large set of historical data (130 000 registered jobs) where category already was selected by the user.** This was a good starting point for a type of machine learning technique called supervised learning. **In supervised learning historical data is used as a training set**, and results in a classifier that can classify job titles into categories. 

Delving into the field of machine learning is quite a complex task, and requires both theoretical  understanding and knowhow of various software libraries. And in many cases, it requires access to a special machine learning infrastructure. So, **as a lean product development team with limited resources we looked for an easier solution**.

### Good old search

Search was a sentral part of our service. All the jobs were indexed, and we had all the necessary libraries and infrastructure for this. So, **what about using normal search to find the category?**

A simple search on the title “Cleaning kitchen”, gave the following ranked results:


| Match rank | Category|
|-|-----|
|1|Renovation|
|2|Cleaning|
|3|Cleaning|
|4|Renovation|
|5|Cleaning|
|6|Cleaning|
|7|Cleaning|
|8|Cleaning|
|9|Carpentry and assembly|
|10|Cleaning|
|||

Our first attempt was to simply **select the highest ranked match**. This gave us 87% chance for guessing the main category correctly, and 68% chance of guessing the subcategory correctly. 

These probabilities where calculated in a simulation by guessing category for 10000 random jobs in our dataset, and comparing the guessed category with what was actually selected by the user.

**We were quite impressed by these results**, however, we wished to provide the users with even better guesses. When we analysed the search results for various job titles, we found that the correct category often was not the highest ranked match, but number 2 or 3, as shown in the table above.

So based on this insight we came up with a simple algorithm that **finds the most frequently represented categories among the 10* best matches**. This gave us the following results:


|Category|Frequency|
|--------|---------|
|Cleaning|	7|
|Renovation|	2|
|Carpentry and assembly|	1|
|||

**It was safe to assume that the correct category was cleaning, as it is represented 7 times.** 

We only wanted to make a guess when we were quite certain, and concluded that **when a category is represented 6 or more times among the 10 best matches, we make a guess**. As for the rest, we leave it up to the user to manually select a category. 

This algorithm improved our guessing, and in our simulation gave us a **95% chance of guessing main category correctly**, and **85% chance of guessing subcategory correctly**.

## In production

The following graph shows that we guess category for about 60% of all the jobs.  


![Success matches in production](/images/2017-02-02-category-guessing/production.png "Success matches in production")


**The graph also shows that around 90% of the jobs are submitted with the the guessed category.** As for the remaining 10% of the guessed jobs, we assume that we have guessed wrong category, and that the user has changed the proposed category to one they found more suitable.

What is interesting is that having the user correct our guesses, it will **automatically improve our guesses over time**, as the given title will be associated with a more correct category.




### Thanks to 
- Håvard Nesvold for proofreading and feedback. 
- The brilliant FINN småjobber team!
- Simen Eide and Fredrik Jørgensen for good talks on machine learning.


*) We experimented with the number of best matches to include in the calculation, but found that 10 gave the overall best results. 
