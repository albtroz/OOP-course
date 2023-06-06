package diet;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a complete menu.
 * 
 * It can be made up of both packaged products and servings of given recipes.
 *
 */
public class Menu implements NutritionalElement {
	final static int PORTION = 100;
	private static class Dish {
		final NutritionalElement r;
		final double qty;
		Dish(NutritionalElement r, double q) {
			this.r=r; this.qty=q;
		}
	}
	
	private String name;
	private Food food;
	private List<Dish> dishesList;
	private List<NutritionalElement> productsList;
	
	public Menu(String name, Food food) {
		this.name = name;
		this.food = food;
		dishesList = new ArrayList<Dish>();
		productsList = new ArrayList<NutritionalElement>();
	}
	
	/**
	 * Adds a given serving size of a recipe.
	 * The recipe is a name of a recipe defined in the {@code food}
	 * argument of the constructor.
	 * 
	 * @param recipe the name of the recipe to be used as ingredient
	 * @param quantity the amount in grams of the recipe to be used
	 * @return the same Menu to allow method chaining
	 */
    public Menu addRecipe(String recipe, double quantity) {
    	NutritionalElement r = food.getRecipe(recipe);
    	Dish d = new Dish(r, quantity);
    	dishesList.add(d);
		return this;
	}

	/**
	 * Adds a unit of a packaged product.
	 * The product is a name of a product defined in the {@code food}
	 * argument of the constructor.
	 * 
	 * @param product the name of the product to be used as ingredient
	 * @return the same Menu to allow method chaining
	 */
    public Menu addProduct(String product) {
    	NutritionalElement p = food.getProduct(product);
    	productsList.add(p);
		return this;
	}

	@Override
	public String getName() {
		return name;
	}

	/**
	 * Total KCal in the menu
	 */
	@Override
	public double getCalories() {
		double result=0.0;
		for(Dish d : dishesList) {
			result += d.r.getCalories()*d.qty/PORTION;
		}
		for(NutritionalElement p : productsList) {
			result += p.getCalories();
		}
		return result;
	}

	/**
	 * Total proteins in the menu
	 */
	@Override
	public double getProteins() {
		double result=0.0;
		for(Dish d : dishesList) {
			result += d.r.getProteins()*d.qty/PORTION;
		}
		for(NutritionalElement p : productsList) {
			result += p.getProteins();
		}
		return result;
	}

	/**
	 * Total carbs in the menu
	 */
	@Override
	public double getCarbs() {
		double result=0.0;
		for(Dish d : dishesList) {
			result += d.r.getCarbs()*d.qty/PORTION;
		}
		for(NutritionalElement p : productsList) {
			result += p.getCarbs();
		}
		return result;
	}

	/**
	 * Total fats in the menu
	 */
	@Override
	public double getFat() {
		double result=0.0;
		for(Dish d : dishesList) {
			result += d.r.getFat()*d.qty/PORTION;
		}
		for(NutritionalElement p : productsList) {
			result += p.getFat();
		}
		return result;
	}

	/**
	 * Indicates whether the nutritional values returned by the other methods
	 * refer to a conventional 100g quantity of nutritional element,
	 * or to a unit of element.
	 * 
	 * For the {@link Menu} class it must always return {@code false}:
	 * nutritional values are provided for the whole menu.
	 * 
	 * @return boolean indicator
	 */
	@Override
	public boolean per100g() {
		return false;
	}
}