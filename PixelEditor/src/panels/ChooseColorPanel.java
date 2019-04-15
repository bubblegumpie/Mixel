package panels;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import image.Pixel;

import java.awt.event.*;
import java.awt.*;
/**
 * Creates a panel that allows to choose a color in rgba format.
 * A color is represented by a Pixel. 
 * A Pixel is composed by four integers between 0 and 255.
 * <br>Red</br><br>Green</br><br>Blue</br><br>Alpha</br>
 * @author Sasori
 * @version 1.0
 * @since 1.0
 */
public class ChooseColorPanel extends JPanel{

	private static final long serialVersionUID = 1L;

	public static final int WIDTH = 350; //the width of this panel
	public static final int HEIGHT = 150; //the height of this panel
	private JSlider redSlider,greenSlider,blueSlider,alphaSlider;
	private JTextField currentRed,currentGreen,currentBlue,currentAlpha;
	private static Pixel pixel; //represents the current color (rgba format)
		
	/**
	 * Creates the panel that allows to choose a color in rgb format.
	 * This panel can be added to a JFrame or JPanel.
	 * @since 1.0
	 */
	public ChooseColorPanel(){
		this.setLayout(new GridLayout(1,2));
		JPanel rgbWrapper = new JPanel(new GridLayout(4,2));

		JPanel color = new JPanel(new GridLayout(1,1));
		pixel = new Pixel(255,255,255,255);
		color.setBackground(new Color(pixel.getRed(),pixel.getGreen(),pixel.getBlue()));

		//Defines the color red slider and the color red text field
		//also defines the events to change the current color
		//It also adds to the components to the panel

		currentRed = new JTextField("255");
		redSlider = new JSlider(0,255,255);
		currentRed.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event) {
				pixel.setRed(Integer.parseInt(currentRed.getText()));
				redSlider.setValue(pixel.getRed());
				color.setBackground(new Color(pixel.getRed(),pixel.getGreen(),pixel.getBlue(),
						pixel.getAlpha()));
			}
		});

		JPanel aux = new JPanel(new GridLayout(1,2));
		aux.add(new JLabel("Red:"));
		aux.add(currentRed);

		redSlider.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent event) {
				pixel.setRed(redSlider.getValue());
				currentRed.setText("" + pixel.getRed());
				color.setBackground(new Color(pixel.getRed(),pixel.getGreen(),pixel.getBlue(),
						pixel.getAlpha()));
			}

		});
		rgbWrapper.add(redSlider);
		rgbWrapper.add(aux);

		//Defines the color green slider and the color green text field
		//and the events to change the current color
		//It also adds to the components to the panel

		currentGreen = new JTextField("255");
		greenSlider = new JSlider(0,255,255);
		currentGreen.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event) {
				pixel.setGreen(Integer.parseInt(currentGreen.getText()));
				greenSlider.setValue(pixel.getGreen());
				color.setBackground(new Color(pixel.getRed(),pixel.getGreen(),pixel.getBlue(),
						pixel.getAlpha()));
			}
		});

		aux = new JPanel(new GridLayout(1,2));
		aux.add(new JLabel("Green:"));
		aux.add(currentGreen);

		greenSlider.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent event) {
				pixel.setGreen(greenSlider.getValue());
				currentGreen.setText("" + pixel.getGreen());
				color.setBackground(new Color(pixel.getRed(),pixel.getGreen(),pixel.getBlue(),
						pixel.getAlpha()));
			}

		});
		rgbWrapper.add(greenSlider);
		rgbWrapper.add(aux);

		//Defines the color blue slider and the color blue text field
		//also defines the events to change the current color	
		//It also adds to the components to the panel

		currentBlue = new JTextField("255");
		blueSlider = new JSlider(0,255,255);
		currentBlue.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event) {
				pixel.setBlue(Integer.parseInt(currentBlue.getText()));
				blueSlider.setValue(pixel.getBlue());
				color.setBackground(new Color(pixel.getRed(),pixel.getGreen(),pixel.getBlue(),
						pixel.getAlpha()));
			}
		});

		aux = new JPanel(new GridLayout(1,2));
		aux.add(new JLabel("Blue:"));
		aux.add(currentBlue);

		blueSlider.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent event) {
				pixel.setBlue(blueSlider.getValue());
				currentBlue.setText("" + pixel.getBlue());
				color.setBackground(new Color(pixel.getRed(),pixel.getGreen(),pixel.getBlue(),
						pixel.getAlpha()));
			}

		});
		rgbWrapper.add(blueSlider);
		rgbWrapper.add(aux);

		//Defines the alpha slider and the alpha blue text field
		//also defines the events to change the current color	
		//It also adds to the components to the panel

		currentAlpha = new JTextField("255");
		alphaSlider = new JSlider(0,255,255);
		currentAlpha.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event) {
				pixel.setAlpha(Integer.parseInt(currentAlpha.getText()));
				alphaSlider.setValue(pixel.getAlpha());
				color.setBackground(new Color(pixel.getRed(),pixel.getGreen(),pixel.getBlue(),
						pixel.getAlpha()));
			}
		});

		aux = new JPanel(new GridLayout(1,2));
		aux.add(new JLabel("Alpha:"));
		aux.add(currentAlpha);

		alphaSlider.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent event) {
				pixel.setAlpha(alphaSlider.getValue());
				currentAlpha.setText("" + pixel.getAlpha());
				color.setBackground(new Color(pixel.getRed(),pixel.getGreen(),pixel.getBlue()
						,pixel.getAlpha()));
			}

		});
		rgbWrapper.add(alphaSlider);
		rgbWrapper.add(aux);

		this.add(rgbWrapper);
		this.add(color);
	}


	/**
	 * 
	 * @return the color, in rgba format, of that shows in the panel.
	 * A color is represented by a pixel.
	 * <br>
	 * A pixel has a red, green, blue and alpha Integer.
	 * </br>
	 * @since 1.0
	 */
	public static Pixel getColor(){
		return pixel;
	}
}
