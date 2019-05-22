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
    
    public void render(int x, int y, int faction){
    
        pixels[x +y*size] = 0x66AD00;
        
    }
}
