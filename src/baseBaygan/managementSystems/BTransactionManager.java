package baseBaygan.managementSystems;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import baseBaygan.BAbstractComponent;
import baseBaygan.BBaseStatics;
import baseBaygan.ComponentType;
import baseBaygan.eventPassing.eventListeners.BParsedQueryEventListener;
import baseBaygan.eventPassing.eventListeners.BQueryResultEventHandler;
import baseBaygan.eventPassing.events.BDirectRequestEvent;
import baseBaygan.eventPassing.events.BEvent;
import baseBaygan.eventPassing.events.BParsedQueryEvent;
import baseBaygan.eventPassing.events.BQueryEvent;
import baseBaygan.eventPassing.events.BRequestResultEvent;
import baseBaygan.highLevelComponents.BExcutator;
import baseBaygan.highLevelComponents.BOptimizationLayer;
import baseBaygan.highLevelComponents.BParser;
import baseBaygan.highLevelComponents.BTableManager;
import baseBaygan.objects.DirectTableRequest;
import baseBaygan.objects.Query;
import baseBaygan.objects.UserInterface;

/**
 * This class makes it possible to execute multiple queries. Each instance of
 * this class takes care of one query.
 * 
 * @author Salim
 * 
 */
public class BTransactionManager extends BAbstractComponent implements
		BBaseStatics, BParsedQueryEventListener, BQueryResultEventHandler {
	private boolean resultSent = false;
	private long startTime;
	private String startDate;
	/**
	 * Pointer to BManagementSystem managers
	 */
	BManagementSystem mSystem;
	private final UserInterface user;

	public BTransactionManager(BManagementSystem mSystem, UserInterface user) {
		super(null);
		addListener(this);
		this.mSystem = mSystem;
		this.user = user;
	}

	/**
	 * Does not really need AutoInstanstiable methods.
	 */
	@Override
	protected void initArgs(Object[] args) {
	}

	/**
	 * Request are handled and first proper events are sent to proper
	 * Components.
	 */
	private void handleRequest() {
		// -------set start date and time
		startTime = System.currentTimeMillis();
		startDate = new SimpleDateFormat().format(Calendar.getInstance()
				.getTime());
		// -------- handle the request
		String request = user.readRequest();
		if (request.startsWith(DIRECT_REQUEST_STRING_HEADER)) {
			handleDirectTableRequest(request);
		} else {
			handleQuery(new Query(request));
		}

	}

	/**
	 * Parses the direct request event string and dispatches a
	 * DirectRequestEvent to TableManager and waits for results.
	 * 
	 * @param request
	 */
	private void handleDirectTableRequest(String request) {
		DirectTableRequest directRequest = mSystem.getDirectRequestFactory()
				.createRequest(request);
		communicationSystem.dispatchEventByClass(BTableManager.class,
				new BDirectRequestEvent(user.getName(), this, directRequest));
	}

	/**
	 * This function is called when a new query is entered by user.<br>
	 * <b>Notice:</b><br>
	 * If you want to change the way that ManagementSystem take care of a query
	 * you should either re-implement this function or the BTransactionManager
	 * class.
	 * 
	 * 
	 * @param Query
	 *            instance
	 * @return
	 */
	public void handleQuery(Query query) {
		BQueryEvent e = new BQueryEvent("QueryEvent", this, query);
		communicationSystem.dispatchEventByClass(BParser.class, e);
	}

	/**
	 * This function handles flow of a parse tree event from parser to suer(if
	 * has errors) or to optimization layer (if there were no errors during
	 * parsing procedure) or even to executor if it is optimized too.
	 * 
	 * @param e
	 */
	public void handleParsedQuery(BParsedQueryEvent e) {
		if (!e.isOptimized()) {
			if (e.getParseTee().hasError())
				user.write(mSystem.getResultFactory().createQueryResult(
						e.getParseTee(), startTime, startDate));
			else
				communicationSystem.dispatchEventByClass(
						BOptimizationLayer.class, e);
		} else {
			communicationSystem.dispatchEventByClass(BExcutator.class, e);
		}

	}

	/**
	 * Forwards the result to user.
	 * 
	 * @param e
	 */
	public void handleResultEvent(BRequestResultEvent e) {
		user.write(mSystem.getResultFactory().createQueryResult(e, startTime,
				startDate));
	}

	@Override
	protected void beforeDeath() {

	}

	@Override
	public boolean acceptsEvents() {
		return true; // TODO should be revised
	}

	@Override
	protected void afterAnyEvent(BEvent e) {
		setStatus(ComponentStatus.WOROKLESS);
	}

	@Override
	public void processEvent(BEvent e) {
	}

	@Override
	protected void beforeAnyEvent() {
		setStatus(ComponentStatus.WORKING_ON_TASK);

	}

	@Override
	protected void initLife() {
		handleRequest();
		setStatus(ComponentStatus.WOROKLESS);

	}

	@Override
	protected boolean alive() {
		return !resultSent;
	}

	@Override
	public ComponentType type() {
		return ComponentType.TRANSACTION_MANAGER;
	}

	@Override
	protected int queriesAtHand() {
		return 1;
	}

}