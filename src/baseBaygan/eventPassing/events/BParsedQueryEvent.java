package baseBaygan.eventPassing.events;

import baseBaygan.BAbstractComponent;
import baseBaygan.parseTree.BParseTree;

/**
 * Message which ParseTree returns to BTranactionMessage.
 * 
 * @author Salim
 * 
 */
public class BParsedQueryEvent extends BEvent {

	/**
	 * if true it means that Optimization layer has worked on it.
	 */
	private boolean optimized;
	private final BParseTree parseTee;

	public BParsedQueryEvent(String name, BAbstractComponent source,BParseTree parseTree) {
		super(name, source);
		parseTee = parseTree;
	}

	public BParseTree getParseTee() {
		return parseTee;
	}

	public boolean isOptimized() {
		return optimized;
	}

	public void setOptimized(boolean optimized) {
		this.optimized = optimized;
	}

}
