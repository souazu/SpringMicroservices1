package io.javabrains.moviecatalogservice.resources;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.javabrains.moviecatalogservice.models.CatalogItem;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
	
	@RequestMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){
		return Arrays.asList(new CatalogItem("Titanic","Titanic Movie",10),
				new CatalogItem("Transformers","Transformers Movie",7),new CatalogItem("Virus","Virus Movie",9));
	}
}
