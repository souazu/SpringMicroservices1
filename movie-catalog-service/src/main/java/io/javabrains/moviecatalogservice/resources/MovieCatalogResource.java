package io.javabrains.moviecatalogservice.resources;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.javabrains.moviecatalogservice.models.CatalogItem;
import io.javabrains.moviecatalogservice.models.Movie;
import io.javabrains.moviecatalogservice.models.Rating;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
	
	@RequestMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){
		
		RestTemplate restTemplate = new RestTemplate();
		
		//Get all the rated movie ids
		List<Rating> ratings = Arrays.asList(new Rating("1234",4),new Rating("5678",7));
		//For each movie id, call the movie info service and get details.
		return ratings
				.stream()
				.map(rating->{
					Movie movie = restTemplate.getForObject("http://localhost:8082/movies/"+rating.getMovieId(), Movie.class); 
					return new CatalogItem(movie.getName(),"Malayalam movie",rating.getRating());
				})
				.collect(Collectors.toList());
		
		
		//put them all together
		
		//return Arrays.asList(new CatalogItem("Titanic","Titanic Movie",10),
		//		new CatalogItem("Transformers","Transformers Movie",7),new CatalogItem("Virus","Virus Movie",9));
	}
}
