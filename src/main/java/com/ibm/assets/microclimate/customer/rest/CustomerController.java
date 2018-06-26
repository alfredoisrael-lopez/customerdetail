package com.ibm.assets.microclimate.customer.rest;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.assets.microclimate.customer.dao.CustomerDao;
import com.ibm.assets.microclimate.customer.dao.CustomerDaoImpl;
import com.ibm.assets.microclimate.customer.domain.Customer;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApplicationPath("/api")
@Path("/")
public class CustomerController extends Application {

	private final static Log log = LogFactory.getLog(CustomerController.class);

	private CustomerDao dao = new CustomerDaoImpl();

	@GET
	@Path("/{clientNumber}")
	@ApiOperation(value = "REST endpoint to retrieve customer detail",
	notes = "GET operation to retrieve customer detail")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Customer found it"),
			@ApiResponse(code = 404, message = "No customer detail")} )
	public Response retrieveCustomer(@PathParam("clientNumber") String clientNumber) {
		if (log.isInfoEnabled()) {
			log.info("Getting Access CustomerController.retrieveCustomer --> " + clientNumber);
		}
		
		Customer customer = dao.getCustomer(clientNumber);
		
		return (!customer.getClientNumber().isEmpty()) ? Response.ok(customer).build() : Response.status(404).build();
	}
}
