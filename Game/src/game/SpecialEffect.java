package game;

import java.awt.Graphics;

public interface SpecialEffect {
	
	public void update();
	public void draw(Graphics g);
	public boolean isAlive();
	
}