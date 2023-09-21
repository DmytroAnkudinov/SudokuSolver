
public abstract class Group {
	public static Engine parent;
	
	protected int index;
	protected Field[] fields;
	
	public Group(int index)
	{
		this.index = index;
		fields = new Field[9];
	}
	
	public boolean runCheck() 
	{
		for (int i = 0; i < 8; ++i)
		{
			if (fields[i].getCurrentValue() == 0)
				continue;
			
			for (int j = i + 1; j < 9; ++j)
				if (fields[i].getCurrentValue() == fields[j].getCurrentValue())
					return true;
		}
		
		return false;
	}
	
	/*
	public void updateCandidates()
	{
		for (int i = 0; i < 9; ++i)
		{
			if (fields[i].getStatus() != EFieldStatus.EFS_GAME)
			{
				for (int j = 0; j < )
			}
		}
	}
	*/
}
