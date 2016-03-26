package com.vonzhou.mvnbook.account.captcha;

import java.util.List;

/**
 * Created by vonzhou on 16/3/25.
 */
public interface AccountCaptchaService {
    String generateCaptchaKey() throws AccountCaptchaException;
    byte[] generateCaptchaImage(String captchaKey) throws AccountCaptchaException;
    boolean validateCaptcha(String captchaKey, String captchaValue) throws AccountCaptchaException;
    List<String> getPreDefinedTexts();
    void setPreDefinedText(List<String> preDefinedTexts);
}
