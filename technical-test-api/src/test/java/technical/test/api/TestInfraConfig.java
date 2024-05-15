package technical.test.api;

import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

// Quick and dirty way to share a single mongoDB container between all tests
// TODO: clean up both test in order to share the Spring context between all tests
@Testcontainers
public class TestInfraConfig {
    @Container
    @ServiceConnection
    final static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:5"));

    static {
        // TODO: LibraryServiceImplTest used to start it with `@BeforeAll` (junit 5)
        // but   LibraryEndpointIntegrationTest is using junit 4 so we can't re-use junit 5 machinery
        // Ideally junit4 should be excluded in pom.xml and not be used at all
        mongoDBContainer.start();
    }
}
