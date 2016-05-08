package game;

import java.awt.Graphics;

public class Level {
	
	private Tilemap tilemap;
	private int[][] tiles;
	private boolean[][] walkable;
	
	public Level(Tilemap tilemap, int[][] tiles, boolean[][] walkable) {
		this.tiles=tiles;
		this.tilemap = tilemap;
		
	}
	
	public void draw(Graphics g){
		
		int x = 0;
		int y = 0;
		int k = 344;
		for(int j = 0; j < tiles[1].length; j++){
			for(int i = 0; i < tiles.length; i++){
				if (x>=6){
					x=0;
					k += 1;
				}
				tilemap.drawImage(g, i*32, j*32, k);
				x++;
			}
			if (y > 5){
				y = 0;
				k += 2;
			}
			y++;
		
		}
	}
	
	public boolean canWalk(int x, int y){
		return walkable[x][y];
		
		
	}
	
}