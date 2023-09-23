
public class Field {
	private EFieldStatus status;
	private int actualValue;
	private int currentValue;
	private boolean[] candidates;
	private boolean[] excluded;
	
	public Field(int initialValue)
	{
		this.actualValue = initialValue;
		this.currentValue = initialValue;
		this.status = EFieldStatus.EFS_INITIAL;
		candidates = null;
		excluded = null;
	}
	
	public Field()
	{
		this.actualValue = 0;
		this.status = EFieldStatus.EFS_GAME;
		candidates = new boolean[9];
		excluded = new boolean[9];
		resetCandidates();
	}
	
	public void resetCandidates()
	{
		if (status != EFieldStatus.EFS_GAME)
			return;
		
		for (int i = 0; i < 9; ++i)
		{
			candidates[i] = true;
			excluded[i] = false;
		}
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
		return candidates[i - 1];
	}
	
	public void setCandidate(int i, boolean value)
	{
		candidates[i - 1] = value;
	}
	
	public void toggleCandidate(int i)
	{
		candidates[i - 1] = !candidates[i - 1];
	}
	
	public boolean getExcluded(int i)
	{
		return excluded[i - 1];
	}
	
	public void setExcluded(int i, boolean value)
	{
		excluded[i - 1] = value;
	}
	
	public EFieldStatus getStatus()
	{
		if (status == EFieldStatus.EFS_INITIAL)
			return status;
		
		return EFieldStatus.EFS_GAME;
	}
	
	public EFieldStatus isSolved()
	{
		if (status == EFieldStatus.EFS_INITIAL)
			return status;

		if (currentValue == 0)
			return EFieldStatus.EFS_UNSOLVED;

		if (actualValue == currentValue)
			return EFieldStatus.EFS_SOLVED_CORRECTLY;
		
		return EFieldStatus.EFS_SOLVED_INCORRECTLY;
	}
}