package com.soecode.lyf.entity;

public class TalkPackage extends ServicePackage implements SendService, CallService{
    private int talkTime;
    private int smsCount;

    public TalkPackage(){
        this.talkTime = 500;
        this.smsCount = 30;
        this.price = 58;
    }

    public int getTalkTime() {
        return talkTime;
    }

    public void setTalkTime(int talkTime) {
        this.talkTime = talkTime;
    }

    public int getSmsCount() {
        return smsCount;
    }

    public void setSmsCount(int smsCount) {
        this.smsCount = smsCount;
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

    @Override
    public String showInfo() {
        String string;
        string = "话痨套餐：通话时长为";
        string = string.concat(String.valueOf(500));
        string = string.concat("分钟/月，短信条数为");
        string = string.concat(String.valueOf(30));
        string = string.concat("条/月，资费为");
        string = string.concat(String.valueOf(58));
        string = string.concat("元/月");
        return string;
    }
}
