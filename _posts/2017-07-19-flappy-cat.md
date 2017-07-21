---
layout: post
comments: true
date: 2017-07-19 17:58:36+0100
authors: Pernille Celia Sethre, Gunn Skinderviken
title: "Flappy Cat"
tags: 
- fun 
- experimentation 
- game 
- hackday
---

## Why make a Flappy Cat game? 
The Idea was to make a Flappy Bird with motion tracking control for our JavaZone stand. Some quick research told us it should be possible. Since cats are assosiated with FINN, it of course became a Flappy Cat game. Come and try it at JavaZone in September. 

Made by search team. 

## How we made it

![Flappy tutorial](/images/2017-07-19-flappy-cat/flappy-tutorial.gif)   

**Step 1:** Make the game. As neither of us are game developers, we found this [flappy bird](http://www.lessmilk.com/tutorial/flappy-bird-phaser-1) tutorial online that we decided to follow. This tutorial uses Phaser, an open source JavaScript/HTML5 game development framework. It was easy to follow and it explained all the steps in detail, so even the most back-end heavy developer in our team had no problem writing his first JavaScript game. 

![Phaser](/images/2017-07-19-flappy-cat/phaser-logo.png)   

**Step 2:** Buy the motion tracker. We chose Kinect as we found videos on YouTube of people doing exactly what we wanted to do with the Kinect sensor. We only had one day to do this project, so this was not the time to reinvent the wheel. 

![alt text](/images/2017-07-19-flappy-cat/kinect.jpg)

**Step 3:** Tweaking one of the example apps that was included in the Kinect 2.0 SDK. We found an app that was made to do something when it detected a gesture. This was perfect for our purpose. We recorded our desired gesture, “the flap”, in Kinect Studio and used Visual Gesture Builder (also included in the SDK) to build a database of our gesture. We then used this gesture database in the example app and suddenly we could recognize a flap! Now, all we needed was for something to happen when a flap was registered. 

![alt text](/images/2017-07-19-flappy-cat/wave.gif)

**Step 4:** Creating a simple Node.js server that could not only serve our game, but also have an API endpoint and give the Windows app a way to notify the game that it detected a flap. This is a simple solution; a GET call to /flap is equivalent to pressing space in the game. 

![Flap demo](/images/2017-07-19-flappy-cat/flap-demo.gif)