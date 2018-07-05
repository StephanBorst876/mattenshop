package nl.workshop1;

import nl.workshop1.controller.MainController;
import nl.workshop1.utility.Slf4j;
import nl.workshop1.view.OutputText;

/**
 *
 * @author FeniksBV
 */
public class Mattenshop {

    public static void main(String[] args) {
        // Slf4j Logger
        Slf4j.getLogger().info("Mattenshop started");

        MainController mainController = new MainController();
        mainController.runController();

        // correct afsluiten
        mattenshopAfsluiten();

    }

    protected static void mattenshopAfsluiten() {

        OutputText.showError("\n\nThanks for using Mattenshop. See you soon!");

        Slf4j.getLogger().info("Mattenshop ended");
        System.exit(0);
    }
}
