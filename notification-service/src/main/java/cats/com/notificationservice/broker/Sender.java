package cats.com.notificationservice.broker;

import cats.com.notificationservice.message.Message;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class Sender {

    private final KafkaTemplate<String, Message> messageKafkaTemplate;

    public Sender(KafkaTemplate<String, Message> messageKafkaTemplate) {
        this.messageKafkaTemplate = messageKafkaTemplate;
    }

    public void send(String topic, Message message) {
        messageKafkaTemplate.send(topic, message);
    }
}