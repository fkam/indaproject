package game;

import java.util.ArrayList;
import java.util.Random;

public class NPC extends Sprite{
	
	private Random r;
	
	public NPC(Tilemap tilemap, int tileOffset, int x, int y) {
		super(tilemap, tileOffset, x, y);
		r = new Random();
	}

	public void update(Level level, ArrayList<Sprite> sprites){
		super.update(level, sprites);
		
		if(!isWalking()){
			int direction = r.nextInt(4);
			super.walk(level, sprites, direction);
		}
		
	}
	
}
