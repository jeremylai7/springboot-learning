package com.interfaces.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author: laizc
 * @date: 2026/4/3
 * @desc: 加购
 */
@Data
public class AddCartRequest {

    /**
     * 店铺id
     */
    @NotNull
    private Long storeId;

    @NotEmpty
    private List<CartTem> products;

    @Data
    public class CartTem {
        /**
         * 商品id
         */
        @NotNull
        private Long skuId;
        /**
         * 数量
         */
        @NotNull
        @Min(1)
        private Integer quantity;
    }
}
