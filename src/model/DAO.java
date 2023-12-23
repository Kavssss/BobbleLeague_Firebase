package model;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import java.io.FileInputStream;
import java.io.IOException;

public class DAO {

    public static void connection() {
        try {
//            FileInputStream serviceAccount = new FileInputStream("../src/model/credentials.json"); // Rodar fora NetBeans
            FileInputStream serviceAccount = new FileInputStream("src/model/credentials.json"); // Rodar no NetBeans

            FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://db-boobleleague-default-rtdb.firebaseio.com")
                .build();

            FirebaseApp.initializeApp(options);

            System.out.println("Conectado ao firebase");
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }
}
