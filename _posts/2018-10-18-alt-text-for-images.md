---
layout: post
comments: true
date: 2018-10-18 11:00:00+0200
authors: Tor-Martin Storsletten
title: "How to make good alt text for images"
tags:
- accessibility
- alt text
- assistive technology
- html
- images
- screen readers
- svg
---

It is common knowledge that images are required to have an alt attribute. This is one of the primary rules of accessibility for web pages (defined in [WCAG 2.1 Principle 1, success criterion 1.1.1](https://www.w3.org/TR/WCAG21/#non-text-content)). Nevertheless, coming up with a good alt text for an img element might be easier said than done. Here are some guidelines that may help you:

- The alt text is meant to be a description, not a title or a tooltip. Try to describe the image with a clear and concise sentence. What do you see when you look at the image? Is it a "Purple dinosaur with a Santa hat on its head", or a "Red exclamation mark emitting yellow sparks"? Imagine the potential confusion if customer rep says, "Click on the red arrow," but the blind user can't find it because the alt text reads, "Go". Or imagine it the other way around. A blind user trying to help their sighted friend by saying, "Just tap the Go button", and the friend ends up feeling stupid because they can't see a "Go" button anywhere on the page.
- Do not start the text by stating that it is "Icon of" or "Picture of", since this is already implied by the semantics of the img element.
- Do not use text that is also displayed in an adjacent element such as a heading. There's no need to show exactly the same text twice in the same context.
- If it's technically impossible to provide a good description for an image whose presence is significant, then you may resort to a more generic alt text, such as "User's main profile picture", or "Uploaded image 1" (or use date and time instead of index).
- Never omit the alt attribute. However, if the image is merely for decorative purposes and it's impractical to provide a proper description, then you may use an empty string as alt text to indicate that the image can be safely ignored by assistive technology.

P.S.: Give SVG elements alt text too! Either by creating a title element as the first child element of the svg, or by setting role="img" and aria-label="The alt text" on the svg element. The latter won't generate any tooltips on mouse-over events!
