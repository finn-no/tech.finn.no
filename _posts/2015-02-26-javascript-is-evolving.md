---
authors: Tor Arne Kvaløy
layout: post
comments: true
date: 2015-02-26 10:23:01+0100
title: "JavaScript is evolving"
tags:
- javascript
---

As a die-hard java-developer for several years I was quite annoyed by the fact that Java as a language lagged several years behind C#. E.g. Java got lambdas last year, 6 years after [C#](http://en.wikipedia.org/wiki/C_Sharp_%28programming_language%29).

## Polyfill
Now, as a frontend-developer and getting to know JavaScript, I have realized that JavaScript is lagging even further behind. Take for example the everyday case of checking if a string contains another string:

{% highlight javascript %}

"hello world".indexOf("hello") > -1
//=> true

{% endhighlight %}

This is awkward and you know it, however JavaScript is evolving. The next version ECMAScript 6 is in its final phase, and will have, among [several features](https://github.com/zloirock/core-js#ecmascript-6)), the following method:

{% highlight javascript %}

"hello world".includes("hello")
//=> true

{% endhighlight %}


Another new nice feature is for finding the first item in an array. It used to be written like this:
{% highlight javascript %}

for(int i = 0; i < array.length; i++){
  if(array[i] === something){
    return array[i];
  }
}

{% endhighlight %}

And now it can be written like:

{% highlight javascript %}

array.find(function(it) {
      return it === something
      });

{% endhighlight %}

ECMAScript 6 is feature complete, and is started to be supported by modern browser like Chrome, Firefox and Internet Explorer 11. As for older browsers, great polyfills like [core-js](https://github.com/zloirock/core-js) come to our rescue.

## Transpiling
While polyfills extend current objects with new methods, ECMAScript 6 also contains new language syntax. Let's look at the arrow function (also known as fat arrow):

{% highlight javascript %}
["one", "two", "three"].map(v => v + "!")
{% endhighlight %}

Older browser will not understand "=>" (for a long time!), so this code has to be transpiled to ECMAScript 5:

{% highlight javascript %}

["one", "two", "three"].map(function (v) {
  return v + "!";
});

{% endhighlight %}

There are some great transcompilation tools out there, and the one we have used is [Babel](https://babeljs.io/). You can even play around with it [here](https://babeljs.io/repl/).

### Scoped variables
JavaScript will also get scoped variables (let and const):

{% highlight javascript %}

const hello = "Hello"

if(true) {
  const hello = "Inner scoped hello"
}
{% endhighlight %}

This will transpile to:

{% highlight javascript %}
var hello = "Hello";

if (true) {
  var _hello = "Inner scoped hello";
}
{% endhighlight %}

### Class
And finally, JavaScript is getting support for classes (which is sugar around prototype), and the syntax looks like this:

{% highlight javascript %}

class Hello {
  constructor() {
    this.hello = "Hello World"
  }
  getHello() {
    return this.hello;
  }
}
{% endhighlight %}

This will be transpiled to the following, not very readable, but working code:

{% highlight javascript %}
var _prototypeProperties = function (child, staticProps, instanceProps) { if (staticProps) Object.defineProperties(child, staticProps); if (instanceProps) Object.defineProperties(child.prototype, instanceProps); };

var _classCallCheck = function (instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } };

var Hello = (function () {
  function Hello() {
    _classCallCheck(this, Hello);

    this.hello = "Hello World";
  }

  _prototypeProperties(Hello, null, {
    getHello: {
      value: function getHello() {
        return this.hello;
        },
        writable: true,
        configurable: true
      }
      });

      return Hello;
})();

{% endhighlight %}

## Conclusion
ECMAScript 6 is here, and we should start using it. Older browser will be around for a long time, so I think we should get used to transpiling code on the web-platform.

Tor Arne
Senior developer
