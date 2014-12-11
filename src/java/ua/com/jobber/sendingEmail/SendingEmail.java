/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.com.jobber.sendingEmail;

import java.io.IOException;
import java.io.InputStream;
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

    public static boolean sendEmail(String email, String msgBody) {
        try {
            Properties props = new Properties();
            if (loadPropertiesFile(props)) {
                return false;
            }
            final String username = props.getProperty("username");
            final String password = props.getProperty("password");
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
            } catch (SendFailedException e) {
                Logger.getLogger(SendingEmail.class.getName()).log(Level.SEVERE, null, e);
                return false;
            } catch (AddressException e) {
                Logger.getLogger(SendingEmail.class.getName()).log(Level.SEVERE, null, e);
                return false;
            } catch (MessagingException e) {
                Logger.getLogger(SendingEmail.class.getName()).log(Level.SEVERE, null, e);
                return false;
            }

        } catch (NoSuchProviderException ex) {
            Logger.getLogger(SendingEmail.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    private static boolean loadPropertiesFile(Properties props) {
        InputStream input = null;
        try {
            String filename = "sendingEmail.properties";
            input = SendingEmail.class.getClassLoader().getResourceAsStream(filename);
            if (input == null) {
                System.out.println("Sorry, unable to find " + filename);
                return true;
            }
            //load a properties file from class path, inside static method
            props.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
}
