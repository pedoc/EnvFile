package net.ashald.envfile.providers.shenv;

import net.ashald.envfile.EnvVarsProvider;
import net.ashald.envfile.exceptions.EnvFileException;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Map;

public class ShenvProvider implements EnvVarsProvider {

    @Override
    public Map<String, String> getEnvVars(
            File file,
            boolean isExecutable,
            Map<String, String> context
    ) throws EnvFileException {
        return importDirenv(file);
    }

    @NotNull
    private static Map<String, String> importDirenv(File file) throws EnvFileException {
        String fileName = file.getName()
                .toLowerCase();
        final Map<String, String> envVars = new java.util.HashMap<String, String>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                line = line.trim();

                // Skip empty lines and comments (for .env and .properties files)
                if (line.isEmpty() || line.startsWith("#") || line.startsWith("!")) {
                    continue;
                }

                int equalIndex = line.indexOf('=');
                if (equalIndex > 0) {
                    String key = line.substring(0, equalIndex)
                            .trim();
                    String value = line.substring(equalIndex + 1)
                            .trim();

                    if (key.startsWith("export ") || key.startsWith("EXPORT ")) {
                        key = key.substring(7)
                                .trim();
                        envVars.put(key, value);
                    }
                    else {
                        // Skip lines that do not start with export in .sh files
                        continue;
                    }
                }
            }
        } catch (Exception e) {
            throw new EnvFileException(e);
        } finally {
            // No resources to close
        }
        return envVars;
    }
}
