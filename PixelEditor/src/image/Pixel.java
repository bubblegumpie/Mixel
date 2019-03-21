package image;

/**
 * This class represents a basic pixel. A pixel is composed by 4 integers
 * Every integer has a value between 0 and 255
 * <br> red </br> <br> green </br> <br> blue </br> <br> alpha </br>
 * @author Sasori
 * @version 1.0
 * @since 1.0
 *
 */
public class Pixel implements Cloneable{
	private int red,green,blue,alpha;
	
	/**
	 * 
	 * @param r the amount of red of this pixel
	 * @param g the amount of green of this pixel
	 * @param b the amount of blue of this pixel
	 * @param a the alpha value of the pixel
	 * @requires 0 <= r <= 255 && 0 <= g <= 255 && 0 <= b <= 255  &&  0 <= a <= 255
	 * @since 1.0
	 */
	public Pixel(int r,int g,int b,int a){
		red = r;
		green = g;
		blue = b;
		alpha = a;
	}

	/**
	 * 
	 * @return the amount of red of this pixel
	 * @since 1.0
	 */
	public int getRed(){
		return red;
	}
	
	/**
	 * 
	 * @return the amount of green of this pixel
	 * @since 1.0
	 */
	public int getGreen(){
		return green;
	}
	
	
	/**
	 * 
	 * @return the amount of blue of this pixel
	 * @since 1.0
	 */
	public int getBlue(){
		return blue;
	}
	
	/**
	 * 
	 * @return the alpha value of the pixel
	 * @since 1.0
	 */
	public int getAlpha(){
		return alpha;
	}
	
	/**
	 * 
	 * @param r the new amount of red of this pixel
	 * @requires 0 <= r <= 255
	 * @since 1.0
	 */
	public void setRed(int r){
		red = r;
	}
	
	/**
	 * 
	 * @param g the new amount of green of this pixel
	 * @requires 0 <= g <= 255
	 * @since 1.0
	 */
	public void setGreen(int g){
		green = g;
	}
	
	/**
	 * 
	 * @param b the new amount of blue of this pixel
	 * @requires 0 <= b <= 255
	 * @since 1.0
	 */
	public void setBlue(int b){
		blue = b;
	}
	
	/**
	 * 
	 * @param a the new alpha value of the pixel
	 * @requires 0 <= a <= 255
	 * @since 1.0
	 */
	public void setAlpha(int a){
		alpha = a;
	}

	
	/**
	 * @return the hash code of this object
	 * @since 1.0
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(alpha);
		result = prime * result + blue;
		result = prime * result + green;
		result = prime * result + red;
		return result;
	}


	/**
	 * @param obj the obj to be compared
	 * @return true is the current object is the same as obj 
	 * @since 1.0
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pixel other = (Pixel) obj;
		if (alpha != other.alpha)
			return false;
		if (blue != other.blue)
			return false;
		if (green != other.green)
			return false;
		if (red != other.red)
			return false;
		return true;
	}
	
	/**
	 * @return a copy of this object
	 * @since 1.0
	 */
	@Override
	public Pixel clone(){
		try {
			return (Pixel) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @return the textual representation of a pixel. 
	 * Ex: Red: 255 Green: 255 Blue: 255 Alpha: 10
	 * @since 1.0
	 */
	@Override
	public String toString(){
		return "Red: " + red + " Green: " + green + " Blue: " + blue + " Alpha: " + alpha;
	}
}
