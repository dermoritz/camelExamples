package com.prodyna.camelExamples.threadpool;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.ExecutorService;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Qualifier;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.ThreadPoolBuilder;

public class ThreadPoolProvider {

    private CamelContext context;


    @Inject
    private ThreadPoolProvider(CamelContext context){
        this.context = context;

    }

    @Produces
    @ThreeTo6
    private ExecutorService get3to6() throws Exception{
        return new ThreadPoolBuilder(context).poolSize(3).maxPoolSize(6).build("3to6");
    }


    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
    public static @interface ThreeTo6 {

    }

}
