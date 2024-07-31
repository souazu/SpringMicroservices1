Microservices with Spring Boot Level 2
Fault Tolerance and Resilience

Agenda
- Understand challenges with availability
- Making microservices fault-tolerant and resilient

Fault Tolerance - When there is a fault in the application, what is the impact of the fault on the application? How tolerant is the application? For example, if a part of a microservice goes down, what would get impacted?

Resilience - How many faults can a system tolerate before the system can completely go down? Is there a mechanism the system can come back to a normal state?

What happens if a microservice goes down?
If an instance of a microservice goes down, other instances of the same microservice can take up the request. 

What happens if a microservice is slow?
When a request comes to a web server, a thread is spawned to fulfil the request.
Once the request is fulfilled, the thread dies.
When another request comes to a web server, a new thread is spawned. 
When multiple requests are coming in fast, the web server goes slow because the threshold limit of the number of threads to be created would have been reached. The threads holding the older requests need to be completed and freed before new threads can cater to new requests.

Imagine microservice A calls B and then calls microservice C in that sequence. Now, if microservice B calls microservice D internally and if that call is taking time, then even if the call from microservice A to microservice C takes lesser time, the request to microservice A goes slow.  This is because the thread fulfilling the request is waiting for the microservice D to finish. 

Solution for microservice going slow:
1) Adding timeouts - Setting timeout for RestTemplate
   Applying timeout partially solves the problem. For example, we set the timeout at 3 seconds. And requests come at each second. The number of threads getting cleared is still lower than the threads that need to be created because of new requests.

Circuit Breaker Pattern
1) Detect something is wrong - such as some microservice is taking too long to respond
2) Take temporary steps to handle the situation
3) Deactivate the problem component so that it does not affect the downstream component





