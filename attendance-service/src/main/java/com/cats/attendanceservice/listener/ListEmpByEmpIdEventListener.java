package com.cats.attendanceservice.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ListEmpByEmpIdEventListener {
//    private final KafkaTemplate<Long, ListEmpByEmpIdEvent> kafkaTemplate;
//    private final ObservationRegistry observationRegistry;
//
//    @EventListener
//    public void handleListEmpByEmpIdEven(ListEmpByEmpIdEvent event) {
//        log.info("Order Placed Event Received, Sending OrderPlacedEvent to notificationTopic: {}", event.getEmId());
//
//        // Create Observation for Kafka Template
//        try {
//            Observation.createNotStarted("notification-topic", this.observationRegistry).observeChecked(() -> {
//                CompletableFuture<SendResult<Long, ListEmpByEmpIdEvent>> future = kafkaTemplate.send("notificationTopic",
//                        new ListEmpByEmpIdEvent(event.getEmId()));
//                return future.handle((result, throwable) -> CompletableFuture.completedFuture(result));
//            }).get();
//        } catch (InterruptedException | ExecutionException e) {
//            Thread.currentThread().interrupt();
//            throw new RuntimeException("Error while sending message to Kafka", e);
//        }
//    }
}
