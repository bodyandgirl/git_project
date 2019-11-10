package com.service;

import com.dao.OrderRecordDao;
import com.entity.OrderRecord;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author  豪
 */
@Service
public class OrderRecordServiceImpl implements IOrderRecordService {

    @Autowired
    private OrderRecordDao recordDao;

    @Override
    public List<OrderRecord> getAllList() {
        PageHelper.startPage(1,10);
        Page<OrderRecord> recordList = recordDao.findAllList();
        return recordList.getResult();
    }

    @Override
    public void insertEntity(OrderRecord orderRecord) {
        recordDao.insertEntity(orderRecord);
    }
}
