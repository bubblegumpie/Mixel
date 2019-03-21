/**
 * Executes the main frame
 * @author Sasori
 * @version 1.0
 * @since 1.0
 */
public class Main implements Runnable{
	
	private Window window = new Window();
	private boolean leaveProgram = false;
	
	@Override
	public void run() {
		while(!leaveProgram)
			window.repaint();
	}

	public static void main(String[] args){
		new Thread (new Main()).start();
	}
}
