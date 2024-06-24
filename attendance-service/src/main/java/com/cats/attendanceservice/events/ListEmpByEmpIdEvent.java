package com.cats.attendanceservice.events;

import com.cats.attendanceservice.service.ApiService;
import lombok.*;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;
import java.util.Collection;

@Getter
@Setter
public class ListEmpByEmpIdEvent extends ApplicationEvent {
    private Long emId;

    public ListEmpByEmpIdEvent(ApiService source, Long emId) {
        super(source);
        this.emId = emId;
    }

    public ListEmpByEmpIdEvent(Long emId) {
        super(emId);
        this.emId = emId;
    }
}
