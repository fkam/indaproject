package game;


public class Stats {

	int level = 1;
	int xp;
	int health = 1;
	
	public void setStats(int level){
		this.level = level;
		this.xp = 0;
		health = level * 10;
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
	

	
}

