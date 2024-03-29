package panels;
import javax.swing.*;
import eventListeners.*;
import general.Pair;
import general.PixelArrays;
import image.Pixel;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Stack;
/**
 * This class represents the drawing panel where the draw of the pixels will appear
 * @author Sasori
 * @version 1.0
 * @since 1.0
 */
public class DrawPanel extends JComponent{

	private static final long serialVersionUID = 1L;
	public static final Pixel ERASE = new Pixel(255,255,255,0); //transparent pixel
	public static final Pixel HOVER = new Pixel (255,255,255,100); //hovering color
	public static final int WIDTH = 600; //the total width of this panel
	public static final int HEIGHT = 600; //the total height of this panel
	public static final int MAX_SCALE = 5; //the max scale of the draw
	
	//private static final float DELTA = 0.001f; //the delta is used to compare two floats

	private int width; //the width of the draw
	private int height; //the height of the draw
	private int spacing; //the space between pixels
	private int tileWidth; //the width of a pixel
	private int tileHeight; //the height of a pixel
	public int mouseX; //the x of the mouse
	public int mouseY; //the y of the mouse
	private Pixel[] pixelKeyboardColors; //the current color to paint a pixel when
	//the a number of the keyboard was clicked
	private Pixel currentPixelColor; //the current pixel to be painted into the drawn
	//it can be the currentLeftPixelColor or currentRightPixelColor
	public Pixel[][] pixels; //a matrix that represents all the pixels of the draw
	private boolean[][] hoverLocation; //the mouse hovers the pixels. When it does
	//the current pixel changes color to be easier to visualize the current pixel
	//if one of the values of the array is true it mean the mouse is hovering
	//that pixel

	private Point min; //represents the starting point of the draw
	private Point max; //represents the ending point of the draw
	//both of these points are use to know where the user scaled the draw
	//the representation of the draw starts an min.x and min.y and ends
	//at max.x and max.y
	private Stack<Pixel[][]> previousChanges; //allows to go back (ctrl+z)
	private Stack<Pixel[][]> redoStack; //contains the possible redos (ctrl+y)

	private int currentScale; //represents the current zoom scale
	private int lastPressedKey = 0; //the last pressed key of the keyboard
	
	/**
	 * 
	 * @param width the width of the image
	 * @param height the height of the image
	 * <p>
	 * Creates the drawing panel where the user can make a pixel art
	 * </p>
	 * @since 1.0
	 */
	public DrawPanel(int width, int height){
		super();
		this.width = width;
		this.height = height;
		spacing = 1;
		tileWidth =  WIDTH / width;
		tileHeight =  HEIGHT / height;
		mouseX = -1;
		mouseY = -1;
		currentScale = 1;
		min = new Point(0,0);
		max = new Point(width-1,height-1);
		previousChanges = new Stack<>();
		redoStack = new Stack<>();
		
		pixelKeyboardColors = new Pixel[10];
		for(int i = 0; i < pixelKeyboardColors.length; i++)
			pixelKeyboardColors[i] = new Pixel(255,255,255,0); 
		//all the pixels all transparent at the beginning

		currentPixelColor = new Pixel(255,255,255,255); //The starting color is white 

		pixels = new Pixel[width][height];
		hoverLocation = new boolean[width][height];
		//fills all the pixels with the current color (transparent)
		for(int i = 0; i < pixels.length; i++){
			for(int j = 0; j < pixels[i].length; j++)
				pixels[i][j] = new Pixel(255,255,255,0); // transparent		
		}

		//previousChanges.push(PixelArrays.copyPixelMatrix(pixels));

		this.addMouseListener(new MouseClickHandler());
		this.addMouseMotionListener(new MouseMotionHandler());
		this.addMouseWheelListener(new MouseWheelHandler());

		setDoubleBuffered(true);
	}

