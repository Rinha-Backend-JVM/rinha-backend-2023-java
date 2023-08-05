package org.acme.config;

import io.quarkus.arc.Arc;
import io.quarkus.arc.InstanceHandle;
import io.quarkus.flyway.runtime.FlywayContainer;
import io.quarkus.flyway.runtime.FlywayContainerProducer;
import io.quarkus.flyway.runtime.QuarkusPathLocationScanner;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.flywaydb.core.Flyway;

import javax.sql.DataSource;
import java.util.List;

@ApplicationScoped
public class RunFlyway {

    @ConfigProperty(name = "quarkus.datasource.reactive.url")
    String datasourceUrl;
    @ConfigProperty(name = "quarkus.datasource.username")
    String datasourceUsername;
    @ConfigProperty(name = "quarkus.datasource.password")
    String datasourcePassword;

    @ConfigProperty(name = "flyway.native.files")
    List<String> files;

    public void runFlywayMigration(@Observes StartupEvent event) {
        QuarkusPathLocationScanner.setApplicationMigrationFiles(files);
        DataSource ds = Flyway.configure().dataSource("jdbc:" + datasourceUrl, datasourceUsername, datasourcePassword).getDataSource();
        try (InstanceHandle<FlywayContainerProducer> instanceHandle = Arc.container().instance(FlywayContainerProducer.class)) {
            FlywayContainer flywayContainer = instanceHandle.get().createFlyway(ds, "<default>", true, true);
            Flyway flyway = flywayContainer.getFlyway();
            flyway.migrate();
        }
    }
}