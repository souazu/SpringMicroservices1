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
