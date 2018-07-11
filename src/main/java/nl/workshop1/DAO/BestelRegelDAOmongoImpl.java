package nl.workshop1.DAO;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import java.math.BigDecimal;
import java.util.ArrayList;
import nl.workshop1.model.Artikel;
import nl.workshop1.model.BestelRegel;
import nl.workshop1.utility.Slf4j;
import org.bson.Document;

/**
 *
 * @author FeniksBV
 */
public class BestelRegelDAOmongoImpl implements BestelRegelDAO {

    @Override
    public ArrayList<BestelRegel> readRegelsWithFilter(int bestellingId, String filter) {
        Slf4j.getLogger().info("readRegelsWithFilter({})", filter);

        MongoDatabase db = DbConnection.getMongoConnection();
        MongoCollection<Document> collection = db.getCollection("bestel_regel");

        // Bouw eerst een tijdelijke list van bestelregels
        ArrayList<BestelRegel> regelList = new ArrayList<>();
        BasicDBObject q = new BasicDBObject();
        FindIterable<Document> docs = collection.find();
        for (Document doc : docs) {
            BestelRegel regel = new BestelRegel();
            regel.setId(doc.getInteger("id"));
            regel.setBestellingId(bestellingId);
            regel.setArtikelId(doc.getInteger("artikel_id"));
            regel.setAantal(doc.getInteger("aantal"));
            BigDecimal prijs = new BigDecimal(doc.getDouble("prijs"));
            regel.setPrijs(prijs);

            regelList.add(regel);
        }

        // Zoek nu de artikelen erbij
        ArrayList<Artikel> artikelList = DAOFactory.getArtikelDAO().readArtikelWithFilter(filter);

        // Behoud alleen de regels die voorkomen in artikelList
        // Neem hiervan de artikelNaam over in de regel
        for (int i = regelList.size() - 1; i >= 0; i--) {
            int artikelId = regelList.get(i).getArtikelId();
            int index = -1;
            for (int j = 0; j < artikelList.size(); j++) {
                if (artikelList.get(j).getId() == artikelId) {
                    index = j;
                    regelList.get(i).setArtikelNaam(artikelList.get(j).getNaam());
                }
            }
            if (index == -1) {
                // Niet gevonden, dus verwijder de bestelregel
                regelList.remove(i);
            }

        }

        return regelList;
    }

    @Override
    public void deleteBestelRegel(int bestelRegelId) {
        Slf4j.getLogger().info("deleteBestelRegel({})", bestelRegelId);

        MongoDatabase db = DbConnection.getMongoConnection();
        MongoCollection<Document> collection = db.getCollection("bestel_regel");

        collection.deleteOne(new Document("id", bestelRegelId));
    }

    @Override
    public void insertBestelRegel(BestelRegel bestelRegel) {

        Slf4j.getLogger().info("insertBestelRegel({})", bestelRegel.getArtikelId());

        MongoDatabase db = DbConnection.getMongoConnection();
        MongoCollection<Document> collection = db.getCollection("bestel_regel");

        // Get the next available (unique) bestel_regel.id
        bestelRegel.setId(DbConnection.nextMongoId(collection));

        // Now perform full insert
        Document document = new Document("id", bestelRegel.getId()).
                append("bestelling_id", bestelRegel.getBestellingId()).
                append("artikel_id", bestelRegel.getArtikelId()).
                append("aantal", bestelRegel.getAantal()).
                append("prijs", bestelRegel.getPrijs().doubleValue());

        collection.insertOne(document);
    }

    @Override
    public void updateBestelRegel(BestelRegel bestelRegel) {

        Slf4j.getLogger().info("updateBestelRegel({})", bestelRegel.getArtikelId());

        MongoDatabase db = DbConnection.getMongoConnection();
        MongoCollection<Document> collection = db.getCollection("bestel_regel");

        Document document = new Document("aantal", bestelRegel.getAantal()).
                append("prijs", bestelRegel.getPrijs().doubleValue());

        collection.updateOne(eq("id", bestelRegel.getId()),
                new Document("$set", document));
    }

}
