package org.jugtaas.spike.service;

import org.postgresql.ds.PGPoolingDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Created by mario on 02/11/2016.
 */
@Configuration
public class JugtaasDataSource {

    public static final String DATA_SOURCE_NAME = "dataSource";

    @Value("${spring.datasource.databaseName}")
    private String databaseName;

    @Value("${spring.datasource.serverName}")
    private String serverName;

    @Value("${spring.datasource.serverPort}")
    private int serverPort;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Bean(name = "dataSource")
    public DataSource dataSource() {
        PGPoolingDataSource ds = new PGPoolingDataSource();

        ds.setDatabaseName(databaseName);
        ds.setDataSourceName(DATA_SOURCE_NAME);
        ds.setServerName(serverName);
        ds.setPortNumber(serverPort);
        ds.setUser(username);
        ds.setPassword(password);

        return ds;
    }
}
