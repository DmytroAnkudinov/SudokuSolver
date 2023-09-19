
public class GroupVertical extends Group {
	public GroupVertical(int index)
	{
		super(index);
		
		for (int i = 0; i < 9; ++i)
			fields[i] = parent.getField(i, index);
	}
}
