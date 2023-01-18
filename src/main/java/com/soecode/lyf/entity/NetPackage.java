package com.soecode.lyf.entity;

public class NetPackage extends ServicePackage implements NetService {
    private int flow;

    public NetPackage(){
        this.flow = 3*1024;//MB
        this.price = 68;
    }

    public int getFlow() {
        return flow;
    }

    public void setFlow(int flow) {
        this.flow = flow;
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
    public String showInfo() {
        String string;
        string = "网虫套餐：上网流量为";
        string = string.concat(String.valueOf(this.getFlow()/1024));
        string = string.concat("GB/月，资费为");
        string = string.concat(String.valueOf(this.getPrice()));
        string = string.concat("元/月");
        return string;
    }
}
