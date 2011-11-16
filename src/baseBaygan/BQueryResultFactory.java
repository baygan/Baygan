package baseBaygan;

import baseBaygan.eventPassing.events.BRequestResultEvent;
import baseBaygan.objects.QueryResult;
import baseBaygan.parseTree.BParseTree;

public abstract class BQueryResultFactory extends BAutoInstantiatable {

	public BQueryResultFactory(Object[] args) {
		super(args);
	}

	public abstract QueryResult createQueryResult(BParseTree parseTree,
			long startTime, String startDate);

	public abstract QueryResult createQueryResult(BRequestResultEvent msg,
			long startTime, String startDate);
}
