package com.libertymutual.goforcode.angrycb.api;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;


import java.util.ArrayList;
import java.util.List;

import org.aspectj.apache.bcel.generic.Instruction;
import org.junit.Before;
import org.junit.Test;
import org.meanbean.test.BeanTester;
import org.springframework.dao.EmptyResultDataAccessException;

import com.libertymutual.goforcode.angrycb.models.Ingredients;
import com.libertymutual.goforcode.angrycb.models.Instructions;
import com.libertymutual.goforcode.angrycb.models.Recipe;
import com.libertymutual.goforcode.angrycb.repositories.IngredientRepository;
import com.libertymutual.goforcode.angrycb.repositories.InstructionRepository;
import com.libertymutual.goforcode.angrycb.repositories.RecipeRepository;


public class CookBookApiControllerTests {

	private RecipeRepository recipeRepo;
	private IngredientRepository ingredientRepo;
	private InstructionRepository instructionRepo;
	private CookBookApiController controller;
	

	@Before
	public void setUp() {
		recipeRepo = mock(RecipeRepository.class);
		ingredientRepo = mock(IngredientRepository.class);
		instructionRepo = mock(InstructionRepository.class);
		controller = new CookBookApiController(recipeRepo, ingredientRepo, instructionRepo);
	}

	@Test
	public void test_getAll_returns_all_recipes_returned_by_the_repo() {
		// Arrange
		ArrayList<Recipe> recipes = new ArrayList<Recipe>();
		recipes.add(new Recipe());
		recipes.add(new Recipe());

		when(recipeRepo.findAll()).thenReturn(recipes);

		// Act
		List<Recipe> actual = controller.getAll();

		// Asserts
		assertThat(actual.size()).isEqualTo(2);
		assertThat(actual.get(1)).isSameAs(recipes.get(1));
		verify(recipeRepo).findAll();
	}

	@Test
	public void test_getOne_returns_Recipe_returned_from_repo() throws ItemNotFoundException {
		// Arrange
		Recipe roastBeef = new Recipe();
		when(recipeRepo.findOne(7L)).thenReturn(roastBeef);

		// Act
		Recipe actual = controller.getOne(7L);

		// Assert
		assertThat(actual).isSameAs(roastBeef);
		verify(recipeRepo).findOne(7L);
	}

	@Test
	public void test_getOne_throws_ItemNotFound_when_no_Recipe_returned_from_repo() {
		try {
			controller.getOne(1);
			fail("The controller did not throw the ItemNotFoundException");

		} catch (ItemNotFoundException infe) {
		}
	}

	@Test
	public void test_create_returns_recipe_and_updates_recipeRepo() {
		// Arrange
		Recipe newRecipe = new Recipe();
		when(recipeRepo.save(newRecipe)).thenReturn(newRecipe);

		// Act
		Recipe actual = controller.create(newRecipe);

		// Assert
		assertThat(newRecipe).isSameAs(actual);

	}

	@Test
	public void test_delete_returns_recipe_deleted_when_found() {
		// Arrange
		Recipe recipe = new Recipe();
		when(recipeRepo.findOne(7L)).thenReturn(recipe);

		// Act
		Recipe actual = controller.deleteRecipe(7L);

		// Assert
		assertThat(recipe).isSameAs(actual);
		verify(recipeRepo).delete(7L);
		verify(recipeRepo).findOne(7L);
	}

	@Test
	public void test_that_null_is_returned_when_findOne_throws_EmptyResultDataAccessException()
			throws ItemNotFoundException {
		// arrange
		when(recipeRepo.findOne(7L)).thenThrow(new EmptyResultDataAccessException(0));

		// Act
		Recipe actual = controller.deleteRecipe(7L);

		// Assert
		assertThat(actual).isNull();
		verify(recipeRepo).findOne(7L);

	}

	@Test
	public void test_update_upates_recipeRepo_and_returns_updated_recipe() {
		// Arrange
		Recipe updateRecipe = new Recipe();

		when(recipeRepo.save(updateRecipe)).thenReturn(updateRecipe);

		// Act
		Recipe actual = controller.update(updateRecipe, 7L);

		// Assert
		assertThat(updateRecipe).isSameAs(actual);

	}
	
	@Test
	public void test_getOneIngredients_returns_Ingredient_returned_from_repo() throws ItemNotFoundException {
		// Arrange
		Recipe roastBeef = new Recipe();
		Ingredients beef = new Ingredients();
		when(recipeRepo.findOne(7L)).thenReturn(roastBeef);
		when(ingredientRepo.findOne(8L)).thenReturn(beef);
		
		// Act
		Ingredients actual = controller.getOneIngredient(8L);
		

		// Assert
		assertThat(actual).isSameAs(beef);
		
	}
	
	@Test
	public void test_getOneInstruction_returns_Recipe_returned_from_repo() throws ItemNotFoundException {
		// Arrange
		Recipe roastBeef = new Recipe();
		Instructions stepOne = new Instructions();
		when(recipeRepo.findOne(7L)).thenReturn(roastBeef);
		when(instructionRepo.findOne(8L)).thenReturn(stepOne);
		
		// Act
		Instructions actual = controller.getOneInstruction(8L);

		// Assert
		assertThat(actual).isSameAs(stepOne);
		
	}
	
