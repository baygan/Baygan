package baseBaygan.highLevelComponents;

import baseBaygan.BAbstractComponent;
import baseBaygan.eventPassing.eventListeners.BParsedQueryEventListener;
import baseBaygan.objects.QueryResult;
import baseBaygan.parseTree.BParseTree;

public abstract class BExcutator extends BAbstractComponent implements
		BParsedQueryEventListener {
	public BExcutator(Object[] args) {
		super(args);
	}

	public abstract QueryResult execute(BParseTree parseTree);

}
