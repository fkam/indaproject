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
		for(int x = 0; x < tiles.length; x++){
			for(int y = 0; y < tiles[x].length; y++){
				tilemap.drawImage(g, x*32, y*32, tiles[x][y]);
			}	
		}
	}
	
	public boolean canWalk(int x, int y){
		return walkable[x][y];
		
		
	}
	
}