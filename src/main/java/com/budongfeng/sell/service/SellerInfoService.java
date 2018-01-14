package com.budongfeng.sell.service;

import com.budongfeng.sell.dataobject.SellerInfo;

public interface SellerInfoService {

    SellerInfo save(SellerInfo sellerInfo);

    SellerInfo findByOpenid(String openid);

    SellerInfo findByUsername(String username);
}
