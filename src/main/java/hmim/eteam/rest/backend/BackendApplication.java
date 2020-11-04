package hmim.eteam.rest.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Arrays;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        ArrayList<String> modifiedArgs = new ArrayList<>(Arrays.asList(args));
        modifiedArgs.add("-Dserver.port=" + System.getenv("PORT"));
        modifiedArgs.add("-Dspring.datasource.username=" + System.getenv("DB_USER"));
        modifiedArgs.add("-Dspring.datasource.password=" + System.getenv("DB_PASSWORD"));

        String[] modifiedArgsAsArray = new String[modifiedArgs.size()];
        modifiedArgsAsArray = modifiedArgs.toArray(modifiedArgsAsArray);
        SpringApplication.run(BackendApplication.class, modifiedArgsAsArray);
    }
}
