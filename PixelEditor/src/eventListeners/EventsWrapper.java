package eventListeners;

import panels.DrawPanel;

/**
 * 
 * Helps charing variables between the handlers
 * @author Sasori
 * @version 1.0
 * @since 1.0
 */
public abstract class EventsWrapper {
	protected static boolean shiftCombination;//if the user holds the shift key this is set 
	//to true. This is used to hotkeys such as shift+0
	protected static boolean controlCombination;
	protected static boolean rectTool; //true if the rect tool is being used
	protected static boolean mouseBeingPressed; //true if the mouse is being pressed
	protected static boolean fillTool; //fills all the space available
	//helps to undo after a mouse drag
	public static DrawPanel drawPanel; //this will be shared between all handlers
	
}
