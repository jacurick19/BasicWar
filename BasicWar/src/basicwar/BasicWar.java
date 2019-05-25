
package basicwar;


import basicwar.graphics.Menu;
import basicwar.graphics.OptionsMenu;
import basicwar.graphics.Screen;
import basicwar.io.MouseIn;
import basicwar.map.Map;
import basicwar.units.TestTroop;
import basicwar.units.Troop;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class BasicWar extends Canvas implements Runnable  {
    private static final long serialVersionUID = 1L;
    BufferStrategy bs;
    private BufferedImage image = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB);
    private int[] pixles = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
    private Map map;
    private Screen screen;
    private Thread thrred;
    private MouseIn mouse;
    private boolean running = false;
    private JFrame frame;
    private Menu menu;
    private OptionsMenu options;
    public STATE state;
    public double simSpeed = 60.0;
  
    public BasicWar(){
        mouse = new MouseIn();
        screen = new Screen(500);
        map = new Map(500);
        frame = new JFrame();
        frame.addMouseListener(mouse);
        menu = new Menu(this);
        options = new OptionsMenu(this);
        this.addMouseListener(menu);
        this.addMouseListener(options);
       addMouseListener(mouse);
        setPreferredSize(new Dimension(500,500));
  
        
    }
    public synchronized void start() {
		running = true;
		thrred = new Thread(this, "display");
		thrred.start(); 
	}
	
	
	public synchronized void stop() {
		running = false;
		try {
		thrred.join();
	} catch (InterruptedException e) {
			e.printStackTrace ();
		}
	}
    
    public void run(){

        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        double interval = 1000000000.0 / simSpeed;
        double delta = 0; 
        int frames = 0;
        int updates = 0;
        
        requestFocus();
        while(running){
        	interval = 1000000000.0 / simSpeed;
        	long now = System.nanoTime();
            delta+=(now-lastTime)/interval;
            lastTime=now;
            while(delta>=1){
                update();
            updates++;
            delta--;
        }
        render();

        frames++;
        if(System.currentTimeMillis() - timer > 1000){
        	System.out.println("FPS: "+frames+" UPS: " +updates);
            timer += 1000;
            updates = 0; 
            frames = 0;
        }

        }
        stop();
    }

    
    public void update(){
        if(state == STATE.RUNNING){
            map.update();
        }
        
        else if(state == STATE.MENU){
            menu.update();
        }
        else if(state == STATE.TESTING){
            map.update();
        }
        else if(state == STATE.OPTIONS){
            options.update();
        }else state = STATE.MENU;
    }
    
    
    
    public void render(){
                

        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {

        	createBufferStrategy(3);

        	return;
		
		}
        if(state == STATE.RUNNING || state== STATE.TESTING) {
        	 screen.clear();
        map.render(screen);
        
        
        }
        
        for (int i = 0; i < pixles.length; i++) {
            pixles[i] = screen.pixels[i];
}
        
        
        
        
        Graphics g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        if(state == STATE.MENU) {
        	
        	menu.render(g);
        
        }
        	if(state == STATE.OPTIONS) {
        	
        	options.render(g);
        
        }
        bs.show();
    }
    
    public void setUp(STATE s) {
    
    	if(s == STATE.RUNNING) {
    	
    		for(int i = 0; i < 10; i ++){
    			map.addMap(new Troop(i+100,i+200,1, map));
    		}
    		
    		for(int i = 0; i < 10; i ++){
    			map.addMap(new Troop(i+100,i+201,0, map));

    		}
    		
    		
    		for(int i = 0; i < 10; i ++){
    			map.addMap(new Troop(i+200,i+100,2, map));
    		}
    		
    		for(int i = 0; i < 10; i ++){
    			map.addMap(new Troop(i+200,i+201,3, map));

    		}
    		
    	}
    	if(s== STATE.TESTING) {
    		map.addMap(new TestTroop(100,100,1, map));
    		map.addMap(new TestTroop(104,100,0, map));
    		simSpeed = 1;
    	}
    }
    
  

    public static void main(String[] args) {
        BasicWar war = new BasicWar();
        war.frame.setResizable(false);
        war.frame.setTitle("war");
        war.frame.add(war);
        war.frame.pack();
        war.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        war.frame.setLocationRelativeTo(null);
        war.frame.setVisible(true);
        war.start();
        
    }
    
}
