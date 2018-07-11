package nl.workshop1.DAO;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import java.math.BigDecimal;
import java.util.ArrayList;
import nl.workshop1.model.Artikel;
import nl.workshop1.utility.Slf4j;
import org.bson.Document;

/**
 *
 * @author FeniksBV
 */
public class ArtikelDAOmongoImpl implements ArtikelDAO {

    @Override
    public void deleteArtikel(int id) {
        Slf4j.getLogger().info("deleteArtikel({})", id);

        MongoDatabase db = DbConnection.getMongoConnection();
        MongoCollection<Document> collection = db.getCollection("artikel");
        Document document = new Document("aktief", 0);

        collection.updateOne(eq("id", id),
                new Document("$set", document));
    }

    @Override
    public void insertArtikel(Artikel artikel) {

        Slf4j.getLogger().info("insertArtikel({})", artikel.getNaam());

        MongoDatabase db = DbConnection.getMongoConnection();
        MongoCollection<Document> collection = db.getCollection("artikel");

        // Get the next available (unique) artikel.id
        artikel.setId(DbConnection.nextMongoId(collection));

        // Now perform full insert
        Document document = new Document("id", artikel.getId()).
                append("naam", artikel.getNaam()).
                append("prijs", artikel.getPrijs().doubleValue()).
                append("voorraad", artikel.getVoorraad()).
                append("gereserveerd", artikel.getGereserveerd()).
                append("sortering", artikel.getSortering());

        collection.insertOne(document);

    }

    @Override
    public void updateArtikel(Artikel artikel) {

        Slf4j.getLogger().info("updateArtikel({})", artikel.getNaam());

        MongoDatabase db = DbConnection.getMongoConnection();
        MongoCollection<Document> collection = db.getCollection("artikel");

        Document document = new Document("naam", artikel.getNaam()).
                append("prijs", artikel.getPrijs().doubleValue()).
                append("voorraad", artikel.getVoorraad()).
                append("gereserveerd", artikel.getGereserveerd()).
                append("sortering", artikel.getSortering());

        collection.updateOne(eq("id", artikel.getId()),
                new Document("$set", document));
    }

    @Override
    public ArrayList<Artikel> readArtikelWithFilter(String filter) {
        Slf4j.getLogger().info("readArtikelWithFilter({})", filter);

        ArrayList<Artikel> artikelList = new ArrayList<>();

        MongoDatabase db = DbConnection.getMongoConnection();
        MongoCollection<Document> collection = db.getCollection("artikel");

        BasicDBObject query = new BasicDBObject("aktief", new BasicDBObject("$ne", 0));
        if (filter.length() > 0) {
            query.append("naam", java.util.regex.Pattern.compile(filter));
        } 
        FindIterable<Document> docs = collection.find(query).sort(new BasicDBObject("sortering", 1));
        for (Document doc : docs) {
            artikelList.add(dataFromDoc(doc));
        }
        return artikelList;
    }

    protected Artikel dataFromDoc(Document doc) {
        Artikel artikel = new Artikel();
        artikel.setId(doc.getInteger("id"));
        artikel.setNaam(doc.getString("naam"));

//        BigDecimal prijs = new BigDecimal( doc.getDouble(""));
//        BigDecimal prijs = ((org.bson.types.Decimal128)doc.getDouble("prijs")).bigDecimalValue();
//        org.bson.types.Decimal128 prijs = (org.bson.types.Decimal128) doc.get("prijs");
        artikel.setPrijs(new BigDecimal(doc.getDouble("prijs")));

        artikel.setVoorraad(doc.getInteger("voorraad"));
        artikel.setGereserveerd(doc.getInteger("gereserveerd"));
        artikel.setSortering(doc.getInteger("sortering"));

        return artikel;
    }

    @Override
    public Artikel readArtikelByNaam(String naam) {
        Slf4j.getLogger().info("readArtikelByNaam({})", naam);

        MongoDatabase db = DbConnection.getMongoConnection();
        MongoCollection<Document> collection = db.getCollection("artikel");

        Document doc = collection.find(eq("naam", naam)).first();
        if ((doc != null) && (!doc.isEmpty())) {
            return dataFromDoc(doc);
        }

        return null;
    }

    @Override
    public Artikel readArtikelById(int id) {
        Slf4j.getLogger().info("readArtikelById({})", id);

        MongoDatabase db = DbConnection.getMongoConnection();
        MongoCollection<Document> collection = db.getCollection("artikel");

        Document doc = collection.find(eq("id", id)).first();
        if ((doc != null) && (!doc.isEmpty())) {
            return dataFromDoc(doc);
        }

        return null;
    }

}
