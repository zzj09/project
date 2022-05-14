package com.dao;

import com.entity.User;

public interface UserDao {
    public Boolean add(User user);
    public Boolean query(User user);
    public Boolean checkUserName(String username);
    public Boolean update(User user,String newpassword,String newpassword2);
    public User findUserByUserName(String username);
}
