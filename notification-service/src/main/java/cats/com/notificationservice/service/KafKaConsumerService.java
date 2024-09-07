package cats.com.notificationservice.service;

import brave.Tracer;
import io.micrometer.observation.ObservationRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class KafKaConsumerService {

    private final ObservationRegistry observationRegistry;
	private final Tracer tracer;
	@KafkaListener(topics = "${general.topic.name}", groupId = "${general.topic.group.id}", containerFactory = "kafkaListenerContainerFactory")
    public void consume(String message) {
        System.out.println("Sent message: {} with offset: {}"+ message);
        log.info(String.format("Message received -> %s", message));
    }

    @KafkaListener(topics = "test", groupId = "${general.topic.group.id}", containerFactory = "kafkaListenerContainerFactory")
    public void consumeTest(String message) {
        System.out.println("Sent message: {} with test offset: {}"+ message);
        log.info(String.format("Message received test-> %s", message));
    }


//    @KafkaListener(topics = "${general.topic.name}",
//            groupId = "${general.topic.group.id}")
//	public void handleNotification(ListEmpByEmpIdEvent listEmpByEmpIdEvent) {
//		Observation.createNotStarted("on-message", this.observationRegistry).observe(() -> {
//			log.info("Got message <{}>", listEmpByEmpIdEvent);
//			log.info("TraceId- {}, Received Notification for EmId - {}", this.tracer.currentSpan().context().traceId(),
//					listEmpByEmpIdEvent.getEmId());
//		});
//		// send out an email notification
//	}

//    @KafkaListener(topics = "${user.topic.name}",
//            groupId = "${user.topic.group.id}",
//            containerFactory = "userKafkaListenerContainerFactory")
//    public void consume(User user) {
//        log.info(String.format("User created -> %s", user));
//    }
}
