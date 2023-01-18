package com.soecode.lyf.dao;

import com.soecode.lyf.entity.ConsumInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InfoDao {
    /**
     * 插入新的消费记录
     * @param updInfo
     * @return
     */
    public int updateInfo(ConsumInfo updInfo);

    /**
     * 查询消费记录
     * @param num
     * @return
     */
    public List<ConsumInfo> searchInfo(@Param("number") String num);
}
