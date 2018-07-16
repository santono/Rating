package rating.util.captcha;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CaptchaSettings {
    @Value( "${google_recaptcha_site_key}" )
    private String site;
    @Value( "${google.recaptcha.private.key}" )
    private String secret;

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
// standard getters and setters
}