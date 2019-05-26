package basicwar.graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;



import basicwar.BasicWar;
import basicwar.STATE;

public class Credits extends MouseAdapter{

	String input;
	int x,y;
	BasicWar bw;
	
	public Credits(BasicWar bw) {
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
    		if(y>450&&y<485) returnButton();
    		
    	}
    	
    	x=-2;
    	y=-2;

    }

    	
    	
    
    public void returnButton() {
    	System.out.println("check");
    	bw.state = STATE.MENU;
    	
    	
    }
    
    

    public void render(Graphics g){
    
    	
    	g.setColor(Color.black);
    	
    	//Make sure this (0, 0, map size, map size)
    	g.fillRect(0, 0, 500, 500);
    	
    	g.setColor(Color.WHITE);
        g.drawRect(200, 450, 100, 30);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("Created by Jacob Urick", 10, 30);
        g.drawString("Project page https://github.com/jacurick19/BasicWar", 10, 70);
        g.drawString("jacoburick01@gmail.com", 10, 110);
        g.drawString("Return", 205, 470);
        


    }
	@Override
	public void mouseClicked(MouseEvent arg0) {
		if(bw.state == STATE.CREDITS) {
			x= arg0.getX();
			y= arg0.getY();
			}
		
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
		x= 0;
		y= 0;			
	}
}
