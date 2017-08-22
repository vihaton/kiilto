package logiikka.valineluokat;

/**
 * Kuvaa karkkikasakokoelmaa.
 *
 * @author xvixvi
 */
public class Kasakokoelma {

    //0-kul, 1-val, 2-sin, 3-vih, 4-pun, 5-mus
    private Nallekarkkikasa[] kasat = new Nallekarkkikasa[6];

    /**
     * Luo kasakokoelman.
     *
     * @param pelaajia 0 -> tyhjät kasat pelaajille, 2-4 -> pelaajien lukumäärän
     * mukaiset markkinat pöydälle.
     */
    public Kasakokoelma(int pelaajia) {
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

    /**
     * Luo kasakokoelman, jossa on annettu määrä karkkeja.
     *
     * @param karkkeja no niitä just.
     */
    public Kasakokoelma(int[] karkkeja) {
        if (karkkeja.length != 6) {
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < kasat.length; i++) {
            kasat[i] = new Nallekarkkikasa(i, karkkeja[i]);
        }
    }

    /**
     * Luo kasakokoelman, jossa on annettu määrä karkkeja.
     *
     * @param palat luetut karkkimäärät.
     */
    public Kasakokoelma(String[] palat) {
        kasat[0] = new Nallekarkkikasa(0);
        for (int i = 1; i < 6; i++) {
            kasat[i] = new Nallekarkkikasa(i, Integer.parseInt(palat[i + 2]));
        }
    }

    /**
     * Veikkaapa.
     * @param kasanNro Veikkaapa.
     * @return Veikkaapa.
     */
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

    /**
     * Tekstiesitys nro 2.
     *
     * @return kasat tekstinä, ilman kultakasaa.
     */
    public String toStringIlmanKultaa() {
        String s = "";
        for (int i = 1; i < kasat.length - 1; i++) {
            s = s.concat(kasat[i].toString() + ", ");
        }
        s = s.concat(kasat[5].toString());
        return s;
    }

    /**
     * Kasvattaa tiettyä kasaa tietyn määrän. Ei voi tehdä kasasta negatiivisen
     * kokoista.
     *
     * @param i mikä kasa (0-5)
     * @param maara kuinka paljon kasvatetaan (myös negatiiviset arvot käyvät).
     */
    public void kasvataKasaa(int i, int maara) {
        if (maara < -this.getKasanKoko(i)) {
            kasat[i].kasvata(-kasat[i].getKoko());
        } else {
            kasat[i].kasvata(maara);
        }
    }

    /**
     * Siirtää tästä kasakokoelmasta karkkeja annettuun kasakokoelmaan.
     *
     * @param kohdekasat minne siirretään.
     * @param i mistä siirretään.
     * @param m paljonko siirretään (ei voi siirtää enempää, kuin kasasta
     * löytyy.
     */
    public void siirraToiseenKasaan(Kasakokoelma kohdekasat, int i, int m) {
        if (m > this.getKasanKoko(i)) {
            //jos ei ole varaa siirtää niin paljon, siirretään kaikki muttei enempää;
            m = this.getKasanKoko(i);
        }
        this.kasvataKasaa(i, -m);
        kohdekasat.kasvataKasaa(i, m);
    }

    /**
     * Veikkaapa.
     * @return Veikkaapa.
     */
    public int getKarkkienMaara() {
        int summa = 0;
        for (Nallekarkkikasa kasa : kasat) {
            summa += kasa.getKoko();
        }
        return summa;
    }

    /**
     * Veikkaapa.
     * @param i Veikkaapa.
     * @return Veikkaapa.
     */
    public Nallekarkkikasa getKasa(int i) {
        return kasat[i];
    }

    /**
     *
     * @return lue nimi
     */
    public int kuinkaMonessaTavallisessaKasassaOnKarkkeja() {
        int kuinkaMonessaKasassa = 0;
        for (int i = 1; i < 6; i++) {
            if (getKasanKoko(i) > 0) {
                kuinkaMonessaKasassa++;
            }
        }
        return kuinkaMonessaKasassa;
    }
}
