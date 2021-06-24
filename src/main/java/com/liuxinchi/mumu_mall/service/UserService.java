package com.liuxinchi.mumu_mall.service;

import com.liuxinchi.mumu_mall.exception.MumuMallException;
import com.liuxinchi.mumu_mall.model.pojo.User;

public interface UserService {

    User test();

    void register(String userName, String password) throws MumuMallException;

}
