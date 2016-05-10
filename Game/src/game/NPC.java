package game;

import java.util.Random;

public class NPC extends Sprite{
	private Random r;
	public NPC(Tilemap tilemap, int tileOffset) {
		super(tilemap, tileOffset);
		r = new Random();
	}

	public void update(Level level){
		super.update(level);
		int direction = r.nextInt(4);
		super.walk(level, direction);
		
	}
	
}
