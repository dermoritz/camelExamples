package com.prodyna.camelExamples.routes;

import javax.inject.Inject;

import org.apache.camel.Endpoint;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

import com.prodyna.camelExamples.endpoints.EndpointProvider.CountDb;
import com.prodyna.camelExamples.endpoints.EndpointProvider.RestCountEndpoint;
import com.prodyna.camelExamples.endpoints.EndpointProvider.RestEndpoint;
import com.prodyna.camelExamples.endpoints.EndpointProvider.RestTruncateEndpoint;
import com.prodyna.camelExamples.endpoints.EndpointProvider.TruncateDb;
import com.prodyna.camelExamples.processors.RestProcessor.RestProc;

public class RestRoute extends RouteBuilder {

    private Endpoint rest;
    private Processor answer;
    private Endpoint restCountEndpoint;
    private Endpoint countDb;
    private Endpoint restTruncateEndpoint;
    private Endpoint truncateDb;

    @Inject
    private RestRoute(@RestEndpoint Endpoint rest, @RestProc Processor answer,
                      @RestCountEndpoint Endpoint restCountEndpoint, @CountDb Endpoint countDb,
                      @RestTruncateEndpoint Endpoint restTruncateEndpoint, @TruncateDb Endpoint truncateDb) {
        this.rest = rest;
        this.answer = answer;
        this.restCountEndpoint = restCountEndpoint;
        this.countDb = countDb;
        this.restTruncateEndpoint = restTruncateEndpoint;
        this.truncateDb = truncateDb;

    }

    @Override
    public void configure() throws Exception {
        restConfiguration().component("restlet").apiContextPath("/").port(8082);
        from(rest).process(answer);
        from(restCountEndpoint).to(countDb);
        from(restTruncateEndpoint).to(truncateDb).to(countDb);
    }

}
