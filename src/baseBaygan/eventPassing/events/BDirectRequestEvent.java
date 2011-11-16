package baseBaygan.eventPassing.events;

import baseBaygan.BAbstractComponent;
import baseBaygan.objects.DirectTableRequest;

public class BDirectRequestEvent extends BEvent {
	private final DirectTableRequest request;

	public BDirectRequestEvent(String name, BAbstractComponent source,
			DirectTableRequest request) {
		super(name, source);
		this.request = request;
	}

	public DirectTableRequest getDirectRequest() {
		return request;
	}

}
