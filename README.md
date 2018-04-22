[![Build Status](https://api.travis-ci.org/writeonly/scalare.svg?branch=master)](https://travis-ci.org/writeonly/scalare)
[![Codacy grade](https://img.shields.io/codacy/grade/e27821fb6289410b8f58338c7e0bc686.svg)](https://www.codacy.com/app/writeonly/scalare/dashboard)
[![Codecov](https://img.shields.io/codecov/c/github/writeonly/scalare.svg)](https://codecov.io/gh/writeonly/scalare)
[![GitHub issues](https://img.shields.io/github/issues/writeonly/scalare.svg)](https://github.com/writeonly/scalare/issues)
[![License][licenseImg]][licenseLink]
 
[licenseImg]: https://img.shields.io/github/license/writeonly/scalare.svg
[licenseImg2]: https://img.shields.io/:license-mit-blue.svg
[licenseLink]: LICENSE

# scalare #

## Build & Run ##

```sh
$ cd scalare
$ sbt
> jetty:start
> browse
```

If `browse` doesn't launch your browser, manually open [http://localhost:8080/](http://localhost:8080/) in your browser.

### WebServer
 ```
sbt "project drop" "run server"
```
