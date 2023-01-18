package com.soecode.lyf.entity;

public class MobileCard {
    private String cardNumber;
    private String userName;
    private String passWord;
    private ServicePackage serPackage;
    private double consumAmount;
    private double money;
    private int realTalkTime;
    private int realSMSCount;
    private int realFlow;//MB
    private String servicePackage;

    public MobileCard() {}

    public MobileCard(String cardNumber, String userName, String passWord, ServicePackage serPackage, double money
                        , String servicePackage){
        this.cardNumber = cardNumber;
        this.userName = userName;
        this.passWord = passWord;
        this.serPackage = serPackage;
        this.money = money;
        this.servicePackage = servicePackage;
    }

    public MobileCard(String cardNumber, String userName, String passWord, ServicePackage serPackage,
                      double consumAmount, double money, int realTalkTime, int realSMSCount, int realFlow,
                      String servicePackage){
        this.cardNumber = cardNumber;
        this.userName = userName;
        this.passWord = passWord;
        this.serPackage = serPackage;
        this.consumAmount = consumAmount;
        this.money = money;
        this.realTalkTime = realTalkTime;
        this.realSMSCount = realSMSCount;
        this.realFlow = realFlow;
        this.servicePackage = servicePackage;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public double getConsumAmount() {
        return consumAmount;
    }

    public void setConsumAmount(double consumAmount) {
        this.consumAmount = consumAmount;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getRealFlow() {
        return realFlow;
    }

    public void setRealFlow(int realFlow) {
        this.realFlow = realFlow;
    }

    public int getRealSMSCount() {
        return realSMSCount;
    }

    public void setRealSMSCount(int realSMSCount) {
        this.realSMSCount = realSMSCount;
    }

    public int getRealTalkTime() {
        return realTalkTime;
    }

    public void setRealTalkTime(int realTalkTime) {
        this.realTalkTime = realTalkTime;
    }

    public ServicePackage getSerPackage() {
        return serPackage;
    }

    public void setSerPackage(ServicePackage serPackage) {
        this.serPackage = serPackage;
    }

    public String getServicePackage() {
        return servicePackage;
    }

    public void setServicePackage(String servicePackage) {
        this.servicePackage = servicePackage;
    }

    public String showMeg(){
        String string;
        string = "卡号：";
        string = string.concat(this.getCardNumber());
        string = string.concat("用户名：");
        string = string.concat(this.getUserName());
        string = string.concat("当前余额：");
        string = string.concat(String.valueOf(this.getMoney()));
        string = string.concat("元");
        string = string.concat(this.serPackage.showInfo());
        return string;
    }

    @Override
    public String toString(){
        return "card [cardNumber=" + cardNumber + ", userName=" + userName + ", passWord=" + passWord + ",servicePackage="
                + servicePackage + ", consumAmount=" + consumAmount + ", money=" + money + ", realTalkTime=" + realTalkTime
                + ", realSMSCount=" + realSMSCount + ", realFlow=" + realFlow + "]";
     }
}
