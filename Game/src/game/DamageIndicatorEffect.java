package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.FontMetrics;

public class DamageIndicatorEffect implements SpecialEffect {
	
	private static final int LIFE_TIME = 100;
	private static final Font DAMAGE_FONT = new Font("Arial", Font.PLAIN, 25);
	
	private int damage;
	private int x, y;
	private boolean crit;
	
	private int lifeLeft;
	
	public DamageIndicatorEffect(int damage, int x, int y, boolean crit) {
		this.damage = damage;
		this.x = x;
		this.y = y;
		this.crit = crit;
		
		lifeLeft = LIFE_TIME;
	}
	

	@Override
	public void update() {
		y--;
		lifeLeft--;
	}

	@Override
	public void draw(Graphics g) {
		if(!crit){
			g.setColor(new Color(1, 1, 1, (float)Math.sqrt((float)lifeLeft / LIFE_TIME)));
		}else{
			g.setColor(new Color(1, 0, 0, (float)Math.sqrt((float)lifeLeft / LIFE_TIME)));
		}
		g.setFont(DAMAGE_FONT);
		
		FontMetrics fm = g.getFontMetrics();
		String s = Integer.toString(damage);
		g.drawString(s, x - fm.stringWidth(s)/2, y);
	}

	@Override
	public boolean isAlive() {
		return lifeLeft > 0;
	}
	
}
