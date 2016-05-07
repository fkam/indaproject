package game;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class GameWindow {
	
	private JFrame frame; //The window
	private BufferStrategy bufferStrategy; //Object for doing high-performance buffer swapping
	
	private Graphics graphics; //The Graphics object of the current frame.
	
	
	/**
	 * Creates a new game window.
	 */
	public GameWindow(int width, int height) {
		
		frame = new JFrame();
		frame.setPreferredSize(new Dimension(width, height));
		frame.setLocation(100, 100);
		frame.setUndecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		
		frame.createBufferStrategy(2);
		bufferStrategy = frame.getBufferStrategy();

		graphics = bufferStrategy.getDrawGraphics();
	}
	
	/**
	 * Swaps the buffers, making the previously drawn stuff visible on the window, and readies the window for the next frame.
	 */
	public void swapBuffers(){
		
		graphics.dispose();
		bufferStrategy.show();
		
		graphics = bufferStrategy.getDrawGraphics();		
	}
	/**
	 * 
	 * Gets the graphics object and returns it.
	 */
	public Graphics getGraphics(){
		return graphics; 
	}
	
	public int getWidth(){
		return frame.getWidth();
	}
	
	public int getHeight(){
		return frame.getHeight();
	}
}
