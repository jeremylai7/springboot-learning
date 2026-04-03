package com.interfaces.web;

import com.application.dto.AddCartCommand;
import com.application.dto.CartProductDTO;
import com.application.dto.CartStoreDTO;
import com.application.service.ShopCartApplicationService;
import com.interfaces.dto.AddCartRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: laizc
 * @date: 2026/4/3
 * @desc: 购物车接口
 */
@RestController("shop/cart")
@Validated
public class ShopCartController {


    @Autowired
    private ShopCartApplicationService shopCartApplicationService;

    /**
     * 加入购物车
     * @param request
     */
    @PostMapping("/add")
    public void addCart(@RequestBody @Validated List<AddCartRequest> request) {
        // request 转换成 commond
        // 模拟当前登录人
        Long currentUserId = 12345L;
        AddCartCommand command = new AddCartCommand();
        command.setUserId(currentUserId);
        List<CartStoreDTO> storeProducts = new ArrayList<>();
        for (AddCartRequest addCartRequest : request) {
            CartStoreDTO cartStoreDTO = new CartStoreDTO();
            cartStoreDTO.setStoreId(addCartRequest.getStoreId());
            List<CartProductDTO> cartProducts = new ArrayList<>();
            for (AddCartRequest.CartTem cartTem : addCartRequest.getProducts()) {
                CartProductDTO cartProductDTO = new CartProductDTO();
                cartProductDTO.setSkuId(cartTem.getSkuId());
                cartProductDTO.setQuantity(cartTem.getQuantity());
                cartProducts.add(cartProductDTO);
            }
            cartStoreDTO.setProducts(cartProducts);
            storeProducts.add(cartStoreDTO);
        }
        shopCartApplicationService.addProduct(command);






    }


}
