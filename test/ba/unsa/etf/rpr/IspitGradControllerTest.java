package ba.unsa.etf.rpr;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
public class IspitGradControllerTest {
    Stage theStage;
    GradController ctrl;

    @Start
    public void start(Stage stage) throws Exception {
        GeografijaDAO dao = GeografijaDAO.getInstance();
        dao.vratiBazuNaDefault();

        // Postavljamo Veliku Britaniju za olimpijsku državu u bazi kako bismo mogli testirati
        Drzava vbr = dao.nadjiDrzavu("Velika Britanija");
        vbr.setOlimpijska(true);
        dao.izmijeniDrzavu(vbr);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/grad.fxml"));
        ctrl = new GradController(null, dao.drzave());
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
    public void testPoljaPostoje(FxRobot robot) {
        CheckBox cbOlimpijski = robot.lookup("#cbOlimpijski").queryAs(CheckBox.class);
        assertFalse(cbOlimpijski.isSelected());
    }

    @Test
    public void testVracanjeGrada(FxRobot robot) {
        // Upisujemo grad
        robot.clickOn("#fieldNaziv");
        robot.write("London");
        robot.clickOn("#fieldBrojStanovnika");
        robot.write("8825000");
        robot.clickOn("#choiceDrzava");
        robot.clickOn("Velika Britanija");

        // Jeste olimpijski
        robot.clickOn("#cbOlimpijski");

        // Klik na dugme ok
        robot.clickOn("#btnOk");

        Grad london = ctrl.getGrad();
        assertEquals("London", london.getNaziv());
        assertEquals(8825000, london.getBrojStanovnika());
        assertEquals("Velika Britanija", london.getDrzava().getNaziv());
        assertTrue(london.isOlimpijski());
    }

    @Test
    public void testNijeOlimpijska(FxRobot robot) {
        // Upisujemo grad
        robot.clickOn("#fieldNaziv");
        robot.write("Beč");
        robot.clickOn("#fieldBrojStanovnika");
        robot.write("1899055");
        robot.clickOn("#choiceDrzava");
        robot.clickOn("Austrija");

        // Jeste olimpijski
        robot.clickOn("#cbOlimpijski");

        // Klik na dugme ok
        robot.clickOn("#btnOk");

        // Čekamo da dijalog postane vidljiv
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        robot.lookup(".dialog-pane").tryQuery().isPresent();

        // Provjera teksta
        DialogPane dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        assertNotNull(dialogPane.lookupAll("Država Austrija nije olimpijska država"));

        // Klik na dugme Ok
        Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        robot.clickOn(okButton);

        // Označavamo da ipak nije olimpijski
        robot.clickOn("#cbOlimpijski");
        robot.clickOn("#btnOk");

        // Provjera da nije luka
        Grad bech = ctrl.getGrad();
        assertEquals("Beč", bech.getNaziv());
        assertEquals(1899055, bech.getBrojStanovnika());
        assertEquals("Austrija", bech.getDrzava().getNaziv());
        assertFalse(bech.isOlimpijski());
    }
}