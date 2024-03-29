import java.awt.*;
import java.awt.event.KeyListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.*;

import eventListeners.EventsWrapper;
import eventListeners.KeyboardHandler;
import panels.ChooseColorPanel;
import panels.DrawPanel;
/**
 * Creates a window that will show all the panels (DrawPanel, ChooseColorPanel, etc)
 * @author Sasori
 * @version 1.0
 * @since 1.0
 */
public class Window extends JFrame{
	
	private static final long serialVersionUID = 1L;
	private DrawPanel drawPanel;

	/**
	 * Creates the frame with all the panels
	 * @since 1.0
	 */
	public Window(){
		super("PixelEditor");
		this.setSize(new Dimension(1260,680));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(true);
		this.setLayout(new GridLayout(1,2));
		
		//choose the size
		boolean wrongSize = false;
		String height = "",width = "";
		while(!wrongSize){ //checks if it is a valid input
			width = JOptionPane.showInputDialog("Choose the width of the image");
			Pattern pattern = Pattern.compile("[^0-9]|^$");
			Matcher matcher = pattern.matcher(width);
			if(!matcher.find())
				wrongSize = true;
			else
				JOptionPane.showMessageDialog(null, "Please only insert numbers");
		}
		wrongSize =  false;
		while(!wrongSize){//checks if it is a valid input
			height = JOptionPane.showInputDialog("Choose the height of the image");
			Pattern pattern = Pattern.compile("[^0-9]|^$");
			Matcher matcher = pattern.matcher(height);
			if(!matcher.find())
				wrongSize = true;
			else
				JOptionPane.showMessageDialog(null, "Please only insert numbers");
		}
		drawPanel = new DrawPanel(Integer.parseInt(width),Integer.parseInt(height));
		this.add(drawPanel);
		this.add(new ChooseColorPanel());

		//all of the handlers reference to this draw panel
		EventsWrapper.drawPanel = drawPanel; 
		addEventToAllComponents(this,new KeyboardHandler());
		this.setVisible(true);
	}
	
	/**
	 * 
	 * @return the draw panel of the frame
	 * @since 1.0
	 */
	public DrawPanel getDrawPanel() {
		return drawPanel;
	}
	
	/**
	 * 
	 * @param c the container
	 * @param event the event
	 * <p>
	 * Adds a KeyListener to all the components of the frame
	 * </p>
	 * @since 1.0
	 */
	private void addEventToAllComponents(Container c,KeyListener event) {
		Component[] comps = c.getComponents();
		for (Component comp : comps) {
			if (comp instanceof Container){
				comp.addKeyListener(event);
				addEventToAllComponents((Container)comp,event);
			}
		}
	}	
}