	/**
	 * @param g the graphic component. The Graphics will represent all the pixels
	 * <p>
	 * Paints the panel, so its also responsible of painting the pixels
	 * </p>
	 * @since 1.0
	 */
	@Override
	public void paint(Graphics g){
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;

		g2.setColor(Color.DARK_GRAY);
		g2.fillRect(0,0, WIDTH,HEIGHT);
		g2.setColor(Color.WHITE);


		int x = 0,y = 0;
		for(int i = min.x; i <= max.x; i++){
			int minX = tileWidth * x;
			int rectWidth = tileWidth - 2*spacing;

			if(minX + rectWidth > WIDTH)
				break;

			for(int j = min.y; j <= max.y; j++){
				int minY = y * tileHeight;
				int rectHeight = tileHeight -2*spacing;

				if(minY + rectHeight > WIDTH)
					break;

				Pixel pixel = pixels[i][j]; 
				Paint p = new Color(pixel.getRed(),pixel.getGreen(),
						pixel.getBlue(),pixel.getAlpha());
				g2.setPaint(p);
				g2.fillRect(minX,minY,rectWidth,rectHeight);

				//see if the pixel is being hovered
				if(hoverLocation[i][j]){
					g2.setColor(new Color(HOVER.getRed(),HOVER.getGreen()
							,HOVER.getBlue(),HOVER.getAlpha()));
					g2.fillRect(minX,minY,rectWidth,rectHeight);
				}

				//boundary lines				
				//horizontal lines
				g2.setColor(Color.BLACK);
				g2.drawLine(minX + rectWidth+spacing, minY, minX + rectWidth+spacing, 
						minY + rectHeight + spacing);
				//vertical lines
				g2.drawLine(minX, minY + rectHeight + spacing, minX + rectWidth+spacing, 
						minY + rectHeight + spacing);
				y++;

			}
			y = 0;
			x++;
		}
	}

	/**
	 * @return the width of the draw
	 * @since 1.0
	 */
	public int getDrawWidth(){
		return width;
	}

	/**
	 * 
	 * @param width the new width of the draw
	 * <p>
	 * Sets the new width of the draw
	 * </p>
	 * @since1.0
	 */
	public void setDrawWidth(int width){
		this.width = width;
	}

	/**
	 * @return the height of the draw
	 * @since 1.0
	 */
	public int getDrawHeight(){
		return height;
	}

	/**
	 * 
	 * @param height the new height of the draw
	 * <p>
	 * Sets the new height of the draw
	 * </p>
	 * @since1.0
	 */
	public void setDrawHeight(int height){
		this.height = height;
	}

	/**
	 * 
	 * @return the current zoom scale
	 * @since 1.0
	 */
	public int getCurrentScale(){
		return currentScale;
	}

	/**
	 * 
	 * @param newScale the new zoom scale
	 * <p>
	 * Sets the new zoom scale and updats the tile sizes accordingly
	 * </p>
	 * @since 1.0
	 */
	public void setCurrentScale(int newScale){
		currentScale = newScale;
		tileWidth =  (WIDTH / width)*currentScale;
		tileHeight =  (HEIGHT / height)*currentScale;
	}

	/**
	 * 
	 * @return the matrix that represents the whole draw. 
	 * This matrix is represented by pixels.
	 * @since 1.0
	 */
	public Pixel[] getPixelKeyboardColors(){
		return pixelKeyboardColors;
	}

	/**
	 * 
	 * @param color the new pixel color of the number
	 * @param number the number corresponding to the pixel
	 * <p>
	 * Changes the pixel color to be painted when the number is clicked on the keyboard
	 * </p>
	 * @since 1.0
	 */
	public void setPixelKeyboardColors(Pixel color, int number){
		pixelKeyboardColors[number] = color.clone();
	}

	/**
	 * 
	 * @return the current color that will paint a pixel
	 * @since 1.0
	 */
	public Pixel getCurrentPixelColor(){
		return currentPixelColor;
	}

	/**
	 * 
	 * sets the new current pixel to be drawn when the mouse is pressed or dragged
	 * @since 1.0
	 */
	public void setCurrentPixelColor(Pixel pixel){
		currentPixelColor = pixel.clone();
	}

	/**
	 * 
	 * @return the key that was pressed
	 * @since 1.0
	 */
	public int getLastPressedKey(){
		return lastPressedKey;
	}

	/**
	 * 
	 * sets the last pressed key of the keyboard
	 * @since 1.0
	 */
	public void setLastPressedKey(int keyCode){
		lastPressedKey = keyCode;
	}

	/**
	 * 
	 * @return the current size of a tile
	 * @since 1.0
	 */
	public Pair<Integer,Integer> getTileSize(){
		return new Pair<Integer,Integer>(tileWidth,tileHeight);
	}

