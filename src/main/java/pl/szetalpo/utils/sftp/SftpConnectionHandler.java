package pl.szetalpo.utils.sftp;

import com.jcraft.jsch.*;
import pl.szetalpo.models.config.ConfigModel;

import java.io.ByteArrayOutputStream;

public class SftpConnectionHandler {
    private Session sftpSession;
    private JSch jsch;
    private ConfigModel config;

    public SftpConnectionHandler(ConfigModel config) {
        this.config = config;
        this.jsch = new JSch();
        try {
            sftpSession = jsch.getSession(config.getUsername(),config.getHost());
            sftpSession.setPassword(config.getPassword());
            sftpSession.setConfig("StrictHostKeyChecking", "no");
        } catch (JSchException e) {
            throw new RuntimeException(e);
        }
    }

    public String listDir(){
        String responseString = "";

        this.startSession();
        try {
            ChannelExec channelExec = (ChannelExec) sftpSession.openChannel("exec");
            channelExec.setCommand("pwd");
            ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
            channelExec.setOutputStream(responseStream);
            channelExec.connect();

            while(channelExec.isConnected()){
                Thread.sleep(100);
            }

            responseString = new String(responseStream.toByteArray());
        } catch (JSchException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }finally {
            this.endSession();
            return responseString;
        }
    }

    public void startSession(){
        if(sftpSession != null) {
            try {
                sftpSession.connect();
            } catch (JSchException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void endSession(){
        if(sftpSession != null) sftpSession.disconnect();

    }
}
