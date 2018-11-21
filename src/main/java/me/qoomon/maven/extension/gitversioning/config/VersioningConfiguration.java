package me.qoomon.maven.extension.gitversioning.config;

import me.qoomon.maven.extension.gitversioning.config.model.VersionFormatDescription;

import java.util.List;
import java.util.Objects;
import java.util.Properties;

/**
 * Created by qoomon on 30/11/2016.
 */
public class VersioningConfiguration {

    private final List<VersionFormatDescription> branchVersionDescriptions;
    private final List<VersionFormatDescription> tagVersionDescriptions;
    private final VersionFormatDescription commitVersionDescription;
    private final Properties properties = new Properties();
    private final boolean includeProperties;

    public VersioningConfiguration(List<VersionFormatDescription> branchVersionDescriptions,
                                   List<VersionFormatDescription> tagVersionDescriptions,
                                   VersionFormatDescription commitVersionDescription,
                                   Boolean includeProperties) {
        this.branchVersionDescriptions = Objects.requireNonNull(branchVersionDescriptions);
        this.tagVersionDescriptions = Objects.requireNonNull(tagVersionDescriptions);
        this.commitVersionDescription = Objects.requireNonNull(commitVersionDescription);
        this.includeProperties = includeProperties;
    }

    public void setProperties(Properties properties) {
        this.properties.putAll(properties);
    }

    public Properties getProperties() {
        return properties;
    }

    public List<VersionFormatDescription> getBranchVersionDescriptions() {
        return branchVersionDescriptions;
    }

    public List<VersionFormatDescription> getTagVersionDescriptions() {
        return tagVersionDescriptions;
    }

    public VersionFormatDescription getCommitVersionDescription() {
        return commitVersionDescription;
    }

    public boolean isIncludeProperties() {
        return includeProperties;
    }
}
