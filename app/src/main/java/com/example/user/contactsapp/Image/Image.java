package com.example.user.contactsapp.Image;

/**
 * Created by User on 7/19/2017.
 */

public class Image {

    private int id;
    private String path;
    private String description;
    private long ownerId;

    public int getId() {
        return id;
    }

    public Image(int id, String path, String description, int ownerId) {
        this.id = id;
        this.path = path;
        this.description = description;
        this.ownerId = ownerId;

    }

    public Image(String path, String description, long ownerId) {
        this.path = path;
        this.description = description;
        this.ownerId = ownerId;
    }
    public Image(String path ,String description) {
        this.path = path;
        this.description = description;

    }
    public Image() {

    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }
}
