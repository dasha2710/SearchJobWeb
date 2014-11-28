/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sendingEmail;


import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Daryna_Ragimova
 */
public class SendingEmail {
    public static boolean sendEmail(String email, String msgBody){
        try {

            final String username = "businkadasha@gmail.com";
            final String password = "Dasha2710";
            
            Properties props = System.getProperties();
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "587");

            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });
            Transport transport = session.getTransport();
            try {
                Message msg = new MimeMessage(session);
                msg.setFrom(new InternetAddress(username));
                msg.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
                msg.setSubject("JOBBER");
                msg.setText(msgBody);
                transport.send(msg);
            }catch(SendFailedException e){
                Logger.getLogger(SendingEmail.class.getName()).log(Level.SEVERE, null,e);
                return false;
            } catch (AddressException e) {
                Logger.getLogger(SendingEmail.class.getName()).log(Level.SEVERE, null,e);
                return false;
            } catch (MessagingException e) {
                Logger.getLogger(SendingEmail.class.getName()).log(Level.SEVERE, null, e);
                return false;
            }
            
    }catch (NoSuchProviderException ex) {
            Logger.getLogger(SendingEmail.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
}
