package com.teamvoy.testJava.services;

import com.teamvoy.testJava.models.Good;
import com.teamvoy.testJava.repositories.GoodRepository;
import com.teamvoy.testJava.serviceInterfaces.GoodsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GoodsServiceImpl implements GoodsService {
    private final GoodRepository goodRepository;

    @Override
    public void addGoods(String goodName, double goodPrice) {
        goodRepository.save(
                Good.builder()
                        .name(goodName)
                        .price(goodPrice)
                        .build());
    }

    @Override
    public List<Good> getAllGoods() {
        return goodRepository.findAll();
    }

    @Override
    public Good getGoodById(long id) {
        return goodRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }
}
