package com.prodyna.camelExamples.routes;

import javax.inject.Inject;

import org.apache.camel.Endpoint;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

import com.prodyna.camelExamples.endpoints.FileEndpointProvider.FolderEndpoint;
import com.prodyna.camelExamples.endpoints.FileEndpointProvider.TargetFolderEndpoint;
import com.prodyna.camelExamples.processors.CsvProcessor.CsvProc;

public class FileRoute extends RouteBuilder {

    private Endpoint folder;
    private Processor csvProcessor;
    private Endpoint targetFolder;

    @Inject
    private FileRoute( @FolderEndpoint Endpoint folder, @CsvProc Processor csvProcessor, @TargetFolderEndpoint Endpoint targetFolder ) {
        this.folder = folder;
        this.csvProcessor = csvProcessor;
        this.targetFolder = targetFolder;
    }

    @Override
    public void configure() throws Exception {

        from( folder )
            .unmarshal().gzip()
            .unmarshal().csv()
            .process( csvProcessor )
            .marshal().csv()
            .marshal().gzip()
            .to( targetFolder );
    }

}
