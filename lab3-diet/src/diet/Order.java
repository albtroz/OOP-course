package diet;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Represents and order issued by an {@link Customer} for a {@link Restaurant}.
 *
 * When an order is printed to a string is should look like:
 * <pre>
 *  RESTAURANT_NAME, USER_FIRST_NAME USER_LAST_NAME : DELIVERY(HH:MM):
 *  	MENU_NAME_1->MENU_QUANTITY_1
 *  	...
 *  	MENU_NAME_k->MENU_QUANTITY_k
 * </pre>
 */
public class Order {
	/**
	 * Possible order statuses
	 */
	public enum OrderStatus {
		ORDERED, READY, DELIVERED
	}

	/**
	 * Accepted payment methods
	 */
	public enum PaymentMethod {
		PAID, CASH, CARD
	}
	
	private Customer customer;
	private String restaurantName;
	private String deliveryTime;
	private OrderStatus os;
	private PaymentMethod pm;
	private SortedMap<String, Integer> menus;
	
	public Order(Customer customer, String restaurantName, String time) {
		this.customer = customer;
		this.restaurantName = restaurantName;
		deliveryTime = time;
		os = OrderStatus.ORDERED;
		pm = PaymentMethod.CASH;
		menus = new TreeMap<String, Integer>();
		
	}
	
	public String getCustomerFullname() {
		return customer.toString();
	}
	
	
	public String getRestaurantName() {
		return restaurantName;
	}
	
	public String getDeliveryTime() {
		return deliveryTime;
	}

	/**
	 * Set payment method
	 * @param pm the payment method
	 */
	public void setPaymentMethod(PaymentMethod pm) {
		this.pm = pm;
	}

	/**
	 * Retrieves current payment method
	 * @return the current method
	 */
	public PaymentMethod getPaymentMethod() {
		return pm;
	}

	/**
	 * Set the new status for the order
	 * @param os new status
	 */
	public void setStatus(OrderStatus os) {
		this.os = os;
	}

	/**
	 * Retrieves the current status of the order
	 *
	 * @return current status
	 */
	public OrderStatus getStatus() {
		return os;
	}

	/**
	 * Add a new menu to the order with a given quantity
	 *
	 * @param menu	menu to be added
	 * @param quantity quantity
	 * @return the order itself (allows method chaining)
	 */
	public Order addMenus(String menu, int quantity) {
		menus.put(menu, quantity);
		return this;
	}
	
	private static String menusList(SortedMap<String, Integer> menus) {
		StringBuffer sb = new StringBuffer("");
		menus.forEach((x,y)->sb.append(x+"->"+y+'\n'+'\t'));
		sb.delete(sb.length()-2, sb.length());
		return sb.toString();
	}
	
	@Override
	public String toString() {
		return(restaurantName+", "+customer.toString()+" : ("+deliveryTime+"):"+'\n'+
			   (menus.isEmpty() ? "" : '\t'+menusList(menus))+'\n');
	}
	
}
