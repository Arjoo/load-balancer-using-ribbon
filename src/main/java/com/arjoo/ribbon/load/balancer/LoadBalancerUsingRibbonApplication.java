package com.arjoo.ribbon.load.balancer;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@RestController
@RibbonClient(
  name = "ping-a-server",
  configuration = RibbonConfiguration.class)
public class LoadBalancerUsingRibbonApplication {

	@Autowired
	private LoadBalancerClient client;
	

    @RequestMapping("/server-location")
    public String serverLocation() {
    	ServiceInstance instance = client.choose("MyApplication");
    	URI storesUri = URI.create(String.format("http://%s:%s", instance.getHost(), instance.getPort()));
    	return storesUri.toString();
    }

	
	public static void main(String[] args) {
		SpringApplication.run(LoadBalancerUsingRibbonApplication.class, args);
	}

}
