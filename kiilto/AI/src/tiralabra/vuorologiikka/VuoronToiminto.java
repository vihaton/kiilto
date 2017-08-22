package tiralabra.vuorologiikka;

/**
 * Created by vili on 11.8.2017.
 */
public enum VuoronToiminto {

    NOSTA("nosta"),
    NOSTA_KOLME("nosta kolme"),
    NOSTA_KAKSI("nosta kaksi"),
    VARAA_HALPA("varaa halpa"),
    VARAA_ARVOKAS("varaa arvokas"),
    OSTA_POYDASTA("osta poydasta"),
    OSTA_VARAUS("osta kadesta"),
    ENTEEMITAAN("en tee mitään");

    private final String toiminto;

    VuoronToiminto(String toiminto) {
        this.toiminto = toiminto;
    }


}
