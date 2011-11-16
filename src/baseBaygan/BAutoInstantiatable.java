package baseBaygan;

public abstract class BAutoInstantiatable {

	public BAutoInstantiatable(Object[] args) {
		initArgs(args);
	}

	protected abstract void initArgs(Object[] args);
}
