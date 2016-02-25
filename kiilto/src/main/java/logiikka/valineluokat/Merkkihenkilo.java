package logiikka.valineluokat;

import java.awt.Color;
import java.awt.Graphics;
import logiikka.omaisuusluokat.*;
import ui.gui.piirtaminen.Piirtoavustaja;

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
     * @param nimi nimi.
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
     * @param nimi nimi.
     * @param omaisuusvaatimus kuuden kokoisessa taulukossa.
     */
    public Merkkihenkilo(String nimi, int[] ov) {
        this(nimi, ov, 3);
    }

    public Merkkihenkilo(int[] ov) {
        this("homo", ov, 3);
    }

    public int getArvovaltalisa() {
        return arvovaltalisa;
    }

    public String getNimi() {
        return nimi;
    }

    /**
     * Tietynvärisen omaisuusvaatimuksen hakemiseksi.
     *
     * @param i indeksi, mitä vaatimusta halutaan (kul, val, sin, vih, pun,
     * mus).
     * @return vaatimus.
     */
    public int getOmaisuusvaatimusVarilla(int i) {
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
}
