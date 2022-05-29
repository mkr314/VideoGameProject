 package testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.response.Response;

import static org.hamcrest.Matchers.*;

import java.util.HashMap;

import static io.restassured.RestAssured.*;

public class VideoGameAPITests {
	
	
	@Test(priority=1)
	public void test_getAllVideoGames()
	{
		given().
		when().
		get("http://localhost:8080/app/videogames")
		.then()
		.statusCode(200);
	
		
	}
	@Test(priority=2)
	public void test_addNewVideoGame()
	{
		HashMap data = new HashMap();
		data.put("id", "13");
		data.put("name", "pubg");
		data.put("releaseDate","2022-05-27T13:00:07.437Z");
		data.put("reviewScore", "9");
		data.put("category", "enjoy");
		data.put("rating", "allover");
		
	
		Response res=
		given()
		.contentType("application/json").
		body(data).
		when().
		post("http://localhost:8080/app/videogames").
		then().
		statusCode(200)
		.log().body()
		.extract().response();
	String jsonString =	res.asString();//conert json into string
			
		   Assert.assertEquals(jsonString.contains("Record Added Successfully"), true);
	}
	
	@Test(priority=3)
	public void test_getVideoGame()
	{
		given().
		when().
		get("http://localhost:8080/app/videogames/13").
		then().
		statusCode(200)
		.log().body()
		.body("videoGame.id", equalTo("13"))
		.body("videoGame.name", equalTo("pubg"));
	}

	@Test(priority=4)
	public void test_updateVideoGame()
	{
		HashMap data = new HashMap();
		data.put("id", "13");
		data.put("name", "candycrush");
		data.put("releaseDate","2022-05-27T13:00:07.437Z");
		data.put("reviewScore", "7");
		data.put("category", "enjoy");
		data.put("rating", "good");
		
		given().
		contentType("application/json").
		body(data).
		when().
		put("http://localhost:8080/app/videogames/13").
		then().statusCode(200)
		.log().body()
		.body("videoGame.id", equalTo("13"))
		.body("videoGame.name", equalTo("candycrush"));
	//	.body("reviewScore", equalTo("7"))
	//	.body("rating",equalTo("good"));
		
		
	}
	@Test(priority=5)
	public void test_deleteVideoGame() throws InterruptedException
	{
		Response res=
		given()
		.when()
		.delete("http://localhost:8080/app/videogames/13")
		.then()
		.statusCode(200)
		.log().body()
		.extract().response();
		
		Thread.sleep(3000);
		String jsonString=res.asString();
		Assert.assertEquals(jsonString.contains("Record Deleted Successfully"),true);
		
	}
}
