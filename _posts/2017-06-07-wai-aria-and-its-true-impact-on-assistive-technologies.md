---
layout: post
comments: true
date: 2017-06-07 14:00:00+0200
authors: Tor-Martin Storsletten
title: "WAI-ARIA and its true impact on assistive technologies"
tags:
- accessibility
- aria
- screen readers
---


## 1. Introduction
This article is intended for web developers who want to improve their understanding of the effect that WAI-ARIA has on assistive technologies. It will therefore be assumed that you already have basic knowledge about terms such as ARIA and screen readers.

### 1.1. Generalize screen readers
From the perspective of web development, it serves no purpose to delve into technical details about specific brands of screen reading software. There are several reasons for this:

- We can't programmatically detect which assistive technologies our users are using. There are several good reasons for this, including privacy concerns pertaining to confidential information regarding personal health and diagnostics.
- Universal design means design for everyone, regardless of which type of assistive technology is being used.

Therefore we won't talk about specific brands of screen reading software in this article. We will focus on best practices with universal design in mind.

### 1.2. The main issues with ARIA
Firstly, support for ARIA in assistive technologies is still partial at best. A consequence of this is that only a handful of ARIA features are reliable across different implementations, and that is if we don't count older assistive technologies that have no support for ARIA at all. Therefore it becomes important to differentiate between ARIA features that we can reliably use for correcting accessibility issues versus ARIA features that we should only use for improving UX. I.e. to a certain degree we can accept that UX will differ depending on which screen reader is being used, but keeping parts of our applications inaccessible to everyone except those who are fortunate enough to use the right screen reader is unacceptable. Naturally this is something that will slowly but surely sort itself out as makers of assistive technologies continue to work on their ARIA implementations, but for the time being we ought to remain mindful of this.

The second issue related to ARIA is that many developers who technically know how to use it aren't aware of the actual effect it has on assistive technologies. This is probably the biggest root of widespread incorrect use of ARIA on the web today, which is a growing source of frustration for users of assistive technologies. This is definitely an issue that web developers can and should do something about, and a good step in the right direction is to read the rest of this article.

## 2. Screen readers and virtual user interfaces
Many screen readers create an invisible virtual user interface for presenting web content. This concept typically involves a process where the screen reader reads the entire web document by accessing its DOM, then it uses that information to construct a virtual representation of the document that the user can browse through. The screen reader will route most of the keyboard input to functions in the virtual user interface where it will be used for navigational purposes. Different screen readers have different quick keys, but typical examples are H for jumping to next heading, Shift+H for jumping to the previous heading, digits 1 through 6 for jumping between headings at levels 1 through 6 respectively, F for form elements, T for tables, and so on. These virtual user interfaces provide the user with a consistent and familiar interface that is highly optimized for quick and convenient keyboard navigation. On the flip side, the web application won't receive keyboard input from the user while this virtual browsing mode is engaged, which can be a real issue in situations where the application requires keyboard input for optimal interaction.

### 2.1. Native interactive widgets
The most obvious example of an interactive widget that requires keyboard input from the user is an edit field (HTML textarea and certain input types). When the user starts interacting with such an element, their screen reader will automatically disengage its virtual user interface and pass keyboard input on to the application. At this point the user may freely take advantage of all keyboard features offered by the application and the web browser, such as typing in the edit field, move the cursor around using Arrow keys, etc. No ARIA magic is needed to make this work, since both input and textarea elements are native HTML widgets that web browsers and screen readers know how to handle correctly.

The select element is another example of a native HTML widget that web browsers provide keyboard navigation for. The user can use arrow keys for navigation between the options, page up and page down for quickly scrolling through the options, home and end for beginning and end of the dropdown, and alpha-numeric keys can be used to efficiently navigate to the options that match the input from the user. Again, screen readers will therefore automatically turn off the virtual user interface when the user starts interacting with this type of widget, and all this works right out-of-the-box because the select element is a native HTML widget.

### 2.2. Non-native interactive widgets
The same opportunity for automatic toggling of virtual user interfaces should also be applied to non-native interactive widgets that are expected to handle keyboard navigation. Examples include menus, list boxes, tree views, grids, and even modal dialog boxes. Why should all of these widgets process keyboard input? Firstly because some users rely on a keyboard for interaction. Not everyone can use a pointing device (such as a mouse). Secondly, some users want it, because keyboard navigation can be fast, efficient, and convenient for interaction with these types of widgets. Implementing keyboard navigation for custom interactive widget can improve UX for a surprisingly wide range of users.

