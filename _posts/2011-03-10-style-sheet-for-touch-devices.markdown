---
authors: gregers
comments: true
date: 2011-03-10 13:05:53+00:00
layout: post
title: Style sheet for touch devices
redirect_from: /style-sheet-for-touch-devices/
tags:
- CSS
- interface development
- ipad
- modernizr
---

It's easy to include a style sheet for small-screen mobile devices, but what if you specifically want to target touch devices. Then you also need to include devices with larger screens. The iPad has a resolution of 1024x768, but many desktop users also have this resolution, so you can't just increase the max-device-width in your CSS media query. I've tried to [find a better solution to the problem](http://stackoverflow.com/questions/2607248/optimize-website-for-touch-devices), but none seems to exist yet.

![Touch](/images/2011-03-10-style-sheet-for-touch-devices/touch2.jpg)
So the best solution I've found is to use JavaScript to detect if the browser has touch events. It's a bit dangerous because Firefox and Chrome have support for touch, so you can't be sure they don't expose the touch events to devices without a touch screen. Fortunately this has [already happened](http://code.google.com/p/chromium/issues/detail?id=36415) in Google Chrome. The team decided to disable the touch events so the detection developers already used would continue to work.

[Paul Irish](http://twitter.com/#!/paul_irish) has researched a bit for [Modernizr](http://modernizr.com) to find out [what detection methods work best](http://modernizr.github.com/Modernizr/touch.html). You can of course use Modernizr, but if all you need is this test it's not necessary to include the whole library. All you really need is this:

{% highlight javascript %}
'ontouchstart' in window
{% endhighlight %}



At first I did the test in the initialization code on document.ready, but then the style sheet will load too late. I definitely don't like document.write in external scripts because then you can't load them asynchronously in an easy manner. However, now it's actually useful, since we want the style sheet to be written out and loaded before the page is visible.

{% highlight javascript %}
<script type="text/javascript">
    if("ontouchstart" in window) {
        document.write('<link rel="stylesheet" type="text/css" media="only screen" href="/css/touch.css">');
    }
</script>
{% endhighlight %}



Anyone have a better way?
