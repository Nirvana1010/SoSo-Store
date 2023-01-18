package com.soecode.lyf.service.impl;

import com.soecode.lyf.dao.CardDao;
import com.soecode.lyf.dao.InfoDao;
import com.soecode.lyf.entity.*;
import com.soecode.lyf.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.*;

@Service
public class CardUtil implements CardService {

    @Autowired
    CardDao cardDao;

    @Autowired
    InfoDao infoDao;
    Map<Integer, Scene> scenes = new HashMap<Integer, Scene>();

    @Override
    public boolean isExistCard(String number){
        if(cardDao.findCardByNum(number) != null)
            return true;
        else
            return false;
    }

    @Override
    public String createNumber(){
        Random random = new Random();
        String cardNumber = "";
        int tmp = 0;
        boolean ex = false;
        while(!ex){
            tmp = random.nextInt(100000000);//限制8位数
            if(tmp < 10000000)
                tmp = tmp + 10000000;

            cardNumber = "139" + tmp;

            //判断是否存在
            if(cardDao.findCardByNum(cardNumber) != null) {
                continue;
            }
            else
                break;
        }
        return cardNumber;
    }

    //实现
    @Override
    public boolean isExistCard(String number, String passWord) throws SQLException {
        if(cardDao.findCardByNum(number) != null){
            if(cardDao.findCardByPsw(number, passWord) != null)
                return true;
            else{
                System.out.println("密码错误！");
                return false;
            }

        }
        else{
            System.out.println("卡号不存在！");
            return false;
        }
    }

    //实现
    @Override
    public void addCard(MobileCard card){
        cardDao.saveCard(card);
    }

    //本月账单查询
    //实现
    public String showAmountDetail(String number){
        System.out.println("*****本月账单查询*****");
        MobileCard temp;
        String r;
        temp = cardDao.findCardByNum(number);

        StringBuffer sbuf = new StringBuffer();
        DecimalFormat fnum = new DecimalFormat();
        fnum.setMaximumFractionDigits(1);
        double money = temp.getConsumAmount();

        //sbuf.append("您的卡号：" + number + ",当月账单：\n");
        //sbuf.append("\n");
        String serPackage = temp.getServicePackage();
        if(serPackage.equals("Talk")) {
            //话痨套餐
            sbuf.append("套餐资费：" + new TalkPackage().getPrice() + "元\n");
        } else if(serPackage.equals("Net")) {
            //网虫套餐
            sbuf.append("套餐资费：" + new NetPackage().getPrice() + "元\n");
        } else if(serPackage.equals("Super")) {
            //网虫套餐
            sbuf.append("套餐资费：" + new SuperPackage().getPrice() + "元\n");
        }

        sbuf.append("合计：" + fnum.format(money) + "元\n");
        sbuf.append("账户余额：" + fnum.format(temp.getMoney()) + "元");
        System.out.println(sbuf);
        r = sbuf.toString();
        return r;
    }

