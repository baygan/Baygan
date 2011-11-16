package baseBaygan.eventPassing.events;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import tools.MethodInvoker;

import baseBaygan.BAbstractComponent;
import baseBaygan.eventPassing.eventListeners.BEventListener;

public class BEvent implements BPassable {
	/**
	 * Request Id shows the id of the message which request something and now
	 * this message is the answer for that request. If the amount of RequestId
	 * is '-1' it means that this is a request itself.
	 */
	private final int requestId;
	/**
	 * Id of the message. either set by the creator or it is set randomly by
	 * itself.
	 */
	private final int id;
	/**
	 * Source of the Message.
	 */
	private final BAbstractComponent source;
	/**
	 * Name of the message
	 */
	private final String name;
	/**
	 * Priority of the message. if a priority of a message is higher then it
	 * will be handled sooner in destination component.
	 */
	private int priority;

	public BEvent(String name, BAbstractComponent source, int passedId,
			int requestId, int priority) {
		this.name = name;
		this.source = source;
		this.id = passedId;
		this.requestId = requestId;
		this.setPriority(priority);
	}

	/**
	 * Since there is no input id therefore if the message is created using this
	 * Constructor then it will generate an random message for itself.
	 * 
	 * @param name
	 * @param source
	 * @param id
	 * @param requestId
	 */
	public BEvent(String name, BAbstractComponent source, int id, int requestId) {
		this(name, source, id, requestId, -1);
	}

	/**
	 * Creates an request message with random id. It is a request message
	 * because the requestId is '-1'.
	 * 
	 * @param name
	 * @param source
	 */
	public BEvent(String name, BAbstractComponent source) {
		this(name, source, getRandomId(), -1);
	}

	/**
	 * Creates an request message with source defined id. It is a request
	 * message because the requestId is '-1'.
	 * 
	 * @param name
	 * @param source
	 */
	public BEvent(String name, BAbstractComponent source, int id) {
		this(name, source, id, -1);
	}

	/**
	 * Creates an request message with randomized defined id. It is a request
	 * message because the requestId is not '-1'.
	 * 
	 * @param name
	 * @param source
	 */
	public BEvent(String name, int requestId, BAbstractComponent source) {
		this(name, source, getRandomId(), requestId);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public BAbstractComponent getSource() {
		return source;
	}

	@Override
	public int getId() {
		return id;
	}

	public int getRequestId() {
		return requestId;
	}

	public boolean isRequest() {
		return getRequestId() == -1;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	/**
	 * Creates a random number between 0 and Integer.MAX_VALUE.
	 * 
	 * @return
	 */
	private static int getRandomId() {
		return (int) (Math.random() * Integer.MAX_VALUE);
	}

	/**
	 * Uses getAppropriateMethods function in order to find methods. if there
	 * were null it returns means which means that the Listener was not
	 * appropriate for this event. but if it finds one or more function then it
	 * invokes those function on input listeners with the current event as
	 * arguments and returns true;
	 * 
	 * @param listener
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public boolean invokeIfAppropriate(BEventListener listener)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		ArrayList<Method> appropiateMethods = getAppropriateMethods(listener);
		if (appropiateMethods.size() == 0)
			return false;
		for (Method method : appropiateMethods) {
			MethodInvoker.invoke(method, listener, new Object[] { this });
		}
		return true;
	}

	/**
	 * Find appropriate functions. An appropriate function is one that has
	 * exactly one parameter and the parameter is of the class as such as the
	 * current BEvent
	 * 
	 * @param listener
	 * @return
	 */
	public ArrayList<Method> getAppropriateMethods(BEventListener listener) {
		ArrayList<Method> methods = new ArrayList<Method>(2);
		for (Class<?> iface : listener.getClass().getInterfaces()) {
			if (iface.getSuperclass() != null
					&& BEventListener.class.equals(iface.getClass()
							.getSuperclass())) {
				for (Method method : iface.getMethods()) {
					if (MethodInvoker.isAppropriate(method,
							new Object[] { this })) {
						methods.add(method);
					}
				}
			}
		}
		return methods;
	}
}
