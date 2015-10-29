package com.prodyna.camelExamples.endpoints;

import java.io.File;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.component.file.FileEndpoint;

import com.prodyna.camelExamples.qualifier.Folder;
import com.prodyna.camelExamples.qualifier.FolderEndpoint;
import com.prodyna.camelExamples.qualifier.RestEndpoint;

public class EndpointProvider {

    private CamelContext context;

    @Inject
    private EndpointProvider(CamelContext context) {
        this.context = context;

    }

    @Produces
    @FolderEndpoint
    private Endpoint getFolderEndpoint(@Folder File folder) {
        FileEndpoint endpoint = context.getEndpoint("file:" + folder.getPath(), FileEndpoint.class);
        return endpoint;
    }

    public static final String PARAMETER_HEADER = "para";

    @Produces
    @RestEndpoint
    private Endpoint getRestEndpoint() {
        org.apache.camel.component.rest.RestEndpoint endpoint = context.getEndpoint("rest:get:hello/{"
                                                                                    + PARAMETER_HEADER + "}",
                                                                                    org.apache.camel.component.rest.RestEndpoint.class);
        return endpoint;
    }

}
