import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;

public class DataEntranceScreen extends Screen { 
	
	private JLabel allaLabel = new JLabel("Where do you want to go today, Alla?");
	
	private class LabelMovingThreadProcess implements Runnable
	{
		private int x = allaLabel.getX();
		private int y = allaLabel.getY();
		private int xInc = 1;
		private int yInc = 1;
		public boolean isActive = false; 
		
		@Override
		public void run() {
			
			while (isActive)
			{
				x = x + xInc;
				y = y + yInc;
				
				allaLabel.setLocation(x , y);
				
				if ((x == 0) || (x + allaLabel.getWidth() == DataEntranceScreen.this.getWidth()))
					xInc = -xInc;
				
				if ((y == 200) || (y + allaLabel.getHeight() == DataEntranceScreen.this.getHeight()))
					yInc = -yInc;
				
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	private LabelMovingThreadProcess labelMovingThreadProcess;
	
	public DataEntranceScreen() {
		super(1000, 800);
		
		setLayout(null);

		allaLabel.setHorizontalAlignment(JLabel.CENTER);
		allaLabel.setBounds(375, 200, 250, 25);
		// allaLabel.setBorder(BorderFactory.createLineBorder(Color.black, 1));

		add(allaLabel);
		
		labelMovingThreadProcess = new LabelMovingThreadProcess();
		
		JButton backButton = new JButton("Back to previous screen");
		
		backButton.setBounds(100, 25, 200, 50);
		backButton.setHorizontalAlignment(JButton.CENTER);
		backButton.setFocusable(false);
		backButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				labelMovingThreadProcess.isActive = false;
				DataEntranceScreen.this.segueToScreen(new StartScreen());
			}
		});
		add(backButton);
		
		JButton forwardButton = new JButton("Move to next screen");
		
		forwardButton.setBounds(500, 25, 200, 50);
		forwardButton.setHorizontalAlignment(JButton.CENTER);
		forwardButton.setFocusable(false);
		forwardButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				labelMovingThreadProcess.isActive = false;
				DataEntranceScreen.this.segueToScreen(new MainScreen());
			}
		});
		add(forwardButton);
		
		JButton toggleMoveButton = new JButton("Start Moving");
		
		toggleMoveButton.setBounds(400, 100, 200, 50);
		toggleMoveButton.setHorizontalAlignment(JButton.CENTER);
		toggleMoveButton.setFocusable(false);
		toggleMoveButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (labelMovingThreadProcess.isActive)
				{
					toggleMoveButton.setText("Start Moving");
					labelMovingThreadProcess.isActive = false;
				}
				else
				{
					toggleMoveButton.setText("Stop Moving");
					labelMovingThreadProcess.isActive = true;
					Thread labelMovingThread = new Thread(labelMovingThreadProcess);
					labelMovingThread.start();
				}
			}
		});
		add(toggleMoveButton);

	}
}
