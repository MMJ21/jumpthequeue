package com.devonfw.application.jtqj.visitormanagement.dataaccess.api;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.devonfw.application.jtqj.general.common.api.validation.EmailExtended;
import com.devonfw.application.jtqj.general.common.api.validation.Phone;
import com.devonfw.application.jtqj.general.dataaccess.api.ApplicationPersistenceEntity;
import com.devonfw.application.jtqj.visitormanagement.common.api.Visitor;

/**
 * TODO marcosma This type ...
 */
@Entity
@Table(name = "Visitor")
public class VisitorEntity extends ApplicationPersistenceEntity implements Visitor {

  @NotNull
  @EmailExtended
  private String username;

  @NotNull
  private String name;

  @NotNull
  @Phone
  private String phoneNumber;

  private String password;

  private Boolean acceptedCommercial;

  private Boolean acceptedTerms;

  private Boolean userType;

  private static final long serialVersionUID = 1L;

  /**
   * @return username
   */
  @Override
  public String getUsername() {

    return this.username;
  }

  /**
   * @param username new value of {@link #getusername}.
   */
  @Override
  public void setUsername(String username) {

    this.username = username;
  }

  /**
   * @return name
   */
  @Override
  public String getName() {

    return this.name;
  }

  /**
   * @param name new value of {@link #getname}.
   */
  @Override
  public void setName(String name) {

    this.name = name;
  }

  /**
   * @return phoneNumber
   */
  @Override
  public String getPhoneNumber() {

    return this.phoneNumber;
  }

  /**
   * @param phoneNumber new value of {@link #getphoneNumber}.
   */
  @Override
  public void setPhoneNumber(String phoneNumber) {

    this.phoneNumber = phoneNumber;
  }

  /**
   * @return password
   */
  @Override
  public String getPassword() {

    return this.password;
  }

  /**
   * @param password new value of {@link #getpassword}.
   */
  @Override
  public void setPassword(String password) {

    this.password = password;
  }

  /**
   * @return acceptedCommercial
   */
  @Override
  public Boolean getAcceptedCommercial() {

    return this.acceptedCommercial;
  }

  /**
   * @param acceptedCommercial new value of {@link #getacceptedCommercial}.
   */
  @Override
  public void setAcceptedCommercial(Boolean acceptedCommercial) {

    this.acceptedCommercial = acceptedCommercial;
  }

  /**
   * @return acceptedTerms
   */
  @Override
  public Boolean getAcceptedTerms() {

    return this.acceptedTerms;
  }

  /**
   * @param acceptedTerms new value of {@link #getacceptedTerms}.
   */
  @Override
  public void setAcceptedTerms(Boolean acceptedTerms) {

    this.acceptedTerms = acceptedTerms;
  }

  /**
   * @return userType
   */
  @Override
  public Boolean getUserType() {

    return this.userType;
  }

  /**
   * @param userType new value of {@link #getuserType}.
   */
  @Override
  public void setUserType(Boolean userType) {

    this.userType = userType;
  }

}
