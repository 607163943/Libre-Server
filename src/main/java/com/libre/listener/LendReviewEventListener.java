package com.libre.listener;

import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.libre.constant.MessageType;
import com.libre.constant.PlatformScope;
import com.libre.enums.ExceptionEnums;
import com.libre.exception.LendReviewException;
import com.libre.pojo.dto.admin.MessageSendDTO;
import com.libre.pojo.event.LendReviewEvent;
import com.libre.pojo.po.Book;
import com.libre.pojo.po.Message;
import com.libre.pojo.po.User;
import com.libre.service.admin.AdminMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Component
public class LendReviewEventListener {
    private final AdminMessageService messageService;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @EventListener
    public void handleLendReviewEvent(LendReviewEvent event) {
        log.info("开始处理借阅审核事件");
        // 获取书籍价格
        Book book = Db.lambdaQuery(Book.class)
                .eq(Book::getId, event.getBookId()).one();
        if (book == null) {
            throw new LendReviewException(ExceptionEnums.LEND_REVIEW_BOOK_NOT_EXIST);
        }
        // 走人工审核
        userLendReview(event, book);
        log.info("处理完成");
    }

    private void userLendReview(LendReviewEvent event, Book book) {
        // 给所有管理员发送审核消息
        // 构建消息
        Message message = new Message();
        message.setTitle("借阅审核");
        message.setType(MessageType.REVIEW);
        // 系统发送
        message.setCreateUserId(0L);
        User user = Db.lambdaQuery(User.class)
                .eq(User::getId, event.getUserId())
                .one();
        message.setContent("用户#" + user.getId() + ":" + user.getName() + "申请借阅书籍#" + book.getId() + ":" + book.getBookName());

        messageService.save(message);

        // 发送消息
        MessageSendDTO messageSendDTO = new MessageSendDTO();
        messageSendDTO.setId(message.getId());
        // 全体管理员
        messageSendDTO.setTarget(PlatformScope.ADMIN);
        messageService.sendMessage(messageSendDTO);
    }
}
