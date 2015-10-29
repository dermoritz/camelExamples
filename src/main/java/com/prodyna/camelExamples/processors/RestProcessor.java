package com.prodyna.camelExamples.processors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.prodyna.camelExamples.endpoints.EndpointProvider;

@com.prodyna.camelExamples.qualifier.RestProcessor
public class RestProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        String parameter = exchange.getIn().getHeader(EndpointProvider.PARAMETER_HEADER, String.class);
        String answer = "?";
        if(parameter.equalsIgnoreCase("ping")){
            answer = "pong";
        } else if (parameter.equalsIgnoreCase("pong")) {
            answer = "ping";
        }
        exchange.getIn().setBody(answer);
    }

}
