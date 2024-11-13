package com.test.dto;

import lombok.Data;

import java.util.List;

/**
 * @author: laizc
 * @date: created in 2024/11/13
 * @desc:
 **/
@Data
public class AddImageDTO {

    private String entityId;

    private String imgUrl;

    private String picName;

}
