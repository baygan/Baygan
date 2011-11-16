package baseBaygan.eventPassing.eventListeners;

import baseBaygan.eventPassing.events.BQueryEvent;

public interface BQueryEventListener extends BEventListener {
	public void handleQuery(BQueryEvent e);
}
