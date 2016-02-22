package logiikka.valineluokat;

import java.awt.Color;
import java.awt.Graphics;
import logiikka.omaisuusluokat.*;
import ui.gui.PiirtoAvustaja;

/**
 *
 * @author xvixvi
 *
 * Merkkihenkilot tulevat pelaajien luokse kyläilemään, jos pelaajalla tarpeeksi
 * monipuolisesti omaisuutta. Merkkihenkilön vierailu antaa pelaajalle lisää
 * arvovaltaa, ja ehkä kupan.
 */
public class Merkkihenkilo {
    
    private String nimi;
    private int[] omaisuusvaatimus;
    private int arvovaltalisa;

    /**
     *
     * @param ov int[] jossa omaisuusvaatimus (kul,val,sin,vih,pun,mus).
     * @param arvo Merkkihenkilön vierailun pelaajalle antama lisäarvovalta.
     */
    public Merkkihenkilo(String nimi, int[] ov, int arvo) {
        if (ov.length != 6) {
            throw new IllegalArgumentException("taulukko ei ole koko != 6.");
        }
        this.omaisuusvaatimus = ov;
        arvovaltalisa = arvo;
        this.nimi = nimi;
    }

    /**
     * Konstruktori, joka luo arvovaltalisäykseltään 3 arvoisen merkkihenkilön.
     *
     * @param omaisuusvaatimus kuuden kokoisessa taulukossa.
     */
    public Merkkihenkilo(String nimi, int[] ov) {
        this(nimi, ov, 3);
    }
    
    public Merkkihenkilo(int[] ov) {
        this("homo", ov, 3);
    }
    
    public int getArvo() {
        return arvovaltalisa;
    }
    
    public String getNimi() {
        return nimi;
    }

    /**
     *
     *
     * @param i indeksi, mitä vaatimusta halutaan (kul, val, sin, vih, pun,
     * mus).
     * @return vaatimus.
     */
    public int getVaatimus(int i) {
        if (i < 0 || i > 5) {
            return 0;
        }
        return omaisuusvaatimus[i];
    }

    /**
     * Metodi merkkihenkilön mielenliikkeiden selvittämiseksi.
     *
     * @param omaisuus omaisuus jolla yritetään houkutella merkkihenkilö kylään.
     * @return vaikuttuuko merkkihenkilö.
     */
    public boolean vaikuttuukoOmaisuudesta(Omaisuus o) {
        int[] omaisuusBonukset = o.getOmaisuudestaTulevatBonusKarkit();
        
        for (int i = 0; i < omaisuusBonukset.length; i++) {
            if (omaisuusBonukset[i] < omaisuusvaatimus[i]) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public String toString() {
        String vaatimus = "";
        for (int i = 1; i < 5; i++) {
            vaatimus = vaatimus.concat("" + omaisuusvaatimus[i]) + "-";
        }
        vaatimus += "" + omaisuusvaatimus[5];
        return "'olen " + nimi + ", arvovallaltani " + arvovaltalisa + " ja vaadin kauppiaalta omaisuutta mallia\n"
                + vaatimus + "'\n";
    }

    /**
     * Piirtää merkkihenkilön annettuihin koordinaatteihin.
     *
     * @param graphics grafiikat.
     * @param pa varin asettaja auttaa helpottaa nallekarkkien värien
     * asettamisessa.
     * @param x
     * @param y
     */
    public void piirra(Graphics graphics, PiirtoAvustaja pa, int x, int y) {
        graphics.setColor(Color.darkGray);
        graphics.fill3DRect(x, y, 90, 100, true);
        graphics.setColor(Color.yellow);
        graphics.draw3DRect(x, y, 90, 100, true);
        
        pa.piirraNimi(graphics, nimi, x + 40, y + 14);
        pa.piirraArvovalta(graphics, arvovaltalisa, x + 5, y + 5);
        piirraOmaisuusvaatimus(graphics, pa, x + 10, y + 25);
    }
    
    private void piirraOmaisuusvaatimus(Graphics graphics, PiirtoAvustaja va, int x, int y) {
        for (int i = 1; i < 6; i++) {
            va.asetaVari(graphics, i);
            graphics.drawString("" + omaisuusvaatimus[i], x, y + i * 14);
        }
    }
}
