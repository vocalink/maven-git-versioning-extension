package me.qoomon.maven.extension.gitversioning;

import org.apache.maven.execution.MavenExecutionRequest;

import java.io.File;

/**
 * Created by qoomon on 06/12/2016.
 */
public class ExtensionUtil {

    public static File getConfigFile(MavenExecutionRequest request, String artifactId) {
        File rootProjectDirectory = request.getMultiModuleProjectDirectory();
        return new File(rootProjectDirectory, ".mvn/" + artifactId + ".xml");
    }

    public static File getPropertiesFile(MavenExecutionRequest request, String artifactId, String fileName) {
        if(fileName == null || fileName.isEmpty()) {
            fileName = ".mvn/" + artifactId + ".properties";
        }
        File propertiesFile = new File(fileName);
        if (!propertiesFile.isAbsolute()) {
            File rootProjectDirectory = request.getMultiModuleProjectDirectory();
            propertiesFile = new File(rootProjectDirectory, fileName);
        }
        return propertiesFile;
    }
}
