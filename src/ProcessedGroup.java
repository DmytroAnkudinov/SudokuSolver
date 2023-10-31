public class ProcessedGroup {
	public enum Type {EPGT_NONE, EPGT_ROW, EPGT_COLUMN, EPGT_SQUARE};
	
	public Type  type = Type.EPGT_NONE;
	public int   index = 0;
	public Group group;
	
}
