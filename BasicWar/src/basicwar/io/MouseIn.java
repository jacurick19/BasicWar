package basicwar.io;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class MouseIn implements MouseListener{

	
	private int x = 0;
	private int y = 0;
    @Override
    public void mouseClicked(MouseEvent e) {
           x = e.getX();
        y = e.getY();

    }

    @Override
    public void mousePressed(MouseEvent e) {
        x =e.getX();
        y =e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    	x = -1;
    	y = -1;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
            
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

	public int getX() {
		// TODO Auto-generated method stub
		return x;
	}
	public int getY() {
		// TODO Auto-generated method stub
		return y;
	}
    

    }
    

