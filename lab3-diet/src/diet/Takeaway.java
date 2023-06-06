package diet;

import java.util.*;


/**
 * Represents a takeaway restaurant chain.
 * It allows managing restaurants, customers, and orders.
 */
public class Takeaway {
	Food food;
	private SortedMap<String, Restaurant> restaurants;
	private List<Customer> customers;
	
	/**
	 * Constructor
	 * @param food the reference {@link Food} object with materials and products info.
	 */
	public Takeaway(Food food){
		this.food = food;
		restaurants = new TreeMap<String, Restaurant>();
		customers = new ArrayList<>();
	}

	/**
	 * Creates a new restaurant with a given name
	 *
	 * @param restaurantName name of the restaurant
	 * @return the new restaurant
	 */
	public Restaurant addRestaurant(String restaurantName) {
		Restaurant r = new Restaurant(restaurantName, food);
		restaurants.put(restaurantName, r);
		return r;
	}

	/**
	 * Retrieves the names of all restaurants
	 *
	 * @return collection of restaurant names
	 */
	public Collection<String> restaurants() {
		return restaurants.keySet();
	}

	/**
	 * Creates a new customer for the takeaway
	 * @param firstName first name of the customer
	 * @param lastName	last name of the customer
	 * @param email		email of the customer
	 * @param phoneNumber mobile phone number
	 *
	 * @return the object representing the newly created customer
	 */
	public Customer registerCustomer(String firstName, String lastName, String email, String phoneNumber) {
		Customer c = new Customer(firstName, lastName, email, phoneNumber);
		customers.add(c);
		return c;
	}

	/**
	 * Retrieves all registered customers
	 *
	 * @return sorted collection of customers
	 */
	public Collection<Customer> customers(){
		List<Customer> c = new ArrayList<>(customers);
		c.sort(Comparator.comparing(Customer::getLastName).thenComparing(Customer::getFirstName));
		return c;
	}

	/**
	 * Creates a new order for the chain.
	 *
	 * @param customer		 customer issuing the order
	 * @param restaurantName name of the restaurant that will take the order
	 * @param time	time of desired delivery
	 * @return order object
	 */
	public Order createOrder(Customer customer, String restaurantName, String time) {
		Restaurant r = restaurants.get(restaurantName);
		Order o;
		if (time.length()<5) {
			StringBuilder sb = new StringBuilder(time);
		    sb.insert(0, '0');
		    time = sb.toString();
		}
		if (!r.isOpenAt(time)) {
			String nextTime = new String(r.getNextAvailableTime(time));
			o = new Order(customer, restaurantName, nextTime);
		} else {
			o = new Order(customer, restaurantName, time);
		}
		r.addOrder(o);
		return o;
	}

	/**
	 * Find all restaurants that are open at a given time.
	 *
	 * @param time the time with format {@code "HH:MM"}
	 * @return the sorted collection of restaurants
	 */
	public Collection<Restaurant> openRestaurants(String time){
		Collection<Restaurant> openRestaurantList = new ArrayList<>();
		Restaurant[] restaurantArray = restaurants.values().toArray(Restaurant[]::new);
		
		for (Restaurant r : restaurantArray) {
			if (r.isOpenAt(time)) {
				openRestaurantList.add(r);
			}
		}
		return openRestaurantList;
	}
	
}
