package diet;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a recipe of the diet.
 * 
 * A recipe consists of a a set of ingredients that are given amounts of raw materials.
 * The overall nutritional values of a recipe can be computed
 * on the basis of the ingredients' values and are expressed per 100g
 * 
 *
 */
public class Recipe implements NutritionalElement {
	final static int PORTION = 100;
	private static class Ingredient {
		final NutritionalElement ne;
		final double qty;
		Ingredient(NutritionalElement e, double q) {
			this.ne=e; this.qty=q;
		}
	}
	
	private String name;
	private Food food;
	private List<Ingredient> ingredientsList;
	private double weight;
	
	public Recipe(String name, Food food) {
		this.name = name;
		this.food = food;
		ingredientsList = new ArrayList<Ingredient>();
		weight = 0.0;
	}
	
	/**
	 * Adds the given quantity of an ingredient to the recipe.
	 * The ingredient is a raw material.
	 * 
	 * @param material the name of the raw material to be used as ingredient
	 * @param quantity the amount in grams of the raw material to be used
	 * @return the same Recipe object, it allows method chaining.
	 */
	public Recipe addIngredient(String material, double quantity) {
		NutritionalElement ne = food.getRawMaterial(material);
		Ingredient in = new Ingredient(ne, quantity);
		ingredientsList.add(in);
		weight += quantity;
		return this;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public double getCalories() {
		double result=0.0;
		for(Ingredient i : ingredientsList) {
			result += i.ne.getCalories()*i.qty / PORTION;
		}
		return (result*PORTION/weight);
	}
	
	@Override
	public double getProteins() {
		double result=0.0;
		for(Ingredient i : ingredientsList) {
			result += i.ne.getProteins()*i.qty / PORTION;
		}
		return (result*PORTION/weight);
	}

	@Override
	public double getCarbs() {
		double result=0.0;
		for(Ingredient i : ingredientsList) {
			result += i.ne.getCarbs()*i.qty / PORTION;
		}
		return (result*PORTION/weight);
	}

	@Override
	public double getFat() {
		double result=0.0;
		for(Ingredient i : ingredientsList) {
			result += i.ne.getFat()*i.qty / PORTION;
		}
		return (result*PORTION/weight);
	}

	/**
	 * Indicates whether the nutritional values returned by the other methods
	 * refer to a conventional 100g quantity of nutritional element,
	 * or to a unit of element.
	 * 
	 * For the {@link Recipe} class it must always return {@code true}:
	 * a recipe expresses nutritional values per 100g
	 * 
	 * @return boolean indicator
	 */
	@Override
	public boolean per100g() {
		return true;
	}
}
