package com.soecode.lyf.entity;

public class SuperPackage extends ServicePackage implements CallService, NetService, SendService {
    private int talkTime;
    private int smsCount;
    private int flow;

    public SuperPackage(){
        this.talkTime = 200;
        this.smsCount = 50;
        this.flow = 1*1024;//MB
        this.price = 78;
    }

    @Override
    public double getPrice() {
        return super.getPrice();
    }

    @Override
    public void setPrice(double price) {
        super.setPrice(price);
    }

    public int getFlow() {
        return flow;
    }

    public void setFlow(int flow) {
        this.flow = flow;
    }

    public int getSmsCount() {
        return smsCount;
    }

    public void setSmsCount(int smsCount) {
        this.smsCount = smsCount;
    }

    public int getTalkTime() {
        return talkTime;
    }

    public void setTalkTime(int talkTime) {
        this.talkTime = talkTime;
    }

    @Override
    public String showInfo() {
        String string;
        string = "超人套餐：通话时长为";
        string = string.concat(String.valueOf(this.getTalkTime()));
        string = string.concat("分钟/月，短信条数为");
        string = string.concat(String.valueOf(this.getSmsCount()));
        string = string.concat("条/月，上网流量为");
        string = string.concat(String.valueOf(this.getFlow()/1024));
        string = string.concat("GB/月，资费为");
        string = string.concat(String.valueOf(this.getPrice()));
        string = string.concat("元/月");
        return string;
    }

    @Override
    public int call(int minCount, MobileCard card) throws Exception {
        int i;
        int tmp = minCount;
        for(i = 0; i < minCount; i++){
            if(this.talkTime >= card.getRealTalkTime() + 1){
                //套餐通话时间足够
                card.setRealTalkTime(card.getRealTalkTime() + 1);
            }else if(card.getMoney() >= 0.2){
                //套餐通话时间不够，但套餐余额足够
                card.setMoney(card.getMoney() - 0.2);//账户余额
                card.setRealTalkTime(card.getRealTalkTime() + 1);
                card.setConsumAmount(card.getConsumAmount() + 0.2);//当月消费金额
            }else{
                //套餐余额不够
                tmp = i;
                throw new Exception("本次已通话" + i + "分钟，您的余额不足，请充值后再使用");
            }
        }
        return tmp;
    }

    @Override
    public int netPlay(int flow, MobileCard card) throws Exception {
        int i;
        int tmp = flow;
        for(i = 0; i < flow; i++){
            if(this.flow >= card.getRealFlow() + 1){
                //套餐流量足够
                card.setRealFlow(card.getRealFlow() + 1);
            }else if(card.getMoney() >= 0.1){
                //套餐流量不够，但套餐余额足够
                card.setMoney(card.getMoney() - 0.1);//账户余额
                card.setRealFlow(card.getRealFlow() + 1);
                card.setConsumAmount(card.getConsumAmount() + 0.1);//当月消费金额
            }else{
                //套餐余额不够
                tmp = i;
                throw new Exception("已经使用流量" + i + "MB，您的余额不足，请充值后再使用");
            }
        }
        return tmp;
    }

    @Override
    public int send(int count, MobileCard card) throws Exception {
        int i;
        int tmp = count;
        for(i = 0; i < count; i++){
            if(this.smsCount >= card.getRealSMSCount() + 1){
                //套餐短信足够
                card.setRealSMSCount(card.getRealSMSCount() + 1);
            }else if(card.getMoney() >= 0.1){
                //套餐短信不够，但套餐余额足够
                card.setMoney(card.getMoney() - 0.1);//账户余额
                card.setRealSMSCount(card.getRealSMSCount() + 1);
                card.setConsumAmount(card.getConsumAmount() + 0.1);//当月消费金额
            }else{
                //套餐余额不够
                tmp = i;
                throw new Exception("已经发送短信" + i + "条，您的余额不足，请充值后再使用");
            }
        }
        return tmp;
    }
}
