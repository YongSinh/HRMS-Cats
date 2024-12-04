package cats.com.notificationservice.service;

import cats.com.notificationservice.message.MessageFull;
import cats.com.notificationservice.repo.MessageRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageServiceImp implements MessageService {
    private final MessageRepo messageRepository;

    @Override
    public MessageFull saveMessage(MessageFull messageFull) {
        return messageRepository.save(messageFull);
    }

    @Override
    public MessageFull viewMessage(Integer id) {
        return null;
    }

    @Override
    public MessageFull getMessageById(Integer id) {
        return messageRepository.findById(id).orElse(null);
    }

    @Override
    public List<MessageFull> getMessageBySender(String sender) {
        return messageRepository.findBySenderOrderByDateTimeDesc(sender);
    }

    @Override
    public List<MessageFull> getListMessage(Integer id) {
        return null;
    }

    @Override
    public MessageFull updateMessage(Integer id, MessageFull newMessage) {
        Optional<MessageFull> existingMessage = messageRepository.findById(id);
        if (existingMessage.isPresent()) {
            MessageFull message = existingMessage.get();
            message.setKhmerText(newMessage.getKhmerText());
            message.setEnglishText(newMessage.getEnglishText());
            message.setSender(newMessage.getSender());
            message.setReceiver(newMessage.getReceiver());
            message.setMessageType(newMessage.getMessageType());
            message.setSessionId(newMessage.getSessionId());
            message.setDateTime(newMessage.getDateTime());
            message.setServiceType(newMessage.getServiceType());
            message.setIsRead(newMessage.getIsRead());
            message.setStatus(newMessage.getStatus());
            message.setAttachments(newMessage.getAttachments());
            message.setPriority(newMessage.getPriority());
            message.setEditedAt(newMessage.getEditedAt());
            message.setDeletedAt(newMessage.getDeletedAt());
            return messageRepository.save(message);
        }
        return null;
    }

    @Override
    public List<MessageFull> getAllMessages() {
        return messageRepository.findAll();
    }

    @Override
    public MessageFull markRead(Integer id) {
        MessageFull messageFull = getMessageById(id);
        messageFull.setIsRead(true);
        messageRepository.save(messageFull);
        return messageFull;
    }

    @Override
    public List<MessageFull> getAllMessagesOrderByDate() {
        return messageRepository.findAllByOrderByDateTimeDesc();
    }

    @Override
    public List<MessageFull> getMessageType(String type) {
        return messageRepository.findByMessageTypeOrderByDateTimeDesc(type);
    }

    @Override
    public Page<MessageFull> getMessageType(String type, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return messageRepository.findByMessageTypeOrderByDateTimeDesc(type, pageable);
    }

    @Override
    public List<MessageFull> findUnreadMessagesByReceiver(String receiver) {
        return messageRepository.findByReceiverAndIsRead(receiver, false);
    }

    @Override
    public List<MessageFull> getAllMessagesBySenderAndService(String sender, String service) {
        return messageRepository.findBySenderAndServiceTypeOrderByDateTimeDesc(sender,service);
    }

    @Override
    public Page<MessageFull> getNotificationsForUser(String userId, String type, Pageable pageable) {
        return messageRepository.findByMessageTypeAndReceiverOrderByDateTimeDesc(type, userId, pageable);
    }


    @Override
    public long getUnreadCountForUser(String userId) {
       return messageRepository.countByReceiverAndIsReadFalse(userId);
    }

    @Override
    public void markNotificationsAsRead(String userId) {
        List<MessageFull> notifications = messageRepository.findAllByReceiverAndIsReadFalse(userId, Pageable.unpaged());
        for (MessageFull notification : notifications) {
            notification.setIsRead(true);
            notification.setEditedAt(LocalDateTime.now());
        }
        messageRepository.saveAll(notifications);
    }

}
