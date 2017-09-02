package tiralabra.tietorakenteet;

/**
 * Created by vili on 22.8.2017.
 */
public enum Strategia {

    OLETUS(new VuoronToiminto[]{
        VuoronToiminto.OSTA_VARAUS,
        VuoronToiminto.NOSTA_KOLME,
        VuoronToiminto.VARAA_ARVOKAS,
        VuoronToiminto.OSTA_POYDASTA,
        VuoronToiminto.NOSTA_KAKSI,
        VuoronToiminto.VARAA_HALPA,
        VuoronToiminto.NOSTA,
        VuoronToiminto.ENTEEMITAAN
    }),
    HAMSTRAA_KARKKEJA(new VuoronToiminto[]{
        VuoronToiminto.NOSTA_KOLME,
        VuoronToiminto.VARAA_HALPA,
        VuoronToiminto.NOSTA_KAKSI,
        VuoronToiminto.VARAA_ARVOKAS,
        VuoronToiminto.NOSTA,
        VuoronToiminto.OSTA_VARAUS,
        VuoronToiminto.OSTA_POYDASTA,
        VuoronToiminto.ENTEEMITAAN
    }),
    HAMSTRAA_OMISTUKSIA(new VuoronToiminto[]{
        VuoronToiminto.OSTA_VARAUS,
        VuoronToiminto.OSTA_POYDASTA,
        VuoronToiminto.NOSTA_KOLME,
        VuoronToiminto.VARAA_HALPA,
        VuoronToiminto.VARAA_ARVOKAS,
        VuoronToiminto.NOSTA_KAKSI,
        VuoronToiminto.NOSTA,
        VuoronToiminto.ENTEEMITAAN
    }),
    TEHOKAS(new VuoronToiminto[]{
        VuoronToiminto.OSTA_VARAUS,
        VuoronToiminto.OSTA_POYDASTA,
        VuoronToiminto.NOSTA_KOLME,
        VuoronToiminto.VARAA_ARVOKAS,
        VuoronToiminto.VARAA_HALPA,
        VuoronToiminto.NOSTA_KAKSI,
        VuoronToiminto.NOSTA,
        VuoronToiminto.ENTEEMITAAN
    });

    private VuoronToiminto[] strategia;
    public static Strategia[] strategiat = new Strategia[]{
            HAMSTRAA_OMISTUKSIA, TEHOKAS, OLETUS, HAMSTRAA_KARKKEJA
    };

    Strategia(VuoronToiminto[] strategia) {
        this.strategia = strategia;
    }

    public int kuinkaMontaVaihtoehtoa() {
        return this.strategia.length;
    }

    public VuoronToiminto getToiminto(int i) {
        return strategia[i];
    }
}
