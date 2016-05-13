package game;

import java.awt.Graphics;

public class SwordEffect implements SpecialEffect{
	
	private Tilemap tilemap;
	
	private int x, y;
	private int direction;
	
	private int timeLeft;
	
	public SwordEffect(Tilemap tilemap, int x, int y, int direction, int time) {
		
		this.tilemap = tilemap;
		this.x = x;
		this.y = y;
		this.direction = direction;
		
		timeLeft = time;
	}

	@Override
	public void update() {
		timeLeft--;
	}

	@Override
	public void draw(Graphics g) {
		
		int drawX = x*32 + 16 - tilemap.getTileWidth()/2;
		int drawY = y*32 + 16 - tilemap.getTileHeight();
		tilemap.drawImage(g, drawX, drawY, direction);
		
	}

	@Override
	public boolean isAlive() {
		return timeLeft > 0;
	}
	
	
}