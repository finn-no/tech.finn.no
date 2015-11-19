---
layout: post
comments: true
date: 2015-10-16 16:00:00+0200
authors: Tor Arne Kvaløy
title: "Javascript: from callback hell to heaven"
description: "Short introduction to ES7 async await features"
tags:
- es6
- es7
---

This blogpost explains how some nifty features in ES7 will make it easier to write asynchronous code, and how ES6 generators will pave the way for this.

## Async/await

My main annoyance with javascript and Node has been the tedious asynchronous programming model of callbacks, leading to nested callbacks and the so called “callback hell” or “pyramid of doom”. Take for example the following code where we are creating a function that reads a file and parses it to JSON, and see how cumbersome it is to read and follow the code:

{% highlight javascript %}
const fs = require("fs");

function readJSONFile(callback) {
    fs.readFile("file.json", "utf8", (err, data) => {
        if (err) return callback(err);
        
        let json;
        let parseError;
        
        try {
            json = JSON.parse(data);
        } catch(e) {
            parseError = e;
        }
        
        callback(null, json);
    });
};

readJSONFile((err, json) => {
    if(err) {
        //handle error
    }
    console.log(json);
    //continue program
});

{% endhighlight %}

This was slightly improved and simplified with ES6 [promises](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Promise), however, as we can see in the following code, we are still stuck with nested .then-calls:

{% highlight javascript %}
const bluebird = require("bluebird");
const fs = bluebird.promisifyAll(require("fs"));

function readJSONFile() {
    return fs.readFileAsync("file.json", "utf8")
        .then(data => {
            return JSON.parse(data);
        })
};

readJSONFile()
    .then(json => {
        console.log(json);
        //continue program
    })
    .catch(err => {
        //handle error
    })
{% endhighlight %}

Callbacks and nested .then-statements will be a thing of the past with the upcoming version of Javascript (ES7). It will provide us with the keywords [async and await](https://tc39.github.io/ecmascript-asyncawait/), which will enable us to write asynchronous code as we would have written it in an imperative synchronous way:
{% highlight javascript %}
const bluebird = require("bluebird");
const fs = bluebird.promisifyAll(require("fs"));

async function readJSONFile() {
    const data = await fs.readFileAsync("file.json", "utf8");
    return JSON.parse(data);
}

async function() {
    try {
        const json = await readJSONFile();
        console.log(json);
        //continue program
    } catch(err) {
        //handle error
    }
};
{% endhighlight %}

The await keyword can either take a promise or an async function, and pauses until the promise resolves, or the async function returns.  If the promise rejects or the async function throws an error, the error can be caught with a normal catch-statement.  This provides a significant improvement in readability, which likely leads to less bugs and a pleasure to write.


## Generators

Along with promises, ES6 brought [generators](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Iterators_and_Generators), which allows a function to pause at a certain point (yield) and continue when requested with .next(), as illustrated in the following code:

{% highlight javascript %}
function* hello(name) {
    console.log(name);
    const age = yield;
    console.log(age);
}

const helloGen = hello("Tor");
helloGen.next(); // => Tor
helloGen.next(37); // => 37

{% endhighlight %}


Generators are not very useful for normal application logic, but they provide the buildings blocks for some nifty features.

First of all, ES7 async/await can be transpiled ([by Babel](https://babeljs.io)) to generators, so that javascript environments that supports generators (Chrome, Firefox and Node), also supports async/await.

However, because Safari and Internet Explorer don't support generators yet, it is to early to start using generators on the front-end, however, on the backend with Node, it can be safely used, because async/await keywords are transpiled to generators.

A web framework in Node that is based around generators, and uses it for all it is worth, is [Koa](http://koajs.com). They utilize generators to make the code behave similar to async/await:

{% highlight javascript %}
const Koa = require("koa");
const app = Koa();
const router = require("koa-router")();

function* getName() {
    return Promise.resolve("Tor");
}

app.use(router.routes());

router.get("/hello", function* () {
    const name = yield getName();
    this.body = "Hello " + name;
});

app.listen(3000);

{% endhighlight %}

The [yield](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Operators/yield) keyword behaves as await, and generator [function* ](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Statements/function*) behaves as async functions. The main motivation for this is to simplify writing middlewares, and avoid the mentioned callback hell. In the next version of Koa, the yield keyword and generator functions will be replaced with await and async.

## Conclusion
So, ES7 provides a fantastic improvement in simplifying asynchronous code that makes code easier to read and write. These features can already be used on the backend with Node and web frameworks like Koa.

## Credits
Thanks to Sveinung Røsaker for improving the callback example.
