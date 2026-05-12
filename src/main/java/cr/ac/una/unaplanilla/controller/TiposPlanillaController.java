package cr.ac.una.unaplanilla.controller;

import cr.ac.una.unaplanilla.model.EmpleadoDto;
import cr.ac.una.unaplanilla.model.TipoPlanillaDTO;
import cr.ac.una.unaplanilla.util.Formato;
import cr.ac.una.unaplanilla.util.Mensaje;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author USUARIO UNA PZ
 */
public class TiposPlanillaController extends Controller implements Initializable {

    @FXML
    private MFXTextField txtId;
    @FXML
    private MFXCheckbox chkActivo;
    @FXML
    private MFXTextField txtCodigo;
    @FXML
    private MFXTextField txtDescripcion;
    @FXML
    private MFXTextField txtCantidadPlanillasMes;
    @FXML
    private MFXTextField txtIdEmpleado;
    @FXML
    private MFXTextField txtNombreEmpleado;
    @FXML
    private TableView<EmpleadoDto> listaEmpleados;
    
    ////////////////////////////////////////////
    ///
    ///

    private TipoPlanillaDTO tipoPlanilla;
    private ObjectProperty<TipoPlanillaDTO> tipoPlanillaProperty = new SimpleObjectProperty<>();
    private List<Node> requeridos = new ArrayList<>();
    
    
    @FXML
    private TableColumn<EmpleadoDto, String> colId;
    @FXML
    private TableColumn<EmpleadoDto, String> colNombre;
    @FXML
    private TableColumn<EmpleadoDto, Void> colEliminar;
    
    private TipoPlanillaDTO tiposPlanillaDto;
    
    private ObservableList<EmpleadoDto> empleados;
    @FXML
    private TitledPane panelTiposPlanillas;
    @FXML
    private TitledPane panelEmpleados;
    @FXML
    private Accordion acordeonModulos;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @Override
    public void initialize() {
        acordeonModulos.setExpandedPane(panelEmpleados);
        
        txtId.delegateSetTextFormatter(Formato.getInstance().integerFormat());
        txtCodigo.delegateSetTextFormatter(Formato.getInstance().maxLengthFormat(10));
        txtDescripcion.delegateSetTextFormatter(Formato.getInstance().maxLengthFormat(100));
        txtCantidadPlanillasMes.delegateSetTextFormatter(Formato.getInstance().integerFormat());
        txtIdEmpleado.delegateSetTextFormatter(Formato.getInstance().integerFormat());
        txtNombreEmpleado.delegateSetTextFormatter(Formato.getInstance().letrasFormat(30));

        this.tipoPlanilla = new TipoPlanillaDTO();
        configurarTablaEmpleados();
        bindTipoPlanilla();
        cargarValoresDefecto();
        indicarRequeridos();
    }
    
