package com.service;

import com.entity.User;

public interface UserService {
    public Boolean add(User user);
    public Boolean query(User user);
    public Boolean checkUserName(String username);
    public Boolean update(User user,String newpassword,String newpassword2);
    User findUserByUserName(String username);
}
