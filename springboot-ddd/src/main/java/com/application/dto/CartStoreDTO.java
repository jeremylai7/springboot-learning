package com.application.dto;

import lombok.Data;

import java.util.List;

/**
 * @author: laizc
 * @date: 2026/4/3
 * @desc:
 */
@Data
public class CartStoreDTO {

    private Long storeId;

    private List<CartProductDTO> products;

}
