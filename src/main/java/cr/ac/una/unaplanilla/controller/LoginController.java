package cr.ac.una.unaplanilla.controller;

import cr.ac.una.unaplanilla.util.FlowController;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author USUARIO UNA PZ
 */
public class LoginController extends Controller implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private ImageView imvFondo;
    
    @FXML
    private AnchorPane root;
    @FXML
    private MFXButton btnCancelar;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //TODO
        imvFondo.fitHeightProperty().bind(root.heightProperty());
        imvFondo.fitWidthProperty().bind(root.widthProperty());
    }    

    @FXML
    private void onActionBtnCancelar(ActionEvent event) {
        ((Stage)btnCancelar.getScene().getWindow()).hide(); // .close();
    }

    @FXML
    private void onActionBtnIngresar(ActionEvent event) {
        FlowController.getInstance().goMain();
        onActionBtnCancelar(null);
    }

    @Override
    public void initialize() {}
    
}
