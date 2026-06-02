package cr.ac.una.unaplanilla.controller;

import cr.ac.una.unaplanilla.model.EmpleadoDto;
import cr.ac.una.unaplanilla.service.EmpleadoService;
import cr.ac.una.unaplanilla.util.AppContext;
import cr.ac.una.unaplanilla.util.FlowController;
import cr.ac.una.unaplanilla.util.Formato;
import cr.ac.una.unaplanilla.util.Mensaje;
import cr.ac.una.unaplanilla.util.Respuesta;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
    @FXML
    private MFXTextField txtUsuario;
    @FXML
    private MFXPasswordField txtClave;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //TODO
        imvFondo.fitHeightProperty().bind(root.heightProperty());
        imvFondo.fitWidthProperty().bind(root.widthProperty());
        
        txtUsuario.delegateSetTextFormatter(Formato.getInstance().letrasFormat(30));
        txtClave.delegateSetTextFormatter(Formato.getInstance().letrasFormat(30));
        
        txtUsuario.clear();
        txtClave.clear();
        txtUsuario.requestFocus();
    }    

    @FXML
    private void onActionBtnCancelar(ActionEvent event) {
        ((Stage)btnCancelar.getScene().getWindow()).hide(); // .close();
    }

    @FXML
    private void onActionBtnIngresar(ActionEvent event) {
        
        if (txtUsuario.getText().isBlank() || txtClave.getText().isBlank()) {
            new Mensaje().showModal(Alert.AlertType.WARNING, "Iniciar sesión", getStage(), "Debe ingresar usuario y clave.");
            return;
        }

        // Llamar al service
        EmpleadoService empleadoService = new EmpleadoService();
        Respuesta respuesta = empleadoService.login(
                txtUsuario.getText().trim(),
                txtClave.getText()
        );
        
        if (respuesta.getEstado()) {
            EmpleadoDto empleadoLogueado = (EmpleadoDto) respuesta.getResultado("Empleado");
            AppContext.getInstance().set("empleado", empleadoLogueado);
            FlowController.getInstance().goMain();
            onActionBtnCancelar(null);
        } else {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Iniciar sesión", getStage(), respuesta.getMensaje());
        }
    }

    @Override
    public void initialize() {}

    @FXML
    private void onKeyPressedEnterUsuario(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            if (!txtClave.getText().isBlank()) {
                onActionBtnIngresar(null);
            } else {
                txtClave.requestFocus();
            }
        }
    }

    @FXML
    private void onKeyPressedEnterClave(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            if (!txtUsuario.getText().isBlank()) {
                onActionBtnIngresar(null);
            } else {
                txtUsuario.requestFocus();
            }
        }
    }
    
}
