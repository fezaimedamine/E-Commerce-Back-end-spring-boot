package com.SpringBootProject.proj1.Entitys;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.SpringBootProject.proj1.dto.ProductDto;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Table(name = "product")
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  
    private long id;
    private String name;
    private Long price;
    private String description;
     
    @Lob
    private byte[] img;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "category_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Category category;

    public ProductDto getDto(){
        ProductDto productDto=new ProductDto();
        productDto.setId(id);
        productDto.setByteImg(img);
        productDto.setName(name);
        productDto.setPrice(price);
        productDto.setCategoryId(category.getId());
        productDto.setDescription(description);
        return productDto;
    }

    
}
