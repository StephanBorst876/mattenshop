package nl.workshop1.controller;

/**
 *
 * @author FeniksBV
 */
public abstract class Controller {
    
    
    public String requestedAction;

    public Controller() {
        this.requestedAction = "";
    }

    /**
     *  Uitvoeren van de controller
     */
    public abstract void runController();


    /**
     *
     * @return  Door de gebruiker gevraagde actie
     */
    public String getRequestedAction() {
        return requestedAction;
    }

}
