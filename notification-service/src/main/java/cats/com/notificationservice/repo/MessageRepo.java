package cats.com.notificationservice.repo;

import cats.com.notificationservice.message.MessageFull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepo extends JpaRepository<MessageFull, Integer> {
}
