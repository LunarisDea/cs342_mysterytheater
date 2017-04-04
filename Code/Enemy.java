import java.awt.event.KeyEvent;//can do multiple inheritance in C++ but not in Java -- instead, use interfaces in Java
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

class Enemy extends Character implements GameInfo{

    public Enemy(){

    }

    public Enemy(int type, int X, int Y){
		x = X;
		y = Y;
        if(type==1){
            initializeAdultFemaleSoul();
        }
        if(type==2){
            initializeAdultMaleSoul();
        }
    }

    private void initializeAdultFemaleSoul(){
		Box hit = new Box(-2, -2, -1, -1);		
		Box hurt = new Box(0, 0, 64, 64);
		
		initChar("AF", hurt, hit);
    }

    private void initializeAdultMaleSoul(){
		Box hit = new Box(-2, -2, -1, -1);		
		Box hurt = new Box(13, 38, 40, 22);
		
		initChar("MF", hurt, hit);
    }
}
