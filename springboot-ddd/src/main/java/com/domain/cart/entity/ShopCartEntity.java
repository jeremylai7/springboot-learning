package com.domain.cart.entity;

import com.application.dto.CartStoreDTO;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: laizc
 * @date: 2026/4/3
 * @desc:
 */
@Data
public class ShopCartEntity {

    private Long userId;

    private List<StoreProductEntity> storeProducts;

    // 加购
    public void addStoreProduct(StoreProductEntity storeProduct) {
        if (this.storeProducts == null) {
            this.storeProducts = new ArrayList<>();
        }
        StoreProductEntity targetStoreProduct = null;
        int idx = -1;
        // 找到店铺的位置
        for (int i = 0; i < storeProducts.size(); i++) {
            StoreProductEntity tempStoreProduct = storeProducts.get(i);
            if (tempStoreProduct.getStoreId().equals(storeProduct.getStoreId())) {
                targetStoreProduct = tempStoreProduct;
                idx = i;
                break;
            }
        }
        if (targetStoreProduct == null) {
            // 没有找到店铺，直接添加
            storeProduct.reverse();
            this.storeProducts.add(0,storeProduct);
        } else {
            // 找到店铺，添加商品
            StoreProductEntity removed = this.storeProducts.remove(idx);
            removed.addProduct(storeProduct.getProducts());
            this.storeProducts.add(0, removed);
        }
    }
}
