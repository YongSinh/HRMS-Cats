package cats.com.notificationservice.service;

import cats.com.notificationservice.message.MessageFull;

import java.util.List;

public interface MessageService {
    MessageFull saveMessage(MessageFull messageFull);
    MessageFull viewMessage(Integer id);
    MessageFull getMessageById (Integer id);
    List<MessageFull> getMessageBySender (String sender);
    List<MessageFull> getListMessage (Integer id);
}
