---
authors: mick
comments: true
date: 2013-06-26 20:42:28+00:00
layout: post
title: Package Management conflicts Continuous Delivery
redirect_from: /package-management-conflicts-continuous-delivery/
---




![Package](http://www.slashroot.in/sites/default/files/styles/article_image_full_node/public/field/image/yum%20package_0.png)


The idea of package management is to correctly operate and bundle together various components in any system. The practice of package management is a consequence from the design and evolution of each component's API.



### Package management is tedious

but necessary. It can also help to address the 'fear of change'.

We can minimise package management by minimising API. But we can't minimise API if we don't have experience with where it comes from. You can't define for yourself what the API of your code is. It is well beyond that of your public method signatures. Anything that with change can break a consumer is API.



### Continuous Delivery isn't void of API

despite fixed and minimised interfaces between runtime services, each runtime service also contains an API in how it behaves. The big difference though is you own the release change, a la the deployment event, and if things don't go well you can roll back. Releasing artifacts in the context of package management can not be undone. Once you have released the artifact you must presume someone has already downloaded it and you can't get it back. The best you can do it release a new version and hope everyone upgrades to it quickly.



### Push code out from behind the shackles of package management

take advantage of continuous delivery! Bearing in mind a healthy modular systems design comes from making sure you got the api design right – so the amount one can utilise CD is ultimately limited, unless you want to throw out modularity. In general we let components low in the stack "be safe" by focusing on api design over delivery time, and the opposite for components high in the stack.



### High in the stack doesn't refer to front-end code

Code at the top of the stack is that free of package management and completely free for continuous deployment. Components with direct consumers no longer sit at the top of the stack. As components consumers multiple, and they become transitive dependencies, they move further down the stack. Typically entropy of the component corresponds to position in the stack. Other components forced into package management can be those where parallel versions need be deployed.



### Some simple rules to abide by…






  * don't put configuration into libraries.
      _because this creates version-churn and leads to more package management_  




  * don't put services into libraries.
      _same reason as above._  




  * don't confuse deploying with version releases.
      _don't release every artifact as part of a deployment pipeline.
      separate concerns of continuous delivery and package management._  




  * try to use a runtime service instead of a compile-time library.
      _this minimises API, in turn minimising package management,_  




  * try to re-use standard APIs (REST, message-buses, etc).
      _the less API you own the less package management.
      but don't cheat! data formats are APIs, and anything exposed that breaks stuff when changed is API._


