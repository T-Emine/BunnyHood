package be.ecam.sept;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

/**
 * Created by sen on 24-04-18.
 * Update by emino on 24-05-18
 */

public class AndroidLauncher extends AndroidApplication implements MainBunnyHood.MyGameCallback {
	private DatabaseAccess db;
	MainBunnyHood bunnyHood;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		DisplayMetrics metrics = new DisplayMetrics();
		display.getMetrics(metrics);
		int width = metrics.widthPixels;
		int height = metrics.heightPixels;
		this.bunnyHood = new MainBunnyHood(width,height);
		bunnyHood.setGameCallBack(this);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(bunnyHood, config);
	}

	@Override
	public void gameOverActivity(int score) {

	    db = new DatabaseAccess();
	    long max = db.read();
	    if(score>max){
	    	max=score;
	    	db.write(score);
		}
		this.bunnyHood.txt="GAME OVER \n \n Highest Score: "+ max+"\n Tape to continue";	}
}
