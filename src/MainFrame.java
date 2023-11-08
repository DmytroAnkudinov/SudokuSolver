import javax.swing.JFrame;

public class MainFrame extends JFrame {
	
	private Screen currentScreen;
	
	public void setInitialScreen(Screen initialScreen)
	{
		currentScreen = initialScreen;
		currentScreen.setParent(this);
		
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setResizable(false);
	    setVisible(true);
	    setTitle("Sudoku Solver");
	    
	    setSize(currentScreen.getWidth(), currentScreen.getHeight());
	    setLocationRelativeTo(null);
	    getContentPane().add(currentScreen);
	}
	
	public void segueToScreen(Screen newScreen)
	{
		getContentPane().removeAll();
		getContentPane().invalidate();
		getContentPane().add(newScreen);
		setSize(newScreen.getSize());
		setLocationRelativeTo(null);
		getContentPane().revalidate();
		repaint();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 MainFrame mainFrame = new MainFrame();
	     mainFrame.setInitialScreen(new StartScreen());
	}


}
