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
public class IspitDrzavaControllerSetTest {
    Stage theStage;
    DrzavaController ctrl;

    @Start
    public void start(Stage stage) throws Exception {
        GeografijaDAO dao = GeografijaDAO.getInstance();
        dao.vratiBazuNaDefault();
        Drzava vbr = dao.nadjiDrzavu("Velika Britanija");
        vbr.setOlimpijska(true);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/drzava.fxml"));
        ctrl = new DrzavaController(vbr, dao.gradovi());
        loader.setController(ctrl);
        Parent root = loader.load();
        stage.setTitle("Država");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.setResizable(false);
        stage.show();
        stage.toFront();
        theStage = stage;
    }

    @Test
    public void testIspravneVrijednosti(FxRobot robot) {
        CheckBox cbOlimpijska = robot.lookup("#cbOlimpijska").queryAs(CheckBox.class);
        assertTrue(cbOlimpijska.isSelected());

        // Proglašavamo da nije olimpijska
        robot.clickOn("#cbOlimpijska");

        // Klik na dugme ok
        robot.clickOn("#btnOk");

        Drzava vbr = ctrl.getDrzava();
        assertEquals("Velika Britanija", vbr.getNaziv());
        assertFalse(vbr.isOlimpijska());
    }
}
