package com.teamvoy.testJava.services;

import com.teamvoy.testJava.models.Good;
import com.teamvoy.testJava.repositories.GoodRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GoodsServiceImplTest {
    @InjectMocks
    private GoodsServiceImpl goodsService;
    @Mock
    private GoodRepository goodRepository;


    @Test
    public void testAddGoods() {
        String goodName = "Test Good";
        double goodPrice = 19.99;

        Good savedGood = new Good();
        when(goodRepository.save(any(Good.class))).thenReturn(savedGood);

        goodsService.addGoods(goodName, goodPrice);

        ArgumentCaptor<Good> goodCaptor = ArgumentCaptor.forClass(Good.class);
        verify(goodRepository).save(goodCaptor.capture());

        Good capturedGood = goodCaptor.getValue();
        assertEquals(goodName, capturedGood.getName());
        assertEquals(goodPrice, capturedGood.getPrice(), 0.01);
    }

    @Test
    public void testGetAllGoods() {
        List<Good> expectedGoods = new ArrayList<>();
        when(goodRepository.findAll()).thenReturn(expectedGoods);

        List<Good> actualGoods = goodsService.getAllGoods();

        assertSame(expectedGoods, actualGoods);
    }

    @Test
    public void testGetGoodById_Successful() {
        long goodId = 1L;
        Good expectedGood = new Good(goodId, "Test Good", 19.99);
        when(goodRepository.findById(goodId)).thenReturn(Optional.of(expectedGood));

        Good actualGood = goodsService.getGoodById(goodId);

        assertEquals(expectedGood, actualGood);
    }

    @Test
    public void testGetGoodById_NotFound() {
        long goodId = 1L;
        when(goodRepository.findById(goodId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> goodsService.getGoodById(goodId));
    }

}