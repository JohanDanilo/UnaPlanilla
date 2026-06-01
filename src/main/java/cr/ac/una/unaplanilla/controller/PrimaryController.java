package cr.ac.una.unaplanilla.controller;

import cr.ac.una.unaplanilla.model.EmpleadoDto;
import cr.ac.una.unaplanilla.util.AppContext;
import cr.ac.una.unaplanilla.util.FlowController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author USUARIO UNA PZ
 */
public class PrimaryController extends Controller implements Initializable {

    @FXML
    private ImageView onActionBtnCasa;
    @FXML
    private Label userLbl;
    @FXML
    private StackPane contenedor;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        EmpleadoDto empleado = (EmpleadoDto) AppContext.getInstance().get("empleado");
        if (empleado != null) {
            userLbl.setText(empleado.getUsuario());
        }
    }    

    @Override
    public void initialize() {}

    @FXML
    private void onActionBtnEmpleados(ActionEvent event) {
        FlowController.getInstance().goView("EmpleadosView");
    }

    @FXML
    private void onActionBtnTipoPlanilla(ActionEvent event) {
        FlowController.getInstance().goView("TiposPlanillaView");
    }
    

    @FXML
    private void onActionBtnCerrar(ActionEvent event){
        
        FlowController.getInstance().limpiarLoader("LoginView");
        getStage().close();
        FlowController.getInstance().goViewInWindow("LoginView");
}
    
    @FXML
    private void onActionBtnSalir(ActionEvent event) {
        FlowController.getInstance().salir();
    }
    
}
