package baseBaygan.managementSystems;

import java.util.ArrayList;

import baseBaygan.BBaseStatics;
import baseBaygan.BQueryResultFactory;
import baseBaygan.ComponentType;
import baseBaygan.fileSystem.BAccessLayer;
import baseBaygan.fileSystem.BBackupManager;
import baseBaygan.fileSystem.BBufferManager;
import baseBaygan.fileSystem.BLockManager;
import baseBaygan.fileSystem.BStorageManager;
import baseBaygan.global.communication.BCommunicationFactory;
import baseBaygan.highLevelComponents.BEnVarFactory;
import baseBaygan.highLevelComponents.BExcutator;
import baseBaygan.highLevelComponents.BOptimizationLayer;
import baseBaygan.highLevelComponents.BParser;
import baseBaygan.highLevelComponents.BSecurityManager;
import baseBaygan.highLevelComponents.BTableManager;
import baseBaygan.objects.UserInterface;

import tools.ClassInstantiators;
import config.BConfiguration;
import erros.exeptions.ComponentChangeException;

public class BManagementSystem implements BBaseStatics {
	public enum BMSStatus {
		/**
		 * System in initialation state/
		 */
		INITIALIZING,
		/**
		 * System has one or more transactions at hand.
		 */
		WORKING_ON_TRANSACTIONS,
		/**
		 * System has nothing to do.
		 */
		WORKLESS;
		public String toString() {
			if (this.equals(INITIALIZING)) {
				return "Initializing";
			} else if (this.equals(WORKING_ON_TRANSACTIONS)) {
				return "Working on transaction";
			} else if (this.equals(WORKLESS)) {
				return "Workless";
			}
			return null;
		}
	}

	/**
	 * Status shows the current state of the BMS
	 */
	private BMSStatus status;

	/*
	 * High Level Components
	 */
	/**
	 * This holds the <b>Parser</b> that is going to be used in the management
	 * system. <b>Parser</b> is supposed to receive a string of query and hand
	 * out it's parse tree. <br>
	 * It would instantiate the Class set in conf/BDefaultConfig.xml or it
	 * instantiate the class specified in config file which it takes as
	 * constructor input.
	 */
	private BParser parser;
	/**
	 * This holds the <b>OptimizationLayer</b> that is going to be used in the
	 * management system. <b>OtimizationLayer</b> is supposed to receive a
	 * string of query and hand out it's parse tree. <br>
	 * It would instantiate the Class set in conf/BDefaultConfig.xml or it
	 * instantiate the class specified in config file which it takes as
	 * constructor input.
	 */
	private BOptimizationLayer optimizator;

