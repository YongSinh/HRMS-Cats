package cats.com.notificationservice;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;

//@Configuration(proxyBeanMethods = false)
//@RequiredArgsConstructor
public class ManualConfiguration {
//    @Primary
//    @Qualifier("kafkaListenerContainerFactory")
//    private final ConcurrentKafkaListenerContainerFactory concurrentKafkaListenerContainerFactory;
//    @PostConstruct
//    void setup() {
//        this.concurrentKafkaListenerContainerFactory.getContainerProperties().setObservationEnabled(true);
//    }

}
