package be.ecam.sept;

import com.badlogic.gdx.math.GridPoint2;

/**
 * Class qui créer le héros/joueur
 * Created by emino on 24-04-18.
 * Update by sen on 26-05-18
 */

public class Bunny {

    private String img;
    private GridPoint2 p;



    Bunny( String img){
        this.img = img;
        this.p=new GridPoint2(0,0);
    }

    public String getImg(){
        return this.img;
    }

    public GridPoint2 getPosition() {
        return p;
    }

    public void setPosition(int x, int y) {
        this.p.set(x,y);
    }
}
