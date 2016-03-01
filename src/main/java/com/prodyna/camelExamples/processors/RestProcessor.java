package com.prodyna.camelExamples.processors;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.inject.Qualifier;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.prodyna.camelExamples.endpoints.RestEndpointProvider;
import com.prodyna.camelExamples.processors.RestProcessor.RestProc;

@RestProc
public class RestProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        String parameter = exchange.getIn().getHeader(RestEndpointProvider.PARAMETER_HEADER, String.class);
        String answer = "42";
        if(parameter.equalsIgnoreCase("ping")){
            answer = "pong";
        } else if (parameter.equalsIgnoreCase("pong")) {
            answer = "ping";
        }
        exchange.getIn().setBody(answer);
    }

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE})
    public @interface RestProc {

    }

}
