package cr.ac.una.unaplanilla.controller;

import cr.ac.una.unaplanilla.model.EmpleadoDto;
import cr.ac.una.unaplanilla.model.TipoPlanillaDTO;
import cr.ac.una.unaplanilla.service.EmpleadoService;
import cr.ac.una.unaplanilla.service.TipoPlanillaService;
import cr.ac.una.unaplanilla.util.BindingUtils;
import cr.ac.una.unaplanilla.util.Formato;
import cr.ac.una.unaplanilla.util.Mensaje;
import cr.ac.una.unaplanilla.util.Respuesta;
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
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableCell;
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

    private TipoPlanillaDTO tipoPlanilla;
    private ObjectProperty<TipoPlanillaDTO> tipoPlanillaProperty = new SimpleObjectProperty<>();
    private List<Node> requeridos = new ArrayList<>();

    @FXML
    private TableColumn<EmpleadoDto, String> colId;
    @FXML
    private TableColumn<EmpleadoDto, String> colNombre;
    @FXML
    private TableColumn<EmpleadoDto, Boolean> colEliminar;

    private TipoPlanillaDTO tiposPlanillaDto;

    private ObservableList<EmpleadoDto> empleados;
    @FXML
    private TitledPane panelTiposPlanillas;
    @FXML
    private TitledPane panelEmpleados;
    @FXML
    private Accordion acordeonModulos;

    private EmpleadoDto empleado;
    private ObjectProperty<EmpleadoDto> empleadoProperty = new SimpleObjectProperty<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        acordeonModulos.setExpandedPane(panelTiposPlanillas);
        txtId.delegateSetTextFormatter(Formato.getInstance().integerFormat());
        txtCodigo.delegateSetTextFormatter(Formato.getInstance().maxLengthFormat(10));
        txtDescripcion.delegateSetTextFormatter(Formato.getInstance().maxLengthFormat(100));
        txtCantidadPlanillasMes.delegateSetTextFormatter(Formato.getInstance().integerFormat());
        txtIdEmpleado.delegateSetTextFormatter(Formato.getInstance().integerFormat());
        txtNombreEmpleado.delegateSetTextFormatter(Formato.getInstance().letrasFormat(30));

        empleado = new EmpleadoDto();
        this.tipoPlanilla = new TipoPlanillaDTO();
        configurarTablaEmpleados();
        bindTipoPlanilla();
        bindEmpleado();
        cargarValoresDefecto();
        indicarRequeridos();

        colId.setCellValueFactory((cd) -> cd.getValue().getIdProperty());
        colNombre.setCellValueFactory((cd) -> cd.getValue().getNombreProperty());
        colEliminar.setCellValueFactory((cd) -> new SimpleBooleanProperty(cd.getValue() != null));
        colEliminar.setCellFactory((p) -> new ButtonCell());

        listaEmpleados.getSelectionModel().selectedItemProperty().addListener((ov, oldValue, newValue) -> {
            if (newValue != null) {
                this.empleado = newValue;
                this.empleadoProperty.setValue(this.empleado);

            }
        });

        panelEmpleados.expandedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                if (this.tipoPlanilla.getId() == null) {
                    
                    new Mensaje().showModal(Alert.AlertType.WARNING,
                        "TipoPlanilla", getStage(), "Debe cargar una planilla antes de ver empleados");
                    panelEmpleados.setExpanded(false);
                    panelTiposPlanillas.setExpanded(true);
                }
            }
        });

    }

    @Override
    public void initialize() {

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
                }
            });
        } catch (Exception ex) {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Error al realizar el bindeo", getStage(), "Ocurrió un error al realizar el bindeo");
        }
    }

    private void bindEmpleado() {
        try {
            empleadoProperty.addListener((ov, oldVal, newVal) -> {

                if (oldVal != null) {

                    txtIdEmpleado.textProperty().unbind();

                    txtNombreEmpleado.textProperty().unbindBidirectional(oldVal.getNombreProperty());

                }

                if (newVal != null) {

                    if (newVal.getIdProperty().get() != null && !newVal.getIdProperty().get().isBlank()) {
                        txtIdEmpleado.textProperty().bind(newVal.getIdProperty());
                    }

                    txtNombreEmpleado.textProperty().bindBidirectional(newVal.getNombreProperty());

                }
            });

        } catch (Exception ex) {
            Logger.getLogger(EmpleadosController.class.getName())
                    .log(Level.SEVERE, "Error realizando bindings.", ex);

            new Mensaje().showModal(
                    Alert.AlertType.ERROR,
                    "Error al realizar el bindeo",
                    getStage(),
                    "Ocurrió un error al realizar el bindeo."
            );
        }
    }

    private void cargarValoresDefecto() {
        this.tipoPlanilla = new TipoPlanillaDTO();
        this.tipoPlanilla.setActivo(Boolean.TRUE);
        this.tipoPlanillaProperty.setValue(this.tipoPlanilla);
        txtId.clear();
        txtId.requestFocus();
        limpiarEmpleado();
        cargarEmpleados();
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
                if (!validos) {
                    invalidos.append(", ");
                }
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

    private void cargarEmpleado(Long id) {
        try {
            EmpleadoService empleadoService = new EmpleadoService();
            Respuesta respuesta = empleadoService.getEmpleado(id);
            if (respuesta.getEstado()) {
                this.empleado = (EmpleadoDto) respuesta.getResultado("Empleado");
                this.empleadoProperty.set(this.empleado);
            } else {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Buscar empleado", getStage(), respuesta.getMensaje());
            }
        } catch (Exception ex) {
            Logger.getLogger(EmpleadosController.class.getName())
                    .log(Level.SEVERE, "Error cargando el empleado", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Cargar Empleado", getStage(),
                    "Ocurrió un error cargando el empleado");
        }
    }

    @FXML
    private void selectionChangeAcEmp(ActionEvent event) {
        if (panelEmpleados.isExpanded()) {
            limpiarEmpleado();
        } else {
            if (new Mensaje().showConfirmation("Nuevo Tipo Planilla", getStage(), "¿Está seguro que desea limpiar el registro?")) {
                cargarValoresDefecto();
            }
        }

    }

    @FXML
    private void onBtnNuevo(ActionEvent event) {
        if (panelEmpleados.isExpanded()) {
            limpiarEmpleado();
        } else {
            if (new Mensaje().showConfirmation("Nuevo Tipo Planilla", getStage(), "¿Está seguro que desea limpiar el registro?")) {
                cargarValoresDefecto();
            }
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
                new Mensaje().showModal(Alert.AlertType.WARNING, "Guardar TipoPlanilla",
                        getStage(), invalidos);
            } else {
                TipoPlanillaService empleadoService = new TipoPlanillaService();
                Respuesta respuesta = empleadoService.guardarTipoPlanilla(this.tipoPlanilla);
                if (respuesta.getEstado()) {
                    this.tipoPlanilla = (TipoPlanillaDTO) respuesta.getResultado("TipoPlanilla");
                    this.tipoPlanillaProperty.set(this.tipoPlanilla);
                    validarRequeridos();
                } else {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar TipoPlanilla", getStage(), respuesta.getMensaje());
                }

                new Mensaje().showModal(Alert.AlertType.INFORMATION, "Guardar TipoPlanilla",
                        getStage(), "El Tipo de Planilla se guardó correctamente.");
            }
        } catch (Exception ex) {
            Logger.getLogger(TiposPlanillaController.class.getName()).
                    log(Level.SEVERE, "Error guardando el TipoPlanilla.", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar TipoPlanilla", getStage(),
                    "Ocurrió un error guardando el TipoPlanilla.");
        }
    }

    @FXML
    private void onBtnEliminar(ActionEvent event) {

        try {
            String invalidos = validarRequeridos();
            if (!invalidos.isBlank()) {
                new Mensaje().showModal(Alert.AlertType.WARNING, "Eliminar tipoPlanilla", getStage(), "Favor consultar el tipoPlanilla a eliminar");
            } else {
                TipoPlanillaService tipoPlanillaService = new TipoPlanillaService();
                Respuesta respuesta = tipoPlanillaService.eliminarTipoPlanilla(this.tipoPlanilla.getId());
                if (respuesta.getEstado()) {
                    cargarValoresDefecto();
                    new Mensaje().showModal(Alert.AlertType.INFORMATION, "Eliminar tipoPlanilla", getStage(), "El tipoPlanilla se eliminó correctamente");
                } else {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Eliminar tipoPlanilla", getStage(), respuesta.getMensaje());
                }

            }

        } catch (Exception ex) {
            Logger.getLogger(TiposPlanillaController.class.getName()).log(Level.SEVERE,
                    "Error eliminando el tipoPlanilla.", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Eliminar tipoPlanilla", getStage(),
                    "Ocurrió un error eliminando el tipoPlanilla.");
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

    private void cargarTipoPlanilla(Long id) {
        try {
            TipoPlanillaService tipoPlanillaService = new TipoPlanillaService();
            Respuesta respuesta = tipoPlanillaService.getTipoPlanilla(id);
            if (respuesta.getEstado()) {
                this.tipoPlanilla = (TipoPlanillaDTO) respuesta.getResultado("TipoPlanilla");
                this.tipoPlanillaProperty.set(this.tipoPlanilla);
                validarRequeridos();
                cargarEmpleados();
            } else {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Cargar tipoPlanilla", getStage(), respuesta.getMensaje());
            }
        } catch (Exception ex) {
            Logger.getLogger(EmpleadosController.class.getName())
                    .log(Level.SEVERE, "Error cargando el empleado", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Cargar tipoPlanilla", getStage(),
                    "Ocurrió un error cargando el empleado");
        }
    }

    @FXML
    private void onBtnAgregarEmpleado(ActionEvent event) {
        if (this.empleado.getId() == null || this.empleado.getNombre().isBlank()) {
            new Mensaje().showModal(Alert.AlertType.WARNING, "Agregar Empleado", getStage(),
                    "Es necesario cargar un empleado para agregaro a la lista.");
        } else if (listaEmpleados.getItems() == null
                || listaEmpleados.getItems().stream().noneMatch((e) -> e.equals(this.empleado))) {
            this.empleado.setModificado(true);
            listaEmpleados.getItems().add(this.empleado);
            listaEmpleados.refresh();

        }
    }

    private void limpiarEmpleado() {
        listaEmpleados.getSelectionModel().select(null);
        this.empleado = new EmpleadoDto();
        this.empleadoProperty.setValue(this.empleado);
        txtIdEmpleado.clear();
        txtIdEmpleado.requestFocus();
    }

    private void cargarEmpleados() {
        listaEmpleados.getItems().clear();
        listaEmpleados.setItems(this.tipoPlanilla.getEmpleados());
        listaEmpleados.refresh();
    }

    private class ButtonCell extends TableCell<EmpleadoDto, Boolean> {

        final Button cellButton = new Button();

        public ButtonCell() {
            cellButton.setPrefWidth(500);
            cellButton.getStyleClass().add("jfx-btnimg-tbveliminar");

            cellButton.setOnAction((t) -> {
                EmpleadoDto emp = ButtonCell.this.getTableView().getItems().get(ButtonCell.this.getIndex());
                tipoPlanilla.getEmpleadosEliminados().add(emp);
                listaEmpleados.getItems().remove(emp);
                listaEmpleados.refresh();
            });
        }
    }

}
