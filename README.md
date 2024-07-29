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
When using RestTemplate, we need to specify the class name to return the response from the webservice. 
- UserRating userRating = restTemplate.getForObject("http://localhost:8083/users/"+userId, UserRating.class);
In case of ArrayList, the conversion to get the list of objects is more complicated than a plain object containing the list.
Hence, as a practice, it is better to return an object in the response contract of the web service instead of a List.

Service Discovery
In this project there are three microservices:
- movie info service
- catalog service
- ratings service

If a client needs to get services of a particular microservice  such as the catalog service, the client needs to know the URL of the catalog service.
Service Discovery server is a lookup server where microservices are registered in the discovery server.

So, when client wants to use the catalog service, 
- client makes a call to the service discovery server. 
- discovery server returns the url of the catalog service.
- client then makes the call to the catalog service.

This is referred to as the client-side discovery server

Server-side discovery.
- Client makes a call to the discovery server requesting for catalog service
- Discovery server forwards the client's request to the catalog service.

Spring cloud has the client side discovery server.

Eureka discovery server has the default port number of 8791.
When catalog or movie info services include eureka client libraries, it tries to register to the eureka server with port number 8791. 
When the Eureka server is running and catalogue or movie info services are started, they are automatically registered to the discovery server using the port and they get listed as clients in the eureka server portal - http://localhost:8761/

@LoadBalanced annotation
The @LoadBalanced annotation creates an instance of created RestTemplate load-balanced. There is no code you need to write to make the RestTemplate load-balance HTTP request it sends to an internal microservice. The RestTemplate bean will be intercepted and auto-configured by Spring Cloud.

SUMMARY:
1. Created three microservices
2. Added code using RestTemplate so that the three microservices communicate with each other.
3. Added discovery server
4. Added client side dicovery libraries to the three microservices.
5. Added LoadBalanced annotation and replaced the hardcoded urls with application names of the microservices in code so that hard coding can be removed.












