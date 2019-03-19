package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(10);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }
    
    @Test
    public void kortinSaldoOnOikein() {
        assertEquals(10,kortti.saldo());
    }
    
    @Test
    public void kortinSaldoKasvaaOikein() {
        kortti.lataaRahaa(2);
        assertEquals(12, kortti.saldo());
    }
    
    @Test
    public void rahaaVoiOttaaJosOnRahaa() {
        kortti.otaRahaa(6);
        assertEquals(4, kortti.saldo());
    }
    
    @Test
    public void rahaaEiVoiOttaaJosEiOleTarpeeksi() {
        kortti.otaRahaa(12);
        assertEquals(10, kortti.saldo());
    }
    
    @Test
    public void palauttaaTrueJosOnRahaa() {
        assertEquals(true, kortti.otaRahaa(5));
    }
    
    @Test
    public void palauttaaFalseJosEiOleRahaa() {
        assertEquals(false, kortti.otaRahaa(20));
    }
    
    @Test
    public void toStringToimii() {
        assertEquals("saldo: 0.10",kortti.toString());
    }
}