	/**
	 * 
	 * @param newTileWidth the new width of a tile
	 * @param newTileHeight the new height of a tile
	 * @deprecated
	 * @since 1.0
	 */
	public void setTileSize(int newTileWidth,int newTileHeight){
		tileWidth =  newTileWidth;
		tileHeight = newTileHeight;
	}

	/**
	 * 
	 * @return the pixel coordinates based on the last position of the mouse
	 * @since 1.0
	 */
	public Point getPixelPositionBasedOnMouse(){
		int x = 0,y = 0;
		for(int i = min.x; i <= max.x; i++){
			for(int j = min.y; j <= max.y; j++){
				int minX = tileWidth * x;
				int minY = y * tileHeight;
				int rectWidth = tileWidth; //no need to add the spacing
				int rectHeight = tileHeight; //no need to add the spacing

				if(mouseX >=  minX && mouseX <= rectWidth + minX
						&& mouseY >= minY && mouseY <= rectHeight + minY)
					return new Point(i,j);
				y++;
			}
			y = 0;
			x++;
		}
		return null;
	}

	/**
	 * 
	 * @param event the event that activated the paint of the pixel
	 * <p>
	 * Paints a pixel in the last clicked position
	 * </p>
	 * @since 1.0
	 */
	public void setPixel(MouseEvent event){
		Point position = getPixelPositionBasedOnMouse();
		if(position == null)
			return;
		pixels[position.x][position.y] = currentPixelColor.clone();
	}

	/**
	 * 
	 * @param event the event that activated the paint of the pixel
	 * <p>
	 * Sets the new hover position, that is, where paint the pixel where
	 * the mouse is hovering into
	 * </p>
	 * @since 1.0
	 */
	public void setHoverLocation(MouseEvent event){
		Point position = getPixelPositionBasedOnMouse();
		if(position == null)
			return;

		boolean found = false;
		for(int i = 0; i < hoverLocation.length && !found; i++){
			for(int j = 0; j < hoverLocation[i].length && !found; j++){
				if(hoverLocation[i][j]){
					//sets the previous hover to false
					hoverLocation[i][j] = false;
					found = true;
				}
			}
		}
		hoverLocation[position.x][position.y] = true;
	}


	/**
	 * 
	 * @return the point where the draw will start
	 * @since 1.0
	 */
	public Point getMinPoint(){
		return min;
	}

	/**
	 * 
	 * @param newMin the new minimum points
	 * <p>
	 * Sets the point where the draw will start
	 * </p>
	 * @since 1.0
	 */
	public void setMinPoint(Point newMin){
		min.x = newMin.x;
		min.y = newMin.y;
	}

	/**
	 * 
	 * @return the point where the draw will end
	 * @since 1.0
	 */
	public Point getMaxPoint(){
		return max;
	}

	/**
	 * 
	 * @param newMax the new maximum points
	 * <p>
	 * Sets the point where the draw will end
	 * </p>
	 * @since 1.0
	 */
	public void setMaxPoint(Point newMax){
		max.x = newMax.x;
		max.y = newMax.y;
	}

	/**
	 * 
	 * @param add if this is true it means that the operation is supposed 
	 * (it may not be necessary) to add the pixels onto the change stack, 
	 * <br> if not it will undo the previous operation if possible 
	 * @since 1.0
	 */
	public void handleUndoStackChange(boolean add){
		if(add){ //painting the pixels
			if(previousChanges.size() == 0){//adds the initial state
				Pixel[][] newPixels = new Pixel[pixels.length][pixels[0].length];
				for(int i = 0;i < pixels.length;i++){
					for(int j = 0; j < pixels[i].length; j++){
						newPixels[i][j] = ERASE; //transparent
					}
				}
				previousChanges.push(PixelArrays.copyPixelMatrix(newPixels));
			}else
				previousChanges.push(PixelArrays.copyPixelMatrix(pixels));
			
		}else{ //undo
			if(previousChanges.size() > 0){
				redoStack.push(PixelArrays.copyPixelMatrix(pixels));
				Pixel[][] newPixels = previousChanges.pop(); //pops the last change
				for(int i = 0;i < newPixels.length;i++){
					for(int j = 0; j < newPixels[i].length; j++){
						pixels[i][j] = newPixels[i][j];
					}
				}
			}else{
				for(int i = 0;i < pixels.length;i++){
					for(int j = 0; j < pixels[i].length; j++){
						pixels[i][j] = ERASE;
					}
				}
			}
		}
	}

