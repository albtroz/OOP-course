package social;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import java.awt.Dimension;


public class SocialGui extends JFrame {


	/*
	 * Default UID generated by Eclipse
	 */
	private static final long serialVersionUID = 1L;

	// The following components are declared public
	// in order to allow testing the user interface
	
	/**
	 * The code of the person to log in
	 */
	public JTextField id ;
	public JLabel t;
	
	/**
	 * The button to perform login
	 */
	public JButton login ;
	
	/**
	 * The label that shall contain the info
	 * of the logged in person 
	 */
	public JLabel name ;
	
	/**
	 * The list of friends of the person
	 * that is logged in
	 */
	public JList<String> friends;
	

	public SocialGui(Social m) {
		final int WIDTH = 400;
	    final int HEIGHT = 600;
	    setSize(WIDTH, HEIGHT);
		setTitle("Lab06-Social");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Set the font to Arial
        Font font = new Font("Arial", Font.PLAIN, 12);
        setFont(font);
        
        
        // Logging phase
        t = new JLabel("ID: ");
        id = new JTextField(12);
        login = new JButton("Login");
        
        // User name
        name = new JLabel("<user name>");

        // Friends list
        friends = new JList<String>();
        friends.setListData(new String[]{"friends", "..."});
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        JPanel loginPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel friendsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        loginPanel.setPreferredSize(new Dimension(400, 60));
        loginPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        userPanel.setPreferredSize(new Dimension(400, 60));
        userPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        friendsPanel.setPreferredSize(new Dimension(400, 450));
        friendsPanel.setBackground(Color.white);
        friendsPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        
        
        loginPanel.add(t);
        loginPanel.add(id);
        loginPanel.add(login);
        userPanel.add(name);
        friendsPanel.add(friends);
        
        mainPanel.add(loginPanel, BorderLayout.NORTH);
        mainPanel.add(userPanel, BorderLayout.CENTER);
        mainPanel.add(friendsPanel, BorderLayout.SOUTH);
        
        
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	loginOperation(m);
            }
        });
        
        id.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					loginOperation(m);
				}
					
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					loginOperation(m);
				}
			}
        	
        });
        
        setContentPane(mainPanel);
        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(true);

	}
	
	public void loginOperation(Social m) {
		try {
            String idUser = id.getText();
            String[] toDisplay = m.getPerson(idUser).split(" ");
            name.setText(toDisplay[1]+" "+toDisplay[2]);
            friends.setListData(m.listOfFriends(idUser).toArray(String[]::new));
        } catch (Exception ex) {
        	JOptionPane.showMessageDialog(null, "The user code is invalid", "Login error", JOptionPane.ERROR_MESSAGE);
        	name.setText("<user name>");
        	friends.setListData(new String[2]);
        	id.setText("");
        	
        }
	}

}
