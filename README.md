# hacker_news_scraper

Simple command line application that outputs to STDOUT top Hacker News posts in JSON.

## Install

You can use command line:

```sudo add-apt-repository ppa:webupd8team/java -y
sudo apt-get update
sudo apt-get install oracle-java8-installer
sudo apt-get install oracle-java8-set-default
sudo apt-get install maven
sudo apt-get install git
git clone https://github.com/katooshka/hacker_news_scraper/
cd hacker_news_scraper
mvn clean
mvn package
./hackernews —posts <posts counter>
```


Or you can create a new container from the Dockerfile.

## Used libraries

JUnit - a unit testing framework for Java. Mostly used among other testing frameworks for this language.

Mockito - a mocking framework for testing in Java. Clean and simple.

javax.json - an object model API to process JSON in Java. One of three most commonly used frameworks for JSON processing in Java. 

