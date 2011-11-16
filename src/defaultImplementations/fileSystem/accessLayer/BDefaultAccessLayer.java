package defaultImplementations.fileSystem.accessLayer;

import baseBaygan.ComponentType;
import baseBaygan.eventPassing.events.BEvent;
import baseBaygan.fileSystem.BAccessLayer;

public class BDefaultAccessLayer extends BAccessLayer {

	public BDefaultAccessLayer(Object[] args) {
		super(args);
	}

	@Override
	protected void beforeDeath() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void afterAnyEvent(BEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void processEvent(BEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void beforeAnyEvent() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initLife() {
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean alive() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ComponentType type() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected int queriesAtHand() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected void initArgs(Object[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean acceptsEvents() {
		return true; // TODO Should be revised
	}

}