	/**
	 * This holds the <b>Executor</b> that is going to be used in the management
	 * system. <b>Executor</b> is supposed to be able to recieve a parse tree as
	 * input and calculate and execute the tree node by node; starting from the
	 * leaves <br>
	 * It would instantiate the Class set in conf/BDefaultConfig.xml or it
	 * instantiate the class specified in config file which it takes as
	 * constructor input.
	 */
	private BExcutator executor;
	/**
	 * This holds the <b>EnviromentVariableFactory</b> that is going to be used
	 * in the management system. <b>EnviromentVariableFactory</b> is supposed to
	 * be able to create a new instance of EnviromenFatactory for the executor
	 * for every new query<br>
	 * It would instantiate the Class set in conf/BDefaultConfig.xml or it
	 * instantiate the class specified in config file which it takes as
	 * constructor input.
	 */
	private BEnVarFactory evFactory;
	/**
	 * This holds the <b>SecurityManager</b> that is going to be used in the
	 * management system. <b>SecurityManager</b> is supposed to check if users
	 * have permission to access proper tables and views mentioned in their
	 * queries<br>
	 * It would instantiate the Class set in conf/BDefaultConfig.xml or it
	 * instantiate the class specified in config file which it takes as
	 * constructor input.
	 */
	private BSecurityManager securityManager;
	/**
	 * This holds the <b>TableManager</b> that is going to be used in the
	 * management system. <b>TableManager</b> take cares of table pointers. is
	 * the end interface of all high level components with fileSsytem
	 * components. it knows what table have already been created and uses the
	 * answers if requested again by the same query or even other queries. it
	 * manages and answers to user's needs to directly connect to table and
	 * manage to change and alter them. <br>
	 * It would instantiate the Class set in conf/BDefaultConfig.xml or it
	 * instantiate the class specified in config file which it takes as
	 * constructor input.
	 */
	private BTableManager tableManager;
	/*
	 * FileSystem Components
	 */
	/**
	 * This holds the <b>AccessLayer</b> that is going to be used in the
	 * management system. <b>AccessLayer</b> is supposed to be able to find the
	 * physical address of a tuple in the File<br>
	 * It would instantiate the Class set in conf/BDefaultConfig.xml or it
	 * instantiate the class specified in config file which it takes as
	 * constructor input.
	 */
	private BAccessLayer accessLayer;
	/**
	 * This holds the <b>BackupManager</b> that is going to be used in the
	 * management system. <b>BackupManager</b> is supposed to take care of
	 * actions such as backing up he database and reverting changed if any sort
	 * f interrupts or errors occurred during execution of one query. There are
	 * many other responsibilities that BackupManagers have which are explained
	 * independently in Baygan's Documentation<br>
	 * It would instantiate the Class set in conf/BDefaultConfig.xml or it
	 * instantiate the class specified in config file which it takes as
	 * constructor input.
	 */
	private BBackupManager backupManager;
	/**
	 * This holds the <b>BufferManager</b> that is going to be used in the
	 * management system. <b>BufferManager</b> chooses which table or which part
	 * of tables should be kept online and which offline. it is responsible to
	 * apply latest changes in physical file and etc. it acts as a memory
	 * manager and a cache simultaneously.<br>
	 * It would instantiate the Class set in conf/BDefaultConfig.xml or it
	 * instantiate the class specified in config file which it takes as
	 * constructor input.
	 */
	private BBufferManager bufferManager;
	/**
	 * This holds the <b>LockManager</b> that is going to be used in the
	 * management system. <b>LockManager</b>is soppused to take care of all
	 * actions needed to make parallel query execution possible<br>
	 * It would instantiate the Class set in conf/BDefaultConfig.xml or it
	 * instantiate the class specified in config file which it takes as
	 * constructor input.
	 */
	private BLockManager lockManager;
	/**
	 * This holds the <b>StorageManager</b> that is going to be used in the
	 * management system. <b>StorageManager</b> applies changes needed by higher
	 * level components to files.<br>
	 * It would instantiate the Class set in conf/BDefaultConfig.xml or it
	 * instantiate the class specified in config file which it takes as
	 * constructor input.
	 */
	private BStorageManager sotrageManager;
	/**
	 * This holds the <b>BCommunicationFactory</b> that is going to be used in
	 * the management system. <b>CommunicationFactory</b> is used to create new
	 * instance of the communication systems for each BComponent.<br>
	 * It would instantiate the Class set in conf/BDefaultConfig.xml or it
	 * instantiate the class specified in config file which it takes as
	 * constructor input.
	 */
	private BCommunicationFactory communicationFactory;
	/**
	 * This instance specifies the protocol of accessing the TableManager
	 * directly by interfaces and users. This Class is used to parse and create
	 * a request from the input string to pass to TableManager.
	 */
	private DirectTableFactory dtrFactory;
	/**
	 * Contains information and properties in configuration files.<br>
	 * 
	 * <b>Notice:</b><br>
	 * List of configuration files is mentioned in BConfugration class's java
	 * doc.<br>
	 * <b>Default:</b><br>
	 * Default file is stored at "conf/BDefaultConfig.xml"
	 * 
	 * 
	 */

	protected final BConfiguration config;

	/**
	 * Contains transaction managers created for input queries.
	 */
	private final ArrayList<BTransactionManager> tManagers;
	/**
	 * SystemStatusController is able to check the status of all components and
	 * return as a single String.
	 */
	private final SystemStatusController ssController;
	private BQueryResultFactory resultFactory;

	/**
	 * List of at hand transaction managers.
	 * 
	 * @param configAdd
	 */
	public BManagementSystem(String configAdd) throws ComponentChangeException {
		config = new BConfiguration(configAdd);
		instantiateComponents();
		tManagers = new ArrayList<BTransactionManager>();
		ssController = new SystemStatusController();
	}

	public BManagementSystem() throws ComponentChangeException {
		config = new BConfiguration();
		instantiateComponents();
		tManagers = new ArrayList<BTransactionManager>();
		ssController = new SystemStatusController();
	}

	/**
	 * This function instantiates every component needed for the
	 * ManagementSystem to run.<br>
	 * <b>Notice:</b><br>
	 * If you need to add a new component which is not in Baygan's Architecture
	 * you re-implement this function in your own ManagementSystem (if you
	 * management system extends BManagementSystem class)
	 * 
	 * @throws ComponentChangeException
	 */
	private void instantiateComponents() throws ComponentChangeException {
		setAccessLayer((BAccessLayer) ClassInstantiators.instantiate(config
				.getComponentClassPath(ComponentType.ACCESS_LAYER.toString()),
				null));
		// TODO
	}

	public void handleRequest(UserInterface user) {
		createTransactionManager(user);
	}

	private BTransactionManager createTransactionManager(UserInterface user) {
		BTransactionManager bTransactionManager = new BTransactionManager(this,
				user);
		tManagers.add(bTransactionManager);
		// ----------
		Thread thread = new Thread(bTransactionManager);
		thread.start();
		// ----------
		return bTransactionManager;
	}

	//
	// public abstract QueryCondition getQueryCondition(Query query);
	//
	// public abstract Query optimize(Query query);
	//
	// public abstract TransactionResult evaluateQuery(Query query);
	//
	// public ArrayList<String> getTableList() {
	// return fileSystem.getTableList();
	// }
	//
	// public String getGrammerAddress() {
	// // return Config.getGrammerAddress();
	// }
	//
	// public Table getTable(String name) throws Exception {
	// return fileSystem.getTable(name);
	// }

