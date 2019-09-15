package com.entry;

import java.io.Serializable;

public class Permission extends BaseEntry implements Serializable {
    private String permission;
    private String resources;

    @Override
    public String toString() {
        return "Permission{" +
                "permission='" + permission + '\'' +
                ", resources='" + resources + '\'' +
                ", createDate=" + createDate +
                ", id='" + id + '\'' +
                ", createUser='" + createUser + '\'' +
                ", modifyDate=" + modifyDate +
                ", modifyUser='" + modifyUser + '\'' +
                ", delFlag='" + delFlag + '\'' +
                '}';
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getResources() {
        return resources;
    }

    public void setResources(String resources) {
        this.resources = resources;
    }
}
