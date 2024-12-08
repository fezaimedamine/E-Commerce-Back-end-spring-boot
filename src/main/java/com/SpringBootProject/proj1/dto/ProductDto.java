package com.SpringBootProject.proj1.dto;


import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ProductDto {
    private long id;
    private String name;
    private Long price;
    private String description;
     
    private byte[] byteImg;
    private Long categoryId;
    private MultipartFile img;
    
}
