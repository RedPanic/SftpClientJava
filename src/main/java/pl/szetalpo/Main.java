package pl.szetalpo;

import net.schmizz.sshj.sftp.RemoteResourceInfo;
import pl.szetalpo.models.config.ConfigModel;
import pl.szetalpo.utils.config.ConfigHelper;
import pl.szetalpo.utils.sftp.SSHJConnectionHandler;

import java.util.List;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        showMenu();
        String choice = sc.nextLine();
        switch (choice.toLowerCase()) {
            case "config" -> ConfigHelper.createJSONConfig();
            case "ls" -> listDir();
            case "exit" -> System.exit(0);
            default -> System.out.println("Error");
        }

    }

    public static void listDir() {
        ConfigModel configModel = ConfigHelper.getConfiguration("./res");
        SSHJConnectionHandler sftpHandler = new SSHJConnectionHandler(configModel);
        List<RemoteResourceInfo> resultList = sftpHandler.listDir("./files/bos");
        for(var item: resultList){
            System.out.println(item.getPath());
        }
    }

    public static void showMenu() {
        System.out.println("UCTI SFTP Client v1.0");
        System.out.println("MENU:");
        System.out.println("Create config file(config)");
        System.out.println("Execute ls on remote server (ls)");
        System.out.println("Exit application(exit)");
        System.out.print("Choice:\t");
    }
}

