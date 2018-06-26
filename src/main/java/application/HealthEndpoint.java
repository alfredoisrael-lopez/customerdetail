package application;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ibm.assets.microclimate.customer.dao.CustomerDao;
import com.ibm.assets.microclimate.customer.dao.CustomerDaoImpl;
import com.ibm.assets.microclimate.customer.domain.Customer;

@Path("health")
public class HealthEndpoint {

	private static String url = "http://localhost:9080/customer/api/1000000001";
	
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response healthcheck() {
    	  CustomerDao dao = new CustomerDaoImpl();
      Customer customer = dao.getCustomer("1000000001");  
      return (!customer.getClientNumber().isEmpty()) ? Response.ok(customer).build() : Response.status(404).build();
    }

}
