package eventListeners;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
/**
 * Handles the mouse drag
 * @author Sasori
 * @version 1.0
 * @since 1.0
 */
public class MouseMotionHandler extends EventsWrapper implements MouseMotionListener{
	
	@Override
	public void mouseDragged(MouseEvent event) {
		drawPanel.mouseX = event.getX();
		drawPanel.mouseY = event.getY();
		mouseBeingPressed = true;
		if(!rectTool){
			//there is no need to add this to the previous alteration
			//we only need to add the WHOLE alteration, that is:
			//all the pixels changed after the release of the mouse
			drawPanel.setPixel(event);
			
		}
		
	}

	@Override
	public void mouseMoved(MouseEvent event) {
		//hover the current pixel
		drawPanel.mouseX = event.getX();
		drawPanel.mouseY = event.getY();
		drawPanel.setHoverLocation(event);
	}
		
}
