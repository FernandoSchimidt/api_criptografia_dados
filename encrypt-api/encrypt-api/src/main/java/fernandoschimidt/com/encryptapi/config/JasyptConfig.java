package fernandoschimidt.com.encryptapi.config;

import org.jasypt.util.text.AES256TextEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JasyptConfig {

    @Value("${encrypt.key}")
    private String secreKey;
    @Bean
    public AES256TextEncryptor createEncryptor() {
        AES256TextEncryptor textEncryptor = new AES256TextEncryptor();
        textEncryptor.setPassword(secreKey);
        return textEncryptor;
    }
}
