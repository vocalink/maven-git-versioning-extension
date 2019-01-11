package me.qoomon.maven.extension.gitversioning;


import java.util.Map;

public class GAVGit extends GAV {

    private final String commit;
    private final String commitRefType;
    private final String commitRefName;
    private final Map<String, String> projectVersionDataMap;

    GAVGit(String groupId, String artifactId, String version, String commit, String commitRefType, String commitRefName, Map<String, String> projectVersionDataMap) {
        super(groupId, artifactId, version);
        this.commit = commit;
        this.commitRefType = commitRefType;
        this.commitRefName = commitRefName;
        this.projectVersionDataMap = projectVersionDataMap;
    }

    String getCommit() {
        return commit;
    }

    String getCommitRefType() {
        return commitRefType;
    }

    String getCommitRefName() {
        return commitRefName;
    }

    Map<String, String> getProjectVersionDataMap() {
        return projectVersionDataMap;
    }
}