### 2.3. Examples of common keyboard navigation features
- Alpha-numeric keys for quick selection of items. For instance, if focus is on one of the grouped buttons in a dialog box, the user should be able to press e.g. the Y key to select the Yes button. And item matching should be implemented for list boxes, tree views, menus, radio buttons, and pretty much any widget that contains grouped text items that are available for selection.
- Arrow keys. Up and Down for navigating between items. Left and Right for expanding and collapsing sub-menus and branches in treeviews. Up/Down for values and Left/Right to switch between year, month, and date in a date picker widget. Proper spatial navigation for grids. And users should be able to use arrow keys to move focus between the dismiss option in a dialog box (Yes, No, Cancel, etc.)
- Home/End keys for moving focus to the first or the last item in a group of items.
- Page Up/Page Down should be implemented as the equivalent of X amount of Arrow Up/Arrow Down key presses for faster browsing through the items. X could either be a fixed number, or you could make a formula to calculate X based on the size of the group.

### 2.4. Announcing support for keyboard navigation
We need to let screen readers know that we have taken the time to implement proper keyboard navigation for our non-native interactive widgets, otherwise screen readers will inadvertently hide this functionality behind their virtual user interfaces. It is true that these virtual user interfaces are effective for overall page navigation, but custom keyboard navigation is often even more effective when interacting with specific widgets. Below you'll find three methods of using ARIA to help screen readers with automatic toggling of virtual user interfaces for non-native interactive widgets that support keyboard navigation.

#### 2.4.1. Method 1: Defining roles
The WAI-ARIA specification lists many possible roles for HTML elements, and modern screen readers will handle presentation and interaction of elements according to the designated role. Screen readers will typically turn off the virtual user interface when the user starts interacting with elements that have any of the following roles: combobox, grid, listbox, menu, menubar, radiogroup, scrollbar, slider, spinbutton, tablist, textbox, tree, and treegrid.
IMPORTANT! You MUST NOT set any of these roles unless the widget has a functional mechanism for keyboard navigation implemented, otherwise the user will be much better off with navigation features provided by the screen reader. These ARIA roles are not just meant to be used for announcing the presence of something that visually resembles a certain widget. Their purpose is to prepare assistive technologies to accommodate the type of interaction that users associate with the type of widget in question, and naturally this requires that the widget itself keeps the promise of providing methods for keyboard navigation. E.g. a tree view must not just look like a tree view, it must also behave like one. Just like a doctor must not just look like a doctor. In short, we have certain expectations about entities that have specific roles.

ARIA roles might also affect how screen readers will present the content of a widget to the user. A tree view for instance might be represented in a virtual user interface as a single starting point for interaction, effectively hiding the content of the tree so that it won't clutter the virtual user interface with a lot of data. The user could then choose to interact with the tree if they want to browse through its items, but if keyboard navigation has not been implemented then the tree will essentially end up being totally inaccessible to the user since they can't access the content of the tree in the virtual user interface either. So, let me emphasize that you MUST NOT set any of the ARIA roles for interactive widgets listed above unless you have also implemented a mechanism for keyboard navigation.

#### 2.4.2. Method 2: Using the aria-haspopup property
This method can be used in conjunction with ARIA roles to aid screen readers with automatic deactivation of virtual user interfaces when an interactive widget pops up (like an overlay) in response to an action from the user. E.g. the user clicks on a button, and the expected result is that a menu pops up. Obviously the most convenient solution here for the user's perspective would be to make the screen reader automatically initiate interaction with the popup widget since the user has already implied that they would like to interact with the widget by clicking on the button that produced it.

By setting aria-haspopup on an element, we promise two things:

1. The application takes responsibility of moving focus to the starting point / default selectable element of the pop-up widget when the user starts interacting with the element that has aria-haspopup (e.g. by clicking on it).
2. The pop-up widget is accessible to users of assistive technology (i.e. keyboard navigation has been properly implemented for the pop-up widget).

Screen readers may then automatically deactivate the virtual user interface to facilitate optimal usability with the widget. And again, this means that the pop-up widget MUST be accessible with a keyboard in the first place. Think of the aria-haspopup property as a promise of implemented accessibility features. It does not make the pop-up widget more accessible by itself.

A few more things to keep in mind regarding aria-haspopup:

