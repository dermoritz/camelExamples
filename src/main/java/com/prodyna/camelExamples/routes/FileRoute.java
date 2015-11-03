package com.prodyna.camelExamples.routes;

import java.util.concurrent.ExecutorService;

import javax.inject.Inject;

import org.apache.camel.Endpoint;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

import com.prodyna.camelExamples.endpoints.EndpointProvider.FolderEndpoint;
import com.prodyna.camelExamples.endpoints.EndpointProvider.TargetFolderEndpoint;
import com.prodyna.camelExamples.processors.CsvProcessor.CsvProc;
import com.prodyna.camelExamples.threadpool.ThreadPoolProvider.ThreeTo6;

public class FileRoute extends RouteBuilder {

    private Endpoint folder;
    private Processor csvProcessor;
    private ExecutorService threeTo6;
    private Endpoint targetFolder;

    @Inject
    private FileRoute(@FolderEndpoint Endpoint folder, @CsvProc Processor csvProcessor,
                      @ThreeTo6 ExecutorService threeTo6, @TargetFolderEndpoint Endpoint targetFolder) {
        this.folder = folder;
        this.csvProcessor = csvProcessor;
        this.threeTo6 = threeTo6;
        this.targetFolder = targetFolder;
    }

    @Override
    public void configure() throws Exception {

        from(folder)
                    .unmarshal().gzip()
                    .unmarshal().csv()
                    .process(csvProcessor)
                    .marshal().csv()
                    .marshal().gzip()
                    .to(targetFolder);
    }

}
