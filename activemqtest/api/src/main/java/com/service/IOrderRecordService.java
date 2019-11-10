package com.service;

import com.entity.OrderRecord;

import java.util.List;

/**
 * @author 豪
 */
public interface IOrderRecordService {
    List<OrderRecord> getAllList();

    void insertEntity(OrderRecord orderRecord);
}
