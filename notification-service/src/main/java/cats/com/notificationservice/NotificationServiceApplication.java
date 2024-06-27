package cats.com.notificationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
//@Slf4j
//@RequiredArgsConstructor
@EnableKafka
public class NotificationServiceApplication {

//	private final ObservationRegistry observationRegistry;
//	private final Tracer tracer;
	public static void main(String[] args) {
		SpringApplication.run(NotificationServiceApplication.class, args);
	}
//	@KafkaListener(topics = "notificationTopic")
//	public void handleNotification(ListEmpByEmpIdEvent listEmpByEmpIdEvent) {
//		Observation.createNotStarted("on-message", this.observationRegistry).observe(() -> {
//			log.info("Got message <{}>", listEmpByEmpIdEvent);
//			log.info("TraceId- {}, Received Notification for EmId - {}", this.tracer.currentSpan().context().traceId(),
//					listEmpByEmpIdEvent.getEmId());
//		});
//		// send out an email notification
//	}
}
