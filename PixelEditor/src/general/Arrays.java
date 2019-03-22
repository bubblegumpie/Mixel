package general;
import image.Pixel;
public abstract class Arrays {

	public static Pixel[][] copyPixelMatrix(Pixel[][] font){
		Pixel[][] result = new Pixel[font.length][font[0].length];
		for(int i = 0; i < font.length; i++){
			for(int j = 0; j < font[i].length; j++){
				result[i][j] = font[i][j];
			}
		}
		return result;
	}
}
