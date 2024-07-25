Spring Microservices

What is the difference between microservices and web services?
Take an example of a utility service such as an IP service - give an IP address as input and return the location of the IP address.
The makers of such a utility service did not think where exactly this service would be used. It was built and whoever wants to use it, could use it in their application.
In the case of microservices, services are built for a specific reason. For example, in an e-commerce website, creating a shopping cart could be a microservice. They are not developed as a utility, but more for the need of such a service.

Creating a spring boot application
- Using maven and adding the required spring dependencies
- Using spring CLI
- Using start.spring.io

How to make a REST call from your code?
- Calling REST API's programmatically
- Using REST client library
- Spring Boot already comes with a client already in your classpath - RestTemplate

@Bean annotation - Producer - Remember it as anyone with this annotation is producing the instance of that class
@Autowired annotation - Consumer - Consumer of the class instance that is already available. 

Why should lists be avoided in the response of an API?
Suppose, along with the list, if we wish to return another attribute at a later point in time, then we will need to wrap the list in an object along with the new attribute, This changes the signature of the API response.








