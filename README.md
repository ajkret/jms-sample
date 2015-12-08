# jms-sample
JMS Sample using ActiveMQ. There are two projects:
- jms-producer: It is the client application, runs on spring-boot and enables an POST endpoint to post messages
- jms-consumer: Has the Service class, which consumes messages from the producer. The messages will be printed on the console.

The application depends on ActiveMQ to run. Just download it and run. Then run in two different consoles:

    java -jar producer.war

    java -jar consumer-jar-with-dependencies.war
 
To test the application using the endpoint, POST a message with a _text/plain_ payload to 

    http://localhost:8080/greet/send

I recommend using Advanced Rest Client, a plugin for Google Chrome to POST messages.
