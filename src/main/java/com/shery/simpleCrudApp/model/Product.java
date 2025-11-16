package com.shery.simpleCrudApp.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;


@Entity // To mark this as JPA bean
@Data // to create all setters, getters, toString(), hashCode() and a constructor with all params
@AllArgsConstructor // it also creates all arg constructor with some more features
@NoArgsConstructor // create one no-arg constructor (needed for different purposes)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String description;
    private String brand;
    private BigDecimal price;
    private String category;

//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    // handled at frontend
    private Date releaseDate;
    private int stockQuantity;
    private boolean productAvailable;

    private String imageName;
    private String imageType;
    // Large object data
    @Lob
    private byte[] imageData;

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }
}