	/**
	 * Redo the changes
	 * @since 1.0
	 */
	public void redo() {
		if(redoStack.size() > 0){
			//redo
			previousChanges.push(PixelArrays.copyPixelMatrix(pixels));
			Pixel[][] newPixels = redoStack.pop();

			for(int i = 0; i < pixels.length; i++){
				for(int j = 0; j < pixels[i].length; j++)
					pixels[i][j] = newPixels[i][j];
			}
		}
	}

	/**
	 * 
	 * fills all the available pixels. That is, all the pixels that
	 * are transparent and that are not being surrounded by others
	 * @since 1.0
	 */
	public void fillPixels(){
		Point clickedPoint = this.getPixelPositionBasedOnMouse();		
		//if the clicked pixel is not a transparent one, the fill tool
		//as no effect
		if(pixels[clickedPoint.x][clickedPoint.y].equals(ERASE))
			fillTransparentPixels(clickedPoint);

	}

	/**
	 * 
	 * @param startingPoint the starting point of the fill
	 * <p>
	 * fills all the available pixels. That is, all the pixels that
	 * are transparent and that are not being surrounded by others.
	 * It searches all the neighbors of which point and paints it
	 * if the position meets the requirements
	 * </p>
	 * @since 1.0
	 */
	private void fillTransparentPixels(Point startingPoint){
		ArrayList<Point> neighbors = getNeighbors(startingPoint);
		//the starting point is always transparent so it must
		//be filled
		pixels[startingPoint.x][startingPoint.y] = currentPixelColor.clone();

		for(int i = 0; i < neighbors.size(); i++){
			Point neighbor = neighbors.get(i);
			if ((pixels[neighbor.x][neighbor.y]).equals(ERASE)){
				//see all of the neighbor neighbors. if they are 
				//transparent they should also be filled
				pixels[neighbor.x][neighbor.y] = currentPixelColor.clone();
				ArrayList<Point> neighborAux = getNeighbors(neighbor);
				for(Point p: neighborAux){
					if(pixels[p.x][p.y].equals(ERASE))
						fillTransparentPixels(neighbor);
				}

			}
		}
	}

	/**
	 * 
	 * @param p the point
	 * @return all the neighbors of this point
	 *  the maximum neighbors a point can have is four
	 *	[p.x - 1][p.y] 
	 *	[p.x][p.y - 1] 
	 *	[p.x + 1][p.y] 
	 *	[p.x][p.y + 1]
	 * @since 1.0 
	 */
	private ArrayList<Point> getNeighbors(Point p){
		ArrayList<Point> result = new ArrayList<>();
		if(p.x == 0 && p.y == 0){//top left corner of the screen
			result.add(new Point(p.x,p.y + 1));
			result.add(new Point(p.x + 1,p.y));
		}else if(p.x == width-1 && p.y == 0){//top right corner of the screen
			result.add(new Point(p.x,p.y + 1));
			result.add(new Point(p.x - 1,p.y));
		}else if(p.x == 0 && p.y == height-1){//bottom left corner of the screen
			result.add(new Point(p.x,p.y - 1));
			result.add(new Point(p.x + 1,p.y));
		}else if (p.x == width-1 && p.y == height-1){//bottom right corner of the screen
			result.add(new Point(p.x,p.y - 1));
			result.add(new Point(p.x - 1,p.y));
		}else if(p.x == 0){ //left side
			result.add(new Point(p.x,p.y - 1));
			result.add(new Point(p.x,p.y + 1));
			result.add(new Point(p.x+1,p.y));
		}else if (p.x == width - 1){ //right side
			result.add(new Point(p.x,p.y - 1));
			result.add(new Point(p.x,p.y + 1));
			result.add(new Point(p.x - 1,p.y));
		}else if(p.y == 0){ //top side
			result.add(new Point(p.x + 1,p.y));
			result.add(new Point(p.x - 1,p.y));
			result.add(new Point(p.x,p.y + 1));
		}else if(p.y == height - 1){ //bottom side
			result.add(new Point(p.x + 1,p.y));
			result.add(new Point(p.x - 1,p.y));
			result.add(new Point(p.x,p.y +-1));
		}else{ //middle
			result.add(new Point(p.x + 1,p.y));
			result.add(new Point(p.x - 1,p.y));
			result.add(new Point(p.x,p.y + 1));
			result.add(new Point(p.x,p.y - 1));
		}
		return result;
	}

