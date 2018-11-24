---
layout: post
comments: true
date: 2016-11-01 12:17:32+0200
authors: Per Jørgen Walstrøm
title: "Hello world!"
tags:
- schibsted
- messaging
- barcelona
---

As some of you may know, FINN is owned by [Schibsted](http://www.schibsted.com/). What may not be that familiar, is that Schibsted has online classified solutions in several markets around the world, including [Blocket](http://www.blocket.se) in Sweden, [Le Bon Coin](http://www.leboncoin.fr) in France and [Vibbo](http://www.vibbo.es) in Spain. Schibsted has several hundreds of developers distributed around the globe, and there is a strong need and will to coordinate and cooperate across geographical boundaries.

## The need to cooperate
Reinventing the wheel is generally a bad idea. With that in mind, Schibsted has a strong focus on building global components that can be useful for all the companies within Schibsted. However, it is not always easy to understand the needs and challenges across national borders. There are cultural differences and different companies have reached different maturity levels. FINN has been around for years, and is one of the more mature classified sites within Schibsted. In an effort to help building global components, Schibsted has set up an international hub in Barcelona and developers from the local companies are encouraged to come to Barcelona to help out, share knowledge and build a common platform. Several developers from FINN have taken up the challenge and spent some months in beautiful and vibrant Barcelona. Here are Øyvind's impressions.

![developers_in_bcn](/images/2016-06-28-working-for-another-sch-company/20160628_160134.jpg "happy campers")
*Anders, Øyvind and Per Jørgen. Three happy FINN-campers in Barcelona*

## Øyvind goes messaging
Øyvind has worked as a developer for FINN for 9 years, but when he got the opportunity to move to Barcelona for 6 months, the decision was easy.

\- The decision was really easy. There was little risk involved and I thought it would be an excellent opportunity to experience Barcelona. I love the city and the international atmosphere at the office. I think we are around 25 nationalities working here.

\- Tell us about the work you have been doing in Barcelona

\- I work as a developer here, in a team that developes a global messaging-component to be used by Schibsted-owned classified-sites around the world. When I arrived in January, we had integrated 6 global sites and the platform handled hundreds of thousands messages/day. Today, we have even more sites using our platform. It's worth mentioning that the messaging-platform I'm working on, originally was developed by FINN. In 2014, however, it was decided that this platform was to be promoted to become a global component, so the responsibility was moved from Oslo to the global components team in Barcelona. The developers in Barcelona inherited the FINN code-base and developed it from being a FINN-only solution to a full-fledged multitenant solution. It's not easy to inherit code like that, but I feel the ownership has been transferred now. The Barcelona-team owns the product and is soon going to integrate with FINN as well. We'll replace FINN's messaging platform with the global component-version, but that is not an easy task. FINN needs to preserve it's messaging history, and that means we have to import 150 million messages! It's a big job for our Cassandra-nodes.

\- What are the technical differences compared to working in FINN?

\- We use [Amazon Web Services](http://aws.amazon.com/) and it is really great to see how easy it is to scale up and set things into production. The infrastructure team here in Barcelona has done an amazing job and I feel it is really at the bleeding edge of technology working in this environment. 

\- You are soon returning to Norway. What will you miss from your stay in Barcelona?

\- The people! It has been great working here, in this including atmosphere. 

\- Learned any lessons?

\- When working in FINN, it's easy to forget that you are part of a bigger company. I see that clearly now, the global perspective. It's inspiring to know that I am a part of a global company.

## Epilogue
Øyvind spent 6 months in Barcelona and came home with lots of new knowledge, new friends and a Messi-shirt. He contributed in building global components and believes FC Barcelona will win Champions League again this season. The migration of the FINN-messaging platform went off without a hitch and we are still [looking for great developers](https://finn.no/apply-here)!




