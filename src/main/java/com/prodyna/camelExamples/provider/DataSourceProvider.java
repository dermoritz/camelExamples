package com.prodyna.camelExamples.provider;

import java.sql.SQLException;

import javax.enterprise.inject.Produces;
import javax.inject.Singleton;
import javax.sql.DataSource;

import oracle.jdbc.pool.OracleDataSource;

public class DataSourceProvider {

    private static final String DB_URL = "jdbc:oracle:thin:camel/camel@localhost:1521:xe";

    @Produces
    @Singleton
    private DataSource getDS() throws SQLException{
        OracleDataSource ds = new OracleDataSource();
        ds.setURL(DB_URL);
        return ds;
    }

}
