	package baseBaygan.highLevelComponents;

import baseBaygan.BAbstractComponent;
import baseBaygan.eventPassing.eventListeners.BParsedQueryEventListener;
import baseBaygan.parseTree.BParseTree;

public abstract class BOptimizationLayer extends BAbstractComponent implements
		BParsedQueryEventListener {

	public BOptimizationLayer(Object[] args) {
		super(args);
	}

	public abstract BParseTree optimize(BParseTree parseTree);
}
