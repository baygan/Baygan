package baseBaygan.objects;

import java.util.ArrayList;

public class DirectTableRequest {
	/**
	 * Name of the table which direct commands are for.
	 */
	private final String tableName;
	/**
	 * True if all commands are read commands.No change will be applied to
	 * database.
	 */
	private final boolean readOnlyRequest;

	/**
	 * Commands that should be applied on certain tuples of the table.
	 */
	public DirectTableRequest(String tableName, boolean readOnly,
			ArrayList<DirectTableCommand> commands) {
		this.tableName = tableName;
		this.readOnlyRequest = readOnly;
		this.commands = commands;
	}

	private final ArrayList<DirectTableCommand> commands;

	public String getTable() {
		return tableName;
	}

	public boolean isReadOnlyRequest() {
		return readOnlyRequest;
	}

	public ArrayList<DirectTableCommand> getCommonds() {
		return commands;
	}
}
