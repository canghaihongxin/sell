package com.budongfeng.sell.repository;

import com.budongfeng.sell.dataobject.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerInfoRepository extends JpaRepository<SellerInfo,String> {


    SellerInfo save(SellerInfo sellerInfo);

    SellerInfo findByOpenid(String opendId);

    SellerInfo findByUsername(String username);

}
