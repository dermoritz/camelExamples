package com.prodyna.camelExamples.routes;

import javax.inject.Inject;

import com.prodyna.camelExamples.endpoints.DbEndpointProvider;
import org.apache.camel.Endpoint;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

import com.prodyna.camelExamples.endpoints.RestEndpointProvider.RestCountEndpoint;
import com.prodyna.camelExamples.endpoints.RestEndpointProvider.RestEndpoint;
import com.prodyna.camelExamples.endpoints.RestEndpointProvider.RestFileEndpoint;
import com.prodyna.camelExamples.endpoints.RestEndpointProvider.RestTruncateEndpoint;
import com.prodyna.camelExamples.endpoints.DbEndpointProvider.CountDb;
import com.prodyna.camelExamples.endpoints.DbEndpointProvider.TruncateDb;
import com.prodyna.camelExamples.processors.RestProcessor.RestProc;
import com.prodyna.camelExamples.processors.SendFile.SendFileProc;

public class RestRoute extends RouteBuilder {

    private Endpoint rest;
    private Processor answer;
    private Endpoint restCountEndpoint;
    private Endpoint countDb;
    private Endpoint restTruncateEndpoint;
    private Endpoint truncateDb;
    private Endpoint restFileEndpoint;
    private Processor sendFile;

    @Inject
    private RestRoute(@RestEndpoint Endpoint rest, @RestProc Processor answer,
                      @RestCountEndpoint Endpoint restCountEndpoint, @CountDb Endpoint countDb,
                      @RestTruncateEndpoint Endpoint restTruncateEndpoint, @TruncateDb Endpoint truncateDb,
                      @RestFileEndpoint Endpoint restFileEndpoint, @SendFileProc Processor sendFile) {
        this.rest = rest;
        this.answer = answer;
        this.restCountEndpoint = restCountEndpoint;
        this.countDb = countDb;
        this.restTruncateEndpoint = restTruncateEndpoint;
        this.truncateDb = truncateDb;
        this.restFileEndpoint = restFileEndpoint;
        this.sendFile = sendFile;

    }

    @Override
    public void configure() throws Exception {
        restConfiguration().component("restlet").apiContextPath("/").port(8082);
        from(rest).process(answer);
        from(restCountEndpoint).to(countDb);
        from(restTruncateEndpoint).to(truncateDb).to(countDb);
        from(restFileEndpoint).process(sendFile);
    }

}
