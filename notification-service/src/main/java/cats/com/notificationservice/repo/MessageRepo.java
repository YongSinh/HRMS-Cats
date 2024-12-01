package cats.com.notificationservice.repo;

import cats.com.notificationservice.message.MessageFull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepo extends JpaRepository<MessageFull, Integer> {
    List<MessageFull> findAllByOrderByDateTimeDesc();
    List<MessageFull> findBySenderAndServiceTypeOrderByDateTimeDesc(String sender, String serviceType);
    List<MessageFull> findBySenderOrderByDateTimeDesc(String sender);
    List<MessageFull> findByMessageTypeOrderByDateTimeDesc(String messageType);
    Page<MessageFull> findByMessageTypeOrderByDateTimeDesc(String messageType, Pageable pageable);
    List<MessageFull> findByReceiverAndIsRead(String receiver, Boolean isRead);

}
