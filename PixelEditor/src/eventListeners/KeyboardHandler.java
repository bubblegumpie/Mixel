package eventListeners;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JOptionPane;

import general.ConvertIntoPNG;
import panels.*;
/**
 * This class is responsible for handling all the keyboard input of the application
 * @author Sasori
 * @version 1.0
 * @since 1.0
 */
public class KeyboardHandler extends EventsWrapper implements KeyListener{

	
	private static final int ERASE = KeyEvent.VK_DELETE;
	private static final int CONTROL = KeyEvent.VK_CONTROL;
	private static final int FILL_KEY = KeyEvent.VK_B;
	private static final int UNDO_KEY = KeyEvent.VK_Z;
	private static final int REDO_KEY = KeyEvent.VK_Y;
	private static final int SAVE_KEY = KeyEvent.VK_S;
	
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
		}else if(controlCombination && event.getKeyCode() == UNDO_KEY){
			//undo
			drawPanel.handleUndoStackChange(false);
		}else if(controlCombination && event.getKeyCode() == REDO_KEY){
			//redo
			drawPanel.redo();
		}else if(event.getKeyCode() == FILL_KEY){ //fill option
			fillTool = true;
		}else if(event.getKeyCode() == SAVE_KEY && controlCombination){
			String name = JOptionPane.showInputDialog("Choose the image's name");
			ConvertIntoPNG.saveIntoFile(drawPanel.pixels, name + ".png",drawPanel);
			JOptionPane.showMessageDialog(drawPanel, "File saved");
		}
	}

	@Override
	public void keyReleased(KeyEvent event) {
		//this happens when a key is released.
		//such as shift. By this means, it is no longer a hotkey because there won't be
		//more than two being pressed at the same time
		shiftCombination = false; 
		rectTool = false;
		//circleTool = false;
		controlCombination = false;
		fillTool = false;
	}

	@Override
	public void keyTyped(KeyEvent event) {}
}
