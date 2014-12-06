/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ua.com.jobber.util;

import org.jboss.crypto.CryptoUtil;
/**
 *
 * @author Daryna_Ragimova
 */


public class Encrypt {

 public static String encryptSHA256(String toEncrypt) {
  String hash = CryptoUtil.createPasswordHash("SHA-256", CryptoUtil.BASE64_ENCODING, "UTF-8", null, toEncrypt);
  return hash;
 }

}
    

