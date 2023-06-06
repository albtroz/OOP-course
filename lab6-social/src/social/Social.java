package social;

import java.util.*;
import java.util.stream.Collectors;


public class Social {

	TreeMap<String, Person> persons = new TreeMap<>();
	TreeMap<String, Group> groups = new TreeMap<>();
	
	/**
	 * Creates a new account for a person
	 * 
	 * @param code	nickname of the account
	 * @param name	first name
	 * @param surname last name
	 * @throws PersonExistsException in case of duplicate code
	 */
	public void addPerson(String code, String name, String surname) throws PersonExistsException {
		if(persons.containsKey(code)) {
			throw new PersonExistsException();
		}
		persons.put(code, new Person(code, name, surname));
	}

	/**
	 * Retrieves information about the person given their account code.
	 * The info consists in name and surname of the person, in order, separated by blanks.
	 * 
	 * @param code account code
	 * @return the information of the person
	 * @throws NoSuchCodeException
	 */
	public String getPerson(String code) throws NoSuchCodeException {
		if(!persons.containsKey(code)) {
			throw new NoSuchCodeException();
		}
		
		return new String(persons.get(code).getInfo());
	}

	/**
	 * Define a friendship relationship between to persons given their codes.
	 * 
	 * Friendship is bidirectional: if person A is friend of a person B, that means that person B is a friend of a person A.
	 * 
	 * @param codePerson1	first person code
	 * @param codePerson2	second person code
	 * @throws NoSuchCodeException in case either code does not exist
	 */
	public void addFriendship(String codePerson1, String codePerson2) throws NoSuchCodeException {
		if(!persons.containsKey(codePerson1)) {
			throw new NoSuchCodeException();
		}
		
		if(!persons.containsKey(codePerson1)) {
			throw new NoSuchCodeException();
		}
		
		Person p2 = persons.get(codePerson1);
		Person p1 = persons.get(codePerson2);
		p1.addFriend(p2);
		p2.addFriend(p1);
	}

	/**
	 * Retrieve the collection of their friends given the code of a person.
	 * 
	 * 
	 * @param codePerson code of the person
	 * @return the list of person codes
	 * @throws NoSuchCodeException in case the code does not exist
	 */
	public Collection<String> listOfFriends(String codePerson) throws NoSuchCodeException {
		if(!persons.containsKey(codePerson)) {
			throw new NoSuchCodeException();
		}
		return persons
					.get(codePerson)
					.getFriend()
					.stream()
					.map(Person::getCode)
					.collect(Collectors.toList());
	}

	/**
	 * Retrieves the collection of the code of the friends of the friends
	 * of the person whose code is given, i.e. friends of the second level.
	 * 
	 * 
	 * @param codePerson code of the person
	 * @return collections of codes of second level friends
	 * @throws NoSuchCodeException in case the code does not exist
	 */
	public Collection<String> friendsOfFriends(String codePerson) throws NoSuchCodeException {
		if(!persons.containsKey(codePerson)) {
			throw new NoSuchCodeException();
		}
		
		Person p = persons.get(codePerson);
		TreeSet<Person> friends = p.getFriend();
		List<Person> res = new ArrayList<Person>();
		
		for (Person person : friends) {
			res.addAll(person.getFriend());
		}
		
		return res
				.stream()
				.filter(x->x.getCode() != codePerson)
				.map(Person::getCode)
				.collect(Collectors.toList());
	}

	/**
	 * Retrieves the collection of the code of the friends of the friends
	 * of the person whose code is given, i.e. friends of the second level.
	 * The result has no duplicates.
	 * 
	 * 
	 * @param codePerson code of the person
	 * @return collections of codes of second level friends
	 * @throws NoSuchCodeException in case the code does not exist
	 */
	public Collection<String> friendsOfFriendsNoRepetition(String codePerson) throws NoSuchCodeException {
		if(!persons.containsKey(codePerson)) {
			throw new NoSuchCodeException();
		}
		
		Person p = persons.get(codePerson);
		TreeSet<Person> friends = p.getFriend();
		TreeSet<Person> res = new TreeSet<Person>();
		
		for (Person person : friends) {
			res.addAll(person.getFriend());
		}
		
		return res
				.stream()
				.filter(x->x.getCode() != codePerson)
				.map(Person::getCode)
				.collect(Collectors.toList());
	}

