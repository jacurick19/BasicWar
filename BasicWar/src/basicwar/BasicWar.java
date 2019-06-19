
package basicwar;


import basicwar.graphics.Credits;
import basicwar.graphics.Menu;
import basicwar.graphics.OptionsMenu;
import basicwar.graphics.Screen;
import basicwar.io.MouseIn;
import basicwar.map.Map;
import basicwar.units.TestTroop;
import basicwar.units.Troop;
import basicwar.units.Unit;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.text.DecimalFormat;

import javax.swing.JFrame;





//TODO dont allow test and normal troops to be in the same place via problem with the options menu
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
    private boolean ranOnce = false;
    private OptionsMenu options;
    public STATE state;
    public double simSpeed = 60.0;
    public boolean displayTerritoryAsSolid = false;
    private Credits credits;
    private int numberOfGenerations = 20;
    public GENSTATE genstate = GENSTATE.FIRST_RUN;
   
    public BasicWar(){
        mouse = new MouseIn();
        screen = new Screen(500);
        map = new Map(500, this);
        frame = new JFrame();
        frame.addMouseListener(mouse);
        menu = new Menu(this);
        options = new OptionsMenu(this);
        credits = new Credits(this);
        this.addMouseListener(menu);
        this.addMouseListener(options);
        this.addMouseListener(credits);
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
    
	public void setState(STATE s) {
		
		state = s;
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
        	

        		runGeneration();
        	
//            map.update();
        }
        
        
        
        else if(state == STATE.MENU){
            menu.update();
        }
        else if(state == STATE.TESTING){
            map.update();
        }
        else if(state == STATE.OPTIONS){
            options.update();
        }
        else if(state == STATE.CREDITS){
            credits.update();
        }else state = STATE.MENU;
    }
    
    public String factionToColor(int faction) {
    	String s = "";
    	if(faction ==0) s+= "green";
    	if(faction ==1) s+= "red";
    	if(faction ==2) s+= "blue";
    	if(faction ==3) s+= "yellow";
    	return s;
    }
    
    
    public void render(){
                

        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
        	createBufferStrategy(3);
        	return;
		
		}
        Graphics g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
   	 	g.setColor(Color.WHITE);

        if(state == STATE.RUNNING || state== STATE.TESTING) {
        	 screen.clear();
        	 if(!displayTerritoryAsSolid) {
        		 map.render(screen);
        	 }
        	 
        	 else {
        		 map.renderSolid(screen);
        		 
        	 }
        	 
        	 g.drawString("Faction "+factionToColor(map.mostTerritory()) +" has the most territory", 300, 10);
        	 g.drawString("Average strength:  "+new DecimalFormat("#.##").format(map.averageStrength()), 300, 30);
        		g.setColor(Color.BLACK);
            	
            	g.fillRect(400, 450, 80, 30);
            	g.setColor(Color.WHITE);
    	        g.drawRect(400, 450, 80, 30);
    	        
    	        g.setFont(new Font("Arial", Font.PLAIN, 22));
    	        g.drawString("Options", 400, 472);
    	        if(mouse.getX()> 400 && mouse.getX()<450) {
    	        	 if(mouse.getY()> 450 && mouse.getY()<480) {
    	        		 
    	        		state = STATE.OPTIONS;
    	        	 }
    	        }
        }
        
        for (int i = 0; i < pixles.length; i++) {
            pixles[i] = screen.pixels[i];
        }
        
        if(state == STATE.MENU) {
        	menu.render(g);
        }
        if(state == STATE.OPTIONS) {
        	options.render(g);
        }
        	
        if(state == STATE.CREDITS) {
            	credits.render(g);
        }
        bs.show();
    }
    
    public void setSimSpeed(int n) {
	   simSpeed = n;
   }
    
    

    
    
    
    
    
    
  
    
    
    public void runGeneration() {
    		if(genstate == GENSTATE.FIRST_RUN) map.setUp(null);
    		if(genstate == GENSTATE.SET_UP) map.setUp(null);
    		if(genstate == GENSTATE.WORK) map.update();
    		if(genstate == GENSTATE.RESET) map.reset();
    		
    	
    	
    }
    
    
    
    
    
    
    
    
    public void setUp() {map.setUp(null);}
    
    
    
  
    
    


    public static void main(String[] args) {
        BasicWar war = new BasicWar();
        war.frame.setResizable(false);
        war.frame.setTitle("war");
        war.frame.add(war);
        System.out.println(args.length);
        war.frame.pack();
        war.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        war.frame.setLocationRelativeTo(null);
        war.frame.setVisible(true);
        war.start();
      
        
    }

    
}
