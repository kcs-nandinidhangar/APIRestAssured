package restassured.constant;

import java.io.File;

public class ApplicationConstant {
	
	private ApplicationConstant() {
		// DUMMY
	}
	

	/** The Constant TESTCASE_FILE_NAME. */
	public static final String TESTCASE_FILE_NAME = "./input/ABC.xlsx";


	/** The Constant CERTIFICATE_FILE_LOCATION. */
	public static final String CERTIFICATE_FILE_LOCATION = "./certificate/StageTruststore.jks";

	/** The trust store file. */
	public static File trustStoreFile = null;

	/** The Constant trustStorePassword. */
	public static final String trustStorePassword = "Test@123";
}


