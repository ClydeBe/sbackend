package com.thewheel.sawatu.database.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Address implements Serializable {

    @Column(name = "number")
    private String number;

    @Column(name = "street")
    private String street;

    @Column(name = "zipcode")
    private int zipCode;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;
}
