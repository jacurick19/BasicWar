package basicwar.graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import basicwar.BasicWar;
import basicwar.STATE;
public class OptionsMenu extends MouseAdapter{

		String input;
		int x,y;
		BasicWar bw;
		
		public OptionsMenu(BasicWar bw) {
			this.bw = bw;
			
		}
		
		public void MousePressed(MouseEvent e) {

			 x = e.getX();
			 y = e.getY();		
			
		}
		public void MouseReleased(MouseEvent e) {
			x=-1;
			y=-1;
		}
	    public void update(){
	    	if(x >200&&x<300) {
	    		if(y>150&&y<180) settingsButton();
	    		if(y>200&&y<230) returnButton();
	    		if(y>250&&y<280) creditsButton();
	    	}
	    	
	    	x=-2;
	    	y=-2;

	    }
	    
	    public void settingsButton() {
	    	
	    	JFrame panel = new JFrame();
	    	
	    	JLabel label = new JLabel("Enter New Simulation Speed");
	    	JButton button1 = new JButton("Confirm");
	    	JButton button2 = new JButton("Cancel");
	    	JTextField text = new JTextField();
	    	text.setBounds(10, 40, 270, 20);
	    	label.setBounds(10,10,200,20);
	    	
	    	
	    	button1.setBounds(30, 210, 90, 30);
	    	button2.setBounds(170, 210, 90, 30);
	    	
	    	
	    	panel.add( button2);
	    	panel.add( button1);
	    	panel.add(text);
	    	panel.add(label);
	    	panel.setSize(300, 300);
	    	panel.setResizable(false);
	    	panel.setLocationRelativeTo(null);
	    	panel.setTitle("SETTINGS");
	    	panel.setLayout(null);
	    	panel.requestFocus();
	    	panel.setPreferredSize(new Dimension(300,300));
	    	panel.setVisible(true);
	    	
	    	
	    	button1.addActionListener(new ActionListener() {
	    		public void actionPerformed(ActionEvent a) {
	    			input = text.getText();
	    			try {
	    				bw.simSpeed = Double.parseDouble(input);
	    			}catch(NumberFormatException e) {
	    				
	    				System.out.println("ERROR");
	    				
	    			}
	    				panel.dispose();
	    				
	    			}
	    			
	    		
	    	});
	    	
	    	button2.addActionListener(new ActionListener() {
	    		public void actionPerformed(ActionEvent a) {
	    			
	    			panel.dispose();
	    		}
	    		
	    	});
	    }	
	    
	    public void returnButton() {
	    	
	    	bw.state = STATE.MENU;
	    	
	    	
	    }
	    
	    
	    public void creditsButton() {System.out.println("creditsbutton");}
	    
	    public void render(Graphics g){
	    	g.setColor(Color.WHITE);
	        g.drawRect(200, 150, 100, 30);
	        g.drawRect(200, 200, 100, 30);
	        g.drawRect(200, 250, 100, 30);
	        g.setFont(new Font("Arial", Font.PLAIN, 22));
	        g.drawString("Sim. Settings", 205, 172);
	        g.drawString("Return", 205, 222);
	        g.drawString("Credits", 205, 272);
	        


	    }
		@Override
		public void mouseClicked(MouseEvent arg0) {
			x= arg0.getX();
			y= arg0.getY();
			
		}
		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	}
