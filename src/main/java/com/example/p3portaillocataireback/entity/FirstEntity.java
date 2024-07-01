package com.example.p3portaillocataireback.entity;

public class FirstEntity {
    private String name;
    private String description;

    public FirstEntity() {
    }

    public FirstEntity(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public FirstEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public FirstEntity setDescription(String description) {
        this.description = description;
        return this;
    }
}
