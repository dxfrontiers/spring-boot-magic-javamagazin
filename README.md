# Spring Boot AutoConfiguration Examples
> This repository contains the code examples demonstrated in a [Java Magazin ](https://kiosk.entwickler.de/java-magazin/) article, published in issue 08.2020


## Structure

The repository contains a maven multi-module project, with the following modules:

Module | Description
----------|------------
`spring-factories-exploration` | Explores the `SpringFactoriesLoader` in conjunction with the spring boot autoconfiguration concept
`util` | Some generic utilities used throughout the codebase. These are solely used to make examples easier to read, or provide a nicer console output.
`example` | Contains the example application, that has been enriched using spring boot autoconfiguration. This module depends on the `mina` module
`mina` | This is a meta-module that itself has two submodules: `mina-autoconfigure` and `mina-sshd-spring-boot-starter`.

------
Do you have any questions or suggestions? Get in touch with us:

![digital frontiers](doc/img/logo_250x75.png)

:globe_with_meridians: [https://www.digitalfrontiers.de](https://www.digitalfrontiers.de) \
:email: info@digitalfrontiers.de \
Twitter [@dxfrontiers](https://twitter.com/dxfrontiers)


