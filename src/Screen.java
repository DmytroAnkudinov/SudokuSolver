import javax.swing.JPanel;

public class Screen extends JPanel {
	
	private MainFrame parent;
	
	public Screen(int width, int height)
	{
		setSize(width, height);
	}
	
	public void setParent(MainFrame parent)
	{
		this.parent = parent;
	}
	
	public void segueToScreen(Screen newScreen)
	{
		newScreen.setParent(parent);
		parent.segueToScreen(newScreen);
	}
}
