package basicwar.graphics;

public class Screen {


    public int[] pixels;   
    public int size;
    public Screen(int size){
    this.size = size;
    pixels = new int[size*size];
    }
    public void clear(){
        for(int i = 0; i < pixels.length; i++){
        pixels[i ]= 0;
        }
    }
    
    public boolean renderIsEmpty(int x, int y, int faction) {
    	
    	if(x+y*size < pixels.length) {
    		if(pixels[x+y*size] == 0) {
    			if(faction == 1) pixels[x +y*size] = 0xEE1414;
        		if(faction == 2) pixels[x +y*size] = 0x0DD757;
        		return true;
        	}else {
        		return false;
        	}
    	} else {
    		System.out.println("problem");
    	}
    return false;	
    }
    
    public void render(int x, int y, int faction){
    	if(x+y*size < pixels.length) {
    	if(faction == 1) pixels[x +y*size] = 0xEE1414;
    	if(faction == 2) pixels[x +y*size] = 0x0DD757;
    	} else System.out.println("problem");
    }
}
