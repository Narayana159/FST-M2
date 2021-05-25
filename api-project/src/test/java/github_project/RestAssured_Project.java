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
					.addHeader("Authorization", "token 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")
					// Set base URL
					.setBaseUri("https://api.github.com")
					// Build request specification
					.build();
			sshKey = "ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABgQCr8q+koqQ/vh8ENReaQFbQcN+qHiNU1Fc7WlM/VF5lP3wFWijzwZqeqVx4cZCMO0QRT/DqS1Iu4W1V+HE2u5xthUSAxkGEOB76AbsOlE1LhPVUWl0pdXZ+DPWCcrw4iXc5pdbuOKFBsQ6TVTr1wp8qkPWk5EYmzxUTOrdgQYvDQtGULgBL8gdLUt0vuEH+j+3PGrqA8tOVDgpQVgA5UM9ZvCtBRoadsXC11Hj9ZRpXrafBQSc8q10QMJYcqDfVCiK53Kcu5y7zjxOa09WUAyLeyk8/UmY3/UlKmYYqC510xNfq8+RoJZOrRtwTOCJPjyeLiZrddb26aAeCYOyf7ss94G/u5HJ3BeHcrWph2aUY6y59Bjgihp5W32WItuR6xNK6KBwTNfJ5TIWh5TzddYk+Yb2vmo1nxNfddQ2Ikhb2uixCQpNzxVBz0kf57n8miy5jsWqMuVIX0bjy1j8jbSF/ibpl4Jb+ZIPVEJvGQbD093zV/i4IJIPOfxzEBVSUZuc= gmx";
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
