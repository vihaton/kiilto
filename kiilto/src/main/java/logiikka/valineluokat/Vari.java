package logiikka.valineluokat;

/**
 *
 * @author xvixvi
 */
public enum Vari {

    KULTAINEN("kultainen"),
    MUSTA("musta"),
    VALKOINEN("valkoinen"),
    SININEN("sininen"),
    PUNAINEN("punainen"),
    VIHREA("vihrea");

    private final String nimi;

    Vari(String name) {
        this.nimi = name;
    }  
}
