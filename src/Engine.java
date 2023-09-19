public class Engine {
	private MainFrame mainFrame;
	private Field[][] field = new Field[9][9];
	
	private GroupHorizontal[] rows = new GroupHorizontal[9];
	private GroupVertical[] columns = new GroupVertical[9];
	private GroupSquare[] squares = new GroupSquare[9];
	
	private EProcessingStatus currentProcessingStatus;
	private ProcessedGroup    currentProcessedGroup = new ProcessedGroup();
	
	public String debugText = "DEBUG";
	
	public Engine(MainFrame mainFrame)
	{
		this.mainFrame = mainFrame;
		Group.parent = this;
		for (int row = 0; row < 9; ++row)
			for (int column = 0; column < 9; ++column)
				field[row][column] = new Field(row, column);		
	}
	
	public void initGroups()
	{
		for (int i = 0; i < 9; ++i)
		{
			rows[i] = new GroupHorizontal(i);
			columns[i] = new GroupVertical(i);
			squares[i] = new GroupSquare(i);
		}
	}
	
	public Group getRow(int index)
	{
		return rows[index];
	}
	
	public Group getColumn(int index)
	{
		return columns[index];
	}
	
	public Group getSquare(int index)
	{
		return squares[index];
	}

	// Move checks to the Field class
	public boolean initField(int row, int column, int value)
	{
		if ((row < 0) || (row > 8) || (column < 0) || (column > 8) || (value <= 0) || (value > 9))
			return false;
		
		field[row][column] = new Field(row, column, value);
		return true;
	}
	
	public boolean isFieldFilled(int row, int column)
	{
		if ((row < 0) || (row > 8) || (column < 0) || (column > 8))
			return false;
		
		return (field[row][column].getStatus() !=  EFieldStatus.EFS_UNSOLVED);
	}
	
	public Field getField(int row, int column)
	{
		if ((row < 0) || (row > 8) || (column < 0) || (column > 8))
			return null;
		
		return field[row][column];
	}
	
	public int getFieldCurrentValue(int row, int column)
	{
		if ((row < 0) || (row > 8) || (column < 0) || (column > 8))
			return 0;
		
		return field[row][column].getCurrentValue();
	}
	
	public boolean setFieldCurrentValue(int row, int column, int value)
	{
		if ((row < 0) || (row > 8) || (column < 0) || (column > 8) || (value < 0) || (value > 9))
			return false;
		
		field[row][column].setCurrentValue(value);
		return true;		
	}
	
	public ProcessedGroup.Type getCurrentProcessedGroupType()
	{
		return currentProcessedGroup.type;
	}
	
	public int getCurrentProcessedGroupIndex()
	{
		return currentProcessedGroup.index;
	}
	
	public EProcessingStatus performOneProcessingStep()
	{
		EProcessingStatus result = EProcessingStatus.EPS_NO_NEW_DATA;
		
		return result;
	}
	
	public EProcessingStatus performOneProcessingRun()
	{
		currentProcessingStatus = EProcessingStatus.EPS_IN_PROCESS;

		currentProcessedGroup.type = ProcessedGroup.Type.EPGT_ROW;
		for (currentProcessedGroup.index = 0; currentProcessedGroup.index < 9; ++currentProcessedGroup.index)
		{
			debugText = "Performing processing of the row " + currentProcessedGroup.index;
			mainFrame.repaint();
			EProcessingStatus localResult = performOneProcessingStep();
			if (localResult == EProcessingStatus.EPS_FOUND_NEW_DATA)
				currentProcessingStatus = EProcessingStatus.EPS_FOUND_NEW_DATA;
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		currentProcessedGroup.type = ProcessedGroup.Type.EPGT_COLUMN;
		for (currentProcessedGroup.index = 0; currentProcessedGroup.index < 9; ++currentProcessedGroup.index)
		{
			debugText = "Performing processing of the column " + currentProcessedGroup.index;
			mainFrame.repaint();
			EProcessingStatus localResult = performOneProcessingStep();
			if (localResult == EProcessingStatus.EPS_FOUND_NEW_DATA)
				currentProcessingStatus = EProcessingStatus.EPS_FOUND_NEW_DATA;
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		currentProcessedGroup.type = ProcessedGroup.Type.EPGT_SQUARE;
		for (currentProcessedGroup.index = 0; currentProcessedGroup.index < 9; ++currentProcessedGroup.index)
		{
			debugText = "Performing processing of the square " + currentProcessedGroup.index;
			mainFrame.repaint();
			EProcessingStatus localResult = performOneProcessingStep();
			if (localResult == EProcessingStatus.EPS_FOUND_NEW_DATA)
				currentProcessingStatus = EProcessingStatus.EPS_FOUND_NEW_DATA;
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		currentProcessedGroup.type = ProcessedGroup.Type.EPGT_NONE;
		currentProcessedGroup.index = 0;
		debugText = "Finished processing with the status " + currentProcessingStatus;
		mainFrame.repaint();
		
		return currentProcessingStatus;
	}
}
