package baseBaygan.objects;

/**
 * Command to change a tuple
 * 
 * @author Salim
 * 
 */
public class DirectTableCommand {
	private final String command;
	/**
	 * Shows the first of a group (in order ) which the command should be
	 * applied to.
	 */
	private final int firstTuplePhysicalId;
	/**
	 * Shows the last of a group (in order ) which the command should be applied
	 * to.
	 */
	private final int lastTuplePhysicalId;
	/**
	 * shows the first of a group (in order ) which the command should be
	 * applied to.
	 */
	private final Object firstTuplePrimaryKey; // TODO
	/**
	 * shows the last of a group (in order ) which the command should be applied
	 * to.
	 */
	private final Object lastTuplePrimaryKey; // TODO

	public DirectTableCommand(int firstTuplePhysicalId,
			int lastTuplePhysicalId, String cmd) {
		this.firstTuplePhysicalId = firstTuplePhysicalId;
		this.command = cmd;
		this.lastTuplePhysicalId = lastTuplePhysicalId;
		this.firstTuplePrimaryKey = null;
		this.lastTuplePrimaryKey = null;

	}

	public DirectTableCommand(Object firstTuplePrimaryKey,
			Object lastTuplePrimaryKey, String cmd) {
		this.firstTuplePrimaryKey = firstTuplePrimaryKey;
		this.command = cmd;
		this.lastTuplePrimaryKey = lastTuplePrimaryKey;
		this.firstTuplePhysicalId = -1;
		this.lastTuplePhysicalId = -1;
	}

	public DirectTableCommand(Object tuplePrimaryKey, String cmd) {
		this.firstTuplePrimaryKey = tuplePrimaryKey;
		this.command = cmd;
		this.lastTuplePrimaryKey = tuplePrimaryKey;
		this.firstTuplePhysicalId = -1;
		this.lastTuplePhysicalId = -1;
	}

	public DirectTableCommand(int tuplePhysicalId, String cmd) {
		this.firstTuplePhysicalId = tuplePhysicalId;
		this.command = cmd;
		this.lastTuplePhysicalId = tuplePhysicalId;
		this.firstTuplePrimaryKey = null;
		this.lastTuplePrimaryKey = null;
	}

	/**
	 * It determines if the first and last TuplePhysicalIds are set or not.
	 * 
	 * @return
	 */
	public boolean hasPhysicalIds() {
		return (firstTuplePrimaryKey() == null && getLastTuplePrimaryKey() == null);
	}

	/**
	 * Determines if only a single tuple is going to change
	 * 
	 * @return
	 */
	public boolean isSingle() {
		return (hasPhysicalIds() && firstTuplePhysicalId() == lastTuplePhysicalId());
	}

	public String command() {
		return command;
	}

	public int firstTuplePhysicalId() {
		return firstTuplePhysicalId;
	}

	public int lastTuplePhysicalId() {
		return lastTuplePhysicalId;
	}

	public Object firstTuplePrimaryKey() {
		return firstTuplePrimaryKey;
	}

	public Object getLastTuplePrimaryKey() {
		return lastTuplePrimaryKey;
	}

}
