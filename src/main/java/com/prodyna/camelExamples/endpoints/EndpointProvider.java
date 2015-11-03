package com.prodyna.camelExamples.endpoints;

import java.io.File;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Qualifier;

import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.component.file.FileEndpoint;
import org.apache.camel.component.sql.SqlEndpoint;

import com.prodyna.camelExamples.ArgsConfiguration.Folder;
import com.prodyna.camelExamples.ArgsConfiguration.TargetFolder;
import com.prodyna.camelExamples.Boot;

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

    @Produces
    @TargetFolderEndpoint
    private Endpoint getTargetFolder(@TargetFolder File folder) {
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

    public static final String UUID_PAR = "UUID";
    public static final String COL1 = "COL1";
    public static final String COL2 = "COL2";
    public static final String COL3 = "COL3";
    private static final String DB_WRITE_QUERY = "INSERT INTO CAMEL.STUFF VALUES (:#" + UUID_PAR + ",:#" + COL1
                                                 + ",:#" + COL2 + ",:#" + COL3 + ")";

    @Produces
    @WriteDb
    private Endpoint getWriteDbEndpoint() {
        //data source must be set within uri
        SqlEndpoint endpoint = context.getEndpoint("sql:" + DB_WRITE_QUERY + "?dataSource=" + Boot.DB, SqlEndpoint.class);
        endpoint.setBatch(true);
        return endpoint;
    }

    private static final String DB_TRUNCATE_QUERY = "TRUNCATE TABLE CAMEL.STUFF";

    @Produces
    @TruncateDb
    private Endpoint getTruncateDbEndpoint() {
        //data source must be set within uri
        SqlEndpoint endpoint = context.getEndpoint("sql:" + DB_TRUNCATE_QUERY + "?dataSource=" + Boot.DB, SqlEndpoint.class);
        return endpoint;
    }

    @Produces
    @CountDb
    private Endpoint getCountDbEndpoint() {
        //data source must be set within uri
        SqlEndpoint endpoint = context.getEndpoint("sql:" + DB_COUNT_QUERY + "?dataSource=" + Boot.DB, SqlEndpoint.class);
        return endpoint;
    }

    private static final String DB_COUNT_QUERY = "select count(*) from CAMEL.STUFF";

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
    public static @interface FolderEndpoint {

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
    public static @interface TargetFolderEndpoint {

    }

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
    public static @interface WriteDb {

    }

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
    public static @interface CountDb {

    }

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
    public static @interface TruncateDb {

    }
}
