package be.ecam.sept;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.GridPoint2;

import javax.swing.GrayFilter;


/**
 * Class qui gère l'interface utilisateur,
 * Cette class va afficher toutes les écrans du jeu,
 * elle va aussi intercepter toutes les interactions/actions du joueurs avec le jeu
 * Created by emino on 24-04-18.
 * Update by sen on 26-05-18
 */

public class MainBunnyHood extends ApplicationAdapter {


	// Callback Interface
	public interface MyGameCallback {
		void gameOverActivity(int someParameter);
	}
	private MyGameCallback gameCallBack;

	public void setGameCallBack(MyGameCallback callback) {
		gameCallBack = callback;
	}


	SpriteBatch boardBatch, player_batch, object_batch;
	Texture img_bunnyHood, img_baby,wolf,endScreen,map;
	int height,width;
	float mvX,mvY;
	private OrthographicCamera cam;
	private Sprite mapSprite, endSprite;
	static final int WORLD_WIDTH = 100;
	static final int WORLD_HEIGHT = 100;
	Game game_;
	boolean isOnAccelerometer, isGameOver;
	public String txt="Hello WORLD";
	BitmapFont font;
	GridPoint2 pos;

	MainBunnyHood(int w, int h) {
		this.height=h;
		this.width=w;
	}

	@Override
	public void create () {

        /**
         * Texture
         */
        this.endScreen=new Texture("tavern.jpg");


        this.font = new BitmapFont();
        this.font.setColor(Color.FIREBRICK);
        this.isOnAccelerometer = Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer);
        /**
         * Sprite
         */
        this.boardBatch = new SpriteBatch();//cadre graphique (frame)
        this.player_batch = new SpriteBatch(); // joueur
        this.object_batch = new SpriteBatch();


        this.endSprite = new Sprite(this.endScreen);
        this.endSprite.setPosition(0, 0);
        this.endSprite.setSize(WORLD_WIDTH, WORLD_HEIGHT);

        /**
         * Camera
         */
        this.cam = new OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT);
        this.cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
        this.cam.update();

        initGame();
	}

	private void initGame(){

		this.isGameOver=false;
		this.mvX=0;
		this.mvY=0;
		this.game_ = new Game(1,this.height,this.width);

        this.map=new Texture(this.game_.getMap());
        this.img_bunnyHood = new Texture(this.game_.getBunnyHood().getImg());
        this.img_baby = new Texture(this.game_.getSaveBaby().get(0).getImg());
        this.wolf =new Texture(this.game_.getWolf().getImg());

        this.mapSprite = new Sprite(this.map);
        this.mapSprite.setPosition(0, 0);
        this.mapSprite.setSize(WORLD_WIDTH, WORLD_HEIGHT);

    }

	private void startGame() {

/**
 * Dessiner les lapins a sauver
 */
		for (int j = 0; j< this.game_.getSaveBaby().size();j++){
			BabyBunnies b = this.game_.getSaveBaby().get(j);
            this.object_batch.begin();
            this.object_batch.draw(img_baby,b.getPosition().y,b.getPosition().x,150,150);
            this.object_batch.end();
			this.pos=b.getPosition();
		}

/**
 * Apparition du loup et mv
 * et reset de position quand il va trop loin
 */
		if ((this.game_.getNbBunnies()%10) == 0 && this.game_.getNbBunnies()!= 0 ){
			this.game_.hunt(this.pos);
            this.object_batch.begin();
            this.object_batch.draw(wolf,this.game_.getWolf().getPosition().y,this.game_.getWolf().getPosition().x,150,150);
            this.object_batch.end();
			this.game_.getWolf().move(this.game_.getNbBunnies()/10);
            Gdx.app.log("wolf","pos lapon "+this.game_.getSaveBaby().get(0).getPosition());
            Gdx.app.log("wolf","pos du loup "+this.game_.getWolf().getPosition());
		}
		if (this.game_.getWolf().getPosition().y>=width || this.game_.getWolf().getPosition().y<0){
			this.game_.getWolf().restPost();
		}

/**
 * Check + mv accelerometre
 */
		if (this.isOnAccelerometer) {
			if (this.mvX < 0  ){
                this.mvX=height;
			}
			if (this.mvY < 0) {
                this.mvY = width;
			}
			if (this.mvX >height){
                this.mvX=0;
			}
			if (this.mvY> width){
                this.mvY = 0;
			}

            this.mvX +=-Gdx.input.getAccelerometerX()*1.50;
            this.mvY += Gdx.input.getAccelerometerY()*2.50;
			this.game_.getBunnyHood().setPosition((int)(mvX),(int)(mvY));
			collision();
            this.player_batch.begin();
            this.player_batch.draw(this.img_bunnyHood,this.game_.getBunnyHood().getPosition().y,this.game_.getBunnyHood().getPosition().x,200,200);
            this.player_batch.end();

			Gdx.app.log("Score",Integer.toString(this.game_.getNbBunnies()));

		}
	}

	/**
	 * Verification  Sauvetage d un img_baby et capture du loup
	 */

	private void collision(){
		boolean touched = false;
		boolean hit;
		int j =0;
		while ( j< this.game_.getSaveBaby().size() && !touched){
			BabyBunnies b = this.game_.getSaveBaby().get(j);
			touched = this.game_.getBunnyHood().getPosition().dst(b.getPosition())<=80;
			hit = this.game_.getWolf().getPosition().dst(b.getPosition())<=80;
			if (touched){
				this.game_.setBabySaved(b);
			}
			if (hit){
				this.game_.wolfCatchBunnies(b);
				/**
				 * Test de callback, retour vers l app android
				 */
				if(this.game_.getScoreWolf()==3){
					if (this.gameCallBack != null) {

                        this.gameCallBack.gameOverActivity(this.game_.getScoreBunny());
						this.isGameOver=true;

					} else {
						Gdx.app.log("MyGame", "To use this class you must implement MyGameCallback!");
					}
				}
			}
			j++;
		}
	}

	@Override
	public void render () {

		if(!this.isGameOver) {
			this.boardBatch.setProjectionMatrix(cam.combined);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			this.boardBatch.begin();
			this.mapSprite.draw(this.boardBatch);
			this.boardBatch.end();
			startGame();
		}else {
			reTry();
			gameOver();
		}
	}

	@Override
	public void dispose () {
		this.boardBatch.dispose();
		this.img_bunnyHood.dispose();
		this.mapSprite.getTexture().dispose();
		this.player_batch.dispose();
		this.object_batch.dispose();
		this.img_baby.dispose();
		this.mvY=0;
		this.mvX=0;
	}


	public  void gameOver(){
		this.boardBatch.begin();
		this.endSprite.draw(this.boardBatch);
		this.boardBatch.end();
		this.font.getData().setScale(5,5);
		this.object_batch.begin();
		this.font.draw(this.object_batch,this.txt,width/2,height/2);
		this.object_batch.end();
    }

	private void reTry(){
		if(Gdx.input.justTouched() && this.isGameOver) {
			this.isGameOver=false;
            initGame();
		}

	}
}
