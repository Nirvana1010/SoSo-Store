package com.soecode.lyf.service;

import com.soecode.lyf.entity.ConsumInfo;
import com.soecode.lyf.entity.MobileCard;

import java.sql.SQLException;
import java.util.List;

public interface CardService {

    /**
     * 根据号码查找是否存在某电话卡
     * @param number
     * @return
     */
    boolean isExistCard(String number);

    /**
     * 查看帐号密码是否对应
     * @param number
     * @param passWord
     * @return
     */
    boolean isExistCard(String number, String passWord) throws SQLException;

    /**
     * 生成一个新手机号
     * @return
     */
    String createNumber();

    /**
     * 注册新用户时添加电话卡
     * @param card
     */
    void addCard(MobileCard card);

    /**
     *本月账单查询
     * @param number
     */
    String showAmountDetail(String number);

    /**
     *套餐余量查询
     * @param number
     */
    String showRemainDetail(String number);

    /**
     *添加消费记录
     * @param number
     * @param info
     */
    void addConsumInfo(String number, ConsumInfo info);

    /**
     * 打印消费详单
     * @param number
     */
    void printConsumInfo(String number);

    /**
     * 使用嗖嗖
     * @param number
     */
    String useSoso(String number);

    /**
     * 删除电话卡
     * @param number
     */
    void delCard(String number);

    /**
     * 更改套餐类型
     * @param number
     */
    String changingPack(String number, int sel);

    /**
     *话费充值
     * @param number
     * @param money
     */
    double chargeMoney(String number, double money);

    /**
     *查看资费说明
     */
    void showDescription();

    /**
     * 得到count个新手机号
     * @param count
     * @return
     */
    List<String> getNewNumbers(int count);
}
