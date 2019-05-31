package basicwar.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import basicwar.BasicWar;
import basicwar.GENSTATE;
import basicwar.STATE;



public class Menu extends MouseAdapter{
	int x,y;
	BasicWar bw;
	
	public Menu(BasicWar bw) {
		this.bw = bw;
		
	}
	
	public void MousePressed(MouseEvent e) {

	
		
	}
	public void MouseReleased(MouseEvent e) {
		x=-1;
		y=-1;
	}
    public void update(){
    	if(x >200&&x<300) {
    		if(y>150&&y<180) playButton();
    		if(y>200&&y<230) testsButton();
    		if(y>250&&y<280) optionsButton();
    	}
    	
    	

    }
    
    public void playButton() {
    	bw.genstate = GENSTATE.FIRST_RUN;
    	bw.state = STATE.RUNNING;
    	bw.setUp();
    }
    
    public void testsButton() {
    	
    	bw.state = STATE.TESTING;
//    	bw.setUp(STATE.TESTING);
    }
    
    
    public void optionsButton() {
    	bw.state = STATE.OPTIONS;
    	
    }
    
    public void render(Graphics g){
    	
    	
    	g.setColor(Color.black);
    	
    	//Make sure this (0, 0, map size, map size)
    	g.fillRect(0, 0, 500, 500);
    	
    	g.setColor(Color.WHITE);
        g.drawRect(200, 150, 100, 30);
        g.drawRect(200, 200, 100, 30);
        g.drawRect(200, 250, 100, 30);
        g.setFont(new Font("Arial", Font.PLAIN, 22));
        g.drawString("Play", 205, 172);
        g.drawString("Run Test", 205, 222);
        g.drawString("Options", 205, 272);
        


    }
	@Override
	public void mouseClicked(MouseEvent arg0) {
		if(bw.state != STATE.OPTIONS) {
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
		x=0;
		y=0;
		
	}
}
