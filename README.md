# spring-boot-aws
A simple example of how to deploy a docker image to AWS elastic container service

http://localhost/8080/echo?message=test

### Run Standalone
```
mvn clean install spring-boot:run
```

### Run In Docker Locally
Build the image
``` 
 Sending build context to Docker daemon  19.41MB
 Step 1/7 : FROM openjdk:8u212-jdk-alpine
  ---> a3562aa0b991
 Step 2/7 : LABEL maintainer="john.dobie@ecs.co.uk"
  ---> Using cache
  ---> 44b0a1cd1876
 Step 3/7 : VOLUME /tmp
  ---> Using cache
  ---> 487893eed320
 Step 4/7 : EXPOSE 8080
  ---> Using cache
  ---> 77c955bfd0dc
 Step 5/7 : ARG JAR_FILE=target/*.jar
  ---> Using cache
  ---> f27a9ac993d5
 Step 6/7 : ADD ${JAR_FILE} echo.jar
  ---> Using cache
  ---> 61704f9e8aa5
 Step 7/7 : ENTRYPOINT ["java","-jar","/echo.jar"]
  ---> Using cache
  ---> 52b221836c76
 Successfully built 52b221836c76
 Successfully tagged johndobie/ecs-spring-boot-echo:latest
```

Run the image locally
```
 docker run -p 8080:8080 -t "johndobie/ecs-spring-boot-echo"    
```

## Push to Dockerhub
Login to DockerHub
```
>docker login -u johndobie
Password:
Login Succeeded
```

Push to DockerHub
```
>docker push johndobie/ecs-spring-boot-echo
The push refers to repository [docker.io/johndobie/ecs-spring-boot-echo]
The push refers to repository [docker.io/johndobie/ecs-spring-boot-echo]
7a1572ef2719: Pushed
ceaf9e1ebef5: Pushed
9b9b7f3d56a0: Pushed
f1b5933fe4b5: Pushed
latest: digest: sha256:8ff6f8504247393a78c89c40a76fcacf83920e860b5db7620611a6d365e507c2 size: 1159
```

## Create Cluster

https://eu-west-2.console.aws.amazon.com/ecs/home?region=eu-west-2#/clusters

Choose cluster type
 
```
Cluster Type : EC2 Linux + Networking
```

Populate the following and leave everything else as default.
```
Cluster Name : echo-cluster
EC2 Instance Type : t3.micro
Number of Instances : 2
```

This will go away and create a new cluster.

### Create a task in AWS.
https://eu-west-2.console.aws.amazon.com/ecs/home?region=eu-west-2#/taskDefinitions

Next create a task definition which will pull and run your image.
Click on "create new task Definition" and choose EC2.

```
__Task Defintion Name__ : ecs-spring-boot-echo-task
__Requires Compatabilities__ : EC2
__Task memory__ : 256
__Task CPU Unit__ :  1 vCpu
```

#### Add Container
Add the details of the image and the port mappings below before saving.
```
* Container name : ecs-spring-boot-echo-container
* Image : johndobie/ecs-spring-boot-echo:latest
```

The mapping is used to forward the public port 80 to the Microservice port 8080.
```
__Port Mappings__
* Host Port 80
* Container Port : 8080
```

### Run Task
Go to the created task and click on the dropdown to "Run task"
First switch the launch type to EC2
 
Then fill in the following details.
``` 
Cluster : echo-cluster
Number of Tasks : 2
``` 

https://eu-west-2.console.aws.amazon.com/ecs/home?region=eu-west-2#/clusters/echo-cluster/tasks

The task view will show the tasks running.
You can click on one of the tasks and see the details.

#### Test Endpoint
Click on the ECS instances tab and then click on an individual instance.

You can then use the public DNS to test the service is running.
```
Echo Cluster Instances
Public DNS:
ec2-3-8-77-18.eu-west-2.compute.amazonaws.com/echo?message=test
```