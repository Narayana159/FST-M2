package github_project;

import static io.restassured.RestAssured.given;

import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;


public class RestAssured_Project {
	// Declare request specification
		RequestSpecification requestSpec;
		// Declare response specification
		ResponseSpecification responseSpec;
		// Global properties
		String sshKey;
		int sshKeyId;
		@BeforeClass
		public void setUp() {
			// Create request specification
			requestSpec = new RequestSpecBuilder()
					// Set content type
					.setContentType(ContentType.JSON)
					.addHeader("Authorization", "token XXX")
					// Set base URL
					.setBaseUri("https://api.github.com")
					// Build request specification
					.build();
			sshKey = "ssh-rsa XXX";
		}
		@Test(priority = 1)
		// Test case using a DataProvider
		public void addKeys() {
			String reqBody = "{\"title\": \"TestKey\", \"key\": \"" + sshKey + "\" }";
			Response response = given().spec(requestSpec) // Use requestSpec
					.body(reqBody) // Send request body
					.when().post("/user/keys"); // Send POST request
			String resBody = response.getBody().asPrettyString();
			System.out.println(resBody);
			sshKeyId = response.then().extract().path("id");
			Reporter.log("SSH Key ID: "+sshKeyId);
			// Assertions
			response.then().statusCode(201);
			Reporter.log("Status Code: 201");
		}
		@Test(priority = 2)
		// Test case using a DataProvider
		public void getKeys() {
			Response response = given().spec(requestSpec) // Use requestSpec
					.when().get("/user/keys"); // Send GET Request
			String resBody = response.getBody().asPrettyString();
			System.out.println(resBody);
			 Reporter.log("Response: "+resBody);
			// Assertions
			response.then().statusCode(200);
			Reporter.log("Status Code: 200");
		}
		@Test(priority = 3)
		// Test case using a DataProvider
		public void deleteKeys() {
			Response response = given().spec(requestSpec) // Use requestSpec
					.pathParam("keyId", sshKeyId).when().delete("/user/keys/{keyId}"); // Send GET Request
			String resBody = response.getBody().asPrettyString();
			System.out.println(resBody);
			 Reporter.log("Response: "+resBody);
			// Assertions
			response.then().statusCode(204);
			Reporter.log("Status Code: 204");
		}	
	

}
