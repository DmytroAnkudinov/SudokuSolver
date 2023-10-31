public class Engine {
	private MainFrame mainFrame;
	private Field[][] field = new Field[9][9];
	
	private GroupHorizontal[] rows = new GroupHorizontal[9];
	private GroupVertical[] columns = new GroupVertical[9];
	private GroupSquare[] squares = new GroupSquare[9];
	
	private volatile EProcessingStatus currentProcessingStatus = EProcessingStatus.EPS_OFF;
	private EProcessingStatus currentProcessingResult = EProcessingStatus.EPS_OFF;
	private ProcessedGroup    currentProcessedGroup = new ProcessedGroup();
	
	public String debugText = "DEBUG";
	
	public Engine(MainFrame mainFrame)
	{
		this.mainFrame = mainFrame;
		Group.parent = this;
		for (int row = 0; row < 9; ++row)
			for (int column = 0; column < 9; ++column)
				field[row][column] = new Field();		
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
	
	public int calculateFilled()
	{
		int result = 81;
		for (int row = 0; row < 9; ++row)
			for (int column = 0; column < 9; ++column)
			{
				if (field[row][column].isSolved() == EFieldStatus.EFS_UNSOLVED)
					result--;
			}
		
		return result;
	}

	// Move checks to the Field class
	public boolean initField(int row, int column, int value)
	{
		if ((row < 0) || (row > 8) || (column < 0) || (column > 8) || (value <= 0) || (value > 9))
			return false;
		
		field[row][column] = new Field(value);
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
	
	public void resetCandidates()
	{
		for (int row = 0; row < 9; ++row)
			for (int column = 0; column < 9; ++column)
				field[row][column].resetCandidates();
	}
	
	public void recheckCandidates()
	{
		resetCandidates();
			
		for (int i = 0; i < 9; ++i)
		{
			rows[i].runCandidateCheck();
			columns[i].runCandidateCheck();
			squares[i].runCandidateCheck();
		}
	}
	
	public ProcessedGroup.Type getCurrentProcessedGroupType()
	{
		return currentProcessedGroup.type;
	}
	
	public int getCurrentProcessedGroupIndex()
	{
		return currentProcessedGroup.index;
	}
	
	private EProcessingStatus performOneProcessingStep()
	{
		EProcessingStatus result = EProcessingStatus.EPS_NO_NEW_DATA;
		
		// More complex analysis
		{
			for (int i = 0; i < 9; ++i)
			{
				Field currentField = currentProcessedGroup.group.getField(i);
				if (currentField.isSolved() == EFieldStatus.EFS_UNSOLVED)
				{
					if (currentField.getAmountExcluded() > 4)
					{
						int groupSize = 0;
						for (int j = 0; j < 9; ++j)
						{
							Field checkedField = currentProcessedGroup.group.getField(j);
							if (checkedField.isSolved() != EFieldStatus.EFS_UNSOLVED)
								continue;
							if ((~currentField.getExcludedBitMask() & ~checkedField.getExcludedBitMask()) == 
									~checkedField.getExcludedBitMask())
								groupSize++;
						}
						
						if (groupSize == 9 - currentField.getAmountExcluded())
						{
							for (int j = 0; j < 9; ++j)
							{
								Field checkedField = currentProcessedGroup.group.getField(j);
								if (checkedField.isSolved() != EFieldStatus.EFS_UNSOLVED)
									continue;
								if ((~currentField.getExcludedBitMask() & ~checkedField.getExcludedBitMask()) != 
										~checkedField.getExcludedBitMask())
								{
									checkedField.excludeBitMask(~currentField.getExcludedBitMask());
									result = EProcessingStatus.EPS_FOUND_NEW_DATA;
								}
							}
						}
					}
				}
			}
		}

		// Check if a square of a group has only one available candidate
		for (int i = 0; i < 9; ++i)
		{
			Field currentField = currentProcessedGroup.group.getField(i);
			if (currentField.isSolved() == EFieldStatus.EFS_UNSOLVED)
			{
				for (int number = 1; number < 10; ++number)
				{
					if (currentField.getAmountExcluded() == 8)
					{
						currentField.setCurrentValue(currentField.getNextIncluded(1));
						recheckCandidates();
						result = EProcessingStatus.EPS_FOUND_NEW_DATA;
					}
				}
			}
		}
		
		// Check if a group has only one available location for a candidate
		{
			int[] availablePositions = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
			for (int i = 0; i < 9; ++i)
			{
				Field currentField = currentProcessedGroup.group.getField(i);
				if (currentField.isSolved() == EFieldStatus.EFS_UNSOLVED)
				{
					for (int number = 1; number < 10; ++number)
					{
						if (!currentField.getExcluded(number))
							availablePositions[number-1]++;
					}
				}
			}
			
			for (int number = 1; number < 10; ++number)
			{
				if (availablePositions[number - 1] == 1)
				{
					for (int i = 0; i < 9; ++i)
					{
						Field currentField = currentProcessedGroup.group.getField(i);
						if ((currentField.isSolved() == EFieldStatus.EFS_UNSOLVED) && !currentField.getExcluded(number))
						{
							currentField.setCurrentValue(number);
							recheckCandidates();
							result = EProcessingStatus.EPS_FOUND_NEW_DATA;
							break;
						}
					}
				}
			}
		}
		
		return result;
	}
	
	private EProcessingStatus performProcessingOfGroups()
	{
		Group[] groups;
		String debugTextPrefix = "";
		
		if (currentProcessedGroup.type == ProcessedGroup.Type.EPGT_ROW)
		{
			debugTextPrefix = "Performing processing of the row ";
			groups = rows;
		}
		else if (currentProcessedGroup.type == ProcessedGroup.Type.EPGT_COLUMN)
		{
			debugTextPrefix = "Performing processing of the column ";
			groups = columns;
		}
		else 
		{
			debugTextPrefix = "Performing processing of the square ";
			groups = squares;
		}

		for (currentProcessedGroup.index = 0; currentProcessedGroup.index < 9; ++currentProcessedGroup.index)
		{
			currentProcessedGroup.group = groups[currentProcessedGroup.index];
			debugText = debugTextPrefix + currentProcessedGroup.index;
			mainFrame.repaint();
			
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (currentProcessingStatus != EProcessingStatus.EPS_IN_PROCESS)
				return currentProcessingStatus;
			
			EProcessingStatus localResult = performOneProcessingStep();
			if (localResult == EProcessingStatus.EPS_FOUND_NEW_DATA)
			{
				mainFrame.updateBoard();
				if (calculateFilled() == 81)
				{
					currentProcessingResult = EProcessingStatus.EPS_SOLVED;
					currentProcessingStatus = EProcessingStatus.EPS_SOLVED; 
					return currentProcessingStatus;
				}
				else
					currentProcessingResult = EProcessingStatus.EPS_FOUND_NEW_DATA;
			}
		}
		
		return currentProcessingStatus;
	}
	
	
	public EProcessingStatus performOneProcessingRun()
	{
		currentProcessingStatus = EProcessingStatus.EPS_IN_PROCESS;
		currentProcessingResult = EProcessingStatus.EPS_NO_NEW_DATA;
		
		recheckCandidates();

		if (currentProcessingStatus == EProcessingStatus.EPS_IN_PROCESS)
		{
			currentProcessedGroup.type = ProcessedGroup.Type.EPGT_ROW;
			performProcessingOfGroups();
		}
		
		if (currentProcessingStatus == EProcessingStatus.EPS_IN_PROCESS)
		{
			currentProcessedGroup.type = ProcessedGroup.Type.EPGT_COLUMN;
			performProcessingOfGroups();
		}
		
		if (currentProcessingStatus == EProcessingStatus.EPS_IN_PROCESS)
		{
			currentProcessedGroup.type = ProcessedGroup.Type.EPGT_SQUARE;
			performProcessingOfGroups();
		}
		
		debugText = "Finished processing with the result " + currentProcessingResult;
		mainFrame.repaint();

		if (currentProcessingStatus != EProcessingStatus.EPS_TERMINATED)
			currentProcessingStatus = EProcessingStatus.EPS_OFF;
		currentProcessedGroup.type = ProcessedGroup.Type.EPGT_NONE;
		currentProcessedGroup.index = 0;
		
		return currentProcessingResult;
	}

	public EProcessingStatus performFullProcessing()
	{
		do
		{
			performOneProcessingRun();
		}
		while ((currentProcessingStatus != EProcessingStatus.EPS_TERMINATED) &&
			   (currentProcessingResult == EProcessingStatus.EPS_FOUND_NEW_DATA));
		
		currentProcessingStatus = EProcessingStatus.EPS_OFF;
		
		return currentProcessingResult;
	}
	
	public void terminateProcessing()
	{
		if (currentProcessingStatus == EProcessingStatus.EPS_IN_PROCESS)
			currentProcessingStatus = EProcessingStatus.EPS_TERMINATED;
	}
	
}