    //套餐余量查询
    //实现
    @Override
    public String showRemainDetail(String number){
        System.out.println("*****套餐余量查询*****");
        //MobileCard temp = cards.get(number);
        StringBuffer sbuf = new StringBuffer();
        DecimalFormat fnum = new DecimalFormat();
        fnum.setMaximumFractionDigits(1);
        int talkTime;
        int smsCount;
        int flow;
        String s;

        MobileCard temp;
        temp = cardDao.findCardByNum(number);

        if(temp.getServicePackage().equals("Talk")) {
            temp.setSerPackage(new TalkPackage());
        } else if(temp.getServicePackage().equals("Net")) {
            temp.setSerPackage(new NetPackage());
        } else if(temp.getServicePackage().equals("Super")) {
            temp.setSerPackage(new SuperPackage());
        }

        ServicePackage p = temp.getSerPackage();

        //sbuf.append("您的卡号：" + number + ",套餐内剩余：\n");
        if(temp.getSerPackage() instanceof TalkPackage){
            //话痨套餐
            TalkPackage tp = (TalkPackage) p;

            //通话时长
            if(tp.getTalkTime() > temp.getRealTalkTime())
                talkTime = tp.getTalkTime() - temp.getRealTalkTime();
            else
                talkTime = 0;
            sbuf.append("通话时长：" + talkTime + "分钟\n");

            //短信条数
            if(tp.getSmsCount() > temp.getRealSMSCount())
                smsCount = tp.getSmsCount() - temp.getRealSMSCount();
            else
                smsCount = 0;
            sbuf.append("短信条数：" + smsCount + "条");
        }else if(temp.getSerPackage() instanceof NetPackage){
            //网虫套餐
            NetPackage np = (NetPackage) p;

            //上网流量
            if(np.getFlow() > temp.getRealFlow())
                flow = np.getFlow() - temp.getRealFlow();
            else
                flow = 0;
            sbuf.append("上网流量：" + fnum.format((double)flow/1024) + "GB");
        }else{
            //超人套餐
            SuperPackage sp = (SuperPackage) p;

            //通话时长
            if(sp.getTalkTime() > temp.getRealTalkTime())
                talkTime = sp.getTalkTime() - temp.getRealTalkTime();
            else
                talkTime = 0;
            sbuf.append("通话时长：" + talkTime + "分钟\n");

            //短信条数
            if(sp.getSmsCount() > temp.getRealSMSCount())
                smsCount = sp.getSmsCount() - temp.getRealSMSCount();
            else
                smsCount = 0;
            sbuf.append("短信条数：" + smsCount + "条\n");

            //上网流量
            if(sp.getFlow() > temp.getRealFlow())
                flow = sp.getFlow() - temp.getRealFlow();
            else
                flow = 0;
            sbuf.append("上网流量：" + fnum.format((double)flow/1024) + "GB");
        }
        System.out.println(sbuf);
        s = sbuf.toString();
        return s;
    }

    //添加消费记录
    //实现
    @Override
    public void addConsumInfo(String number, ConsumInfo info){
        int result = infoDao.updateInfo(info);
        return;
    }

