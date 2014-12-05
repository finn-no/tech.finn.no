---
authors: mick
comments: true
date: 2011-04-08 07:09:39+00:00
layout: post
title: 'XSS protection: who''s responsibility?'
redirect_from: /xss-protection-whos-responsibility/
tags:
- API Design
- front-end
- Legacy Systems
- Systems
- XSS
---

In a multi-tier application who can be responsible for XSS protection?
Must security belong to a dedicated team...or can it be a shared responsibility?
Today **[XSS protection](http://en.wikipedia.org/wiki/Cross-site_scripting)** is typically tackled by front end developers.
Let's challenge the status quo.






## New Applications vs Legacy Applications


For protection against _Stored XSS_ many applications have the luxury of ensuring any text input from the user, or from a CMS system, is made clean and safe before being written to database. Given a clean database the only XSS protection required is around request values, for example values from url parameters, cookies, and form data.




![Image by The Planet via Flickr -- http://www.flickr.com/photos/26388913@N05/4879419700](/wp-content/uploads/2011/04/rack.jpg)


But some applications, especially legacy applications, are in a different boat. Databases typically have lots of existing data in many different formats and tables so it's often no longer feasible to focus on protecting data on its way into the system. In this situation it is the front end developers that pay the price for the poor quality of backend data and are are left to protect everything. This often results in a napalm-the-whole-forest style of xss protection where every single variable written out in the front end templates goes through some equivalent of


                <c:out value="${someText}"/>






This makes sense but...   if you don't have control is your only option to be so paranoid?  




## A Messed up World



To illustrate the problem let's create a simple example by defining the following service



      interface AdvertisementService{
        Advertisement getAdvertisement(long id);
      }

      interface Advertisement{
        /** returns plain text title */
        String getTitle();
        /** return description, which may contain html if isHtmlEnabled() returns true */
        String getDescription();
        /** indicates the description is html */
        boolean isDescriptionHtml();
      }



The web application, having already fetched an advertisement in the control tier, somewhere would have a view template looking something like



        <div>
            <h1><c:out value="${advertisement.title}"/></h1>
            <p>
                <c:out value="${advertisement.description}"
                          escapeXml="${!advertisement.descriptionHtml}"/>
            </p>
        </div>






![](/wp-content/uploads/2010/11/information.gif)

Here we add another dimension: simple escaping with c:out won't work if you actually want to write html (and what is the safety and quality of such html data).








When this service is used by different applications each with their own view templates, and maybe also exposed through web services, you end up no doubt protecting it over many times, system developers in the web services, and front end developers in each of the presentation layers... likely there will be confusion over the safety and quality of data coming from this service, and of course everyone will be doing it differently so nothing will be consistent.





  * Is there a better way?


  * Can we achieve a consistent XSS protection in such an environment?


  * How many developers need to know about XSS and about the origin of the data being served?



In the above code if we can guarantee that the service _always_ returns safe data then we can simplify it by removing the isDecriptionHtml() method. The same code and view template would become



      interface AdvertisementService{
        Advertisement getAdvertisement(long id);
      }

      /** All fields xss protected */
      interface Advertisement{
        /** returns plain text title */
        String getTitle();
        /** return description, which may contain safe html */
        String getDescription();
      }






        <div>
            <h1>${advertisement.title}</h1>
            <p>${advertisement.description}</p>
        </div>




By introducing one constraint: that **all data is xss protected in the services tier**; we have provided a simpler and more solid service API, and allowed all applications to have simpler, more concise, more readable, view templates.



## Solutions



_Having a clean database_ all non-html data can be escaped as it comes in. Take advantage of Apache's StringEscapeUtils.escapeHtml(..) from [commons-lang](http://commons.apache.org/lang/) library. For incoming html one can take advantage of a rich html manipulation tool, eg like JSoap, to clean, normalise, and standardise it.

_With legacy or foreign data_, especially those applications with exposed service architecture and/or multiple front ends, a different approach is best: ensure nothing unsafe ever comes out of the services tier. For the html data the services will often be filtering many snippets of html over and over again, so this needs to be fast and a heavy html manipulation library like JSoap isn't appropriate any more.
A suitable library is [xss-html-filter](http://finn-no.github.com/xss-html-filter), a port from libfilter. It is fast and has an easy API

    String safeHtml = new HTMLInputFilter().filter( "some html" );



If we do this it means
![](/wp-content/uploads/2010/11/lightbulb_on.gif) xss protection is not duplicated, but rather made clear who is responsible for what,
![](/wp-content/uploads/2010/11/lightbulb_on.gif) c:out becomes just junk in verbosity and performance* ,
![](/wp-content/uploads/2010/11/lightbulb_on.gif) service APIs become simpler,
![](/wp-content/uploads/2010/11/lightbulb_on.gif) view templates look the same for both new and legacy applications,
![](/wp-content/uploads/2010/11/lightbulb_on.gif) system developers become responsible for protection of database data and this creates a natural incentive for them to clean up existing data and ensure new data comes in safe,

 * No matter what an inescapable fact is all front ends developers must have a concrete understanding that any value fresh from the request is prone to _Reflected XSS_ and there's nobody but them that can be responsible for protecting these values.

**XSS protection has become a basic knowledge requirement for all developers**.
_Like much to do about security ... it's always only as strong as its weakest link_.

At FINN.no because we take security seriously, and because we know we are only human and we need some room for the occasional mistake, for each release we run security audits. These include using [WatchCom](http://www.watchcom.no/) reports, tools like [Acunetix](http://www.acunetix.com/), and custom tests using [Cucumber](http://cukes.info/).


