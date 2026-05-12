package cr.ac.una.unaplanilla.controller;

import cr.ac.una.unaplanilla.util.FlowController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author USUARIO UNA PZ
 */
public class PrimaryController extends Controller implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