    //打印消费详单
    //实现
    @Override
    public void printConsumInfo(String number){
        File file = new File("F:\\WEI\\University\\Study\\Grade Three\\Autumn Term\\JAVA\\ssmshowshow\\consumInfos");
        FileWriter out = null;
        String tab = "\t\t";
        String enter = "\r\n";
        int i = 0;
        int data = 0;
        List<ConsumInfo> infos = infoDao.searchInfo(number);

        if(infos != null){ //该卡号是否存在消费记录
            //List<ConsumInfo> temp = new ArrayList<ConsumInfo>();
            //temp = consumInfos.get(number);

            try{
                if(!file.exists())
                    file.createNewFile();
                out = new FileWriter(file);
                System.out.println(file.getAbsolutePath());
                System.out.println(file.getAbsolutePath());
                out.write("******" + number + "消费记录******");
                out.append(enter);
                out.append("序号");
                out.append(tab);
                out.append("类型");
                out.append(tab);
                out.append("数据（通话（条）/上网（MB）/短信（条））");
                out.append(enter);

                for(ConsumInfo info : infos) {
                    out.append((i+1) + ".");
                    out.append(tab);
                    out.append(info.getType());
                    out.append(tab);
                    data = info.getConsumData();
                    out.append(data + "");
                    out.append(enter);
                    i++;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(out != null){
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }else {
            try{
                if(!file.exists())
                    file.createNewFile();
                out = new FileWriter(file);
                out.write("该卡号暂无消费记录！");
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(out != null){
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    //使用嗖嗖
    //实现
    @Override
    public String useSoso(String number){
        String sce = "";

        Scene s0 = new Scene("通话", 90, "问候客户，谁知其如此难缠  通话90分钟");
        Scene s1 = new Scene("短信", 50, "通知朋友手机换号，发送短信50条");
        Scene s2 = new Scene("短信", 5, "参与环境保护实施方案问卷调查，发送短信5条");
        Scene s3 = new Scene("通话", 60, "第一次去外地上学，和妈妈打电话60分钟");
        Scene s4 = new Scene("上网", 1024*2, "为了一时爽，用流量看巴塞罗那比赛，使用流量2GB");
        Scene s5 = new Scene("上网", 1024, "半夜失眠忍不住刷起微博，浪费流量1GB");

        MobileCard temp = new MobileCard();
        temp = cardDao.findCardByNum(number);

        if(temp.getServicePackage().equals("Talk")) {
            temp.setSerPackage(new TalkPackage());
        } else if(temp.getServicePackage().equals("Net")) {
            temp.setSerPackage(new NetPackage());
        } else if(temp.getServicePackage().equals("Super")) {
            temp.setSerPackage(new SuperPackage());
        }

        ServicePackage pack = temp.getSerPackage();

        Random random = new Random();
        int realConsum = 0;//真实消费数据

        scenes.put(0, s0);
        scenes.put(1, s1);
        scenes.put(2, s2);
        scenes.put(3, s3);
        scenes.put(4, s4);
        scenes.put(5, s5);

        while (true){
            int sceneNum = random.nextInt(6);//随机获得场景
            switch (sceneNum){
                case 0://通话
                    if(pack instanceof CallService){
                        //支持通话
                        System.out.println(s0.getDescription());
                        CallService call = (CallService) pack;
                        try {
                            realConsum = call.call(s0.getData(), temp);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        cardDao.updateMoney(temp.getCardNumber(), temp.getMoney());
                        cardDao.updateConsumAmount(temp.getCardNumber(), temp.getConsumAmount());
                        cardDao.updateRealTalkTime(temp.getCardNumber(), temp.getRealTalkTime());
                        addConsumInfo(number, new ConsumInfo(number, s0.getType(), realConsum));
                        sce = s0.getDescription();
                        System.out.println("添加一条记录至消费清单。");
                        break;
                    }else
                        continue;
                case 1://短信
                    if(pack instanceof SendService){
                        //支持短信
                        System.out.println(s1.getDescription());
                        SendService call = (SendService) pack;
                        try {
                            realConsum = call.send(s1.getData(), temp);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        cardDao.updateMoney(temp.getCardNumber(), temp.getMoney());
                        cardDao.updateConsumAmount(temp.getCardNumber(), temp.getConsumAmount());
                        cardDao.updateRealSMSCount(temp.getCardNumber(), temp.getRealSMSCount());
                        addConsumInfo(number, new ConsumInfo(number, s1.getType(), realConsum));
                        sce = s1.getDescription();
                        System.out.println("添加一条记录至消费清单。");
                        break;
                    }else
                        continue;
                case 2://短信
                    if(pack instanceof SendService){
                        //支持短信
                        System.out.println(s2.getDescription());
                        SendService call = (SendService) pack;
                        try {
                            realConsum = call.send(s2.getData(), temp);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        cardDao.updateMoney(temp.getCardNumber(), temp.getMoney());
                        cardDao.updateConsumAmount(temp.getCardNumber(), temp.getConsumAmount());
                        cardDao.updateRealSMSCount(temp.getCardNumber(), temp.getRealSMSCount());
                        addConsumInfo(number, new ConsumInfo(number, s2.getType(), realConsum));
                        sce = s2.getDescription();
                        System.out.println("添加一条记录至消费清单。");
                        break;
                    }else
                        continue;
                case 3:
                    if(pack instanceof CallService){
                        //支持通话
                        System.out.println(s3.getDescription());
                        CallService call = (CallService) pack;
                        try {
                            realConsum = call.call(s3.getData(), temp);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        cardDao.updateMoney(temp.getCardNumber(), temp.getMoney());
                        cardDao.updateConsumAmount(temp.getCardNumber(), temp.getConsumAmount());
                        cardDao.updateRealTalkTime(temp.getCardNumber(), temp.getRealTalkTime());
                        addConsumInfo(number, new ConsumInfo(number, s3.getType(), realConsum));
                        sce = s3.getDescription();
                        System.out.println("添加一条记录至消费清单。");
                        break;
                    }else
                        continue;
                case 4:
                    if(pack instanceof NetService){
                        //支持上网
                        System.out.println(s4.getDescription());
                        NetService call = (NetService) pack;
                        try {
                            realConsum = call.netPlay(s4.getData(), temp);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        cardDao.updateMoney(temp.getCardNumber(), temp.getMoney());
                        cardDao.updateConsumAmount(temp.getCardNumber(), temp.getConsumAmount());
                        cardDao.updateRealFlow(temp.getCardNumber(), temp.getRealFlow());
                        addConsumInfo(number, new ConsumInfo(number, s4.getType(), realConsum));
                        sce = s4.getDescription();
                        System.out.println("添加一条记录至消费清单。");
                        break;
                    }else
                        continue;
                case 5:
                    if(pack instanceof NetService){
                        //支持上网
                        System.out.println(s5.getDescription());
                        NetService call = (NetService) pack;
                        try {
                            realConsum = call.netPlay(s5.getData(), temp);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        cardDao.updateMoney(temp.getCardNumber(), temp.getMoney());
                        cardDao.updateConsumAmount(temp.getCardNumber(), temp.getConsumAmount());
                        cardDao.updateRealFlow(temp.getCardNumber(), temp.getRealFlow());
                        addConsumInfo(number, new ConsumInfo(number, s5.getType(), realConsum));
                        sce = s5.getDescription();
                        System.out.println("添加一条记录至消费清单。");
                        break;
                    }else
                        continue;
            }
            break;
        }
        return sce;
    }

    //办理退网
    //实现
    @Override
    public void delCard(String number){
        System.out.println("******办理退网******");
        //cards.remove(number);
        cardDao.deleteCardByNumber(number);
        System.out.println("卡号" + number + "办理退网成功！");
        System.out.println("谢谢使用！");
    }

    //套餐变更
    @Override
    public String changingPack(String number, int sel){
        System.out.println("*****套餐变更*****");
        System.out.println("1.话痨套餐 2.网虫套餐 3.超人套餐 请选择（序号）：");
        int input = sel;
        String msg = "";
        //MobileCard card = cards.get(number);

        MobileCard card;
        card = cardDao.findCardByNum(number);

        if(card.getServicePackage().equals("Talk")) {
            card.setSerPackage(new TalkPackage());
        } else if(card.getServicePackage().equals("Net")) {
            card.setSerPackage(new NetPackage());
        } else if(card.getServicePackage().equals("Super")) {
            card.setSerPackage(new SuperPackage());
        }

        ServicePackage pack = card.getSerPackage();

        switch (input){
            case 1://话痨套餐
                if(pack instanceof TalkPackage)
                    //System.out.println("对不起，您已经是该套餐用户，无需变更套餐");
                    return "0";
                else {
                    if(card.getMoney() > new TalkPackage().getPrice()){
                        //余额足以支付新套餐资费
                        card.setRealSMSCount(0);
                        card.setRealFlow(0);
                        card.setRealTalkTime(0);
                        card.setSerPackage(new TalkPackage());
                        card.setMoney(card.getMoney() - new TalkPackage().getPrice());
                        cardDao.updateMoney(card.getCardNumber(), card.getMoney());
                        cardDao.updateRealTalkTime(card.getCardNumber(), card.getRealTalkTime());
                        cardDao.updateServicePackage(card.getCardNumber(), "Talk");
                        cardDao.updateRealSMSCount(card.getCardNumber(), card.getRealSMSCount());
                        cardDao.updateRealFlow(card.getCardNumber(), card.getRealFlow());

                        System.out.println("套餐变更成功！");
                        msg = card.showMeg();
                    }
                    else
                        return "0";
                        //System.out.println("对不起，您的余额不足以支付新套餐本月资费，请充值后再办理更换套餐业务！");
                }
                break;
            case 2://网虫套餐
                if(pack instanceof NetPackage)
                    return "0";
                    //System.out.println("对不起，您已经是该套餐用户，无需变更套餐");
                else {
                    if(card.getMoney() > new NetPackage().getPrice()){
                        //余额足以支付新套餐资费
                        card.setRealSMSCount(0);
                        card.setRealFlow(0);
                        card.setRealTalkTime(0);
                        card.setSerPackage(new NetPackage());
                        card.setMoney(card.getMoney() - new NetPackage().getPrice());
                        cardDao.updateMoney(card.getCardNumber(), card.getMoney());
                        cardDao.updateRealTalkTime(card.getCardNumber(), card.getRealTalkTime());
                        cardDao.updateServicePackage(card.getCardNumber(), "Net");
                        cardDao.updateRealSMSCount(card.getCardNumber(), card.getRealSMSCount());
                        cardDao.updateRealFlow(card.getCardNumber(), card.getRealFlow());

                        System.out.println("套餐变更成功！");
                        msg = card.showMeg();
                    }
                    else
                        return "0";
                        //System.out.println("对不起，您的余额不足以支付新套餐本月资费，请充值后再办理更换套餐业务！");
                }
                break;
            case 3://超人套餐
                if(pack instanceof SuperPackage)
                    return "0";
                    //System.out.println("对不起，您已经是该套餐用户，无需变更套餐");
                else {
                    if(card.getMoney() > new SuperPackage().getPrice()){
                        //余额足以支付新套餐资费
                        card.setRealSMSCount(0);
                        card.setRealFlow(0);
                        card.setRealTalkTime(0);
                        card.setSerPackage(new SuperPackage());
                        card.setMoney(card.getMoney() - new SuperPackage().getPrice());
                        cardDao.updateMoney(card.getCardNumber(), card.getMoney());
                        cardDao.updateRealTalkTime(card.getCardNumber(), card.getRealTalkTime());
                        cardDao.updateServicePackage(card.getCardNumber(), "Super");
                        cardDao.updateRealSMSCount(card.getCardNumber(), card.getRealSMSCount());
                        cardDao.updateRealFlow(card.getCardNumber(), card.getRealFlow());
                        System.out.println("套餐变更成功！");
                        msg = card.showMeg();
                    }
                    else
                        return "0";
                        //System.out.println("对不起，您的余额不足以支付新套餐本月资费，请充值后再办理更换套餐业务！");
                }
                break;
        }
        return msg;
    }

    //话费充值
    //实现
    @Override
    public double chargeMoney(String number, double money){
        MobileCard card;
        double result;
        card = cardDao.findCardByNum(number);
        card.setMoney(card.getMoney() + money);
        cardDao.updateMoney(number, card.getMoney());
        result = card.getMoney();
        System.out.println("充值成功，当前话费余额为" + card.getMoney() + "元。");
        return result;
    }

    //查看资费说明
    @Override
    public void showDescription(){
        try{
            BufferedReader buffreader = new BufferedReader(new InputStreamReader(
                    new FileInputStream("src\\description.txt"),"UTF-8"));
            String line = buffreader.readLine();
            StringBuffer buffer = new StringBuffer();
            while(line != null){
                buffer.append(line + "\r\n");
                line = buffreader.readLine();
            }
            System.out.println(buffer.toString());
            buffreader.close();
        }
        catch (FileNotFoundException e){
            System.out.println("要读取的文件不存在" + e.getMessage());
        }
        catch (IOException e){
            System.out.println("文件读取错误" + e.getMessage());
        }

    }

    @Override
    public List<String> getNewNumbers(int count){
        int i = 0;
        String tmp = "";
        List<String> str = new ArrayList<String>();
        for(i = 0; i < count; i++){
            tmp = createNumber();
            str.add(i, tmp);
        }
        return str;
    }
}
