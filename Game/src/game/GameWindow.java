package game;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class GameWindow implements KeyListener, MouseListener, MouseMotionListener {
	
	private JFrame frame; //The window
	private BufferStrategy bufferStrategy; //Object for doing high-performance buffer swapping
	
	private Graphics graphics; //The Graphics object of the current frame.
	
	private boolean[] pressedKeys;
	
	private int mouseX, mouseY;
	private boolean[] pressedMouseButtons;
	
	
	/**
	 * Creates a new game window.
	 */
	public GameWindow(int width, int height) {
		
		
		pressedKeys = new boolean[65536];
		pressedMouseButtons = new boolean[256];
		
		/*
		 * Swing objects can only be modified from the AWT thread. Modifying them from the main thread
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
					//frame.setLocation(100, 100);
					frame.setUndecorated(true);
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.pack();
					frame.setVisible(true);

					frame.addKeyListener(GameWindow.this);
					frame.addMouseListener(GameWindow.this);
					frame.addMouseMotionListener(GameWindow.this);
					
					frame.createBufferStrategy(2);
					bufferStrategy = frame.getBufferStrategy();

					graphics = bufferStrategy.getDrawGraphics();
				}
			});
		} catch (Exception e) {
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
	

	
	
	//Keys and mouse
	public boolean isKeyDown(int keyCode){
		if(keyCode < 0 || keyCode >= pressedKeys.length){
			return false;
		}
		return pressedKeys[keyCode];
	}

	
	public int getMouseX() {
		return mouseX;
	}
	
	public int getMouseY() {
		return mouseY;
	}
	
	public boolean isMouseButtonDown(int button){
		if(button < 0 || button >= pressedMouseButtons.length){
			return false;
		}
		return pressedMouseButtons[button];
	}
	
	
	
	//Listener functions

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		if(code < 0 || code >= pressedKeys.length){
			return;
		}
		pressedKeys[code] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		if(code < 0 || code >= pressedKeys.length){
			return;
		}
		pressedKeys[code] = false;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		int button = e.getButton() - 1; //Button indices start at usually.
		System.out.println(button);
		if(button < 0 || button >= pressedMouseButtons.length){
			return;
		}
		pressedMouseButtons[button] = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		int button = e.getButton() - 1; //Button indices start at usually.
		if(button < 0 || button >= pressedMouseButtons.length){
			return;
		}
		pressedMouseButtons[button] = false;
	}
	
	
	
	//Unused events

	@Override
	public void keyTyped(KeyEvent e) {}
	
	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}
}
