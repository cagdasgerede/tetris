package runner;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

// To create graphics , i will use swing tools
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
 
/* This class creates a user interface. For example, there will be a "start game" button and with event
 * handling this button will start the game. Also there will be settings button to allow player choose 
 * difficulty and other options. And so on .. 
 */
public class UserInterface extends JFrame {
	
	private CustomPanel mainPanel;   // Main Menu 
	private JPanel topPanel;   // Main Menu 
	private static boolean wait = true ;
	static JFrame game ;
	private static JPanel otherPanel;
	private static boolean sound = true;

	public UserInterface(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setBounds(0, 0, 1000, 500);
		setLocationRelativeTo(null);   // to appear in center 
		
		// Initializing our custom panel with background image 
		try {
			mainPanel = new CustomPanel("src/new_images/bg.jpg",1000,500);
		} catch (IOException e1) {
			System.out.println("IO Exception");
		}
	
		setContentPane(mainPanel);
		mainPanel.setLayout(new BorderLayout());
		setResizable(false);     // no need to be full size for Tetris game menu
		
		/* Buttons and their functions */
		JButton button1  = new JButton(""); // Start Game  
		JButton button2 = new JButton("");  // Settings
		JButton button3  = new JButton(""); //Controls
		JButton button4 = new JButton("");  // High Scores
		JButton button5  = new JButton(""); // Exit
		
		// 2 images for every button. Extra images are for rollover effect.
		ImageIcon b1_roll = new ImageIcon("src/new_images/play.png");
		button1.setIcon(new ImageIcon("src/new_images/r_play.png"));
		ImageIcon b2_roll = new ImageIcon("src/new_images/settings.png");
		button2.setIcon(new ImageIcon("src/new_images/r_settings.png"));
		ImageIcon b3_roll = new ImageIcon("src/new_images/gamepad.png");
		button3.setIcon(new ImageIcon("src/new_images/r_gamepad.png"));
		ImageIcon b4_roll = new ImageIcon("src/new_images/scores.png");
		button4.setIcon(new ImageIcon("src/new_images/r_scores.png"));
		ImageIcon b5_roll = new ImageIcon("src/new_images/exit.png");
		button5.setIcon(new ImageIcon("src/new_images/r_exit.png"));
		
		// to delete button frame for better looking 
		Border emptyBorder = BorderFactory.createEmptyBorder();
		button1.setBorder(emptyBorder);
		button1.setOpaque(false);
		button1.setContentAreaFilled(false);
		button1.setBorderPainted(false);
		
		button2.setBorder(emptyBorder);
		button2.setOpaque(false);
		button2.setContentAreaFilled(false);
		button2.setBorderPainted(false);
		
		button3.setBorder(emptyBorder);
		button3.setOpaque(false);
		button3.setContentAreaFilled(false);
		button3.setBorderPainted(false);
		
		button4.setBorder(emptyBorder);
		button4.setOpaque(false);
		button4.setContentAreaFilled(false);
		button4.setBorderPainted(false);
	
		button5.setBorder(emptyBorder);
		button5.setOpaque(false);
		button5.setContentAreaFilled(false);
		button5.setBorderPainted(false);
		
		// adding buttons mouseover effect.
		button1.setRolloverIcon(b1_roll);
		button2.setRolloverIcon(b2_roll);
		button3.setRolloverIcon(b3_roll);
		button4.setRolloverIcon(b4_roll);
		button5.setRolloverIcon(b5_roll);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets= new Insets(15,45,15,20);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor=GridBagConstraints.NORTHWEST;
		gbc.weightx=1;
		gbc.weighty=1;
			
		topPanel= new JPanel();
		topPanel.setLayout(new GridBagLayout());
		topPanel.setOpaque(false);
		topPanel.setPreferredSize(new Dimension(1000, 250));
		
		topPanel.add(button1,gbc);
		gbc.gridx = 1;
		topPanel.add(button2,gbc);
		gbc.gridx = 2;
		topPanel.add(button3,gbc);
		gbc.gridx = 3;
		topPanel.add(button4,gbc);
		gbc.gridx = 4;
		topPanel.add(button5,gbc);		
		
		add(topPanel,BorderLayout.NORTH);
		
		otherPanel = new JPanel();
		JPanel emptyPanel = new JPanel();
		otherPanel.setLayout(new GridBagLayout());
		otherPanel.setPreferredSize(new Dimension(500, 250));
		
		emptyPanel.setPreferredSize(new Dimension(500, 250));
		
		otherPanel.setOpaque(false);
		emptyPanel.setOpaque(false);
		add(otherPanel,BorderLayout.WEST);
		add(emptyPanel,BorderLayout.EAST);
			
		
		button1.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				game.dispose();
				wait=false;
			}
		});
		
		// Add functionality to exit button. It's trivial.
		button5.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
				
		button3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
	
				GridBagConstraints gbc2 = new GridBagConstraints();
				JLabel arrowLabel = new JLabel("To move and turn the"+ "\n"+ "blocks use arrow keys!");
				JLabel undoLabel = new JLabel("You can undo your move with key U!");
				JLabel spaceLabel = new JLabel("Don't wanna wait? Use space button!");
				
				otherPanel.removeAll();
				otherPanel.repaint();
				
				gbc2.weighty=1;				
				
				gbc2.gridx=0; gbc2.gridy=0;
				otherPanel.add(arrowLabel,gbc2);
				
				gbc2.gridx=0; gbc2.gridy=1;
				otherPanel.add(undoLabel,gbc2);
				
				gbc2.gridx=0; gbc2.gridy=2;
				otherPanel.add(spaceLabel,gbc2);

				otherPanel.revalidate();
						
			}
		});
		
		JCheckBox mute = new JCheckBox("Mute");
			button2.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					JPanel settingPanel = new JPanel();
					mute.setContentAreaFilled(false);
					mute.setOpaque(false);
					mute.setBorderPainted(false);
					 				 
					 settingPanel.add(mute);
					 otherPanel.removeAll();
					otherPanel.repaint();
					 otherPanel.add(settingPanel);
					 otherPanel.revalidate();

				}
			});
			mute.addChangeListener(new ChangeListener() {
				
				@Override
				public void stateChanged(ChangeEvent e) {
					 sound = !mute.isSelected();				
				}
			});   
	}
	
	public static boolean getSound(){
		return sound;
	}
	public static void main(String[] args) {
			game = new UserInterface ();
			game.setVisible(true);
			
			try {
				while(wait)
					Thread.sleep(1000);
				new GameRunner();
				wait=true;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}
	

}