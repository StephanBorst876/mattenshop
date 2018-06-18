package nl.workshop1.DAO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import nl.workshop1.utility.Slf4j;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author FeniksBV
 */
public class DbConnection {

    private static Connection connection = null;

    protected static String driver = "";
    protected static String url = "";
    protected static String username = "";
    protected static String password = "";

    public static Connection getConnection() {
        if (connection == null) {
            connection = initializeConnection();
        }
        return connection;
    }

    protected static Connection initializeConnection() {

        // Initialiseer Driver en Connection interface met een .properties of met een .xml bestand
        initializeSettingsXml();
//        initializeSettingsProperties();

        Slf4j.getLogger().info("driver = {}", driver);
        Slf4j.getLogger().info("url = {}", url);
        Slf4j.getLogger().info("username = {}", username);
        Slf4j.getLogger().info("password = {}", password);

        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);
            return connection;

        } catch (ClassNotFoundException ex) {
            Slf4j.getLogger().error("initializeConnection() Class() failed", ex);
        } catch (SQLException ex) {
            Slf4j.getLogger().error("initializeConnection() DriverManager failed", ex);
        }

        return null;

    }

    protected static void initializeSettingsXml() {

        try {
            File DbConnFile = new File("mysql.xml");
            if (!DbConnFile.exists()) {
                Slf4j.getLogger().error("Missing mysql.xml in : ", DbConnFile.getAbsolutePath());
            }
            DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = docBuilder.parse(DbConnFile);
            document.getDocumentElement().normalize();

            driver = document.getElementsByTagName("jdbc_driver").item(0).getTextContent();
            url = document.getElementsByTagName("jdbc_url").item(0).getTextContent();
            username = document.getElementsByTagName("jdbc_username").item(0).getTextContent();
            password = document.getElementsByTagName("jdbc_password").item(0).getTextContent();

        } catch (ParserConfigurationException | IOException | SAXException e) {
            Slf4j.getLogger().error("initializeSettingsXml failed", e);
        }
    }

    protected static void initializeSettingsProperties() {
        // Initialize a file which holds all DB properties
        // JDBC Driver Name & Database URL
        // JDBC Database Credentials (user / password) 
        Properties props = new Properties();

        try (FileInputStream in = new FileInputStream("db.properties")) {
            props.load(in);
            driver = props.getProperty("jdbc.driver");
            url = props.getProperty("jdbc.url");
            username = props.getProperty("jdbc.username");
            password = props.getProperty("jdbc.password");

        } catch (FileNotFoundException e) {
            Slf4j.getLogger().error("initializeSettingsProperties failed", e);
        } catch (IOException e) {
            Slf4j.getLogger().error("initializeSettingsProperties failed", e);
        }

    }
}
