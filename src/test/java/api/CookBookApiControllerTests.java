package api;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.libertymutual.goforcode.angrycb.api.CookBookApiController;
import com.libertymutual.goforcode.angrycb.models.Recipe;
import com.libertymutual.goforcode.angrycb.repositories.RecipeRepository;

public class RecipeApiControllerTests {

	private RecipeRepository recipeRepo;
	private CookBookApiController controller;
	
	@Before
	public void setUp() {
		recipeRepo = mock(RecipeRepository.class);
		controller = new CookBookApiController(recipeRepo);
	}

	@Test
	public void test_getAll_returns_all_recipes_returned_by_the_repo() {
		//Arrange
		ArrayList<Recipe> recipes = new ArrayList<Recipe>();
		recipes.add(new Recipe());
		recipes.add(new Recipe());
		
		
		when(recipeRepo.findAll()).thenReturn(recipes);
		
		
		
		//Act
		List<Recipe> actual = controller.getAll();
		
		//Asserts
		assertThat(actual.size()).isEqualTo(2);	
		assertThat(actual.get(1)).isSameAs(recipes.get(1));
		verify(recipeRepo).findAll();
	}
	
	
	
}
