package com.liuxinchi.mumu_mall.service;

import com.liuxinchi.mumu_mall.exception.MumuMallException;
import com.liuxinchi.mumu_mall.model.pojo.User;

/**
 * @author 拾荒老冰棍
 */
public interface UserService {

    void register(String userName, String password) throws MumuMallException;

    User login(String userName, String password) throws MumuMallException;

    void updateUser(User user) throws MumuMallException;

    boolean checkAdmin(User user);
}
