---
authors:
- mick
comments: true
date: 2010-11-04 18:11:57+00:00
layout: post
slug: the-ultimate-view
title: the ultimate view
wordpress_id: 22
tags:
- Composite Pattern
- Decorator Pattern
- front-end
- spring
- Spring Web
- Systems
- Tiles
---




> **This article has been rewritten for Tiles-3.**  
Redirecting to [tech.finn.no/the-ultimate-view-tiles-3/](http://tech.finn.no/the-ultimate-view-tiles-3/) ...




    A story of getting the View layer up and running quickly in Spring, using Tiles with wildcards, fallbacks, and definition includes, to use the Composite pattern and Convention over Configuration providing a minimal ongoing xml changes.


## Summary


    From the architect's perspective you see Apache Tiles as rare exotic Italian marble sheets laid out exquisitely, while the same architect will see SiteMesh as the steel wiring stuck inside the concrete slab. The beauty of the tiles is always admired and a key component in creating an eye-catching surrounding.

    ![](/wp-content/uploads/2011/01/1351-222x300.jpg)








  * [Summary](/2010/11/04/the-ultimate-view/1/#theultimateview-part1-Summary)


  * [Background](/2010/11/04/the-ultimate-view/1/#theultimateview-part1-Background)


  * [Step 0: Spring to Tiles Integration](/2010/11/04/the-ultimate-view/2/)


  * [Step 1: Wildcards](/2010/11/04/the-ultimate-view/3/)


  * [Step 2: The fallback pattern](/2010/11/04/the-ultimate-view/4/)


  * [Step 3: Definition includes](/2010/11/04/the-ultimate-view/5/)


  * [When the Composite pattern is superior](/2010/11/04/the-ultimate-view/6/)


  * [Conclusion](/2010/11/04/the-ultimate-view/6#theultimateview-part1-Conclusion)







## Background


    At FINN.no we were redesigning our control and view layers. We, being the architectural team of six, had already decided on Spring-Web as a framework for the control layer due to its flexibility and a design for us providing better, simpler, migration path. For the front end we were a little unclear. In a department of ~60 developers we knew that the popular vote would lead us towards SiteMesh. And we knew why for practical purposes sitemesh gives the front end developer more flexibility and less xml editing. But we knew sitemesh has some serious shortcomings...

    ![](/wp-content/uploads/2011/01/images.jpe)

    **SiteMesh shortcomings:**




  * from a design perspective the Decorator pattern doesn't combine with MVC as elegantly as the Composite pattern does


  * requires to hold all possible html for a request in buffer requiring large amounts of memory


  * unable to flush the response before the response is complete


  * requires more overall processing due to the processing of all the potentially included fragments


  * does not guaranteed thread safety


  * does not provide any structure or organisation amongst jsps, making refactorings and other tricks awkward


    One of the alternatives we looked at was [Apache Tiles.](http://tiles.apache.org/) It follows the Composite Pattern, but within that allows one to take advantage of the Decorator pattern using a [ViewPreparer](http://tiles.apache.org/tutorial/advanced/preparer.html). This meant it provided by default what we considered a superior design but if necessary could also do what SiteMesh was good at. It already had integration with Spring, and the way it worked it meant that once the Spring-Web controller code was executed, the Spring's view resolver would pass the ball onto Tiles letting it do the rest. This gave us a clear MVC separation and an encapsulation ensuring single thread safety within the view domain.

    Yet the most valuable benefit Tiles was going to offer wasn't realised until we [started experimenting a little more...](/2010/11/04/the-ultimate-view/2/)




## Step 0: Spring to Tiles Integration


    The first step was integrating [Tiles and Spring](http://static.springsource.org/spring/docs/3.0.x/spring-framework-reference/html/view.html#view-tiles) together. There exists tutorials on this already, it boils down to registering both a ViewResolver and a TilesConfigurer in your spring-web configuration.




        <bean id="viewResolver" class="org.springframework.web.servlet.view.tiles2.TilesViewResolver">
    	    <property name="order" value="1"/>
    	    <property name="viewClass" value="org.springframework.web.servlet.view.tiles2.TilesView"/>
        </bean>
        <bean id="tilesConfigurer" class="org.springframework.web.servlet.view.tiles2.TilesConfigurer">
    	    <property name="tilesInitializer">
    		    <bean class="no.finntech.control.servlet.tiles.FinnTilesInitialiser"/>
    	    </property>
        </bean>




    The TilesConfigurer hooks into the "tilesInitializer" class that you must supply yourself. This is the new way in Tiles-2 of providing the [configuration](http://tiles.apache.org/config-reference.html). Our FinnTilesInitialiser looks like




        public class FinnTilesInitialiser extends AbstractTilesInitializer {
            public FinnTilesInitialiser() {}

            @Override
            protected AbstractTilesContainerFactory createContainerFactory(TilesApplicationContext context) {
                return new FinnTilesContainerFactory();
            }
        }







## Step 1: Wildcards


    ![create these files and folders](/wp-content/uploads/2010/12/Screenshot1-149x300.png)
    Here we hit Tiles' most obvious downfall: every single jsp included must be declared in a definition in the tiles.xml file. Anyone that ever tried Tiles-1 knows this bad smell.

    Tiles-2 though provides a way to avoid it by using [wildcards](http://tiles.apache.org/framework/tutorial/advanced/wildcard.html), and when i hear people talking about tiles it's clear this Tiles-2 "dynamic composite" paradigm hasn't really caught on.

    Let's declare a definition and template like (and create some files and folders as shown to the right):


    //--tiles.xml
        <definition name="REGEXP:(.+)" template="/WEB-INF/tiles/template.jsp">
    	    <put-attribute name="meta" value="/WEB-INF/tiles/{1}/meta.jsp"/>
    	    <put-attribute name="header" value="/WEB-INF/tiles/{1}/header.jsp"/>
    	    <put-attribute name="body" value="/WEB-INF/tiles/{1}/body.jsp"/>
    	    <put-attribute name="footer" value="/WEB-INF/tiles/{1}/footer.jsp"/>
        </definition>






     //--template.jsp
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




    Now our Spring controller can return definitions that match the names of the folder containing meta, header, body, and footer.

    Now we that we have three ready to go definitions "cat", "dog", and "cow". Spring controllers can now use them like this




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




    What's brilliant here is that we can keep adding new definitions and matching jsps in new folders without ever having to edit the xml.




## Step 2: The fallback pattern


    ![create these files and folders](/wp-content/uploads/2011/01/Screenshot-146x300.png)
    Not having to edit tiles.xml anymore is nice, but as the number of definitions grows the number of duplicated jsps with also grow. For example maybe footer and header are identical in nearly every definition.

    Here we looked into writing a custom [AttributeRenderer](http://tiles.apache.org/framework/tutorial/advanced/attribute-rendering.html) to give us such a "fallback pattern".

    In the xml the fallbacks are represented within square braces [] where each fallback option follows a pipe character "|". And they are ordered in preference.

    Now we introduce a "common" folder for jsps, and our example would turn into



    //--tiles.xml
        <definition name="REGEXP:(.+)" template="/WEB-INF/tiles/template.jsp">
    	    <put-attribute name="meta" value="/WEB-INF/tiles/[{1}|common]/meta.jsp"/>
    	    <put-attribute name="header" value="/WEB-INF/tiles/[{1}|common]/header.jsp"/>
    	    <put-attribute name="body" value="/WEB-INF/tiles/[{1}|common]/body.jsp"/>
    	    <put-attribute name="footer" value="/WEB-INF/tiles/[{1}|common]/footer.jsp"/>
        </definition>




    The AttributeRenderer, at least a simplified version of it, looks like


    public class TemplateAttributeRenderer extends org.apache.tiles.renderer.impl.TemplateAttributeRenderer {

            private static final Pattern OPTIONAL_PATTERN
                        = Pattern.compile(Pattern.quote("[") + ".+" + Pattern.quote("]"));

            private static final String OPTION_SEPARATOR = Pattern.quote("|");

            @Override
            public void write(Object obj, Attribute attribute, TilesRequestContext request) throws IOException {

                Matcher matcher = null != obj && obj instanceof String
                        ? OPTIONAL_PATTERN.matcher((String) obj)
                        : null;

                if (null != matcher && matcher.find()) {
                    PrintWriter writer = request.getPrintWriter();
                    String match = matcher.group();
                    String[] choices = match.substring(1, match.length() - 1).split(OPTION_SEPARATOR);
                    for (int i = 0; i < choices.length && !done; ++i) {
                        String template = ((String) obj).replaceFirst(Pattern.quote(match), choices[i]);
                            try {
                                if (null != applicationContext.getResource(template)) {
                                    super.write(template, attribute, request);
                                }
                            } catch (FileNotFoundException ex) {
                                if(ex.getMessage().contains(template)){
                                    LOG.trace(ex.getMessage()); // expected outcome. continue loop.

                                }else{
                                  throw ex; // comes from an inner templateAttribute.render(..) so throw on
                                }
                            }
                    }
                } else {
                    super.write(obj, attribute, request);
                }
            }
        }



    To plug this AttributeRenderer make sure your TilesContainerFactory returns it



        public class FinnTilesContainerFactory extends BasicTilesContainerFactory {
            ...
            @Override
            protected AttributeRenderer createTemplateAttributeRenderer(
                    BasicRendererFactory rendererFactory,
                    TilesApplicationContext applicationContext,
                    TilesRequestContextFactory contextFactory,
                    TilesContainer container,
                    AttributeEvaluatorFactory attributeEvaluatorFactory) {

                TemplateAttributeRenderer templateRenderer = new TemplateAttributeRenderer();
                templateRenderer.setApplicationContext(applicationContext);
                templateRenderer.setRequestContextFactory(contextFactory);
                templateRenderer.setAttributeEvaluatorFactory(attributeEvaluatorFactory);
                return templateRenderer;
            }
            ....
        }



    As the system developers write new spring controllers creating brand new definitions the front end developers, with this new "Convention over Configuration" Tiles setup, only need to create the  new folder and jsps for fragments they wish to customise. No more xml editing and no duplicate JSPs.

    ![](/wp-content/uploads/2010/11/information.gif) For a large company this could help enforce UI standards because they have control over the common/ folder - and they can keep an eye that the overrides, the customisations, never become too outlandish, or too out of line, with the standard look of the website. In turn the front end developers can also look back at the common/ folder to see what the expected default should look like.




## Step 3: Definition includes


    For a large website these fragments: meta, header, body, and footer; won't be enough. A real website can quickly be using scores of fragments for columns within the body, banners and advertisements, analytics, seo meta data, css and javascript meta includes, etc, etc.

    And adding another touch of reality is knowing that these different pages: cat, dog, and cow; for any real website would likely have different "actions". For example view a dog, edit a dog, and search for a dog. Again there'll be a lot of duplicate jsps.

    By being able to dynamically inject one tiles definitions into another, a la "definition injection", this was all elegantly handed. Here we're stepping into the Decorator pattern's paradigm, needing to decorate our definitions by extending Tiles' DefinitionFactory. It would also make sense here to take advantage of Tiles' ViewPreparer, but we weren't so clear on the ViewPreparer implementation required, and overriding the DefintionFactory is one accepted way to configure Tiles now.

    First of all we change the existing definition names into the form "action.category" making the controller(s) now look like




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




    then change the tiles.xml to introduce the definition includes




        //-- tiles.xml
        <definition name="REGEXP:([^.]+)\.([^.]+)" template="/WEB-INF/tiles/template.jsp">
            <put-attribute name="meta" value="/WEB-INF/tiles/[{2}|{1}|common]/meta.jsp"/>
            <put-attribute name="header" value="/WEB-INF/tiles/[{2}|{1}|common]/header.jsp"/>
            <put-attribute name="body" value="/WEB-INF/tiles/[{2}|{1}|common]/body.jsp"/>
            <put-attribute name="footer" value="/WEB-INF/tiles/[{2}|{1}|common]/footer.jsp"/>

            <!-- definition injection performed by DefinitionInjectingContainerFactory.instantiateDefinitionFactory(..) -->
            <put-list-attribute name="definition-injection">
                <add-attribute value=".category.{2}" type="string"/>
                <add-attribute value=".action.{1}.{2}" type="string"/>
            </put-list-attribute>
        </definition>

        //-- tiles-core.xml
        <definition name="REGEXP:\.action\.view\.([^.]+)">
            <!-- override attributes -->
            <put-attribute name="body" value="/WEB-INF/tiles/[{2}|{1}|view]/view_body.jsp"/>
            <!-- search attributes -->
            <put-attribute name="view.content" value="/WEB-INF/tiles/[{2}|{1}|view]/view_content.jsp"/>
            <put-attribute name="view.statistics" value="/WEB-INF/tiles/[{2}|{1}|view]/view_statistics.jsp"/>
        </definition>
        <definition name="REGEXP:\.action\.edit\.([^.]+)">
            <!-- override attributes -->
            <put-attribute name="body" value="/WEB-INF/tiles/[{2}|{1}|edit]/edit_body.jsp"/>
            <!-- edit attributes -->
            <put-attribute name="edit.form" value="/WEB-INF/tiles/[{2}|{1}|view]/edit_form.jsp"/>
            <put-attribute name="edit.status" value="/WEB-INF/tiles/[{2}|{1}|view]/edit_status.jsp"/>
        </definition>
        <definition name="REGEXP:\.action\.search\.([^.]+)">
            <!-- override attributes -->
            <put-attribute name="body" value="/WEB-INF/tiles/[{2}|{1}|search]/search_body.jsp"/>
            <!-- search attributes -->
            <put-attribute name="search.form" value="/WEB-INF/tiles/[{2}|{1}|search]/search_form.jsp"/>
            <put-attribute name="search.results" value="/WEB-INF/tiles/[{2}|{1}|search]/search_results.jsp"/>
        </definition>
        //-- tiles-cat.xml
        <definition name="REGEXP:\.category\.cat">
            <!-- cat attributes -->
            <put-attribute name="cat.extra_information" value="/WEB-INF/tiles/[{2}|{1}|cat]/cat_extra_information.jsp"/>
            <put-attribute name="cat.feline_attributes" value="/WEB-INF/tiles/[{2}|{1}|cat/cat_feline_attributes.jsp"/>
        </definition>
        //-- tiles-dog.xml
        <definition name="REGEXP:\.category\.dog">
            <!-- cat attributes -->
            <put-attribute name="dog.extra_information" value="/WEB-INF/tiles/[{2}|{1}|dog]/dog_extra_information.jsp"/>
            <put-attribute name="dog.k9_attributes" value="/WEB-INF/tiles/[{2}|{1}|dog/dog_k9_attributes.jsp"/>
        </definition>
        //-- tiles-cow.xml
        <definition name="REGEXP:\.category\.cow">
            <!-- cat attributes -->
            <put-attribute name="cow.extra_information" value="/WEB-INF/tiles/[{2}|{1}|cow]/cow_extra_information.jsp"/>
            <put-attribute name="cow.farm" value="/WEB-INF/tiles/[{2}|{1}|cow/cow_farm.jsp"/>
        </definition>




    There's a lot of configuration here now, but the idea isn't to avoid configuration altogether but to avoid having to edit the configuration everytime a new jsp or page is created.

    Take note there's a clear separation now between each category definition and each action definition. For example such separation helps various development roles work in parallel: front end developers can concentrate on categorical designs while system developers often work initially with actions jsps to get them functionally working before passing them off to the front end developers. But of course such separation of concerns will show benefits in many more ways that just this example.

    The following DefinitionsFactory makes this all come together




        public class FinnUnresolvingLocaleDefinitionsFactoryImpl extends UnresolvingLocaleDefinitionsFactory {

            private static final String DEF_INJECTION = "definition-injection";

            public FinnUnresolvingLocaleDefinitionsFactoryImpl() {}

            // this method can return null
            // injected definitions are expected to have "." prefix
            @Override
            public Definition getDefinition(String name, TilesRequestContext tilesContext) {
                Definition def = null;
                // WEB-INF is a pretty clear indicator it is not a definition
                if(!name.startsWith("/WEB-INF/")){
                    def = super.getDefinition(name, tilesContext);
                    if(null != def){
                        // use a safe copy
                        def = new Definition(def);
                        // explicit injected definitions
                        Attribute defList = def.getLocalAttribute(DEF_INJECTION);
                        if(null != defList && defList instanceof ListAttribute){
                            List list = (List) defList.getValue();
                            for(Attribute inject : list){
                                injectDefinition((String) inject.getValue(), def, tilesContext);
                            }
                        }
                    }
                }
                return def;
            }

            private void injectDefinition(String fromName, Definition to, TilesRequestContext cxt) {
                assert null != fromName : "Definition not found " + fromName;
                assert fromName.startsWith(".") : "Injected definitions must have \".\" prefix: " + fromName;
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




    To plug this DefinitionsFactory in again make sure to return it from your TilesContainerFactory




        public class FinnTilesContainerFactory extends BasicTilesContainerFactory {
            ...
            @Override
            protected UnresolvingLocaleDefinitionsFactory instantiateDefinitionsFactory(
                    final TilesApplicationContext applicationContext,
                    final TilesRequestContextFactory contextFactory,
                    final LocaleResolver resolver) {

                return new FinnUnresolvingLocaleDefinitionsFactoryImpl();
            }
            ...
        }







## When the Composite pattern is superior


    It's nonsense to think that any one pattern is _better_. Both the Composite pattern and the Decorator pattern have their strengths and weaknesses, even after we have progressed the Composite on to being highly dynamic and automated. They do, and achieve, quite different things and hence each should be used where applicable...

    ![](/wp-content/uploads/2010/11/lightbulb_on.gif) The [Decorator pattern](http://en.wikipedia.org/wiki/Decorator_pattern) allows front end code and design to be injected as we process. When a decision is known during the request processing the code can immediately build a decorator. The context here only holds all the built decorators. And at the end of the request lifecycle the page is assembled by putting together all these decorators. Decorators can also be built upon each other, or stacked, and this can be useful when the composition of the page is completely loose.

    ![](/wp-content/uploads/2010/11/lightbulb_on.gif) The [Composite pattern](http://en.wikipedia.org/wiki/Composite_pattern) presumes the page to be a "composite" made up from components, where each component is free to be itself a "composite". The pattern allows more control of the composite's heirarchy: as the delegation is top-down as opposed to the Decorator pattern whom's is bottom-up. The composite pattern works well when the operations you need to perform on each object is limited, that is it isn't a problem that the operations you may perform on any one object is the lowest common demominator of operations that you can perform on every object.



    When you use jsp includes whether you like it or not you have a page heirarchy. If your jsps typically have a template with fragments you are already working within the composite pattern's paradigm: the template being the "composite" and the fragments being the components. Each fragment, each JSP, has a context that holds the model map, or variables, in various scopes, and within this context they are self-sufficient. The only operation required upon them by others is inclusion. Applying the composite pattern here means treating each fragment as a self-sufficient object, the only thing that can happen to it is it will be included.

    This shows how we can design to reduce complexity and encourage there to be only one unified context.

    It also shows how we can maintain a top-down control of the page's heirarchy, something necessary in a MVC design where the control layer wants to hand off a finished model map, ie a fixed context, that the view layer is free to build itself off. In contrast when we choose the Decorator pattern we can run foul of letting code run in parallel to the MVC pattern.


    ![](/wp-content/uploads/2010/11/patterns-flow.gif)




## Conclusion


    Front end developers, the system developers, and the architects need to work together. With the front end today often centered around loose coupling design: javascript's lack of type safety, ajax's separation from the server, and jQuery's liberation of the dom; it is all too easy to rebel against any form of structure or organisation for fear of being tied into the formalities of the strict-typed java world.

    Neither should the fear of giving up control of one's craftsmanship mean keeping logic and control in the View layer, this stuff belongs in the Control layer: and this means the system developers need to work harder to get the front end developers collaborating in the control layer. What's too easily forgotten is that everything has entropy, and the larger the organisation, the larger the codebase, the less you fight the entropy, the quicker you end up with just a pile of spaghetti in your lap.

    The four steps gone through in this article show you how Tiles-2 and the Composite pattern can be ramped up on steroids to give front end and systems developers what they want hopefully in a way that encourages them to work together.




    **References**




  1. [Tiles-2](http://tiles.apache.org)


  2. [Composite pattern](http://en.wikipedia.org/wiki/Composite_pattern)


  3. [Decorator pattern](http://en.wikipedia.org/wiki/Decorator_pattern)


  4. [Spring Web](http://static.springsource.org/spring/docs/3.0.x/spring-framework-reference/html/spring-web.html)


  5. Integrating [Tiles and Spring](http://static.springsource.org/spring/docs/3.0.x/spring-framework-reference/html/view.html#view-tiles)


  6. Initial discussion regarding Tiles [definition delegation](http://old.nabble.com/howto-delegate-to-other-definitions---td27516586.html)


  7. Spring >3.0.1 required to work with Tiles-2.2+ :: [TilesConfigurer does not ...](http://jira.springframework.org/browse/SPR-6097)

    [ ](http://jira.springframework.org/browse/SPR-6097)  



