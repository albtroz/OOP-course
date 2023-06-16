package it.polito.po.test;

import java.awt.Container;

import javax.swing.JComponent;
import javax.swing.JFrame;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import social.SocialGui;
import social.Social;

import static org.junit.Assert.assertTrue;

public class TestR5_Layout  {

	private final Social f = new Social();
	private SocialGui gui;

	@Before
	public void setUp() throws Exception {
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
			
			gui = new SocialGui(f);
			gui.setVisible(true);
	}

	@After
	public void tearDown(){
		gui.setVisible(false);
		gui.dispose();
	}

	private static int[] findCoord(JComponent c){
		int[] coordinates = {0,0};
		while(c!=null){
			coordinates[0]+=c.getX();
			coordinates[1]+=c.getY();
			Container p = c.getParent();
			if(p instanceof JFrame) break;
			c = (JComponent) p;
		}
		return coordinates;
	}

	@Test
	public void testVisible(){
		
		assertTrue("Main frame is not visible",gui.isVisible());
		assertTrue("Login button is not present in the frame",gui.login.getBounds().width>0);
		assertTrue("ID text field is not present in the frame",gui.id.getBounds().width>0);
		assertTrue("Friend list is not present in the frame",gui.friends.getBounds().width>0);
		assertTrue("Name of user is not present in the frame",gui.name.getBounds().width>0);		
	}

	@Test
	public void testRelativePositionsLogin(){
		
		int textX = findCoord(gui.id)[0];
		int buttonX = findCoord(gui.login)[0];
		
		assertTrue("Text field should be on the left of login button",textX < buttonX);
	}

	@Test
	public void testRelativePositionsInfo(){
		
		int buttonY = findCoord(gui.login)[1];
		int nameY = findCoord(gui.name)[1];
		int friendsY = findCoord(gui.friends)[1];

		assertTrue("Button should be above the name",buttonY < nameY);
		assertTrue("Name should be above friends list",nameY < friendsY);
	}
}
