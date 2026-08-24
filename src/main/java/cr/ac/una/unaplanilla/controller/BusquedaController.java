package cr.ac.una.unaplanilla.controller;

import cr.ac.una.unaplanilla.model.EmpleadoDto;
import cr.ac.una.unaplanilla.model.TipoPlanillaDto;
import cr.ac.una.unaplanilla.service.EmpleadoService;
import cr.ac.una.unaplanilla.service.TipoPlanillaService;
import cr.ac.una.unaplanilla.util.Formato;
import cr.ac.una.unaplanilla.util.Mensaje;
import cr.ac.una.unaplanilla.util.Respuesta;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.enums.FloatMode;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author cbcar
 */
public class BusquedaController extends Controller implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private VBox vbxParametros;
    @FXML
    private MFXButton btnFiltrar;
    @FXML
    private Label lblTitulo;
    @FXML
    private TableView tbvResultados;
    @FXML
    private MFXButton btnAceptar;
    
    private EventHandler<KeyEvent> keyEnter;
    
    private Object resultado;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       keyEnter = (KeyEvent event) -> {
            if (event.getCode() == KeyCode.ENTER) {
                btnFiltrar.fire();
            }
        };
    }    

    @FXML
    private void OnMousePressedTbvResultados(MouseEvent event) {
        if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
            onActionBtnAceptar(null);
        }
    }

    @FXML
    private void onActionBtnAceptar(ActionEvent event) {
        resultado = tbvResultados.getSelectionModel().getSelectedItem();
        //getStage().getScene().setRoot(new Pane());
        getStage().close();
    }
    
    public Object getResultado() {
        return resultado;
    }
    
    public void busquedaEmpleados() {
        try {
            lblTitulo.setText("Búsqueda de Empleados");
            
            MFXTextField txtCedula = new MFXTextField();
            txtCedula.setFloatMode(FloatMode.INLINE);
            txtCedula.setMaxWidth(1.7976931348623157E308);
            txtCedula.setFloatingText("Cédula");
            txtCedula.setOnKeyPressed(keyEnter);
            txtCedula.delegateSetTextFormatter(Formato.getInstance().cedulaFormat(40));
            
            MFXTextField txtNombre = new MFXTextField();
            txtNombre.setFloatMode(FloatMode.INLINE);
            txtNombre.setMaxWidth(1.7976931348623157E308);
            txtNombre.setFloatingText("Nombre");
            txtNombre.delegateSetTextFormatter(Formato.getInstance().letrasFormat(30));
            txtNombre.setOnKeyPressed(keyEnter);
            
            MFXTextField txtPApellido = new MFXTextField();
            txtPApellido.setFloatMode(FloatMode.INLINE);
            txtPApellido.setMaxWidth(1.7976931348623157E308);
            txtPApellido.setFloatingText("Primer Apellido");
            txtPApellido.delegateSetTextFormatter(Formato.getInstance().letrasFormat(15));
            txtPApellido.setOnKeyPressed(keyEnter);
            
            MFXTextField txtSApellido = new MFXTextField();
            txtSApellido.setFloatMode(FloatMode.INLINE);
            txtSApellido.setMaxWidth(1.7976931348623157E308);
            txtSApellido.setFloatingText("Segundo Apellido");
            txtSApellido.delegateSetTextFormatter(Formato.getInstance().letrasFormat(15));
            txtSApellido.setOnKeyPressed(keyEnter);
            
            
            vbxParametros.getChildren().clear();
            vbxParametros.getChildren().add(txtCedula);
            vbxParametros.getChildren().add(txtNombre);
            vbxParametros.getChildren().add(txtPApellido);
            vbxParametros.getChildren().add(txtSApellido);

            tbvResultados.getColumns().clear();
            tbvResultados.getItems().clear();
            
            TableColumn<EmpleadoDto, String> tbcId = new TableColumn<>("Id");
            tbcId.setPrefWidth(0);
            tbcId.setCellValueFactory(cd -> cd.getValue().getIdProperty());
            
            TableColumn<EmpleadoDto, String> tbcCedula = new TableColumn<>("Cédula");
            tbcCedula.setPrefWidth(100);
            tbcCedula.setCellValueFactory(cd -> cd.getValue().getCedulaProperty());
            
            TableColumn<EmpleadoDto, String> tbcNombre = new TableColumn<>("Nombre");
            tbcNombre.setPrefWidth(150);
            tbcNombre.setCellValueFactory(cd -> cd.getValue().getNombreProperty());
            
            TableColumn<EmpleadoDto, String> tbcPApellido = new TableColumn<>("Primer Apellido");
            tbcPApellido.setPrefWidth(150);
            tbcPApellido.setCellValueFactory(cd -> cd.getValue().getPrimerApellidoProperty());
            
            TableColumn<EmpleadoDto, String> tbcSApellido = new TableColumn<>("Segundo Apellido");
            tbcSApellido.setPrefWidth(150);
            tbcSApellido.setCellValueFactory(cd -> cd.getValue().getSegundoApellidoProperty());
            
            
            tbvResultados.getColumns().add(tbcId);
            tbvResultados.getColumns().add(tbcCedula);
            tbvResultados.getColumns().add(tbcNombre);
            tbvResultados.getColumns().add(tbcPApellido);
            tbvResultados.getColumns().add(tbcSApellido);
            tbvResultados.refresh();

            btnFiltrar.setOnAction((ActionEvent event) -> {
                tbvResultados.getItems().clear();
                EmpleadoService service = new EmpleadoService();
                String cedula = "%" + txtCedula.getText() + "%";

                String nombre = "%" + txtNombre.getText() + "%";
                
                String pApellido = "%" + txtPApellido.getText() + "%";
                
                String sApellido = "%" + txtSApellido.getText() + "%";

                Respuesta respuesta = service.getEmpleados(cedula.toUpperCase(), nombre.toUpperCase(), pApellido.toUpperCase(), sApellido.toUpperCase());

                if (respuesta.getEstado()) {
                    ObservableList<EmpleadoDto> empleados = FXCollections.observableList((List<EmpleadoDto>) respuesta.getResultado("Empleados"));
                    tbvResultados.setItems(empleados);
                    tbvResultados.refresh();
                } else {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Consultar empleados", getStage(), respuesta.getMensaje());
                }
            });
        } catch (Exception ex) {
            Logger.getLogger(BusquedaController.class.getName()).log(Level.SEVERE, "Error consultando los empleados.", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Consultar empleados", getStage(), "Ocurrio un error consultado los empleados.");
        }
    }
    
    public void busquedaTiposPlanilla() {
        try {
            lblTitulo.setText("Búsqueda de Tipos de Planilla");
            
            MFXTextField txtCodigo = new MFXTextField();
            txtCodigo.setFloatMode(FloatMode.INLINE);
            txtCodigo.setMaxWidth(1.7976931348623157E308);
            txtCodigo.setFloatingText("Código");
            txtCodigo.delegateSetTextFormatter(Formato.getInstance().letrasFormat(15));
            txtCodigo.setOnKeyPressed(keyEnter);
            
            MFXTextField txtDescripcion = new MFXTextField();
            txtDescripcion.setFloatMode(FloatMode.INLINE);
            txtDescripcion.setMaxWidth(1.7976931348623157E308);
            txtDescripcion.setFloatingText("Descripción");
            txtDescripcion.delegateSetTextFormatter(Formato.getInstance().letrasFormat(30));
            txtDescripcion.setOnKeyPressed(keyEnter);
            
            MFXTextField txtPlaMes = new MFXTextField();
            txtPlaMes.setFloatMode(FloatMode.INLINE);
            txtPlaMes.setMaxWidth(1.7976931348623157E308);
            txtPlaMes.setFloatingText("Planillas x Mes");
            txtPlaMes.delegateSetTextFormatter(Formato.getInstance().integerFormat());
            txtPlaMes.setOnKeyPressed(keyEnter);
            
            MFXTextField txtIdEmp = new MFXTextField();
            txtIdEmp.setFloatMode(FloatMode.INLINE);
            txtIdEmp.setMaxWidth(1.7976931348623157E308);
            txtIdEmp.setFloatingText("Id Empleado");
            txtIdEmp.setOnKeyPressed(keyEnter);
            txtIdEmp.delegateSetTextFormatter(Formato.getInstance().integerFormat());
            
            MFXTextField txtCedula = new MFXTextField();
            txtCedula.setFloatMode(FloatMode.INLINE);
            txtCedula.setMaxWidth(1.7976931348623157E308);
            txtCedula.setFloatingText("Cédula");
            txtCedula.setOnKeyPressed(keyEnter);
            txtCedula.delegateSetTextFormatter(Formato.getInstance().cedulaFormat(40));
            
            vbxParametros.getChildren().clear();
            vbxParametros.getChildren().add(txtCodigo);
            vbxParametros.getChildren().add(txtDescripcion);
            vbxParametros.getChildren().add(txtPlaMes);
            vbxParametros.getChildren().add(txtIdEmp);
            vbxParametros.getChildren().add(txtCedula);

            tbvResultados.getColumns().clear();
            tbvResultados.getItems().clear();
            
            TableColumn<TipoPlanillaDto, String> tbcId = new TableColumn<>("Id");
            tbcId.setPrefWidth(0);
            tbcId.setCellValueFactory(cd -> cd.getValue().getIdProperty());
            
            TableColumn<TipoPlanillaDto, String> tbcCodigo = new TableColumn<>("Código");
            tbcCodigo.setPrefWidth(100);
            tbcCodigo.setCellValueFactory(cd -> cd.getValue().getCodigoProperty());
            
            TableColumn<TipoPlanillaDto, String> tbcDes = new TableColumn<>("Descripción");
            tbcDes.setPrefWidth(150);
            tbcDes.setCellValueFactory(cd -> cd.getValue().getDescripcionProperty());
            
            TableColumn<TipoPlanillaDto, String> tbcPlaMes = new TableColumn<>("Planillas x Mes");
            tbcPlaMes.setPrefWidth(150);
            tbcPlaMes.setCellValueFactory(cd -> cd.getValue().getPlanillaPorMesProperty());
            
            tbvResultados.getColumns().add(tbcId);
            tbvResultados.getColumns().add(tbcCodigo);
            tbvResultados.getColumns().add(tbcDes);
            tbvResultados.getColumns().add(tbcPlaMes);
            tbvResultados.refresh();

            btnFiltrar.setOnAction((ActionEvent event) -> {
                tbvResultados.getItems().clear();
                TipoPlanillaService service = new TipoPlanillaService();
                String cedula = "%" + txtCedula.getText() + "%";
                
                String idEmp = "%" + txtIdEmp.getText() + "%";

                String des = "%" + txtDescripcion.getText() + "%";
                
                String codigo = "%" + txtCodigo.getText() + "%";
                
                String plaMes = "%" + txtPlaMes.getText() + "%";

                Respuesta respuesta = service.getTiposPlanilla(codigo.toUpperCase(), des.toUpperCase(), plaMes.toUpperCase(), idEmp.toUpperCase(), cedula.toUpperCase());

                if (respuesta.getEstado()) {
                    ObservableList<TipoPlanillaDto> tipoPlanillas = FXCollections.observableList((List<TipoPlanillaDto>) respuesta.getResultado("TipoPlanillas"));
                    tbvResultados.setItems(tipoPlanillas);
                    tbvResultados.refresh();
                } else {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Consultar tipos planillas", getStage(), respuesta.getMensaje());
                }
            });
        } catch (Exception ex) {
            Logger.getLogger(BusquedaController.class.getName()).log(Level.SEVERE, "Error consultando los tipos planillas.", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Consultar empleados", getStage(), "Ocurrio un error tipos planillas.");
        }
    }

    @Override
    public void initialize() {
        Platform.runLater(() -> {
            for (Node object : vbxParametros.getChildren()) {
                if(object.isFocusTraversable()) {
                    object.requestFocus();
                    break;
                }
            }
        });
        
        resultado = null;
    }
}
