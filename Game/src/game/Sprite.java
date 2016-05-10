package game;

import java.awt.Graphics;

public class Sprite {
	

	public static final int DIRECTION_DOWN = 0;
	public static final int DIRECTION_LEFT = 1;
	public static final int DIRECTION_RIGHT = 2;
	public static final int DIRECTION_UP = 3;
	
	
	private Tilemap tilemap;
	private int tileOffset;
	
	
	protected int x, y;
	protected int direction;
	
	private boolean walking;
	private int offsetX, offsetY;
	
	public Sprite(Tilemap tilemap, int tileOffset){
		this.tilemap = tilemap;
		this.tileOffset = tileOffset;
		
		x = 10;
		y = 10;
		direction = DIRECTION_DOWN;
		
		walking = false;
		offsetX = 0;
		offsetY = 0;
	}
	
	public void update(){
		
		if(walking){
			if(direction == DIRECTION_DOWN){
				offsetY++;
			}
			if(direction == DIRECTION_LEFT){
				offsetX--;
			}
			if(direction == DIRECTION_RIGHT){
				offsetX++;
			}
			if(direction == DIRECTION_UP){
				offsetY--;
			}
			
			if(offsetX == 0 && offsetY == 0){
				walking = false;
			}
		}
	}
	
	public void draw(Graphics g){
		
		int tileID = tileOffset + direction*tilemap.getColumns();
		
		int frame = 1;
		if(walking){
			int off = Math.abs(offsetX) + Math.abs(offsetY);
			
			frame = 0;
			if(off > 8){
				frame = 1;
			}
			if(off > 16){
				frame = 2;
			}
			if(off > 24){
				frame = 1;
			}
		}
		
		int drawX = x*32 + 16 - tilemap.getTileWidth()/2 + offsetX;
		int drawY = y*32 + 16 - tilemap.getTileHeight() + offsetY;
		
		tilemap.drawImage(g, drawX, drawY, tileID + frame);
	}
	
	public void walk(int direction){
		this.direction = direction;
		
		if(direction == DIRECTION_DOWN){
			y++;
			offsetY = -32;
		}
		
		if(direction == DIRECTION_LEFT){
			x--;
			offsetX = +32;
		}
		
		if(direction == DIRECTION_RIGHT){
			x++;
			offsetX = -32;
		}
		
		if(direction == DIRECTION_UP){
			y--;
			offsetY = +32;
		}
		walking = true;
	}
	
	public void setDirection(int direction) {
		this.direction = direction;
	}
	
	public boolean isWalking() {
		return walking;
	}
	
	public int getTileX(){
		return x;
	}
	
	public int getTileY(){
		return y;
	}
	
	public int getX() {
		return x*32 + 16 + offsetX;
	}
	
	public int getY() {
		return y*32 + 16 + offsetY;
	}
}