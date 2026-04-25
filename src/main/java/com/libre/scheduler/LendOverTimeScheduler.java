package com.libre.scheduler;

import com.libre.service.common.CommonLendService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class LendOverTimeScheduler {
    private final CommonLendService commonLendService;

    // 每天0点扫描当前处于借阅状态的借阅信息，将超时数据进行状态更改
    // 临时改成10分钟一次
    @Scheduled(cron = "0 0 0/10 * * ?")
    public void checkLendOverTime() {
        commonLendService.updateOverTimeLend();
    }
}
