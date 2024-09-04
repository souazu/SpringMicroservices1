package io.javabrains.moviecatalogservice.resources;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import io.javabrains.moviecatalogservice.models.CatalogItem;
import io.javabrains.moviecatalogservice.models.Movie;
import io.javabrains.moviecatalogservice.models.Rating;
import io.javabrains.moviecatalogservice.models.UserRating;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private WebClient.Builder webClientBuilder;
	
	@RequestMapping("/{userId}")

	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){
		
		//Get all the rated movie ids
		UserRating userRating = getRatingService(userId);
		List<Rating> ratings = userRating.getUserRatings();
		//For each movie id, call the movie info service and get details.
		return ratings
				.stream()
				.map(rating->{
					return getMovieInfo(rating);
				})
				.collect(Collectors.toList());
		
		
		//put them all together
		
		//return Arrays.asList(new CatalogItem("Titanic","Titanic Movie",10),
		//		new CatalogItem("Transformers","Transformers Movie",7),new CatalogItem("Virus","Virus Movie",9));
	}
	@HystrixCommand(fallbackMethod = "getFallbackCatalogItem")
	private CatalogItem getMovieInfo(Rating rating) {
		Movie movie = restTemplate.getForObject("http://movie-info-service/movies/"+rating.getMovieId(), Movie.class); 
		return new CatalogItem(movie.getName(),"Malayalam movie",rating.getRating());
	}
	@HystrixCommand(fallbackMethod = "getFallbackUserRating")
	private UserRating getRatingService(String userId) {
		return restTemplate.getForObject("http://ratings-data-service/ratingsdata/users/"+userId, UserRating.class);
	}
	
	public UserRating getFallbackUserRating(@PathVariable("userId") String userId) {
		UserRating userRating = new UserRating();
		userRating.setUserId(userId);
		userRating.setUserRatings(Arrays.asList(new Rating("no ratings found",0)));
				
		return userRating;
	}
	public List<CatalogItem> getFallbackCatalogItem(@PathVariable("userId") String userId){
		return Arrays.asList(new CatalogItem("No movies  found","",2));
	}
	
}
