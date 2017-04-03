import java.awt.event.KeyEvent;//can do multiple inheritance in C++ but not in Java -- instead, use interfaces in Java
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

class Enemy extends Character implements GameInfo{

    public Enemy(){

    }

    public Enemy(int type){
        if(type==1){
            initializeAdultFemaleSoul();
        }
        if(type==2){
            initializeAdultMaleSoul();
        }
    }

    private void initializeAdultFemaleSoul(){
        name = "AF";

        hurtbox = new Box(13, 38, 40, 22);
        hurtbox.changeOffset(x, y);
        vulnerable = true;
        loadAnimations();
        setMaxHP(4);
    }

    private void initializeAdultMaleSoul(){
        name = "MF";

        hurtbox = new Box(13, 38, 40, 22);
        hurtbox.changeOffset(x, y);
        vulnerable = true;
        loadAnimations();
        setMaxHP(4);
    }
}