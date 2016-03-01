package com.prodyna.camelExamples.routes;

import java.util.concurrent.ExecutorService;

import javax.inject.Inject;

import com.prodyna.camelExamples.endpoints.DbEndpointProvider.WriteDb;
import com.prodyna.camelExamples.endpoints.FileEndpointProvider.TargetFolderEndpoint;
import org.apache.camel.Endpoint;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import com.prodyna.camelExamples.processors.DbProcessor.DbProc;
import com.prodyna.camelExamples.processors.DbResultsProcessor.DbResults;
import com.prodyna.camelExamples.processors.MergerAggregator.Merger;
import com.prodyna.camelExamples.threadpool.ThreadPoolProvider.ThreeTo6;

public class DbRoute extends RouteBuilder {

    private Endpoint folder;
    private Endpoint db;
    private Processor dbProcessor;
    private AggregationStrategy merger;
    private Processor dbResults;
    private ExecutorService threadPool;

    @Inject
    private DbRoute(@TargetFolderEndpoint Endpoint folder, @WriteDb Endpoint db, @DbProc Processor dbProcessor,
                    @Merger AggregationStrategy merger, @DbResults Processor dbResults, @ThreeTo6 ExecutorService threadPool) {
        this.folder = folder;
        this.db = db;
        this.dbProcessor = dbProcessor;
        this.merger = merger;
        this.dbResults = dbResults;
        this.threadPool = threadPool;

    }

    @Override
    public void configure() throws Exception {
        from(folder)
                    .unmarshal().gzip()
                    .unmarshal().csv()
                    //handle csv line by line
                    .split().body()
                    .threads().executorService(threadPool)
                    .process(dbProcessor)
                    .aggregate(constant(true), merger).completionSize(100).completionTimeout(1000)
                    .forceCompletionOnStop()
                    .to(db)
                    .process(dbResults).id("dbResults");
    }

}
