---
authors: mick
comments: true
date: 2013-06-20 11:56:20+00:00
layout: post
title: Dark Launching and Feature Toggles
redirect_from: /dark-launching-and-feature-toggles/
---

[![Dark side of the moon](http://th02.deviantart.net/fs47/200H/f/2009/194/3/1/Dark_Side_of_The_Moon_by_Be_Toru.png)](http://be-toru.deviantart.com/art/Dark-Side-of-The-Moon-129409258)

Make sure to distinguish between these two.

They are not the same thing,
and it's a lot quicker to just _Dark Launch_.

In addition Dark Launching promotes incremental development, continuous delivery, and modular design. Feature Toggles need not, and can possibly be counter-productive.

Dark Launching is an operation to silently and freely deploy something. Giving you time to ensure everything operates as expected in production and the freedom to switch back and forth while bugfixing. A Dark Launch's goal is often about being completely invisible to the end-user. It also isolates the context of the deployment to the component itself.

Feature Toggling, in contrast, is often the ability to A/B test new products in production. Feature Toggling is typically accompanied with measurements and analytics, eg NetInsight/Google-Analytics. Feature Toggles may also extend to situations when the activation switch of a dark launch can only happen in a consumer codebase, or when only some percentage of executions will use the dark launched code.

Given that one constraint of any decent enterprise platform is that all components must fail gracefully Dark Launching is the easiest solution, and a golden opportunity to ensure your new code fails gracefully. Turn the new module on, and it's dark launched and in use, any problems turn it off again. You also shouldn't have to worry about only running some percentage of executions against the new code, let it all go to the new component, if the load is too much the excessive load should also fail-gracefully and fall back to the old system.

Dark Launching is the simple approach as it requires no feature toggling framework, or custom key-value store of options. It is a DevOps goal that remains best isolated to the context of DevOps – in a sense the 'toggling' happens through operations and not through code. When everything is finished it is also the easier approach to clean up. Dealing with and cleaning up old code takes up a lot of our time and is a significant hindrance to our ability to continually innovate. In contrast any feature toggling framework can risk encouraging a mess of outdated, arcane, and unused properties and code paths. KISS: always try and bypass a feature toggle framework by owning the 'toggle' in your own component, rather than forcing it out into consumer code.

Where components have [CQS](http://en.wikipedia.org/wiki/Command-query_separation) it gets even better. First the command component is dark launched, whereby it runs in parallel, and data can be test between the old and new system (blue-green deployments). Later on the query component is dark launched. While the command components can run and be used in parallel for each and every request the query components cannot. When the dark launch of the query component is final the old system is completely turned off.

Now the intention of this post isn't to say we don't need feature toggling, but to give terminology to, and distinguish, between two different practices instead of lumping everything under the term "feature toggling". And to discourage using a feature toggling framework for everything because we fail to understand the simpler alternative.

In the context of front-end changes, it's typical that for a new front-end feature to come into play there's been some new backend components required. These will typically have been dark launched. Once that is done, the front-end will introduce a feature toggle rather than dark launch because it's either introducing something new to the user or wanting to introduce something new to a limited set of users. So even here dark launching can be seen not as a "cool" alternative, but as the prerequisite practice.

Reference: "[DevOps for Developers](http://bit.ly/16IgCit)" By Michael Hüttermann