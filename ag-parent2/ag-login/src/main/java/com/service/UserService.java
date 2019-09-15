package com.service;

import com.LoginBootStrap;
import com.entry.Permission;
import com.entry.Role;
import com.entry.User;
import com.mapper.UserMapper;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void login(User user){
        List<Role> res1 = userMapper.getRoleByUid(user);
        List<Permission> res2 = userMapper.getPremission(user);
        System.out.println(res1);
        System.out.println(res2);
    }

    public List<Role> getRoles (User u){
        return userMapper.getRoleByUid(u);
    }

    public List<Permission> getPermission(User u){
        return userMapper.getPremission(u);
    }

    public User getOne(User u){
        return userMapper.getOne(u);
    }
}
