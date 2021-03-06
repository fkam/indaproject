package game;

import java.util.ArrayList;
import java.util.Random;

public class NPC extends Sprite{
	
	private Random r;
	
	public NPC(Tilemap tilemap, int tileOffset, Tilemap swordMap, int x, int y, int setLevel) {
		super(tilemap, tileOffset, swordMap, x, y, 1);
		r = new Random();
		stats.setLevel(setLevel);
		stats.setLevelUpMultiplier(2.5);
	}

	public void update(Level level, ArrayList<Sprite> sprites, ArrayList<SpecialEffect> specialEffects){
		super.update(level, sprites, specialEffects);
		
		if(!isIdle()){
			return;
		}
		
		if(r.nextInt(100) == 0){
			int direction = r.nextInt(4);
			walk(level, sprites, direction);
		}
		
		if(r.nextInt(100) == 0){
			attack(specialEffects);
		}
		
	}
	
}
