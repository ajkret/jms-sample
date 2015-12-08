# jms-sample
JMS Sample using ActiveMQ. There are two projects:
*jms-producer: It is the client application, runs on spring-boot and enables an POST endpoint to post messages
*jms-consumer: Has the Service class, which consumes messages from the producer. The messages will be printed on the console.

The application depends on ActiveMQ to run. Just download it and run. Then run in two different consoles:

java -jar producer.war

java -jar consumer-jar-with-dependencies.war
 


