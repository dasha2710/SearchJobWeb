/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name = "EMPLOYER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Employer.findAll", query = "SELECT e FROM Employer e"),
    @NamedQuery(name = "Employer.findByIdemployer", query = "SELECT e FROM Employer e WHERE e.idemployer = :idemployer"),
    @NamedQuery(name = "Employer.findByName", query = "SELECT e FROM Employer e WHERE e.name = :name"),
    @NamedQuery(name = "Employer.findByDescription", query = "SELECT e FROM Employer e WHERE e.description = :description"),
    @NamedQuery(name = "Employer.findByUserId", query = "SELECT e FROM Employer e WHERE e.user = :iduser")})
public class Employer implements Serializable{
    @Id
    @NotNull
    @Column(name = "IDEMPLOYER")
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="seq")
    @SequenceGenerator(name="seq", sequenceName="EMPLOYER_SEQUENCE", initialValue=1)
    private Integer idemployer;
    @NotNull
    @Size(min = 1, max = 50, message = "{longCompany}")
    @Column(name = "NAME")
    private String name;
    @Size(min = 1)//, max = 500, message = "{longDescription}")
    @Column(name = "DESCRIPTION")
    private String description;
    @JoinColumn(name = "IDUSER", referencedColumnName = "IDUSER")
    @OneToOne
    private User user;
    @Pattern(regexp="^$|^[0-9]{6,20}$", message="{invalidPhone}")
    private String phone;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employer", fetch = FetchType.EAGER)
    private Set<Vacancy> vacancies = new HashSet<Vacancy>();

    public Employer() {
        this.vacancies = new HashSet<Vacancy>();
    }

    public Integer getIdemployer() {
        return idemployer;
    }

    public void setIdemployer(Integer idemployer) {
        this.idemployer = idemployer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description.replace("\r", "");
    }

    public User getUser() {
        return user;
    }

    public void setUser(User iduser) {
        this.user = iduser;
    }

 
    public Set<Vacancy> getVacancies() {
        return vacancies;
    }

    public void setVacancies(Set<Vacancy> vacancySet) {
        this.vacancies = vacancySet;
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
        hash += (idemployer != null ? idemployer.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Employer)) {
            return false;
        }
        Employer other = (Employer) object;
        if ((this.idemployer == null && other.idemployer != null) || (this.idemployer != null && !this.idemployer.equals(other.idemployer))) {
            return false;
        }
        if (this.name.equals(other.name) && this.description.equals(other.description)
                && ((this.phone == null && other.phone == null) || this.phone.equals(other.phone))){
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return name;
    }
    
}
