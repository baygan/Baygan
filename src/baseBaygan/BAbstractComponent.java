package baseBaygan;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

import baseBaygan.eventPassing.eventListeners.BEventListener;
import baseBaygan.eventPassing.events.BEvent;
import baseBaygan.global.communication.BCommunicationFactory;
import baseBaygan.global.communication.BCommunicationSystem;

public abstract class BAbstractComponent extends BAutoInstantiatable implements
		Runnable, BEventListener {
	public enum ComponentStatus {
		/**
		 * When it is doing something
		 */
		WORKING_ON_TASK,
		/**
		 * When it is waiting for another component to response to a request of
		 * the component
		 */
		WAITING_RESPONSE,
		/**
		 * Has nothing to do.
		 */
		WOROKLESS,
		/**
		 * It is actually dead and not responding to anything any more.
		 */
		DEAD;
		public String toString() {
			if (this.equals(WORKING_ON_TASK)) {
				return "Working On Task";
			} else if (this.equals(WAITING_RESPONSE)) {
				return "Waiting Response";
			} else if (this.equals(WOROKLESS)) {
				return "Workless";
			} else if (this.equals(DEAD)) {
				return "Dead";
			}
			return null;
		};
	}

	/**
	 * Event Queue keeps events of one component. Every events is treated as a
	 * task of the component.
	 */
	protected final PriorityQueue<BEvent> eventQueue;
	/**
	 * Show the status of the component. Some status such as DEAD are set in
	 * abstract class but others should be implemented in each Component such as
	 * XManager.
	 */
	private ComponentStatus status;
	/**
	 * keeps all listeners
	 */
	private final ArrayList<BEventListener> listeners;
	/**
	 * Contains CommunicationSystem needed to send/recieve events and
	 * communicate with other BComponents
	 */
	protected BCommunicationSystem communicationSystem;

	public BAbstractComponent(Object[] args) {
		super(args);
		listeners = new ArrayList<BEventListener>();
		eventQueue = new PriorityQueue<BEvent>(3, new Comparator<BEvent>() {
			@Override
			public int compare(BEvent bm1, BEvent bm2) {
				if (bm1.getPriority() > bm2.getPriority())
					return 1;
				if (bm1.getPriority() < bm2.getPriority())
					return -1;
				return 0;
			}
		});
	}

	/**
	 * @see BAutoInstantiatable
	 */
	@Override
	protected void initArgs(Object[] args) {
		// creating communicationSystem
		BCommunicationFactory cfactory = (BCommunicationFactory) args[0];
		setCommunicationSystem(cfactory.createCommunicationSystem(this));
	}

	/**
	 * Events are added to the eventQueue of the Component . therefore it can
	 * choose and respond to them according to their priority.
	 */
	public void addEvent(BEvent event) {
		eventQueue.add(event);
	}

	/**
	 * Returns next event to process
	 * 
	 * @return
	 */
	private BEvent nextEvent() {
		return eventQueue.poll();
	}

	@Override
	public void run() {
		initLife();
		while (alive()) {
			beforeAnyEvent();
			BEvent event = nextEvent();
			if (event == null)
				waitForEvent();
			else
				try {
					handleEvent(event);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			afterAnyEvent(event);
		}
		beforeDeath();
		// component wont response to anything
		setStatus(ComponentStatus.DEAD);
	}

	protected abstract void beforeDeath();

	protected void waitForEvent() {

	}

	/**
	 * This function runs afters handling every task(event or event).
	 * 
	 * @param event
	 */
	protected abstract void afterAnyEvent(BEvent event);

	/**
	 * You can add couple of event listeners to a component. for every input
	 * event it checks the list of event listeners automatically and then if
	 * there were any class that implemented BEventListener or any of its childs
	 * which had a function appropriate for the event it will simply call the
	 * event listener's method with the event as input. otherwise it only calls
	 * ProcessEvent which every Component has.
	 * 
	 * @param event
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	protected void handleEvent(BEvent event) throws IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {
		testListener(event);
		processEvent(event);
	}

	private boolean testListener(BEvent event) throws IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {
		boolean found = false;
		for (BEventListener listener : getListeners()) {
			if (event.invokeIfAppropriate(listener))
				found = true;
		}
		return found;
	}

	/**
	 * Determines if the BComponent accepts any event
	 * 
	 * @return
	 */
	public abstract boolean acceptsEvents();

	/**
	 * This function is supposed to take care of every Act(Event or Event).
	 * 
	 * @param event
	 */
	public abstract void processEvent(BEvent event);

	/**
	 * This function runs when the last Act (Event) is taken care of.
	 */
	protected abstract void beforeAnyEvent();

	/**
	 * This function should prepare the component and set initial values before
	 * it starts it's life cycle of handling acts.
	 */
	protected abstract void initLife();

	/**
	 * This function determines if the components is still alive or should be
	 * destroyed and we dont need it anymore
	 * 
	 * @return
	 */
	protected abstract boolean alive();

	public abstract ComponentType type();

	/**
	 * It returns number of events in eventQueue as number of future tasks of
	 * one component.
	 * 
	 * @return
	 */
	public int futureTasks() {
		return eventQueue.size();
	}

	public ComponentStatus status() {
		return status;
	}

	protected void setStatus(ComponentStatus status) {
		this.status = status;
	}

	public String getStatusDescription() {
		return type().toString() + ": 		Status: " + status().toString()
				+ "	Future Tasks: " + futureTasks() + "	Queries: "
				+ queriesAtHand() + " ";
	}

	protected abstract int queriesAtHand();

	public ArrayList<BEventListener> getListeners() {
		return listeners;
	}

	public void addListener(BEventListener listener) {
		listeners.add(listener);
	}

	public BCommunicationSystem getCommunicationSystem() {
		return communicationSystem;
	}

	public void setCommunicationSystem(BCommunicationSystem communicationSystem) {
		this.communicationSystem = communicationSystem;
	}

}
