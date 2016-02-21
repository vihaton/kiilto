package logiikka;

import java.awt.Color;
import ui.gui.Kayttoliittyma;
import java.awt.Graphics;
import java.util.*;
import ui.*;
import logiikka.valineluokat.*;
import ui.gui.PiirtoAvustaja;

/**
 * Luokka vastaa pelin pyörittämisestä. Esimerkiksi pelin alustaminen, vuorojen
 * organisoiminen sekä UI.n ja logiikan rajapintana toimiminen ovat sen
 * tehtäviä.
 *
 * Tulevaisuudessa peliin tehdään tekoäly, minkä vuoksi testinomaisia text user
 * interface -metodeita ja toimintoja säilytetään yhä kommenteissa.
 *
 * @author xvixvi
 */
public class Pelivelho {

    private TUI tui;
    private Kayttoliittyma kayttoliittyma;
    private ArrayList<Pelaaja> pelaajat;
    private Poyta poyta;
    private int voittoValta;
    private int kierros = 0;
    private boolean GUIPeli;
    private boolean pysahdy;
    private String syote;

    public Pelivelho() {
        tui = new TUI(new Scanner(System.in));
        kayttoliittyma = new Kayttoliittyma(this);
        GUIPeli = true;
        pysahdy = false;
        syote = "";
    }

    /**
     * Alustaa ja pelaa kiilto-pelin.
     */
    public void pelaa() {
//        alustaTestiTUIPeli(); //tui-peli
//        alustaTUIPeli();
        
//        pelaaTUI();
        
        alustaGUIPeli();
        
        poyta.luoTestitilanneKeskipelista();

        kayttoliittyma.run();
        
    }
    
    private void pelaaTUI() {
        while (eiVoittajaa()) {
            pelaaKierros();
        }
    }

    private void alustaGUIPeli() {
        voittoValta = 15;

        poyta = new Poyta(pelaajat);
    }
    
    private void alustaTUIPeli() {
        int pm = tui.selvitaPelaajienMaara();
        ArrayList<String> nimet = tui.selvitaPelaajienNimet(pm);
        luoPelaajat(nimet);
        
        voittoValta = tui.selvitaVoittoonTarvittavaValta();
         
    }

    private void alustaTestiTUIPeli() {
        GUIPeli = false;
        ArrayList<String> p = new ArrayList<>();
        p.add("varakas");
        p.add("homokaks");
        p.add("homo3");
        p.add("mr Gandalf");
        luoPelaajat(p);
        pelaajat.get(0).setKarkit(new int[]{3, 5, 5, 5, 5, 5});
        voittoValta = 5;
        poyta = new Poyta(pelaajat);
    }

    /**
     * Luo annettujen nimien mukaiset pelaajat.
     *
     * @param nimet pelaajien nimet.
     */
    public void luoPelaajat(ArrayList<String> nimet) {
        pelaajat = new ArrayList<>();
        for (int i = 0; i < nimet.size(); i++) {
            this.pelaajat.add(new Pelaaja(nimet.get(i)));
        }
    }

    /**
     * Luo pelaaja-oliot oletusnimillä.
     *
     * @param pelaajia kuinka monta pelaajaa luodaan.
     */
    public void luoPelaajat(int pelaajia) {
        pelaajat = new ArrayList<>();
        for (int i = 0; i < pelaajia; i++) {
            Pelaaja p = new Pelaaja("Pelaaja" + (i + 1));
            pelaajat.add(p);
        }
    }

    public ArrayList<Pelaaja> getPelaajat() {
        return pelaajat;
    }

    private boolean eiVoittajaa() {
        for (Pelaaja p : pelaajat) {
            if (p.voittaja(voittoValta)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Pelataan kaikkien pelaajien vuorot.
     */
    private void pelaaKierros() {
        kierros++;
        tui.tulostaKierroksenVaihto(kierros, voittoValta);
        for (Pelaaja p : pelaajat) {
            pelaaVuoro(p);
        }
    }

    /**
     * Pelataan yhden pelaajan vuoro alusta loppuun.
     *
     * @param pelaaja pelaaja kenen vuoro pelataan.
     */
    private void pelaaVuoro(Pelaaja pelaaja) {
        tui.tulostaVuoronAlkuinfot(pelaaja, poyta);

        int valinta = 0;
//        valinta = tui.pelaajanToimi(pelaaja.getNimi());
        valinta = kayttoliittyma.pelaajanToimi(pelaaja.getNimi());
        
        if (valinta == 1) { // nostetaan nallekarkkeja
            nostaNallekarkkeja(pelaaja);
        } else if (valinta == 2) { // ostetaan omaisuutta
            ostaOmaisuutta(pelaaja);
        } else if (valinta == 3) { // tehdään varaus pöydästä
            teeVaraus(pelaaja);
        }

        if (pelaaja.liikaaKarkkeja()) {
            //wip
        }
    }

    private void nostaNallekarkkeja(Pelaaja pelaaja) {
        int[] maarat = tui.mitaKarkkejaNostetaan(poyta.getMarkkinat());
        //testataan, halusiko pelaaja tehdä toisen toiminnon
        if (maarat == null) {
            pelaaVuoro(pelaaja);
            return;
        }

        Kasakokoelma karkit = pelaaja.getKarkit();
        for (int i = 0; i < maarat.length; i++) {
            int m = maarat[i];
            if (m > 0) {
                poyta.getMarkkinat().siirraToiseenKasaan(karkit, i + 1, m);
            }
        }
    }

    private void ostaOmaisuutta(Pelaaja pelaaja) {
        while (true) {
            int ostonNumero = tui.mikaOmistusOstetaan(poyta.getNakyvienNimet());

            if (ostonNumero == -1) {
                pelaaVuoro(pelaaja);
                return;
            }

            if (ostonNumero == 0) {
                ostaVaraus(pelaaja);
                return;
            } else if (poyta.suoritaOsto(pelaaja, ostonNumero)) {
                break;
            }
        }
    }

    private void ostaVaraus(Pelaaja pelaaja) {
        while (true) {
            int ostettava = tui.ostettavanVarauksenNro(pelaaja);

            if (ostettava == -1) {
                pelaaVuoro(pelaaja);
                return;
            }

            if (poyta.suoritaOstoVarauksista(pelaaja, ostettava)) {
                break;
            }
        }
    }

    private void teeVaraus(Pelaaja pelaaja) {
        while (true) {
            int varauksenNro = tui.mikaOmistusVarataan(poyta.getNakyvienNimet());

            if (varauksenNro == -1) {
                pelaaVuoro(pelaaja);
                return;
            }

            if (poyta.teeVaraus(pelaaja, varauksenNro)) {
                break;
            }
        }
    }

    /**
     * Pelipöytä välittää käyttäjän graafisessa käyttöliittymässä antaman
     * syötteen pelivelholle ja antaa luvan jatkaa peliä.
     *
     * @param syote kayttäjän antama syöte.
     */
    public void tekstiSyotePelaajalta(String syote) {
        this.syote = syote;
        pysahdy = false;
    }

    public void piirra(Graphics graphics, PiirtoAvustaja pa) {
        poyta.piirra(graphics, pa);
    }

}
