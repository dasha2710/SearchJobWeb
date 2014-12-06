/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ua.com.jobber.util;

import java.util.Random;
import org.apache.commons.lang3.RandomStringUtils;

/**
 *
 * @author Daryna_Ragimova
 */
public class PasswordGenerator {
    public static String generate(){
        int count = 6 + new Random().nextInt(12);
        return RandomStringUtils.randomAlphanumeric(count);
    }
}
