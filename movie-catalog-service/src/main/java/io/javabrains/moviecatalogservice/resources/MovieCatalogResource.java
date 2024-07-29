package io.javabrains.moviecatalogservice.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

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
		UserRating userRating = restTemplate.getForObject("http://ratings-data-service/ratingsdata/users/"+userId, UserRating.class);
		List<Rating> ratings = userRating.getUserRatings();
		//For each movie id, call the movie info service and get details.
		return ratings
				.stream()
				.map(rating->{
					Movie movie = restTemplate.getForObject("http://movie-info-service/movies/"+rating.getMovieId(), Movie.class); 
					
					// Using WebClient Builder
					/*Movie movie = webClientBuilder.build()
						.get()
						.uri("http://localhost:8082/movies/"+rating.getMovieId())
						.retrieve()
						.bodyToMono(Movie.class)
						.block();
					*/
					return new CatalogItem(movie.getName(),"Malayalam movie",rating.getRating());
				})
				.collect(Collectors.toList());
		
		
		//put them all together
		
		//return Arrays.asList(new CatalogItem("Titanic","Titanic Movie",10),
		//		new CatalogItem("Transformers","Transformers Movie",7),new CatalogItem("Virus","Virus Movie",9));
	}
}
