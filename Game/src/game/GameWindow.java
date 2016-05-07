package game;

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
	public GameWindow() {
		
		frame = new JFrame();
		frame.setSize(1024, 768);
		frame.setLocation(100, 100);
		frame.setUndecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
}
