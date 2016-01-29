
package logiikka.valineluokat;

import java.util.*;
import logiikka.valineluokat.*;

/**
 *
 * @author xvixvi
 */
public class Kasakokoelma {
    private Nallekarkkikasa[] kasat = new Nallekarkkikasa[6];

    //konstruktori pelaajien karkeille ja pöydän karkkimarkkinoille
    public Kasakokoelma(int pelaajia) { // 0 -> tyhjät kasat pelaajille, 2-4 -> pelaajien lukumäärän mukaiset markkinat
        int karkkeja = 0;
        if (pelaajia == 2) karkkeja = 4;
        else if (pelaajia == 3) karkkeja = 5;
        else if (pelaajia == 4) karkkeja = 7;
        
        for (int j = 0; j < 6; j++) {
            if (pelaajia > 1 && j==0) { //lisätään kultakarkit markkinoille
                kasat[j] = new Nallekarkkikasa(0, 5);
                continue;
            }
            kasat[j] = new Nallekarkkikasa(j, karkkeja);
        }
    }

    //konstruktori omistusten hintoja kuvaaville kasoille
    public Kasakokoelma(String[] palat) {
        kasat[0] = new Nallekarkkikasa(0);
        for (int i = 1; i < 6; i++) {
            kasat[i] = new Nallekarkkikasa(i, Integer.parseInt(palat[i+2]));
        }
    }
    
    public int getKasanKoko(int kasanNro) {
        if (kasanNro<0||kasanNro>5) return 0;
        return kasat[kasanNro].getKoko();
    }

    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < kasat.length-1; i++) {
            s = s.concat(kasat[i].toString() + ", ");
        }
        s = s.concat(kasat[5].toString());
        return s;
    }
}