	@Test
	public void test_getAllIngredients_returns_list_of_all_ingredients_for_specific_recipe_by_id() throws ItemNotFoundException {
		
		// Arrange
		ArrayList<Ingredients> stuff = new ArrayList<Ingredients>();
		stuff.add(new Ingredients());
		stuff.add(new Ingredients());
		when(ingredientRepo.findByRecipeId(8L)).thenReturn(stuff);
		
		// Act
		List<Ingredients> actual = controller.getIngredientsByRecipeId(8L);

		// Assert
		
		assertThat(actual.size()).isEqualTo(2);
		assertThat(actual.get(1)).isSameAs(stuff.get(1));
		verify(ingredientRepo).findByRecipeId(8L);
	}
	
	@Test
	public void test_getAllInstructionss_returns_list_of_all_instructions_for_specific_recipe_by_id() throws ItemNotFoundException {
		
		// Arrange
		ArrayList<Instructions> steps = new ArrayList<Instructions>();
		steps.add(new Instructions());
		steps.add(new Instructions());
		when(instructionRepo.findByRecipeId(8L)).thenReturn(steps);
		
		// Act
		List<Instructions> actual = controller.getInstructionsByRecipeId(8L);

		// Assert
		
		assertThat(actual.size()).isEqualTo(2);
		assertThat(actual.get(1)).isSameAs(steps.get(1));
		verify(instructionRepo).findByRecipeId(8L);
	}
	
	@Test
	public void test_getOne_returns_Recipe_and_getOne_returns_Ingredient_and_add_adds_the_ingredient_to_the_recipe_and_save_updates_recipeRepo() throws ItemNotFoundException {
		// Arrange
		Recipe taco = new Recipe();
		Ingredients beef = new Ingredients();
		when(recipeRepo.findOne(8L)).thenReturn(taco);
		when(ingredientRepo.findOne(7L)).thenReturn(beef);
		
		
		
		
		//Act
		Recipe actual = controller.createAnIngredient(8L, beef);
		
		//Assert
		assertThat(actual).isSameAs(taco);
//		verify(recipeRepo).findOne(8L);
//		verify(ingredientRepo).findOne(7L);
//		assertThat(taco.getIngredients()).contains(beef);
//		verify(recipeRepo).save(taco);
		
		} 
	
	@Test
	public void test_getOne_returns_Recipe_and_getOne_returns_Instruction_and_add_adds_the_instruction_to_the_recipe_and_save_updates_recipeRepo() throws ItemNotFoundException {
		// Arrange
		Recipe taco = new Recipe();
		Instructions stepOne = new Instructions();
		when(recipeRepo.findOne(8L)).thenReturn(taco);
		when(instructionRepo.findOne(7L)).thenReturn(stepOne);
		
		
		
		
		//Act
		Recipe actual = controller.createAnInstruction(8L, stepOne);
		
		//Assert
		assertThat(actual).isSameAs(taco);

		
		} 
	@Test
    public void test_all_gets_and_sets_for_Recipe() {
        new BeanTester().testBean(Recipe.class);
    }
	
	@Test
    public void test_all_gets_and_sets_for_Ingredients() {
        new BeanTester().testBean(Ingredients.class);
    }
	
	@Test
    public void test_all_gets_and_sets_for_Instructions() {
        new BeanTester().testBean(Instructions.class);
    }
    
    @Test
    public void test_delete_returns_ingredient_deleted_when_found() {
        // Arrange
    	Ingredients ingredient = new Ingredients();
        when(ingredientRepo.findOne(10L)).thenReturn(ingredient);
        // Act
        Ingredients actual = controller.deleteIngredient(10L); 
        // Assert
        assertThat(actual).isSameAs(actual);
        verify(ingredientRepo).delete(10L);
        verify(ingredientRepo).findOne(10L);
    }
    
    @Test
    public void test_that_null_is_returned_when_delete_ingredient_throws_EmptyResultDataAccessException()
            throws ItemNotFoundException {
        // arrange
        when(ingredientRepo.findOne(13L)).thenThrow(new EmptyResultDataAccessException(0));
        // Act
        Ingredients actual = controller.deleteIngredient(13L);
        // Assert
        assertThat(actual).isNull();
        verify(ingredientRepo).findOne(13L);
    }
    
    @Test
    public void test_delete_returns_instruction_deleted_when_found() {
        // Arrange
        Instructions instruction = new Instructions();
        when(instructionRepo.findOne(7L)).thenReturn(instruction);
        // Act
        Instructions actual = controller.deleteInstruction(7L); 
        // Assert
        assertThat(actual).isSameAs(actual);
        verify(instructionRepo).delete(7L);
        verify(instructionRepo).findOne(7L);
    }
    
    @Test
    public void test_that_null_is_returned_when_delete_instruction_throws_EmptyResultDataAccessException()
            throws ItemNotFoundException {
        // arrange
        when(instructionRepo.findOne(9L)).thenThrow(new EmptyResultDataAccessException(0));
        // Act
        Instructions actual = controller.deleteInstruction(9L);
        // Assert
        assertThat(actual).isNull();
        verify(instructionRepo).findOne(9L);
    }
	
}

