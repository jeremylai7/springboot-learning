package com.test.dto;

import lombok.Data;

import java.util.List;

/**
 * @author: laizc
 * @date: created in 2024/11/13
 * @desc:
 **/
@Data
public class DeleteImageDTO {

    private String entityId;

    private List<String> picName;
}
