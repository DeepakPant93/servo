package com.stereotype.servo.commons;

import com.stereotype.servo.exception.FileNotFoundException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * This class provides utility methods for downloading file content.
 * It contains methods to download files.
 */
@Component
public class FileUtil {

    private static final String BASE_DEFAULT_URL_FORMAT = "/%s/%s/%s.yml";
    private static final String TEMP_FILENAME = "script.yml";
    private static final String TEMP_DIRECTORY = System.getProperty("java.io.tmpdir");

    public String download(String app, String packageManager, String ansiblePlaybookRepoPath, String command) {
        String tempFilepath = null;
        String url = populateURL(app, packageManager, ansiblePlaybookRepoPath, command);

        try {
            URL fileUrl = new URL(url);

            Path targetPath = Path.of(TEMP_DIRECTORY, TEMP_FILENAME);
            Files.copy(fileUrl.openStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
            tempFilepath = targetPath.toString();
        } catch (IOException e) {
            System.out.println("Unable to process the file. Error message :: " + e.getMessage());
        }
        return tempFilepath;
    }

    public void delete(String filepath) {
        Path path = Paths.get(filepath);

        try {
            if (Files.exists(path)) {
                Files.delete(path);
            }
        } catch (IOException e) {
            throw new FileNotFoundException("File not found at " + filepath);
        }
    }

    private String populateURL(String app, String packageManager, String ansiblePlaybookRepoPath, String command) {
        return String.format(ansiblePlaybookRepoPath.concat(BASE_DEFAULT_URL_FORMAT), app.toLowerCase(), packageManager, command.toLowerCase());
    }
}
