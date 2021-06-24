package com.liuxinchi.mumu_mall.service.impl;

import com.liuxinchi.mumu_mall.exception.MumuMallException;
import com.liuxinchi.mumu_mall.exception.MumuMallExceptionEnum;
import com.liuxinchi.mumu_mall.model.dao.UserMapper;
import com.liuxinchi.mumu_mall.model.pojo.User;
import com.liuxinchi.mumu_mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserSerivceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public User test() {
        return userMapper.selectByPrimaryKey(1);
    }

    @Override
    public void register(String userName, String password) throws MumuMallException {
        if(userMapper.selectByName(userName)!=null){
            throw new MumuMallException(MumuMallExceptionEnum.NAME_EXISTED);
        }

        User user = new User();
        user.setUsername(userName);
        user.setPassword(password);
        if(userMapper.insertSelective(user)==0){
            throw new MumuMallException(MumuMallExceptionEnum.INSERT_FAILED);
        }

    }
}
