package eventListeners;

import image.Pixel;

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
	
	/**
	 * 
	 * @param a matrix of pixels
	 * @param b matrix of pixels
	 * @return true if the pixels in the matrix are the same
	 * @since 1.0
	 */
	protected boolean samePixels(Pixel[][] a,Pixel[][] b){
		if(b == null || a == null)
			return false;
		if(a.length != b.length)
			return false;
		for(int i = 0;i < a.length;i++){
			for(int j = 0; j < a[i].length; j++){
				if(!a[i][j].equals(b[i][j]))
					return false;
			}
		}
		return true;
	}
}
