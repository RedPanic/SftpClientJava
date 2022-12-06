package pl.szetalpo.models.config;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConfigModel {
    private String host;
    private String username;
    private String password;
    private String port;
}
