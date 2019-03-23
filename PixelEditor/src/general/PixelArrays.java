package general;
import image.Pixel;
public abstract class PixelArrays {

	public static Pixel[][] copyPixelMatrix(Pixel[][] font){
		Pixel[][] result = new Pixel[font.length][font[0].length];
		for(int i = 0; i < font.length; i++){
			for(int j = 0; j < font[i].length; j++){
				result[i][j] = font[i][j];
			}
		}
		return result;
	}
	
	/**
	 * 
	 * @param a matrix of pixels
	 * @param b matrix of pixels
	 * @return true if the pixels in the matrix are the same
	 * @since 1.0
	 */
	public static boolean samePixels(Pixel[][] a,Pixel[][] b){
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
