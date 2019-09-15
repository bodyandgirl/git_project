package com.mapper;

import com.entry.Permission;
import com.entry.Role;
import com.entry.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    List<Role> getRoleByUid(User u);

    List<Permission> getPremission(User r);

    User getOne(User u);
}
