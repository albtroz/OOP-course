package diet;

import diet.Order.OrderStatus;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Comparator;

/**
 * Represents a restaurant class with given opening times and a set of menus.
 */
public class Restaurant {
	private static class WorkingHour {
		final String openingTime;
	    final String closingTime;
	    final int openingTimeH;
	    final int openingTimeM;
	    final int closingTimeH;
	    final int closingTimeM;
		
		public WorkingHour(String o, String c) {		
	        String[] openingTimeArray = o.split(":");
	        String[] closingTimeArray = c.split(":");
	        openingTimeH = Integer.parseInt(openingTimeArray[0]);
	        openingTimeM = Integer.parseInt(openingTimeArray[1]);
	        closingTimeH = Integer.parseInt(closingTimeArray[0]);
	        closingTimeM = Integer.parseInt(closingTimeArray[1]);
	        
	        StringBuffer sbO = new StringBuffer();
	        StringBuffer sbC = new StringBuffer();
	        if (openingTimeH < 10) {
	        	sbO.append("0");
	        }
	        sbO.append(openingTimeH);
	        sbO.append(":");
	        if (openingTimeM < 10) {
	        	sbO.append("0");
	        }
	        sbO.append(openingTimeM);
	        
	        
	        
	        if (closingTimeH < 10) {
	        	sbC.append("0");
	        }
	        sbC.append(closingTimeH);
	        sbC.append(":");
	        if (closingTimeM < 10) {
	        	sbC.append("0");
	        }
	        sbC.append(closingTimeM);
	        
	        openingTime = sbO.toString();
	        closingTime = sbC.toString();
	        
	        //System.out.println(openingTime+" - "+closingTime);
		}
	}
	private String name;
	private SortedMap<String, WorkingHour> timeTable;
	private Map<String, Menu> menusList;
	private List<Order> orders;
	
	public Restaurant(String name, Food food) {
		this.name = name;
		timeTable = new TreeMap<>();
		menusList = new HashMap<>();
		orders = new ArrayList<>();
	}
	
	public String getNextAvailableTime(String time) {
		WorkingHour[] timeTableArray = timeTable.values().toArray(WorkingHour[]::new);
		WorkingHour curr = timeTableArray[0];
		
		if (timeTable.size() > 1) {
			String[] timeArray = time.split(":");
	        int timeH = Integer.parseInt(timeArray[0]);
	        int timeM = Integer.parseInt(timeArray[1]);
	     
			WorkingHour next = null;
			for(int i = 1; i < timeTableArray.length; i++) {
				next = timeTableArray[i];
				if ((timeH > curr.closingTimeH) || (timeH == curr.closingTimeH && timeM > curr.closingTimeM)) {
					if ((timeH < next.openingTimeH) || (timeH == next.openingTimeH && timeM > next.openingTimeM)) {
						return new String(next.openingTime);
					}
				}
				curr = next;
			}
		}
		return (new String(timeTableArray[0].openingTime));
	}
	
	/**
	 * retrieves the name of the restaurant.
	 *
	 * @return name of the restaurant
	 */
	public String getName() {
		return name;
	}

	/**
	 * Define opening times.
	 * Accepts an array of strings (even number of elements) in the format {@code "HH:MM"},
	 * so that the closing hours follow the opening hours
	 * (e.g., for a restaurant opened from 8:15 until 14:00 and from 19:00 until 00:00,
	 * arguments would be {@code "08:15", "14:00", "19:00", "00:00"}).
	 *
	 * @param hm sequence of opening and closing times
	 */
	public void setHours(String ... hm) {
		for (int i = 0; i < hm.length/2; i++) {
			timeTable.put(hm[2*i], new WorkingHour(hm[2*i], hm[2*i+1]));
		}
	}

	/**
	 * Checks whether the restaurant is open at the given time.
	 *
	 * @param time time to check
	 * @return {@code true} is the restaurant is open at that time
	 */
	public boolean isOpenAt(String time){
		String[] timeArray = time.split(":");
        int timeH = Integer.parseInt(timeArray[0]);
        int timeM = Integer.parseInt(timeArray[1]);
        WorkingHour[] timeTableArray = timeTable.values().toArray(WorkingHour[]::new);
		
		for (WorkingHour wh : timeTableArray) {
			if (wh.closingTimeH - wh.openingTimeH < 0) {
				if ((timeH > wh.openingTimeH) || (timeH == wh.openingTimeH && timeM >= wh.openingTimeM)) {
					return true;
				} 
				if ((timeH < wh.closingTimeH) || (timeH == wh.closingTimeH && timeM < wh.closingTimeM)) {
					return true;
				}
			} else {
				if (((timeH > wh.openingTimeH) || (timeH == wh.openingTimeH && timeM >= wh.openingTimeM))&& 
						((timeH < wh.closingTimeH) || (timeH == wh.closingTimeH && timeM < wh.closingTimeM))) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Adds a menu to the list of menus offered by the restaurant
	 *
	 * @param menu	the menu
	 */
	public void addMenu(Menu menu) {
		menusList.put(menu.getName(), menu);
	}

	/**
	 * Gets the restaurant menu with the given name
	 *
	 * @param name	name of the required menu
	 * @return menu with the given name
	 */
	public Menu getMenu(String name) {
		return menusList.get(name);
	}
	
	public void addOrder(Order order) {
		orders.add(order);
	}

	/**
	 * Retrieve all order with a given status with all the relative details in text format.
	 *
	 * @param status the status to be matched
	 * @return textual representation of orders
	 */
	public String ordersWithStatus(OrderStatus status) {
		Order[] ordersArray = orders.toArray(Order[]::new);
		Arrays.sort(ordersArray, Comparator.comparing(Order::getRestaurantName).
				thenComparing(Order::getCustomerFullname).
				thenComparing(Order::getDeliveryTime));
		StringBuffer sb = new StringBuffer("");
		
		for (Order o : ordersArray) {
			if (o.getStatus() == status) {
				sb.append(o.toString());
			}
		}
		return sb.toString();
	}
}
