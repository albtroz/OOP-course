package social;

import java.util.TreeSet;

public class Group {
	private String name;
	private TreeSet<Person> subscribers;
	
	public Group(String name) {
		this.name = name;
		subscribers = new TreeSet<>();
	}
	
	public String getName() {
		return name;
	}
	
	public void addSubscriber(Person p) {
		subscribers.add(p);
	}
	
	public TreeSet<Person> getSubscribers() {
		return subscribers;
	}
	
}
