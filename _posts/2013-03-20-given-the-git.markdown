---
authors:
- mick
comments: true
date: 2013-03-20 11:14:08+00:00
layout: post
slug: given-the-git
title: given the git
redirect_from: /given-the-git/
tags:
- git
---

![](http://t2.gstatic.com/images?q=tbn:ANd9GcT06Df3cXPHx1qzMy6AV2ibmUOXlKFstSObwkgmQvdUpIC16Uz_)





> "There is no way to do CVS right." - Linus



**FINN is migrating from subversion to the ever trendy git.**
We've waited years for it to happen,
here we'll try to highlight why and how we are doing it.




### Working together


There's no doubt that git gives us a cleaner way of working on top of each other. Wherever you promote peer review you need a way of working with changesets from one computer to the next without having to commit to and via the trunk where everyone is affected. Creating custom branches comes with too much (real or perceived) overhead, so the approach at best falls to throwing patches around. Coming away from a pair-programming session it's better when developers go back to their own desk with such a patch so they can work on it a bit more and finish it properly with tests, docs, and a healthy dose of clean coding. It properly entitles them as the author rather than appearing as if someone else took over and committed their work. Git's decentralisation of repositories provides the cleaner way by replacing these patches with private repositories and its easy to use branches.




### Individual productivity


Git improves the individual's productivity with benefits of [stashing](http://git-scm.com/book/en/Git-Tools-Stashing), [squashing](http://git-scm.com/book/en/Git-Tools-Rewriting-History), [reseting](http://git-scm.com/book/en/Git-Basics-Undoing-Things), and [rebasing](http://git-scm.com/book/en/Git-Branching-Rebasing). A number of programmers for a number of years were already on the bandwagon using git-svn against our subversion repositories. This was real proof of the benefits, given the headaches of git-svn (can't [move](http://stackoverflow.com/questions/5652521/does-git-svn-handle-moved-files) files and [renaming](http://stackoverflow.com/questions/3011625/git-mv-and-only-change-case-of-directory) files gives corrupted repositories)

With Git, work is encouraged to be done on feature branches and merged in to master as complete (squashed/rebased) changesets with clean and summarised commit messages.



  1. This improves efforts towards continuous deployment due to a more stable HEAD.


  2. Rolling back any individual feature regardless of its age is a far more manageable task.


  3. By squashing all those checkpoint commits we typically make we get more meaningful, contextual, and accurate [commit messages](http://thomashw.github.com/blog/2012/12/02/commit-messages/).



Reading isolated and complete changesets provides clear oversight, to the point reading code history becomes enjoyable, rather than a chore. Equally important is that such documentation that resides so close to, if not with, the code comes with a real permanence. There is no documentation more accurate over all of time than the code itself and the commit messages to it. Lastly writing and rewriting good commit message will alleviate any culture of jira issues with vague, or completely inadequate, descriptions as teams hurry themselves through scrum methodologies where little attention is given to what is written down.




### Maintaining forks


Git makes maintaining forks of upstream projects easy.


**With Git**
 fork the upstream repository,
branch,  fix and commit,
create the upstream pull request,
 while you wait for the pull request to be accepted/rejected use your  custom inhouse artifact.




**With Subversion**
file an upstream issue,
checkout the code,
fix and store in a patch attached to the issue,
while you wait use the inhouse custom artifact from the patched but uncommited codebase.




Both processes are largely the same but it's safer and so much easier using a forked git repository over a bunch of patch files.




### Has Git-Flow any advantage?


We're putting some thoughts into how we were to organise our repositories, branches, and workflows. The best introductory article we've so far come across is from [sandofsky](http://sandofsky.com/blog/git-workflow.html) and should be considered mandatory reading. Beyond this one [popular](http://nvie.com/posts/a-successful-git-branching-model/) approach is organising branches using [Git Flow](http://jeffkreeftmeijer.com/2010/why-arent-you-using-git-flow/). This seemed elegant but upon closer inspection comes with more disadvantages than benefits...



  * the majority needs to 'checkout develop' after cloning (there are more developers than ops),


  * master is but a sequence of "tags" and therefore develop becomes but a superfluous branch, a floating "stable" tag instead is a better solution over any branch that simply marks states,


  * it was popular but didn't form any standard,


  * requires a script not available in all GUIs/IDEs, otherwise it is but a convention,


  * prevents you from getting your hands dirty with the real Git, how else are you going to learn?,


  * it goes against having focus and gravity towards continuous integration that promotes an always stable HEAD. That is we desire less stabilisation and qa branches, and more individual feature and fix branches.


[GitHub Flow](http://scottchacon.com/2011/08/31/github-flow.html) gives a healthy critique of Git-Flow and it helped identify our style better. GitHub Flow focus' on continuous integration, "deploy to production every day" rather than releasing, and relying on git basics rather than adding another plugin to our development environment.




### Our workflows


So we devised two basic and flexible workflows: one for applications and one for services and libraries. Applications are end-user products and stuff that has no defined API like batch jobs. Services are our runtime services that builds up our platform, each come with a defined API and client-server separation in artifacts. Applications are deployed to environments, but because no other codebase depends on them their artifacts are never released. Services, with their APIs and client-side libraries, are released using [semantic versions](http://semver.org/), and the server-side code to them is deployed to environments in the same manner as Applications. The differences between Applications and Services/Libraries warrant two different workflow styles.

Both workflow styles use master as the stable branch. Feature branches come off master. An optional “integration” (or “develop”) branch  may exist between master and feature branches, for example CI build tools might automatically merge integration changes back to master, but take care not to fall into the anti-pattern of using merges to mark state.

The workflow for Applications may use an optional stable branch where deployments are made from, this is used by projects that have not perfected continuous deployment. Here bug fix branches are taken from the stable branch, and forward ported to master. For applications practising continuous deployment a more GitHub approach may be taken where deployments from finished feature branches occur and after successfully running in production such feature branches are then merged to master.

The workflow for Services is based upon each release creating a new semantic version and the git tagging of it. Continuous deployment off master is encouraged but is limited to how compatible API and the client libraries are against the HEAD code in the master branch – code that is released and deployed must work together. Instead of the optional stable branch, optional versioned branches may exist. These are created lazy from the release tag when the need for a bug fix on any previous release arises, or when master code no longer provides compatibility to the released artifacts currently in use. The latter case highlights the change when deployments start to occur off the versioned branch instead of off master. Bug fix branches are taken from the versioned branch, and forward ported to master.

Similar to Services are Libraries. These are artifacts that have no server-side code. They are standalone code artifacts serving as compile-time dependencies to the platform. A Library is released, but never itself deployed to any environment. Libraries are void of any efforts towards continuous deployments but otherwise follow very similar workflow as Services – typically they give longer support to older versions and therefore have multiple release branches active.

How any team operates their workflow is up to them, free to experiment to see what is effective. At the end of the day as long as you understand the differences between [merge](http://git-scm.com/book/en/Git-Branching-Basic-Branching-and-Merging) and [rebase](http://git-scm.com/book/en/Git-Branching-Rebasing) then evolving from one workflow to another over time shouldn't be a problem.




### Infrastructure: Atlassian Stash


The introduction of Git was stalled for a year from our Ops team as there was no repository management software they were happy enough with to support (integration with existing services was important, particularly Crowd). Initially they were waiting on either Gitolite or Gitorius. Eventually someone suggested Stash from Atlassian and after a quick trial this was to be it. We're using a number of Atlassian products already: Jira, Fisheye, Crucible, and Confluence; so the integration factor was good and so we've paid for a product that was at the time incredibly overpriced with next to nothing on its feature list. One feature the otherwise very naked Stash comes with is _Projects_, which provides a basic grouping of repositories. We've used this grouping to organise our repositories based on our  architectural layers: "applications", "services", "libraries", and "operations". The idea is not to build fortresses with projects based on teams but to best please the outsider who is looking for some yet unknown codebase and only knows what _type_ of codebase it is. We're hoping that Atlassian adds [labels](https://jira.atlassian.com/browse/STASH-3217) and [descriptions](https://jira.atlassian.com/browse/STASH-3216) to repositories to further help organisation. Permissions is easy, full read and write access to everyone, we'll learn quickest when we're all free to make mistakes, it's all under version control at the end of the day.










[![Atlassian Stash](http://www.atlassian.com/software/stash/overview/feature-overview/featureItems/00/imageBinary/stashtour_highlight_builtforenterprise.png)](http://www.atlassian.com/software/stash/)










### We're still a cathedral


Git decentralises everything, but we're not a real bazaar: our private code is our cathedral with typical enterprise trends like scrum and kanban in play; and so we have still the need to centralise a lot.
Our list of users and roles we still want centralised, when people push to the master repository are all commits logged against known users or are we going to end up with multiple aliases for every developer? Or worse junk users like "localhost"?
To tackle this we wrote a pre-push hook that authenticates usernames for all commits against [Crowd](http://www.atlassian.com/software/crowd/overview). If a commit from an unknown user is encountered the push fails and the pusher needs to fix their history using this [recipe](https://gist.github.com/carlosmcevilly/2221249) before pushing again.

Releases can be made off any clone and obviously not be something we want. Released artifacts need to be permanent and unique, and deployed to our central maven repository. Maven's release plugin fortunately tackles this for us as when you run `mvn release:prepare` or `mvn release:branch` it automatically pushes resulting changes upstream for you, as dictated by the scm details in the pom.xml




### Migrating repositories


Our practice with subversion was to have everything in one large subversion repository, like how Apache does [it](http://svn.apache.org/repos/asf/). This approach worked best for us allowing projects and the code across projects to be freely moved around. With Git it makes more sense for each project to have its own repository, as moving files along with their history between repositories is easy.

Initial attempts of conversion were using svn2git as described [here](http://veys.com/2010/07/24/migrating-multi-project-subversion-repositories-to-git/) along with [svndumpfilter3](http://furius.ca/pubcode/pub/conf/bin/svndumpfilter3.html).

But a plugin in Stash came along called [SubGit](http://subgit.com/stash/). It rocks! Converting individual projects from such a large subversion repository one at a time is easy. Remember to moderate the .gitattributes file afterwards, we found in most usecases it could be deleted.





### Hurdles


**Integration with our existing tools** (bamboo, fisheye, jira) was easier when everything was in one subversion repository. Now with scores of git repositories it is rather cumbersome. Every new git repository has to be added manually into every other tool. We're hoping that Atlassian comes to the rescue and provides some sort of [automatic recognition](https://jira.atlassian.com/browse/STASH-2589) of new and renamed repositories.

**Renaming** repositories in Stash is very easy, and should be encouraged in an agile world, but it breaks the integration with other tools. Every repository rename means going through other tools and manually updating them. Again we hope Atlassian comes to the rescue.


**Binary files** we were worried about as our largest codebase had many and was already slow on subversion due to it. Subversion also stores all xml files by default as binary and in a large spring based application with a long history this might have been a problem. We were ready to investigate solutions like [git-annex](http://git-annex.branchable.com/). All test migrations though showed that it was not a problem, git clones of this large codebase were super fast, and considerably smaller (subversion 4.1G -> git 1.1G).





### Adaptation


Towards the end of February we were lucky enough to have [Tim Berglund](http://twitter.com/tlberglund), [Brent Beer](http://twitter.com/brntbeer), and [David Graham](http://twitter.com/davidgraham), from GitHub come and [teach](http://teach.github.com/presentations/git-foundations.html#/) us Git. The first two days was a set course with 75 participants and covered

  * Git Fundamentals (staging, moving, copying, history, branching, merging, resetting, reverting),
  * Collaboration using GitHub (Push, pull, and fetch, Pull Requests Project Sites, Gists, Post-receive hooks), and
  * Advanced Git (Filter-Branch, Bisect, Rebase-onto, External merge/diff tools, Event Hooks, Refspec, .gitattributes).

The third day with the three GitHubbers was more of an open space with under twenty participants where we discussed various [specifics](https://gist.github.com/jarib/184945f80373474943a8) to FINN's adoption to Git, from continuous deployment (which GitHub excels at) to branching workflows.

 No doubt about it this was one of the best, if not very best, courses held for FINN developers, and left everyone with a resounding drive to immediately switch all codebases over to Git.

Other documentation that's encouraged for everyone to read/watch is

  * [The entire Pro Git book](http://git-scm.com/book),
  * [Advanced Git](http://vimeo.com/49444883) video from Tim Berglund,
  * [The Flow Of Change](http://www.youtube.com/watch?v=AJ-CpGsCpM0) video from Google (covers engineering principles of branching codebases).



![](/wp-content/uploads/2013/03/timberglund.jpg)





### Tips and tricks for beginners…


To wrap it up here's some of the tips and tricks we've documented for ourselves…  

**Cant push because HEAD is newer**
So you pull first… Then you go ahead and push which adds two commits into history: the original and a duplicate merge from you.
You need to learn to do `git rebase` in such situations, better yet to do `git --rebase pull`.
You can make the latter permanent behaviour with `
    git config --global branch.master.rebase true
    git config --global branch.autosetuprebase always`

**Colour please!**
`git config --global color.ui auto`

**Did you really want to push commits on all your branches?**
This can trap people, they often expect push to be restricted to the current branch your on. It can be enforced to be this way with `git config --global push.default tracking`

**Pretty log display**
Alias `git lol` to your preferred log format…
Simple oneline log formatting:
`git config --global alias.lol "log --abbrev-commit --graph --decorate --all --pretty=oneline"`

Oneline log formatting including committer's name and relative times for each commit:
`git config --global alias.lol "log --abbrev-commit --graph --all
--pretty=format:'%Cred%h%Creset -%C(yellow)%d%Creset %s %Cgreen(%cr) %C(bold blue)<%an>%Creset'"`

Compact log formatting with full commit messages, iso timestamps, file history over renames, and mailmap usernames:
`git config --global alias.lol "log --follow --find-copies-harder --graph  --abbrev=4
--pretty=format:'%Cred%h%Creset -%C(yellow)%d%Creset %Cgreen%ai %n %C(bold blue)%aN%Creset  %B'"`

**Cherry-picking referencing the original commit**
When recording a cherry-pick commit, using the "-x" option appends the line _"(cherry picked from commit ...)"_ to the original commit message so to indicate which commit this change was cherry-picked from. For example `git cherry-pick -x 3523dfc`

**Quick log to see what i've done in the last 24 hours?**
`git config --global alias.standup "log --all --since yesterday --author `git config --global user.email`"`

**What files is this project got but ignoring?**
`git config --global alias.ignored "ls-files --others --i --exclude-standard"`

**Wipe all uncommitted changes**
`git config --global alias.wipe "reset --hard HEAD"`

**Edit and squash commits before pushing them**
`git config --global alias.ready "rebase -i @{u}"`




