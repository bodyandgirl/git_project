package com.dao;


import com.entity.OrderRecord;
import com.github.pagehelper.Page;

public interface OrderRecordDao {
    Page<OrderRecord> findAllList();

    Integer insertEntity(OrderRecord orderRecord);

    OrderRecord  findById(Integer id);

    Page<OrderRecord> findList(OrderRecord orderRecord);
}
