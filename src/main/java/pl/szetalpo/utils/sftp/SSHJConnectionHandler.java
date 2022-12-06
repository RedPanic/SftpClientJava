package pl.szetalpo.utils.sftp;

import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.sftp.RemoteResourceInfo;
import net.schmizz.sshj.sftp.SFTPClient;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;
import pl.szetalpo.models.config.ConfigModel;

import java.io.IOException;
import java.util.List;

public class SSHJConnectionHandler {
    private SSHClient sshClient;
    private ConfigModel configModel;

    public SSHJConnectionHandler(ConfigModel configModel) {
        this.configModel = configModel;
        sshClient = new SSHClient();
        sshClient.addHostKeyVerifier(new PromiscuousVerifier());

    }

    public String getFile(String sourcePath, String destPath) {
        try {
            connect();
            SFTPClient sftpClient = sshClient.newSFTPClient();
            try {
                sftpClient.get(sourcePath, destPath);
            } finally {
                sftpClient.close();
            }

            disconnect();
            return "File " + sourcePath + " downloaded succesfully from remote";
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "Failed";
    }

    public String putFile(String sourcePath, String destPath) {
        try {
            connect();
            SFTPClient sftpClient = sshClient.newSFTPClient();
            try {
                sftpClient.put(sourcePath, destPath);
            } finally {
                sftpClient.close();
            }

            disconnect();
            return "File " + sourcePath + " uploaded succesfully to remote";
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "Failed";
    }

    public String removeFile(String remotePath) {
        try {
            connect();
            SFTPClient sftpClient = sshClient.newSFTPClient();
            try {
                sftpClient.rm(remotePath);
            } finally {
                sftpClient.close();
            }
            disconnect();
            return "Success";
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "Failed.";
    }

    public String removeDir(String remotePath) {
        try {
            connect();
            SFTPClient sftpClient = sshClient.newSFTPClient();
            try {
                sftpClient.rmdir(remotePath);
            } finally {
                sftpClient.close();
            }
            disconnect();
            return "Success";
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "Failed";
    }

    public String makeDir(String remotePath) {
        try {
            connect();
            SFTPClient sftpClient = sshClient.newSFTPClient();
            try {
                sftpClient.mkdir(remotePath);
            } finally {
                sftpClient.close();
            }
            disconnect();
            return "Success";
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "Failed";
    }

    public List<RemoteResourceInfo> listDir(String path) {
        if (path.isEmpty() || path.equals(null)) {
            path = ".";
        }
        try {
            connect();
            List<RemoteResourceInfo> resultList;
            SFTPClient sftpClient = sshClient.newSFTPClient();
            try {
                resultList = sftpClient.ls(path);
            } finally {
                sftpClient.close();
            }
            disconnect();
            return resultList;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void connect() throws IOException {
        if (configModel != null) {
            sshClient.connect(configModel.getHost(), Integer.parseInt(configModel.getPort()));
            sshClient.authPassword(configModel.getUsername(), configModel.getPassword());
        }
    }

    private void disconnect() throws IOException {
        sshClient.disconnect();
    }
}
