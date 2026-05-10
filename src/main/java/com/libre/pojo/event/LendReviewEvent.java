package com.libre.pojo.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Setter
@Getter
public class LendReviewEvent extends ApplicationEvent {
    private Long id;
    private Long userId;
    private Long bookId;
    private Integer applyType;

    public LendReviewEvent(Object source, Long id, Long userId, Long bookId, Integer applyType) {
        super(source);
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
        this.applyType = applyType;
    }
}
