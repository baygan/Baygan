package baseBaygan.objects.database;

import java.io.Serializable;

public class BTableSchema {

	private static final long serialVersionUID = 6284316155442017926L;

	private String tableName;
	private final int initialSize;
	private final Attribute[] attributeList;
	private int[] primaryKey;

	public TableSchema(String tableName, int initialSize,
			Attribute[] attributeList, int[] primaryKey) {
		this.tableName = tableName;
		this.initialSize = initialSize;
		this.attributeList = attributeList;
		this.primaryKey = primaryKey;

	}

	public TableSchema(String tableName, int initialSize,
			Attribute[] attributeList, String[] primaryKey) {
		this.tableName = tableName;
		this.initialSize = initialSize;
		this.attributeList = attributeList;

		this.primaryKey = new int[primaryKey.length];
		int index = 0;
		for (int i = 0; i < primaryKey.length; i++) {
			String key = primaryKey[i];
			for (int j = 0; j < attributeList.length; j++) {
				if (attributeList[j].getName().equals(key))
					this.primaryKey[index] = j;
			}
			index++;
			// TODO: handle errors
		}
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		// TODO -> tableName should be FINAL
		this.tableName = tableName;
	}

	public int getInitialSize() {
		return initialSize;
	}

	public Attribute[] getAttributeList() {
		return attributeList;
	}

	public Attribute[] getAttributes(int[] indeces) {
		Attribute[] result = new Attribute[indeces.length];
		for (int i = 0; i < indeces.length; i++)
			result[i] = attributeList[indeces[i]];

		return result;
	}

	public int[] getPrimaryKey() {
		return primaryKey;
	}

	public String toString() {
		String out = "";
		out += "*\n* TABLE NAME: " + tableName + "\n*\n* ATTRIBUTES:";
		for (int i = 0; i < attributeList.length; i++)
			out += "\n*   |___ " + attributeList[i];
		out += "\n*\n* PRIMARY KEYS: ";
		for (int i = 0; i < primaryKey.length; i++)
			out += "\n*   |___ " + primaryKey[i] + " ("
					+ attributeList[primaryKey[i]].getName() + ")";
		return out;
	}

	public static boolean doSchemasMatch(TableSchema schema1,
			TableSchema schema2) {
		boolean flag = true;

		// -------------------------------------
		// ------------- ATTRIBUTES ------------

		Attribute[] attributeList1 = schema1.attributeList;
		Attribute[] attributeList2 = schema2.attributeList;

		int attLength1 = attributeList1.length;
		int attLength2 = attributeList2.length;

		if (attLength1 != attLength2)
			return false;
		for (int i = 0; i < attLength1; i++) {
			flag = attributeList1[i].equals(attributeList2[i]);
			if (!flag)
				return false;
		}

		// -------------------------------------
		// ------------ PRIMARY KEYS -----------

		int[] primaryKey1 = schema1.primaryKey;
		int[] primaryKey2 = schema2.primaryKey;

		int keyLength1 = primaryKey1.length;
		int keyLength2 = primaryKey2.length;

		if (keyLength1 != keyLength2)
			return false;

		for (int i = 0; i < keyLength1; i++) {
			flag = (primaryKey1[i] == primaryKey2[i]);
			if (!flag)
				return false;
		}

		// -------------------------------------
		// ------------ CONSTRAINTS ------------
		// TODO
		//

		return flag;
	}

}
