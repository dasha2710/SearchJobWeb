/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;

import java.io.Serializable;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Daryna_Ragimova
 */
@Entity
@Table(name = "VACANCY")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vacancy.findAll", query = "SELECT v FROM Vacancy v"),
    @NamedQuery(name = "Vacancy.findByIdvacancy", query = "SELECT v FROM Vacancy v WHERE v.idvacancy = :idvacancy"),
    @NamedQuery(name = "Vacancy.findByName", query = "SELECT v FROM Vacancy v WHERE v.name = :name"),
    @NamedQuery(name = "Vacancy.findBySalary", query = "SELECT v FROM Vacancy v WHERE v.salary = :salary"),
    @NamedQuery(name = "Vacancy.findBySkills", query = "SELECT v FROM Vacancy v WHERE v.skills = :skills"),
    @NamedQuery(name = "Vacancy.findByExperience", query = "SELECT v FROM Vacancy v WHERE v.experience = :experience"),
    @NamedQuery(name = "Vacancy.findByEducation", query = "SELECT v FROM Vacancy v WHERE v.education = :education")})
public class Vacancy implements Serializable {
    @Id
    @NotNull
    @Column(name = "IDVACANCY")
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="seq")
    @SequenceGenerator(name="seq", sequenceName="VACANCY_SEQUENCE", initialValue=1)
    private Integer idvacancy;
    @NotNull
    @Size(min = 1, max = 80, message = "{longVacancy}")
    @Column(name = "NAME")
    private String name;
    @NotNull
    @Column(name = "SALARY")
    private double salary;
    @Size(min = 1) //max = 1500, message = "{longSkills}")
    @Column(name = "SKILLS")
    private String skills;
    @NotNull
    @Column(name = "EXPERIENCE")
    private int experience;
    @NotNull
    @Size(min = 1)//, max = 500, message = "{longEducation}")
    @Column(name = "EDUCATION")
    private String education;
    @NotNull
    @Column(name = "status")
    private boolean status;
    @JoinColumn(name = "IDCITY", referencedColumnName = "IDCITY")
    @ManyToOne
    private City city;
    @JoinColumn(name = "IDEMPLOYER", referencedColumnName = "IDEMPLOYER")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Employer employer;

    public Vacancy() {
    }

    
    public Vacancy(Integer idvacancy, String name, double salary, int experience, String education) {
        this.name = name;
        this.salary = salary;
        this.experience = experience;
        this.education = education;
    }

    public Integer getIdvacancy() {
        return idvacancy;
    }

    public void setIdvacancy(Integer idvacancy) {
        this.idvacancy = idvacancy;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills.replace("\r", "");
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education.replace("\r", "");
    }

    @XmlTransient
    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Employer getEmployer() {
        return employer;
    }

    public void setEmployer(Employer idemployer) {
        this.employer = idemployer;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idvacancy != null ? idvacancy.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vacancy)) {
            return false;
        }
        Vacancy other = (Vacancy) object;
        if ((this.idvacancy == null && other.idvacancy != null) || (this.idvacancy != null && !this.idvacancy.equals(other.idvacancy))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Vacancy[ idvacancy=" + idvacancy + " ]";
    }
    
}
