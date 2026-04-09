package com.libre.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.libre.pojo.dto.LendDTO;
import com.libre.pojo.dto.LendPageDTO;
import com.libre.pojo.po.Lend;
import com.libre.pojo.vo.LendPageVO;
import com.libre.result.PageResult;

import java.util.List;

public interface LendService extends IService<Lend> {
    /**
     * 分页查询借阅信息
     * @param lendPageDTO 查询参数
     * @return 查询结果
     */
    PageResult<List<LendPageVO>> pageQueryLend(LendPageDTO lendPageDTO);

    /**
     * 添加借阅记录
     * @param lendDTO 借阅信息
     */
    void addLend(LendDTO lendDTO);

    /**
     * 修改借阅记录
     * @param lendDTO 借阅信息
     */
    void modifyLend(LendDTO lendDTO);

    /**
     * 删除借阅记录
     * @param lendId 借阅记录id
     */
    void deleteLend(Long lendId);
}
