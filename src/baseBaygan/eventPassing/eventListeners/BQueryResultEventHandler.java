package baseBaygan.eventPassing.eventListeners;

import baseBaygan.eventPassing.events.BRequestResultEvent;

public interface BQueryResultEventHandler extends BEventListener{
	public void handleResultEvent(BRequestResultEvent e);
}
