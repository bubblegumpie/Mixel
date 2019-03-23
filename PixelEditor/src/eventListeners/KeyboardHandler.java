package eventListeners;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import panels.*;
/**
 * This class is responsible for handling all the keyboard input of the application
 * @author Sasori
 * @version 1.0
 * @since 1.0
 */
public class KeyboardHandler extends EventsWrapper implements KeyListener{

	
	private final static int ERASE = KeyEvent.VK_DELETE;
	private final static int CONTROL = KeyEvent.VK_CONTROL;
	private final static int FILL_KEY = KeyEvent.VK_B;
	
	@Override
	public void keyPressed(KeyEvent event) {
		//if the key pressed was a number
		boolean numberPressed = event.getKeyCode() == KeyEvent.VK_0
				|| event.getKeyCode() == KeyEvent.VK_1
				|| event.getKeyCode() == KeyEvent.VK_2
				|| event.getKeyCode() == KeyEvent.VK_3
				|| event.getKeyCode() == KeyEvent.VK_4
				|| event.getKeyCode() == KeyEvent.VK_5
				|| event.getKeyCode() == KeyEvent.VK_6
				|| event.getKeyCode() == KeyEvent.VK_7
				|| event.getKeyCode() == KeyEvent.VK_8
				|| event.getKeyCode() == KeyEvent.VK_9;
		
		if(KeyEvent.VK_SHIFT == event.getKeyCode())
			shiftCombination = true;
		else if(shiftCombination && numberPressed){ 
			//change the color of a certain key
			drawPanel.setLastPressedKey(event.getKeyCode());
			int key = drawPanel.getLastPressedKey()- 48;
			drawPanel.setPixelKeyboardColors(ChooseColorPanel.getColor().clone(),key);
			drawPanel.setCurrentPixelColor(drawPanel.getPixelKeyboardColors()[key].clone());; 
			shiftCombination = false;
		}else if (numberPressed){
			//no combination means changing the current color
			drawPanel.setLastPressedKey(event.getKeyCode());
			int key = drawPanel.getLastPressedKey()- 48;
			drawPanel.setCurrentPixelColor(drawPanel.getPixelKeyboardColors()[key].clone());
		}else if(event.getKeyCode() == ERASE)//erase pixel is set to the current pixel
			drawPanel.setCurrentPixelColor(DrawPanel.ERASE);
		else if(event.getKeyCode() == CONTROL){
			controlCombination = true;
		}else if(controlCombination && event.getKeyCode() == KeyEvent.VK_Z){
			//undo
			drawPanel.handleUndoStackChange(false);
		}else if(event.getKeyCode() == FILL_KEY){ //fill option
			fillTool = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent event) {
		//this happens when a key is released.
		//such as shift. By this means, it is no longer a hotkey because there won't be
		//more than two being pressed at the same time
		shiftCombination = false; 
		rectTool = false;
		fillTool = false;
	}

	@Override
	public void keyTyped(KeyEvent event) {}
}
