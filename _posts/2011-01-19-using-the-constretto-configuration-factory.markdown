---
authors:
- mick
comments: true
date: 2011-01-19 18:07:02+00:00
layout: post
slug: using-the-constretto-configuration-factory
title: Using the Constretto Configuration Factory
redirect_from: /using-the-constretto-configuration-factory/
tags:
- constretto
- Inversion of Control
- spring
- Systems
---

Development at FINN.no was quick to jump on the [Constretto](http://constretto.org) library. It's a great addition to any application making it easy to handle different environments and profiles with just the one build.

When using Constretto at FINN.no one problem we found with the API is that it creates very verbose client code when the same ini or properties file is accessed throughout various class files.

For example a repetition of the following happens


    public static final String CONSTANT_A = new ConstrettoBuilder()
                .createIniFileConfigurationStore()
                .addResource(new DefaultResourceLoader().getResource("classpath:file.ini"))
                .done()
                .getConfiguration()
                .evaluateToString("CONSTANT_A");

    public static final String CONSTANT_B = new ConstrettoBuilder()
                .createIniFileConfigurationStore()
                .addResource(new DefaultResourceLoader().getResource("classpath:file.ini"))
                .done()
                .getConfiguration()
                .evaluateToString("CONSTANT_B");





One way around this is to use spring injection. This makes nice concise code but comes with two flaws: the constants can no longer be constant (they have to be member fields), and you have to lump all your properties together into one big "globals" store (days of f77 comes to mind). A third problem with this approach is that you're passing Constretto's context through Spring's context, we don't really want chained contexts but direct access to individual contexts if possible.

So looking for a more elegant solution to all this we wrote a [simple factory](http://constretto.jira.com/secure/attachment/10010/constrettoconfigurationfactory.java), that has now been accepted upsteam,  that provides the necessary convenience methods so the constants can be declared short, sweet, and simple


    import static org.constretto.util.StaticlyCachedConfiguration.config;

    public static final String CONSTANT_A = config("classpath:file.ini").evaluateToString("CONSTANT_A");
    public static final String CONSTANT_B = config("classpath:file.ini").evaluateToString("CONSTANT_B");





This also caches the `ConstrettoConfiguration`s improving your application's startup performance.

This was taken from Constretto's [issue CC-14](http://constretto.jira.com/browse/CC-14)  


