package com.srjons;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ArrayUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class Main {
    public static void main(String[] args) {
        if (ArrayUtils.isEmpty(args)) {
            System.out.println("no args passed. please pass container name/cmd");
            return;
        }
        String containerCmd = args[0];
        try {
            Process exec = Runtime.getRuntime().exec("cmd /c docker ps");
            InputStream inputStream = exec.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains(containerCmd)) {
                    System.out.println("line = " + line);
                    String containerId = line.substring(0, 12);
                    System.out.println(containerId);
                    String dockerExec = "docker exec -it " + containerId + " /bin/bash";
                    FileUtils.writeStringToFile(new File("connect-container.bat"), dockerExec, Charset.defaultCharset());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}