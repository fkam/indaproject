package game;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Tilemap {
	private Image image;
	private int column;
	public Tilemap(String path, int column) throws IOException {
		image = ImageIO.read(new File(path));
		this.column = column;
	}
	public void drawImage(Graphics g, int x, int y,int tileID){
		int sx = (tileID % column) * 32;
		int sy = (tileID / column) * 32;
		g.drawImage(image, x, y, x+32, y+32, sx, sy, sx+32, sy+32, null);
	}
	


}
