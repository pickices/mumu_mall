package com.liuxinchi.mumu_mall.service.impl;

import com.liuxinchi.mumu_mall.exception.MumuMallException;
import com.liuxinchi.mumu_mall.exception.MumuMallExceptionEnum;
import com.liuxinchi.mumu_mall.model.dao.UserMapper;
import com.liuxinchi.mumu_mall.model.pojo.User;
import com.liuxinchi.mumu_mall.service.UserService;
import com.liuxinchi.mumu_mall.util.Md5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

@Service
public class UserSerivceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public void register(String userName, String password) throws MumuMallException {
        if(userMapper.selectByName(userName)!=null){
            throw new MumuMallException(MumuMallExceptionEnum.NAME_EXISTED);
        }

        User user = new User();
        user.setUsername(userName);
        try {
            user.setPassword(Md5Utils.getMd5Str(password));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        if(userMapper.insertSelective(user)==0){
            throw new MumuMallException(MumuMallExceptionEnum.INSERT_FAILED);
        }

    }

    @Override
    public User login(String userName, String password) throws MumuMallException {
        String md5Password = null;
        try {
            md5Password = Md5Utils.getMd5Str(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        User user = userMapper.selectByNameAndPwd(userName, md5Password);

        if(user==null){
            throw new MumuMallException(MumuMallExceptionEnum.WRONG_PASSWORD);
        }

        return user;

    }

    @Override
    public void updateUser(User user) throws MumuMallException {
        if(userMapper.updateByPrimaryKeySelective(user)!=1){
            throw new MumuMallException(MumuMallExceptionEnum.UPDATE_FAILED);
        }
    }

    @Override
    public boolean checkAdmin(User user){
        return user.getRole().equals(2);
    }
}
