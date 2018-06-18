package com.vogella.junit.first;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import nl.workshop1.DAO.DbConnection;

/**
 *
 * @author FeniksBV
 */
public class sqlExecuteStatement {

    public static boolean executeStatement(String query) {
        try {
            Connection connObj = DbConnection.getConnection();
            PreparedStatement pstmtObj = connObj.prepareStatement(query);
            return pstmtObj.execute();
        } catch (SQLException ex) {
            return false;
        }
    }
}
