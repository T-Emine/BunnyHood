package be.ecam.sept;

import com.badlogic.gdx.math.GridPoint2;

import static com.badlogic.gdx.math.MathUtils.random;
import static java.lang.Math.abs;

/**
 * Class qui créer l'objet qui doit être sauvé
 * Created by emino on 24-04-18.
 * Update by sen on 26-05-18
 */

public class BabyBunnies {
    private String img;
    private int score=10;
    private GridPoint2 p;


    BabyBunnies(String img,int height,int width){
        this.img = img;
        this.p=new GridPoint2(random(0,abs(height-100)),random(0,abs(width-100)));
    }


    public String getImg(){
        return this.img;
    }


    public GridPoint2 getPosition() {
        return p;
    }

}
