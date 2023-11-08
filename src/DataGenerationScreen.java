import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

public class DataGenerationScreen extends Screen {

	public DataGenerationScreen() {
		super(1000, 800);
		
		setLayout(null);
	
	    // Convert the Date objects to Duration objects.
	    long totalHours = Duration.between(LocalDate.of(2023, 11, 7).atStartOfDay(), LocalDate.of(2023, 12, 13).atStartOfDay()).toHours();
	    long currentHours = Duration.between(LocalDate.of(2023, 11, 7).atStartOfDay(), LocalDateTime.now()).toHours();
	    
		JLabel serviceProgressLabel = new JLabel(String.format("%d hours served out of %d total hours to serve. %d hours left to serve.",
				currentHours, totalHours, totalHours - currentHours), JLabel.CENTER);
		serviceProgressLabel.setBounds(200, 200, 600, 25);
		serviceProgressLabel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
	
		add(serviceProgressLabel);
	    
		JProgressBar servedProgressBar = new JProgressBar(0, (int)totalHours);
		servedProgressBar.setBounds(200, 300, 600, 25);
		servedProgressBar.setStringPainted(true);
		servedProgressBar.setFocusable(false);
		
		servedProgressBar.setValue((int)currentHours);
	
		add(servedProgressBar);
		
	    long totalLearnedHours = 300;
	    long currentLearnedHours = Duration.between(LocalDate.of(2023, 11, 7).atStartOfDay(), LocalDateTime.now()).toDays();
	    currentLearnedHours -= (currentLearnedHours + 2) / 7;
	    currentLearnedHours *= 10;
	    
		JLabel learnedProgressLabel = new JLabel(String.format("%d hours attended classes out of %d total hours to attend classes. %d hours left to attend classes.",
				currentLearnedHours, totalLearnedHours, totalLearnedHours - currentLearnedHours), JLabel.CENTER);
		learnedProgressLabel.setBounds(200, 400, 600, 25);
		learnedProgressLabel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
	
		add(learnedProgressLabel);
	    
		JProgressBar learnedProgressBar = new JProgressBar(0, (int)totalLearnedHours);
		learnedProgressBar.setBounds(200, 500, 600, 25);
		learnedProgressBar.setStringPainted(true);
		learnedProgressBar.setFocusable(false);
		
		learnedProgressBar.setValue((int)currentLearnedHours);
	
		add(learnedProgressBar);
		
		JButton backButton = new JButton("Back to previous screen");
		
		backButton.setBounds(200, 25, 200, 50);
		backButton.setHorizontalAlignment(JButton.CENTER);
		backButton.setFocusable(false);
		backButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				DataGenerationScreen.this.segueToScreen(new StartScreen());
			}
		});
		add(backButton);
		
		JButton forwardButton = new JButton("Move to next screen");
		
		forwardButton.setBounds(600, 25, 200, 50);
		forwardButton.setHorizontalAlignment(JButton.CENTER);
		forwardButton.setFocusable(false);
		forwardButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				DataGenerationScreen.this.segueToScreen(new MainScreen());
			}
		});
		add(forwardButton);

	}
}
