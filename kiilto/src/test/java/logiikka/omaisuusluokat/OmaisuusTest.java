/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logiikka.omaisuusluokat;

import java.util.Random;
import logiikka.valineluokat.Kasakokoelma;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author xvixvi
 */
public class OmaisuusTest {
    
    public OmaisuusTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    Omaisuus o;
    
    @Before
    public void setUp() {
        o = new Omaisuus();
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void getArvovaltaToimii() {
        assertTrue(o.getArvovalta()==0); //tyhjä omaisuus
        
        Omistus omistus = new Omistus("testi", 1, 0, new Kasakokoelma(0));
        o.lisaaOmistus(omistus);
        assertTrue(o.getArvovalta()==1);
        o.lisaaOmistus(new Omistus(null, 0, 0, null));
        assertTrue(omistus.getArvovalta() == 1); //lisättiin 0 arvovallan omistus
        
        for (int i = 0; i < 10; i++) {
            o.lisaaOmistus(omistus);
        }
        assertTrue(o.getArvovalta()==11); //sama omistus 11 kertaa
    }  
    
    @Test
    public void getOmaisuudenBonuskarkitToimii() {
        for (int i = 1; i < 4; i++) {
            o.lisaaOmistus(new Omistus("testi"+i, i, i, null));
        }
        int[] bonukset = o.getOmaisuudestaTulevatBonusKarkit();
        int bonuksiaYhteensa = 0;
        for (int i = 0; i < bonukset.length; i++) {
            bonuksiaYhteensa += bonukset[i];
        }
        assertTrue(bonuksiaYhteensa == 3);
        assertTrue(bonukset[0] == 1);
        assertTrue(bonukset[1] == 1);
        assertTrue(bonukset[2] == 1);
        assertTrue(bonukset[3] == 0);
        
        o.lisaaOmistus(new Omistus("testi"+5, 1, 1, null));
        bonukset = o.getOmaisuudestaTulevatBonusKarkit();
        assertTrue(bonukset[0] == 2);
    }
    
    @Test
    public void sekoitaOnTarpeeksiSatunnainen() {
        //omaisuuteen lisätään 16 = 2^3 omistusta
        for (int i = 1; i <= 16; i++) {
            o.lisaaOmistus(new Omistus(""+i, i, 0, new Kasakokoelma(0)));
        }
        
        //täysin satunnaisen sekoituksen jälkeen todennäköisyys,
        //että omaisuus olisi täysin samassa järjestyksessä,
        //kuin ennen sekoitusta, on 1 / 16^16
        double p0 = 1 / Math.pow(16,16);
        int ksj = 0; //kaikkiSamassaJärjestyksessä
        //todennäköisyys, että täysin satunnaisen sekoittamisen
        //jälkeen omaisuudessa olisi 5 omistuksen sarja samassa
        //järjestyksessä, on 1 / 16^5 * 11, missä 11 on sarjan
        //mahdollisten aloitusindeksien lukumäärä
        double p1 = 11 / Math.pow(16, 5);
        int vsj = 0; //viidenSarjojaJärjestyksessä
        
        //sekoitetaan omaisuus 1000krt ja katsotaan, kuinka
        //monessa tapauksessa löytyy alisarja, joka on järjestyksessä
        //verrataan näin saatua todennäköisyyttä täysin satunnaiseen
        for (int i = 0; i < 1000; i++) {
            o.sekoita(new Random());
            if (onkoAlisarjaaJokaOnJarjestyksessa(o, 16)) ksj++;
            if (onkoAlisarjaaJokaOnJarjestyksessa(o, 5)) vsj++;
        }
        assertTrue("Sekoitus tuotti todella epätonnennäköiset järjestykset useasti.\n"
                + "Sekoitus tuotti " + ksj + "kpl samaa järjestystä sekoitettaessa 1000krt.\n"
                + "Todennäköisyys saada täysin satunnaisesti sama järjestys on 1 / (16^16)"
                ,ksj < 5);
        assertTrue("Sekoitus tuotti todella epätodennäköisen sarjan useasti.\n"
                + "Sekoitettaessa 1000krt sekoitus tuotti " +vsj+"tapauksessa viiden sarjan, joka oli järjestyksessä.\n"
                + "Täysin satunainen todennäköisyys olisi 11 / 16^5.",vsj < 10);
    }

    private boolean onkoAlisarjaaJokaOnJarjestyksessa(Omaisuus o, int alisarjanPituus) {
        //käydään omistus läpi, jos tarpeeksi pitkä, järjestyksessä
        //oleva alisarja löytyy, palautetaan true, muuten false
        int joap = 1; //järjestyksessä olevan alisarjan pituus
        int edellinen = Integer.parseInt(o.getEkaOmistus().getNimi());
        for (int i = 1; i < o.getKoko(); i++) {
            int seuraava = Integer.parseInt(o.getOmistuksenNimiIndeksista(i));
            if (edellinen+1 == seuraava) {
                joap++;
            } else joap=1;
            edellinen = seuraava;
            
            if (joap == alisarjanPituus) return true;
        }
        
        //koko omaisuus käyty läpi, eikä tarpeeksi pitkää ehjää sarjaa löytynyt
        return false;
    }
}
