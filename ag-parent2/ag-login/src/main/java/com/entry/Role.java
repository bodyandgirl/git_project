package com.entry;

import java.io.Serializable;

public class Role extends BaseEntry implements Serializable {
    private String roleName;

    @Override
    public String toString() {
        return "Role{" +
                "roleName='" + roleName + '\'' +
                ", createDate=" + createDate +
                ", id='" + id + '\'' +
                ", createUser='" + createUser + '\'' +
                ", modifyDate=" + modifyDate +
                ", modifyUser='" + modifyUser + '\'' +
                ", delFlag='" + delFlag + '\'' +
                '}';
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
