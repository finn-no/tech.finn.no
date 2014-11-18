---
authors:
- mick
comments: true
date: 2014-10-27 11:50:00+00:00
layout: post
slug: log4j2-bringing-together-the-dependencies
title: Log4j2 - dependencies
wordpress_id: 2150
---
To make it easy for all our codebases, to automatically have all logging abstractions pointing towards log4j2 along with logstash configured, we have bundled together the dependencies via one library.
These dependencies look like:

{% highlight java %}
    <!-- core libraries -->  
    org.apache.logging.log4j:log4j-api:2.0.2  
    org.apache.logging.log4j:log4j-core:2.0.2  
    com.lmax:disruptor:3.2.1  

    <!-- commons-logging -->  
    org.apache.logging.log4j:log4j-jcl:2.0.2  
    org.apache.logging.log4j:log4j-1.2-api:2.0.2  

    <!-- slf4j -->  
    org.slf4j:slf4j-api:1.7.7  

    <!-- JUL routed through slf4j -->  
    org.apache.logging.log4j:log4j-slf4j-impl:2.0.2  

    <!-- override old log4j with an empty jarfile -->  
    <!-- incase it gets re-introduced transitively -->  
    log4j:log4j:2-empty  

    <!-- logstash -->  
    net.logstash.log4j2:log4j2-logstash-jsonevent-layout:3.0.0-finn-2  
{% endhighlight %}