    private void configurarTablaEmpleados() {
        colId = (TableColumn<EmpleadoDto, String>) listaEmpleados.getColumns().get(0);
        colNombre = (TableColumn<EmpleadoDto, String>) listaEmpleados.getColumns().get(1);
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));   
    }
    
    private void bindTipoPlanilla() {
        try {
            tipoPlanillaProperty.addListener((ov, oldVal, newVal) -> {
                if (oldVal != null) {
                    txtId.textProperty().unbind();
                    txtCodigo.textProperty().unbindBidirectional(oldVal.getCodigoProperty());
                    txtDescripcion.textProperty().unbindBidirectional(oldVal.getDescripcionProperty());
                    txtCantidadPlanillasMes.textProperty().unbindBidirectional(oldVal.getCantidadPlanillasMesProperty());
                    chkActivo.selectedProperty().unbindBidirectional(oldVal.getActivoProperty());
                    listaEmpleados.setItems(null);
                }
                if (newVal != null) {
                    if (newVal.getIdProperty().get() != null
                            && !newVal.getIdProperty().get().isBlank()) {
                        txtId.textProperty().bindBidirectional(newVal.getIdProperty());
                    }
                    txtCodigo.textProperty().bindBidirectional(newVal.getCodigoProperty());
                    txtDescripcion.textProperty().bindBidirectional(newVal.getDescripcionProperty());
                    txtCantidadPlanillasMes.textProperty().bindBidirectional(newVal.getCantidadPlanillasMesProperty());
                    chkActivo.selectedProperty().bindBidirectional(newVal.getActivoProperty());
                    listaEmpleados.setItems(newVal.getEmpleados());
                }
            });
        } catch (Exception ex) {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Error al realizar el bindeo", getStage(), "Ocurrió un error al realizar el bindeo");
        }
    }
    
    private void cargarValoresDefecto() {
        this.tipoPlanilla = new TipoPlanillaDTO();
        this.tipoPlanilla.setActivo(Boolean.TRUE);
        this.tipoPlanillaProperty.setValue(this.tipoPlanilla);
        txtId.clear();
        txtId.requestFocus();
    }

    private void indicarRequeridos() {
        requeridos.clear();
        requeridos.addAll(Arrays.asList(txtCodigo, txtDescripcion, txtCantidadPlanillasMes));
    }

    public String validarRequeridos() {
        boolean validos = true;
        StringBuilder invalidos = new StringBuilder();
        for (Node node : requeridos) {
            if (node instanceof MFXTextField campo && (campo.getText() == null || campo.getText().isBlank())) {
                if (!validos) invalidos.append(", ");
                invalidos.append(campo.getFloatingText());
                validos = false;
            }
        }
        if (validos) {
            return "";
        } else {
            return "Campos requeridos o con problemas de formato [" + invalidos + "].";
        }
    }

    private void cargarEmpleado(Long idEmp) {
        try {

        } catch (Exception ex) {
            Logger.getLogger(TiposPlanillaController.class.getName()).log(Level.SEVERE, "Error cargando empleado", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Cargar Empleado", getStage(), "Ocurrió un error cargando el empleado");
        }
    }

    private void cargarTipoPlanilla(Long idTipo) {
        try {

        } catch (Exception ex) {
            Logger.getLogger(TiposPlanillaController.class.getName()).log(Level.SEVERE, "Error cargando tipo planilla", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Cargar Tipo Planilla", getStage(), "Ocurrió un error cargando el tipo de planilla");
        }
    }

    @FXML
    private void onBtnNuevo(ActionEvent event) {
        if (new Mensaje().showConfirmation("Nuevo Tipo Planilla", getStage(), "¿Está seguro que desea limpiar el registro?")) {
            cargarValoresDefecto();
        }
    }

    @FXML
    private void onBtnBuscar(ActionEvent event) {
        if (!txtId.getText().isBlank()) {
            cargarTipoPlanilla(Long.valueOf(txtId.getText()));
        } else {
            new Mensaje().showModal(Alert.AlertType.WARNING, "Buscar Tipo Planilla", getStage(), "Ingrese un ID para buscar");
        }
    }

    @FXML
    private void onBtnGuardar(ActionEvent event) {
        try {
            String invalidos = validarRequeridos();
            if (!invalidos.isBlank()) {
                new Mensaje().showModal(Alert.AlertType.WARNING, "Guardar Tipo Planilla",getStage(), invalidos);
                return;
            }
        System.out.println("Verificación de Binding TiposPlanilla:");
        System.out.println("Codigo:" + tipoPlanilla.getCodigo());
        System.out.println("Descripcion: " + tipoPlanilla.getDescripcion());
        System.out.println("Cantidad:" + tipoPlanilla.getCantidadPlanillasMes());
        System.out.println("Activo:" + tipoPlanilla.getActivo());
        System.out.println("ID:" + tipoPlanilla.getId());
        System.out.println("Fin de Verificación");
        
        
            new Mensaje().showModal(Alert.AlertType.INFORMATION, "Guardar Tipo Planilla", getStage(), "El tipo de planilla se guardó correctamente");
        } catch (Exception ex) {
            Logger.getLogger(TiposPlanillaController.class.getName()).log(Level.SEVERE, "Error guardando tipo planilla", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar Tipo Planilla",getStage(), "Ocurrió un error guardando el tipo de planilla");
        }
    }

    @FXML
    private void onBtnEliminar(ActionEvent event) {
        try {
            if (this.tipoPlanilla.getId() == null) {
                new Mensaje().showModal(Alert.AlertType.WARNING, "Eliminar Tipo Planilla", getStage(), "Favor consultar el tipo de planilla a eliminar");
                return;
            }
            if (new Mensaje().showConfirmation("Eliminar Tipo Planilla", getStage(),"¿Está seguro que desea eliminar este tipo de planilla?")) {
                cargarValoresDefecto();
            }
        } catch (Exception ex) {
            Logger.getLogger(TiposPlanillaController.class.getName()).log(Level.SEVERE, "Error eliminando tipo planilla", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Eliminar Tipo Planilla",getStage(), "Ocurrió un error eliminando el tipo de planilla");
        }
    }

    @FXML
    private void onKeyPressedIdEmpleado(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER && !txtIdEmpleado.getText().isBlank()) {
            cargarEmpleado(Long.valueOf(txtIdEmpleado.getText()));
        }
    }
    
    @FXML
    private void onKeyPressedId(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER && !txtId.getText().isBlank()) {
            cargarTipoPlanilla(Long.valueOf(txtId.getText()));
        }
    }

    

}