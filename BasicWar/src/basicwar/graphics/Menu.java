package basicwar.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import basicwar.BasicWar;
import basicwar.STATE;



public class Menu extends MouseAdapter{
	int x,y;
	BasicWar bw;
	
	public Menu(BasicWar bw) {
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
    		if(y>150&&y<180) playButton();
    		if(y>200&&y<230) optionsButton();
    		if(y>250&&y<280) creditsButton();
    	}
    	
    	x=-2;
    	y=-2;
    }
    
    public void playButton() {

    	bw.state = STATE.RUNNING;
    }
    
    public void optionsButton() {
    	
    	System.out.println("optionsbutton");
    }
    
    
    public void creditsButton() {System.out.println("creditsbutton");}
    
    public void render(Graphics g){
    	g.setColor(Color.WHITE);
        g.drawRect(200, 150, 100, 30);
        g.drawRect(200, 200, 100, 30);
        g.drawRect(200, 250, 100, 30);
        g.setFont(new Font("Arial", Font.PLAIN, 22));
        g.drawString("Test 1", 220, 172);
        g.drawString("Test 2", 220, 222);
        g.drawString("Test 3", 220, 272);
        


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
