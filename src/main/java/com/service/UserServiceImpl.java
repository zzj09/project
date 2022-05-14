package com.service;

import com.dao.UserDao;
import com.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public Boolean add(User user) {
        return userDao.add(user);
    }

    @Override
    public Boolean query(User user) {
        return userDao.query(user);
    }

    @Override
    public Boolean checkUserName(String username){
        return userDao.checkUserName(username);
    }

    @Override
    public Boolean update(User user, String newpassword, String newpassword2) {
        if (userDao.query(user)){
            return userDao.update(user,newpassword,newpassword2);
        }else{
            return false;
        }
    }

    @Override
    public User findUserByUserName(String username) {
        return userDao.findUserByUserName(username);
    }
}
