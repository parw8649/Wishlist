package com.wishlist.cst438project2.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;

/**
 * Firebase configuration with Springboot
 * @author Chaitanya Parwatkar
 * @version %I% %G%
 */

@Configuration
@Slf4j
public class FirebaseConfig {

    @Value("${wishlist.firebase.service-account-filename}")
    private String serviceAccountKeyFile;

    @Value("${wishlist.firebase.cloud-firestore-url}")
    private String databaseUrl;

    @PostConstruct
    @SneakyThrows
    public void init() {

        try {
            FileInputStream serviceAccount = new FileInputStream(serviceAccountKeyFile);

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl(databaseUrl)
                    .build();

            FirebaseApp.initializeApp(options);

        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw ex;
        }
    }
}
