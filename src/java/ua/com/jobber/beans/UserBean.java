/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.com.jobber.beans;

import ua.com.jobber.dao.ApplicantDAO;
import ua.com.jobber.dao.CvDAO;
import ua.com.jobber.dao.EmployerDAO;
import ua.com.jobber.dao.UserDAO;
import ua.com.jobber.dao.VacancyDAO;
import ua.com.jobber.entities.Applicant;
import ua.com.jobber.entities.Cv;
import ua.com.jobber.entities.Employer;
import ua.com.jobber.entities.Role;
import ua.com.jobber.entities.User;
import ua.com.jobber.entities.Vacancy;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.ResourceBundle;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.Pattern;
import org.primefaces.event.FlowEvent;

/**
 *
 * @author Daryna_Ragimova
 */
@Named("userBean")
@SessionScoped
public class UserBean implements Serializable {

    @Inject
    private UserDAO userDAO;
    @Inject
    private EmployerDAO employerDAO;
    @Inject
    private ApplicantDAO applicantDAO;
    @Inject
    private CvDAO cvDAO;
    @Inject
    private VacancyDAO vacancyDAO;

    private User user;
    @Pattern(regexp = ".{6,1000}$", message = "{invalidPassword}")
    private String passwordConfirmation;
    @Pattern(regexp = ".{6,1000}$", message = "{invalidPassword}")
    private String oldPassword;
    @Pattern(regexp = ".{6,1000}$", message = "{invalidPassword}")
    private String newPassword;
    private Cv cv;
    private Cv selectedCv;
    private Vacancy selectedVacancy;
    private Vacancy vacancy;
    private Employer employer;
    private Applicant applicant;

    /**
     * Creates a new instance of UserBean
     */
    public UserBean() {
        this.user = new User();
        this.applicant = new Applicant();
        this.employer = new Employer();
        this.cv = new Cv();
        this.vacancy = new Vacancy();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public Cv getCv() {
        return cv;
    }

    public void setCv(Cv cv) {
        this.cv = cv;
    }

    public Vacancy getVacancy() {
        return vacancy;
    }

    public void setVacancy(Vacancy vacancy) {
        this.vacancy = vacancy;
    }

    public Cv getSelectedCv() {
        return selectedCv;
    }

    public void setSelectedCv(Cv selectedCv) {
        this.selectedCv = selectedCv;
    }

    public Vacancy getSelectedVacancy() {
        return selectedVacancy;
    }

    public void setSelectedVacancy(Vacancy selectedVacancy) {
        this.selectedVacancy = selectedVacancy;
    }

    public Employer getEmployer() {
        return employer;
    }

    public void setEmployer(Employer employer) {
        this.employer = employer;
    }

    public Applicant getApplicant() {
        return applicant;
    }

    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
    }
    
    

