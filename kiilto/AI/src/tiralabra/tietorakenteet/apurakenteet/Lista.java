package tiralabra.tietorakenteet.apurakenteet;

/**
 * Created by vili on 23.8.2017.
 */
public class Lista<E> {

    private Object[] dataelementti;
    private int koko = 0;

    public Lista() {
        this.dataelementti = new Object[10];
    }

    public void lisaa(E asia) {
        if (!onkoTilaa(koko + 1)) {
            tuplaaPituus();
        }
        dataelementti[koko++] = asia;
    }

    public E otaPeralta() {
        return (E) dataelementti[koko--];
    }

    public E otaEnsimmainen() {
        return (E) dataelementti[0];
    }

    /**
     *
     * @param indeksi
     * @return objekti jos on mitä antaa, null jos ei ole
     */
    public E otaIndeksista(int indeksi) {
        if (onkoObjectia(indeksi)) {
            return (E) dataelementti[indeksi];
        } else
            return null;
    }

    private void tuplaaPituus() {
        Object[] uusiTaulukko = new Object[2 * koko];
        dataelementti = kopioiUuteen(uusiTaulukko, dataelementti);
    }

    private boolean onkoTilaa(int pituus) {
        return dataelementti.length > pituus;
    }

    private static Object[] kopioiUuteen(Object[] minne, Object[] mista) {
        for (int i = 0; i < mista.length; i++) {
            minne[i] = mista[i];
        }
        return minne;
    }

    private boolean onkoObjectia(int indeksissa) {
        return indeksissa >= 0 && indeksissa <= koko;
    }
}
