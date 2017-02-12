FROM ubuntu:14.04
MAINTAINER katooshka

RUN apt-get update && apt-get install -y python-software-properties software-properties-common
RUN add-apt-repository ppa:webupd8team/java
RUN echo "oracle-java8-installer shared/accepted-oracle-license-v1-1 boolean true" | debconf-set-selections
RUN apt-get update && apt-get install -y oracle-java8-installer maven
RUN apt-get install -y git
RUN git clone https://github.com/katooshka/hacker_news_scraper/
RUN cd hacker_news_scraper && mvn clean package

CMD ["hacker_news_scraper/hackernews", "--posts", "100"]

