package application;

import static org.junit.Assert.assertTrue;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("health")
public class HealthEndpoint {

	private String url="";
	
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response healthcheck() {
    	System.out.println("Testing endpoint " + url);
        int maxCount = 30;
        int responseCode = makeRequest();
        for(int i = 0; (responseCode != 200) && (i < maxCount); i++) {
          System.out.println("Response code : " + responseCode + ", retrying ... (" + i + " of " + maxCount + ")");
          try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
          responseCode = makeRequest();
        }
        assertTrue("Incorrect response code: " + responseCode, responseCode == 200);
      return Response.ok("{\"status\":\"UP\"}").build();
    }
    
    private int makeRequest() {
        Client client = ClientBuilder.newClient();
        Invocation.Builder invoBuild = client.target(url).request();
        Response response = invoBuild.get();
        int responseCode = response.getStatus();
        response.close();
        return responseCode;
      }

}
