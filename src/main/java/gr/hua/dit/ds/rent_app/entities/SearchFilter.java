package gr.hua.dit.ds.rent_app.entities;

import jakarta.persistence.Entity;

/*Class for search filter*/
public class SearchFilter {

    private String location;

    private Double minPrice;

    private Double maxPrice;

    private TypeOfProperty typeOfProperty;

    private Integer minArea;

    private Integer maxArea;

    private Integer bedrooms;

    private Boolean available;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public TypeOfProperty getTypeOfProperty() {
        return typeOfProperty;
    }

    public void setTypeOfProperty(TypeOfProperty typeOfProperty) {
        this.typeOfProperty = typeOfProperty;
    }

    public Integer getMinArea() {
        return minArea;
    }

    public void setMinArea(Integer minArea) {
        this.minArea = minArea;
    }

    public Integer getMaxArea() {
        return maxArea;
    }

    public void setMaxArea(Integer maxArea) {
        this.maxArea = maxArea;
    }

    public Integer getBedrooms() {
        return bedrooms;
    }

    public void setBedrooms(Integer bedrooms) {
        this.bedrooms = bedrooms;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public SearchFilter() {
    }

    public SearchFilter(String location, Double minPrice, Double maxPrice, TypeOfProperty typeOfProperty,
                        Integer minArea, Integer maxArea, Integer bedrooms, Boolean available) {
        this.location = location;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.typeOfProperty = typeOfProperty;
        this.minArea = minArea;
        this.maxArea = maxArea;
        this.bedrooms = bedrooms;
        this.available = available;
    }

    @Override
    public String toString() {
        return "SearchFilter{" +
                "location='" + location + '\'' +
                ", minPrice=" + minPrice +
                ", maxPrice=" + maxPrice +
                ", typeOfProperty=" + typeOfProperty +
                ", minArea=" + minArea +
                ", maxArea=" + maxArea +
                ", bedrooms=" + bedrooms +
                ", available=" + available +
                '}';
    }
}
