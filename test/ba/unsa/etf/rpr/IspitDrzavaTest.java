package ba.unsa.etf.rpr;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IspitDrzavaTest {

    @Test
    void olimpijskaDrzavaTest() {
        Drzava d = new Drzava(1, "Bosna i Hercegovina", null);
        assertFalse(d.isOlimpijska());
        d.setOlimpijska(true);
        assertTrue(d.isOlimpijska());
    }
}
