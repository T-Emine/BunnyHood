package be.ecam.sept;

/**
 * Class qui créer le background du jeu
 * Created by emino on 24-04-18.
 */

public class Map {
    private String img;

   Map(String img){
       this.img = img;
   }

    public String getImg(){
        return this.img;

    }

    public void setImg(String img){
        this.img = img;
    }

}
