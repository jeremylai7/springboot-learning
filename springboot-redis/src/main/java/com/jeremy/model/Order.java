package com.jeremy.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: laizc
 * @date: created in 2023/3/28
 * @desc:
 */
@Getter
@Setter
@Table(name = "t_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 订单号
     */
    private String sn;

    /**
     * 数量
     */
    private Integer num;

    /**
     * 单价
     */
    private Integer price;

    /**
     * 商品id
     */
    private Long productId;

    private Date createTime;

}
