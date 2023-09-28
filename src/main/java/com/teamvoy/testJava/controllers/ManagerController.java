package com.teamvoy.testJava.controllers;

import com.teamvoy.testJava.serviceInterfaces.GoodsService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/manager")
@SecurityRequirement(name = "bearer-token")
@Tag(name = "Manager")
public class ManagerController {
    private final GoodsService goodsService;

    @PostMapping("/goods")
    public ResponseEntity addGoods(@RequestParam String goodName, @RequestParam double goodPrice) {
        goodsService.addGoods(goodName, goodPrice);
        return ResponseEntity.ok().build();
    }
}
