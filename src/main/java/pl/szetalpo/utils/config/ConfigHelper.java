package pl.szetalpo.utils.config;

import org.json.simple.parser.ParseException;
import pl.szetalpo.models.config.ConfigModel;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

public class ConfigHelper {
    public static String createJSONConfig() {
        String result = "";
        Scanner sc = new Scanner(System.in);
        System.out.print("Please enter username:\t");
        String username = sc.nextLine();
        System.out.print("Please enter password:\t");
        String password = sc.nextLine();
        System.out.print("Please enter host:\t");
        String host = sc.nextLine();
        System.out.print("Please enter port:\t");
        String port = sc.nextLine();
        System.out.print("Enter config path:\t");
        String path = sc.nextLine();

        ConfigModel configModel = new ConfigModel(host, username, password, port);
        try {
            result = ConfigHandler.saveConfigToJSON(configModel, path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;

    }

    public static ConfigModel getConfiguration(String confDirPath){
        Scanner sc = new Scanner(System.in);
        List<String> configFiles = ConfigHandler.showConfigurations(confDirPath);
        System.out.println("Choose your configuration: ");
        configFiles.forEach(System.out::println);
        Integer choice = sc.nextInt();
        String path = Path.of(confDirPath, configFiles.get(--choice)).toString();

        try {
            ConfigModel configModel = ConfigHandler.createFromJSON(path);
            return configModel;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
