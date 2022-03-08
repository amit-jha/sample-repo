FROM jenkinsci/blueocean

USER root

RUN wget -O /tmp/apache-maven-3.8.4-bin.tar.gz https://dlcdn.apache.org/maven/maven-3/3.8.4/binaries/apache-maven-3.8.4-bin.tar.gz

RUN tar -zxvf /tmp/apache-maven-3.8.4-bin.tar.gz -C /opt/

RUN ln -s /opt/apache-maven-3.8.4 /opt/maven

RUN rm -rf /tmp/apache-maven-3.8.4-bin.tar.gz

ENV MAVEN_HOME=/opt/maven

ENV PATH=${MAVEN_HOME}/bin:${PATH}

RUN chown -R jenkins:jenkins /opt/maven

USER jenkins