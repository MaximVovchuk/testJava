package com.teamvoy.testJava.serviceInterfaces;

import com.teamvoy.testJava.models.Good;

import java.util.List;

public interface GoodsService {
    void addGoods(String goodName, double goodPrice);
    List<Good> getAllGoods();
    Good getGoodById(long id);
}
