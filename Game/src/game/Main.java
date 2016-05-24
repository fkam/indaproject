package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Main {
	
	private static final int LEVEL_WIDTH = 32, LEVEL_HEIGHT = 32;
	
	private static final Font XP_FONT = new Font("Arial", Font.PLAIN, 30);
	
	
	
	private GameWindow window;
	
	private Tilemap levelTilemap;
	private Tilemap spriteSheet;
	private Tilemap swordMap;
	private Font damageFont;
	
	private TileType desert, grass, bush;
	
	
	

	private TileType[][] layer1;
	private TileType[][] layer2;
	
	private Level level;

	private ArrayList<Sprite> sprites;
	private Sprite player;

	private ArrayList<SpecialEffect> specialEffects;
	
	
	Random r = new Random();
	int stage = 1;
	
	public Main() {
		window = new GameWindow(1280, 720);
		try {
			levelTilemap = new Tilemap("tilemap.png", 64, 16, 16);
			
			grass = new TileType(30*levelTilemap.getColumns() + 0, levelTilemap.getColumns(), true);
			desert = new TileType(36*levelTilemap.getColumns() + 0, levelTilemap.getColumns(), true);
			bush = new TileType(30*levelTilemap.getColumns() + 4, levelTilemap.getColumns(), true);
			
			spriteSheet = new Tilemap("spritesheet1.png", 12, 33, 35);
			swordMap = new Tilemap("sword.png", 4, 32, 32);
			
			damageFont = new Font("Arial", Font.PLAIN, 30);
			
			
			player = new Sprite(spriteSheet, 0, swordMap, 0, 0,1);
			
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		generateLevel(1, 1);
	}
	
	private void generateLevel(int setLevel, int stage){


		layer1 = new TileType[LEVEL_WIDTH][LEVEL_HEIGHT];
		layer2 = new TileType[LEVEL_WIDTH][LEVEL_HEIGHT];
		for(int x = 0; x < LEVEL_WIDTH; x++){
			for(int y = 0; y < LEVEL_HEIGHT; y++){
				layer1[x][y] = desert;
				layer2[x][y] = TileType.NULL_WALKABLE;
			}
		}
		
		
		
		sprites = new ArrayList<>();
		sprites.add(player);
		
		for(int i = 0; i < stage*10; i++){
			sprites.add(new NPC(spriteSheet, r.nextInt(4) * 3, swordMap, r.nextInt(LEVEL_WIDTH), r.nextInt(LEVEL_HEIGHT), setLevel));
		}
		
		specialEffects = new ArrayList<>();
		
		player.resetToIdle(LEVEL_WIDTH/2, LEVEL_HEIGHT/2);
		
		level = new Level(levelTilemap, LEVEL_WIDTH, LEVEL_HEIGHT, 2, new TileType[][][]{layer1, layer2});
	}
	
	private void updateLevel(){
		level = new Level(levelTilemap, LEVEL_WIDTH, LEVEL_HEIGHT, 2, new TileType[][][]{layer1, layer2});
	}

	private void gameloop() {


		long time = System.nanoTime();
		
		while(true){
			
			if(window.isKeyDown(KeyEvent.VK_L)){
				generateLevel(1,stage);
			}
			

			int translateX = window.getWidth()/2 - player.getX(), translateY = window.getHeight()/2 - player.getY();
			
			int mouseX = Math.floorDiv(window.getMouseX() - translateX, 32), mouseY = Math.floorDiv(window.getMouseY() - translateY, 32);
		//	System.out.println(mouseX + ", " + mouseY);
			boolean levelChanged = false;
			if(window.isMouseButtonDown(0)){
				layer1[mouseX][mouseY] = grass;
				levelChanged = true;
			}
			if(window.isMouseButtonDown(1)){
				layer1[mouseX][mouseY] = desert;
				levelChanged = true;
			}
			if(window.isMouseButtonDown(2)){
				layer2[mouseX][mouseY] = bush;
				levelChanged = true;
			}
			
			if(levelChanged){
				updateLevel();
			}
			
			
			
			Graphics g = window.getGraphics();
			
			g.setColor(Color.black);
			g.fillRect(0, 0, window.getWidth(), window.getHeight());

			g.translate(translateX, translateY);
			
			level.drawLayer(g, 0);
			level.drawLayer(g, 1);
			
			
			
			if(window.isKeyDown(KeyEvent.VK_UP)){
				player.walk(level, sprites, Sprite.DIRECTION_UP);
			}
			if(window.isKeyDown(KeyEvent.VK_LEFT)){
				player.walk(level, sprites, Sprite.DIRECTION_LEFT);
			}
			if(window.isKeyDown(KeyEvent.VK_RIGHT)){
				player.walk(level, sprites, Sprite.DIRECTION_RIGHT);
			}
			if(window.isKeyDown(KeyEvent.VK_DOWN)){
				player.walk(level, sprites, Sprite.DIRECTION_DOWN);
			}
			if(window.isKeyDown(KeyEvent.VK_SPACE)){
				player.attack(specialEffects);
			}
			
			
			
			for(int i = 0; i < sprites.size(); i++){
				Sprite sprite = sprites.get(i);
				sprite.update(level, sprites, specialEffects);
				
			}
			
			for(int i = 0; i < sprites.size(); i++){
				Sprite sprite = sprites.get(i);
				if(!sprite.isAlive()){
					sprites.remove(i);
					i--; //Makes sure we don't miss the next element.
				}
			}

			for(int i = 0; i < sprites.size(); i++){
				Sprite sprite = sprites.get(i);
				sprite.draw(g);	
			}
			
			for(int i = 0; i < sprites.size(); i++){
				Sprite sprite = sprites.get(i);
				sprite.drawHealthBar(g);
				
			}
			
			for(int i = 0; i < specialEffects.size(); i++){
				SpecialEffect effect = specialEffects.get(i);
				effect.update();
				effect.draw(g);
			}
			for(int i = 0; i < specialEffects.size(); i++){
				SpecialEffect effect = specialEffects.get(i);
				if(!effect.isAlive()){
					specialEffects.remove(i);
					i--; //Makes sure we don't miss the next element.
				}
			}
			

			//Undo the camera transform so that we can draw the user interface
			g.translate(-translateX, -translateY);
			
			Stats s = player.getStats();

			g.setColor(new Color(0.3f, 0, 0.5f));
			g.fillRect(0, window.getHeight()-30, window.getWidth(), 30);
			
			g.setColor(new Color(0.6f, 0, 1f));
			g.fillRect(0, window.getHeight()-30, window.getWidth() * s.getXP() / s.getNeededXP(), 30);
			
			
			String statsText= ""; 
			statsText += "HP: " + s.currentHP() + " / " + s.maxHP();
			statsText += "   Level " + s.getLevel() + "   Experience: " + s.getXP() + " / " + s.getNeededXP();
			statsText += "   Stage: " + stage + "   Enemies left: " + Integer.toString(sprites.size()-1)  ;
			g.setFont(XP_FONT);
			g.setColor(Color.white);
			g.drawString(statsText, window.getWidth()/2 - g.getFontMetrics().stringWidth(statsText)/2, window.getHeight() - 5);
			
			if (sprites.size()-1 == 0){
				generateLevel(s.getLevel()+3,++stage);
			}
			window.swapBuffers();
			
			
			
			long currentTime = System.nanoTime();
			long timeTaken = currentTime - time;
			long sleepTime = (1_000_000_000 / 50 - timeTaken) / 1_000_000;
			
			if(sleepTime > 0){
				try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			time += 1_000_000_000 / 50;
		}
	}
	
	/**
	 * Entry point of the program.
	 * @param args console arguments
	 */
	public static void main(String[] args) {
		new Main().gameloop();
	}
}