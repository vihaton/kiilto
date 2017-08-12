package javaa;

import java.util.ArrayList;

/**
 * Created by vili on 12.8.2017.
 *
 * Pelinpystyttäjä vastaa pelaajien ja pöydän generoimisesta. Toiminnallisuus oli aikaisemmin pelivelholla,
 * mutta nyt se on eriytetty selkeys- ja riippuvuussyistä eri moduuliin.
 */
public class Pelinpystyttaja {

    public ArrayList<Pelaaja> pelaajat;
    public Poyta poyta;
    public boolean[] onkoPelaajaAI;

    public Pelinpystyttaja(boolean[] onkoPelaajaAI) {
        pelaajat = new ArrayList<>();
        this.onkoPelaajaAI = onkoPelaajaAI;
        luoPelaajat(generoiNimet(onkoPelaajaAI));
        this.poyta = new Poyta(pelaajat);
    }


    /**
     *
     * @param onkoAI lista, mitkä pelaajat ovat tekoälyjä
     * @return nimet
     */
    public ArrayList<String> generoiNimet(boolean[] onkoAI) {
        ArrayList<String> nimet = new ArrayList<>();
        int tekoalyja = 0;
        for (int i = 0; i < onkoAI.length ; i++) {
            if (onkoAI[i]) {
                tekoalyja++;
                nimet.add("AI " + tekoalyja);
            } else {
                nimet.add("Pelaaja " + (i - tekoalyja + 1));
            }
        }
        return nimet;
    }

    /**
     * Luo annettujen nimien mukaiset pelaajat.
     *
     * @param nimet pelaajien nimet.
     */
    public void luoPelaajat(ArrayList<String> nimet) {
        for (int i = 0; i < nimet.size(); i++) {
            this.pelaajat.add(new Pelaaja(nimet.get(i)));
        }
    }

}
