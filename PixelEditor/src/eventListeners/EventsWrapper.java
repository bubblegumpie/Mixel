package eventListeners;

import java.awt.Point;
import java.awt.event.MouseEvent;

import panels.DrawPanel;

/**
 * 
 * Helps charing variables between the handlers
 * @author Sasori
 * @version 1.0
 * @since 1.0
 */
public abstract class EventsWrapper {
	public static DrawPanel drawPanel; //this will be shared between all handlers
	
	protected static final int LEFT_MOUSE_BUTTON = MouseEvent.BUTTON1;
	protected static final int SCROLL_MOUSE_BUTTON = MouseEvent.BUTTON2;
	protected static final int RIGHT_MOUSE_BUTTON = MouseEvent.BUTTON3;
	
	protected static boolean shiftCombination;//if the user holds the shift key this is set 
	//to true. This is used to hotkeys such as shift+0
	protected static boolean controlCombination;
	protected static boolean rectTool; //true if the rect tool is being used
	//protected static boolean circleTool; //true if the circle tool is being used
	protected static boolean mouseBeingPressed; //true if the mouse is being pressed
	protected static boolean fillTool; //fills all the space available
	//helps to undo after a mouse rect drag
	protected static Point rectToolStartingPoint; //the starting point of the rect tool
	//protected static Point circleToolStartingPoint; //the starting point of the circle tool
	
}
