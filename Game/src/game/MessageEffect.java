package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.FontMetrics;

public class MessageEffect implements SpecialEffect {
	
	private static final int LIFE_TIME = 100;
	private static final Font FONT = new Font("Arial", Font.PLAIN, 25);
	
	private String message;
	private int x, y;
	private float r, g, b;
	
	private int lifeLeft;
	
	public MessageEffect(String message, int x, int y, float r, float g, float b) {
		
		this.message = message;
		
		this.x = x;
		this.y = y;
		
		this.r = r;
		this.g = g;
		this.b = b;
		
		lifeLeft = LIFE_TIME;
	}
	

	@Override
	public void update() {
		y--;
		lifeLeft--;
	}

	@Override
	public void draw(Graphics graphics) {
		graphics.setColor(new Color(r, g, b, (float)Math.sqrt((float)lifeLeft / LIFE_TIME)));
		graphics.setFont(FONT);
		
		FontMetrics fm = graphics.getFontMetrics();
		graphics.drawString(message, x - fm.stringWidth(message)/2, y);
	}

	@Override
	public boolean isAlive() {
		return lifeLeft > 0;
	}
	
}
