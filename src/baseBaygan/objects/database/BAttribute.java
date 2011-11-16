package baseBaygan.objects.database;

import java.io.Serializable;

public class BAttribute implements Serializable {
	private static final long serialVersionUID = -2962551430107263766L;

	private String attributeName;
	private final Type type;
	private final Constraint[] constraints;

	public Attribute(String attributeName, Type type, Constraint[] constraints) {
		this.attributeName = attributeName;
		this.type = type;
		if (constraints == null)
			constraints = new Constraint[0];
		this.constraints = constraints;
	}

	public String getName() {
		return attributeName;
	}

	public void setName(String attributeName) {
		this.attributeName = attributeName;
	}

	public Type getType() {
		return type;
	}

	public Constraint[] getConstraints() {
		return constraints;
	}

	public String toString() {
		String out = "";
		out += "Name: " + attributeName + " | Type: " + type.NAME()
				+ " | Constraints: " + constraints.length;
		return out;
	}

	public boolean equals(Attribute att2) {
		if (attributeName.equals(att2.attributeName))
			if (type.getClass() == att2.type.getClass())
				return true;
		return false;
	}
}
