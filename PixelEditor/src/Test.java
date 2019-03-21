import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
 
import javax.swing.JComponent;
import javax.swing.JFrame;
 
public class Test {
 
	private static TransformingCanvas canvas;
 
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		canvas = new TransformingCanvas();
		TranslateHandler translater = new TranslateHandler();
		canvas.addMouseListener(translater);
		canvas.addMouseMotionListener(translater);
		canvas.addMouseWheelListener(new ScaleHandler());
		frame.setLayout(new BorderLayout());
		frame.getContentPane().add(canvas, BorderLayout.CENTER);
		frame.setSize(500, 500);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
	}
 
	private static class TransformingCanvas extends JComponent {
		private double translateX;
		private double translateY;
		private double scale;
 
		TransformingCanvas() {
			translateX = 0;
			translateY = 0;
			scale = 1;
			setOpaque(true);
			setDoubleBuffered(true);
		}
 
		@Override public void paint(Graphics g) {
 
			AffineTransform tx = new AffineTransform();
			tx.translate(translateX, translateY);
			tx.scale(scale, scale);
			Graphics2D ourGraphics = (Graphics2D) g;
			ourGraphics.setColor(Color.WHITE);
			ourGraphics.fillRect(0, 0, getWidth(), getHeight());
			ourGraphics.setTransform(tx);
			ourGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			ourGraphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);			
			ourGraphics.setColor(Color.BLACK);
			ourGraphics.drawRect(50, 50, 50, 50);
			ourGraphics.fillOval(100, 100, 100, 100);
			ourGraphics.drawString("Test Affine Transform", 50, 30);
			// super.paint(g);
		}
	}
 
	private static class TranslateHandler implements MouseListener,
			MouseMotionListener {
		private int lastOffsetX;
		private int lastOffsetY;
 
		public void mousePressed(MouseEvent e) {
			// capture starting point
			lastOffsetX = e.getX();
			lastOffsetY = e.getY();
		}
 
		public void mouseDragged(MouseEvent e) {
			
			// new x and y are defined by current mouse location subtracted
			// by previously processed mouse location
			int newX = e.getX() - lastOffsetX;
			int newY = e.getY() - lastOffsetY;
 
			// increment last offset to last processed by drag event.
			lastOffsetX += newX;
			lastOffsetY += newY;
			// update the canvas locations
			canvas.translateX += newX;
			canvas.translateY += newY;
			
			// schedule a repaint.
			canvas.repaint();
		}
 
		public void mouseClicked(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mouseMoved(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
	}
 
	private static class ScaleHandler implements MouseWheelListener {
		public void mouseWheelMoved(MouseWheelEvent e) {
			if(e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL) {
				
				// make it a reasonable amount of zoom
				// .1 gives a nice slow transition
				canvas.scale += (.1 * e.getWheelRotation());
				// don't cross negative threshold.
				// also, setting scale to 0 has bad effects
				canvas.scale = Math.max(0.00001, canvas.scale); 
				canvas.repaint();
			}
		}
	}
 
}