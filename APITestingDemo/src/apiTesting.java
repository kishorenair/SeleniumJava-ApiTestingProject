import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class apiTesting {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {
			// Performing API call with RestAssured
		RestAssured.baseURI = "https://api.tmsandbox.co.nz/v1/Categories/6327/Details.json?catalogue=false";
		Response response = given().param("catalogue", "false").when().get("/v1/Categories/6327/Details.json");
		
		// Printing the formatted response
		JSONObject jsonFormattedResponse = new JSONObject(response.getBody().asString());
        System.out.println("Formatted Response: " + jsonFormattedResponse.toString(2)); // Adding indent with 2 spaces
        
        
        //Verifying acceptance criteria for Name field
        try {
        	String name = jsonFormattedResponse.getString("Name");
            Assert.assertEquals(name, "Carbon credits", "Failure. Name field mismatch!");
            System.out.println("Name field verified successfully.");
        } 
        catch (AssertionError e) {
            System.err.println(e.getMessage());
        }
        
        //Verifying acceptance criteria for CanRelist field
        try {
        	Boolean canRelist = jsonFormattedResponse.getBoolean("CanRelist");
            Assert.assertTrue(canRelist);
            System.out.println("CanRelist verified successfully");
        }
        catch (AssertionError e) {
            System.err.println(e.getMessage());
        }
        
     // Verifying the Promotions element
        try {
            JSONArray promotions = jsonFormattedResponse.getJSONArray("Promotions");
            boolean search = false;
            for (int i = 0; i < promotions.length(); i++) {
                JSONObject promotion = promotions.getJSONObject(i);
                if (promotion.getString("Name").equals("Gallery")) 
                {
                    String description = promotion.getString("Description");
                    if (description.contains("Good position in category")) 
                    {
                        search = true;
                        break;
                    }
                }
            }
            Assert.assertTrue(search, "There was no Promotion element with Name=Gallery and with description Good position in category.");
            System.out.println("Promotion element with Name 'Gallery' and appropriate Description is verified successfully.");
        } catch (AssertionError e) {
            System.err.println(e.getMessage());
        }
        
		}
		
		catch (Exception e) {
            e.printStackTrace();
        } 

	}

}
