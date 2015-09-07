package com.resanet.camel_su.routes;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.camel.Body;
import org.apache.camel.builder.RouteBuilder;

public class MulticastParallelProcessing extends RouteBuilder {

	@Override
	public void configure() throws Exception {

		ExecutorService threadPool = Executors.newFixedThreadPool(2);		
		MulticastParallelProcessing multicastParallelProcessing = new MulticastParallelProcessing();
		
		
		from("direct:mltcast").multicast().executorService(threadPool).parallelProcessing()
			 .to("direct:d1", "direct:d2",
				"direct:d3", "direct:d4", "direct:d5");

		from("direct:d1").bean(multicastParallelProcessing, "maMethode").delay(1000).log("message1");
		from("direct:d2").bean(multicastParallelProcessing, "maMethode").delay(1000).log("message2");
		from("direct:d3").bean(multicastParallelProcessing).delay(1000).log("message3").to("log:"+MulticastParallelProcessing.class.getName()+"?showAll=true&multiline=true");
		from("direct:d4").bean(multicastParallelProcessing).delay(1000).log("message4");
		from("direct:d5").bean(multicastParallelProcessing).delay(1000).log("message5");
	}
	
	public void maMethode(@Body String body){
		System.out.println(">>> " + Thread.currentThread().getName());
	}

}
