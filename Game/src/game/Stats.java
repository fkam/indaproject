package game;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JProgressBar;

public class Stats {

	private int level = 1;
	private int xp;
	private int health = 1;
	private int maxHealth;
	
	private double levelUpMultiplier;

	
	public void setLevel(int level){
		
		this.level = level;
		
		xp = 0;
		health = level * 10;
		maxHealth = health;
		
		levelUpMultiplier = 10;

	}
	
	public void setLevelUpMultiplier(double levelUpMultiplier) {
		this.levelUpMultiplier = levelUpMultiplier;
	}
	

	public int currentHP(){
		return health;
	}
	
	public int maxHP(){
		return maxHealth;
	}
	
	
	public boolean giveXP(int xp) {
		this.xp += xp;
		
		if(this.xp >= level*levelUpMultiplier){
			setLevel(level+1);
			return true;
		}
		return false;
	}
	
	public int getXP() {
		return xp;
	}
	
	public int getNeededXP(){
		return (int)(level*levelUpMultiplier);
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
		
		
		float red = 1 - (float)health/maxHealth;
		float green = (float)health/maxHealth;
		
		float divider = Math.max(red, green);
		
		g.setColor(new Color(red/divider, green/divider, 0));
		g.fillRect(x-15, y, 30*health/maxHealth, 5);
		
		
		g.setColor(Color.BLACK);
		g.drawRect(x-15, y, 30, 5);
		
		
	}

	public int getLevel() {
		return level;
	}
	
}

