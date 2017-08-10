package com.example.e_tecklaptop.testproject.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by E-Teck Laptop on 6/20/2017.
 */

public class AddsItem {

    private String address;
    private String description;
    private String image;
    private String price;
    private String baths;
    private String beds;
    private String area;
    private String latitude;
    private String logitude;
    private ArrayList<ArrayList<String>> allPhotos = new ArrayList<>();
    private String style;
    private String property_type;
    private String community;
    private String country;
    private String mlsID;
    private String lotSize;
    private String yearBuilt;
    private int id;
    private String insertAllImages;

    public String getInsertAllImages() {
        return insertAllImages;
    }

    public void setInsertAllImages(String insertAllImages) {
        this.insertAllImages = insertAllImages;
    }

    public ArrayList<ArrayList<String>> getAllPhotos() {
        return allPhotos;
    }

    public void setAllPhotos(ArrayList<ArrayList<String>> allPhotos) {
        this.allPhotos = allPhotos;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getYearBuilt() {
        return yearBuilt;
    }

    public void setYearBuilt(String yearBuilt) {
        this.yearBuilt = yearBuilt;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getProperty_type() {
        return property_type;
    }

    public void setProperty_type(String property_type) {
        this.property_type = property_type;
    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getMlsID() {
        return mlsID;
    }

    public void setMlsID(String mlsID) {
        this.mlsID = mlsID;
    }

    public String getLotSize() {
        return lotSize;
    }

    public void setLotSize(String lotSize) {
        this.lotSize = lotSize;
    }


    public String getBaths() {
        return baths;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLogitude() {
        return logitude;
    }

    public void setLogitude(String logitude) {
        this.logitude = logitude;
    }

    public void setBaths(String baths) {
        this.baths = baths;
    }

    public String getBeds() {
        return beds;
    }

    public void setBeds(String beds) {
        this.beds = beds;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }




    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
