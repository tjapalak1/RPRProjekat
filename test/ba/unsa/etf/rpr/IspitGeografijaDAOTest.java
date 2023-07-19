package ba.unsa.etf.rpr;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

// Provjera da li se letovi korektno evidentiraju u bazi

public class IspitGeografijaDAOTest {
    GeografijaDAO dao = GeografijaDAO.getInstance();

    @BeforeEach
    public void resetujBazu() throws SQLException {
        dao.vratiBazuNaDefault();
    }

    @Test
    void testDodajOlimpijskuDrzavu() {
        Grad london = dao.nadjiGrad("London");
        assertFalse(london.isOlimpijski());

        Drzava d = new Drzava(1, "Bosna i Hercegovina", london);
        d.setOlimpijska(true);
        dao.dodajDrzavu(d);

        // Uzimam drugu verziju za BiH
        Drzava bih = dao.nadjiDrzavu("Bosna i Hercegovina");
        assertTrue(bih.isOlimpijska());
    }

    @Test
    void testDodajOlimpijskiGrad() {
        Drzava francuska = dao.nadjiDrzavu("Francuska");
        assertFalse(francuska.isOlimpijska());
        francuska.setOlimpijska(true);
        dao.izmijeniDrzavu(francuska);

        Grad chamonix = new Grad(0, "Chamonix", 8611, francuska);
        chamonix.setOlimpijski(true);
        dao.dodajGrad(chamonix);

        // Uzimam drugu verziju države i grada
        Drzava francuska2 = dao.nadjiDrzavu("Francuska");
        assertTrue(francuska2.isOlimpijska());
        Grad chamonix2 = dao.nadjiGrad("Chamonix");
        assertTrue(chamonix2.isOlimpijski());
    }

    @Test
    void testIzmijeniOlimpijskiGrad() {
        Drzava vbr = dao.nadjiDrzavu("Velika Britanija");
        assertFalse(vbr.isOlimpijska());
        vbr.setOlimpijska(true);
        dao.izmijeniDrzavu(vbr);

        Grad london = dao.nadjiGrad("London");
        assertFalse(london.isOlimpijski());
        london.setOlimpijski(true);
        dao.izmijeniGrad(london);

        // Uzimam drugu verziju države i grada
        Drzava vbr2 = dao.nadjiDrzavu("Velika Britanija");
        assertTrue(vbr2.isOlimpijska());
        Grad london2 = dao.nadjiGrad("London");
        assertTrue(london2.isOlimpijski());
    }

}
