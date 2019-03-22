package eventListeners;
import java.awt.Point;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import general.Pair;
import panels.DrawPanel;
/**
 * This class handles the wheeling of the mouse
 * @author Sasori
 * @version 1.0
 * @since 1.0
 */
public class MouseWheelHandler extends EventsWrapper implements MouseWheelListener{

	@Override
	public void mouseWheelMoved(MouseWheelEvent event) {
		int scale = drawPanel.getCurrentScale();
		Point newMin = null; 
		Point newMax = null;
		
		if (event.getWheelRotation() < 0 && scale < DrawPanel.MAX_SCALE){ 
			//mouse wheel up, zoom in
			Point lastPoint = new Point(drawPanel.getPixelPositionBasedOnMouse());
			scale++;
						
			Point lastMin = new Point(drawPanel.getMinPoint().x,drawPanel.getMinPoint().y);
			Point lastMax = new Point(drawPanel.getMaxPoint().x,drawPanel.getMaxPoint().y);
			//add the previous point to stack in order to zoom out after
			drawPanel.previousPoints.push(new Pair<Point,Point>(lastMin,lastMax));
							
			newMin = new Point(Math.max(lastPoint.x - 
					(drawPanel.getDrawWidth()/scale),0)
					,Math.max(lastPoint.y - 
							(drawPanel.getDrawHeight()/scale),0));
			newMax =  new Point(Math.min(lastPoint.x + (drawPanel.getDrawWidth()/scale)
					,drawPanel.getDrawWidth())
					,Math.min(lastPoint.y + (drawPanel.getDrawHeight()/scale)
							,drawPanel.getDrawHeight()));
			
		}else if (event.getWheelRotation() > 0 && scale > 1){ 
			//mouse wheel down, zoom out
			if(drawPanel.previousPoints.size() != 0){
				Pair<Point,Point> previousPoints = drawPanel.previousPoints.pop();
				Point min = previousPoints.getE();
				Point max = previousPoints.getV();
				
				newMin = new Point(min.x,min.y);
				newMax = new Point(max.x,max.y);
								
				scale--;
			}
		}

		if(newMin != null && newMax != null){
			drawPanel.setMinPoint(newMin);
			drawPanel.setMaxPoint(newMax);
			drawPanel.setCurrentScale(scale);
		}

	}

}
