---
authors: mick
comments: true
date: 2012-07-25 13:48:52+00:00
layout: post
title: the ultimate view — Tiles-3
redirect_from: /the-ultimate-view-tiles-3/
---
A story of getting the View layer up and running quickly in Spring...

<div style="font-size:90%;">
<blockquote>Since the <a href="http://tech.finn.no/2010/11/04/the-ultimate-view/">original article</a>, parts of the code has been accepted upstream, now available as part of the Tiles-3 release, so the article has been updated — it's all even simpler!</blockquote>

</div>

<div style="font-size:80%;">

<div style="background-color: WhiteSmoke;">Based upon the <strong>Composite pattern</strong> and <strong>Convention over Configuration</strong> we'll pump steroids into
<br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;a web application's view layer
<br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;with four simple steps using <strong>Spring</strong> and <strong>Tiles-3</strong>
<br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;to make organising large complex websites elegant with minimal of xml editing.</div>
</div>
<br/>

<span class="image-wrap" style="float: right"><img src="/images/2012-07-25-the-ultimate-view-tiles-3/1351-222x300.jpg" alt="tiles" /></span>
<br/>

Contents of article…
 • Background
 • Step 0: Spring to Tiles Integration
 • Step 1: Wildcards
 • Step 2: The fallback pattern
 • Step 3: Definition includes
 • When the Composite pattern is superior
 • Conclusion

<br/>

## Background


<div style="font-size:90%;">
At FINN.no we were redesigning our control and view layers. The architectural team had decided on Spring-Web as a framework for the control layer due to its flexibility and for providing us a simpler migration path. For the front end we were a little unclear. In a department of ~60 developers we knew that the popular vote would lead us towards SiteMesh. And we knew why – for practical purposes sitemesh gives the front end developer more flexibility and definitely less xml editing.
But sitemesh has some serious shortcomings...</div>

<span class="image-wrap" style="float: right"><img style="border: 0px solid black" src="/images/2012-07-25-the-ultimate-view-tiles-3/images.jpg" alt="images" width="145" /></span>

<div style="font-size:90%;"><strong>SiteMesh shortcomings:</strong><ul><li>from a design perspective the Decorator pattern can undermine the seperation MVC intends,</li>
	    <li>requires all possible html for a request in buffer requiring large amounts of memory</li>
	    <li>unable to flush the response before the response is complete,</li>
	    <li>requires more overall processing due to all the potentially included fragments,</li>
	    <li>does not guaranteed thread safety, and</li>
	    <li>does not provide any structure or organisation amongst jsps, making refactorings and other tricks awkward.</li>
    </ul></div>


<div style="font-size:90%;">
One of the alternatives we looked at was <a class="external-link" rel="nofollow" href="http://tiles.apache.org/">Apache Tiles.</a> It follows the Composite Pattern, but within that allows one to take advantage of the Decorator pattern using a <a class="external-link" rel="nofollow" href="http://tiles.apache.org/framework/tutorial/advanced/preparer.html">ViewPreparer</a>. This meant it provided by default what we considered a superior design but if necessary could also do what SiteMesh was good at. It already had integration with Spring, and the way it worked it meant that once the Spring-Web controller code was executed, the Spring's view resolver would pass the model onto Tiles letting it do the rest. This gave us a clear MVC separation and an encapsulation ensuring single thread safety within the view domain.
</div>


<div style="font-size:90%;">
<blockquote>“Tiles has been indeed the most undervalued project in past decade. It was the most useful part of struts, but when the focus shifted away from struts, tiles was forgotten. Since then struts as been outpaced by spring and JSF, however tiles is still the easiest and most elegant way to organize a complex web site, and it works not only with struts, but with every current MVC technology.” – Nicolas Le Bas</blockquote>
</div>


<div style="font-size:90%;">
Yet the best Tiles was going to give wasn't realised until we started experimenting a little more...</div>


<br/>

## Step 0: Spring to Tiles Integration

<div style="font-size:90%;">
The first step is integrating Tiles and Spring together. For Tiles-3 it boils down to registering a ViewResolver and a TilesConfigurer in your spring-web configuration.
</div>

