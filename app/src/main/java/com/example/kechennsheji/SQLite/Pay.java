package com.example.kechennsheji.SQLite;

import static java.sql.DriverManager.println;

public class Pay {
    private int money;
    private String datetime;
    private String sort;
    private String introduce;
    public Pay(int money, String datetime, String sort, String introduce)
    {
        this.money=money;
        this.datetime=datetime;
        this.sort=sort;
        this.introduce=introduce;

    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }


    @Override
    public String toString() {
        println("\n");
        return "    "+money+"         "+datetime +"         "+sort+ "         "+introduce;
    }
}


