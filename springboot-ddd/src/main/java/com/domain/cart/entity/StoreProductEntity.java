package com.domain.cart.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author: laizc
 * @date: 2026/4/3
 * @desc:
 */
@Data
public class StoreProductEntity {

    private Long storeId;

    private List<CartProductEntity> products;

    public void reverse() {
        if (this.products == null || this.products.isEmpty()) {
            return;
        }
        Collections.reverse(this.products);
    }

    public void addProduct(List<CartProductEntity> products) {
        if (this.products == null) {
            this.products = new ArrayList<>();
        }
        if (this.products.isEmpty()) {
            this.products.addAll(products);
        } else {
            //新添加的商品如果不存在就添加,否则就合并
            for (CartProductEntity product : products) {
                CartProductEntity exist = null;
                for (int i = 0; i < this.products.size(); i++) {
                    if (this.products.get(i).getSkuId().equals(product.getSkuId())) {
                        exist = this.products.remove(i);
                        break;
                    }
                }
                if (exist == null) {
                    exist = product;
                } else {
                    exist.merge(product);
                }
                this.products.add(0, exist);
            }


        }

    }
}
