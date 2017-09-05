package com.libertymutual.goforcode.angrycb.repositories;

import org.springframework.stereotype.Repository;

import com.libertymutual.goforcode.angrycb.models.Recipe;

import org.springframework.data.jpa.repository.JpaRepository;


@Repository

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

}
