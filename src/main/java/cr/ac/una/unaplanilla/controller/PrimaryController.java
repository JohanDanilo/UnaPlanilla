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
    
    // Agrega este método dentro de tu clase PrimaryController

    @FXML
    private void onActionBtnCerrar(ActionEvent event){
        /*
         * TODO: Cuando haya sesión real, limpia también el objeto Usuario:
         *   AppContext.getInstance().delete("usuario");
         */
        
        FlowController.getInstance().limpiarLoader("LoginView");
        getStage().close();  // en lugar de this.stage.close()
        FlowController.getInstance().goViewInWindow("LoginView");
}
    
    @FXML
    private void onActionBtnSalir(ActionEvent event) {
        FlowController.getInstance().salir();
    }
    
}
