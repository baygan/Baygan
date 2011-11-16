package baseBaygan.eventPassing.events;

import baseBaygan.BAbstractComponent;
import baseBaygan.objects.QueryResult;

public class BRequestResultEvent extends BEvent {// TODO have not decided
														// how to keep the
														// result

	private final QueryResult result;

	public BRequestResultEvent(String name, BAbstractComponent source,
			QueryResult result) {
		super(name, source);
		this.result = result;
	}

	public BRequestResultEvent(String name, BAbstractComponent source,
			int id, QueryResult result) {
		super(name, source, id, -1);
		this.result = result;
	}

	public BRequestResultEvent(String name, int requestId,
			BAbstractComponent source, QueryResult result) {
		super(name, requestId, source);
		this.result = result;
	}

	public QueryResult getQueryResult() {
		return result;
	}

}
