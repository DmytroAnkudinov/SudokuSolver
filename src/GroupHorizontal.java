
public class GroupHorizontal extends Group {
	
	public GroupHorizontal(int index)
	{
		super(index);
		
		for (int i = 0; i < 9; ++i)
			fields[i] = parent.getField(index, i);
	}
}
