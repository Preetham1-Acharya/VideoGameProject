package testCases;

import org.junit.Assert;
import org.testng.annotations.Test;

import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.*;

import java.util.HashMap;

public class TC_VideoGameAPI {

	@Test(priority=1)
	public void test_getAllVideoGames() {
		given()
		.when()
		.get("http://localhost:8080/app/videogames")
		.then()
		.statusCode(200);
	}

	@Test(priority=2)
	public void test_addNewVideoGame() {

		HashMap data = new HashMap();
		data.put("id"," 104");
		data.put("name", "string");
		data.put("releaseDate", "2021-09-16T10:17:46.245Z");
		data.put("reviewScore", "0");
		data.put("category", "string");
		data.put("rating", "string");

		Response res=
				given()
				.contentType("Application/json")
				.body(data)
				.when()
				.post("http://localhost:8080/app/videogames")
				.then()
				.statusCode(200)
				.log().body()
				.extract().response();

		String json = res.asString();
		Assert.assertEquals(json.contains("Record Added Successfully"),true);

	}

	@Test(priority=3)
	public void test_GetOneVideoGame() {
		given()
		.when()
		.get("http://localhost:8080/app/videogames/104")
		.then()
		.statusCode(200)
		.log().body()
		.body("videoGame.id", equalTo("104"))
		.body("videoGame.name", equalTo("string"));
	}

	@Test(priority=4)
	public void test_UpdateVideoGame() {

		HashMap data1 = new HashMap();
		data1.put("id"," 104");
		data1.put("name", "Preetham");
		data1.put("releaseDate", "2021-09-16T10:17:46.245Z");
		data1.put("reviewScore", "0");
		data1.put("category", "SuperMan");
		data1.put("rating", "Star");

		given()
		.contentType("application/json")
		.body(data1)
		.when()
		.put("http://localhost:8080/app/videogames/104")
		.then()
		.statusCode(200)
		.log().body()
		.body("videoGame.id", equalTo("104"))
		.body("videoGame.name", equalTo("Preetham"));				
	}

	@Test(priority=5)
	public void test_DeletVideoGame() throws InterruptedException {

		Response res1=
				given()
				.when()
				.delete("http://localhost:8080/app/videogames/104")
				.then()
				.statusCode(200)
				.log().body()
				.extract().response();

		Thread.sleep(3000);
		
		String json = res1.asString();
		Assert.assertEquals(json.contains("Record Deleted Successfully"),true);
	}
}
