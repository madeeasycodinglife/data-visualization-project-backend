package com.madeeasy.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Trend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer endYear;
    private Double cityLng;
    private Double cityLat;
    private Integer intensity;
    private String sector;
    private String topic;
    @Column(length = 1000) // Adjust the length as per your requirements
    private String insight;
    private String swot;
    private String url;
    private String region;
    private Integer startYear;
    private String impact;
    private Date added;
    private Date published;
    private String city;
    private String country;
    private Integer relevance;
    private String pestle;
    private String source;
    @Column(length = 1000) // Adjust the length as per your requirements
    private String title;
    private Integer likelihood;

}
