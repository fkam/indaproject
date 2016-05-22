package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Main {
	
	private static final int LEVEL_WIDTH = 64, LEVEL_HEIGHT = 64;
	
	
	
	private GameWindow window;
	
	Tilemap levelTilemap;
	Tilemap spriteSheet;
	Tilemap swordMap;
	
	
	
	
	private Level level;

	private ArrayList<Sprite> sprites;
	private Sprite player;

	private ArrayList<SpecialEffect> specialEffects;
	
	
	Random r = new Random();
	
	public Main() {
		window = new GameWindow(1280, 720);
		try {
			levelTilemap = new Tilemap("tilemap3.png", 32, 32, 32);
			
			spriteSheet = new Tilemap("spritesheet1.png", 12, 33, 35);
			swordMap = new Tilemap("sword.png", 4, 32, 32);
			player = new Sprite(spriteSheet, 0, swordMap, 0, 0);
			
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		generateLevel();
	}
	
	private void generateLevel(){

		
		
		int[][] tiles = new int[LEVEL_WIDTH][LEVEL_HEIGHT];
		boolean[][] walkable = new boolean[LEVEL_WIDTH][LEVEL_HEIGHT];
		for(int x = 0; x < LEVEL_WIDTH; x++){
			for(int y = 0; y < LEVEL_HEIGHT; y++){
				tiles[x][y] = 100+r.nextInt(100);
				walkable[x][y] = r.nextInt(10) != 0;
				if(!walkable[x][y]){
					tiles[x][y] = 32;
				}
			}
		}
		
		
		sprites = new ArrayList<>();
		sprites.add(player);
		
		for(int i = 0; i < 1000; i++){
			sprites.add(new NPC(spriteSheet, r.nextInt(4) * 3, swordMap, r.nextInt(LEVEL_WIDTH), r.nextInt(LEVEL_HEIGHT)));
		}
		
		specialEffects = new ArrayList<>();
		
		player.resetToIdle(LEVEL_WIDTH/2, LEVEL_HEIGHT/2);
		
		level = new Level(levelTilemap, tiles, walkable);
	}

	private void gameloop() {
		
		while(true){
			
			if(window.isKeyDown(KeyEvent.VK_L)){
				generateLevel();
			}
			
			
			Graphics g = window.getGraphics();
			
			g.setColor(Color.black);
			g.fillRect(0, 0, window.getWidth(), window.getHeight());


			g.translate(window.getWidth()/2 - player.getX(), window.getHeight()/2 - player.getY());
			
			level.draw(g);
			
			
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
				sprite.draw(g);
			}
			for(int i = 0; i < sprites.size(); i++){
				Sprite sprite = sprites.get(i);
				if(!sprite.isAlive()){
					sprites.remove(i);
					i--; //Makes sure we don't miss the next element.
				}
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
			
			
			window.swapBuffers();
			
			
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
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