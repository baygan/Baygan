package baseBaygan.objects;

public interface QueryResult {
	/**
	 * This function returns only the main content as text. if the content is a
	 * table it retains the table as text and if the content is an error, it
	 * returns the error as text.
	 * 
	 * @return
	 */
	public String getContentText();

	/**
	 * It specifies if the result class contains result of the input query.
	 * 
	 * @return
	 */
	public boolean isAnswer();

	/**
	 * It is true if it contains an error occurred in any of components.
	 * 
	 * @return
	 */
	public boolean isError();

	/**
	 * It returns the time in milliseconds which it took to get into the result
	 * or error.
	 * 
	 * @return
	 */
	public long time();

	/**
	 * Set start time of the query. right after the Transaction manager is
	 * created.
	 * 
	 * @param time
	 */
	public void setStartTime(long time);

	/**
	 * Set time which the query execution is finished at.
	 */
	public void setEndTime();

	/**
	 * Sets the Date and time which query has been entered by user.
	 * 
	 * @param date
	 */
	public void setStartDate(String date);

	/**
	 * String returned by this function shows the date and time in which query
	 * has been entered by user.
	 * 
	 * @return
	 */
	public String getStartDate();

	/**
	 * Text form contains all information above.
	 * 
	 * @return
	 */
	public String getTextForm();
}
