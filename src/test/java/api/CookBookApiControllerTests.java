package api;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;


import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.EmptyResultDataAccessException;

import com.libertymutual.goforcode.angrycb.api.CookBookApiController;
import com.libertymutual.goforcode.angrycb.api.ItemNotFoundException;
import com.libertymutual.goforcode.angrycb.models.Recipe;
import com.libertymutual.goforcode.angrycb.repositories.RecipeRepository;

public class CookBookApiControllerTests {

	private RecipeRepository recipeRepo;
	private CookBookApiController controller;

	@Before
	public void setUp() {
		recipeRepo = mock(RecipeRepository.class);
		controller = new CookBookApiController(recipeRepo);
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

}
