package baseBaygan.eventPassing.events;

import baseBaygan.BAbstractComponent;

/**
 * Every class implementing this interface is pasable trough components
 * 
 * @author Salim
 * 
 */
public interface BPassable {

	public String getName();

	public BAbstractComponent getSource();

	public int getId();
}
