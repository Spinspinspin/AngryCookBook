package com.libertymutual.goforcode.angrycb.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.libertymutual.goforcode.angrycb.models.Ingredients;

import org.springframework.stereotype.Repository;

@Repository

public interface IngredientRepository extends JpaRepository<Ingredients, Long> {
	
	List<Ingredients> findByRecipeId(long id);
}
