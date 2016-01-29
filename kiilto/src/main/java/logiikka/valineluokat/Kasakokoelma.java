
package logiikka.valineluokat;

import java.util.*;
import logiikka.valineluokat.*;

/**
 *
 * @author xvixvi
 */
public class Kasakokoelma {
    private Nallekarkkikasa[] kasat;

    //konstruktori pelaajien karkeille ja pöydän karkkimarkkinoille
    public Kasakokoelma(int pelaajia) { // 0 -> tyhjät kasat pelaajille, 2-4 -> pelaajien lukumäärän mukaiset markkinat
        kasat = new Nallekarkkikasa[6];
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
    public Kasakokoelma(int val, int sin, int vih, int pun, int mus)  {
        kasat = new Nallekarkkikasa[6]; //hinnoissa ei ole kultaisia, kasat[0] == null
        kasat[1] = new Nallekarkkikasa(1, val);
        kasat[2] = new Nallekarkkikasa(2, sin);
        kasat[3] = new Nallekarkkikasa(3, vih);
        kasat[4] = new Nallekarkkikasa(4, pun);
        kasat[5] = new Nallekarkkikasa(5, mus);
    }

}
