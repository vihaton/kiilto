package logiikka;

import java.io.File;
import java.util.ArrayList;
import logiikka.omaisuusluokat.*;
import logiikka.valineluokat.*;
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
public class PoytaTest {

    Poyta p;

    public PoytaTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        ArrayList<String> pelaajat = new ArrayList<>();
        pelaajat.add("homo1");
        pelaajat.add("homo2");
        pelaajat.add("homo3");
        pelaajat.add("mr Gandalf");
        Pelivelho pv = new Pelivelho();
        pv.luoPelaajat(pelaajat);
        p = new Poyta(pv.getPelaajat());
    }

    @After
    public void tearDown() {
    }

    @Test
    public void poytaLuoLukijan() {
        assertTrue(p.luoLukija(File.separator + "omistustentiedot.csv") != null);
    }

    @Test
    public void luoOikeanMaaranOmistuksiaAlustuksessa() {
        assertTrue("omistuspakkoja ei ole oikeaa määrää", p.getOmistuspakat().size() == 3);
        Omaisuus pakka1 = p.getOmistuspakat().get(0);
        Omaisuus pakka2 = p.getOmistuspakat().get(1);
        Omaisuus pakka3 = p.getOmistuspakat().get(2);
        assertTrue("omistuspakassa 1 väärä määrä omistuksia", pakka1.getOmaisuudenKoko() == 40);
        assertTrue("omistuspakassa 2 väärä määrä omistuksia", pakka2.getOmaisuudenKoko() == 30);
        assertTrue("omistuspakassa 3 väärä määrä omistuksia", pakka3.getOmaisuudenKoko() == 20);
    }

    @Test
    public void sekoittaaOmistuspakatLuotaessa() {
        ArrayList<Omaisuus> pakat = p.getOmistuspakat();
        Omaisuus o = pakat.get(0);
        ArrayList<String> nn = o.getPaallimmaistenNimet();

        int x = 0;
        for (int i = 0; i < nn.size(); i++) {
            int nimi = Integer.parseInt(nn.get(i));
            if (nimi == i) {
                x++;
            }
        }
        assertTrue("neljä ensimmäistä omaisuutta olivat luomisjärjestyksessä!", x < 4);
    }

    @Test
    public void getNakyvienNimetAntaa12Nimea() {
        assertTrue(p.getNakyvienNimet().size() == 12);
    }

    @Test
    public void suoritaOstoOikeallaSyotteella() {
        int ostonNro = Integer.parseInt(p.getNakyvienNimet().get(0));
        Pelaaja peluri = new Pelaaja("p1");
        int[] karkit = new int[]{5, 5, 5, 5, 5, 5};
        peluri.setKarkit(karkit);
        assertTrue(p.suoritaOsto(peluri, ostonNro));

        karkit = new int[]{9, 9, 9, 9, 9, 9};
        peluri.setKarkit(karkit);
        ostonNro = Integer.parseInt(p.getNakyvienNimet().get(4));
        assertTrue(p.suoritaOsto(peluri, ostonNro));

        peluri.setKarkit(karkit);
        ostonNro = Integer.parseInt(p.getNakyvienNimet().get(9));
        assertTrue(p.suoritaOsto(peluri, ostonNro));
    }

    @Test
    public void suoritaOstoVaarallaSyotteella() {
        int ostonNro = Integer.parseInt(p.getNakyvienNimet().get(0));
        Pelaaja pelaaja = new Pelaaja("homo");
        //pelaajalla ei ole varaa
        assertFalse(p.suoritaOsto(pelaaja, ostonNro));

        ostonNro = 0;
        while (p.getNakyvienNimet().contains("" + ostonNro)) {
            ostonNro++;
        }
        String nakyvilla = p.getNakyvienNimet().toString();

        //omistus ei ole näkyvillä
        assertFalse("pelaaja " + pelaaja.toString() + "osti pöydästä omistuksen nro " + ostonNro + " " + nakyvilla,
                p.suoritaOsto(pelaaja, ostonNro));

        pelaaja.setKarkit(new int[]{5, 5, 5, 5, 5, 5});
        //omistus ei ole näkyvilla mutta pelaajalla on varaa
        assertFalse("pelaaja " + pelaaja.toString() + "osti pöydästä omistuksen nro " + ostonNro + " " + nakyvilla,
                p.suoritaOsto(pelaaja, ostonNro));
    }

    @Test
    public void varauksenOstaminenToimii() {
        Omistus o = new Omistus("1", 1, 1, new Kasakokoelma(0));
        Pelaaja pel = new Pelaaja("testihomo");

        int varattavanNro = 0;
        while (!p.getNakyvienNimet().contains("" + varattavanNro)) {
            varattavanNro++;
        }

        p.teeVaraus(pel, varattavanNro);

        assertFalse(p.suoritaOstoVarauksista(pel, 100)); //ei ole varauksissa
        assertFalse(p.suoritaOstoVarauksista(pel, varattavanNro)); //ei ole varaa

        pel.setKarkit(new int[]{5, 5, 5, 5, 5, 5});
        assertFalse(p.suoritaOstoVarauksista(pel, 100)); //ei ole varauksissa
        assertTrue(p.suoritaOstoVarauksista(pel, varattavanNro)); //on varaa
        assertTrue(p.getMarkkinat().getKarkkienMaara() > 40); //pelaajalta on siirtynyt >0 kpl karkkeja markkinoille.
    }

    @Test
    public void teeVarausToimii() {
        Pelaaja pel = new Pelaaja("testihomo");
        assertFalse(p.teeVaraus(pel, 100)); //väärä nro

        int varattavanNro = Integer.parseInt(p.getNakyvienNimet().get(0));
        assertFalse(p.teeVaraus(null, varattavanNro)); //ei pelaajaa

        for (int i = 0; i < 3; i++) {
            assertTrue(pel.getKarkit().getKasanKoko(0) == i); //kultaa on aluksi 0, kasvaa varattaessa

            assertTrue(p.teeVaraus(pel, varattavanNro)); //varaus onnistuu
            assertTrue(pel.getVarauksienMaara() == i + 1); //varausten määrä kasvaa
            assertTrue(varattavanNro != Integer.parseInt(p.getNakyvienNimet().get(0))); //varaus lähtee pöydästä

            varattavanNro = Integer.parseInt(p.getNakyvienNimet().get(0));
        }

        assertFalse(p.teeVaraus(pel, varattavanNro)); //varaus ei onnistu (kolmen raja)
        assertTrue(pel.getVarauksienMaara() == 3); //varausten määrä ei kasva
        assertFalse(varattavanNro != Integer.parseInt(p.getNakyvienNimet().get(0))); //varaus ei lähde pöydästä
    }

    @Test
    public void luoTestattavaPelitilanneToimiiJotenkin() {
        assertTrue(p.luoTestattavaPelitilanne(2) > 5);

        for (Pelaaja pelaaja : p.getPelaajat()) {
            assertTrue(pelaaja.getOmaisuudenKoko() != 0 || pelaaja.getKarkit().getKarkkienMaara() != 0 || pelaaja.getVarauksienMaara() != 0);
        }
    }

    @Test
    public void valitseePelinMerkkihenkilotValitseeOikeanMaaran() {
        ArrayList<Pelaaja> pelaajat = new ArrayList<>();
        pelaajat.add(new Pelaaja("eka"));
        for (int i = 2; i < 5; i++) {
            pelaajat.add(new Pelaaja("" + i));
            p = new Poyta(pelaajat);

            int oikeaMaara = 3;
            if (i == 3) {
                oikeaMaara = 4;
            } else if (i == 4) {
                oikeaMaara = 5;
            }
            assertTrue(p.getMerkkihenkilot().size() == oikeaMaara);
        }
    }
    
    @Test
    public void toStringToimiiJotenkin() {
        assertTrue(p.toString().contains(p.getPelaajat().get(0).getNimi()));
        assertTrue(p.toString().contains(p.getMerkkihenkilot().get(0).getNimi()));
        assertTrue(p.toString().contains(p.getNakyvienNimet().get(0)));
    }

}
