package restassured.authentication;

import static io.restassured.RestAssured.given;

import io.restassured.path.json.JsonPath;

public class AuthenticationTest {

	public static String accessToken;

	/**
	 * Get CARA accessToken
	 * @return
	 */
	public String performCaraAuthentication() {
		String accessToken = null;
		String response = given().contentType("multipart/form-data").multiPart("grant_type", "password")
				.multiPart("resource", "https://5a9d9cfd-c32e-4ac1-a9ed-fe83df4f9e4d/cara-api-qa")
				.multiPart("client_id", "aa48fe11-4e42-4d0e-bd43-5a6b6dba8de9")
				.multiPart("client_secret", "xQjBhIypRtVvPhxKvJpos+jAdUXGkWOQrHZS+pk47s4=")
				.multiPart("username", "s_CRMQACara@ashleyfurniture.com").multiPart("password", "F85!aPhv85#fXyTd")
				.when().post("https://login.microsoftonline.com/5a9d9cfd-c32e-4ac1-a9ed-fe83df4f9e4d/oauth2/token")
				.then().assertThat().statusCode(200).extract().asString();

		JsonPath js = new JsonPath(response);
		String getaccess_token = js.getString("access_token");
		accessToken = "Bearer " + getaccess_token;
	//	System.out.println("accessToken =" + accessToken);
		
		return accessToken;
	}

	/**
	 * Get CDP accessToken
	 * @return
	 */
	public String performCDPAuthentication() {
		String accessToken = null;
		String response = given().contentType("multipart/form-data")
				.multiPart("client_id", "7dfdcf5f-4d96-40fa-a0e8-0b3bb7cf7baf")
				.multiPart("client_secret", "ym-V9FiFwq./wbuKe9nFDG77i?P@Zcb4")
				.multiPart("grant_type", "client_credentials")
				.multiPart("scope", "https://6c09f207-a37e-4a2c-bf5f-fe2682cd5fab/customer-data-master-stage/.default")
				.when().post("https://login.microsoftonline.com/5a9d9cfd-c32e-4ac1-a9ed-fe83df4f9e4d/oauth2/v2.0/token")
				.then().assertThat().statusCode(200).extract().asString();
		
		JsonPath js = new JsonPath(response);
		String getaccess_token = js.getString("access_token");
		accessToken = "Bearer " + getaccess_token;
	//	System.out.println("accessToken =" + accessToken);

		return accessToken;
	}
}
