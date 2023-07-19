package ba.unsa.etf.rpr;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IspitGradTest {

    @Test
    void olimpijskiTest() {
        Drzava d = new Drzava(1, "Bosna i Hercegovina", null);
        d.setOlimpijska(true);
        Grad g = new Grad(100, "Sarajevo", 350000, d);
        assertFalse(g.isOlimpijski());
        g.setOlimpijski(true);
        assertTrue(g.isOlimpijski());
    }

    @Test
    void olimpijskiIzuzetakTest() {
        // Koristimo konstruktor sa pet parametar
        Drzava d = new Drzava(1, "Austrija", null, false);
        Grad g = new Grad(100, "Beč", 1899055, d);
        assertFalse(g.isOlimpijski());
        assertThrows(NotOlimpicCountryException.class, () -> g.setOlimpijski(true), "Država Austrija nije olimpijska država");
        assertFalse(g.isOlimpijski());
    }

    @Test
    void olimpijskiNullTest() {
        Grad g = new Grad(100, "Beč", 1899055, null);
        assertFalse(g.isOlimpijski());
        // Šta će se desiti kada je država null? Opet treba baciti izuzetak jer null nije olimpijska država
        assertThrows(NotOlimpicCountryException.class, () -> g.setOlimpijski(true));
        assertFalse(g.isOlimpijski());
    }

    @Test
    void olimpijskiCtorTest() {
        // I konstruktor klase Grad treba bacati izuzetak
        Drzava d = new Drzava(1, "Mađarska", null, false);
        assertThrows(NotOlimpicCountryException.class,
                () -> new Grad(100, "Budimpešta", 1752000, d, true),
                "Država Mađarska nije olimpijska država"
        );
    }
}