package com.cats.attendanceservice.events;

import com.cats.attendanceservice.service.ApiService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

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
