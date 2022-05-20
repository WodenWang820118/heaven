package oh_heaven.game.service;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PropertiesLoader {
    public static final String DEFAULT_DIRECTORY_PATH = System.getProperty("user.dir") + "\\properties\\";
    public static Properties loadPropertiesFile(String propertiesFile) {
        if (propertiesFile == null) {
            try {
                InputStream input = new FileInputStream(DEFAULT_DIRECTORY_PATH + "runmode.properties");
                Properties properties = new Properties();
                properties.load(input); // load a properties file
    
                propertiesFile = DEFAULT_DIRECTORY_PATH + properties.getProperty("current_mode");
                System.out.println(propertiesFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            InputStream input = new FileInputStream(propertiesFile);
            Properties properties = new Properties();
            properties.load(input); // load a properties file
            return properties;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<String> loadPlayers(Properties properties) {
        // assume always four players in the game
        List<String> players = new ArrayList<String>();
        for (int i = 0; i < 4; i++) {
            String playerType = properties.getProperty("players." + i);
            players.add(playerType);
        }
        return players;
    }
}
