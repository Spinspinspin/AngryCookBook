package com.libertymutual.goforcode.angrycb.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "ingredientId")
@Entity

public class Ingredients {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ingredient_id_seq")
	@SequenceGenerator(name = "ingredient_id_seq", sequenceName = "ingredient_id_seq")
	private Long ingredientId;

	@Column(nullable = false, length = 255)
	private String foodItem;

	@Column(nullable = true, length = 255)
	private String measureUnit;

	@Column(nullable = true, length = 255)
	private String quantity;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JsonIgnore
	private Recipe recipe;

	public Ingredients() {
	}

	public Ingredients(String foodItem, String measureUnit, String quantity) {

		this.foodItem = foodItem;
		this.measureUnit = measureUnit;
		this.quantity = quantity;
	}

	public String getFoodItem() {
		return foodItem;
	}

	public void setFoodItem(String foodItem) {
		this.foodItem = foodItem;
	}

	public String getMeasureUnit() {
		return measureUnit;
	}

	public void setMeasureUnit(String measureUnit) {
		this.measureUnit = measureUnit;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public Long getIngredientId() {
		return ingredientId;
	}

	public void setIngredientId(Long ingredientId) {
		this.ingredientId = ingredientId;
	}

	public Recipe getRecipe() {
		return recipe;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}

}
