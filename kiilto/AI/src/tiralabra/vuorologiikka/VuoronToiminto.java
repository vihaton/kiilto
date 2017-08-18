package tiralabra.vuorologiikka;

/**
 * Created by vili on 11.8.2017.
 */
public enum VuoronToiminto {

    NOSTA("nosta"),
    VARAA("varaa"),
    OSTA("osta"),
    ENTEEMITAAN("en tee mitään");

    private final String toiminto;

    VuoronToiminto(String toiminto) {
        this.toiminto = toiminto;
    }


}
