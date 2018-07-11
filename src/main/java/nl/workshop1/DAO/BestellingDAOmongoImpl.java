package nl.workshop1.DAO;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import nl.workshop1.model.Bestelling;
import nl.workshop1.model.Bestelstatus;
import nl.workshop1.utility.Slf4j;
import org.bson.Document;

/**
 *
 * @author FeniksBV
 */
public class BestellingDAOmongoImpl implements BestellingDAO {

    @Override
    public ArrayList<Bestelling> readBestellingWithFilter(int klantId, String filter) {
        Slf4j.getLogger().info("readBestellingWithFilter({})", filter);

        ArrayList<Bestelling> bestelList = new ArrayList<>();

        MongoDatabase db = DbConnection.getMongoConnection();
        MongoCollection<Document> collection = db.getCollection("bestelling");

        BasicDBObject query = new BasicDBObject("klant_id", klantId);
//        query.append("bestelstatus", Bestelstatus.Onderhanden);
//        if (filter.length() > 0) {
//            query.append("id", java.util.regex.Pattern.compile(filter));
//        }
        Slf4j.getLogger().info(query.toString());

        FindIterable<Document> docs = collection.find(query).sort(new BasicDBObject("besteldatum", 1));
        for (Document doc : docs) {
            Bestelling bestelling = new Bestelling();
            bestelling.setId(doc.getInteger("id"));
            bestelling.setKlantId(doc.getInteger("klant_id"));
            BigDecimal totaalPrijs = new BigDecimal(doc.getDouble("totaalprijs"));
            bestelling.setTotaalprijs(totaalPrijs);
            
            Date besteldatum = doc.getDate("besteldatum");
            bestelling.setBestelDatum(besteldatum);
            bestelling.setBestelstatus(Bestelstatus.valueOf(doc.getString("bestelstatus")));
            bestelList.add(bestelling);
        }
        return bestelList;
    }

    @Override
    public void deleteBestelling(int bestellingId) {
        Slf4j.getLogger().info("deleteBestelling({})", bestellingId);

        MongoDatabase db = DbConnection.getMongoConnection();
        MongoCollection<Document> collection = db.getCollection("bestelling");

        collection.deleteOne(new Document("id", bestellingId));
    }

    @Override
    public void insertBestelling(Bestelling bestelling) {

        Slf4j.getLogger().info("insertBestelling({})", bestelling.getKlantId());

        MongoDatabase db = DbConnection.getMongoConnection();
        MongoCollection<Document> collection = db.getCollection("bestelling");

        bestelling.setId(DbConnection.nextMongoId(collection));
        Document document = new Document("id", bestelling.getId()).
                append("klant_id", bestelling.getKlantId()).
                append("totaalprijs", bestelling.getTotaalprijs().doubleValue()).
                append("besteldatum", bestelling.getBestelDatum()).
                append("bestelstatus", bestelling.getBestelstatus().getDescription());

        collection.insertOne(document);
    }

    @Override
    public void updateBestelling(Bestelling bestelling) {
        Slf4j.getLogger().info("updateBestelling({})", bestelling.getId());

        MongoDatabase db = DbConnection.getMongoConnection();
        MongoCollection<Document> collection = db.getCollection("bestelling");

        Document document = new Document("totaalprijs", bestelling.getTotaalprijs().doubleValue()).
                append("besteldatum", bestelling.getBestelDatum());

        collection.updateOne(eq("id", bestelling.getId()),
                new Document("$set", document));
    }

}
