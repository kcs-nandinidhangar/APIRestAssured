package restassured.test;

import static io.restassured.RestAssured.given;
import java.io.File;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import io.restassured.http.ContentType;
import restassured.authentication.AuthenticationTest;
import restassured.constant.ApplicationConstant;
import restassured.util.ExcelReaderUtility;

public class TestGetCTI {

	AuthenticationTest authenticationTest;
	String sheetName = "CDP_Testcases";

	/**
	 * the CARA URL Request sent
	 */
	@DataProvider
	public Object[][] getCRMTestData() throws Exception {
		Object data[][] = ExcelReaderUtility.getTestData1(ApplicationConstant.TESTCASE_FILE_NAME, sheetName);
		return data;
	}

	 @Test(dataProvider="getCRMTestData")
	 public void get_CARA_Response(String TestInputData) {
		 
			authenticationTest = new AuthenticationTest();
			
			ApplicationConstant.trustStoreFile = new File(ApplicationConstant.CERTIFICATE_FILE_LOCATION);
			
			if (ApplicationConstant.trustStoreFile.exists()) {
				System.out.println("Truststore file found.....using password as Test@123 to access it...");
			}

			String CARA_AUTH_TOKEN =authenticationTest.performCaraAuthentication();
			//System.out.println("3 :" + CARA_AUTH_TOKEN);
			String response=	given().trustStore(ApplicationConstant.CERTIFICATE_FILE_LOCATION, ApplicationConstant.trustStorePassword)
					.header("Authorization",CARA_AUTH_TOKEN).contentType(ContentType.JSON)
					.when().get("https://test.cara.ashleyretail.com/odata/CTI?"+TestInputData)
					.then().log().all().assertThat().statusCode(200).extract().response().asString();
					
					//System.out.println(response);
					
		}
	/**
	 * the CDP URL Request sent
	 */
	@Test(dataProvider="getCRMTestData")
	public void get_CDP_Response(String TestInputData) {
		authenticationTest = new AuthenticationTest();
		String CDP_AUTH_TOKEN = authenticationTest.performCDPAuthentication();
		//System.out.println("3 :" + CDP_AUTH_TOKEN);

		String response2 = given().log().all()
				.trustStore(ApplicationConstant.CERTIFICATE_FILE_LOCATION, ApplicationConstant.trustStorePassword)
				.header("Authorization", CDP_AUTH_TOKEN).contentType(ContentType.JSON).when()
				.get("https://cdp.stage.ashleyretail.com/sfsc/ivr/odata/CTI?"+TestInputData)
				.then().log().all().assertThat().statusCode(200).extract().response().asString();

		//System.out.println("response " + response2);
	}
}
