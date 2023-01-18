package com.soecode.lyf.dao;

import com.soecode.lyf.entity.MobileCard;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CardDao {
    /**
     *根据用户Number删除MobileCard
     * @param delNum
     * @return
     */
    public int deleteCardByNumber(@Param("number")String delNum);

    /**
     * 添加新卡
     * @param card
     * @return
     */
    public int saveCard(MobileCard card);

    /**
     * 查询Number对应的卡是否存在
     * @param number
     * @return
     */
    public MobileCard findCardByNum(@Param("number") String number);

    /**
     * 查询某电话卡账号密码是否正确
     * @param num
     * @param psw
     * @return
     */
    public MobileCard findCardByPsw(@Param("number") String num, @Param("password") String psw);

    /**
     * 更新Card余额
     * @param num
     * @param updMoney
     * @return
     */
    public int updateMoney(@Param("number") String num, @Param("money") double updMoney);

    /**
     * 更新消费余额
     * @param num
     * @param updConsumAmount
     * @return
     */
    public int updateConsumAmount(@Param("number") String num, @Param("consumAmount") double updConsumAmount);

    /**
     * 更新通话时间
     * @param num
     * @param updRealTalkTime
     * @return
     */
    public int updateRealTalkTime(@Param("number") String num, @Param("realTalkTime") int updRealTalkTime);

    /**
     * 更新短信条数
     * @param num
     * @param updRealSMSCount
     * @return
     */
    public int updateRealSMSCount(@Param("number") String num, @Param("realSMSCount") int updRealSMSCount);

    /**
     * 更新使用流量
     * @param num
     * @param updRealFlow
     * @return
     */
    public int updateRealFlow(@Param("number") String num, @Param("realFlow") int updRealFlow);

    /**
     * 更新套餐类型
     * @param num
     * @param updServicePackage
     * @return
     */
    public int updateServicePackage(@Param("number") String num, @Param("SerPackage") String updServicePackage);
}
