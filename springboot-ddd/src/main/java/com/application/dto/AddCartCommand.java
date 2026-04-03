package com.application.dto;

import lombok.Data;

import java.util.List;

/**
 * @author: laizc
 * @date: 2026/4/3
 * @desc:
 */
@Data
public class AddCartCommand {

    private Long userId;

    private List<CartStoreDTO> storeProducts;


}
