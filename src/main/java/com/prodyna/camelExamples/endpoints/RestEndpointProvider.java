package com.prodyna.camelExamples.endpoints;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Qualifier;

import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;

public class RestEndpointProvider {

    private CamelContext context;

    @Inject
    private RestEndpointProvider(CamelContext context) {
        this.context = context;

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

    @Produces
    @RestCountEndpoint
    private Endpoint getRestCountEndpoint() {
        org.apache.camel.component.rest.RestEndpoint endpoint = context.getEndpoint("rest:get:count",
                                                                                    org.apache.camel.component.rest.RestEndpoint.class);
        return endpoint;
    }

    @Produces
    @RestTruncateEndpoint
    private Endpoint getRestTruncateEndpoint() {
        org.apache.camel.component.rest.RestEndpoint endpoint = context.getEndpoint("rest:get:delete",
                                                                                    org.apache.camel.component.rest.RestEndpoint.class);
        return endpoint;
    }

    @Produces
    @RestFileEndpoint
    private Endpoint getRestFileEndpoint() {
        org.apache.camel.component.rest.RestEndpoint endpoint = context.getEndpoint("rest:get:file",
                                                                                    org.apache.camel.component.rest.RestEndpoint.class);
        return endpoint;
    }

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
    public static @interface RestEndpoint {

    }

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
    public static @interface RestCountEndpoint {

    }

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
    public static @interface RestTruncateEndpoint {

    }

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
    public static @interface RestFileEndpoint {

    }

}
