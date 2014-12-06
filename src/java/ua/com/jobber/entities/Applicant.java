/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ua.com.jobber.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;


/**
 *
 * @author Daryna_Ragimova
 */
@Entity
@Table(name = "APPLICANT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Applicant.findAll", query = "SELECT a FROM Applicant a"),
    @NamedQuery(name = "Applicant.findByIdapplicant", query = "SELECT a FROM Applicant a WHERE a.idapplicant = :idapplicant"),
    @NamedQuery(name = "Applicant.findByName", query = "SELECT a FROM Applicant a WHERE a.name = :name"),
    @NamedQuery(name = "Applicant.findBySurname", query = "SELECT a FROM Applicant a WHERE a.surname = :surname"),
    @NamedQuery(name = "Applicant.findByAge", query = "SELECT a FROM Applicant a WHERE a.age = :age"),
    @NamedQuery(name = "Applicant.findByEducation", query = "SELECT a FROM Applicant a WHERE a.education = :education"),
    @NamedQuery(name = "Applicant.findByUserId", query = "SELECT a FROM Applicant a WHERE a.user = :iduser")})
public class Applicant implements Serializable{
    @Id
    @NotNull
    @Column(name = "IDAPPLICANT")
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="seq")
    @SequenceGenerator(name="seq", sequenceName="APPLICANT_SEQUENCE", initialValue=1)
    private Integer idapplicant;
    @NotNull
    @Size(min = 1, max = 80, message = "{longName}")
    @Column(name = "NAME")
    private String name;
    @NotNull
    @Size(min = 1, max = 80, message = "{longSurname}")
    @Column(name = "SURNAME")
    private String surname;
    @NotNull
    @Column(name = "AGE")
    private int age;
    @NotNull
    @Size(min = 1)//, max = 500, message = "{longEducation}")
    @Column(name = "EDUCATION")
    private String education;
    @JoinColumn(name = "IDUSER", referencedColumnName = "IDUSER")
    @OneToOne
    private User user;
    @JoinColumn(name = "IDCITY", referencedColumnName = "IDCITY")
    @ManyToOne
    private City city;
    @Pattern(regexp="^$|^[0-9]{6,20}$", message="{invalidPhone}")
    private String phone;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "applicant", fetch = FetchType.EAGER)
    private Set<Cv> cvs = new HashSet<Cv>();

    public Applicant() {
    }

    public Applicant(String name, String surname, int age, String education) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.education = education;
    }

    public Integer getIdapplicant() {
        return idapplicant;
    }

    public void setIdapplicant(Integer idapplicant) {
        this.idapplicant = idapplicant;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education.replace("\r", "");
    }

    public User getUser() {
        return user;
    }

    public void setUser(User iduser) {
        this.user = iduser;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    
    public Set<Cv> getCvs() {
        return cvs;
    }

    public void setCvs(Set<Cv> cvSet) {
        this.cvs = cvSet;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idapplicant != null ? idapplicant.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Applicant)) {
            return false;
        }
        Applicant other = (Applicant) object;
        if ((this.idapplicant == null && other.idapplicant != null) || (this.idapplicant != null && !this.idapplicant.equals(other.idapplicant))) {
            return false;
        }
        if (this.age == other.age && this.education.equals(other.education) 
                && this.name.equals(other.name) && this.surname.equals(other.surname) 
                && this.phone.equals(other.phone) && this.city.getName().equals(other.city.getName()))
            return true;
        else
            return false;
    }

    @Override
    public String toString() {
        return "entities.Applicant[ idapplicant=" + idapplicant + " ]";
    }
    
}
