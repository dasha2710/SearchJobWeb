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
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Daryna_Ragimova
 */
@Entity
@Table(name = "CV")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cv.findAll", query = "SELECT c FROM Cv c"),
    @NamedQuery(name = "Cv.findByIdcv", query = "SELECT c FROM Cv c WHERE c.idcv = :idcv"),
    @NamedQuery(name = "Cv.findByVacancy", query = "SELECT c FROM Cv c WHERE c.vacancy = :vacancy"),
    @NamedQuery(name = "Cv.findBySalary", query = "SELECT c FROM Cv c WHERE c.salary = :salary"),
    @NamedQuery(name = "Cv.findBySkills", query = "SELECT c FROM Cv c WHERE c.skills = :skills"),
    @NamedQuery(name = "Cv.findByExperience", query = "SELECT c FROM Cv c WHERE c.experience = :experience")})
public class Cv implements Serializable{
    @Id
    @NotNull
    @Column(name = "IDCV")
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="seq")
    @SequenceGenerator(name="seq", sequenceName="CV_SEQUENCE", initialValue=1)
    private Integer idcv;
    @NotNull
    @Size(min = 1, max = 80, message = "{longVacancy}")
    @Column(name = "VACANCY")
    private String vacancy;
    @NotNull
    @Column(name = "SALARY")
    private double salary;
    @NotNull
    @Size(min = 1)//, max = 1500, message = "{longSkills}")
    @Column(name = "SKILLS")
    private String skills;
    @NotNull
    @Column(name = "EXPERIENCE")
    private int experience;
    @JoinColumn(name = "IDAPPLICANT", referencedColumnName = "IDAPPLICANT")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Applicant applicant;
    @NotNull
    @Column(name = "status")
    private boolean status;
    
    public Cv() {
    }

    public Cv(String vacancy, double salary, String skills, int experience) {
        this.vacancy = vacancy;
        this.salary = salary;
        this.skills = skills;
        this.experience = experience;
    }
    
    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
   

    public Integer getIdcv() {
        return idcv;
    }

    public void setIdcv(Integer idcv) {
        this.idcv = idcv;
    }

    public String getVacancy() {
        return vacancy;
    }

    public void setVacancy(String vacancy) {
        this.vacancy = vacancy;
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

    public Applicant getApplicant() {
        return applicant;
    }

    public void setApplicant(Applicant idapplicant) {
        this.applicant = idapplicant;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcv != null ? idcv.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cv)) {
            return false;
        }
        Cv other = (Cv) object;
        if ((this.idcv == null && other.idcv != null) || (this.idcv != null && !this.idcv.equals(other.idcv))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Cv[ idcv=" + idcv + " ]";
    }
    
}
