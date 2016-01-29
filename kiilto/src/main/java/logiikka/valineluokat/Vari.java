package logiikka.valineluokat;

/**
 *
 * @author xvixvi
 */
public enum Vari {

    KULTAINEN("kultainen"),
    VALKOINEN("valkoinen"),
    SININEN("sininen"),
    VIHREA("vihrea"),
    PUNAINEN("punainen"),
    MUSTA("musta");

    private final String nimi;

    Vari(String name) {
        this.nimi = name;
    }
}
