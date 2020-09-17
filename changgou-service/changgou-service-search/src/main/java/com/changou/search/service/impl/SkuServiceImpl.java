package com.changou.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.changgou.goods.feign.SkuFeign;
import com.changgou.goods.pojo.Sku;
import com.changgou.search.pojo.SkuInfo;
import com.changou.search.dao.ESManagerMapper;
import com.changou.search.service.SkuService;
import entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @auther wkk
 * @date 2020/6/22 10:35
 */
@Service
public class SkuServiceImpl implements SkuService {

    @Autowired
    private SkuFeign skuFeign;

    @Autowired
    private ESManagerMapper esManagerMapper;

    @Override
    public void importDate() {

        List<Sku> listResult = skuFeign.findAll();

        List<SkuInfo> skuInfos = JSON.parseArray(JSON.toJSONString(listResult), SkuInfo.class);

        esManagerMapper.saveAll(skuInfos);

    }
}
