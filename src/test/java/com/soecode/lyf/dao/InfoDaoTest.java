package com.soecode.lyf.dao;

import com.soecode.lyf.BaseTest;
import com.soecode.lyf.entity.ConsumInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class InfoDaoTest extends BaseTest {
    @Autowired
    private InfoDao infoDao;

    @Test
    public void testUpdInfo(){
        ConsumInfo info = new ConsumInfo("13309511588", "上网", 280);
        int res = infoDao.updateInfo(info);
        System.out.println("update: " + res);
    }

    @Test
    public void testsearchInfo() {
        String num = "13309511588";
        List<ConsumInfo> infos = infoDao.searchInfo(num);
        for(ConsumInfo consumInfo : infos)
            System.out.println(consumInfo);
    }
}
