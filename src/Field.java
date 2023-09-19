
public class Field {
	private EFieldStatus status;
	private int row;
	private int column;
	private int actualValue;
	private int currentValue;
	private boolean[] candidates;
	
	public Field(int row, int column, int initialValue)
	{
		this.row = row;
		this.column = column;
		this.actualValue = initialValue;
		this.currentValue = initialValue;
		this.status = EFieldStatus.EFS_INITIAL;
		candidates = null;
	}
	
	public Field(int row, int column)
	{
		this.row = row;
		this.column = column;
		this.actualValue = 0;
		this.status = EFieldStatus.EFS_GAME;
		candidates = new boolean[9];
		for (int i = 0; i < 9; ++i)
			candidates[i] = true;
	}
	
	public int getCurrentValue()
	{
		return this.currentValue;
	}
	
	public void setCurrentValue(int value)
	{
		this.currentValue = value;
	}
	
	public int getActualValue()
	{
		return this.actualValue;
	}
	
	public boolean getCandidate(int i)
	{
		return candidates[i];
	}
	
	public void setCandidate(int i, boolean value)
	{
		candidates[i] = value;
	}
	
	public EFieldStatus getStatus()
	{
		if (status == EFieldStatus.EFS_INITIAL)
			return status;
		
		return EFieldStatus.EFS_GAME;
		
		/*
		if (currentValue == 0)
			return EFieldStatus.EFS_UNSOLVED;

		if (actualValue == currentValue)
			return EFieldStatus.EFS_SOLVED_CORRECTLY;
		
		return EFieldStatus.EFS_SOLVED_INCORRECTLY;
		*/
	}
}
