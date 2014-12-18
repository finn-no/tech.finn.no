# CONTRIBUTING

[General](https://github.com/finn-no/tech.finn.no/blob/gh-pages/CONTRIBUTING.md#general)  
[Write a post](https://github.com/finn-no/tech.finn.no/blob/gh-pages/CONTRIBUTING.md#write-a-post-with-git)  
[Metadata](https://github.com/finn-no/tech.finn.no/blob/gh-pages/CONTRIBUTING.md#metadata)

## General
This project is live. All posts in `_posts` are live on the blog.  
To add a new post you need to add a new file to `_posts`.  
The filename is of the formate `YYYY-MM-DD-post-title.md`.  
Add [metadata](https://github.com/finn-no/tech.finn.no/blob/gh-pages/CONTRIBUTING.md#metadata) to the top of the file and write the post in markdown.  
You can use something like [Prose.io](http://prose.io) if you are less familier with Git.  
Read [here](http://jekyllrb.com/docs/posts/) for more information.

## Write a post with Git
Follow the [Quick start](https://github.com/finn-no/tech.finn.no/blob/gh-pages/README.md). We have made a `rake` to make it easier to create a new post.

```sh
$ bundle exec rake post title="Hello World"
```
Write the post in markdown. Commit and push your changes and the post will be live within minutes.

## Metadata

Since we are using Jekyll every post is required to have some metadata at the top over every post.
The metadata takes the form of YAML this is a description of the different fields.

```YAML
---
layout: post
comments: true
date: 2014-12-18 13:01:03+0100
authors: Name
title: "Hello World"
tags:
- tag
---
```

## layout
This defines the design of the post. You want to use `post`.

## comments
This enables or disables comments with `true` or `false`.

## title
This is the title for your post

## date
This date will be the publishing date.

## authors
Write the name of the author. If there are multiple authors you can write it like this:

```YAML
authors:
- Nasse Nøff
- Ole Brumm
```

## tags
If you want to add tags add them here. For inspiration of what tags others use see [here](http://tech.finn.no/tags/).