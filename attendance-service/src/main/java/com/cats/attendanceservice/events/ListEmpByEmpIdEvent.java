package com.cats.attendanceservice.events;

import lombok.*;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;
import java.util.Collection;

@Getter
@Setter
public class ListEmpByEmpIdEvent extends ApplicationEvent {
    private Long emId;
    public ListEmpByEmpIdEvent(Object source, Collection<Long> attendanceList) {
        super(source);
    }

    public ListEmpByEmpIdEvent(Object source, Clock clock) {
        super(source, clock);
    }
}
