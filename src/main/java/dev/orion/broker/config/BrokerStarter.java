package dev.orion.broker.config;

import dev.orion.broker.consumer.ActivityConsumer;
import dev.orion.broker.consumer.DocumentUpdatedConsumer;
import io.quarkus.runtime.StartupEvent;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.io.IOException;

@ApplicationScoped
public class BrokerStarter {
    private static final Logger LOGGER = Logger.getLogger("ListenerBean");

    @Inject
    DocumentUpdatedConsumer documentUpdatedConsumer;

//    @Inject
//    ActivityConsumer activityConsumer;

    void onStart(@Observes StartupEvent ev) {
        LOGGER.info("The application is starting...");
        try {
            documentUpdatedConsumer.attachQueueListener();
            //activityConsumer.attachQueueListener();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}