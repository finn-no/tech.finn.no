---
authors:
- mick
comments: true
date: 2012-05-15 18:58:12+00:00
layout: post
slug: putting-a-mustache-on-tiles-3
title: Putting a mustache on Tiles-3
wordpress_id: 1150
tags:
- apache
- front-end
- java
- javascript
- mustache
- templating
- Tiles
---

We're proud to see a contribution from one of our developers end up in the Tiles-3 release!

The front-end architecture of FINN.no is evolving to be a lot more advanced and a lot more work is being done by client-side scripts. In order to maintain first time rendering speeds and to prevent duplicating template-code we needed something which allowed us to reuse templates both client- and server-side. This is where [mustache templates](http://mustache.github.com/) have come into play. We could've gone ahead and done a large template framework review, like others have done, but we instead opted to just solve the problem with the technology we already had.

Morten Lied Johansen's [contribution](http://tiles.apache.org/tiles-request/xref/org/apache/tiles/request/mustache/MustacheRenderer.html) allows Tiles-3 to render mustache templates. Existing jsp templates can be rewritten into mustache without having to touch surrounding templates or code!



## The code please


To get Tiles-3 to do this include the tiles-request-mustache library and configure your TilesContainerFactory like



        protected void registerAttributeRenderers(...) {
            MustacheRenderer mustacheRenderer = new MustacheRenderer();
            mustacheRenderer.setAcceptPattern(Pattern.compile(".*.mustache"));
            rendererFactory.registerRenderer("mustache", mustacheRenderer);
            ...
        }
        protected Renderer createTemplateAttributeRenderer(...) {
            final ChainedDelegateRenderer chainedRenderer = new ChainedDelegateRenderer();
            chainedRenderer.addAttributeRenderer(rendererFactory.getRenderer("mustache"));
            ...
        }




then you're free to replace existing tiles attributes like

    <put-attribute name="my_template" value="/WEB-INF/my_template.jsp"></put-attribute>

with stuff like

    <put-attribute name="my_template" value="/my_template.mustache"></put-attribute>



Good stuff FINN!
