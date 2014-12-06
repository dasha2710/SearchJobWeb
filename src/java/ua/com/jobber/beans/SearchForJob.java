/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.com.jobber.beans;

import ua.com.jobber.entities.Applicant;
import ua.com.jobber.entities.Vacancy;
import java.io.Serializable;
import java.util.ResourceBundle;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.LazyDataModel;
import ua.com.jobber.sendingEmail.SendingEmail;

/**
 *
 * @author Daryna_Ragimova
 */
@Named
@SessionScoped
public class SearchForJob implements Serializable {

    @Inject
    private LazyDataModel<Vacancy> lazyModel;
    @Inject
    private UserBean userBean;
    private Vacancy selectedVacancy;

    public void sendEmail(Vacancy selectedVacancy) {
        String applicantEmail = userBean.getUser().getEmail();
        String applicantName = userBean.getUser().getApplicant().getName() + " " + userBean.getUser().getApplicant().getSurname();
        Applicant applicant = userBean.getUser().getApplicant();
        String email = selectedVacancy.getEmployer().getUser().getEmail();
        String msgBody = "Your vacancy " + selectedVacancy.getName()
                + " was chosen by the applicant " + applicantName
                + ", he has such education - " + applicant.getEducation()
                + ", he is " + applicant.getAge() + " years old"
                + ", his email is " + applicantEmail;
        if (applicant.getPhone() != null) {
            msgBody += ". You can call to the applicant by phone " + applicant.getPhone() + ".";
        }
        String text;
        ResourceBundle bundle = ResourceBundle.getBundle("resources.jsf.texts", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        if (SendingEmail.sendEmail(email, msgBody)) {
            text = bundle.getString("emailWasSend");
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            text, null));
        } else {
            text = bundle.getString("emailSendingFailed");
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            text, null));
        }

    }

    public Vacancy getSelectedVacancy() {
        return selectedVacancy;
    }

    public void setSelectedVacancy(Vacancy selectedVacancy) {
        this.selectedVacancy = selectedVacancy;
    }

    public LazyDataModel<Vacancy> getLazyModel() {
        return lazyModel;
    }

    public void setLazyModel(LazyDataModel<Vacancy> lazyModel) {
        this.lazyModel = lazyModel;
    }

    public UserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }

}
