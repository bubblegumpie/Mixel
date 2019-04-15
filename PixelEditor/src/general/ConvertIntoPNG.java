package general;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

import image.Pixel;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.*;
/**
 * This class converts pixels into an actual png image
 * @author Sasori
 * @version 1.0
 * @since 1.0
 */
public abstract class ConvertIntoPNG {
	
	/**
	 * 
	 * @param pixels the pixels of the image
	 * @param outputPath the outfile path of the image
	 * @param location this is the component where the message should be centered at
	 * <p>
	 * Creates a png image based on the pixels
	 * </p>
	 * @since 1.0
	 */
	public static void saveIntoFile(Pixel[][] pixels,String outputPath
			, JComponent location){
		try {
			BufferedImage image = new BufferedImage(pixels.length
					,pixels[0].length
					,BufferedImage.TYPE_INT_ARGB);
			for(int i = 0; i < pixels.length; i++){
				for(int j = 0; j < pixels[i].length; j++){
					Pixel pixel = pixels[i][j];
					int color = new Color(pixel.getRed(),pixel.getGreen(),
							pixel.getBlue(),pixel.getAlpha()).getRGB();
					image.setRGB(i, j, color);
				}
			}
			File f = new File(outputPath);
			ImageIO.write(image, "PNG",f);
			JOptionPane.showMessageDialog(null, "The image was saved in " +
			 "\n" + f.getAbsolutePath());
			
		} catch (IOException e) {
			JOptionPane.showConfirmDialog(location, "An error has occurred while "
					+ "saving the image");
			e.printStackTrace();
		}
	}
}
