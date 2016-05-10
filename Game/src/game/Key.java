package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;


public class Key implements KeyListener{
	
	public Key(){
		addKeyListener(this);
		setFocusable(true);
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_UP){
			System.out.println("Hello, you pressed UP!");
		}
		if (key == KeyEvent.VK_LEFT){
			System.out.println("Hello, you pressed LEFT!");
		}
		if (key == KeyEvent.VK_RIGHT){
			System.out.println("Hello, you pressed RIGHT!");
		}
		if (key == KeyEvent.VK_DOWN){
			System.out.println("Hello, you pressed DOWN!");
		}
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
		
	}	
    
}
