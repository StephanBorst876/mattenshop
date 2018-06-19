package com.vogella.junit.first;

import nl.workshop1.DAO.AccountDAOImplTest;
import nl.workshop1.DAO.ArtikelDAOImplTest;
import org.junit.Test;

/**
 *
 * @author FeniksBV
 */
public class MyMenuTest {
    
    @Test
    public void main(){
        
        AccountDAOImplTest accountTest = new AccountDAOImplTest();
        accountTest.testReadAccountByUserName();
        
        ArtikelDAOImplTest artikeltest = new ArtikelDAOImplTest();
        artikeltest.testInsertArtikel();
    }
    
}
