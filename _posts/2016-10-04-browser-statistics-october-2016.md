---
layout: post
comments: true
date: 2016-10-04 12:02:32+0200
authors: Henning Spjelkavik
title: "Browser statistics October 2016"
tags:
- browser statistics
---

Last year, we [presented the browser statistics of FINN.no as of June 2015](http://tech.finn.no/2015/06/25/browser-statistics-june-2015/). It's time for an update!

## How many visitors use a desktop or laptop?

[//]: # (Source: http://tableau.finn.no:8000/views/Mobilrapport2_0-weekly/Visitsperchannel)

Our first graph shows the share of our users using a mobile phone, tablet or a desktop computer to access FINN.no, regardless of whether they use our responsive web app (m.finn.no), or a native Android or iPhone app. 66% of our visits are now from a smartphone or a tablet. *The traditional desktop/laptop has a market share of 34%.*

[<img class="center-block" alt="Channel graph" src="/images/2016-10-04-browser-statistics-oct-2016/Visits per channel percent.png"/>](/images/2016-10-04-browser-statistics-oct-2016/Visits per channel percent.png)

If we exclude the native app users, which are obviously on either Android or iOS, what is the distribution between table, mobile and "other" (desktop) on our web site?

[<img class="center-block" alt="Channel graph without app" src="/images/2016-10-04-browser-statistics-oct-2016/Visits per channel percent ohne app.png"/>](/images/2016-10-04-browser-statistics-oct-2016/Visits per channel percent ohne app.png)



## Which FINN.no application do people use?

We have two major offerings of FINN.no - the responsive web (served from the domains m.finn.no and www.finn.no), and native apps for mobile devices. 24% of our visits are from our native apps, and 76% from our responsive web.

Since we're in the middle of a migration, some of our internal details leaks out in this graph; some parts of the finn.no traffic is served from the domain www.finn.no and the majority from the domain m.finn.no. They are supposed to be merged "real soon now".

[<img class="center-block" alt="Application graph" src="/images/2016-10-04-browser-statistics-oct-2016/Visits per application percent.png"/>](/images/2016-10-04-browser-statistics-oct-2016/Visits per application percent.png)

## Browsers

First of all, let's take a look at the numbers of the *browser vendors*. The ranking is clear, Apple is the biggest, ahead of Google, Microsoft, Samsung and Mozilla. Opera is no longer in the top 5.

[//]: # (https://sc3.omniture.com/sc15/reports/index.html?a=Report.Standard&r=Report.GetConversions&rp=e%7C1&0=58771601990286&bookmark=15091307&ssSession=02cf5bacd4842708446fee96acb31b6a&jpj=55877982372978)

[<img class="center-block" alt="All providers" src="/images/2016-10-04-browser-statistics-oct-2016/browser-types.png"/>](/images/2016-10-04-browser-statistics-oct-2016/browser-types.png)

In case you wondered which browser is the biggest on the "desktop" here's the trend. (Ignore the orange and blue triangle markers)

[//]: # ( https://sc3.omniture.com/sc15/reports/index.html?rp=period_from%7C10%2F01%2F15%3Bperiod_to%7C09%2F30%2F16%3Bperiod%7C1150901D366%3Brange_period%7C1%3Bgranularity%7Cweek&r=Report.GetConversions&a=Report.Standard&ssSession=02cf5bacd4842708446fee96acb31b6a&jpj=77984075026732   Segment: prod.device: non-mobile  )

[<img class="center-block" alt="Browsers, Windows" src="/images/2016-10-04-browser-statistics-oct-2016/desktop.png"/>](/images/2016-10-04-browser-statistics-oct-2016/desktop.png)

The *complete browser statistics*, across all applications and devices are as follows:

<a href="/images/2016-10-04-browser-statistics-oct-2016/browsers-all.png"><img class="center-block" alt="All browsers" src="/images/2016-10-04-browser-statistics-oct-2016/browsers-all.png"/></a>

It still seems like a good idea to make sure your website works well with Safari! The old giant Microsoft now has only the 5th spot with IE 11 (6.8%) and the 12th with Edge 13 (1.7%).

## Mobile details ##

Which devices do we see?

<a href="/images/2016-10-04-browser-statistics-oct-2016/mobile-devices.png"><img class="center-block" alt="Mobile devices" src="/images/2016-10-04-browser-statistics-oct-2016/mobile-devices.png"/></a>


The most popular browsers on mobile devices:

[//]: # ( https://sc3.omniture.com/sc15/reports/index.html?rp=ob_segment_id%7C5499485ce4b0b3ffc6f830ec&r=Report.GetConversions&a=Report.Standard&ssSession=02cf5bacd4842708446fee96acb31b6a&jpj=4691664005674 )

<a href="/images/2016-10-04-browser-statistics-oct-2016/browsers-mobile.png"><img class="center-block" alt="All browsers" src="/images/2016-10-04-browser-statistics-oct-2016/browsers-mobile.png"/></a>

Then the question is - what is the "Android Browser"? This is the break down of devices that has delivered traffic from the "Android Browser".

<a href="/images/2016-10-04-browser-statistics-oct-2016/android-browser-devices.png"><img class="center-block" alt="All browsers" src="/images/2016-10-04-browser-statistics-oct-2016/android-browser-devices.png"/></a>

Similarily for the Samsung Browser:.

<a href="/images/2016-10-04-browser-statistics-oct-2016/samsung-browser-devices.png"><img class="center-block" alt="All browsers" src="/images/2016-10-04-browser-statistics-oct-2016/samsung-browser-devices.png"/></a>

And finally the trend on mobile browsers, by vendor:

[//]: # ( https://sc3.omniture.com/sc15/reports/index.html?rp=preset%7CLast%2012%20months%3Bperiod_from%7C10%2F01%2F15%3Bperiod_to%7C09%2F30%2F16%3Bperiod%7C1150901D366%3Brange_period%7C1%3Bgranularity%7Cmonth&r=Report.GetConversions&a=Report.Standard&ssSession=02cf5bacd4842708446fee96acb31b6a&jpj=84809186172571 )

<a href="/images/2016-10-04-browser-statistics-oct-2016/browsers-mobile-trend.png"><img class="center-block" alt="All browsers" src="/images/2016-10-04-browser-statistics-oct-2016/browsers-mobile-trend.png"/></a>

## Which version of Android or iOS? ##

Our apps need to work well on several versions of Android and iOS. How fast are our users upgrading? This shows the distribution of operating systems among our apps users.

[<img class="center-block" alt="Android version" src="/images/2016-10-04-browser-statistics-oct-2016/android-app-version.png"/>](/images/2016-10-04-browser-statistics-oct-2016/android-app-version.png)

[<img class="center-block" alt="iOS version" src="/images/2016-10-04-browser-statistics-oct-2016/ios-app-version.png"/>](/images/2016-10-04-browser-statistics-oct-2016/ios-app-version.png)


[<img class="center-block" alt="Android version" src="/images/2016-10-04-browser-statistics-oct-2016/android-app-version-top15.png"/>](/images/2016-10-04-browser-statistics-oct-2016/android-app-version-top15.png)

[<img class="center-block" alt="iOS version" src="/images/2016-10-04-browser-statistics-oct-2016/ios-app-version-top15.png"/>](/images/2016-10-04-browser-statistics-oct-2016/ios-app-version-top15.png)

