package baseBaygan;

/**
 * Contains Types of components.<br>
 * <b>Notice:</b> <br>
 * If you are planing to add a new component that is not in Baygan's
 * Architecture you better add its name and type to this enum.
 * 
 * @author Salim
 * 
 */
public enum ComponentType {
	/*
	 * High level components
	 */
	ACCESS_LAYER, BACKUP_MANAGER, LOCK_MANAGER, BUFFER_MANAGER, STORAGE_MANAGER,
	/*
	 * File System components
	 */
	ENV_VAR_FACTORY, EXECUTER, OPTIMIZATION_LAYER, PARSER, SECURITY_MANAGER, TABLE_MANAGER,
	/*
	 * OTHER COMPONENTS
	 */
	TRANSACTION_MANAGER;
	public String toString() {
		if (this.equals(ACCESS_LAYER))
			return "AccessLayer";
		else if (this.equals(BACKUP_MANAGER))
			return "BackupManager";
		else if (this.equals(LOCK_MANAGER))
			return "LockManager";
		else if (this.equals(BUFFER_MANAGER))
			return "BufferManager";
		else if (this.equals(STORAGE_MANAGER))
			return "StrorageManager";
		else if (this.equals(ENV_VAR_FACTORY))
			return "EnviromentVariablesFactory";
		else if (this.equals(EXECUTER))
			return "Executor";
		else if (this.equals(OPTIMIZATION_LAYER))
			return "OptmizationLayer";
		else if (this.equals(PARSER))
			return "Parser";
		else if (this.equals(SECURITY_MANAGER))
			return "SecurityManager";
		else if (this.equals(TABLE_MANAGER))
			return "TableManager";
		else if (this.equals(TRANSACTION_MANAGER))
			return "TransactionManager";
		return null;
	};
}