	/**
	 * 
	 * @param startingPoint the starting point of the rect tool
	 * <p>
	 * Creates the area for the rect tool
	 * </p>
	 * @since 1.0
	 */
	public void rectTool(Point startingPoint){
		Point destination = getPixelPositionBasedOnMouse();
		//resets the pixels in order to let the user see the changes of
		//the rect tool
		pixels = PixelArrays.copyPixelMatrix(previousChanges.peek());

		int xOffset = Math.abs(startingPoint.x - destination.x);
		int yOffset = Math.abs(startingPoint.y - destination.y);

		int translateX = startingPoint.x > destination.x ? 1 : -1;
		//if this is set to 1 it means to move left, -1 otherwise
		int translateY = startingPoint.y > destination.y ? 1 : -1;
		//if this is set to 1 it means to move up, -1 otherwise

		//fill horizontal pixels
		for(int x = 0; x <= xOffset; x++){
			pixels[startingPoint.x - x*translateX][startingPoint.y]
					= getCurrentPixelColor().clone();
			pixels[startingPoint.x - x*translateX] 
					[startingPoint.y + yOffset*translateY*-1]
							= getCurrentPixelColor().clone();
		}

		//fill vertical pixels
		for(int y = 0; y <= yOffset; y++){
			pixels[startingPoint.x]
					[startingPoint.y - y*translateY]
							= getCurrentPixelColor().clone();
			pixels[startingPoint.x - xOffset*translateX]
					[startingPoint.y - y*translateY]
							= getCurrentPixelColor().clone();
		}
	}
	
//	/**
//	 * 
//	 * @param startingPoint the starting point of the circle tool
//	 * <p>
//	 * creates a circle within a the starting point and the current position of
//	 * the mouse
//	 * </p>
//	 * @since 1.0
//	 */
//	public void circleTool(Point startingPoint){
//		Point destination = getPixelPositionBasedOnMouse();
//		//refresh the changes
//		//pixels = PixelArrays.copyPixelMatrix(previousChanges.peek());
//		
//		int centerX = (Math.abs(startingPoint.x - destination.x)) / 2;
//		int centerY = (Math.abs(startingPoint.y - destination.y)) / 2;
//		Point center = new Point(centerX,centerY);
//		int radius = distance(center,startingPoint) / 2;
//		
//		System.out.println("start: " + startingPoint.toString());
//		System.out.println("end: " + destination.toString());
//		System.out.println("center: " + center.toString());
//		System.out.println("radius: " + radius);
//		
//		boolean [][] visited = new boolean[width][height];
//		visited[startingPoint.x][startingPoint.y] = true;
//		circlePixelsWithinRadius(startingPoint,radius,visited);
//	}
//	
//	/**
//	 * 
//	 * @param startingPoint
//	 * @param radius 
//	 * <p>
//	 * Circles all the pixels 
//	 * </p>
//	 * @since 1.0
//	 */
//	private void circlePixelsWithinRadius(Point startingPoint,int radius
//			,boolean[][] visited){
//		ArrayList<Point> neighbors = getNeighbors(startingPoint);
//		//the starting point is always within the given radius
//		//so it must be filled
//		pixels[startingPoint.x][startingPoint.y] = currentPixelColor.clone();
//		
//		for(int i = 0; i < neighbors.size(); i++){
//			Point p = neighbors.get(i);
//			//see if the neighbor is within the give radius
//			//if it is paint it and see its neighbors aswell
//			int distance = distance(startingPoint,p);
//			if(Math.abs(distance - radius) == 0 && !visited[p.x][p.y]){
//				visited[p.x][p.y] = true;
//				pixels[p.x][p.y] = currentPixelColor.clone();
//				ArrayList<Point> pNeighbors = getNeighbors(p);
//				for(Point aux: pNeighbors){
//					if(Math.abs(distance - radius) == 0 && !visited[aux.x][aux.y]){
//						visited[aux.x][aux.y] = true;
//						circlePixelsWithinRadius(startingPoint,radius,visited);
//					}
//				}	
//				
//			}
//		}
//	}
//	
//	/**
//	 * 
//	 * @param p1
//	 * @param p2
//	 * @return calculates the distance between two points (p1 and p2)
//	 * <br>
//	 * The loss of precision is wanted
//	 * </br>
//	 * @since 1.0
//	 */
//	private int distance(Point p1,Point p2){
//		return (int) (Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
//	}
}
