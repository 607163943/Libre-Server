package com.libre.pojo.event;

import com.libre.pojo.dto.common.OverMessageInfo;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.util.List;

@Setter
@Getter
public class BatchOverMessageEvent extends ApplicationEvent {
    private List<OverMessageInfo> overMessageInfos;

    public BatchOverMessageEvent(Object source, List<OverMessageInfo> overMessageInfos) {
        super(source);
        this.overMessageInfos = overMessageInfos;
    }
}
