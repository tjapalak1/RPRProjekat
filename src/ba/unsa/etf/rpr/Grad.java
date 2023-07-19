package ba.unsa.etf.rpr;

public class Grad {
    private int id;
    private String naziv;
    private int brojStanovnika;
    private Drzava drzava;
    private boolean olimpijski;

    public Grad(int id, String naziv, int brojStanovnika, Drzava drzava) {
        this.id = id;
        this.naziv = naziv;
        this.brojStanovnika = brojStanovnika;
        this.drzava = drzava;
    }

    public Grad(int id, String naziv, int brojStanovnika, Drzava drzava, boolean olimpijski) {
        this.id = id;
        this.naziv = naziv;
        this.brojStanovnika = brojStanovnika;
        this.drzava = drzava;
        if (olimpijski && (drzava == null || !drzava.isOlimpijska()))
            throw new NotOlimpicCountryException("Drzava " + drzava + " nije pomorska država");
        this.olimpijski = olimpijski;
    }

    public Grad() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public int getBrojStanovnika() {
        return brojStanovnika;
    }

    public void setBrojStanovnika(int brojStanovnika) {
        this.brojStanovnika = brojStanovnika;
    }

    public Drzava getDrzava() {
        return drzava;
    }

    public void setDrzava(Drzava drzava) {
        this.drzava = drzava;
    }

    public boolean isOlimpijski() {
        return olimpijski;
    }

    public void setOlimpijski(boolean olimpijski) {
        if (olimpijski && (drzava == null || !drzava.isOlimpijska()))
            throw new NotOlimpicCountryException("Drzava " + drzava + " nije pomorska država");
        this.olimpijski = olimpijski;
    }

    @Override
    public String toString() { return naziv; }
}
