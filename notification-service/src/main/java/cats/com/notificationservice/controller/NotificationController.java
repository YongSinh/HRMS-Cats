package cats.com.notificationservice.controller;

import cats.com.notificationservice.base.BaseApi;
import cats.com.notificationservice.message.MessageFull;
import cats.com.notificationservice.service.ApiService;
import cats.com.notificationservice.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
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

    @GetMapping("/api/notification/user")
    public BaseApi<?> getNotifications(
            @RequestParam String userId,
            @RequestParam String type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<MessageFull> pagedMessages = messageService.getNotificationsForUser(userId, type ,pageable);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("List All the notification for userId: "+ userId)
                .timestamp(LocalDateTime.now())
                .data(pagedMessages)
                .build();
    }

    @GetMapping("/api/notification/general")
    public ResponseEntity<?> getGenMessage(
            @RequestParam String type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<MessageFull> pagedMessages = messageService.getMessageType(type, page, size);
        return buildPagedResponse(pagedMessages);
    }

    private ResponseEntity<Map<String, Object>> buildPagedResponse(Page<MessageFull> pagedMessages) {
        Map<String, Object> response = new HashMap<>();
        response.put("data", pagedMessages.getContent()); // List of messages
        response.put("hasMore", pagedMessages.hasNext()); // Indicates if more pages are available
        response.put("currentPage", pagedMessages.getNumber());
        response.put("totalPages", pagedMessages.getTotalPages());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/api/notification/listEmp")
    public ResponseEntity<?> getlistEmp() throws IOException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(apiService.getListEmId());
    }

    @GetMapping("/api/notification/unreadCount")
    public ResponseEntity<?> getUnreadCount(@RequestParam String userId) {
        long unreadCount = messageService.getUnreadCountForUser(userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(unreadCount);
    }

    @PutMapping("/api/notification/markAsRead")
    public ResponseEntity<?> markNotificationsAsRead(@RequestParam String userId) {
        messageService.markNotificationsAsRead(userId);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/api/notification/markRead")
    public ResponseEntity<?> markMessageRead(@RequestParam Integer id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(messageService.markRead(id));
    }
}
