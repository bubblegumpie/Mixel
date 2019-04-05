package eventListeners;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import general.PixelArrays;
/**
 * Handles the mouse clicks, etc
 * @author Sasori
 * @version 1.0
 * @since 1.0
 */
public class MouseClickHandler extends EventsWrapper implements MouseListener{

	private int lastOffsetX,lastOffsetY;
	@Override
	public void mouseClicked(MouseEvent event) {
		drawPanel.mouseX = event.getX();
		drawPanel.mouseY = event.getY();

		if(fillTool){ //fill tool is active
			drawPanel.handleUndoStackChange(true);
			drawPanel.fillPixels();
		}else{
			drawPanel.handleUndoStackChange(true);
			drawPanel.setPixel(event);
		}

	}

	@Override
	public void mouseEntered(MouseEvent event) {}

	@Override
	public void mouseExited(MouseEvent event) {}


	@Override
	public void mousePressed(MouseEvent event) {
		drawPanel.mouseX = event.getX();
		drawPanel.mouseY = event.getY();
		if(shiftCombination && event.getButton() == LEFT_MOUSE_BUTTON){ 
			//the user pressed shift and the left mouse button at the same time. 
			//Rect tool
			Point aux = drawPanel.getPixelPositionBasedOnMouse();
			rectToolStartingPoint = new Point(aux.x,aux.y);
			rectTool = true;
		}
	}

	@Override
	public void mouseReleased(MouseEvent event) {
		if(rectTool){
			drawPanel.handleUndoStackChange(true);
			drawPanel.rectTool(rectToolStartingPoint);
		}else if(mouseBeingPressed)
			//only puts into the stacks the whole alteration during the mouse drag
			drawPanel.handleUndoStackChange(true);
		rectTool = false;
		mouseBeingPressed = false;
	}
}
