package game;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class GameWindow {
	
	private JFrame frame; //The window
	private BufferStrategy bufferStrategy; //Object for doing high-performance buffer swapping
	
	private Graphics graphics; //The Graphics object of the current frame.
	
	
	/**
	 * Creates a new game window.
	 */
	public GameWindow(int width, int height) {
		
		
		/*
		 * Swing objects can only be modifed from the AWT thread. Modifying them from the main thread
		 * can result in random errors and crashes.
		 * 
		 * SwingUtilities.invokeAndWait() is used to run the code on the AWT thread to solve this.
		 */
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				
				@Override
				public void run() {
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
			});
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
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