{% highlight xml %}
<bean id="viewResolver" class="org.springframework.web.servlet.view.tiles3.TilesViewResolver"/>  
<bean id="tilesConfigurer" class="org.springframework.web.servlet.view.tiles3.TilesConfigurer">  
<property name="tilesInitializer">  
<bean class="no.finntech.control.servlet.tiles.FinnTilesInitialiser"/>  
</property>  
</bean>  
{% endhighlight %}

<div style="font-size:90%;">
You need Spring-3.2 to get this to work, specifically "spring-webmvc". If you're using an older version of Spring then you can <a href="http://wever.org/spring-webmvc-tiles3-3.2.0.RC2-finn-1.jar">download</a> the required classes separately and add them to your classpath. (There's a <a href="http://wever.org/spring-webmvc-tiles3-3.2.0.RC2-finn-1.pom">pom</a> file also available.)

The TilesConfigurer hooks in the "tilesInitializer" class. This is the way in Tiles-3 of providing the <a class="external-link" rel="nofollow" href="https://tiles.apache.org/framework/config-reference.html">configuration</a>. If you don't specify one it'll use by default <a href="http://tiles.apache.org/framework/apidocs/org/apache/tiles/extras/complete/CompleteAutoloadTilesInitializer.html">CompleteAutoloadTilesInitializer</a> which gives you a fully featured Tiles setup (you'll need tiles-extras.jar in the classpath). The setup here uses the FinnTilesInitialiser and FinnTilesContainerFactory classes to make possible further configurations.
For now FinnTilesContainerFactory is an empty class extending BasicTilesContainerFactory, and FinnTilesInitialiser looks like
</div>

{% highlight java %}
public class FinnTilesInitialiser extends AbstractTilesInitializer {

    public FinnTilesInitialiser() {}

    @Override
    protected AbstractTilesContainerFactory createContainerFactory(TilesApplicationContext context) {
        return new FinnTilesContainerFactory();
    }
}
{% endhighlight %}

<br/>

## Step 1: Wildcards
<img style="margin: 10px" src="/images/2012-07-25-the-ultimate-view-tiles-3/Screenshot1.png" border="0" alt="create these files and folders" align="right" />

<br/>

<div style="font-size:90%;">
We are going to create a basic website with three simple pages: cat, cow, and dog. The jsps making up these pages are shown to the right.

Composing these jsps together into their corresponding pages we encounter Tiles' most obvious downfall: every single jsp included must be declared in a definition in the tiles.xml file. Anyone that ever tried Tiles-1 knows this bad smell.

Since Tiles-2 this can be avoided by using <a class="external-link" rel="nofollow" href="http://tiles.apache.org/framework/tutorial/advanced/wildcard.html">wildcards</a>¹, and when hearing people talk about tiles it is often clear this <em>dynamic composite</em> paradigm hasn't yet superseded the old tiles prejudices.
<br/>

Let's declare a definition and template as below and create some files and folders as shown to the right:
</div>

<br/>

{% highlight xml %}
//--tiles.xml
//--  anything that doesn't start with a slash is considered a definition here.
<definition name="REGEXP:([^/].*)" template="/WEB-INF/tiles/template.jsp">
  <put-attribute name="meta" value="/WEB-INF/tiles/{1}/meta.jsp"/>
  <put-attribute name="header" value="/WEB-INF/tiles/{1}/header.jsp"/>
  <put-attribute name="body" value="/WEB-INF/tiles/{1}/body.jsp"/>
  <put-attribute name="footer" value="/WEB-INF/tiles/{1}/footer.jsp"/>
</definition>
//--template.jsp
{% endhighlight %}{% highlight xml %}
<!DOCTYPE html>
<%@ prefix="tiles" taglib uri="http://tiles.apache.org/tags-tiles" %>
<html>
    <head>
        <tiles:insertAttribute name="meta"/>
    </head>
    <body>
        <div id="header"><tiles:insertAttribute name="header"/></div>
        <div id="body"><tiles:insertAttribute name="body"/></div>
        <div id="footer"><tiles:insertAttribute name="footer"/></div>
    </body>
</html>
{% endhighlight %}

<div style="font-size:90%;">
Write a Spring controller to return definitions that match the names of the folders, that is the three definitions "cat", "dog", and "cow".
</div>

{% highlight java %}
@Controller
public class MyWebsite{

    @RequestMapping("/cat")
    public ModelAndView viewCat(){
        Map modelMap = new HashMap();
        //...
        return new ModelAndView("cat", modelMap);
    }

    @RequestMapping("/dog")
    public ModelAndView viewDog(){
        Map modelMap = new HashMap();
        //...
        return new ModelAndView("dog", modelMap);
    }

    @RequestMapping("/cow")
    public ModelAndView viewCow(){
        Map modelMap = new HashMap();
        //...
        return new ModelAndView("cow", modelMap);
    }
}
{% endhighlight %}


<div style="font-size:90%;">
<strong>Now with this setup we can keep adding new definitions and jsps without having to edit xml.</strong>

<br/>
The Apache Tiles tutorial on wildcards explains that to extend the use of wildcards to also allow rich regular expressions one should use the <code>CompleteAutoloadTilesContainerFactory</code>. In this article we are building up our own FinnTilesInitialiser and FinnTilesContainerFactory configuration classes. To enable both wilcards and regular expressions, distinguished by the use of prefixes, we need to override the following method in FinnTilesContainerFactory…
</div>

{% highlight java %}
    @Override
    protected <T> PatternDefinitionResolver<T> createPatternDefinitionResolver(Class<T> cls) {
        PrefixedPatternDefinitionResolver<T> r = new PrefixedPatternDefinitionResolver<T>();
        r.registerDefinitionPatternMatcherFactory(
                       "WILDCARD", new WildcardDefinitionPatternMatcherFactory());
        r.registerDefinitionPatternMatcherFactory(
                       "REGEXP", new RegexpDefinitionPatternMatcherFactory());
        return r;
    }
{% endhighlight %}

<div style="font-size:80%;"><img class="emoticon" src="/images/2012-07-25-the-ultimate-view-tiles-3/information.gif" alt="information" width="16" height="16" align="absmiddle" /> <strong>Troubleshooting:</strong> remember to include the <a href="http://tiles.apache.org/framework/tiles-extras/project-summary.html"><i>tiles-extras</i></a> dependency! Without it you'll get <code style="dont-size: 70%;">...CompatibilityDigesterDefinitionsReader cannot be cast to ...Definition</code><br/><br/><br/></div>

<br/>

## Step 2: The fallback pattern
<img style="margin: 10px" src="/images/2012-07-25-the-ultimate-view-tiles-3/Screenshot.png" border="0" alt="create these files and folders" align="right" />

<br/>

<div style="font-size:90%;">
Not needing anymore to edit tiles.xml is nice, but as the number of definitions grows the number of duplicated jsps with also grow. For example maybe footers and headers are identical in nearly every definition.

Here we'll use the <a class="external-link" rel="nofollow" href="http://tiles.apache.org/framework/apidocs/org/apache/tiles/extras/renderer/OptionsRenderer.html">OptionsRenderer</a> to provide a desired <em>fallback pattern</em>.

In the xml a fallback is notated with the syntax <code>${options[myopts]}</code> where "myopts" is a list-attribute that references the options in preferential order.

Introduce the "common" folder, as shown to the right, and the example turns into
</div>

<br/><br/><br/>

{% highlight xml %}
//--tiles.xml
<definition name="REGEXP:([^/].*" template="/WEB-INF/tiles/template.jsp">
    <put-attribute name="meta" value="/WEB-INF/tiles/${options[myopts]}/meta.jsp"/>
    <put-attribute name="header" value="/WEB-INF/tiles/${options[myopts]}/header.jsp"/>
    <put-attribute name="body" value="/WEB-INF/tiles/${options[myopts]}/body.jsp"/>
    <put-attribute name="footer" value="/WEB-INF/tiles/${options[myopts]}/footer.jsp"/>


    <put-list-attribute name="myopts" cascade="true">
        <add-attribute value="{1}"/>
        <add-attribute value="common"/>
    </put-list-attribute>
</definition>
{% endhighlight %}

<div style="font-size:90%;">
To configure this <code>OptionsRenderer</code> to work in your <code>TilesContainerFactory</code> you'll also need to do the following
</div>

{% highlight java %}
public class FinnTilesContainerFactory extends BasicTilesContainerFactory {
    ...
    @Override
    protected Renderer createTemplateAttributeRenderer(BasicRendererFactory rendererFactory, ApplicationContext applicationContext, TilesContainer container, AttributeEvaluatorFactory attributeEvaluatorFactory) {

        Renderer original = super.createTemplateAttributeRenderer(rendererFactory, applicationContext, container, attributeEvaluatorFactory);
        OptionsRenderer optionsRenderer = new OptionsRenderer(applicationContext, original);
        return optionsRenderer;
    }
    ....
}
{% endhighlight %}

<div style="font-size:90%;">
As the system developer writes new spring controllers to create completely new definitions the front end developer, with this new <em>Convention over Configuration</em> setup, only needs to create the new folder and jsps for fragments they wish to customise. Again there's no further xml editing.
<br/>

<strong>No more xml editing and no duplicate JSPs.</strong><br/>
</div>

<blockquote style="font-size:80%;"><img class="emoticon" src="/images/2012-07-25-the-ultimate-view-tiles-3/information.gif" alt="information" border="0" width="16" height="16" align="absmiddle" /> For a large company this can help enforce UI standards by having control over the common folder — keeping an eye on UI overrides and customisations never become too outlandish with the standard look of the website. The front end developer can also be referencing this common folder for UI standards.</blockquote>

<br/>

## Step 3: Definition includes

<div style="font-size:90%;">
Realistically these fragments: meta, header, body, and footer; won't be enough. You can quickly be using scores of fragments for columns within the body, banners and advertisements, analytics, seo meta data, css and javascript meta includes, etc, etc.

With these different pages cat, dog, and cow it'll also be pretty certain that they'll have different "actions" applicable upon them. For example viewing, editing, and searching pages. Once again there'll be a lot of duplicate jsps.

By being able to dynamically inject one tiles definitions into another, referred to here as "definition injection", this can all elegantly handed. First of all change the existing definition names into the form "action.category" making the controllers look like
</div>

{% highlight java %}
    @Controller
    @RequestMapping("/cat")
    public class CatController{
        @RequestMapping("/cat/view")
        public ModelAndView viewCat(){ //...
            return new ModelAndView("view.cat", modelMap);
        }
        @RequestMapping("/cat/edit")
        public ModelAndView editCat(){ //...
            return new ModelAndView("edit.cat", modelMap);
        }
        @RequestMapping("/cat/search")
        public ModelAndView searchCat(){ //...
            return new ModelAndView("search.cat", modelMap);
        }
    }
    @Controller
    @RequestMapping("/dog")
    public class DogController{
        @RequestMapping("/dog/view")
        public ModelAndView viewDog(){/ /...
            return new ModelAndView("view.dog", modelMap);
        }
        @RequestMapping("/dog/edit")
        public ModelAndView editDog(){ //...
            return new ModelAndView("edit.dog", modelMap);
        }
        @RequestMapping("/dog/search")
        public ModelAndView searchDog(){ //...
            return new ModelAndView("search.dog", modelMap);
        }
    }
    @Controller
    @RequestMapping("/cow")
    public class CowController{
        @RequestMapping("/cow/view")
        public ModelAndView viewCow(){ //...
            return new ModelAndView("view.cow", modelMap);
        }
        @RequestMapping("/cow/edit")
        public ModelAndView editCow(){ //...
            return new ModelAndView("edit.cow", modelMap);
        }
        @RequestMapping("/cow/search")
        public ModelAndView searchCow(){ //...
            return new ModelAndView("search.cow", modelMap);
        }
    }
{% endhighlight %}

<div style="font-size:90%;">
then change the tiles.xml to introduce the definition includes
</div>

{% highlight xml %}
//-- tiles.xml
<definition name="REGEXP:([^/.][^.]*)\.([^.]+)" template="/WEB-INF/tiles/template.jsp">
    <put-attribute name="meta" value="/WEB-INF/tiles/${options[myopts]}/meta.jsp"/>
    <put-attribute name="header" value="/WEB-INF/tiles/${options[myopts]}/header.jsp"/>
    <put-attribute name="body" value="/WEB-INF/tiles/${options[myopts]}/body.jsp"/>
    <put-attribute name="footer" value="/WEB-INF/tiles/${options[myopts]}/footer.jsp"/>

    <!-- definition injection performed by DefinitionInjectingContainerFactory.instantiateDefinitionFactory(..) -->
    <put-list-attribute name="definition-injection">
        <add-attribute value=".category.{2}" type="string"/>
        <add-attribute value=".action.{1}.{2}" type="string"/>
    </put-list-attribute>

    <put-list-attribute name="myopts" cascade="true">
        <add-attribute value="{2}"/>
        <add-attribute value="{1}"/>
        <add-attribute value="common"/>
    </put-list-attribute>
    <put-list-attribute name="myopts-view" cascade="true">
        <add-attribute value="{2}"/>
        <add-attribute value="{1}"/>
        <add-attribute value="view"/>
    </put-list-attribute>
    <put-list-attribute name="myopts-edit" cascade="true">
        <add-attribute value="{2}"/>
        <add-attribute value="{1}"/>
        <add-attribute value="edit"/>
    </put-list-attribute>
    <put-list-attribute name="myopts-dog" cascade="true">
        <add-attribute value="{2}"/>
        <add-attribute value="{1}"/>
        <add-attribute value="dog"/>
    </put-list-attribute>
    <put-list-attribute name="myopts-cat" cascade="true">
        <add-attribute value="{2}"/>
        <add-attribute value="{1}"/>
        <add-attribute value="cat"/>
    </put-list-attribute>
    <put-list-attribute name="myopts-cow" cascade="true">
        <add-attribute value="{2}"/>
        <add-attribute value="{1}"/>
        <add-attribute value="cow"/>
    </put-list-attribute>
</definition>

//-- tiles-core.xml
<definition name="REGEXP:\.action\.view\.([^.]+)">
    <!-- override attributes -->
    <put-attribute name="body" value="/WEB-INF/tiles/${options[myopts-view]}/view_body.jsp"/>
    <!-- search attributes -->
    <put-attribute name="view.content" value="/WEB-INF/tiles/${options[myopts-view]}/view_content.jsp"/>
    <put-attribute name="view.statistics" value="/WEB-INF/tiles/${options[myopts-view]}/view_statistics.jsp"/>
</definition>
<definition name="REGEXP:\.action\.edit\.([^.]+)">
    <!-- override attributes -->
    <put-attribute name="body" value="/WEB-INF/tiles/${options[myopts-edit]}/edit_body.jsp"/>
    <!-- edit attributes -->
    <put-attribute name="edit.form" value="/WEB-INF/tiles/${options[myopts-edit]}/edit_form.jsp"/>
    <put-attribute name="edit.status" value="/WEB-INF/tiles/${options[myopts-edit]}/edit_status.jsp"/>
</definition>
<definition name="REGEXP:\.action\.search\.([^.]+)">
    <!-- override attributes -->
    <put-attribute name="body" value="/WEB-INF/tiles/${options[myopts-search]}/search_body.jsp"/>
    <!-- search attributes -->
    <put-attribute name="search.form" value="/WEB-INF/tiles/${options[myopts-search]}/search_form.jsp"/>
    <put-attribute name="search.results" value="/WEB-INF/tiles/${options[myopts-search]}/search_results.jsp"/>
</definition>
//-- tiles-cat.xml
<definition name="REGEXP:\.category\.cat">
    <!-- cat attributes -->
    <put-attribute name="cat.extra_information" value="/WEB-INF/tiles/${options[myopts-cat]}/cat_extra_information.jsp"/>
    <put-attribute name="cat.feline_attributes" value="/WEB-INF/tiles/${options[myopts-cat]}/cat_feline_attributes.jsp"/>
</definition>
//-- tiles-dog.xml
<definition name="REGEXP:\.category\.dog">
    <!-- cat attributes -->
    <put-attribute name="dog.extra_information" value="/WEB-INF/tiles/${options[myopts-dog]}/dog_extra_information.jsp"/>
    <put-attribute name="dog.k9_attributes" value="/WEB-INF/tiles/${options[myopts-dog]}/dog_k9_attributes.jsp"/>
</definition>
//-- tiles-cow.xml
<definition name="REGEXP:\.category\.cow">
    <!-- cat attributes -->
    <put-attribute name="cow.extra_information" value="/WEB-INF/tiles/${options[myopts-cow]}/cow_extra_information.jsp"/>
    <put-attribute name="cow.farm" value="/WEB-INF/tiles/${options[myopts-cow]}/cow_farm.jsp"/>
</definition>
{% endhighlight %}

<div style="font-size:90%;">
There's a lot of configuration here now, but the idea isn't to avoid configuration altogether but to avoid having to edit the configuration everytime a new jsp or page is created. That is we are putting effort here into creating our <em>convention</em> so that further configuration isn't required.

Now there's a clear separation between each category definition and each action definition. For example such separation helps various development roles work in parallel: front end developers can concentrate on categorical designs while system developers often work initially with actions jsps to get them functionally working before passing them off to the front end developers. Such <em>separation of concerns</em> will show benefits in more ways that just this example.

The following DefinitionsFactory makes this all come together
</div>

{% highlight java %}
public class FinnUnresolvingLocaleDefinitionsFactoryImpl extends UnresolvingLocaleDefinitionsFactory {

    private static final String DEF_INJECTION = "definition-injection";

    public FinnUnresolvingLocaleDefinitionsFactoryImpl() {}

    // this method may return null
    @Override
    public Definition getDefinition(String name, TilesRequestContext tilesContext) {
        Definition def = null;
        // WEB-INF is a pretty clear indicator it is not a definition
        if(!name.startsWith("/WEB-INF/")){
            def = super.getDefinition(name, tilesContext);
            if(null != def){
                def = new Definition(def); // use a safe copy
                Attribute defList = def.getLocalAttribute(DEF_INJECTION); // explicit injected definitions
                if(null != defList && defList instanceof ListAttribute){
                    for(Attribute inject : (List) defList.getValue()){
                        injectDefinition((String) inject.getValue(), def, tilesContext);
                    }
                }
            }
        }
        return def;
    }
    private void injectDefinition(String fromName, Definition to, TilesRequestContext cxt) {
        assert null != fromName : "Definition not found " + fromName;
        Definition from = getDefinition(fromName, cxt);
        if (null != from) {
            if (null != from.getLocalAttributeNames()) {
                for (String attrName : from.getLocalAttributeNames()) {
                    to.putAttribute(attrName, from.getLocalAttribute(attrName), true);
                }
            }
        }
    }
}
{% endhighlight %}

<div style="font-size:90%;">
To plug this DefinitionsFactory in make sure to return it from your TilesContainerFactory
</div>

{% highlight java %}
public class FinnTilesContainerFactory extends BasicTilesContainerFactory {
    ...
    @Override
    protected UnresolvingLocaleDefinitionsFactory instantiateDefinitionsFactory(TilesApplicationContext applicationContext, TilesRequestContextFactory contextFactory, LocaleResolver resolver) {
        return new FinnUnresolvingLocaleDefinitionsFactoryImpl();
    }
    ...
}
{% endhighlight %}

<br/>

## When the Composite pattern is superior

<div style="font-size:90%;">
It's nonsense to think that any one pattern is <em>better</em>. Both the Composite pattern and the Decorator pattern have their strengths and weaknesses, even after we have progressed the Composite on to being highly dynamic and automated. They do, and achieve, quite different things and hence each should be used where applicable...
</div>
<br/>

<div style="font-size:90%;">
<img src="/images/2012-07-25-the-ultimate-view-tiles-3/lightbulb_on.gif" border="0" alt="lightbulb_on" width="16" height="16" align="absmiddle" /> The <a class="external-link" rel="nofollow" href="http://en.wikipedia.org/wiki/Decorator_pattern">Decorator pattern</a> allows front end code and design to be injected as we process. When a decision is known during the request processing the code can immediately build a decorator. The context here only holds all the built decorators. And at the end of the request lifecycle the page is assembled by putting together all these decorators. Decorators can also be built upon each other, or stacked, and this can be useful when the composition of the page is completely loose.
</div>
<br/>

<div style="font-size:90%;">
<img class="emoticon" src="/images/2012-07-25-the-ultimate-view-tiles-3/lightbulb_on.gif" alt="lightbulb_on" border="0" width="16" height="16" align="absmiddle" /> The <a class="external-link" rel="nofollow" href="http://en.wikipedia.org/wiki/Composite_pattern">Composite pattern</a> presumes the page to be a "composite" made up from components, where each component is free to be itself a "composite". The pattern allows more control of the composite's heirarchy: as the delegation is top-down as opposed to the Decorator pattern whom's is bottom-up. The composite pattern works well when the operations you need to perform on each object is limited, that is it isn't a problem that the operations you may perform on any one object is the lowest common demominator of operations that you can perform on every object.
</div>
<br/>

<div style="font-size:90%;">
When you use jsp includes whether you like it or not you have a page heirarchy. If your jsps typically have a template with fragments you are already working within the composite pattern's paradigm: the template being the "composite" and the fragments being the components. Each fragment, each JSP, has a context that holds the model map, or variables, in various scopes, and within this context they are self-sufficient. The only operation required upon them by others is inclusion. Applying the composite pattern here means treating each fragment as a self-sufficient object, the only thing that can happen to it is it will be included.

This shows how we can design to reduce complexity and encourage there to be only one unified context.

It also shows how we can maintain a top-down control of the page's heirarchy, something necessary in a MVC design where the control layer wants to hand off a finished model map, ie one fixed and unified context, that the view layer is free to build itself off. In contrast when we choose the Decorator pattern we can run foul of letting code run in parallel to the MVC pattern.   <br class="atl-forced-newline" /> <br class="atl-forced-newline" />
<img style="border: 0px solid black;width:100%" src="/images/2012-07-25-the-ultimate-view-tiles-3/patterns-flow.gif" alt="Patterns flow" align="center" />
</div>

<br/>

## Conclusion

<div style="font-size:90%;">
Front end developers, the system developers, and the architects need to work together. With the front end today often centered around loose coupling design: javascript's lack of type safety, ajax's separation from the server, and jQuery's liberation of the dom; it is all too easy to rebel against any form of structure or organisation for fear of being tied into the formalities of the strict-typed java world.  

Neither should the fear of giving up control of one's craftsmanship mean keeping logic and control in the View layer, this stuff belongs in the Control layer: and this means the system developers need to work harder to get the front end developers collaborating in the control layer, or the roles of system and front end developers need more overlap within teams. Everything has entropy, the larger the organisation, the larger the codebase, the less you fight the entropy, the quicker you end up with a pile of spaghetti in your lap.  

These four steps show you how Tiles-3 and the Composite pattern can be ramped up on steroids to give front end and systems developers what they want in a way that encourages them to work together.  
</div>

<br/>

<div style="font-size:80%;">
<strong>References</strong>  <br/>
&nbsp;&nbsp;1. <a href="http://tiles.apache.org">Tiles-3</a>  <br/>
&nbsp;&nbsp;2. <a href="http://en.wikipedia.org/wiki/Composite_pattern">Composite pattern</a>  <br/>
&nbsp;&nbsp;3. <a href="http://en.wikipedia.org/wiki/Decorator_pattern">Decorator pattern</a>  <br/>
&nbsp;&nbsp;4. <a href="http://static.springsource.org/spring/docs/3.0.x/spring-framework-reference/html/spring-web.html">Spring Web</a>  <br/>
&nbsp;&nbsp;5. Integrating <a href="http://static.springsource.org/spring/docs/3.0.x/spring-framework-reference/html/view.html#view-tiles">Tiles and Spring</a>  <br/>
&nbsp;&nbsp;6. Initial discussion regarding Tiles definition delegation[Gone]<br/>
&nbsp;&nbsp;7. Spring 3.2 with Tiles-3 :: <a href="http://jira.springframework.org/browse/SPR-8825">Tiles-3 support</a><br/>
</div>