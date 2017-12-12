package model;

import java.util.Map;

public class Image {
    private String name;
    private String _id;
    private String folder;
    private String url;
    private int height;
    private int width;

    public Image(String name, String _id, String folder, String url, int height, int width) {
        this.name = name;
        this._id = _id;
        this.folder = folder;
        this.url = url;
        this.height = height;
        this.width = width;
    }

    public Image(Map<String, Object> map){
        this.name = (String) map.get("name");
        this.folder = (String) map.get("folder");
        this.url = (String) map.get("url");
        this.height = (Integer) map.get("height");
        this.width = (Integer) map.get("width");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public String toString() {
        return "Image{" +
                "name='" + name + '\'' +
                ", _id='" + _id + '\'' +
                ", folder='" + folder + '\'' +
                ", url='" + url + '\'' +
                ", height=" + height +
                ", width=" + width +
                '}';
    }
}
