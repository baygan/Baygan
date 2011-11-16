package baseBaygan.eventPassing.eventListeners;

import baseBaygan.eventPassing.events.BParsedQueryEvent;

public interface BParsedQueryEventListener extends BEventListener {
	public void handleParsedQuery(BParsedQueryEvent e);
}
