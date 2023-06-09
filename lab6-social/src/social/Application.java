package social;

public class Application {

	public static void main(String[] args) throws PersonExistsException, NoSuchCodeException {
		// TODO Auto-generated method stub
		Social f = new Social();

		f.addPerson("Mario99", "Mario", "Rossi");
		f.addPerson("Mario88", "Mario", "Verdi");
		f.addPerson("Elena66", "Elena", "Aresti");
		f.addPerson("BigLupo", "Lupo", "Bianchi");
		f.addPerson("FFA", "Franca", "Rosetti");
		f.addPerson("Sally", "Sandra", "Sandroni");
		f.addFriendship("Mario99", "BigLupo");
		f.addFriendship("Mario99", "Elena66");
		f.addFriendship("Elena66", "FFA");
		f.addFriendship("Elena66", "Sally");
		f.addFriendship("BigLupo", "FFA");
		SocialGui gui = new SocialGui(f);
	}

}
