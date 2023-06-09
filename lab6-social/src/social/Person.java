package social;

import java.util.TreeSet;

public class Person implements Comparable<Person>{
	private String name;
	private String surname;
	private String code;
	private TreeSet<Person> friends;
	private TreeSet<Group> groups;
	
	public Person(String code, String name, String surname) {
		this.code = code;
		this.name = name;
		this.surname = surname;
		friends = new TreeSet<>();
		groups = new TreeSet<>();
	}
	
	public String getCode() {
		return code;
	}
	
	public String getName() {
		return name;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public String getInfo() {
		return new String(code+" "+name+" "+surname);
	}
	
	public void addFriend(Person p) {
		friends.add(p);
	}
	
	public TreeSet<Person> getFriend() {
		return friends;
	}
	
	public void addSbuscription(Group g) {
		groups.add(g);
	}
	
	public TreeSet<Group> getGroups() {
		return groups;
	}

	@Override
	public int compareTo(Person o) {
		if (code.compareTo(o.getCode()) == 0) {
			if (name.compareTo(o.getName()) == 0) {
				return surname.compareTo(o.getSurname());
			} else {
				return name.compareTo(o.getName());
			}
		} else {
			return code.compareTo(o.getCode());
		}
	}
}
