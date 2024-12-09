package com.himfirst.vidshare.gcs;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;

@Service

public class FirebaseInit {
    @PostConstruct
    public void initialize() throws IOException {
//        String path = "serviceAcount.json";
        String path = "/Users/kazkaz/IdeaProjects/share-jesus-today-backend/serviceAcount.json";
        FileInputStream serviceAccount =
                new FileInputStream(path);
        //  ./serviceAcount.json

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
//                .setDatabaseUrl("https://qc-sacco-default-rtdb.firebaseio.com")
                .setStorageBucket("qc-sacco.appspot.com")
                .build();

            FirebaseApp.initializeApp(options);

    }
}