	/**
	 * Creates a new group with the given name
	 * 
	 * @param groupName name of the group
	 */
	public void addGroup(String groupName) {
		groups.put(groupName, new Group(groupName));
	}

	/**
	 * Retrieves the list of groups.
	 * 
	 * @return the collection of group names
	 */
	public Collection<String> listOfGroups() {
		return groups.keySet();
	}

	/**
	 * Add a person to a group
	 * 
	 * @param codePerson person code
	 * @param groupName  name of the group
	 * @throws NoSuchCodeException in case the code or group name do not exist
	 */
	public void addPersonToGroup(String codePerson, String groupName) throws NoSuchCodeException {
		if(!persons.containsKey(codePerson)) {
			throw new NoSuchCodeException();
		}
		
		if(!groups.containsKey(groupName)) {
			throw new NoSuchCodeException();
		}
		
		groups.get(groupName).addSubscriber(persons.get(codePerson));
		persons.get(codePerson).addSbuscription(groups.get(groupName));
	}

	/**
	 * Retrieves the list of people on a group
	 * 
	 * @param groupName name of the group
	 * @return collection of person codes
	 */
	public Collection<String> listOfPeopleInGroup(String groupName) {
		// Returns null if the group does not exist.
		if(!groups.containsKey(groupName)) {
			return null;
		}
		
		return groups
				.get(groupName)
				.getSubscribers()
				.stream()
				.map(Person::getCode)
				.collect(Collectors.toList());
	}

	/**
	 * Retrieves the code of the person having the largest
	 * group of friends
	 * 
	 * @return the code of the person
	 */
	public String personWithLargestNumberOfFriends() {
		return persons
				.values()
				.stream()
					.collect(Collectors.toMap(
							Person::getCode, 
							person->{
								try {
									return listOfFriends(person.getCode()).size();
								} catch (NoSuchCodeException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								return null;
							}))
				.entrySet()
				.stream()
				.max(Map.Entry.comparingByValue())
				.get()
				.getKey();
	}

	/**
	 * Find the code of the person with largest number
	 * of second level friends
	 * 
	 * @return the code of the person
	 */
	public String personWithMostFriendsOfFriends() {
		return persons
				.values()
				.stream()
					.collect(Collectors.toMap(
							Person::getCode, 
							person->{
								try {
									return friendsOfFriends(person.getCode()).size();
								} catch (NoSuchCodeException e) {
									e.printStackTrace();
								}
								return null;
							}))
				.entrySet()
				.stream()
				.max(Map.Entry.comparingByValue())
				.get()
				.getKey();
	}

	/**
	 * Find the name of group with the largest number of members
	 * 
	 * @return the name of the group
	 */
	public String largestGroup() {
		return groups
					.values()
					.stream()
					.collect(Collectors.toMap(
							Group::getName,
							group -> listOfPeopleInGroup(group.getName()).size()))
					.entrySet()
					.stream()
					.max(Map.Entry.comparingByValue())
					.get()
					.getKey();
	}

	/**
	 * Find the code of the person that is member of
	 * the largest number of groups
	 * 
	 * @return the code of the person
	 */
	public String personInLargestNumberOfGroups() {
		return persons
				.values()
				.stream()
				.collect(Collectors.toMap(
						Person::getCode,
						person -> person.getGroups().size()))
				.entrySet()
				.stream()
				.max(Map.Entry.comparingByValue())
				.get()
				.getKey();
	}
}