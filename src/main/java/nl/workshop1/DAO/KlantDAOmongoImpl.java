package nl.workshop1.DAO;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import java.util.ArrayList;
import nl.workshop1.model.Klant;
import nl.workshop1.utility.Slf4j;
import org.bson.Document;

/**
 *
 * @author FeniksBV
 */
public class KlantDAOmongoImpl implements KlantDAO {

    protected Klant dataFromDoc(Document doc) {
        Klant klant = new Klant();

        klant.setId(doc.getInteger("id"));
        klant.setEmail(doc.getString("email"));
        klant.setVoornaam(doc.getString("voornaam"));
        klant.setAchternaam(doc.getString("achternaam"));
        klant.setTussenvoegsel(doc.getString("tussenvoegsel"));
        klant.setSortering(doc.getInteger("sortering"));

        return klant;
    }

    @Override
    public ArrayList<Klant> readKlantWithFilter(String filter) {
        Slf4j.getLogger().info("readKlantWithFilter({})", filter);

        ArrayList<Klant> klantList = new ArrayList<>();

        MongoDatabase db = DbConnection.getMongoConnection();
        MongoCollection<Document> collection = db.getCollection("klant");

        BasicDBObject query = new BasicDBObject("aktief", new BasicDBObject("$ne", 0));
        if (filter.length() > 0) {
            query.append("achternaam", java.util.regex.Pattern.compile(filter));
        }
        FindIterable<Document> docs = collection.find(query).sort(new BasicDBObject("sortering", 1));

        for (Document doc : docs) {
            klantList.add(dataFromDoc(doc));
        }
        return klantList;
    }

    @Override
    public Klant readKlant(int id) {
        Slf4j.getLogger().info("readKlant({})", id);

        MongoDatabase db = DbConnection.getMongoConnection();
        MongoCollection<Document> collection = db.getCollection("klant");

        BasicDBObject query = new BasicDBObject("aktief", new BasicDBObject("$ne", 0));
        query.append("id", id);
        Document doc = collection.find(query).sort(new BasicDBObject("sortering", 1)).first();
        if ((doc != null) && (!doc.isEmpty())) {
            return dataFromDoc(doc);
        }

        return null;
    }

    @Override
    public Klant readKlantWithEmail(String email) {
        Slf4j.getLogger().info("readKlantWithEmail({})", email);

        MongoDatabase db = DbConnection.getMongoConnection();
        MongoCollection<Document> collection = db.getCollection("klant");

        BasicDBObject query = new BasicDBObject("email", email);
        Document doc = collection.find(query).first();
        if ((doc != null) && (!doc.isEmpty())) {
            return dataFromDoc(doc);
        }

        return null;
    }

    @Override
    public void deleteKlant(int id) {
        Slf4j.getLogger().info("deleteKlant({})", id);

        MongoDatabase db = DbConnection.getMongoConnection();
        MongoCollection<Document> collection = db.getCollection("klant");

        Document document = new Document("aktief", 0);

        collection.updateOne(eq("id", id),
                new Document("$set", document));

    }

    @Override
    public void insertKlant(Klant klant) {

        Slf4j.getLogger().info("insertKlant({})", klant.getFullName());

        MongoDatabase db = DbConnection.getMongoConnection();
        MongoCollection<Document> collection = db.getCollection("klant");

        klant.setId(DbConnection.nextMongoId(collection));
        Document document = new Document("id", klant.getId()).
                append("email", klant.getEmail()).
                append("voornaam", klant.getVoornaam()).
                append("achternaam", klant.getAchternaam()).
                append("tussenvoegsel", klant.getTussenvoegsel()).
                append("sortering", klant.getSortering());

        collection.insertOne(document);
    }

    @Override
    public void updateKlant(Klant klant) {
        Slf4j.getLogger().info("updateKlant({})", klant.getId());

        MongoDatabase db = DbConnection.getMongoConnection();
        MongoCollection<Document> collection = db.getCollection("klant");

        Document document = new Document("email", klant.getEmail()).
                append("voornaam", klant.getVoornaam()).
                append("achternaam", klant.getAchternaam()).
                append("tussenvoegsel", klant.getTussenvoegsel()).
                append("sortering", klant.getSortering());

        collection.updateOne(eq("id", klant.getId()),
                new Document("$set", document));
    }

}
