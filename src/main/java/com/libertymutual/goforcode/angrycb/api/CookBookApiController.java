package com.libertymutual.goforcode.angrycb.api;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.libertymutual.goforcode.angrycb.models.Recipe;
import com.libertymutual.goforcode.angrycb.repositories.RecipeRepository;


@RestController
@RequestMapping("/api/recipes")

public class CookBookApiController {
	
	private RecipeRepository recipeRepo;
	
	public CookBookApiController(RecipeRepository repository) {
        this.recipeRepo = recipeRepo;
	}
	
	@GetMapping("")
	public List<Recipe> getAll() {
		return recipeRepo.findAll();
	}
	
	@GetMapping ("{id}")
	public Recipe getOne(@PathVariable long id) throws ItemNotFoundException {
		Recipe recpie = recipeRepo.findOne(id);
		if (recpie == null) {
			throw new ItemNotFoundException();
		}
		return recpie;
		}

    
	
}
