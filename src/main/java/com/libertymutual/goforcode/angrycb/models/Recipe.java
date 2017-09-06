package com.libertymutual.goforcode.angrycb.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.aspectj.apache.bcel.generic.Instruction;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")

@Entity

public class Recipe {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false, length = 75)
	private String title;

	@Column(nullable = true, length = 255)
	private String description;

	@Column(nullable = true)
	private double numberOfMinutes;

	@Column(nullable = true)
	private String ingredients;

	@Column(nullable = true, length = 255)
	private String instructions;

	@ManyToMany
	private List<Recipe> recipes;

	public Recipe() {
	}

	public Recipe(String title, String description, double numberOfMinutes, String ingredients, String instructions) {
		this.title = title;
		this.description = description;
		this.numberOfMinutes = numberOfMinutes;
		this.ingredients = ingredients;
		this.instructions = instructions;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getdescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public double numberOfMinutes() {
		return numberOfMinutes;
	}

	public void setNumberOfMinutes(double numberOfMinutes) {
		this.numberOfMinutes = numberOfMinutes;
	}
	
	public String getIngredients() {
		return ingredients;
	}

	public void setIngredients(String ingredients) {
		this.ingredients = ingredients;
	}
	
	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	public void addIngredients(Ingredients ingredient) {
		// TODO Auto-generated method stub
		
	}

	public void addInstructions(Instructions instruction) {
		// TODO Auto-generated method stub
		
	}
}
























