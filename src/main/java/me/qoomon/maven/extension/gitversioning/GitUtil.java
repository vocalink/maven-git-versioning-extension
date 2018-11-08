package me.qoomon.maven.extension.gitversioning;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class GitUtil {

    public static Status getStatus(Repository repository) {
        try {
            return Git.wrap(repository).status().call();
        } catch (GitAPIException e) {
            throw new RuntimeException(e);
        }
    }

    public static Optional<String> getHeadBranch(Repository repository) throws IOException {

        ObjectId head = repository.resolve(Constants.HEAD);
        if (head == null) {
            return Optional.of(Constants.MASTER);
        }

        if (ObjectId.isId(repository.getBranch())) {
            return Optional.empty();
        }

        return Optional.ofNullable(repository.getBranch());
    }

    public static String getGitDescribe(Repository repository) throws  IOException {

        String [] query = {"git","describe","--always", "--long", "--tags"};

        String value =  commandExecutor(repository.getDirectory(), query);

        return (value == null) ? "0" : value;
    }

    public static String getGitDescribeLastTag(Repository repository) throws  IOException {

        String [] query = {"git", "describe", "--abbrev=0", "--tags"};

        String value = commandExecutor(repository.getDirectory(), query);

        return (value == null) ? "0" : value;
    }

    /**
     * Run a given command, passed as an array of Strings.
     *
     * @param command the command (including parameters) to execute
     * @return          output of command to stdout
     */
    private static String commandExecutor(File file, String[] command) {
        try {
            Process p = new ProcessBuilder(command).directory(file).start();
            InputStream is = p.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;
            line = br.readLine();
            return line;
        } catch (Exception e) {
            return null;
        }
    }

    public static List<String> getHeadTags(Repository repository) throws IOException {

        ObjectId head = repository.resolve(Constants.HEAD);
        if (head == null) {
            return Collections.emptyList();
        }

        return repository.getRefDatabase().getRefsByPrefix(Constants.R_TAGS).stream()
                .map(ref -> {
                    try {
                        return repository.getRefDatabase().peel(ref);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .filter(ref -> {
                    ObjectId objectId;
                    if (ref.getPeeledObjectId() != null) {
                        objectId = ref.getPeeledObjectId();
                    } else {
                        objectId = ref.getObjectId();
                    }
                    return objectId.equals(head);
                })
                .map(ref -> ref.getName().replaceFirst("^" + Constants.R_TAGS, ""))
                .collect(Collectors.toList());
    }

    public static String getHeadCommit(Repository repository) throws IOException {

        ObjectId head = repository.resolve(Constants.HEAD);
        if (head == null) {
            return "0000000000000000000000000000000000000000";
        }
        return head.getName();
    }
}
