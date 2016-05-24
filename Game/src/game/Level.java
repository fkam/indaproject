package game;

import java.awt.Graphics;
import java.util.Arrays;

public class Level {
	
	private Tilemap tilemap;
	
	private int width, height, layers;
	
	private int[][][] tiles;
	private boolean[][] walkable;
	
	public Level(Tilemap tilemap, int width, int height, int layers, TileType[][][] tileTypes) {
		
		this.tilemap = tilemap;
		
		this.width = width;
		this.height = height;
		this.layers = layers;
		
		generateTiles(tileTypes);
	}

	private void generateTiles(TileType[][][] tileTypes) {
		
		tiles = new int[layers][width*2][height*2];
		
		
		walkable = new boolean[width][height];
		for(int i = 0; i < width; i++){
			Arrays.fill(walkable[i], true);
		}
		
		
		for(int i = 0; i < layers; i++){
			
			TileType[][] types = tileTypes[i];
			int[][] layer = tiles[i];
			
			
			for(int x = 0; x < width; x++){
				for(int y = 0; y < height; y++){
					
					TileType tl = get(types, x-1, y-1);
					TileType t = get(types, x, y-1);
					TileType tr = get(types, x+1, y-1);

					TileType l = get(types, x-1, y);
					TileType c = get(types, x, y);
					TileType r = get(types, x+1, y);
					
					TileType bl = get(types, x-1, y+1);
					TileType b = get(types, x, y+1);
					TileType br = get(types, x+1, y+1);
					

					layer[x*2 + 0][y*2 + 0] = c.getTopLeft(l, t, tl);
					layer[x*2 + 1][y*2 + 0] = c.getTopRight(r, t, tr);

					layer[x*2 + 0][y*2 + 1] = c.getBotLeft(l, b, bl);
					layer[x*2 + 1][y*2 + 1] = c.getBotRight(r, b, br);
					
					walkable[x][y] &= c.isWalkable();
					
				}
			}
			
			
			
		}
	}
	
	private TileType get(TileType[][] types, int x, int y){
		if(x < 0 || x >= width || y < 0 || y >= height){
			return TileType.NULL_WALKABLE;
		}
		return types[x][y];
	}

	public void drawLayer(Graphics g, int layer){
		
		int[][] tiles = this.tiles[layer];
		
		for(int x = 0; x < tiles.length; x++){
			for(int y = 0; y < tiles[x].length; y++){
				int tile = tiles[x][y];
				if(tile < 0){
					continue;
				}
				tilemap.drawImage(g, x*tilemap.getTileWidth(), y*tilemap.getTileHeight(), tiles[x][y]);
			}	
		}
	}
	
	public boolean canWalk(int x, int y){
		if (x >= width || x < 0 || y >= height || y < 0){
			return false;
		}
		return walkable[x][y];
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
}