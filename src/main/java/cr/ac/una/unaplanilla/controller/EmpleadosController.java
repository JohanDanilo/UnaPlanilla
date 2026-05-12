package cr.ac.una.unaplanilla.controller;

import cr.ac.una.unaplanilla.model.EmpleadoDto;
import cr.ac.una.unaplanilla.service.EmpleadoService;
import cr.ac.una.unaplanilla.util.BindingUtils;
import cr.ac.una.unaplanilla.util.Formato;
import cr.ac.una.unaplanilla.util.Mensaje;
import cr.ac.una.unaplanilla.util.Respuesta;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXRadioButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

public class EmpleadosController extends Controller implements Initializable {

    private EmpleadoDto empleado;
    private ObjectProperty<EmpleadoDto> empleadoProperty = new SimpleObjectProperty<>();
    private List<Node> requeridos = new ArrayList();
    @FXML
    private AnchorPane root;
    @FXML
    private MFXTextField txtId;
    @FXML
    private MFXTextField txtNombre;
    @FXML
    private MFXTextField txtApellido;
    @FXML
    private MFXTextField txtSegundoApellido;
    @FXML
    private MFXTextField txtCedula;
    @FXML
    private MFXRadioButton rdbMasculino;
    @FXML
    private MFXRadioButton rdbFemenino;
    @FXML
    private MFXCheckbox cbxAdministrador;
    @FXML
    private MFXCheckbox cbxActivo;
    @FXML
    private MFXDatePicker dprFechaIngreso;
    @FXML
    private MFXDatePicker dprFechaSalida;
    @FXML
    private MFXTextField txtCorreo;
    @FXML
    private MFXTextField txtUsuario;
    @FXML
    private MFXPasswordField txtClave;
    @FXML
    private ToggleGroup tggGenero;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        rdbFemenino.setUserData("F");
        rdbMasculino.setUserData("M");
        txtId.delegateSetTextFormatter(Formato.getInstance().integerFormat());
        txtNombre.delegateSetTextFormatter(Formato.getInstance().letrasFormat(30));
        txtApellido.delegateSetTextFormatter(Formato.getInstance().letrasFormat(15));
        txtSegundoApellido.delegateSetTextFormatter(Formato.getInstance().letrasFormat(15));
        txtCedula.delegateSetTextFormatter(Formato.getInstance().cedulaFormat(40));
        txtCorreo.delegateSetTextFormatter(Formato.getInstance().maxLengthFormat(80));
        txtClave.delegateSetTextFormatter(Formato.getInstance().letrasFormat(8));
        txtUsuario.delegateSetTextFormatter(Formato.getInstance().letrasFormat(30));
        this.empleado = new EmpleadoDto();
        bindEmpleado();
        indicarRequeridos();
        cargarValoresDefecto();

    }

    @Override
    public void initialize() {
    }

    private void bindEmpleado() {
        try {
            empleadoProperty.addListener((ov, oldVal, newVal) -> {

                if (oldVal != null) {

                    txtId.textProperty().unbind();

                    txtNombre.textProperty().unbindBidirectional(oldVal.getNombreProperty());
                    txtApellido.textProperty().unbindBidirectional(oldVal.getPrimerApellidoProperty());
                    txtSegundoApellido.textProperty().unbindBidirectional(oldVal.getSegundoApellidoProperty());

                    txtCedula.textProperty().unbindBidirectional(oldVal.getCedulaProperty());

                    txtCorreo.textProperty().unbindBidirectional(oldVal.getCorreoProperty());

                    txtUsuario.textProperty().unbindBidirectional(oldVal.getUsuarioProperty());

                    txtClave.textProperty().unbindBidirectional(oldVal.getClaveProperty());

                    dprFechaIngreso.valueProperty().unbindBidirectional(oldVal.getFechaIngresoProperty());

                    dprFechaSalida.valueProperty().unbindBidirectional(oldVal.getFechaSalidaProperty());

                    cbxAdministrador.selectedProperty().unbindBidirectional(oldVal.getAdministradorProperty());

                    cbxActivo.selectedProperty().unbindBidirectional(oldVal.getActivoProperty());

                    BindingUtils.unbindToggleGroupToProperty(
                            tggGenero,
                            oldVal.getGeneroProperty()
                    );
                }

                if (newVal != null) {

                    if (newVal.getIdProperty().get() != null && !newVal.getIdProperty().get().isBlank()) {
                        txtId.textProperty().bind(newVal.getIdProperty());
                    }

                    txtNombre.textProperty().bindBidirectional(newVal.getNombreProperty());

                    txtApellido.textProperty().bindBidirectional(newVal.getPrimerApellidoProperty());

                    txtSegundoApellido.textProperty().bindBidirectional(newVal.getSegundoApellidoProperty());

                    txtCedula.textProperty().bindBidirectional(newVal.getCedulaProperty());

                    txtCorreo.textProperty().bindBidirectional(newVal.getCorreoProperty());

                    txtUsuario.textProperty().bindBidirectional(newVal.getUsuarioProperty());

                    txtClave.textProperty().bindBidirectional(newVal.getClaveProperty());

                    dprFechaIngreso.valueProperty().bindBidirectional(
                            newVal.getFechaIngresoProperty()
                    );

                    dprFechaSalida.valueProperty().bindBidirectional(
                            newVal.getFechaSalidaProperty()
                    );

                    cbxAdministrador.selectedProperty().bindBidirectional(
                            newVal.getAdministradorProperty()
                    );

                    cbxActivo.selectedProperty().bindBidirectional(
                            newVal.getActivoProperty()
                    );

                    BindingUtils.bindToggleGroupToProperty(
                            tggGenero,
                            newVal.getGeneroProperty()
                    );
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
        this.empleado = new EmpleadoDto();
        this.empleado.setActivo(Boolean.TRUE);
        this.empleado.setAdministrador(Boolean.FALSE);
        this.empleado.setFechaIngreso(LocalDate.now());
        this.empleado.setGenero("M");
        this.empleadoProperty.set(this.empleado);
        validarAdministrador();
        txtId.clear();
        txtId.requestFocus();
    }

    private void validarAdministrador() {
        if (cbxAdministrador.isSelected()) {
            requeridos.addAll(Arrays.asList(txtUsuario, txtClave));
            txtUsuario.setDisable(false);
            txtClave.setDisable(false);
        } else {
            requeridos.removeAll(Arrays.asList(txtUsuario, txtClave));
            txtUsuario.clear();
            txtUsuario.setDisable(true);
            txtClave.clear();
            txtClave.setDisable(true);
        }
    }

    private void indicarRequeridos() {
        requeridos.clear();
        requeridos.addAll(Arrays.asList(txtCedula, txtNombre, txtApellido, dprFechaIngreso));
    }

    public String validarRequeridos() {
        Boolean validos = true;
        String invalidos = "";
        for (Node node : requeridos) {
            if (node instanceof MFXTextField && (((MFXTextField) node).getText() == null || ((MFXTextField) node).getText().isBlank())) {
                if (validos) {
                    invalidos += ((MFXTextField) node).getFloatingText();
                } else {
                    invalidos += "," + ((MFXTextField) node).getFloatingText();
                }
                validos = false;
            } else if (node instanceof MFXPasswordField && (((MFXPasswordField) node).getText() == null || ((MFXPasswordField) node).getText().isBlank())) {
                if (validos) {
                    invalidos += ((MFXPasswordField) node).getFloatingText();
                } else {
                    invalidos += "," + ((MFXPasswordField) node).getFloatingText();
                }
                validos = false;
            } else if (node instanceof MFXDatePicker && ((MFXDatePicker) node).getValue() == null) {
                if (validos) {
                    invalidos += ((MFXDatePicker) node).getFloatingText();
                } else {
                    invalidos += "," + ((MFXDatePicker) node).getFloatingText();
                }
                validos = false;
            } else if (node instanceof MFXComboBox && ((MFXComboBox) node).getSelectionModel().getSelectedIndex() < 0) {
                if (validos) {
                    invalidos += ((MFXComboBox) node).getFloatingText();
                } else {
                    invalidos += "," + ((MFXComboBox) node).getFloatingText();
                }
                validos = false;
            }
        }
        if (validos) {
            return "";
        } else {
            return "Campos requeridos o con problemas de formato [" + invalidos + "].";
        }
    }

    @FXML
    private void onCbxAdministrador(ActionEvent event) {
        validarAdministrador();
    }

    @FXML
    private void onBtnNuevo(ActionEvent event) {
        if (new Mensaje().showConfirmation("Limpiar Empleado", getStage(), "¿Está seguro que desea limpiar el registro?")) {
            cargarValoresDefecto();
        }
    }

    @FXML
    private void onBtnBuscar(ActionEvent event) {
        if (!txtId.getText().isBlank()) {
            cargarEmpleado(Long.valueOf(txtId.getText()));
        }
    }

    @FXML
    private void onBtnEliminar(ActionEvent event) {

        try {
            String invalidos = validarRequeridos();
            if (!invalidos.isBlank()) {
                new Mensaje().showModal(Alert.AlertType.WARNING, "Eliminar empleado", getStage(), "Favor consultar el empleado a eliminar");
            } else {
                EmpleadoService empleadoService = new EmpleadoService();
                Respuesta respuesta = empleadoService.eliminarEmpleado(this.empleado.getId());
                if (respuesta.getEstado()) {
                    cargarValoresDefecto();
                    new Mensaje().showModal(Alert.AlertType.INFORMATION, "Eliminar empleado", getStage(), "El empleado se eliminó correctamente");
                } else {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Eliminar empleado", getStage(), respuesta.getMensaje());
                }
                
            }

        } catch (Exception ex) {
            Logger.getLogger(EmpleadosController.class.getName()).log(Level.SEVERE,
                    "Error eliminando el empleado.", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Eliminar empleado", getStage(),
                    "Ocurrió un error eliminando el empleado.");
        }
    }

    @FXML
    private void onBtnGuardar(ActionEvent event) {
        try {
            String invalidos = validarRequeridos();
            
            if (!invalidos.isBlank()) {
                new Mensaje().showModal(Alert.AlertType.WARNING, "Guardar Empleado",
                        getStage(), invalidos);
            } else {
                EmpleadoService empleadoService = new EmpleadoService();
                Respuesta respuesta = empleadoService.guardarEmpleado(this.empleado);
                if (respuesta.getEstado()) {
                    this.empleado = (EmpleadoDto) respuesta.getResultado("Empleado");
                    this.empleadoProperty.set(this.empleado);
                    validarAdministrador();
                    validarRequeridos();
                } else {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar empleado", getStage(), respuesta.getMensaje());
                }
                
                
                new Mensaje().showModal(Alert.AlertType.INFORMATION, "Guardar Empleado",
                        getStage(), "El empleado se guardó correctamente.");
            }
        } catch (Exception ex) {
            Logger.getLogger(EmpleadosController.class.getName()).
                    log(Level.SEVERE, "Error guardando el empleado.", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar Empleado", getStage(),
                    "Ocurrió un error guardando el empleado.");
        }
    }

    @FXML
    private void onKeyPressedTxtId(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER
                && !txtId.getText().isBlank()) {
            cargarEmpleado(Long.valueOf(txtId.getText()));
        }
    }

    private void cargarEmpleado(Long id) {
        try {
            EmpleadoService empleadoService = new EmpleadoService();
            Respuesta respuesta = empleadoService.getEmpleado(id);
            if (respuesta.getEstado()) {
                this.empleado = (EmpleadoDto) respuesta.getResultado("Empleado");
                this.empleadoProperty.set(this.empleado);
                validarAdministrador();
                validarRequeridos();
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

}
