package org.agoncal.fascicle.jpa.managing.ex02;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author Antonio Goncalves
 * http://www.antoniogoncalves.org
 * --
 */
// tag::adocsnippet[]
@Entity
public class Address {

  @Id
  @GeneratedValue
  private Long id;
  private String street1;
  private String city;
  private String zipcode;
  private String country;

  // Constructors, getters, setters
  // tag::adocskip[]

  public Address() {
  }

  public Address(String street1, String city, String zipcode, String country) {
    this.street1 = street1;
    this.city = city;
    this.zipcode = zipcode;
    this.country = country;
  }

  // ======================================
  // =          Getters & Setters         =
  // ======================================

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getStreet1() {
    return street1;
  }

  public void setStreet1(String street1) {
    this.street1 = street1;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getZipcode() {
    return zipcode;
  }

  public void setZipcode(String zipcode) {
    this.zipcode = zipcode;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }
  // end::adocskip[]
}
// end::adocsnippet[]
