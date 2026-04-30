package com.libre.scheduler;

import com.libre.pojo.dto.common.OverLend;
import com.libre.pojo.dto.common.OverMessageInfo;
import com.libre.pojo.event.BatchOverMessageEvent;
import com.libre.service.common.CommonLendService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class LendOverTimeScheduler {
    private final CommonLendService commonLendService;
    private final ApplicationEventPublisher applicationEventPublisher;

    // 每天凌晨4点扫描当前处于借阅状态的借阅信息，将超时数据进行状态更改
    // 临时改成每10分钟
    @Scheduled(cron = "* 0/10 * * * ? ")
    public void checkLendOverTime() {
        commonLendService.updateOverTimeLend();
    }

    // 每天下午3点分钟扫描一次超时借阅信息，并发送消息提醒
    @Scheduled(cron = "* * 15 * * ?")
    public void sendOverDueMessage() {
        List<OverLend> overLends = commonLendService.selectOverLend();
        // 构建事件集合
        List<OverMessageInfo> overMessageInfos = new ArrayList<>(overLends.size());
        for (OverLend overLend : overLends) {
            // 计算出从逾期日期当目前截止天数
            long overDay = ChronoUnit.DAYS.between(overLend.getDueTime(), LocalDateTime.now());
            OverMessageInfo overMessageInfo = OverMessageInfo.builder()
                    .userId(overLend.getUserId())
                    .bookName(overLend.getBookName())
                    .overDay(overDay)
                    .build();

            overMessageInfos.add(overMessageInfo);
        }

        // 构建事件
        BatchOverMessageEvent batchOverMessageEvent = new BatchOverMessageEvent(this, overMessageInfos);
        // 发送事件
        applicationEventPublisher.publishEvent(batchOverMessageEvent);
    }
}
