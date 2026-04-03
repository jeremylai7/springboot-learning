package com.domain.cart.entity;

import lombok.Data;

/**
 * @author: laizc
 * @date: 2026/4/3
 * @desc:
 */
@Data
public class CartProductEntity {

    private Long skuId;

    private Integer quantity;

    private String skuNo;

    /**
     * 是否选中
     */
    private boolean select;

    /**
     * 是否失效
     */
    private boolean invalid;

    public void merge(CartProductEntity product) {
        if (product == null) {
            throw new IllegalArgumentException("商品不能为空");
        }
        this.skuId = product.getSkuId();
        this.skuNo = product.getSkuNo();
        this.invalid = product.isInvalid();
        this.select = product.isSelect();
        this.quantity = this.quantity + product.getQuantity();
    }
}
