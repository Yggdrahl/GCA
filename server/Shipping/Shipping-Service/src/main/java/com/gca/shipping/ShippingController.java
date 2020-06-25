package com.gca.shipping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShippingController {
	
	@Autowired
	private ShippingService shippingService;
	
	private Logger LOG = LoggerFactory.getLogger(ShippingController.class);
	
	@RequestMapping("/getShippingCosts")
	@CrossOrigin(origins = "http://localhost:4200")
	public @ResponseBody double getShippingCosts(@RequestParam("costs") double costs) {
		LOG.info("http.GET on '/getShippingCosts'");
		return shippingService.getShippingCosts(costs);
	}
	
	@RequestMapping("/getTracking")
	@CrossOrigin(origins = "http://localhost:4200")
	public String getShippingCosts() {
		LOG.info("http.GET on '/getTracking'");
		return shippingService.generateTracking();
	}

}
