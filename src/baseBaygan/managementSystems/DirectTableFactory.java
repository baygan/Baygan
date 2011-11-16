package baseBaygan.managementSystems;

import java.util.ArrayList;

import tools.Pair;
import baseBaygan.BAutoInstantiatable;
import baseBaygan.objects.DirectTableCommand;
import baseBaygan.objects.DirectTableRequest;

public abstract class DirectTableFactory extends BAutoInstantiatable {

	public DirectTableFactory(Object[] args) {
		super(args);
		// TODO Auto-generated constructor stub
	}

	/**
	 * This function parses a DTR input string and parses the commands according
	 * to protocols defined by TableManager's implementation.
	 * 
	 * @param commandString
	 * @return
	 */
	public DirectTableRequest createRequest(String commandString) {
		String tableName = readTableName(commandString);
		Pair<ArrayList<DirectTableCommand>, Boolean> readCommandsAndTypes = readCommandsAndTypes();
		return new DirectTableRequest(tableName,
				readCommandsAndTypes.getSecond(),
				readCommandsAndTypes.getFirst());
	}

	private String readTableName(String cmdString) {
		return cmdString.split(" ")[0].trim();
	}

	/**
	 * Parses the commands in cmd String. the way commands should be parsed
	 * depends on their syntax which is designed by who designed the
	 * TableManager. The returned pair contains an array list of commands and
	 * one Boolean which specifies if all the commands in the list are data read
	 * commands or some of them are willing to change, delete or add data to the
	 * table.<br>
	 * <b>Notice:</b><br>
	 * If you are changing the TableManager then you should change re-implement
	 * this function too.
	 * 
	 * @return
	 */
	protected abstract Pair<ArrayList<DirectTableCommand>, Boolean> readCommandsAndTypes();
}
