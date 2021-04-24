package com.jrq.xvgdl.generator.publisher.ftp;

import com.jrq.xvgdl.generator.publisher.XvgdlExternalublisher;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Slf4j
public class PublishFtp implements XvgdlExternalublisher {

    public static final String SERVER = "xvgdl-ftp.net";
    public static final int PORT = 21;
    public static final String USER = "xvgdl-ftp-user";
    public static final String PWD = getPassword();

    private static String getPassword() {
        return UUID.randomUUID().toString();
    }

    @Override
    public boolean publish(String filePath) {

        FTPClient ftpClient = new FTPClient();
        try {

            initializeFtp(ftpClient);
            uploadFile(filePath, ftpClient);

        } catch (IOException ex) {
            log.error("Error publishing to FTP: " + ex.getMessage(), ex);
            return false;
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return true;
    }

    private void uploadFile(String filePath, FTPClient ftpClient) throws IOException {
        File firstLocalFile = new File(filePath);
        String firstRemoteFile = "Projects.zip";
        InputStream inputStream = new FileInputStream(firstLocalFile);

        log.info("Start uploading file");
        boolean done = ftpClient.storeFile(firstRemoteFile, inputStream);
        inputStream.close();
        if (done) {
            log.info("The first file is uploaded successfully.");
        }
    }

    private void initializeFtp(FTPClient ftpClient) throws IOException {
        ftpClient.connect(SERVER, PORT);
        ftpClient.login(USER, PWD);
        ftpClient.enterLocalPassiveMode();
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
    }
}
