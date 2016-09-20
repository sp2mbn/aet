![Cognifide logo](http://cognifide.github.io/images/cognifide-logo.png)

[![Build Status](https://travis-ci.org/Cognifide/aet.svg?branch=master)](https://travis-ci.org/Cognifide/aet)
[![Apache License, Version 2.0, January 2004](https://img.shields.io/github/license/cognifide/aet.svg?label=License)](http://www.apache.org/licenses/)

# Automated Exploratory Tests
<p align="center">
  <img src="misc/img/aet-logo-black.png" alt="AET Logo"/>
</p>

AET (acronym formed from **A**utomated **E**xploratory **T**esting) is a system that detects changes on web sites.
AET is designed as a flexible system that can be adapted and tailored to the regression requirements of a given project.
The tool has been developed to aid front end client side layout regression testing of websites or portfolios. 
In essence assessing the impact or change of a website from one snapshot to the next.

## What's philosophy behind AET?
AET helps testers to ensure that a change in one part of the software has not introduced new defects in other parts of the software.

#### AET can be most useful for
* testing large websites or large portfolios using common components in a CMS,
* unit testing custom components for a CMS project,
* regression tests at the end of an Hourly/Daily/Weekly/Per Sprint Completion,
* as part of an upgrade or migration of infrastructure / platform migration.

#### A typical scenario of use
1. The AET user (Developer or QA) baselines a set of components or pages with URLs as an input to the tool.
2. The CMS user changes the page component or content.
3. The ‘current baseline’ is used to compare with the ‘new version’ and the change is assessed for one of the 3 possibilities:
  * There are no changes - no involvement required.
  * There is a change but the user accepts it, which means she/he re-baselines.
  * There is a change and the user does not accept it, so she/he has to fix it.
4. AET produces a report

## What tools does AET consist of?
*AET* uses several tools and frameworks that are used to check page quality in following areas:

* Full page **screenshots comparison** using Firefox browser to render page and Selenium to capture screenshots.
    * Hiding Page Items by xpath,
    * Changing screen resolution (width/height setup),
* Compare page **sources**.
* Compare page source **W3C compliance** using [nu.validator](https://validator.w3.org/nu/).
* Check **js errors** with [JSErrorCollector](https://github.com/mguillem/JSErrorCollector).
* Check **status codes** using [BrowserMob Proxy](https://bmp.lightbody.net/).
* Check page **accessibility** with [HTML_CodeSniffer](http://squizlabs.github.io/HTML_CodeSniffer/).
* Check and modify page **cookies**.
* Check page **client side performance** using [YSlow](http://yslow.org/).

## AET System
The AET System consists of 7 units:

- Client (AET Maven Plugin)
- Runner cluster
- Worker cluster
- JMS Server
- Database
- REST API
- Reports web application

![aet-architecture](misc/img/aet-architecture.png)

Thanks to using AET Maven Plugin as a Client application, AET is easy to integrate with CI Tools like Jenkins or Bamboo.

## How to start
Please see our [AET in 10 minutes](https://github.com/Cognifide/aet/wiki/AETIn10Minutes) guide to start using AET.

To run **AET** tests the following tools are required:

- Java 8
- Maven 3.3.1 or newer
- Chrome Browser to preview AET reports

## Setup
Please refer to the [Setup Guide](https://github.com/Cognifide/aet/wiki/BasicSetup) in the documentation for an overview on how to configure AET.

## License
**AET** is licensed under [Apache License, Version 2.0 (the "License")](https://www.apache.org/licenses/LICENSE-2.0.txt)

## Roadmap

- Selenium Grid
- Microservices Architecture
- Developer Guide
- Report UX improvements
- More browsers support

## Dependencies

- org.seleniumhq.selenium
- net.lightbody.bmp
- com.google.inject
- net.jsourcerer.webdriver
- google-diff-match-patch
- com.github.detro
- nu.validator
- yslow.org
- HTML_CodeSniffer

## Documentation
* [AET Wiki](https://github.com/Cognifide/aet/wiki)