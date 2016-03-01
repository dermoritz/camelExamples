package com.prodyna.camelExamples.endpoints;

import com.prodyna.camelExamples.Boot;
import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.component.sql.SqlEndpoint;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Qualifier;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by moritz on 01.03.2016.
 */
public class DbEndpointProvider {

    private final CamelContext context;

    @Inject
    public DbEndpointProvider(CamelContext context) {
        this.context = context;
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

    private static final String DB_COUNT_QUERY = "select count(*) from CAMEL.STUFF";

    @Produces
    @CountDb
    private Endpoint getCountDbEndpoint() {
        //data source must be set within uri
        SqlEndpoint endpoint = context.getEndpoint("sql:" + DB_COUNT_QUERY + "?dataSource=" + Boot.DB, SqlEndpoint.class);
        return endpoint;
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
