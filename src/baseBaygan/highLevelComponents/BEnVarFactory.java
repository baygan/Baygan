package baseBaygan.highLevelComponents;

import baseBaygan.BAutoInstantiatable;

public abstract class BEnVarFactory extends BAutoInstantiatable {

	public BEnVarFactory(Object[] args) {
		super(args);
	}

	/**
	 * Creates new instance of enviroment variables.
	 * 
	 * @return
	 */
	public abstract BEnviromentVariables createEnviromentVariables();

}
