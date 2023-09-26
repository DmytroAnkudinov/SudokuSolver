
public class Field {
	private EFieldStatus status;
	private int actualValue;
	private int currentValue;
	private int candidates;
	private int excluded;
	private int amountExcluded;
	
	public Field(int initialValue)
	{
		this.actualValue = initialValue;
		this.currentValue = initialValue;
		this.status = EFieldStatus.EFS_INITIAL;
		candidates = 0;
		excluded = 0;
		amountExcluded = -1;
	}
	
	public Field()
	{
		this.actualValue = 0;
		this.status = EFieldStatus.EFS_GAME;
		candidates = 1;
		excluded = 1;
		resetCandidates();
	}
	
	public void resetCandidates()
	{
		if (status != EFieldStatus.EFS_GAME)
			return;
		
		amountExcluded = 0;
		for (int i = 0; i < 9; ++i)
		{
			candidates = (1 << 10) - 1;
			excluded = 1;
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
		return ((candidates & (1 << i)) == (1 << i)) ;
	}
	
	public void setCandidate(int i, boolean value)
	{
		if (value)
			candidates |= (1 << i);
		else
			candidates &= ~(1 << i);
	}
	
	public void toggleCandidate(int i)
	{
		candidates ^= (1 << i);
	}
	
	public boolean getExcluded(int i)
	{
		return ((excluded & (1 << i)) == (1 << i)) ;
	}
	
	public void exclude(int i)
	{
		if ((excluded & (1 << i)) == 0)
			amountExcluded++;
		excluded |= (1 << i);
	}
	
	public int getAmountExcluded()
	{
		return amountExcluded;
	}
	
	public int getNextIncluded(int startValue)
	{
		for (int i = startValue; i < 10; ++i)
		{
			if ((excluded & (1 << i)) == 0)
				return i;
		}
		
		return 0;
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