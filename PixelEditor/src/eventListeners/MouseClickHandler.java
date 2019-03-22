package eventListeners;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import general.Arrays;
/**
 * Handles the mouse clicks, etc
 * @author Sasori
 * @version 1.0
 * @since 1.0
 */
public class MouseClickHandler extends EventsWrapper implements MouseListener{

	private static final int LEFT_MOUSE_BUTTON = MouseEvent.BUTTON1;
	private static final int MIDDLE_MOUSE_BUTTON = MouseEvent.BUTTON2;
	private static final int RIGHT_MOUSE_BUTTON = MouseEvent.BUTTON3;

	@Override
	public void mouseClicked(MouseEvent event) {
		drawPanel.mouseX = event.getX();
		drawPanel.mouseY = event.getY();
		drawPanel.setPixel(event);
		drawPanel.previousAlterarion.push(drawPanel.pixels);
	}

	@Override
	public void mouseEntered(MouseEvent event) {}

	@Override
	public void mouseExited(MouseEvent event) {}

	private Point startingPoint; //the starting point of the stroke
	@Override
	public void mousePressed(MouseEvent event) {
		drawPanel.mouseX = event.getX();
		drawPanel.mouseY = event.getY();
		if(shiftCombination && event.getButton() == LEFT_MOUSE_BUTTON){ 
			//the user pressed shift and the left mouse button at the same time. 
			//Rect tool
			if(!rectTool){
				Point aux = drawPanel.getPixelPositionBasedOnMouse();
				startingPoint = new Point(aux.x,aux.y);
				rectTool = true;
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent event) {
		if(rectTool){
			Point destination = drawPanel.getPixelPositionBasedOnMouse();

			System.out.println("Start: " + startingPoint.toString());
			System.out.println("End: " + destination.toString());
			int xOffset = Math.abs(startingPoint.x - destination.x);
			int yOffset = Math.abs(startingPoint.y - destination.y);
			System.out.println("xOffset: " + xOffset + "\nyOffset: " + yOffset);

			int translateX = startingPoint.x > destination.x ? 1 : -1;
			//if this is set to 1 it means to move left, -1 otherwise
			int translateY = startingPoint.y > destination.y ? 1 : -1;
			//if this is set to 1 it means to move up, -1 otherwise
			System.out.println("translateX: " + translateX + "\ntranslateY: "
					+ translateY);
			//fill horizontals
			for(int x = 0; x <= xOffset; x++){
				drawPanel.pixels[startingPoint.x - x*translateX][startingPoint.y]
						= drawPanel.getCurrentPixelColor().clone();
				drawPanel.pixels[startingPoint.x - x*translateX] 
						[startingPoint.y + yOffset*translateY*-1]
								= drawPanel.getCurrentPixelColor().clone();
			}

			for(int y = 0; y <= yOffset; y++){
				drawPanel.pixels[startingPoint.x]
						[startingPoint.y - y*translateY]
								= drawPanel.getCurrentPixelColor().clone();
				drawPanel.pixels[startingPoint.x - xOffset*translateX]
						[startingPoint.y - y*translateY]
								= drawPanel.getCurrentPixelColor().clone();
			}
			rectTool = false;
		}
		
		drawPanel.previousAlterarion.push(Arrays.copyPixelMatrix(drawPanel.pixels));
	}
}
