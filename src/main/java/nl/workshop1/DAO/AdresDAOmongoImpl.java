package nl.workshop1.DAO;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import java.util.ArrayList;
import nl.workshop1.model.Adres;
import nl.workshop1.model.AdresType;
import nl.workshop1.utility.Slf4j;
import org.bson.Document;

/**
 *
 * @author FeniksBV
 */
public class AdresDAOmongoImpl implements AdresDAO {

    @Override
    public ArrayList<Adres> readAdresWithKlantId(int klantId) {

        Slf4j.getLogger().info("readAdresWithKlantId({})", klantId);

        ArrayList<Adres> adresList = new ArrayList<>();

        MongoDatabase db = DbConnection.getMongoConnection();
        MongoCollection<Document> collection = db.getCollection("adres");

        BasicDBObject q = new BasicDBObject();
        FindIterable<Document> docs = collection.find(eq("klant_id", klantId));
        for (Document doc : docs) {;
            Adres adres = new Adres();
            adres.setId(doc.getInteger("id"));
            adres.setStraatNaam(doc.getString("straatnaam"));
            adres.setHuisNummer(doc.getInteger("huisnummer"));
            adres.setToevoeging(doc.getString("toevoeging"));
            adres.setPostcode(doc.getString("postcode"));
            adres.setWoonplaats(doc.getString("woonplaats"));
            adres.setKlantId(doc.getInteger("klant_id"));
            adres.setAdresType(AdresType.valueOf(doc.getString("adres_type")));
            adresList.add(adres);
        }
        return adresList;
    }

    @Override
    public void deleteAdres(int id) {
        Slf4j.getLogger().info("deleteAdres({})", id);

        MongoDatabase db = DbConnection.getMongoConnection();
        MongoCollection<Document> collection = db.getCollection("adres");

        collection.deleteOne(new Document("id", id));
    }

    @Override
    public void insertAdres(Adres adres) {

        Slf4j.getLogger().info("insertAdres({})", adres.getKlantId());

        MongoDatabase db = DbConnection.getMongoConnection();
        MongoCollection<Document> collection = db.getCollection("adres");

        // Get the next available (unique) adres.id
        adres.setId(DbConnection.nextMongoId(collection));

        // Now perform full insert
        Document document = new Document("id", adres.getId()).
                append("straatnaam", adres.getStraatNaam()).
                append("huisnummer", adres.getHuisNummer()).
                append("toevoeging", adres.getToevoeging()).
                append("postcode", adres.getPostcode()).
                append("woonplaats", adres.getWoonplaats()).
                append("klant_id", adres.getKlantId()).
                append("adres_type", adres.getAdresType().getDescription());

        collection.insertOne(document);
    }

    @Override
    public void updateAdres(Adres adres) {

        Slf4j.getLogger().info("updateAdres({})", adres.getId());

        MongoDatabase db = DbConnection.getMongoConnection();
        MongoCollection<Document> collection = db.getCollection("adres");

        Document document = new Document("straatnaam", adres.getStraatNaam()).
                append("huisnummer", adres.getHuisNummer()).
                append("toevoeging", adres.getToevoeging()).
                append("postcode", adres.getPostcode()).
                append("woonplaats", adres.getWoonplaats());

        collection.updateOne(eq("id", adres.getId()),
                new Document("$set", document));
    }

}
