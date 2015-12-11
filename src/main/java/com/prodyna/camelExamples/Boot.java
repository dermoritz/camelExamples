package com.prodyna.camelExamples;

import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.sql.DataSource;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.restlet.RestletComponent;
import org.apache.camel.impl.SimpleRegistry;
import org.slf4j.Logger;

import com.google.common.base.Preconditions;

@Singleton
public class Boot {

	public static final String DB = "db";

    @Inject
	private Logger log;

	private CamelContext context;

    private Instance<RouteBuilder> routes;

    private CamelMain main;

    private DataSource ds;

	@Inject
	private Boot(CamelContext context, @Any Instance<RouteBuilder> routes, CamelMain main, DataSource ds) throws Exception{
		this.routes = routes;
        this.main = main;
        this.ds = ds;
        this.context = Preconditions.checkNotNull(context);
        this.main.enableHangupSupport();
        setupContext();
	}

	private void setupContext() throws Exception {
	    context.addComponent("restlet", new RestletComponent());
	    context.getRegistry(SimpleRegistry.class).put(DB, ds);
        for (RouteBuilder routeBuilder : routes) {
            context.addRoutes(routeBuilder);
        }

    }

    public void start(){
        try {
            main.run();
        } catch (Exception e) {
            throw new IllegalStateException("Problem on starting camel: ", e);
        }
    }

}
