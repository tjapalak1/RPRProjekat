package ba.unsa.etf.rpr;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
public class IspitGradControllerSetTest {
    Stage theStage;
    GradController ctrl;
    Grad globalniGrad;

    @Start
    public void start(Stage stage) throws Exception {
        // Kreiramo formu sa popunjenim gradom
        GeografijaDAO dao = GeografijaDAO.getInstance();
        dao.vratiBazuNaDefault();

        // Postavljamo Veliku Britaniju za olimpijsku državu u bazi kako bismo mogli testirati
        Drzava vbr = dao.nadjiDrzavu("Velika Britanija");
        vbr.setOlimpijska(true);
        dao.izmijeniDrzavu(vbr);
        Grad london = new Grad(0, "London", 8825000, vbr, true);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/grad.fxml"));
        ctrl = new GradController(london, dao.drzave());
        loader.setController(ctrl);
        Parent root = loader.load();
        stage.setTitle("Grad");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.setResizable(false);
        stage.show();
        stage.toFront();
        theStage = stage;
    }

    @Test
    public void testIspravneVrijednosti(FxRobot robot) {
        CheckBox cbOlimpijski = robot.lookup("#cbOlimpijski").queryAs(CheckBox.class);
        assertTrue(cbOlimpijski.isSelected());

        // Proglašavamo da nije olimpijski
        robot.clickOn("#cbOlimpijski");

        // Klik na dugme ok
        robot.clickOn("#btnOk");

        Grad london = ctrl.getGrad();
        assertEquals("London", london.getNaziv());
        assertEquals(8825000, london.getBrojStanovnika());
        assertEquals("Velika Britanija", london.getDrzava().getNaziv());
        assertFalse(london.isOlimpijski());
    }
}
