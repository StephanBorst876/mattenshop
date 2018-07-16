package nl.workshop1.DAO;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import java.util.ArrayList;
import nl.workshop1.model.Account;
import nl.workshop1.model.Role;
import nl.workshop1.utility.Slf4j;
import org.bson.Document;

/**
 *
 * @author FeniksBV
 */
public class AccountDAOmongoImpl implements AccountDAO {

    @Override
    public Account readAccountByUserName(String userName) {
        Slf4j.getLogger().info("readAccountByUserName({})", userName);

        MongoDatabase db = DbConnection.getMongoConnection();
        MongoCollection<Document> collection = db.getCollection("account");

        Document doc = collection.find(eq("email", userName)).first();
        if ((doc != null) && (!doc.isEmpty())) {
            return dataFromDoc(doc);
        }

        return null;

    }

    protected Account dataFromDoc(Document doc) {
        Account account = new Account();

        account.setUserName(doc.getString("email"));
        account.setWachtwoord(doc.getString("wachtwoord"));
        account.setRole(Role.valueOf(doc.getString("account_type")));
        Integer kl_id = doc.getInteger("klant_id");
        if (kl_id != null) {
            account.setKlantId(kl_id);
        }

        return account;
    }

    @Override
    public ArrayList<Account> readAccountWithFilter(String filter) {
        Slf4j.getLogger().info("readAccountWithFilter({})", filter);

        ArrayList<Account> accountList = new ArrayList<>();

        MongoDatabase db = DbConnection.getMongoConnection();
        MongoCollection<Document> collection = db.getCollection("account");

        BasicDBObject q = new BasicDBObject();
        FindIterable<Document> docs;
        if (filter.length() > 0) {
            q.put("email", java.util.regex.Pattern.compile(filter));
            docs = collection.find(q);
        } else {
            docs = collection.find();
        }
        for (Document doc : docs) {
            accountList.add(dataFromDoc(doc));
        }
        return accountList;
    }

    @Override
    public void deleteAccount(String userName) {
        Slf4j.getLogger().info("deleteAccount({})", userName);

        MongoDatabase db = DbConnection.getMongoConnection();
        MongoCollection<Document> collection = db.getCollection("account");

        collection.deleteOne(new Document("email", userName));

    }

    @Override
    public void insertAccount(Account account) {

        Slf4j.getLogger().info("insertAccount({})", account.getUserName());

        MongoDatabase db = DbConnection.getMongoConnection();
        MongoCollection<Document> collection = db.getCollection("account");

        Document document = new Document("email", account.getUserName()).
                append("wachtwoord", account.getWachtwoord()).
                append("account_type", account.getRole().getDescription()).
                append("klant_id", account.getKlantId());

        collection.insertOne(document);

    }

    @Override
    public void updateAccount(Account account) {
        Slf4j.getLogger().info("updateAccount({})", account.getUserName());

        MongoDatabase db = DbConnection.getMongoConnection();
        MongoCollection<Document> collection = db.getCollection("account");

        Document document = new Document("wachtwoord", account.getWachtwoord()).
                append("account_type", account.getRole().getDescription()).
                append("klant_id", account.getKlantId());

        collection.updateOne(eq("email", account.getUserName()),
                new Document("$set", document));

    }

}
