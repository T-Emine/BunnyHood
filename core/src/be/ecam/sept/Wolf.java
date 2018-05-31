package be.ecam.sept;

import com.badlogic.gdx.math.GridPoint2;

/**
 * Class qui créer l'ennemie, le loup
 * Created by emino on 24-04-18.
 * Update by sen on 26-05-18
 */

public class Wolf {
    private String img;
    private int score;
    private GridPoint2 position;


    Wolf( GridPoint2 pos){
        this.img = "wolf.png";
        this.position = new GridPoint2(pos);


    }

    public String getImg(){
        return this.img;
    }

    public GridPoint2 getPosition() {
        return position;
    }

    public void setPosition(GridPoint2 pos) {

        this.position.set(pos.x,pos.y-500);
    }
    public void restPost(){
        this.position.y=0;
    }

    public void move(int speed){
        this.position.y+=3+speed;
    }
}
