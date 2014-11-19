---
authors:
- mick
comments: true
date: 2012-06-23 10:31:21+00:00
layout: post
slug: profiling-and-debugging-view-templates
title: Profiling and debugging view templates
redirect_from: /profiling-and-debugging-view-templates/
tags:
- front-end
- Tiles
---



Ever needed to profile the tree of JSPs rendered server-side?
Most companies do and I've seen elaborate and rather convoluted ways to do so.

With Tiles-3 you can use the `PublisherRenderer` to profile and debug not just the tree of JSPs but the full tree of all and any view templates rendered whether they be JSP, velocity, freemarker, or mustache.

At FINN all web pages print such a tree at the bottom of the page. This helps us see what templates were involved in the rendering of that page, and which templates are slow to render.


[![](http://tech.finn.no/wp-content/uploads/2012/06/Screenshot-tiles-publisherrenderer-tree.png)](http://tech.finn.no/2012/06/23/profiling-and-debugging-view-templates/screenshot-tiles-publisherrenderer-tree/)
We also embed into the html source wrapping comments like



    ...template output...








## The code please


To do this register and then attach your own listener to the `PublisherRenderer`. For example in your `TilesContainerFactory` (the class you extend to setup and configure Tiles) add to the methd createTemplateAttributeRenderer something like:



        @Override
        protected Renderer createTemplateAttributeRenderer(BasicRendererFactory rendererFactory, ApplicationContext applicationContext, TilesContainer container, AttributeEvaluatorFactory attributeEvaluatorFactory) {

            Renderer renderer = super.createTemplateAttributeRenderer(rendererFactory, applicationContext, container, attributeEvaluatorFactory);
            PublisherRenderer publisherRenderer = new PublisherRenderer(renderer);
            publisherRenderer.addListener(new MyListener());
            return publisherRenderer;
        }


Then implement your own listener, this implementation does just the wrapping comments with profiling information...



    class MyListener implements PublisherRenderer.RendererListener {

        @Override
        public void start(String template, Request request) throws IOException {
            boolean first = null == request.getContext("request").get("started");
            if (!first) {
                // first check avoids writing before a template's doctype tag
                request.getPrintWriter().println("\n");
                startStopWatch(request);
            } else {
                request.getContext("request").put("started", Boolean.TRUE);
            }
        }

        @Override
        public void end(String template, Request request) throws IOException {
            Long time = stopStopWatch(request);
            if(null != time){
                request.getPrintWriter().println("\n");
            }
        }

        private void startStopWatch(Request request){
            Deque<stopwatch> stack = request.getContext("request").get("stack");
            if (null == stack) {
                stack = new ArrayDeque<stopwatch>();
                request.getContext("request").put("stack", stack);
            }
            StopWatch watch = new StopWatch();
            stack.push(watch);
            watch.start();
        }

        private Long stopStopWatch(Request request){
            Deque<stopwatch> stack = request.getContext("request").get("stack");
            return 0 < stack.size() ? stack.pop().getTime() : null;
        }




It's quick to see the possibilities for simple and complex profiling open up here as well as being agnostic to the language of each particular template used. Learn more about [Tiles-3 here](http://tiles.apache.org/).

