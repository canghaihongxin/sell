package com.budongfeng.sell.service.impl;

import com.budongfeng.sell.dataobject.SellerInfo;
import com.budongfeng.sell.enums.ResultEnum;
import com.budongfeng.sell.exception.SellException;
import com.budongfeng.sell.repository.SellerInfoRepository;
import com.budongfeng.sell.service.SellerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerInfoServiceImpl implements SellerInfoService {

    @Autowired
    private SellerInfoRepository sellerInfoRepository;

    @Override
    public SellerInfo save(SellerInfo sellerInfo) {
        return sellerInfoRepository.save(sellerInfo);
    }

    @Override
    public SellerInfo findByOpenid(String openid) {
        return sellerInfoRepository.findByOpenid(openid);
    }

    @Override
    public SellerInfo findByUsername(String username) {

        SellerInfo user = sellerInfoRepository.findByUsername(username);
        if(user==null){
            throw new SellException(ResultEnum.USERNAME_NOT_EXIST);
        }
        return user;
    }
}
