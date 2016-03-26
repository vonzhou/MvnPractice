package com.vonzhou.mvnbook.account.captcha;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by vonzhou on 16/3/25.
 */
public class AccountCaptchaServiceTest {
    private AccountCaptchaService accountCaptchaService;
    @Before
    public void prepare() throws Exception{
        ApplicationContext ctx = new ClassPathXmlApplicationContext("account-captcha.xml");
        accountCaptchaService = (AccountCaptchaService) ctx.getBean("accountCaptchaService");
    }

    @Test
    public void testGenerateCaptcha() throws  Exception{
        String captchaKey = accountCaptchaService.generateCaptchaKey();
        assertNotNull(captchaKey);

        byte[] captchaImage = accountCaptchaService.generateCaptchaImage(captchaKey);
        assertTrue(captchaImage.length > 0);

        File image = new File("target/" + captchaKey + ".jpg");
        OutputStream output = null;
        try{
            output = new FileOutputStream(image);
            output.write(captchaImage);
        }finally {
            if(output != null)
                output.close();
        }

        assertTrue(image.exists() && image.length() >0);
    }

    @Test
    public  void testValidateCaptchaCorrect() throws Exception{
        List<String> preDefinedTexts= new ArrayList<String>();
        preDefinedTexts.add("123456");
        preDefinedTexts.add("abcdef");
        accountCaptchaService.setPreDefinedText(preDefinedTexts);

        String captchaKey = accountCaptchaService.generateCaptchaKey();
        accountCaptchaService.generateCaptchaImage(captchaKey);
        assertTrue(accountCaptchaService.validateCaptcha(captchaKey, "123456"));

        captchaKey = accountCaptchaService.generateCaptchaKey();
        accountCaptchaService.generateCaptchaImage(captchaKey);
        assertTrue(accountCaptchaService.validateCaptcha(captchaKey, "abcdef"));
    }

    @Test
    public void testValidateCaptchaIncorrect() throws Exception{
        List<String> preDefinedTexts= new ArrayList<String>();
        preDefinedTexts.add("123456");
        accountCaptchaService.setPreDefinedText(preDefinedTexts);

        String captchaKey = accountCaptchaService.generateCaptchaKey();
        accountCaptchaService.generateCaptchaImage(captchaKey);
        assertFalse(accountCaptchaService.validateCaptcha(captchaKey, "67890"));
    }

}
