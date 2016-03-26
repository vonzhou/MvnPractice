package com.vonzhou.mvnbook.account.captcha;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.beans.factory.InitializingBean;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by vonzhou on 16/3/25.
 */
public class AccountCaptchaServiceImpl implements AccountCaptchaService, InitializingBean {
    private DefaultKaptcha producer;
    private Map<String, String> captchaMap = new HashMap<String, String>();
    private List<String> preDefinedTexts;
    private int textCount = 0;

    // 在实例化对象的时候,构造验证码生成器
    public void afterPropertiesSet() throws Exception {
        producer = new DefaultKaptcha();
        producer.setConfig(new Config(new Properties()));
    }

    public void setPreDefinedText(List<String> preDefinedTexts) {
        this.preDefinedTexts = preDefinedTexts;
    }

    public List<String> getPreDefinedTexts() {
        return preDefinedTexts;
    }

    public String generateCaptchaKey() throws AccountCaptchaException {
        String key = RandomGenerator.getRandomString();
        String value = getCaptchaText();
        captchaMap.put(key, value);
        return key;
    }

    private String getCaptchaText() {
        if(preDefinedTexts != null && !preDefinedTexts.isEmpty()){
            String text = preDefinedTexts.get(textCount);
            textCount = (textCount + 1) % preDefinedTexts.size();
            return text;
        }
        else{
            return producer.createText();
        }
    }

    public byte[] generateCaptchaImage(String captchaKey) throws AccountCaptchaException {
        String text = captchaMap.get(captchaKey);
        if(text == null){
            throw  new AccountCaptchaException("Captcha key " + captchaKey + " not found ");
        }

        BufferedImage image = producer.createImage(text);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try{
            ImageIO.write(image, "jpg", out);
        } catch (IOException e) {
            new AccountCaptchaException("Failed to write captcha stream!");
        }
        return out.toByteArray();
    }

    public boolean validateCaptcha(String captchaKey, String captchaValue) throws AccountCaptchaException {
        String text = captchaMap.get(captchaKey);
        if(text == null){
            throw  new AccountCaptchaException("Captcha key " + captchaKey + " not found ");
        }
        if(text.equals(captchaValue)){
            captchaMap.remove(captchaKey);
            return true;
        }
        else return false;
    }
}
