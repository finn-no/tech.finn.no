---
authors: Andersos
comments: false
date: 2010-01-01 13:37:00+00:00
layout: post
title: Design template
tags:
- design
- template
---

# Headings

# The largest heading (an <h1> tag)

## The second largest heading (an <h2> tag)

### The 3rd largest heading (an <h3> tag)

#### The 4th largest heading (an <h4> tag)

##### The 5th largest heading (an <h5> tag)

###### The 6th largest heading (an <h6> tag)


# Paragraph

<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam mattis odio id quam semper, eu scelerisque quam condimentum. Curabitur eu arcu feugiat, vehicula mi ac, mollis lacus. In eu fringilla erat. Vestibulum purus enim, ornare elementum sollicitudin vel, lacinia eu neque. Sed dolor ex, accumsan a egestas et, facilisis et mi. Cras ullamcorper blandit lacus, a posuere arcu tristique ut. Curabitur feugiat placerat aliquam. Fusce sit amet nisl a lectus pellentesque faucibus ut ac massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec augue urna, lacinia sed maximus pulvinar, tempus eu massa. Vestibulum malesuada nunc ut tortor congue finibus ut vel urna. Phasellus quis arcu quis purus feugiat pulvinar condimentum eget felis. Donec tincidunt euismod auctor. Phasellus congue elementum faucibus. In ex velit, elementum et elementum et, accumsan hendrerit felis. Fusce lobortis bibendum leo eget condimentum.</p>

<p>Nunc scelerisque, sem quis rutrum scelerisque, lorem mauris scelerisque elit, ut tempus diam tellus at lectus. Donec nec hendrerit sem. Suspendisse eleifend iaculis lorem, nec ultrices nibh convallis sit amet. Ut risus erat, vestibulum vel placerat in, pulvinar nec ex. Ut nec cursus quam, eget pulvinar odio. Interdum et malesuada fames ac ante ipsum primis in faucibus. Pellentesque pretium mauris at ornare accumsan. Donec vel commodo magna. Mauris sed augue tortor. Etiam eget sem quis libero feugiat malesuada. Integer tempor, ligula vel vestibulum sagittis, risus ipsum tempus ipsum, ut maximus eros erat ut augue. Mauris rutrum aliquet est dictum efficitur. Vivamus vestibulum orci massa, ac dignissim mauris lacinia nec. Aliquam volutpat nisl nec ipsum lobortis pellentesque facilisis convallis nulla.</p>

<p>Vivamus sed interdum nibh, sit amet mollis lorem. Sed ultrices, magna id convallis consectetur, justo nunc commodo justo, at dapibus purus nibh sed mauris. Phasellus vulputate interdum aliquet. Donec a nibh id massa placerat ullamcorper. Vestibulum facilisis leo nulla, vitae porttitor lectus facilisis ac. Nulla facilisi. Aenean sagittis lorem id ante elementum dignissim. Vestibulum eu libero mattis, blandit risus eu, consectetur dolor. Aenean tincidunt nisi lacus, nec sodales est sodales at. Ut efficitur dignissim mauris a ullamcorper. Maecenas eget mi quis diam consequat placerat. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Aenean sed leo at risus feugiat mattis. Integer eget porta leo, at accumsan metus. Aliquam dictum metus sit amet diam tristique pellentesque. Sed facilisis viverra nisi, eget mattis nunc commodo a.</p>


# Blockquotes

In the words of Abraham Lincoln:

> Pardon my french

# Styling text

*This text will be italic*

**This text will be bold**

# Lists

## Unordered lists

* Item
* Item
* Item


- Item
- Item
- Item

## Orderd lists

1. Item 1
2. Item 2
3. Item 3

## Nested lists

1. Item 1
    1. A corollary to the above item.
    2. Yet another point to consider.
2. Item 2
    * A corollary that does not need to be ordered.
    * This is indented four spaces, because it's two spaces further than the item above.
    * You might want to consider making a new list.
3. Item 3

# Links

[Visit Finn!](http://m.finn.no/)

# HR


---



***


___


# Images

![Cat](https://placekitten.com/g/500/500 "Cat image")

![Cat](https://placekitten.com/g/1000/1000 "Cat image")

# Video

Embed:

<iframe width="420" height="315" src="//www.youtube.com/embed/dQw4w9WgXcQ" frameborder="0" allowfullscreen></iframe>

# Tweet

This is a cool tweet:

<blockquote class="twitter-tweet" lang="en"><p>Practical library for doing feature toggles in Java/JVM: <a href="https://t.co/XWX2CGxubE">https://t.co/XWX2CGxubE</a> by <a href="https://twitter.com/FINN_tech">@FINN_tech</a> (via <a href="https://twitter.com/rubendw">@rubendw</a>)</p>&mdash; Thomas F. Nicolaisen (@tfnico) <a href="https://twitter.com/tfnico/status/540059140169928704">December 3, 2014</a></blockquote>


# Tables

| Tables        | Are           | Cool  |
| ------------- |:-------------:| -----:|
| col 3 is      | right-aligned | Yey   |
| col 2 is      | centered      | Yow   |
| zebra stripes | are neat      | Yaw   |

Markdown | Less | Pretty
--- | --- | ---
*Still* | `renders` | **nicely**
1 | 2 | 3

Long list

<table>
<tr><th>Domain</th><th>Number registered</th><th>Percent of total</th></tr>
<tr><td>hotmail.com</td><td>1106084</td><td>28.06%</td></tr>
<tr><td>gmail.com</td><td>599293</td><td>15.20%</td></tr>
<tr><td>online.no</td><td>444897</td><td>11.28%</td></tr>
<tr><td>live.no</td><td>96052</td><td>2.44%</td></tr>
<tr><td>yahoo.no</td><td>94029</td><td>2.39%</td></tr>
<tr><td>yahoo.com</td><td>75772</td><td>1.92%</td></tr>
<tr><td>hotmail.no</td><td>67602</td><td>1.71%</td></tr>
<tr><td>c2i.net</td><td>67080</td><td>1.70%</td></tr>
<tr><td>broadpark.no</td><td>48232</td><td>1.22%</td></tr>
</table>

# Code

{% highlight javascript %}
var s = "JavaScript syntax highlighting";
alert(s);
{% endhighlight %}

{% highlight python %}
s = "Python syntax highlighting"
print s
{% endhighlight %}

Code block with line numbers
{% highlight yaml linenos %}
yaml: true
linenos: true
But let's throw in a <b>tag</b>.
{% endhighlight %}

{% highlight ruby %}
require 'redcarpet'
markdown = Redcarpet.new("Hello World!")
puts markdown.to_html
{% endhighlight %}

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

# Emoji

:+1:

# Mentions

@Andersos