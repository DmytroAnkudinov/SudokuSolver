import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainFrame extends JPanel implements KeyListener {
	
	private class IndexedLabel extends JLabel
	{
		public int row;
		public int column;
		public int index;
		
		public IndexedLabel(String text, int horizontalAlignment)
		{
			super(text, horizontalAlignment);
		}
	}
	
	private boolean[] rowsChecked = new boolean[9];
	private boolean[] columnsChecked = new boolean[9];
	private boolean[] squaresChecked = new boolean[9];
	
	private int[] groupStartingCoordinate = new int[9];
	
	private static final int FIELD_UPPER_LEFT_CORNER_X = 10;
	private static final int FIELD_UPPER_LEFT_CORNER_Y = 10;
	private static final int SQUARE_EDGE_LENGTH = 64;
	private static final int ORDINARY_LINE_THICKNESS = 2;
	private static final int SEPARATION_LINE_THICKNESS = 4;
	private static final int FIELD_LENGTH = SQUARE_EDGE_LENGTH * 9 + ORDINARY_LINE_THICKNESS * 6 + SEPARATION_LINE_THICKNESS * 4;
	
	private static final int NUMBERPAD_UPPER_LEFT_CORNER_X = 700;
	private static final int NUMBERPAD_UPPER_LEFT_CORNER_Y = 100;
	
	private static final Color SEMI_TRANSPARENT_RED = new Color(255, 0, 0, 64);
	private static final Color SEMI_TRANSPARENT_YELLOW = new Color(0, 255, 255, 64);
	
	private Engine engine;
	
	private IndexedLabel[][] boardFields;
	private IndexedLabel[]   numberFields;
	private Font largeFont = new Font("Roboto", Font.PLAIN, 24);
	private Font smallFont = new Font("Roboto", Font.PLAIN, 12);
	
	private IndexedLabel selectedBoardField = null;
	
	private MouseListener boardFieldChooser = new MouseListener() {
		
		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {
			setSelectedBoardField((IndexedLabel)e.getSource());
			
			repaint();
		}
	};
	
	private MouseListener numberPadChooser = new MouseListener() {
		
		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			repaint();
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {

			
			setSelectedBoardValue(((IndexedLabel)e.getSource()).index);
		
			repaint();
		}
	};

	
	public MainFrame(JFrame mainFrame) 
	{
		engine = new Engine(this);
		
		engine.initField(0, 0, 6); engine.initField(0, 3, 2); engine.initField(0, 4, 4); engine.initField(0, 7, 8); engine.initField(0, 8, 1);
		engine.initField(1, 0, 1); engine.initField(1, 1, 2); engine.initField(1, 2, 3); engine.initField(1, 6, 5); engine.initField(1, 7, 6);
		engine.initField(2, 0, 9); engine.initField(2, 4, 6); engine.initField(2, 5, 1); engine.initField(2, 7, 2); engine.initField(2, 8, 7);
		
		engine.initField(3, 1, 6); engine.initField(3, 5, 5); engine.initField(3, 6, 1); engine.initField(3, 7, 9); engine.initField(3, 8, 8);
		engine.initField(4, 0, 4); engine.initField(4, 1, 1); engine.initField(4, 2, 5);
		engine.initField(5, 4, 7); engine.initField(5, 5, 2); engine.initField(5, 6, 4);
		
		engine.initField(6, 0, 3); engine.initField(6, 1, 9); engine.initField(6, 2, 1); engine.initField(6, 3, 6);
		engine.initField(7, 0, 5); engine.initField(7, 3, 3); engine.initField(7, 5, 7); engine.initField(7, 6, 2);
		engine.initField(8, 2, 4); engine.initField(8, 3, 9); engine.initField(8, 5, 8); engine.initField(8, 7, 1);
		
		engine.initGroups();
		
		setLayout(null);

		
		{
			int x,y;
			
			
			numberFields = new IndexedLabel[9];
			
			x = NUMBERPAD_UPPER_LEFT_CORNER_X + SEPARATION_LINE_THICKNESS;
			y = NUMBERPAD_UPPER_LEFT_CORNER_Y + SEPARATION_LINE_THICKNESS;
			
			for (int i = 1; i < 10; ++i)
			{
				numberFields[i - 1] = new IndexedLabel(String.valueOf(i), JLabel.CENTER);
				numberFields[i - 1].index = i;
				numberFields[i - 1].setBounds(x, y, SQUARE_EDGE_LENGTH, SQUARE_EDGE_LENGTH);
				numberFields[i - 1].setBackground(Color.LIGHT_GRAY);
				numberFields[i - 1].setOpaque(true);
				numberFields[i - 1].addMouseListener(numberPadChooser);
				add(numberFields[i - 1]);
				
				if ((i % 3) == 0)
				{
					x = NUMBERPAD_UPPER_LEFT_CORNER_X + SEPARATION_LINE_THICKNESS;
					y += SQUARE_EDGE_LENGTH + ORDINARY_LINE_THICKNESS;
				}
				else
					x += SQUARE_EDGE_LENGTH + ORDINARY_LINE_THICKNESS;
	
			}
			
			JButton deleteButton = new JButton();
			
			deleteButton.setBounds(NUMBERPAD_UPPER_LEFT_CORNER_X + SEPARATION_LINE_THICKNESS, y + SEPARATION_LINE_THICKNESS, 
					SQUARE_EDGE_LENGTH * 3 + ORDINARY_LINE_THICKNESS * 2,
					SQUARE_EDGE_LENGTH / 2);
			deleteButton.setText("X");
			deleteButton.setFont(largeFont);
			deleteButton.setHorizontalAlignment(JButton.CENTER);
			deleteButton.setFocusable(false);
			deleteButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					setSelectedBoardValue(0);
				}
			});
			add(deleteButton);
			
			JButton runProcessingOnceButton = new JButton();
			
			runProcessingOnceButton.setBounds(deleteButton.getBounds().x, (int)(deleteButton.getBounds().y+1.5*deleteButton.getBounds().height),
					deleteButton.getBounds().width, deleteButton.getBounds().height);
			runProcessingOnceButton.setText("Run processing once");
			runProcessingOnceButton.setFont(smallFont);
			runProcessingOnceButton.setHorizontalAlignment(JButton.CENTER);
			runProcessingOnceButton.setFocusable(false);
			runProcessingOnceButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
				    new Thread(new Runnable() {
				        @Override public void run() {
				        	engine.performOneProcessingRun();
				        }
				    }).start();
				}
			});
			add(runProcessingOnceButton);
		}
		
		boardFields = new IndexedLabel[9][9];
		
		groupStartingCoordinate[0] = FIELD_UPPER_LEFT_CORNER_X + SEPARATION_LINE_THICKNESS;
		for (int i = 1; i < 9; ++i)
		{
			groupStartingCoordinate[i] = groupStartingCoordinate[i - 1] + SQUARE_EDGE_LENGTH;
			groupStartingCoordinate[i] += ((i % 3) == 0) ? SEPARATION_LINE_THICKNESS : ORDINARY_LINE_THICKNESS;
		}
		
	    for (int row = 0; row < 9; ++row)
	    {
	    	for (int column = 0; column < 9; ++column)
	    	{
    			int value = engine.getFieldCurrentValue(row, column);
    			boardFields[row][column] = new IndexedLabel("", JLabel.CENTER);
    			boardFields[row][column].row = row;
    			boardFields[row][column].column = column;
    			boardFields[row][column].index = (row / 3) * 3 + (column / 3);
    			boardFields[row][column].setLayout(null);

    			if (value != 0)
    			{
    				boardFields[row][column].setText(String.valueOf(value));
    				boardFields[row][column].setBackground(Color.GRAY);
    				boardFields[row][column].setOpaque(true);
    			}
    			else
    				boardFields[row][column].addMouseListener(boardFieldChooser);
    			
    			boardFields[row][column].setBounds(groupStartingCoordinate[column], groupStartingCoordinate[row], SQUARE_EDGE_LENGTH, SQUARE_EDGE_LENGTH);
    			boardFields[row][column].setFont(largeFont);
    			
    			add(boardFields[row][column]);
	    	}
	    }	    
	}
	
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
	    Graphics2D g2 = (Graphics2D) g;


	    // Draw debug data
	    {
		    g.setColor(Color.BLACK);
		    g.drawString(engine.debugText, FIELD_UPPER_LEFT_CORNER_X, 700);
	    }
		    
	    // Draw check results
	    {
		    g.setColor(Color.RED);
		    for (int i = 0; i < 9; ++i)
		    {
		    	if (rowsChecked[i]) 
		    		g.fillRect(FIELD_UPPER_LEFT_CORNER_X, groupStartingCoordinate[i], 
		    				   FIELD_LENGTH, SQUARE_EDGE_LENGTH);
		       	
		    	if (columnsChecked[i]) 
		    		g.fillRect(groupStartingCoordinate[i], FIELD_UPPER_LEFT_CORNER_Y, 
		    				   SQUARE_EDGE_LENGTH, FIELD_LENGTH);
		    	
		    	if (squaresChecked[i]) 
		    		g.fillRect(groupStartingCoordinate[(i % 3) * 3], groupStartingCoordinate[(i / 3) * 3], 
		    				   SQUARE_EDGE_LENGTH * 3 + ORDINARY_LINE_THICKNESS * 2, SQUARE_EDGE_LENGTH * 3  + ORDINARY_LINE_THICKNESS * 2);
		    }
	    }
    
	    {
		    // Draw the game field
		    int x, y;
		    g.setColor(Color.BLACK);
		    // Draw the horizontal lines
		    y = FIELD_UPPER_LEFT_CORNER_Y;
		    for (int i = 0; i < 10; ++i)
		    {
		    	int lineThickness = ((i % 3) == 0) ? SEPARATION_LINE_THICKNESS : ORDINARY_LINE_THICKNESS;
		    	g.fillRect(FIELD_UPPER_LEFT_CORNER_X, y, FIELD_LENGTH, lineThickness);
		    	y += lineThickness + SQUARE_EDGE_LENGTH;
		    }
		    
		    // Draw the vertical lines
		    x = FIELD_UPPER_LEFT_CORNER_X;
		    for (int i = 0; i < 10; ++i)
		    {
		    	int lineThickness = ((i % 3) == 0) ? SEPARATION_LINE_THICKNESS : ORDINARY_LINE_THICKNESS;
		    	g.fillRect(x, FIELD_UPPER_LEFT_CORNER_Y, lineThickness, FIELD_LENGTH);
		    	x += lineThickness + SQUARE_EDGE_LENGTH;
		    }
	    }
	    
	    
	    if (engine.getCurrentProcessedGroupType() != ProcessedGroup.Type.EPGT_NONE)
	    {
	    	g.setColor(Color.RED);
	    	// Draw the group being processed
	    	int upperLeftCornerX = 0;
	    	int upperLeftCornerY = 0;
	    	int width = 0;
	    	int height = 0;
	    	if (engine.getCurrentProcessedGroupType() == ProcessedGroup.Type.EPGT_ROW)
	    	{
	    		upperLeftCornerX = FIELD_UPPER_LEFT_CORNER_X - 1; 
	    		upperLeftCornerY = groupStartingCoordinate[engine.getCurrentProcessedGroupIndex()] - 1;
	    		width = FIELD_LENGTH + 1;
	    		height = SQUARE_EDGE_LENGTH + 1;
	    		g.drawRect(upperLeftCornerX++, upperLeftCornerY++, width--, height--);
	    		g.drawRect(upperLeftCornerX++, upperLeftCornerY++, width--, height--);
	    		g.drawRect(upperLeftCornerX, upperLeftCornerY, width, height);
	    	}
	    	
	    	if (engine.getCurrentProcessedGroupType() == ProcessedGroup.Type.EPGT_COLUMN)
	    	{
	    		upperLeftCornerX = groupStartingCoordinate[engine.getCurrentProcessedGroupIndex()] - 1; 
	    		upperLeftCornerY = FIELD_UPPER_LEFT_CORNER_Y - 1;
	    		width = SQUARE_EDGE_LENGTH + 1;
	    		height = FIELD_LENGTH + 1;
	    		g.drawRect(upperLeftCornerX++, upperLeftCornerY++, width--, height--);
	    		g.drawRect(upperLeftCornerX++, upperLeftCornerY++, width--, height--);
	    		g.drawRect(upperLeftCornerX, upperLeftCornerY, width, height);
	    	}
	    	
	    	if (engine.getCurrentProcessedGroupType() == ProcessedGroup.Type.EPGT_SQUARE)
	    	{
	    		upperLeftCornerX = groupStartingCoordinate[(engine.getCurrentProcessedGroupIndex() % 3) * 3] - 1; 
	    		upperLeftCornerY = groupStartingCoordinate[(engine.getCurrentProcessedGroupIndex() / 3) * 3] - 1;
	    		width = SQUARE_EDGE_LENGTH * 3 + ORDINARY_LINE_THICKNESS * 2 + 1;
	    		height = SQUARE_EDGE_LENGTH * 3 + ORDINARY_LINE_THICKNESS * 2 + 1;
	    		g.drawRect(upperLeftCornerX++, upperLeftCornerY++, width--, height--);
	    		g.drawRect(upperLeftCornerX++, upperLeftCornerY++, width--, height--);
	    		g.drawRect(upperLeftCornerX, upperLeftCornerY, width, height);
	    	}
	    }

	    // Show selection
	    if (selectedBoardField != null)
	    {
	    	g.setColor(SEMI_TRANSPARENT_YELLOW);
	    	g.fillRect(selectedBoardField.getBounds().x, selectedBoardField.getBounds().y, 
	    			selectedBoardField.getBounds().height, selectedBoardField.getBounds().width);
	    	
	    }
	    
	    /*
	    // Paint horizontal line
	    if (selectedBoardField != null)
	    {
	    	g.setColor(SEMI_TRANSPARENT_YELLOW);
	    	g.fillRect(UPPER_LEFT_CORNER_X, selectedBoardField.getBounds().y, 
	    			FIELD_LENGTH, selectedBoardField.getBounds().height);
	    }
	    
	    // Paint vertical line
	    if (selectedBoardField != null)
	    {
	    	g.setColor(SEMI_TRANSPARENT_YELLOW);
	    	g.fillRect(selectedBoardField.getBounds().x, UPPER_LEFT_CORNER_Y, 
	    			selectedBoardField.getBounds().width, FIELD_LENGTH);
	    }
	    
	    // Paint box
	    if (selectedBoardField != null)
	    {
	    	g.setColor(SEMI_TRANSPARENT_YELLOW);
	    	int startX = UPPER_LEFT_CORNER_X + SEPARATION_LINE_THICKNESS;
	    	while (selectedBoardField.getBounds().x >= startX + GROUP_EDGE_LENGTH + SEPARATION_LINE_THICKNESS)
	    		startX += GROUP_EDGE_LENGTH + SEPARATION_LINE_THICKNESS;
	    	int startY = UPPER_LEFT_CORNER_Y + SEPARATION_LINE_THICKNESS;
	    	while (selectedBoardField.getBounds().y >= startY + GROUP_EDGE_LENGTH + SEPARATION_LINE_THICKNESS)
	    		startY += GROUP_EDGE_LENGTH + SEPARATION_LINE_THICKNESS;
	    	g.fillRect(startX, startY, GROUP_EDGE_LENGTH, GROUP_EDGE_LENGTH);
	    }
	    */
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 JFrame mainFrame = new JFrame();
	     mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	     mainFrame.setSize(1024, 800);
	     MainFrame mainFrameEngine = new MainFrame(mainFrame);
	     mainFrame.getContentPane().add(mainFrameEngine);
	     mainFrame.addKeyListener(mainFrameEngine);
	     mainFrame.setResizable(false);
	     mainFrame.setVisible(true);
	     mainFrame.setTitle("Sudoku Solver");
	}


	@Override
	public void keyTyped(KeyEvent e) {
	}


	@Override
	public void keyPressed(KeyEvent e) {
		int keyPressed = e.getKeyCode();
		
		if (selectedBoardField != null)
		{
			if ((keyPressed >= '1') && (keyPressed <= '9'))
				setSelectedBoardValue(keyPressed - '0');
			else if ((keyPressed == '0') || (keyPressed == KeyEvent.VK_DELETE))
				setSelectedBoardValue(0);
			else if (keyPressed == KeyEvent.VK_LEFT)
			{
				setSelectedBoardField(selectedBoardField.row, (selectedBoardField.column + 8) % 9);
			}
			else if (keyPressed == KeyEvent.VK_RIGHT)
			{
				setSelectedBoardField(selectedBoardField.row, (selectedBoardField.column + 1) % 9);
			}
			else if (keyPressed == KeyEvent.VK_DOWN)
			{
				setSelectedBoardField((selectedBoardField.row + 1) % 9, selectedBoardField.column);
			}
			else if (keyPressed == KeyEvent.VK_UP)
			{
				setSelectedBoardField((selectedBoardField.row + 8) % 9, selectedBoardField.column);
			}
		}
				
		repaint();
	
	}


	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	private void setSelectedBoardField(IndexedLabel newSelectedBoardField)
	{
		if (selectedBoardField != null)
		{
			EFieldStatus status = engine.getField(selectedBoardField.row, selectedBoardField.column).getStatus();
	
			selectedBoardField.setBackground(status == EFieldStatus.EFS_GAME ? Color.WHITE : Color.GRAY);
		}
			
		selectedBoardField = newSelectedBoardField;
		selectedBoardField.setBackground(SEMI_TRANSPARENT_RED);
		
		engine.debugText = String.format("ROW : %d | COLUMN : %d | INDEX : %d | "
				+ "actualValue : %d | currentValue : %s", 
				newSelectedBoardField.row, newSelectedBoardField.column, newSelectedBoardField.index, 
				engine.getField(selectedBoardField.row, selectedBoardField.column).getActualValue(),
				engine.getField(selectedBoardField.row, selectedBoardField.column).getCurrentValue());  

	}
	
	private void setSelectedBoardField(int row, int column)
	{
		setSelectedBoardField(boardFields[row][column]);
	}
	
	private void setSelectedBoardValue(int value)
	{
		if (selectedBoardField == null)
			return;
		
		EFieldStatus status = engine.getField(selectedBoardField.row, selectedBoardField.column).getStatus();
		if	(status != EFieldStatus.EFS_GAME)
			return;
		
		setBoardValue(selectedBoardField.row, selectedBoardField.column, value);	
		rowsChecked[selectedBoardField.row] = engine.getRow(selectedBoardField.row).runCheck();
		columnsChecked[selectedBoardField.column] = engine.getColumn(selectedBoardField.column).runCheck();
		squaresChecked[selectedBoardField.index] = engine.getSquare(selectedBoardField.index).runCheck();

	}
	
	private void setBoardValue(int row, int column, int value)
	{
		
		if ((value > 0) && (value < 10))
		{
			boardFields[row][column].setText(String.valueOf(value));
			engine.setFieldCurrentValue(row, column, value);			
		}
		else
		{
			boardFields[row][column].setText("");
			engine.setFieldCurrentValue(row, column, 0);
		}
	}
	
	

}