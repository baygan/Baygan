package baseBaygan.global.communication;

import baseBaygan.BAbstractComponent;
import baseBaygan.eventPassing.events.BEvent;

/**
 * This class is a kind of communication interface which is supposed to make
 * communication between BComponents possible. Each BComponent should be able to
 * connect to components which it needs to pass events and needs a communication
 * system to use in order to achieve the goal. BComponents are able to share
 * their BCommunicationInterfaces to each other so other BComponents will be
 * able to contact them.
 * 
 * @author Salim
 * 
 */
public abstract class BCommunicationInterface {

	public BCommunicationInterface(Class<BAbstractComponent> component) {
		// configure according to the BComponent
		initialize(component);
	}

	/**
	 * This function is used in constructor in order to configure and initialize
	 * any necessary data and configuration which depends of the BComponent.
	 * 
	 * @param component
	 */
	protected abstract void initialize(Class<BAbstractComponent> component);

	/**
	 * This function is used to dispatch a event to the BComponent. By
	 * Dispatching message we mean sending a message to the destination
	 * BComponent. Destination BComponent is the BComponent which the interfacde
	 * belongs to.
	 * 
	 * @param event
	 * @return
	 */
	public abstract boolean dispatchEvent(BEvent event);

	/**
	 * This function is used to check if the BComponent accepts any Events at
	 * the moment.
	 * 
	 * @return
	 */
	public abstract boolean acceptsEvents();

	/**
	 * Returns the name of the destination component.
	 * 
	 * @return
	 */
	public abstract String getComponentName();

	/**
	 * Returns the Load of the destination BComponent. By BComponent load we
	 * mean the tasks it has to do before it can start processing the event that
	 * the origin component is going to send.
	 * 
	 * @return
	 */
	public abstract int getComponentLoad();
}
