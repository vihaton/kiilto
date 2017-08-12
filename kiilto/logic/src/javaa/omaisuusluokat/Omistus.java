package javaa.omaisuusluokat;

import javaa.valineluokat.Kasakokoelma;
import javaa.valineluokat.Vari;

/**
 * Kuvaa pelin omistuskorttia.
 *
 * @author xvixvi
 */
public class Omistus {

    private String nimi;
    private Kasakokoelma hinta;
    private Vari lisaKarkinVari;
    private int arvovalta;

    /**
     * Luo omistuksen.
     *
     * @param n nimi.
     * @param arvo paljonko arvovaltaa antaa.
     * @param vari väri.
     * @param h hinta.
     */
    public Omistus(String n, int arvo, Vari vari, Kasakokoelma h) {
        this.nimi = n;
        this.hinta = h;
        this.lisaKarkinVari = vari;
        arvovalta = arvo;
    }

    /**
     * Luo omistuksen.
     *
     * @param n nimi.
     * @param arvo arvovalta.
     * @param vari vari.
     * @param h hinta.
     */
    public Omistus(String n, int arvo, String vari, Kasakokoelma h) {
        this(n, arvo, Vari.valueOf(vari.toUpperCase()), h);
    }

    /**
     * Luo omistuksen.
     *
     * @param n nimi.
     * @param arvo arvovalta.
     * @param vari vari.
     * @param h hinta.
     */
    public Omistus(String n, int arvo, int vari, Kasakokoelma h) {
        this(n, arvo, Vari.values()[vari], h);
    }

    /**
     * Luo omistuksen.
     *
     * @param n nimi.
     * @param arvo arvovalta.
     * @param vari vari.
     * @param karkkeja hinta.
     */
    public Omistus(String n, int arvo, int vari, int[] karkkeja) {
        this(n, arvo, vari, new Kasakokoelma(karkkeja));
    }

    public Kasakokoelma getHintaKasat() {
        return hinta;
    }

    /**
     * Veikkaapa.
     *
     * @param n Veikkaapa.
     * @return Veikkaapa.
     */
    public int getKasanKoko(int n) {
        if (n < 1 || n > 5) {
            return 0;
        }
        return hinta.getKasanKoko(n);
    }

    public Vari getLisaKarkinVari() {
        return lisaKarkinVari;
    }

    public int getLisaKarkinVariNumerona() {
        return lisaKarkinVari.ordinal();
    }

    public int getArvovalta() {
        return arvovalta;
    }

    @Override
    public String toString() {
        return "Omistus nro " + nimi + "\n"
                + arvovalta + ", " + lisaKarkinVari + "\n"
                + hinta.toStringIlmanKultaa();
    }

    public String getNimi() {
        return this.nimi;
    }
}
