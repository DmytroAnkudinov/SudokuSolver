import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JFrame;

public class StartScreen extends Screen {

	public StartScreen()
	{
		super(400, 400);
		
		setLayout(null);
		
		JButton dataEntranceScreenButton = new JButton("Enter data for a Sudoku");
		
		dataEntranceScreenButton.setBounds(50, 50, 300, 50);
		dataEntranceScreenButton.setHorizontalAlignment(JButton.CENTER);
		dataEntranceScreenButton.setFocusable(false);
		dataEntranceScreenButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				StartScreen.this.segueToScreen(new DataEntranceScreen());
			}
		});
		add(dataEntranceScreenButton);
		
		JButton dataGenerationScreenButton = new JButton("Enter data for a Sudoku");
		
		dataGenerationScreenButton.setBounds(50, 200, 300, 50);
		dataGenerationScreenButton.setText("Generate data for a Sudoku");
		dataGenerationScreenButton.setHorizontalAlignment(JButton.CENTER);
		dataGenerationScreenButton.setFocusable(false);
		dataGenerationScreenButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				StartScreen.this.segueToScreen(new DataGenerationScreen());
			}
		});
		add(dataGenerationScreenButton);
	}
}
