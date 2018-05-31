package be.ecam.sept;

import com.badlogic.gdx.math.GridPoint2;

import java.util.ArrayList;
import java.util.List;

/**
 * Class logique du jeu,
 * Elle contient toute la partie règle et déroulement du jeu
 * Created by emino on 24-04-18.
 * Update by sen on 26-04-18
 */

public class Game {

    private int bunnyScore;
    private int wolfScore;
    private int level;
    private int timer;
    private int nbBunnies;
    private int height,width;
    private Map map;
    private int nbCatchByWolf;
    private Bunny bunnyHood;
    private BabyBunnies baby;
    private Wolf wolf;
    private boolean in;
    private List <BabyBunnies> babySaved,saveBaby;



    Game(int level,int height,int width){
        this.in =false;
        this.height= height;
        this.width=width;
        this.level = level;
        this.timer = 0;
        this.nbBunnies = 0;
        this.nbCatchByWolf = 0;
        this.bunnyScore = 0;
        this.wolfScore = 0;
        this.map=new Map("default");
        this.baby=new BabyBunnies("lapin.png", height, width);
        this.bunnyHood=new Bunny("hero.png");
        this.babySaved = new ArrayList<BabyBunnies>();
        this.saveBaby  = new ArrayList<BabyBunnies>();
        this.saveBaby.add(this.baby);
        this.wolf = new Wolf(new GridPoint2(0,0));
        /**
         * Switch a améliorier
         */
        switch(level){
            case 1 :
                this.map.setImg("map1.png");break

                    ;
            case 2 :
                this.map = new Map("");break
                    ;
            case 3 :
                this.map = new Map("");break
                    ;
            default : ;break;
        }
    }


    public int getScoreBunny()
    {
        this.bunnyScore=(this.nbBunnies-this.wolfScore)*10;
        return this.bunnyScore;
    }

    public int getScoreWolf() {
        return this.wolfScore;
    }

    /**
     * Accesseur du nombre de lapin sauvé
     * @return
     */

    public int getNbBunnies() {
        return nbBunnies;
    }

    public String getMap() {return this.map.getImg();}

    public Bunny getBunnyHood() {
        return bunnyHood;
    }


    public void setBabySaved(BabyBunnies b) {
        this.in=false;
        this.babySaved.add(b);
        this.saveBaby.remove(b);
        BabyBunnies baby = new BabyBunnies("img_baby.png",this.height,this.width);
        this.saveBaby.add(baby);
        this.nbBunnies += 1;

    }

    public List<BabyBunnies> getSaveBaby() {
        return saveBaby;
    }

    public Wolf getWolf() {
        return wolf;
    }

    /**
     * Methode qui indique si un loup a attrapé un lapin
     * @param b, le lapin prit
     */
    public void wolfCatchBunnies(BabyBunnies b){
        this.in=false;
        this.nbCatchByWolf += 1;
        this.wolfScore += 1;
        this.saveBaby.remove(b);
        BabyBunnies baby = new BabyBunnies("img_baby.png",this.height,this.width);
        this.saveBaby.add(baby);

    }

    /**
     * Lancement de la chasse du loup
     * @param pos, position où se trouve le lapin
     */
    public void hunt(GridPoint2 pos) {
        // XOR
        if( this.in^true) {
            this.in=true;
            this.wolf.setPosition(pos);
        }

    }

}