	public BStorageManager getSotrageManager() {
		return sotrageManager;
	}

	public void setSotrageManager(BStorageManager sotrageManager)
			throws ComponentChangeException {
		if (!isSafeToChangeComponent())
			throw new ComponentChangeException();
		else
			this.sotrageManager = sotrageManager;
	}

	public BLockManager getLockManager() {
		return lockManager;
	}

	public void setLockManager(BLockManager lockManager)
			throws ComponentChangeException {
		if (!isSafeToChangeComponent())
			throw new ComponentChangeException();
		else
			this.lockManager = lockManager;
	}

	public BBufferManager getBufferManager() {
		return bufferManager;
	}

	public void setBufferManager(BBufferManager bufferManager)
			throws ComponentChangeException {
		if (!isSafeToChangeComponent())
			throw new ComponentChangeException();
		else
			this.bufferManager = bufferManager;
	}

	public BBackupManager getBackupManager() {
		return backupManager;
	}

	public void setBackupManager(BBackupManager backupManager)
			throws ComponentChangeException {
		if (!isSafeToChangeComponent())
			throw new ComponentChangeException();
		else
			this.backupManager = backupManager;
	}

	public BAccessLayer getAccessLayer() {
		return accessLayer;
	}

	public void setAccessLayer(BAccessLayer accessLayer)
			throws ComponentChangeException {
		if (!isSafeToChangeComponent())
			throw new ComponentChangeException();
		else
			this.accessLayer = accessLayer;
	}

	public BSecurityManager getSecurityManager() {
		return securityManager;
	}

	public void setSecurityManager(BSecurityManager securityManager)
			throws ComponentChangeException {
		if (!isSafeToChangeComponent())
			throw new ComponentChangeException();
		else
			this.securityManager = securityManager;
	}

	public BEnVarFactory getEvFactory() {

		return evFactory;
	}

	public void setEvFactory(BEnVarFactory evFactory)
			throws ComponentChangeException {
		if (!isSafeToChangeComponent())
			throw new ComponentChangeException();
		else
			this.evFactory = evFactory;
	}

	public BExcutator getExecutor() {
		return executor;
	}

	public void setExecutor(BExcutator executor)
			throws ComponentChangeException {
		if (!isSafeToChangeComponent())
			throw new ComponentChangeException();
		else
			this.executor = executor;
	}

	public BOptimizationLayer getOptimizator() {
		return optimizator;
	}

	public void setOptimizator(BOptimizationLayer optimizator)
			throws ComponentChangeException {
		if (!isSafeToChangeComponent())
			throw new ComponentChangeException();
		else
			this.optimizator = optimizator;
	}

	public BParser getParser() {
		return parser;
	}

	public void setParser(BParser parser) throws ComponentChangeException {
		if (!isSafeToChangeComponent())
			throw new ComponentChangeException();
		else
			this.parser = parser;

	}

	/**
	 * Returns number of parallel queries or transactions it has at hand.
	 * 
	 * @return
	 */
	public int countTransaction() {
		return tManagers.size();
	}

	/**
	 * String returned by this function contains status of the Management system
	 * such as if it is working at the moment or how many queries it has or etc.
	 * 
	 * @return
	 */
	public String getStatusDescription() {
		return "BManagementSystem Status: \n\r" + "		Status: "
				+ status().toString() + "	Free Memory: "
				+ Runtime.getRuntime().freeMemory() + "	Transaction: "
				+ countTransaction();
	}

	public BMSStatus status() {
		return status;
	}

	public boolean isSystemStarted() {
		return status != BMSStatus.INITIALIZING;
	}

	public boolean isSafeToChangeComponent() {
		if (isSystemStarted())
			return false;
		return true;
	}

	public DirectTableFactory getDirectRequestFactory() {
		return dtrFactory;
	}

	public void setDirectRequestFactory(DirectTableFactory dtrFactory)
			throws ComponentChangeException {
		if (isSafeToChangeComponent())
			this.dtrFactory = dtrFactory;
		throw new ComponentChangeException();
	}

	public BTableManager getTableManager() {
		return tableManager;
	}

	public void setTableManager(BTableManager tableManager)
			throws ComponentChangeException {
		if (isSafeToChangeComponent())
			this.tableManager = tableManager;
		else
			throw new ComponentChangeException();
	}

	public BQueryResultFactory getResultFactory() {
		return resultFactory;
	}

	public void setResultFactory(BQueryResultFactory resultFactory)
			throws ComponentChangeException {
		if (isSafeToChangeComponent())
			this.resultFactory = resultFactory;
		else
			throw new ComponentChangeException();
	}

	public SystemStatusController getSsController() {
		return ssController;
	}

	public BCommunicationFactory getCommunicationFactory() {
		return communicationFactory;
	}

	public void setCommunicationFactory(
			BCommunicationFactory communicationFactory) {
		this.communicationFactory = communicationFactory;
	}
}
