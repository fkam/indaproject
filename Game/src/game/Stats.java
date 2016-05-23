package game;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JProgressBar;

public class Stats {

	int level = 1;
	int xp;
	int health = 1;
	int maxhealth;

	
	public void setLevel(int level){
		
		this.level = level;
		this.xp = 0;
		health = level * 10;
		maxhealth = health;

	}
			
	public int getDamage(){		
		return level + 3;
	}
		
	public boolean isAlive(){
		return (health > 0);
				
	}

	public void hit(int damage){
		
		health -= damage;
		if (health < 0) {
			health = 0;
		}
		
	}
	

	public void draw(Graphics g, int x, int y){
		
		
		float red = 1 - (float)health/maxhealth;
		float green = (float)health/maxhealth;
		
		float divider = Math.max(red, green);
		
		g.setColor(new Color(red/divider, green/divider, 0));
		g.fillRect(x-15, y, 30*health/maxhealth, 5);
		
		
		g.setColor(Color.BLACK);
		g.drawRect(x-15, y, 30, 5);
		
		
	}
	
}

