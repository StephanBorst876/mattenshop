package com.vogella.junit.first;

import nl.workshop1.DAO.AccountDAOImplTest;
import org.junit.Test;

/**
 *
 * @author FeniksBV
 */
public class MyMenuTest {
    
    @Test
    public void main(){
        
        AccountDAOImplTest accTest = new AccountDAOImplTest();
        accTest.testReadAccountByUserName();
        
    }
    
}
