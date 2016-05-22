package game;

import java.util.Arrays;
import java.util.HashSet;

public class TileType {

	public static final TileType NULL_WALKABLE = new TileType(-10000, 0, true);
	public static final TileType NULL_WALL = new TileType(-10000, 0, false);
	
	private int offset, columns;
	private boolean walkable;
	
	private HashSet<TileType> friendlyNeighbors;
	
	public TileType(int offset, int columns, boolean walkable) {
		this.offset = offset; 
		this.columns = columns;
		this.walkable = walkable;
		
		friendlyNeighbors = new HashSet<>();
		friendlyNeighbors.add(this);
	}
	
	public int getTopLeft(TileType left, TileType top, TileType diagonal){
		
		if(matches(left) && matches(top) && matches(diagonal)){
			return get(2, 4);
		}
		
		if(matches(left) && matches(top) && !matches(diagonal)){
			return get(2, 0);
		}
		
		if(matches(left) && !matches(top)){
			return get(2, 2);
		}
		
		if(!matches(left) && matches(top)){
			return get(0, 4);
		}
		
		return get(0, 2);
	}
	
	public int getTopRight(TileType right, TileType top, TileType diagonal){
		if(matches(right) && matches(top) && matches(diagonal)){
			return get(1, 4);
		}
		
		if(matches(right) && matches(top) && !matches(diagonal)){
			return get(3, 0);
		}
		
		if(matches(right) && !matches(top)){
			return get(1, 2);
		}
		
		if(!matches(right) && matches(top)){
			return get(3, 4);
		}
		
		return get(3, 2);
	}
	
	public int getBotLeft(TileType left, TileType bot, TileType diagonal){
		if(matches(left) && matches(bot) && matches(diagonal)){
			return get(2, 3);
		}
		
		if(matches(left) && matches(bot) && !matches(diagonal)){
			return get(2, 1);
		}
		
		if(matches(left) && !matches(bot)){
			return get(2, 5);
		}
		
		if(!matches(left) && matches(bot)){
			return get(0, 3);
		}
		
		return get(0, 5);
	}
	
	public int getBotRight(TileType right, TileType bot, TileType diagonal){
		if(matches(right) && matches(bot) && matches(diagonal)){
			return get(1, 3);
		}
		
		if(matches(right) && matches(bot) && !matches(diagonal)){
			return get(3, 1);
		}
		
		if(matches(right) && !matches(bot)){
			return get(1, 5);
		}
		
		if(!matches(right) && matches(bot)){
			return get(3, 3);
		}
		
		return get(3, 5);
	}
	
	private boolean matches(TileType t) {
		return friendlyNeighbors.contains(t);
	}

	public void addNeighbor(TileType t) {
		friendlyNeighbors.add(t);
	}

	public boolean isWalkable(){
		return walkable;
	}
	
	private int get(int x, int y){
		return offset + columns*y + x;
	}
}