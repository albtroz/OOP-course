package it.polito.po.test;

import java.awt.Window;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JDialog;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import social.NoSuchCodeException;
import social.SocialGui;
import social.Social;

import static org.junit.Assert.*;

public class TestR6_Login {

	public static final String MARIO_99 = "Mario99";
	private Social f;
	private SocialGui gui;

	@Before
	public void setUp() throws Exception {
		f = new Social();
		f.addPerson(MARIO_99, "Mario", "Rossi");
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
	}

	@After
	public void tearDown(){
		//gui.dispatchEvent(e);
		gui.setVisible(false);
		gui.dispose();
		gui=null;
	}
	
	@Test
	public void testLogin() throws NoSuchCodeException {

		gui.id.setText(MARIO_99);
		gui.login.doClick();
		
		String name = gui.name.getText();

		String[] names = f.getPerson(MARIO_99).split(" ");
		assertTrue("Missing name",name.contains(names[1]));
		assertTrue("Missing surname",name.contains(names[2]));
		
	}


	@Test
	public void testLoginList() throws NoSuchCodeException {

		gui.id.setText(MARIO_99);
		gui.login.doClick();
		
		int n = gui.friends.getModel().getSize();
		int exp = f.listOfFriends(MARIO_99).size();

		assertEquals("Wrong number of friends in GUI",exp,n);
	}


	@Test
	public void testLoginError() throws InterruptedException{

		gui.id.setText("MarioXX");

		Thread t = new Thread(() -> gui.login.doClick() );
		t.start();
		Thread.yield();

		t.join(100);
		while(t.getState() != Thread.State.WAITING &&
				t.getState() != Thread.State.TERMINATED){
			t.join(100);
		}

		Window[] allWindows = Window.getWindows();
		JDialog dialog = null;
		for(Window w : allWindows){
			//System.out.println(w.getClass()+ " : " + w.getX() + ", " + w.getY());
			if(w instanceof JDialog){
				dialog = (JDialog)w;
			}
		}
		
		assertNotNull("No dialog found",dialog);
		
		JButton bt = dialog.getRootPane().getDefaultButton();
		
		assertNotNull("No default button",bt);
		
		assertEquals("OK",bt.getText());
		
		bt.doClick();
	}


	@Test
	public void testLoginEnter(){

		gui.id.setText("Mario99");
//		gui.login.doClick();
		gui.id.dispatchEvent(new KeyEvent(gui.id, KeyEvent.KEY_PRESSED, System.currentTimeMillis()-10, 0, KeyEvent.VK_ENTER, KeyEvent.CHAR_UNDEFINED));
		gui.id.dispatchEvent(new KeyEvent(gui.id, KeyEvent.KEY_RELEASED, System.currentTimeMillis()-5, 0, KeyEvent.VK_ENTER, KeyEvent.CHAR_UNDEFINED));
		gui.id.dispatchEvent(new KeyEvent(gui.id, KeyEvent.KEY_TYPED, System.currentTimeMillis(), 0, KeyEvent.VK_UNDEFINED, '\n'));
		
		String name = gui.name.getText();
		
		assertTrue("Missing name",name.contains("Mario"));
		assertTrue("Missing surname",name.contains("Rossi"));
		
	}
}
