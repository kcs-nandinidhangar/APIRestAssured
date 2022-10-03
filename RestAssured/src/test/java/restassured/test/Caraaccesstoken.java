package restassured.test;

import static io.restassured.RestAssured.given;
import org.testng.annotations.Test;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;

public class Caraaccesstoken {



		// Validate if add Place API is working as expected
		// given - all input details
		// when Submit the API - resource, http method
		// then - validate response
	@Test
		public void get_CARA_Access_Token() {
		String accessToken = null;
		
		String response = given().log().all().contentType("multipart/form-data")
				.multiPart("grant_type", "password")
				.multiPart("resource", "https://5a9d9cfd-c32e-4ac1-a9ed-fe83df4f9e4d/cara-api-qa")
				.multiPart("client_id", "aa48fe11-4e42-4d0e-bd43-5a6b6dba8de9")
				.multiPart("client_secret", "xQjBhIypRtVvPhxKvJpos+jAdUXGkWOQrHZS+pk47s4=")
				.multiPart("username", "s_CRMQACara@ashleyfurniture.com").multiPart("password", "F85!aPhv85#fXyTd")
				.when().post("https://login.microsoftonline.com/5a9d9cfd-c32e-4ac1-a9ed-fe83df4f9e4d/oauth2/token")
				.then().log().all().assertThat().statusCode(200).extract().asString();
	
		System.out.println(" response " + response);
		
		JsonPath js= new JsonPath(response);
		String getaccess_token=js.getString("access_token");
		System.out.println(getaccess_token);
		accessToken= "Bearer "+getaccess_token;
		System.out.println("accessToken ="+accessToken);
		
		String respone2=given().header("Authorization",accessToken).contentType(ContentType.JSON)
		.when().get("https://test.cara.ashleyretail.com/odata/CTI?$filter=CustomerPhoneNumber eq '5684068310'")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		System.out.println(respone2);
		
	}	
	
}