package com.application.service;

import com.application.dto.AddCartCommand;

public interface ShopCartApplicationService {

    void addProduct(AddCartCommand command);
}
