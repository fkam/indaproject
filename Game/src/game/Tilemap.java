package game;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Tilemap {
	
	private Image image;
	private int columns;
	private int tileWidth, tileHeight;
	
	public Tilemap(String path, int columns, int tileWidth, int tileHeight) throws IOException {
		image = ImageIO.read(new File(path));
		this.columns = columns;
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
	}
	/**
	 * 
	 * @param g Graphics object 
	 * @param x Screen coordinate
	 * @param y Screen coordinate
	 * @param tileID Specific tile
	 */
	public void drawImage(Graphics g, int x, int y,int tileID){
		int sx = (tileID % columns) * 32;
		int sy = (tileID / columns) * 32;
		g.drawImage(image, x, y, x+tileWidth, y+tileHeight, sx, sy, sx+tileWidth, sy+tileHeight, null);
	}
	


}
