package com.cats.informationmanagementservice.events;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

@Getter
@Setter
public class ListEmpByEmpIdEvent extends ApplicationEvent{
    private Long emId;

    public ListEmpByEmpIdEvent(Object source, Long emId) {
        super(source);
        this.emId = emId;
    }
    public ListEmpByEmpIdEvent(Long emId) {
        super(emId);
        this.emId = emId;
    }
}
