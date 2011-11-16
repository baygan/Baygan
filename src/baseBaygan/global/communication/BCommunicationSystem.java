package baseBaygan.global.communication;

import java.util.ArrayList;

import tools.ClassInstantiators;

import baseBaygan.BAbstractComponent;
import baseBaygan.eventPassing.events.BEvent;

/**
 * BCommunicationSystem is what makes communication between different
 * BComponents possible. Each BComponent has an Communication system instance
 * which is responsible for:<br>
 * <ul>
 * <li>Type of communication system. which can be a local or distributed system.
 * events can be send through network interfaces or by accessing the BComponents
 * directly.</li>
 * <li>Contains other BComponents' communication interfaces</li>
 * <li>Sending and receiving Events: It only sends events and if it receives any
 * new event, simply passes it to BComponet's event Queue</li>
 * </ul>
 * 
 * @author Salim
 * 
 */
public abstract class BCommunicationSystem {
	/**
	 * Contains other components' interfaces which this component is able to
	 * communicate with.
	 */
	private ArrayList<BCommunicationInterface> interfaces;
	/**
	 * Pointer to the owner component.
	 */
	private final BAbstractComponent component;

	public BCommunicationSystem(BAbstractComponent component) {
		this.component = component;
	}

	/**
	 * Send event to the BComponent with input interface
	 * 
	 * @param recipient
	 * @param event
	 * @return
	 */
	public boolean dispatchEvent(BCommunicationInterface recipient, BEvent event) {
		// check if the recipient accepts events
		if (!recipient.acceptsEvents())
			return false;
		// try sending event
		return recipient.dispatchEvent(event);
	}
	public boolean dispatchEventByClass(Class<? extends BAbstractComponent> recipient, BEvent event) {
		for (BCommunicationInterface bci : interfaces) {
			if(bci.getClass().equals(recipient))
				return dispatchEvent(bci, event);
		}
		return false;
	}

	/**
	 * Send event to BComponent with the input name.
	 * 
	 * @param recipient
	 * @param event
	 * @return
	 */
	public boolean dispatchEvent(String recipient, BEvent event) {
		return dispatchEvent(getInterface(recipient), event);
	}

	/**
	 * Finds interface of the BComponent with the input name if there is any in
	 * the system.
	 * 
	 * @param name
	 * @return
	 */
	public BCommunicationInterface getInterface(String name) {
		for (BCommunicationInterface bci : interfaces) {
			if (bci.getComponentName().equalsIgnoreCase(name)) {
				return bci;
			}
		}
		return null;
	}

	/**
	 * Create an interface to connect to the input BComponent.
	 * 
	 * @param component
	 */
	public void createInterface(Class<BAbstractComponent> component) {
		interfaces.add((BCommunicationInterface) ClassInstantiators
				.instantiateClass(component, new Object[] { component }));
	}

	public void addEventToComponent(BEvent event) {
		component.addEvent(event);
	}
	
}
