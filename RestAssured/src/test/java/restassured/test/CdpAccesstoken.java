package restassured.test;

import static io.restassured.RestAssured.given;
import org.testng.annotations.Test;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import restassured.constant.ApplicationConstant;

public class CdpAccesstoken {

	public String certificatePath="C:\\Users\\nandini.dhangar\\eclipse-workspace\\RestAssured\\certificate\\cdp-certificate_2.cer";
	@Test
	public void get_Cdp_Access_Token() {
	String accessToken = null;
	String response = given().log().all().contentType("multipart/form-data")
			.multiPart("client_id", "7dfdcf5f-4d96-40fa-a0e8-0b3bb7cf7baf")
			.multiPart("client_secret", "ym-V9FiFwq./wbuKe9nFDG77i?P@Zcb4")
			.multiPart("grant_type", "client_credentials")
			.multiPart("scope", "https://6c09f207-a37e-4a2c-bf5f-fe2682cd5fab/customer-data-master-stage/.default")
			.when().post("https://login.microsoftonline.com/5a9d9cfd-c32e-4ac1-a9ed-fe83df4f9e4d/oauth2/v2.0/token")
			.then().log().all().assertThat().statusCode(200).extract().asString();
	System.out.println(" response " + response);
	
	
	JsonPath js= new JsonPath(response);
	String getaccess_token=js.getString("access_token");
	System.out.println(getaccess_token);
	accessToken= "Bearer "+getaccess_token;
	System.out.println("accessToken ="+accessToken);
	
	//ResponseBody<?> cdpResponseBody = given()
		String response2=	given().trustStore(ApplicationConstant.CERTIFICATE_FILE_LOCATION, ApplicationConstant.trustStorePassword)
			.header("Authorization", accessToken).contentType(ContentType.JSON)
			.when().get("https://cdp.stage.ashleyretail.com/sfsc/ivr/odata/CTI?$filter=CustomerPhoneNumber eq '5684068310'")
			.then().log().all().assertThat().statusCode(200).extract().response().asString();
			

//	String response2 = given().header("Authorization", accessToken).contentType(ContentType.JSON)
//			.when().get("https://cdp.stage.ashleyretail.com/sfsc/ivr/odata/CTI?$filter=CustomerPhoneNumber eq '5684068310'")
//			.then().log().all().assertThat().statusCode(200).extract().response().asString();
//	System.out.println("response " + cdpResponseBody);
	
	}
}
