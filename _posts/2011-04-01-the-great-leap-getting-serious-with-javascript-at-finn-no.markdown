---
authors: espen
comments: true
date: 2011-04-01 11:45:39+00:00
layout: post
title: The Great Leap - getting serious with JavaScript at FINN.no
redirect_from: /the-great-leap-getting-serious-with-javascript-at-finn-no/
tags:
- front-end
- javascript
- jstd
- tdd
- testing
---

![Krafttak for JavaScript](/images/2011-04-01-the-great-leap-getting-serious-with-javascript-at-finn-no/krafttak-cjno.png)

FINN.no are blessed to be based in the same country as one of the brightest young stars when it comes to testing in JavaScript and applying Test Driven Development: [Christian Johansen](https://github.com/cjohansen) (the poster above was used to promote the event).
He works as a developer at Gitorious during the day time, but he also cranks out frameworks like [SinonJS](http://sinonjs.org) and he has even published a [book on how to do Test Driven Development in JavaScript](http://www.amazon.com/dp/0321683919/).

## Driving your design with tests

We first had an amazing live coding session which he had previously done at FrontTrends. The task was to create a type-ahead widget which sent requests to a server if the delay was more than 50 millies. Driving the design of an HTML widget with tests is just as awesome as doing the same thing with JUint on the back-end.  You get a nice set of really simple objects which provide you with a set of tools to create the functionality you want. This is very different from the one-object-with-everything kind of code you see a lot of when you do not focus upon design before you code. You can accomplish good clean code without

## Refactor some legacy code with tests

We have just recently ported parts of our platform related to [advanced search](http://www.finn.no/finn/car/used/advanced) to a new Java framework, but we did not port much of the JavaScript code. Only thing we have done is to prevent it from flooding the global scope and extract the JS code into a separate file. Christian gave us the challenge of trying to test this code. This was by no means an easy task.
The code was not written with testing in mind. It has some weird coding errors which for some reason does not produce errors in the user interface. Stuff like a function which one would expect to return a boolean, but when we tried to _assertTrue_ on the result of a validation it failed and we couldn't see why.

    function validateForm() {
        var valid = true;
        $("input.number").each(function(){
            valid &= validateInput($(this));
        });
        return valid;
        }
    ...
        if (validateForm()) { ....}


That was until we noticed the  bitwise and operator used to indicate valid, which returns 0 or 1. Not true or false which you might expect since the valid variable is initialized with true.

## Two wrongs make a right?

To proceed with testing this advanced search field we had to assert whether the red border was on or not. This was the only thing visible from outside of the object. We hit a bit of a snag when it came to the css-function in jQuery, which it turns out does not return a color if you pass in just _border_. However if you pass in _border-color-left_ you get the result _rgb(204, 0, 0)_.


    function validateInput(element) {
        if (val.length > 0 && val.search(/^\d+$/)) {
            element.css({border:"1px solid #CC0000"});
            element.focus(function() {
                element.css({border:"1px solid #BBBBCC"});
            });
            return false;
        }
        return true;
    }




These are just a few of the thing we needed to figure out in order to add a test harness to some of our legacy code. It is very very hard work and you really need some [cojones](http://en.wikipedia.org/wiki/Cojones) to start doing it.  It was truly inspiring to have Christian over and we just have no excuse anymore for not writing tests for our JavaScript code. This is a huge leap forward in terms of getting serious with our JavaScript code.
