package nl.workshop1.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author FeniksBV
 */
public class Bestelling {
    private int id = 0;
    private int klantId = 0;
    private BigDecimal totaalprijs = new BigDecimal(0);
    private Date bestelDatum = new Date();
    private Bestelstatus bestelstatus = Bestelstatus.IN_BEHANDELING;
    private ArrayList<Bestelregel> bestelregel = new ArrayList<>();
    
  
}
