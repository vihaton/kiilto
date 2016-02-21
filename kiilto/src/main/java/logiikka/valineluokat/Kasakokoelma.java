package logiikka.valineluokat;

import java.awt.Graphics;
import java.util.*;
import logiikka.valineluokat.*;
import ui.gui.PiirtoAvustaja;

/**
 *
 * @author xvixvi
 */
public class Kasakokoelma {

    //0-kul, 1-val, 2-sin, 3-vih, 4-pun, 5-mus

    private Nallekarkkikasa[] kasat = new Nallekarkkikasa[6];

    //konstruktori pelaajien karkeille ja pöydän karkkimarkkinoille
    public Kasakokoelma(int pelaajia) { // 0 -> tyhjät kasat pelaajille, 2-4 -> pelaajien lukumäärän mukaiset markkinat
        int karkkeja = 0;
        if (pelaajia == 2) {
            karkkeja = 4;
        } else if (pelaajia == 3) {
            karkkeja = 5;
        } else if (pelaajia == 4) {
            karkkeja = 7;
        }

        for (int j = 0; j < 6; j++) {
            if (pelaajia > 1 && j == 0) { //lisätään kultakarkit markkinoille
                kasat[j] = new Nallekarkkikasa(0, 5);
                continue;
            }
            kasat[j] = new Nallekarkkikasa(j, karkkeja);
        }
    }

    public Kasakokoelma(int[] karkkeja) {
        if (karkkeja.length != 6) {
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < kasat.length; i++) {
            kasat[i] = new Nallekarkkikasa(i, karkkeja[i]);
        }
    }

    //konstruktori omistusten hintoja kuvaaville kasoille
    public Kasakokoelma(String[] palat) {
        kasat[0] = new Nallekarkkikasa(0);
        for (int i = 1; i < 6; i++) {
            kasat[i] = new Nallekarkkikasa(i, Integer.parseInt(palat[i + 2]));
        }
    }

    public int getKasanKoko(int kasanNro) {
        if (kasanNro < 0 || kasanNro > 5) {
            return 0;
        }
        return kasat[kasanNro].getKoko();
    }

    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < kasat.length - 1; i++) {
            s = s.concat(kasat[i].toString() + ", ");
        }
        s = s.concat(kasat[5].toString());
        return s;
    }

    public String toStringIlmanKultaa() {
        String s = "";
        for (int i = 1; i < kasat.length - 1; i++) {
            s = s.concat(kasat[i].toString() + ", ");
        }
        s = s.concat(kasat[5].toString());
        return s;
    }

    public void kasvataKasaa(int i, int maara) {
        if (maara < -this.getKasanKoko(i)) {
            kasat[i].kasvata(-kasat[i].getKoko());
        } else {
            kasat[i].kasvata(maara);
        }
    }

    public void siirraToiseenKasaan(Kasakokoelma karkit, int i, int m) {
        if (m > this.getKasanKoko(i)) {
            //jos ei ole varaa siirtää niin paljon, siirretään kaikki muttei enempää;
            m = this.getKasanKoko(i);
        }
        this.kasvataKasaa(i, -m);
        karkit.kasvataKasaa(i, m);
    }

    public int getKarkkienMaara() {
        int summa = 0;
        for (Nallekarkkikasa kasa : kasat) {
            summa += kasa.getKoko();
        }
        return summa;
    }

    public void piirraIsosti(Graphics graphics, PiirtoAvustaja pa, int x, int y) {
        for (Nallekarkkikasa kasa : kasat) {
            kasa.piirraIsosti(graphics, pa, x, y);
            y += 65;
        }
    }
}
