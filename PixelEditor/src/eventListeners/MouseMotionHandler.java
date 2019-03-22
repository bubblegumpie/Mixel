package eventListeners;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import panels.DrawPanel;
/**
 * Handles the mouse drag
 * @author Sasori
 * @version 1.0
 * @since 1.0
 */
public class MouseMotionHandler extends EventsWrapper implements MouseMotionListener{
	private DrawPanel drawPanel;

	/**
	 * 
	 * @param drawPanel the drawPanel where this handler is going to be applied to
	 * @since 1.0
	 */
	public MouseMotionHandler(DrawPanel drawPanel){
		this.drawPanel = drawPanel;
	}

	@Override
	public void mouseDragged(MouseEvent event) {
		drawPanel.mouseX = event.getX();
		drawPanel.mouseY = event.getY();
		if(!rectTool)
			drawPanel.setPixel(event);
		
	}

	@Override
	public void mouseMoved(MouseEvent event) {
		//hover the current pixel
		drawPanel.mouseX = event.getX();
		drawPanel.mouseY = event.getY();
		drawPanel.setHoverLocation(event);
	}
		
}
