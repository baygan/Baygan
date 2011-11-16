package baseBaygan.objects;

/**
 * This contains query entered by user.
 * 
 * @author Salim
 * 
 */
public class Query {
	private String queryString;

	public Query(String query) {
		this.queryString = query;

	}

	@Override
	public String toString() {
		return queryString;
	}
}
