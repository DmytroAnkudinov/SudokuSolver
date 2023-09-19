public class GroupSquare extends Group {
	public GroupSquare(int index)
	{
		super(index);
		
		for (int i = 0; i < 9; ++i)
			fields[i] = parent.getField((index / 3) * 3 + (i / 3), (index % 3) * 3 + (i % 3));
	}
}