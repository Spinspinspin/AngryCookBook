package com.libertymutual.goforcode.angrycb.api;

import java.util.List;

import org.aspectj.apache.bcel.generic.Instruction;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.libertymutual.goforcode.angrycb.models.Ingredients;
import com.libertymutual.goforcode.angrycb.models.Instructions;
import com.libertymutual.goforcode.angrycb.models.Recipe;
import com.libertymutual.goforcode.angrycb.repositories.IngredientRepository;
import com.libertymutual.goforcode.angrycb.repositories.InstructionRepository;
import com.libertymutual.goforcode.angrycb.repositories.RecipeRepository;

import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping("/api/recipes")

public class CookBookApiController {
	
	private RecipeRepository recipeRepo;
	private IngredientRepository ingredientRepo;
	private InstructionRepository instructionRepo;
	
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

	@PostMapping("")
	public Recipe create(@RequestBody Recipe recipe) {
		return recipeRepo.save(recipe);
	}
	
	@ApiOperation(value="Delete a recipe by ID.")
    @DeleteMapping("{id}")
    public Recipe deleteRecipe(@PathVariable long id) {
        try{
            Recipe recipe = recipeRepo.findOne(id);
            recipeRepo.delete(id);
            return recipe;
        } catch (EmptyResultDataAccessException erdae) {
            return null;
        }
    }
	
	@PutMapping("{id}")
	public Recipe update(@RequestBody Recipe recipe, @PathVariable long id) {
		recipe.setId(id);
		return recipeRepo.save(recipe);
	}
	
	@PostMapping("{ingredientId}/recipe")
	public Ingredients associateAnIngredient(@PathVariable long ingredientId, @RequestBody Ingredients ingredient) {
		Recipe recipe = recipeRepo.findOne(ingredientId);
		ingredient = ingredientRepo.findOne(ingredient.getId());
		recipe.addIngredients(ingredient);
		recipeRepo.save(recipe);
		
		return ingredient;
	}
	
	@PostMapping("{instructionId}/recipe")
	public Instructions associateAnInstruction(@PathVariable long instructionId, @RequestBody Instructions instruction) {
		Recipe recipe = recipeRepo.findOne(instructionId);
		instruction = instructionRepo.findOne(instruction.getId());
		recipe.addInstructions(instruction);
		recipeRepo.save(recipe);
		
		return instruction;
	}
	
	@ApiOperation(value="Delete an ingredient by ID.")
	 @DeleteMapping("{ingredientId}")
	    public Recipe deleteIngredient(@PathVariable long ingredientId) {
	        try{
	            Recipe recipe = recipeRepo.findOne(ingredientId);
	            recipeRepo.delete(ingredientId);
	            return recipe;
	        } catch (EmptyResultDataAccessException erdae) {
	            return null;
	        }
	    }

	@ApiOperation(value="Delete an instruction by ID.")
	 @DeleteMapping("{instructionId}")
	    public Recipe deleteInstruction(@PathVariable long instructionId) {
	        try{
	            Recipe recipe = recipeRepo.findOne(instructionId);
	            recipeRepo.delete(instructionId);
	            return recipe;
	        } catch (EmptyResultDataAccessException erdae) {
	            return null;
	        }
	    }
}
