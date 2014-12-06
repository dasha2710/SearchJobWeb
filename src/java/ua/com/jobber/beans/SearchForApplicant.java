/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.com.jobber.beans;

import ua.com.jobber.entities.Cv;
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
public class SearchForApplicant implements Serializable {

    @Inject
    private LazyDataModel<Cv> lazyModel;
    @Inject
    private UserBean userBean;
    private Cv selectedCV;

    public void sendEmail(Cv selectedCV) {
        String companyEmail = userBean.getUser().getEmail();
        String companyName = userBean.getUser().getEmployer().getName();
        String companyPhone = userBean.getUser().getEmployer().getPhone();
        String email = selectedCV.getApplicant().getUser().getEmail();
        String msgBody = "Your cv for the vacancy " + selectedCV.getVacancy()
                + " was choosen by the company " + companyName
                + ", its email is " + companyEmail + "."
                + " You can call to the company by phone " + companyPhone + ".";
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

    public Cv getSelectedCV() {
        return selectedCV;
    }

    public void setSelectedCV(Cv selectedCV) {
        this.selectedCV = selectedCV;
    }

    public LazyDataModel<Cv> getLazyModel() {
        return lazyModel;
    }

    public void setLazyModel(LazyDataModel<Cv> lazyModel) {
        this.lazyModel = lazyModel;
    }

    public UserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }

}
