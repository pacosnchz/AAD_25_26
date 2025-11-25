package com.paco.aad.config;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;

@Component
public class DatabaseInitializer implements ApplicationRunner {

    private final DataSource dataSource;
    private final SqlProperties sqlProperties;
    private final ResourceLoader resourceLoader;

    public DatabaseInitializer(DataSource dataSource, SqlProperties sqlProperties, ResourceLoader resourceLoader) {
        this.dataSource = dataSource;
        this.sqlProperties = sqlProperties;
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try (Connection con = dataSource.getConnection()) {
            for (String scriptPath : sqlProperties.getScripts()) {
                Resource resource = resourceLoader.getResource(scriptPath);
                ScriptUtils.executeSqlScript(con, resource);
            }
            System.out.println("Database initialized from SQL scripts");
        }
    }
}
