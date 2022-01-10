# BruinCreates SpringBoot Server

[![Build Status](https://travis-ci.org/codecentric/springboot-sample-app.svg?branch=master)](https://travis-ci.org/codecentric/springboot-sample-app)
[![Coverage Status](https://coveralls.io/repos/github/codecentric/springboot-sample-app/badge.svg?branch=master)](https://coveralls.io/github/codecentric/springboot-sample-app?branch=master)
[![License](http://img.shields.io/:license-apache-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)

BruinCreates is an E-commerce shopping site for visual art students at UCLA. 

## Requirements

For building and running the application you need:

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 3.8.3](https://maven.apache.org)
- [MySql 8.0.25](https://dev.mysql.com/doc/relnotes/mysql/8.0/en/news-8-0-25.html)
- [Redis 6.2.6](https://redis.io/)
- [Elasticsearch 7.1.6](https://www.elastic.co/)

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `com.bruincreates.server.ServerApplication` class from your IDE.

```shell
# make sure project dependencies are connect
$ mvn clean install

# set up redis
$ wget https://download.redis.io/releases/redis-6.2.6.tar.gz
$ tar xzf redis-6.2.6.tar.gz
$ cd redis-6.2.6
$ make
$ src/redis-server
$ src/redis-cli

# set up mysql
mysql --host=localhost --user=root --password=123456 bruincreates
```

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```

## Test the application locally
This project is secured using Spring Security and Spring Session Redis. Authentication is needed before accessing the server, for example:

```shell
# register user
request: post
body: json
{
  "username" : "test",
  "password" : "test",
  "profileName" : "test",
  "email" : "some_real_email@gmail.com"
}
url: localhost:8080/api/account/register

# login user
request: post
body: form-data
username:test
password:test

# try sample query api
request: get
url: localhost:8080/api/test
```

## Copyright

Released under the Apache License 2.0. See the [LICENSE](https://github.com/codecentric/springboot-sample-app/blob/master/LICENSE) file.
