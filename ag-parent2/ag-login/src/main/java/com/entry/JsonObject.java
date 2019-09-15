package com.entry;

import java.io.Serializable;

public class JsonObject implements Serializable {
    private String name;
    private String nodeId;

    @Override
    public String toString() {
        return "JsonObject{" +
                "name='" + name + '\'' +
                ", nodeId='" + nodeId + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }
}
