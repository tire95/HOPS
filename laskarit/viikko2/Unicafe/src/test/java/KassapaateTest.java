/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.mycompany.unicafe.Kassapaate;
import com.mycompany.unicafe.Maksukortti;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author timo
 */
public class KassapaateTest {
    
    Kassapaate paate;
    Maksukortti kortti;
    
    public KassapaateTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        paate = new Kassapaate();
        kortti = new Maksukortti(400);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void rahaAlussaOikein() {
        assertEquals(100000, paate.kassassaRahaa());
    }
    
    @Test
    public void lounaitaAlussaNolla() {
        assertEquals(0, paate.edullisiaLounaitaMyyty() + paate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void kateisEdullinenKassaKasvaa() {
        paate.syoEdullisesti(260);
        assertEquals(100240, paate.kassassaRahaa());
    }
    
    @Test
    public void kateisEdullinenPalautuuOikeaRaha() {
        assertEquals(20, paate.syoEdullisesti(260));
    }
    
    @Test
    public void kateisEdullistenMaaraKasvaa() {
        paate.syoEdullisesti(260);
        assertEquals(1, paate.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void kateisMaukasKassaKasvaa() {
        paate.syoMaukkaasti(460);
        assertEquals(100400, paate.kassassaRahaa());
    }
    
    @Test
    public void kateisMaukasPalautuuOikeaRaha() {
        assertEquals(20, paate.syoMaukkaasti(420));
    }
    
    @Test
    public void kateisMaukasMaaraKasvaa() {
        paate.syoMaukkaasti(420);
        assertEquals(1, paate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void kateisEdullinenKassaEiKasva() {
        paate.syoEdullisesti(180);
        assertEquals(100000, paate.kassassaRahaa());
    }
    
    @Test
    public void kateisMaukasKassaEiKasva() {
        paate.syoMaukkaasti(180);
        assertEquals(100000, paate.kassassaRahaa());
    }
    
    @Test
    public void kateisEdullinenPalauttaaRahatJosEiTarpeeksi() {
        assertEquals(180, paate.syoEdullisesti(180));
    }
    
    @Test
    public void kateisMaukasPalauttaaRahatJosEiTarpeeksi() {
        assertEquals(180, paate.syoMaukkaasti(180));
    }
    
    @Test
    public void kateisEdullinenMaaraEiKasva() {
        paate.syoEdullisesti(180);
        assertEquals(0, paate.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void kateisMaukasMaaraEiKasva() {
        paate.syoMaukkaasti(180);
        assertEquals(0, paate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void korttiEdullisestiOnnistuu() {
        assertEquals(true, paate.syoEdullisesti(kortti));
    }
    
    @Test
    public void korttiMaukkaastiOnnistuu() {
        assertEquals(true, paate.syoMaukkaasti(kortti));
    }
    
    @Test
    public void korttiEdullisestiMaaraKasvaa() {
        paate.syoEdullisesti(kortti);
        assertEquals(1, paate.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void korttiMaukkaastiMaaraKasvaa() {
        paate.syoMaukkaasti(kortti);
        assertEquals(1, paate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void korttiEdullisestiVahennetaanRahaa() {
        paate.syoEdullisesti(kortti);
        assertEquals(160, kortti.saldo());
    }
    
    @Test
    public void korttiMaukkaastiVahennetaanRahaa() {
        paate.syoMaukkaasti(kortti);
        assertEquals(0, kortti.saldo());
    }
    
    @Test
    public void korttiEdullisestiRahaEiMuutu() {
        paate.syoEdullisesti(kortti);
        paate.syoEdullisesti(kortti);
        assertEquals(160, kortti.saldo());
    }
    
    @Test
    public void korttiMaukkaastiRahaEiMuutu() {
        paate.syoEdullisesti(kortti);
        paate.syoMaukkaasti(kortti);
        assertEquals(160, kortti.saldo());
    }
    
    @Test
    public void korttiEdullisestiMaaraEiMuutu() {
        paate.syoMaukkaasti(kortti);
        paate.syoEdullisesti(kortti);
        assertEquals(0, paate.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void korttiMaukkaastiMaaraEiMuutu() {
        paate.syoEdullisesti(kortti);
        paate.syoMaukkaasti(kortti);
        assertEquals(0, paate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void korttiEdullisestiPalattaaFalse() {
        paate.syoMaukkaasti(kortti);
        assertEquals(false, paate.syoEdullisesti(kortti));
    }
    
    @Test
    public void korttiMaukkaastiPalattaaFalse() {
        paate.syoMaukkaasti(kortti);
        assertEquals(false, paate.syoMaukkaasti(kortti));
    }
    
    @Test
    public void korttiEdullisestiEiMuutaKassaa() {
        paate.syoEdullisesti(kortti);
        assertEquals(100000, paate.kassassaRahaa());
    }
    
    @Test
    public void korttiMaukkaastiEiMuutaKassaa() {
        paate.syoMaukkaasti(kortti);
        assertEquals(100000, paate.kassassaRahaa());
    }
    
    @Test
    public void korttiLatausNostaaSaldoa() {
        paate.lataaRahaaKortille(kortti, 100);
        assertEquals(500, kortti.saldo());
    }
    
    @Test
    public void korttiLatausNostaaKassaa() {
        paate.lataaRahaaKortille(kortti, 100);
        assertEquals(100100, paate.kassassaRahaa());
    }
    
    @Test
    public void korttiLatausEiKelpo() {
        paate.lataaRahaaKortille(kortti, -100);
        assertEquals(400, kortti.saldo());
    }
    
    @Test
    public void korttiLatausEiKelpoKassa() {
        paate.lataaRahaaKortille(kortti, -100);
        assertEquals(100000, paate.kassassaRahaa());
    }
}