- The mechanism for activating the element that has aria-haspopup MUST be accessible for everybody. This means that you can't just rely on e.g. a mouse-over event to trigger your pop-up menus, since many users of screen readers can't successfully use a mouse even if they had one. Use click or focus events instead/in addition.
- The element that has aria-haspopup must have an interactive role (either implied by HTML semantics or set with the role attribute), otherwise screen readers might simply choose to ignore the aria-haspopup property altogether. The exception here is the link role (or the native a element), which might cause the screen reader to retain its virtual user interface upon activation in anticipation of browser navigation. Remember that you can always override implied semantics with the role attribute, and the button role is usually a good choice for a clickable element that triggers a pop-up widget in the application.
- The pop-up widget must also have correct semantics. I.e. give the widget an ARIA role that semantically corresponds to the type of widget that is specified in the aria-haspopup property. ARIA 1.1 defines the following possible values for aria-haspopup: dialog, grid, listbox, menu, and tree. E.g. use a value of "dialog" even if the pop-up widget has its role set to "alertdialog", not just if its role is "dialog". Also, for backward compatibility with ARIA 1.0, a value of "true" for the aria-haspopup property is equivalent to "menu". Therefore, if your pop-up widget is a menu, then you might get wider coverage by using "true" instead of "menu".

#### 2.4.3. Method 3: The application role
Setting the application role on an element tells screen readers that the content of the element is not intended for virtual user interfaces. The application claims responsibility of providing adequate keyboard navigation features for all interactive widgets that are part of an element that has the application role.
IMPORTANT! You should think very carefully before using this method. Many users prefer to use the screen reader's virtual user interface for overall navigation since it provides a consistent and familiar way of browsing web applications. Setting the application role (e.g. on the body element) is like blocking the highway and forcing drivers to use an alternative route where directions might be scarce. Unless you have a very specific reason to do so, you should refrain from doing it.

## 3. Defining relations between HTML elements
ARIA provides three different properties for explicitly defining relations between HTML elements in cases where the DOM hierarchy does not already do this implicitly (by nesting and ordering elements in a sequence that makes sense from an end user's perspective).

### 3.1. The aria-owns property
The aria-owns property is used to rearrange elements in the accessibility tree (which is a version of the DOM tree that has been tailored to accommodate assistive technologies). Of the three properties for specifying relations between elements, aria-owns is preferred for the following reasons:

- Aria-owns is the most supported property for specifying relations between elements.
- No special interaction is required for aria-owns to take effect. The user won't need any additional knowledge to benefit from this feature.

As an example of a scenario where aria-owns would be useful, let's say we have a list where each item consists of a checkbox, an image, a heading, a paragraph, and a link. We might prefer to keep these elements in that particular order in the DOM for visual styling purposes. For someone who uses a screen reader however, this order could easily end up being confusing since the heading is not the first element. The heading is the element that introduces a new context to the user. Therefore the user wouldn't know the context of the checkbox the first time they spot it. The second issue with this order is that the user might believe that the checkbox belongs to the previous list item, since it's technically under the heading of the previous list item. To fix this, we could create an empty div element at the top of each list item, and set aria-owns to refer to the id of the heading. This would alter the accessibility tree so that the heading would come before the other elements.

### 3.2. The aria-flowto property
Instead of rearranging the DOM hierarchy for assistive technologies (like aria-owns does), the aria-flowto property tells assistive technologies to present the user with an option to jump directly to the related element(s). Unfortunately this property is not widely supported by assistive technologies. Another issue with aria-flowto is that users might not know how to trigger the jumps, even if their assistive technologies support this feature.

### 3.3. The aria-controls property
The aria-controls property is defined by the WAI-ARIA specification as a way of indicating which element(s) are being controlled by the element that has aria-controls set. In practice it provides a jump point (similar to aria-flowto), but it also indicates that interaction with the element that has aria-controls might have an effect on the controlled element(s). Consequently this means that aria-controls must only be added to interactive elements. Unfortunately, just like with aria-flowto, few assistive technologies support this property, and it also requires that the user knows how to initiate the jumps to take advantage of this.

Interestingly enough, numerous web sites seem to be richly seasoned with aria-controls, and the golden rule about using ARIA to define relationships only when it can not be inferred from the DOM hierarchy is often forgotten. In cases where this feature is needed, aria-owns would be a better choice since it is more widely supported. In cases where this feature is superfluous, it just adds extra noise for those who use assistive technologies that support it. E.g. it makes no sense to create a jumping point from a button to a panel if the button is immediately followed by the panel in the DOM hierarchy. The user would spend more time and effort initiating the jump with their screen reader than it would be to just continue with the normal and familiar flow of navigation that would yield the same result.

## 4. External resources
- [w3.org – WAI-ARIA 1.1](https://www.w3.org/TR/wai-aria-1.1/)
- [MozillaWiki – Virtual buffer smash](https://wiki.mozilla.org/Accessibility/Virtual_buffer_smash)
- [HeydonWorks – Aria-Controls is Poop](https://www.heydonworks.com/article/aria-controls-is-poop)
