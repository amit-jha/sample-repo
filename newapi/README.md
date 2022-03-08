### Prerequisites:
1. Java 11
2. SpringBoot 2.6.3


### Deployment Instruction
1. Config Server 
   ```
   docker run -h mylocalhost --ip 172.20.0.2 -p 4040:4040 --rm --network=newsapi newsapi-configserver
    ```
2. News API
    ```
    docker run -p 8080:8080 --rm --network=newsapi newsapi
    ```

3. Jenkins (Bind with docker)

    ```
   docker run --rm --name jenkins -p 8080:8080 -p 50000:50000 -d -v /Users/amijha0/docker/volumes/jenkins:/var/jenkins_home -v /var/run/docker.sock:/var/run/docker.sock jenkins/jenkins:lts-jdk11
    ```


#####References:
https://stackoverflow.com/questions/35466423/how-to-set-docker-container-hostname-ip-permanently
https://mermaid-js.github.io/mermaid/#/
https://plantuml.com/
https://cidr.xyz/

##### Docker setting

        #Issue: Container are not  getting inter connected over localhost
        #Solution: Connect them through IP 
        1. Create a network with CIDR 
        docker network create --subnet=172.20.0.0/16 newsapi
        2. Create container inside network
        docker run --network newsapi --ip 172.20.0.2 -it ubuntu bash

