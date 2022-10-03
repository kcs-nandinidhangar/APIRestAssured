package restassured.constant;

/**
 * The Enum TestCaseCategory.
 */
public enum TestCaseCategory {

	/** The get customer. */
	GET_CUSTOMER("GET_CUSTOMER"),
	
	/** The product */
	SEARCH_ORDER_BY_PHONE_AND_ORDER_NO("SEARCH_ORDER_BY_PHONE_AND_ORDER_NO"),

	GLOBAL_SALES_ORDER_HEADER_SEARCH("GLOBAL_SALES_ORDER_HEADER_SEARCH");

	/** The test case category. */
	private final String testCaseCategory;
	
	/**
	 * Instantiates a new test case category.
	 * 
	 * @param testCaseCategory the test case category
	 */
	private TestCaseCategory(String testCaseCategory) {
		this.testCaseCategory=testCaseCategory;
	}
	
	/**
	 * Gets the test case category.
	 * 
	 * @return the test case category
	 */
	public String getTestCaseCategory() {
		return testCaseCategory;
	}
	
	/**
	 * To string
	 * 
	 * @return the string
	 */	
	@Override
	public String toString() {
		return this.getTestCaseCategory();
	}
}
