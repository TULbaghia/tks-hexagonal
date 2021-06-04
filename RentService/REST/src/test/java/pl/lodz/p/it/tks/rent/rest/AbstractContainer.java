package pl.lodz.p.it.tks.rent.rest;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.images.builder.ImageFromDockerfile;

import java.nio.file.Path;

@SuppressWarnings("rawtypes")
public abstract class AbstractContainer {
    public static GenericContainer<?> serviceOne;

    public static GenericContainer getService() {
        if(serviceOne == null) {
            serviceOne = new GenericContainer<>(
                    new ImageFromDockerfile()
                            .withDockerfileFromBuilder(builder
                                    -> builder
                                    .from("payara/server-full:5.2020.7-jdk11")
                                    .copy("UserServiceApp-1.0-SNAPSHOT.war", "/opt/payara/deployments")
                                    .copy("RentServiceApp-1.0-SNAPSHOT.war", "/opt/payara/deployments")
                                    .build())
                            .withFileFromPath("UserServiceApp-1.0-SNAPSHOT.war",
                                    Path.of("target", "../../../UserService/UserServiceApp/target/UserServiceApp-1.0-SNAPSHOT.war"))
                            .withFileFromPath("RentServiceApp-1.0-SNAPSHOT.war",
                                    Path.of("target", "../../RentServiceApp/target/RentServiceApp-1.0-SNAPSHOT.war"))
            ).withExposedPorts(8181, 8080, 4848)
                    .waitingFor(Wait.forHttp("/UserServiceApp-1.0-SNAPSHOT/api/start").forPort(8080).forStatusCode(200))
                    .withReuse(true);
            serviceOne.start();
        }
        return serviceOne;
    }
}
