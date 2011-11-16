package baseBaygan.highLevelComponents;

import baseBaygan.BAbstractComponent;
import baseBaygan.eventPassing.eventListeners.BQueryEventListener;
import baseBaygan.objects.Query;
import baseBaygan.parseTree.BParseTree;

public abstract class BParser extends BAbstractComponent implements
		BQueryEventListener {

	public BParser(Object[] args) {
		super(args);
	}

	public abstract BParseTree parse(Query query);

}
