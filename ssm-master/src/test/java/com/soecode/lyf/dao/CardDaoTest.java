package com.soecode.lyf.dao;

import com.soecode.lyf.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.soecode.lyf.entity.MobileCard;
import com.soecode.lyf.entity.NetPackage;


public class CardDaoTest extends BaseTest {
    @Autowired
    private CardDao cardDao;

    @Test
    public void testDeleteBynumber(){
        String number = "13969908499";
        int res = cardDao.deleteCardByNumber(number);
        System.out.println("delete card by 13969908499:" + res);
    }

    @Test
    public void testSaveCard(){
        MobileCard card = new MobileCard("13092837462", "zhangsan", "1223", new NetPackage(), 63, "Net");
        int res = cardDao.saveCard(card);
        System.out.println("save card(13092837462):" + res);
    }

    @Test
    public void testFindCardByNumber(){
        String number = "13309511588";
        MobileCard card;
        card = cardDao.findCardByNum(number);
        System.out.println(card);
    }

    @Test
    public void testFindCardByPsw(){
        String number = "18995116880";
        String psw = "6880";
        MobileCard card;
        card = cardDao.findCardByPsw(number, psw);
        System.out.println(card);
    }

    @Test
    public void testUpdMoney(){
        String num = "18995116880";
        double money = 100;
        int res = cardDao.updateMoney(num, money);
        System.out.println("Update Money(6880):" + res);
    }

    @Test
    public void testUpdConsum(){
        String num = "18995116880";
        double con = 22.2;
        int res = cardDao.updateConsumAmount(num, con);
        System.out.println("Update Consum(6880):" + res);
    }

    @Test
    public void testUpdrealtalk(){
        String num = "18995116880";
        int time = 20;
        int res = cardDao.updateRealTalkTime(num, time);
        System.out.println("Update talk time(6880):" + res);
    }

    @Test
    public void testUpdrealSMS(){
        String num = "18995116880";
        int time = 1000;
        int res = cardDao.updateRealSMSCount(num, time);
        System.out.println("Update talk time(6880):" + res);
    }

    @Test
    public void testUpdrealflow(){
        String num = "18995116990";
        int time = 1000;
        int res = cardDao.updateRealFlow(num, time);
        System.out.println("Update talk time(6990):" + res);
    }

    @Test
    public void testUpdSerPack(){
        String num = "18995116990";
        String pack = "Net";
        int res = cardDao.updateServicePackage(num, pack);
        System.out.println("Update talk time(6990):" + res);
    }


}
