---
layout: post
comments: true
date: 2017-03-10 17:58:36+0100
authors: Ivar Conradi Østhus
title: "Unleash Your Features Gradually"
tags: 
- unleash
- feature toggles
---


[FINN.no](https://www.finn.no) is the largest online marketplace in Norway, and we take continuous deployment seriously. We are about one hundred developers deploying new code to production 978 times each week. That is 978 / 100 = 9.78 deployments to production per developer every week. In order to get to these numbers and still keep it under control, we have had to both change our development process and establish the right set of tools. In this article we introduce feature toggles and how this technique has contributed to our continuous deployment success. 


We will also present [Unleash, an open source framework](https://github.com/Unleash/unleash) we created to support feature toggles at an enterprise scale. Unleash allows us to enable features for specific users via the activation strategies concept. Unleash is already set up and in use by multiple teams within Schibsted, including FINN, SPT Payment, and Knocker. 

![alt text](/images/2017-03-10-unleash-your-features-gradually/1-finn-releases.png "Number of production deploys in “week 50” in recent years")<center><i>Number of production deploys in “week 50”</i></center>

<br /><br />

## Feature Toggles
'Feature toggles' is a simple technique to separate the process of putting new code into production from the process of releasing new features to our users. In its simplest form a feature toggle is just a “if” statement in the code, guarding the new feature:

```java
if (unleash.isEnabled("AwesomeFeature")) {
  //magic new feature code
} else {
  //old boring stuff
}
```

The fastest way to get started with feature toggles in your project is to use a plain old [properties file](https://docs.oracle.com/javase/tutorial/essential/environment/properties.html). This allows you to integrate unfinished features to the master branch and hide them from users in production. A simple plain properties files was exactly how we started playing with the features toggles in FINN.

We realised that feature toggles enabled us to move faster, because we were able to integrate new and unfinished features to the master branch early. This also avoids long-running feature branches that tend to be harder to merge. We learned the hard way that having many parallel feature branches adds a lot of extra complexity related to merging, but with the help of feature toggles we can merge them directly to master. 

This went well, but we also felt we could do better. We wanted one simple dashboard to turn the feature toggles on and off. The dashboard should contain all feature toggles for all web apps and applications in FINN. After creating several  proof-of-concepts using various technologies, we proposed a solution to our technology management group. They loved the idea and we created [Unleash](https://github.com/Unleash/unleash), our shiny and open source feature toggle system. 

Unleash gives us a great overview of all feature toggles across all our services and applications. It also makes it super easy to activate new features in real time in production and of course instantaneous deactivation of  the feature if there’s a problem. 

![alt text](/images/2017-03-10-unleash-your-features-gradually/2-unleash-dashboard.png)


## System Overview

Unleash is comprised of three parts:

- **Unleash API** - The service holding all feature toggles and their configurations. Configurations declare which activation strategies to use and which parameters they should get.
- **Unleash UI** - The dashboard used to manage feature toggles, define new strategies, look at metrics, etc. 
- **Unleash SDK** - Used by clients to check if a feature is enabled or disabled. The SDK also collects metrics and sends them to the Unleash API. Activation Strategies are also implemented in the SDK. Unleash currently provides official SDKs for Java and Node.js

<img class="center-block" src="/images/2017-03-10-unleash-your-features-gradually/3-unleash-diagram.png" title="Unleash system overview" alt="Unleash system overview" />
<center><i>Unleash system overview</i></center>

Unleash is written with performance and resilience in mind. 

- **Performance** - In order to be super fast, the client SDK caches all feature toggles and their current configuration in memory. The activation strategies are also implemented in the SDK. This makes it really fast to check if a toggle is on or off because it is just a simple function operating on local state, without the need to poll data from the database. 
- **Resilience** - If the unleash API becomes unavailable for a short amount of time, the cache in SDK will minimise the effect. The client will not be able get updates, when the API is unavailable, but the SDK will keep running with the last known state. 
- **Built in backup** - The SDK also persists the latest known state to a local file at the instance where the client is running. It will persist a local copy every time the client detects changes from the API. Having a local backup of the latest known state minimises the consequence of clients not being able to to talk to Unleash API at startup. This is required because network is unreliable.

## Gradual Roll Out of Features
It is powerful to be able to turn a feature on and off instantaneously without redeploying the whole application. The next level of control comes when you are able to enable a feature for a specific user or a small subset of the users. We achieve this level of control with the help of activation strategies. The simplest strategy is the “default” strategy, which basically means that the feature should be enabled for everyone.

Another common strategy is to enable a new feature only for specific users. Typically I want to enable a new feature only for myself in production, before I enable it for everyone else. To achieve this we can use the “UserWithIdStrategy”. This strategy allows you to specify a list of specific user ids that you want to activate the new feature for. *(A user id may of course be an email if that is more appropriate in your system.)*

![alt text](/images/2017-03-10-unleash-your-features-gradually/4_user_with_id.png)

When I have verified that the new feature works as expected in production, I can activate the feature for some real users. To achieve this I can use one of the gradual rollout strategies. At FINN we use three variants of gradual rollout, which have slightly different purposes: 

**GradualRolloutUserId** is the strategy we use when we want to gradually rollout a new feature to our logged-in users. This strategy guarantees that the same user gets the same experience every time, across devices. It also guarantees that a user from the first 10% will also be part of the first 20% of the users. Thus we ensure that users get the same experience. Even if we gradually increase the number of users who are exposed to a particular feature. To achieve this we hash the user id and normalise the hash value to a number between 1 and 100 with a simple modulo operator. 

<img class="center-block" src="/images/2017-03-10-unleash-your-features-gradually/5_hash_and_normalise.png" alt="5_hash_and_normalise" />
<center><i>Converting a user id string to a number between 1 and 100</i></center>
<br /><br />

**GradualRolloutSessionId** is almost identical to the previous strategy, with the exception that it works on session ids. This makes it possible to target all users (not just logged in users), guaranteeing that a user will get the same experience within a session.

**GradualRolloutRandom** has no form of user stickiness, it just picks a random number for each “isEnabled” call. We have found this rollout strategy to be very useful in some scenarios when we are enabling a feature which is not visible to the end user. Because of its randomness it becomes a great way to sample metrics and error reports and it can be a useful way to detect JavaScript errors in the browser.

<img class="center-block" src="/images/2017-03-10-unleash-your-features-gradually/6_gradual_rollout_random.png" alt="gradual_rollout_random.png" />

In Unleash 2.0 we added support for multiple strategies. This is especially convenient when you both want to expose your new shiny feature to a percentage of the users and at the same time make sure to expose it to yourself. 

## Metrics
When you enable a feature for a small group of users it is nice to know how many users actually get exposed to it. This is why we added metrics to Unleash 2.0. The SDK will locally collect metrics in the background and regularly share this information with the Unleash API. The API then aggregates the metrics from all clients and calculates how many times a feature toggle evaluated to enabled in a given time period. 

<img class="center-block" src="/images/2017-03-10-unleash-your-features-gradually/7_metrics.png" alt="metrics" />

When implementing the metrics feature we discovered that this was a nice way to display which toggles are actually in use in an application. It even made it possible to show feature toggles being used in the client code which are not defined in the Unleash API. Showing undefined toggles used in client applications makes it easier to discover feature toggles not defined in Unleash UI. This can happen when a developer starts using a new feature toggle, but has not defined it in Unleash UI yet. The undefined feature toggle is also a clickable link to  make it easy to define it in Unleash.

<img class="center-block" src="/images/2017-03-10-unleash-your-features-gradually/8_undefined_toggle.png" alt="undefined" />


## Types of Toggles
We have seen how release toggles allow us to decouple deployment of code from the release of new features. At FINN we have ended up with three main categories of feature toggles:


**Release toggles** are the most common type of feature toggles we use. These are used for new features we roll out in the market and are mainly used to roll them out safely:  instead of exposing a new feature to everyone at once we can try it out on a small subset of our users and verify that it scales, does not contain bugs, and that it moves our KPIs in the right direction.

**Kill switches** are sometimes necessary to protect our site. Like many other large sites we occasionally have some features that struggle under certain edge cases, often related to third party integrations. The Kill switches allow us to turn these features off, which is a simple way to degrade our service in order to keep the business running. Of course it would be nice to not need the kill switches, but they have proven their value over time. 

**Business toggles** are used to turn on certain features for specific users, segments or customers. It’s pretty straightforward to solve this in Unleash because of the flexible activation strategies, but we generally don’t use Unleash for this. It’s considered acceptable for experimenting with new ways to segment our service, but we don’t want the business toggles to become a permanent part of Unleash. We think permanent business rules should be handled by the application code and not Unleash. 

## Tips and Best Practices
We have been using feature toggles for several years in FINN. Here are some tips about how to use them more effectively:

### Feature Toggles Increase Technical Debt
One of the biggest issue we have found is that due to ease of use developers love to add new feature toggles but don’t seem to remove them afterwards. Sometimes developers keep them around because they feel they are “nice to have”. Other times they are just forgotten or no one has the time to refactor and remove them. A feature toggles is technical debt from the moment it is added to the code.  The general advice is to remove the toggle as soon as it has served its purpose. Every feature toggle adds a new code path through the application making it harder to test and debug the code.

### Don’t Use Feature Toggles if You Don’t Need Them
In many cases you can safely add a new and unused feature to the application without protecting it with a feature toggle. It might be a new API endpoint or a new page on a separate URL, either way a feature toggle is not needed. 

If you have to protect your new feature you should try to use as few if-statements as possible, ideally just one if-statement should guard the feature.

## Further Plans for Unleash
Unleash has been actively used in production by FINN from 2014. The project has been lucky to have many different [contributors](https://github.com/Unleash/unleash/graphs/contributors) over the years.

Currently [Ivar](https://github.com/ivarconr), [Sveinung](https://github.com/sveisvei) and [Vegard](https://github.com/vsandvold) actively maintain the project. We have some great ideas for the future and we hope to see some of them implemented. In this section we will briefly explain some of these ideas. We would love to hear your feedback on them!

### Unleash as SaaS
Currently you have to host your own instance of the API and the UI in order to use Unleash. This includes setting up a database and running a node application in production. We believe it could greatly improve adoption if Unleash was provided as a software as a service (SaaS). It would of course require us to add some access control, but this is something we have on the roadmap anyway. This might require us to charge the users of Unleash for a monthly cost and spend that money on hosting and new development. 

### “AND Strategies”
Unleash 2.0 added support for multiple strategies. This was a huge improvement! But it is implemented as a simple array where all the strategies are “OR”-ed in order to determine if a feature should be enabled. We believe we could improve this by adding support for required strategies. This would make it possible to combine strategies more freely to create new segmented roll-out groups. For example, this would make it trivial to only gradually roll out a feature toggle to our beta users. 

### Authentication and Access Control
The Unleash service already provides hooks for registering your own middleware, so that you can actually add your own access control. But why not add a few ready to use implementations, such as Google Authentication or Okta? This would make it much easier to integrate into an enterprise already using an identity provider. 

### Application Scoped Toggles
Today all applications receive all toggles for everyone in the same cluster. In Unleash 2.0 we added a client registration feature, where all clients register with the API using a unique identifier. We could use the application identifiers to provide a simple way to define which application you intend to use a feature toggle for. This would make it possible to only expose relevant feature toggles to relevant applications.

### Time to Live
Because most features toggles should be used for a limited time it would be nice to be able to set a time-to-live field on them. Then Unleash UI could then inform developers about toggles they use which have expired, which would be a great motivator to clean up feature toggles which have served their purpose. 

### A/B Testing
At FINN we use Unleash as a way to setup and manage simple A/B tests. Our current implementation is a bit cumbersome and we believe there is room for generalising and standardising this, making it available to anyone. Also, in order to support multivariate experiments we might need to change the API slightly. 

These are just some of the ideas we have for Unleash. Which features would you like to see in Unleash? I would love to hear from you!

## Summary
In this article we introduced feature toggles and how FINN uses Unleash to gradually release new features. This approach gives us more control because it decouples the process of putting new code into production from the release of new features for our users.  

Sound interesting? Check out the [getting started guide](https://github.com/Unleash/unleash/blob/master/docs/getting-started.md). If you need help please do not hesitate file an issue or reach out to any of the [core contributors](https://github.com/Unleash/unleash/graphs/contributors).

**Thanks to everyone who has contributed to the project. All contributions are highly appreciated.**
