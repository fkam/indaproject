package game;

import java.awt.Graphics;
import java.util.ArrayList;

import static game.Sprite.State.*; //Makes it possible to write IDLE instead of State.IDLE.


public class Sprite {
	
	private static final int ATTACK_LENGTH = 50;
	private static final int ATTACK_TIME = 25;
	

	public static final int DIRECTION_DOWN = 0;
	public static final int DIRECTION_LEFT = 1;
	public static final int DIRECTION_RIGHT = 2;
	public static final int DIRECTION_UP = 3;
	
	
	private Tilemap tilemap;
	private int tileOffset;
	
	private Tilemap swordMap;
	
	private Stats stats;
	
	
	
	protected int x, y;
	protected int direction;
	
	
	
	protected enum State{
		IDLE,
		WALKING,
		ATTACKING,
		DEAD,
		//USING_SPECIAL_ATTACK
	}
	private State state;
	

	private int offsetX, offsetY;
	
	private int attackFrame;
	
	
	
	
	public Sprite(Tilemap tilemap, int tileOffset, Tilemap swordMap, int x, int y){
		this.tilemap = tilemap;
		this.tileOffset = tileOffset;
		
		this.swordMap = swordMap;
		
		this.x = x;
		this.y = y;
		direction = DIRECTION_DOWN;
		
		
		state = IDLE;
		
		offsetX = 0;
		offsetY = 0;
		
		attackFrame = 0;
		stats = new Stats();
		stats.setLevel(1);
	}
	
	public void update(Level level, ArrayList<Sprite> sprites, ArrayList<SpecialEffect> specialEffects){
		
		if(state == WALKING){
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
				state = IDLE;
			}
		}
		
		if(state == ATTACKING){
			attackFrame++;
			
			if(attackFrame == ATTACK_TIME){
				int fx = getFacingX();
				int fy = getFacingY();
				Sprite target = getSpriteAt(fx, fy, sprites);
				if(target != null){
					
					int baseDamage = stats.getDamage();
					double modifier = 0.8 + 0.4*Math.random();
					
					boolean crit = false;
					if(target.getDirection() == direction){
						modifier *= 2;
						crit = true;
					}
					
					int damage = (int)Math.round(baseDamage * modifier);
					
					target.damage(damage);
					specialEffects.add(new DamageIndicatorEffect(damage, target.getX(), target.getY(), crit));
				}
			}
			
			if(attackFrame == ATTACK_LENGTH){
				state = IDLE;
			}
		}
		
	}
	
	private void damage(int damage) {
		stats.hit(damage);
		
	}

	public void draw(Graphics g){
		
		int tileID = tileOffset + direction*tilemap.getColumns();
		
		int frame = 1;
		if(state == WALKING){
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
	
	public void drawHealthBar(Graphics g) {
		stats.draw(g, getX(), getY());
		
	}
	
	
	public void walk(Level level, ArrayList<Sprite> sprites, int direction){
		if (state != IDLE){ 
			return; 
		}
		
		this.direction = direction;
		if(direction == DIRECTION_DOWN){
			if (level.canWalk(x, y+1) && getSpriteAt(x, y+1, sprites) == null){
				y++;
				offsetY = -32;
				state = WALKING;
			}
		}
		if(direction == DIRECTION_LEFT){
			if (level.canWalk(x-1, y) && getSpriteAt(x-1, y, sprites) == null){
				x--;
				offsetX = +32;
				state = WALKING;
			}
		}
		if(direction == DIRECTION_RIGHT){
			if (level.canWalk(x+1, y) && getSpriteAt(x+1, y, sprites) == null){
				x++;
				offsetX = -32;
				state = WALKING;
			}
		}
		if(direction == DIRECTION_UP){
			if (level.canWalk(x, y-1) && getSpriteAt(x, y-1, sprites) == null){
				y--;
				offsetY = +32;
				state = WALKING;
			}
		}
	}
	
	public void attack(ArrayList<SpecialEffect> specialEffects){
		if(state != IDLE){
			return;
		}
		
		state = ATTACKING;
		attackFrame = 0;
		
		specialEffects.add(new SwordEffect(swordMap, getFacingX(), getFacingY(), direction, ATTACK_LENGTH));
	}
	
	private Sprite getSpriteAt(int x, int y, ArrayList<Sprite> sprites){
		for(int i = 0; i < sprites.size(); i++){
			Sprite s = sprites.get(i);
			if(s.getTileX() == x && s.getTileY() == y){
				return s;
			}
		}
		return null;
	}
	
	
	public void setDirection(int direction) {
		this.direction = direction;
	}

	public void setPosition(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public void resetToIdle(int x, int y){
		
		this.x = x;
		this.y = y;
		
		state = IDLE;
		offsetX = 0;
		offsetY = 0;
		
		direction = DIRECTION_DOWN;
	}
	
	public boolean isIdle(){
		return state == IDLE;
	}
	
	public boolean isWalking() {
		return state == WALKING;
	}
	
	public int getTileX(){
		return x;
	}
	
	public int getTileY(){
		return y;
	}
	
	public void kill(){
		state = DEAD;
	}
	
	public boolean isAlive(){
		return state != DEAD && stats.isAlive() ;
	}
	
	public int getDirection() {
		return direction;
	}
	
	public int getFacingX(){
		if(direction == DIRECTION_LEFT){
			return x - 1;
		}
		if(direction == DIRECTION_RIGHT){
			return x + 1;
		}
		return x;
	}
	
	public int getFacingY(){
		if(direction == DIRECTION_UP){
			return y - 1;
		}
		if(direction == DIRECTION_DOWN){
			return y + 1;
		}
		return y;
	}
	
	public int getX() {
		return x*32 + 16 + offsetX;
	}
	
	public int getY() {
		return y*32 + 16 + offsetY;
	}

	
}