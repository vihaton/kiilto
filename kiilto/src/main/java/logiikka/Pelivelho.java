package logiikka;

import java.util.*;
import ui.*;
import logiikka.omaisuusluokat.*;
import logiikka.valineluokat.*;

/**
 *
 * @author xvixvi
 * 
 * Luokka vastaa pelin pyörittämisestä. Esimerkiksi pelin alustaminen, vuorojen organisoiminen
 * sekä UI.n ja logiikan rajapintana toimiminen ovat sen tehtäviä.
 * 
 */
public class Pelivelho {

    private TUI tui;
    private ArrayList<Pelaaja> pelaajat;
    private Poyta poyta;
    private int voittoValta;
    private int kierros = 0;

    public Pelivelho() {
        tui = new TUI(new Scanner(System.in));
    }

    /**
     * Alustaa ja pelaa kiilto-pelin.
     */
    public void pelaa() {
//        alustaPeli();
        alustaTestiPeli();
        
        while(eiVoittajaa()) {
            pelaaKierros();
        }
    }

    private void alustaTestiPeli() {
        ArrayList<String> p = new ArrayList<>();
        p.add("homo1"); p.add("homo2");
//        p.add("homo3");
//        p.add("mr Gandalf");
        luoPelaajat(p);
        voittoValta = 10;
        poyta = new Poyta(pelaajat);
    }
    
    public void alustaPeli() {
        int pm = tui.selvitaPelaajienMaara();
        ArrayList<String> nimet = tui.selvitaPelaajienNimet(pm);
//        voittoValta = tui.selvitaVoittoonTarvittavaValta();
        voittoValta=10;
        
        luoPelaajat(nimet);
        poyta = new Poyta(pelaajat);
    }

    public void luoPelaajat(ArrayList<String> nimet) {
        pelaajat = new ArrayList<>();
        for (int i = 0; i < nimet.size(); i++) {
            this.pelaajat.add(new Pelaaja(nimet.get(i)));
        }
    }

    private boolean eiVoittajaa() {
        for (Pelaaja p : pelaajat) {
            if (p.voittaja(voittoValta)) return false;
        }
        return true;
    }
    
    /**
     * Pelataan kaikkien pelaajien vuorot.
     */
    private void pelaaKierros() {
        kierros++;
        System.out.println("*******************");
        System.out.println("Kierros numero " + kierros +", voittamiseen tarvitaan " + voittoValta +" arvovaltapistettä.");
        System.out.println("*******************\n");
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
        System.out.println("Pelaajan " + pelaaja.getNimi() + " vuoro.\n");
        System.out.println(poyta);
        System.out.println(pelaaja);
        int valinta = tui.pelaajanToimi(pelaaja.getNimi());
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
        System.out.println("");
    }

    private void nostaNallekarkkeja(Pelaaja pelaaja) {
        int[] maarat = tui.mitaKarkkejaNostetaan(poyta.getMarkkinat());
        Kasakokoelma karkit = pelaaja.getKarkit();
        for (int i = 0; i < maarat.length; i++) {
            int m = maarat[i];
            if (m>0) {
                poyta.getMarkkinat().siirraToiseenKasaan(karkit, i+1, m);
            }
        }
    }

    private void ostaOmaisuutta(Pelaaja pelaaja) {
        while (true) {
            int ostonNumero = tui.mikaOmistusOstetaan(poyta.getNakyvienNimet());
            
            if (ostonNumero == 0) {
                if (ostaVaraus(pelaaja)) break;
            }
            else if (poyta.suoritaOsto(pelaaja, ostonNumero)) break;
            
            System.out.println("Sinulla ei ollut varaa moiseen! Mene töihin rikastumaan, lortto!\n");
        }
    }

    private void teeVaraus(Pelaaja pelaaja) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    private boolean ostaVaraus(Pelaaja pelaaja) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public ArrayList<Pelaaja> getPelaajat() {
        return pelaajat;
    }

}
