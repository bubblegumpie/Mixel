package eventListeners;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import image.Pixel;
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
		}else if(controlCombination && event.getKeyCode() == KeyEvent.VK_Z
				&& drawPanel.previousAlterarion.size() > 0){
			//undo
			System.out.println(
					samePixels(drawPanel.pixels,drawPanel.previousAlterarion.peek()) + "");
			Pixel[][] newPixels = drawPanel.previousAlterarion.pop();
			
			for(int i = 0;i < newPixels.length;i++){
				for(int j = 0; j < newPixels[i].length; j++){
						drawPanel.pixels[i][j] = newPixels[i][j];
				}
			}
			System.out.println("here1");
			
		}
	}

	@Override
	public void keyReleased(KeyEvent event) {
		shiftCombination = false; //this happens when a key is released.
		//such as shift. By this means, it is no longer a hotkeys because there won't be
		//more than two being pressed at the same time
		rectTool = false;
	}

	@Override
	public void keyTyped(KeyEvent event) {}
}
