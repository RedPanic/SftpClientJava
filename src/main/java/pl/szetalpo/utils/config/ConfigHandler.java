package pl.szetalpo.utils.config;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import pl.szetalpo.models.config.ConfigModel;
import pl.szetalpo.utils.files.DirectoryHandler;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


public class ConfigHandler {
    /**
     * This is a method to read config model from JSON File
     *
     * @param filePath - path to JSON file
     * @return ConfigModel - Config object
     */
    public static ConfigModel createFromJSON(String filePath) throws IOException, ParseException {
        FileReader fileReader = new FileReader(filePath);
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonConfig = (JSONObject) jsonParser.parse(fileReader);
        return new ConfigModel(jsonConfig.get("host").toString(), jsonConfig.get("username").toString(), jsonConfig.get("password").toString(), jsonConfig.get("port").toString());
    }

    public static List<String> showConfigurations(String configDirPath){
        List<String> configFiles = DirectoryHandler.getFiles(configDirPath);
        return configFiles;
    }

    public static String saveConfigToJSON(ConfigModel configModel, String filePath) throws IOException {
        JSONObject jsonConfig = new JSONObject();
        jsonConfig.put("username", configModel.getUsername());
        jsonConfig.put("password", configModel.getPassword());
        jsonConfig.put("host", configModel.getHost());
        jsonConfig.put("port", configModel.getPort());
        Files.write(Paths.get(filePath), jsonConfig.toJSONString().getBytes());
        return "File " + filePath + " has been created succesfully";
    }
}
