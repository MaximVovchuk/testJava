package com.teamvoy.testJava.controllers;

import com.teamvoy.testJava.models.Good;
import com.teamvoy.testJava.serviceInterfaces.GoodsService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/goods")
@SecurityRequirement(name = "bearer-token")
@Tag(name = "Goods")
public class GoodsController {
    private final GoodsService goodsService;

    @GetMapping("/list")
    public List<Good> goods() {
        return goodsService.getAllGoods();
    }
}

