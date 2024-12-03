package cats.com.notificationservice.service;

import cats.com.notificationservice.message.MessageFull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MessageService {
    MessageFull saveMessage(MessageFull messageFull);
    MessageFull viewMessage(Integer id);
    MessageFull getMessageById (Integer id);
    List<MessageFull> getMessageBySender (String sender);
    List<MessageFull> getListMessage (Integer id);
    public MessageFull updateMessage(Integer id, MessageFull newMessage);
    List<MessageFull> getAllMessages();
    MessageFull markRead(Integer id);
    List<MessageFull> getAllMessagesOrderByDate();
    List<MessageFull> getMessageType(String type);
    Page<MessageFull> getMessageType(String type, int page, int size);
    List<MessageFull> findUnreadMessagesByReceiver(String receiver);
    List<MessageFull> getAllMessagesBySenderAndService(String sender, String service);

    Page<MessageFull> getNotificationsForUser(String userId,String type ,Pageable pageable);
    long getUnreadCountForUser(String userId);
    void markNotificationsAsRead(String userId);
}
