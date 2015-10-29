package com.prodyna.camelExamples.routes;

import java.util.concurrent.ExecutorService;

import javax.inject.Inject;

import org.apache.camel.Endpoint;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

import com.prodyna.camelExamples.qualifier.CsvProcessor;
import com.prodyna.camelExamples.qualifier.FolderEndpoint;
import com.prodyna.camelExamples.threadpool.ThreadPoolProvider.ThreeTo6;

public class FileRoute extends RouteBuilder {

    private Endpoint folder;
    private Processor csvProcessor;
    private ExecutorService threeTo6;

    @Inject
    private FileRoute(@FolderEndpoint Endpoint folder, @CsvProcessor Processor csvProcessor, @ThreeTo6 ExecutorService threeTo6) {
        this.folder = folder;
        this.csvProcessor = csvProcessor;
        this.threeTo6 = threeTo6;

    }

    @Override
    public void configure() throws Exception {


        from(folder).threads().executorService(threeTo6)
                    .unmarshal().gzip()
                    .unmarshal().csv()
                    .process(csvProcessor);
    }

}
