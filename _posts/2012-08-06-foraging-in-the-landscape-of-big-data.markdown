---
author: mick
comments: true
date: 2012-08-06 09:26:27+00:00
layout: post
slug: foraging-in-the-landscape-of-big-data
title: Foraging in the landscape of Big Data
wordpress_id: 1508
categories:
- Systems development
tags:
- Big data
- innovation
- lean
- product development
---

![](/wp-content/uploads/2012/08/salamander.gif) 
This is the first article describing FINN.no's coming of age with Big Data. It'll cover the challenges arising the need for it, the challenges in accomplishing new goals with new tools, and the challenges that remain ahead.

Big Data is for many just another vague and hyped up trend getting more than its far share of attention. The general definition, from [Wikipedia](http://en.wikipedia.org/wiki/Big_data), is big data covers the scenario where existing tools fail to process the increasing amount or dimensions of data. This can mean anything from:

**α** – the existing tools being poor _(while large companies pour $$$ into scaling existing solutions up)_ or
**β** – the status quo requiring more data to be used, to
**γ** – a requirement for faster and more connected processing and analysis on existing data.

  
[![http://navajonationparks.org](/wp-content/uploads/2012/08/21603273182394671_O5rG91sY_f-e1344176794222.jpg)](http://navajonationparks.org)The latter two are also described as big data's [three V](http://www.greenbookblog.org/2012/03/21/big-data-opportunity-or-threat-for-market-research/)'s: Volume, Variety, Velocity. If the theoretical definition isn't convincing you put it into context against some of today's big data crunching use-cases..._
• online advertising combining content analysis with behavioural targeting,
• biomedical's DNA sequence tagging,
• pharmaceutics's metabolic network modelling,
• health services detecting disease/virus spread via internet activity & patient records,
• the financial industry ranging from credit scores at retail level to quant trading,
• insurance companies crunching actuarial data,
• US defence programs for offline (ADAMS) and online (CINDER) threat detection,
• environmental research into climate change and atmospheric modelling, and
• neuroscience research into mapping the human brain's neural pathways._

  
On the other hand big data is definitely no silver bullet. It cannot give you answers to questions you haven't yet formulated (pattern recognition aside), and so it doesn't give one excuses to store overwhelming amounts of data where the potential value in it is still [undefined](http://seekingalpha.com/article/441171-beware-the-hype-over-big-data-analytics). And it certainly won't make analysis of existing data sets initially any easier. In this regard it's less to do with the difficulty of achieving such tasks and more to do with the potential to solve what was previously impossible.

Often companies can choose their direction and the services they will provide, but in any competitive market where one fails to match the competitor's offerings it can result in the fall of that company. Here Big Data earns its hype with many a CEO concerned into paying attention. And it probably gives many CEOs a headache as the possibilities it opens, albeit tempting or necessary, create significant challenges in and of themselves. The multiple vague dimensions of big data also allows the critics plenty of room to manoeuvre.

One can argue that to [scale up](http://www.youtube.com/watch?v=zAbFRiyT3LU): to buy more powerful machines or to buy more expensive software solves the problem (α). If all you have is this problem then sure it's a satisfactory solution. But ask yourself are you successfully solving today's problem while forgetting your future?


> “If we look at the path, we do not see the sky.” – Native American saying


One can also argue away the need for such vasts amounts of data (β). Through various strategies: more aggressive normalisation of the data, storing data for shorter periods, or persisting data in more ways in different places; the size of each individual data set can be significantly reduced. Excessively normalising data has its benefits and is what one may do in the Lean development approach for any new feature or product. Indeed a simpler datamodel trickles through into a simpler application design, in turn leaving more content, more productive, pragmatic developers. Nothing to be scoffed at in the slightest. But in this context the Lean methodology isn't about any one state or snapshot in time illustrating the simple and the minimalistic, rather it's about evolution, the processes involved and their direction. Much like the KISS saying: it's not about doing it simple stupid but about *keeping* it simple stupid. Here it questions to how does overly normalised data evolve as its product becomes successful and something more complicated over time. Anyone who has had to deal awkwardly with numerous and superfluous tables, joins, and indexes in a legacy application because it failed over time to continually improve its datamodel due to needs of compatibility knows what we're talking about. There is another problem from such legacy applications that follow a datamodel centric design: the datamodel itself becomes a public API and the many consumers of it create this need of compatibility and the resulting inflexible datamodel. But this isn't an underlying but rather overlapping problem as one loses oversight of the datamodel in its completeness and in a way represented optimally.


![](/wp-content/uploads/2012/08/big-data-300x225.jpg)  
  
It also difficult to deny the amount of data we're drowning in today.
“90% of the data that exists today was created in the last two years.. the sheer volume of social media and mobile data streaming in daily, businesses expect to use big data to aggregate all of this data, extract information from it, and identify value to clients and consumers.. Data is no longer from our normalised datasets sitting in our traditional databases. We're accessing broader, possibly external, sources of data like social media or learning to analyse new types of data like image, video, and audio..” – [greenbookblog.org](http://www.greenbookblog.org/2012/03/21/big-data-opportunity-or-threat-for-market-research/).

And one may also argue that existing business intelligence solutions (can) provide the analysis required from all already existing datasets (γ). It ignores a future of possibilities: take for example the research going into behavioural targeting giving glimpses into the challenges of modern marketing as events and trends spark, shift, and evolve through online social media with ever faster frequencies – just the tip of the iceberg when one thinks forward to being able to connect face and voice recognition to emotional pattern matching analysis. But it also defaults to the conservative opinion that business intelligence need be but a post-mortem analysis of events and trends so to provide the insights intended and required only for internal review. This notion that this large scale analysis is of sole benefit to company strategy must become the tell tale of companies failing to see how users and the likes of social media change dramatically what is possible in product development today.

The methodologies of innovation therefore change. Real time analysis of user behaviour plays a forefront role in decisive actions on what product features and interfaces will become successful. This is the potential to cut down the risk of product development's internal guesswork for a product's popularity at any given point in time. In turn this cuts down time-to-market bringing the products release date closer to its _maximum popularity potential moment_. Startup companies know that a success doesn't come only from a clever designed product, there is a significant factor of luck involved in releasing the right product at the right time. Large, and very costly, marketing campaigns can only extend, or synthetically create, this potential moment by so much.

This latter point around the extents and performance of big data analysis and the differentiation it creates between business analysis versus richer, more spontaneous, product development and innovation creates for FINN an important and consequential factor to our forage into big data. Here at FINN a product owner recently said: "FINN is already developing its product largely built around the customer feedback and therefore achieving [continuous innovation](http://en.wikipedia.org/wiki/Lean_Startup)?". Of course what he meant to say was "from numbers we choose to collect, we generate the statistics and reports we think are interesting, and from these statistics we freely interpret them to support the hypothesis we need for further product development..." We couldn't be further from continuous innovation if it hitched a lift to the other side of Distortionville[¹](http://www.mariettatimes.com/page/content.detail/id/533694/Assessment-couldn-t-be-further-from-the-truth.html?nav=5007).


> “It is a capital mistake to theorize before one has data. Insensibly one begins to twist facts to suit theories, instead of theories to suit facts” – Arthur Conan Doyle



FINN isn't alone here, I'd say most established companies while trying to brand themselves as innovative are still struggling to break out of their existing models of product development based upon internal business analysis. No one said continuous innovation was easy, and there's a lot of opinions out on this, but along with shorter deployment cycles i reckon there's two keywords: truth and transparency. Tell your users the truth and watch how they respond. For example give them the statistics that show them their ads stop getting visits after a week, and then observe how they behave, to which solution do they flock to regain traffic to their ads. Don't try and solve all their problems for them, rather try to enable them. You'll probably make more money out of them, and by telling them the truth you've removed a vulnerability, or to what some fancy refers to as "a business secret".

There's also a potential problem with organisational silos. Large companies having invested properly in business intelligence and data warehousing will have assigned these roles of data collection, aggregation, and analysis, to a separate team or group of experts typically trained database administrators, statisticians, and traffic analysts. They are rarely the programmers, the programmers are on the front lines building the product. Such a split can parallel the sql vs [nosql](http://www.slideshare.net/emileifrem/nosql-overviewneo4jintrotriforkgeeknighttoslideshare) camps. This split against the programmers whom you rely on to make continuous innovation a reality can run the risk of stifling any adoption of big data. With the tools enabling big data the programmers can generate reports and numbers previously only capable from the business intelligence and data warehousing departments, and can serve them to your users at web response times – integrating such insights and intelligence into your product. Such new capabilities [doesn't supersede](http://radar.oreilly.com/2011/01/data-warehouse-big-data.html) these traditional departments, rather it needs everyone accepting the new: working together to face new challenges with old wisdom. The programmers working on big data, even if tools and data become shared between these two organisational silos, cannot replace the needs of business intelligence any more than business intelligence can undertake big data's potential. As data and data sources continue to increase year after year the job of asking the right questions, even knowing how to formulate the questions correctly, needs all hands on deck. Expecting your programmers to do it all might well swamped them into oblivion, but it isn't just the enormity of new challenges involved, it's that these challenges have an integral nature to them that programmers aren't typically [trained to tackle](http://management.fortune.cnn.com/2012/03/19/big-data-wont-solve-your-companys-problems/). Big Data can be used as an opportunity not only to introduce exciting new tools, paradigms, and potential into the company but as a way to help remove existing organisational silos.

The need for big data at FINN came from a combination of (α) and (γ). ![](/wp-content/uploads/2012/08/salamander.gif)  The statistics we show users for their ads had traditional been accumulated and stored in a sybase table. These statistics included everything from page views, "tip a friend" emails sent, clicks on promoted advertisement placements, ads marked as favourite, and whatever else you can imagine.

(α) FINN is a busy site, the busiest in Norway, and we display ~50 million ad pages each day. Like a lot of web applications we had a modern scalable presentation and logic [tier](http://en.wikipedia.org/wiki/Multitier_architecture) based upon ten tomcat servers but just one not-so-scalable monster database sitting in the data tier. The sybase procedure responsible for writing to the statistics table ended up being our biggest thorn. It used 50% of the database's write execution time, 20% of total procedure execution time, and the overall load it created on the database accounted for 30% of ad page performance. It was a problem we had lived with long enough that Operations knew quickly to turn off the procedure if the database showed any signs of trouble. Over one period of troublesome months Operations wrote a cronjob to turn off the procedure automatically during peak traffic hours – when ads were receiving the most traffic we had to stop counting altogether, embarrassing to say the least!

(γ) On top of this product owners in FINN had for years been requesting that we provide statistics on a day basis. The existing table had tinkered with this idea for some of the numbers that didn't accumulate so high, eg "tip a friend emails", but for page viewings this was completely out of the question – not even the accumulated totals were working properly.

At the time we were in the process of modularising the FINN web application. The time was right to turn statistics into something modern and modular. We wanted an asynchronous, fault-tolerance, linearly scaling, and durable solution. The new design uses the Command-Query Separation pattern by using two separate modules: one for counting and one for displaying statistics. The counting achieves asynchronousity, scalability, and durability by using Scribe. The backend persistence and statistics module achieves all goals by using Cassandra and Thrift. As an extension of the [push-on-change](http://highscalability.com/blog/2009/10/13/why-are-facebook-digg-and-twitter-so-hard-to-scale.html) model: the counting stores denormalised data and it is later normalised to the views the statistics module requires; we use MapReduce jobs within a Hadoop cluster.

![http://www.flickr.com/photos/ciordia/2892558385/sizes/m/in/photostream/](/wp-content/uploads/2012/08/2892558385_69bb7f4465.jpg) 

The resulting project is kick-ass, let me say. Especially Cassandra, it is a truly amazing modern database: linear scalability, decentralised, elastic, fault-tolerant, durable; with a rich datamodel that provides often [superior](http://maxgrinev.com/2010/07/12/do-you-really-need-sql-to-do-it-all-in-cassandra/) approaches to joins, grouping, and ordering than traditional sql. But we'll spend more time describing the project in technical details in a later article.

A challenge we face now is broader adoption of the project and the technologies involved. Various departments: from developers to tech support; want to read the data, regardless if it is traditional or 'big data', and the habit was always to read it directly from production's Sybase. And it's a habit that's important in fostering a data-driven culture within the company, without having to encourage datamodel centric designs. With our Big Data solution this hasn't been so easy. Without this transparency to the data, developers, tech support, and product owners alike seem to be failing to initiate further involvement - to solve this, since our big data is stored in Cassandra, we'd love to see a read only web-based gui interface based off [caqel](http://caqel.deadcafe.org/).


**_…to be continued…_**

  
  

----


References:
• [DNA sequencing is ultimately a big data problem.](http://www.fastcoexist.com/1679847/an-operating-system-for-dna)
• [Obama's 2012 Big Data initiatives](http://www.whitehouse.gov/sites/default/files/microsites/ostp/big_data_fact_sheet.pdf)
• [Beware The Hype Over Big Data Analytics](http://seekingalpha.com/article/441171-beware-the-hype-over-big-data-analytics)
• [Big Data won't solve your company's problems](http://management.fortune.cnn.com/2012/03/19/big-data-wont-solve-your-companys-problems/)
• [Big Data: Opportunity or Threat for Market Research?](http://www.greenbookblog.org/2012/03/21/big-data-opportunity-or-threat-for-market-research/)
• [O'Reilly MySQL CE 2010: Mark Atwood, "Guide to NoSQL, redux" ](http://www.youtube.com/watch?v=zAbFRiyT3LU)
• [Lean Startup](http://en.wikipedia.org/wiki/Lean_Startup)
• [FINN moving toward continuous innovation](http://tech.finn.no/2011/05/09/putting-a-face-on-quality/)
• [Will data warehousing survive the advent of big data?](http://radar.oreilly.com/2011/01/data-warehouse-big-data.html)
• [Why are Facebook, Digg, and Twitter so hard to scale?](http://highscalability.com/blog/2009/10/13/why-are-facebook-digg-and-twitter-so-hard-to-scale.html)
• [Do You Really Need SQL to Do It All in Cassandra?](http://maxgrinev.com/2010/07/12/do-you-really-need-sql-to-do-it-all-in-cassandra/)

  
  
  

