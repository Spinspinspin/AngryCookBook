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
	
	public CookBookApiController(RecipeRepository recipeRepo, IngredientRepository ingredientRepo, InstructionRepository instructionRepo) {
        this.recipeRepo = recipeRepo;
        this.ingredientRepo = ingredientRepo;
        this.instructionRepo = instructionRepo;
	}
	@ApiOperation(value="Return a list of recipes.")
	@GetMapping("")
	public List<Recipe> getAll() {
		return recipeRepo.findAll();
	}
	
	@ApiOperation(value="Return a list of ingredients for a specific recipe.")
    @GetMapping("/{id}/ingredients")
    public List<Ingredients> getAllIngredients() {
        return ingredientRepo.findAll();
    }
    
    @ApiOperation(value="Return a list of instructions for a specific recipe.")
    @GetMapping("/{id}/instructions")
    public List<Instructions> getAllInstructions() {
        return instructionRepo.findAll();
    }
	
	@ApiOperation(value="Get a specfic recipe by ID.")
	@GetMapping ("{id}")
	public Recipe getOne(@PathVariable long id) throws ItemNotFoundException {
		Recipe recipe = recipeRepo.findOne(id);
		if (recipe == null) {
			throw new ItemNotFoundException();
		}
		return recipe;
		}
	
	@ApiOperation(value="Get a specific instruction by instructionId.")
	@GetMapping("/{id}/instructions/{instructionId}")
	public Recipe getOneInstruction(@PathVariable long instructionId) throws ItemNotFoundException{
		Recipe recipe = recipeRepo.findOne(instructionId);
		if (recipe == null) {
			throw new ItemNotFoundException();
		}
		return recipe;
	}
	
	@ApiOperation(value="Get a specific ingredient by ingredientId.")
	@GetMapping("/{id}/ingredients/{ingredientId}")
	public Recipe getOneIngredients(@PathVariable long ingredientId) throws ItemNotFoundException{
		Recipe recipe = recipeRepo.findOne(ingredientId);
		if (recipe == null) {
			throw new ItemNotFoundException();
		}
		return recipe;
	}
	
	
	@ApiOperation(value="Create a new recipe")
	@PostMapping("")
	public Recipe create(@RequestBody Recipe recipe) {
		Recipe newRecipe = new Recipe(recipe.getTitle(), recipe.getdescription(), recipe.getNumberOfMinutes(), recipe.getIngredients(), recipe.getInstructions());
		recipeRepo.save(recipe);
				
		return recipe;
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
	
	@ApiOperation(value="Update a recipe by ID.")
	@PutMapping("{id}")
	public Recipe update(@RequestBody Recipe recipe, @PathVariable long id) {
		recipe.setId(id);
		return recipeRepo.save(recipe);
	}
		
	@ApiOperation(value="Add an ingredient to a recipe.")
	@PostMapping("{id}/ingredients")
	public Recipe createAnIngredient(@PathVariable long id, @RequestBody Ingredients ingredient) {
		Ingredients newIngredient = new Ingredients(ingredient.getFoodItem(), ingredient.getMeasureUnit(), ingredient.getQuantity());
		Recipe recipe = recipeRepo.findOne(id);
		newIngredient.setRecipe(recipe);
		ingredientRepo.save(newIngredient);
		
		return recipe;
	}
	
	@ApiOperation(value="Add an instruction to a recipe.")
	@PostMapping("{id}/instructions")
	public Recipe createAnInstruction(@PathVariable long id, @RequestBody Instructions instruction) {
		Instructions newInstruction = new Instructions(instruction.getStep());
		Recipe recipe = recipeRepo.findOne(id);
		newInstruction.setRecipe(recipe);
		instructionRepo.save(newInstruction);
		
		return recipe;
	}
	
	@ApiOperation(value="Delete an ingredient by ID.")
	 @DeleteMapping("/{id}/ingredients/{ingredientId}")
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
	 @DeleteMapping("/{id}/instructions/{instructionId}")
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