    public void sendPassword() {
        User temp = userDAO.findUser(user.getEmail());
        if (temp == null) {
            ResourceBundle bundle = ResourceBundle.getBundle("resources.jsf.texts", FacesContext.getCurrentInstance().getViewRoot().getLocale());
            String text = bundle.getString("emailNotExist");
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            text, null));

        } else {
            user = temp;
            String currentPass = user.getPassword();
            String pass = ua.com.jobber.util.PasswordGenerator.generate();
            user.setPassword(ua.com.jobber.util.Encrypt.encryptSHA256(pass) + user.getSalt());
            if (ua.com.jobber.sendingEmail.SendingEmail.sendEmail(user.getEmail(), "New password is " + pass)) {
                userDAO.update(user);
                ResourceBundle bundle = ResourceBundle.getBundle("resources.jsf.texts", FacesContext.getCurrentInstance().getViewRoot().getLocale());
                String text = bundle.getString("passwordSent");
                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                text, null));
            } else {
                user.setPassword(currentPass);
                ResourceBundle bundle = ResourceBundle.getBundle("resources.jsf.texts", FacesContext.getCurrentInstance().getViewRoot().getLocale());
                String text = bundle.getString("emailSendingFailed");
                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                text, null));

            }
        }
    }
    
    public String changePassword(){
        oldPassword = ua.com.jobber.util.Encrypt.encryptSHA256(oldPassword + user.getSalt());
        if (!user.getPassword().equals(oldPassword)){
            ResourceBundle bundle = ResourceBundle.getBundle("resources.jsf.texts", FacesContext.getCurrentInstance().getViewRoot().getLocale());
                String text = bundle.getString("incorrectPassword");
                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                text, null));
                return "changePassword";
        }
        
        if (!newPassword.equals(passwordConfirmation)){
            ResourceBundle bundle = ResourceBundle.getBundle("resources.jsf.texts", FacesContext.getCurrentInstance().getViewRoot().getLocale());
            String text = bundle.getString("PasswordsDoesNotMatch");

            FacesContext.getCurrentInstance()
                    .addMessage(
                            null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                    text, null));
            return "changePassword";
        }
        newPassword = ua.com.jobber.util.Encrypt.encryptSHA256(newPassword + user.getSalt());
        user.setPassword(newPassword);
        userDAO.update(user);
        if (userIsEmployer())
            return "edit_personal_info_employer?faces-redirect=true";
        else
            return "edit_personal_info_applicant?faces-redirect=true";
            
    }
    public String cancelChangingPassword(){
        if (userIsEmployer())
            return "edit_personal_info_employer?faces-redirect=true";
        else
            return "edit_personal_info_applicant?faces-redirect=true";
            
    }

    public String logoutEvent() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        session.invalidate();
        if (isAuthenticated()) {
            try {
                getRequest().logout();
            } catch (ServletException ex) {
                Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return "login?faces-redirect=true";
    }

    public String onFlowProcess(FlowEvent event) {
        if (checkEmail() && checkPasswordConfirmation()) {
            return event.getNewStep();
        } else {
            return "register";
        }
    }

    public boolean checkPasswordConfirmation() {
        if (!user.getPassword().equals(passwordConfirmation)) {
            ResourceBundle bundle = ResourceBundle.getBundle("resources.jsf.texts", FacesContext.getCurrentInstance().getViewRoot().getLocale());
            String text = bundle.getString("PasswordsDoesNotMatch");

            FacesContext.getCurrentInstance()
                    .addMessage(
                            null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                    text, null));
            return false;
        } else {
            return true;
        }
    }

    public boolean checkEmail() {
        User userTemp = userDAO.findUser(user.getEmail());
        if (userTemp != null) {
            ResourceBundle bundle = ResourceBundle.getBundle("resources.jsf.texts", FacesContext.getCurrentInstance().getViewRoot().getLocale());
            String text = bundle.getString("emailExists");
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            text, null));
            return false;
        } else {
            return true;
        }
    }

    public String register() {
        String salt = Integer.toString(Calendar.getInstance().get(Calendar.MILLISECOND));
        try {
            
            user.setSalt(salt);
            user.setPassword(ua.com.jobber.util.Encrypt.encryptSHA256(passwordConfirmation + salt));
            userDAO.create(user);
        } catch (Exception e) {
            ResourceBundle bundle = ResourceBundle.getBundle("resources.jsf.texts", FacesContext.getCurrentInstance().getViewRoot().getLocale());
            String text = bundle.getString("updateSession");
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            text, null));
            Logger.getLogger(UserBean.class
                    .getName()).log(Level.SEVERE, null, e);
            return "register";
        }
        try {
            if (isAuthenticated()) {
                logout();
            }
            getRequest().login(user.getEmail(), passwordConfirmation + salt);
        } catch (ServletException ex) {
            Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return saveInformation();
    }

    public String login() {
        try {
            ExternalContext eContext = FacesContext.getCurrentInstance().getExternalContext();
            String targetURI = (String) eContext.getRequestMap().get(RequestDispatcher.FORWARD_REQUEST_URI);
            if (isAuthenticated()) {
                logout();
                
            }
            User userTemp = userDAO.findUser(user.getEmail());
            
            if (userTemp == null) {
                ResourceBundle bundle = ResourceBundle.getBundle("resources.jsf.texts", FacesContext.getCurrentInstance().getViewRoot().getLocale());
                String text = bundle.getString("IncorrectLoginPassword");
                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                text, null));
                return "login";
            } else {
                if (targetURI != null) {
                    try {
                        user = userTemp;
                        FacesContext.getCurrentInstance().getExternalContext().redirect(targetURI);
                        return null;

                    } catch (IOException ex) {
                        Logger.getLogger(UserBean.class
                                .getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    String enteredPass = user.getPassword();
                    getRequest().login(userTemp.getEmail(), user.getPassword() + userTemp.getSalt());
                    user = userTemp;
                    HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
                    session.setMaxInactiveInterval(1800);
                    
                    
                    if (user.getRole() == Role.APPLICANT) {
                        return "pages/edit_personal_info_applicant?faces-redirect=true";
                    } else {
                        return "pages/edit_personal_info_employer?faces-redirect=true";

                    }
                }
            }
        } catch (ServletException ex) {
            Logger.getLogger(UserBean.class
                    .getName()).log(Level.SEVERE, null, ex);
            ResourceBundle bundle = ResourceBundle.getBundle("resources.jsf.texts", FacesContext.getCurrentInstance().getViewRoot().getLocale());
            String text = bundle.getString("IncorrectLoginPassword");

            FacesContext.getCurrentInstance()
                    .addMessage(
                            null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                    text, null));

            return "login";
        }
        return null;
    }

    public boolean checkNumberOfCvs() {
        return user.getApplicant().getCvs().size() >= 5;
    }

    public boolean checkNumberOfVacancies() {
        return user.getEmployer().getVacancies().size() >= 20;
    }
    
     public String roleIsEmployer() {
        user = new User();
        employer = new Employer();
        user.setRole(Role.EMPLOYER);
        return "register?faces-redirect=true";
    }

    public String roleIsApplicant() {
        user = new User();
        applicant = new Applicant();
        user.setRole(Role.APPLICANT);
        return "register?faces-redirect=true";
    }

    public boolean userIsEmployer() {
        return user.getRole().equals(Role.EMPLOYER);
    }

    public boolean userIsApplicant() {
        return user.getRole().equals(Role.APPLICANT);
    }

    public String redirectToEdit() {
        if (user.getRole().equals(Role.APPLICANT)) {
            return "editionPersonalInfAppl?faces-redirect=true";
        }
        return "editionPersonalInfEmpl?faces-redirect=true";
    }

    public String redirectToMy() {
        if (user.getRole().equals(Role.APPLICANT)) {
            return "edit_personal_info_applicant?faces-redirect=true";
        }
        return "edit_personal_info_employer?faces-redirect=true";
    }

    public String redirectToSearch() {
        if (user.getRole().equals(Role.APPLICANT)) {
            return "search_for_applicant?faces-redirect=true";
        }
        return "search_for_employer?faces-redirect=true";
    }

    public boolean isAuthenticated() {
        return getRequest().getUserPrincipal() != null;
    }

    public void logout() throws ServletException {
        //FacesContext.getCurrentInstance().getExternalContext().invalidateSession();;
        if (isAuthenticated()) {
            getRequest().logout();
        }
    }

    public static HttpServletRequest getRequest() {
        Object request = FacesContext.getCurrentInstance().getExternalContext().getRequest();
        return (HttpServletRequest) request;
    }

    public String saveInformation() {
        if (user.getRole() == Role.EMPLOYER) {
            try {
                employer.setUser(user);
                user.setEmployer(employer);
                employerDAO.create(employer);

            } catch (Exception ex) {
                Logger.getLogger(UserBean.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            session.setMaxInactiveInterval(1800);
            return "pages/edit_personal_info_employer?faces-redirect=true";
        } else {
            try {
                applicant.setUser(user);
                user.setApplicant(applicant);
                applicantDAO.create(applicant);

            } catch (Exception ex) {
                Logger.getLogger(UserBean.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
            return "pages/edit_personal_info_applicant?faces-redirect=true";
        }
    }

    public String updateInformation() {
        if (user.getRole() == Role.EMPLOYER) {
            try {
                employerDAO.update(user.getEmployer());

            } catch (Exception ex) {
                Logger.getLogger(UserBean.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
            return "edit_personal_info_employer?faces-redirect=true";
        } else {
            applicantDAO.update(user.getApplicant());
            return "edit_personal_info_applicant?faces-redirect=true";
        }
    }

    public String cancelEditingApplicant() {
        user = userDAO.findUser(user.getEmail());
        return "edit_personal_info_applicant?faces-redirect=true";
    }

    public String cancelEditingEmployer() {
        user = userDAO.findUser(user.getEmail());
        return "edit_personal_info_employer?faces-redirect=true";
    }

    public String saveCV() {
        try {
            cv.setApplicant(user.getApplicant());
            cv.setStatus(true);
            cvDAO.create(cv);
            user.getApplicant().getCvs().add(cv);
            cv = new Cv();

        } catch (Exception ex) {
            Logger.getLogger(UserBean.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return "edit_personal_info_applicant?faces-redirect=true";
    }

    public String saveVacancy() {
        try {
            vacancy.setEmployer(user.getEmployer());
            vacancy.setStatus(true);
            vacancyDAO.create(vacancy);
            user.getEmployer().getVacancies().add(vacancy);
            vacancy = new Vacancy();

        } catch (Exception ex) {
            Logger.getLogger(UserBean.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return "edit_personal_info_employer?faces-redirect=true";
    }

    public String editCV() {
        try {
            cvDAO.update(selectedCv);

        } catch (Exception ex) {
            Logger.getLogger(UserBean.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return "edit_personal_info_applicant?faces-redirect=true";
    }

    public String editVacancy() {
        try {
            vacancyDAO.update(selectedVacancy);

        } catch (Exception ex) {
            Logger.getLogger(UserBean.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return "edit_personal_info_employer?faces-redirect=true";
    }
    
    public String redirectToEditCV(Cv selectedCV){
        this.selectedCv = selectedCV;
        return "editCV?faces-redirect=true";
    }
    
    public String redirectToEditVacancy(Vacancy vacancy){
        this.selectedVacancy = vacancy;
        return "editVacancy?faces-redirect=true";
    }

    public void changeStatusCv(Cv selectedCv) {
        this.selectedCv = selectedCv;
        try {
            if (selectedCv.isStatus()) {
                selectedCv.setStatus(false);
            } else {
                selectedCv.setStatus(true);
            }
            cvDAO.update(selectedCv);

        } catch (Exception ex) {
            Logger.getLogger(UserBean.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void changeStatusVacancy(Vacancy vacancy) {
        selectedVacancy = vacancy;
        try {
            if (selectedVacancy.isStatus()) {
                selectedVacancy.setStatus(false);
            } else {
                selectedVacancy.setStatus(true);
            }
            vacancyDAO.update(selectedVacancy);

        } catch (Exception ex) {
            Logger.getLogger(UserBean.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void deleteVacancy() {
        if (vacancyDAO.findVacancy(selectedVacancy.getIdvacancy()) != null) {
            try {
                vacancyDAO.delete(selectedVacancy);
                user.getEmployer().getVacancies().remove(selectedVacancy);
            } catch (Exception ex) {
                Logger.getLogger(UserBean.class
                        .getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public void deleteCV() {
        if (cvDAO.findCv(selectedCv.getIdcv()) != null) {
            try {
                cvDAO.delete(selectedCv);
                user.getApplicant().getCvs().remove(selectedCv);
            } catch (Exception ex) {
                Logger.getLogger(UserBean.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public List getListofVacancies() {
        return new ArrayList<Vacancy>(user.getEmployer().getVacancies());
    }

    public List getListofCVs() {
        return new ArrayList<Cv>(user.getApplicant().getCvs());
    }
}
