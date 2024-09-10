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
======================================================================================
Circuit Breaker Pattern
1) Detect something is wrong - such as some microservice is taking too long to respond
2) Take temporary steps to handle the situation
3) Deactivate the problem component so that it does not affect the downstream component

Circuit Breaker Parameters
When should the trigger be applied to deactivate the problem-causing component?
- Last n requests to consider the decision.
- How many requests should be failed? Sometimes in the last 5 requests, 2 might have succeeded and the rest might have failed. So, on what number of failure requests, should the component be deactivated?
- Timeout duration - How much time should be considered before we mark it as a failure?

When should the component be activated?
How long to wait before the circuit goes back to normal?

Example of circuit breaker parameters:
Last n requests to consider for the decision: 5 - Check the last 5 requests to decide whether to deactivate the problem component
How many of those should fail: 3 - In the last 5 requests, if 3 requests fail or show timeout, deactivate the problem component. If less than 3 fail, keep sending the requests.
Timeout duration: 2 s - Mark a request as a failure if it takes more than 2 s.
How long to wait (sleep window): 10 s - When a circuit breaks, wait for 10s before sending new requests.

1st request: 300 ms
2nd request: 3s
3rd request: 300 ms
4th request: 3s
5th request: 4s

In the above sequence, 3 requests have taken more time to process. So, according to the example above, the circuit can be tripped. In other words, a sleep window of 10s can be triggered and no new requests can be entertained during this time. After 10 s, the circuit can be re-tripped to receive new requests.

The duration of the sleep window is dependent on the number of requests coming in and the number of threads in the thread pool. It is more on a trial-and-error basis before we come to a number.

What to do when a circuit breaks?
- Throw an error - Not the best option.
- Return a fallback (default) response - Much better than the error response option
- Save previous responses in a cache and use them when possible - The best option

Why circuit breakers?
- Failing fast
- Fallback functionality 
- Automatic recovery - Sleep timeout

Circuit Breaker Pattern

When to break circuit | What to do when circuit breaks | When to resume requests
================================================================================================================

Hystrix
- Open source library originally  developed by Netflix
- Implements the circuit breaker pattern
- Give the configuration parameters such as when to trip the circuit, etc
- Works well with Spring Boot

Adding Hystrix to a Spring Boot microservice
- Adding the dependency spring-cloud-starter-netflix-hystrix in Maven.
- Add the annotation - @EnableCircuitBreaker to the application class.
- Add @HystrixCommand to methods that need circuit breakers
- Configure Hystrix behaviour

How does Hystrix work?
Imagine an API class and a method in that class. Imagine this method is annotated with @HystrixCommand. 
When this happens, the API class is covered with a proxy class. 
This proxy class will contain the circuit breaker logic.
So, when a request is taking time, the fallback method is invoked by this wrapper class.
















