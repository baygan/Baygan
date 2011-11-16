package baseBaygan.eventPassing.events;

import baseBaygan.BAbstractComponent;
import baseBaygan.objects.Query;

public class BQueryEvent extends BEvent {

	private final Query query;

	public BQueryEvent(String name, BAbstractComponent source, Query query) {
		super(name, source);
		this.query = query;
	}

	public Query getQuery() {
		return query;
	}

}
