package cats.com.notificationservice.controller;

import cats.com.notificationservice.message.MessageFull;
import cats.com.notificationservice.service.ApiService;
import cats.com.notificationservice.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class NotificationController {

    private final MessageService messageService;
    private final ApiService apiService;

    @GetMapping("/api/notification")
    public ResponseEntity<?> getNotification() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(messageService.getAllMessagesOrderByDate());
    }

    @GetMapping("/api/notification/listEmp")
    public ResponseEntity<?> getlistEmp() throws IOException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(apiService.getListEmId());
    }

    @GetMapping("/api/notification/general")
    public ResponseEntity<?> getGenMessage(
            @RequestParam String type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<MessageFull> pagedMessages = messageService.getMessageType(type, page, size);
        Map<String, Object> response = new HashMap<>();
        response.put("data", pagedMessages.getContent()); // List of messages
        response.put("hasMore", pagedMessages.hasNext()); // Indicates if more pages are available
        response.put("currentPage", pagedMessages.getNumber());
        response.put("totalPages", pagedMessages.getTotalPages());

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @PostMapping("/api/notification/markRead")
    public ResponseEntity<?> markMessageRead(@RequestParam Integer id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(messageService.markRead(id));
    }
}
