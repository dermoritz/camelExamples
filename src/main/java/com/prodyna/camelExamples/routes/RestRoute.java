package com.prodyna.camelExamples.routes;

import javax.inject.Inject;

import org.apache.camel.Endpoint;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

import com.prodyna.camelExamples.qualifier.RestEndpoint;
import com.prodyna.camelExamples.qualifier.RestProcessor;

public class RestRoute extends RouteBuilder {

    private Endpoint rest;
    private Processor answer;

    @Inject
    private RestRoute(@RestEndpoint Endpoint rest, @RestProcessor Processor answer){
        this.rest = rest;
        this.answer = answer;

    }

    @Override
    public void configure() throws Exception {
        restConfiguration().component("restlet").apiContextPath("/").port(8080);
        from(rest).process(answer);
    }

}
