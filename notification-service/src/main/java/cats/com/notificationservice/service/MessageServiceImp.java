package cats.com.notificationservice.service;

import cats.com.notificationservice.message.MessageFull;
import cats.com.notificationservice.repo.MessageRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceImp implements MessageService {
    private final MessageRepo messageRepo;

    @Override
    public MessageFull saveMessage(MessageFull messageFull) {
        return null;
    }

    @Override
    public MessageFull viewMessage(Integer id) {
        return null;
    }

    @Override
    public MessageFull getMessageById(Integer id) {
        return null;
    }

    @Override
    public List<MessageFull> getMessageBySender(String sender) {
        return null;
    }

    @Override
    public List<MessageFull> getListMessage(Integer id) {
        return null;
    }
}
