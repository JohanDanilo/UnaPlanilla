package cr.ac.una.unaplanilla.controller;

import cr.ac.una.unaplanilla.model.EmpleadoDto;
import cr.ac.una.unaplanilla.util.BindingUtils;
import cr.ac.una.unaplanilla.util.Formato;
import cr.ac.una.unaplanilla.util.Mensaje;
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
        // TODO
        //txtNombre.textProperty().bindBidirectional(this.empleado.getNombreProperty());
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
        cargarValoresDefecto();
        indicarRequeridos();
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
                    dprFechaSalida.valueProperty().unbindBidirectional(oldVal.getFechaSalidaProperty());
                    cbxActivo.selectedProperty().unbindBidirectional(oldVal.getActivoProperty());
                    BindingUtils.unbindToggleGroupToProperty(tggGenero, oldVal.getGeneroProperty());
                }
                if (newVal != null) {
                    if (newVal.getIdProperty().get() != null
                            && !newVal.getIdProperty().get().isBlank()) {
                        txtId.textProperty().bindBidirectional(newVal.getIdProperty());
                    }

                    txtNombre.textProperty().bindBidirectional(newVal.getNombreProperty());
                    dprFechaSalida.valueProperty().bindBidirectional(newVal.getFechaSalidaProperty());
                    cbxActivo.selectedProperty().bindBidirectional(newVal.getActivoProperty());
                    BindingUtils.bindToggleGroupToProperty(tggGenero, newVal.getGeneroProperty());
                }
            });
        } catch (Exception ex) {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Eror al realizar el bindeo", getStage(), "Ocurrio un error al realizar el bindeo");
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
    }

    @FXML
    private void onBtnEliminar(ActionEvent event) {

        try {
            String invalidos = validarRequeridos();
            if (!invalidos.isBlank()) {
                new Mensaje().showModal(Alert.AlertType.WARNING, "Eliminar empleado", getStage(), "Favor consultar el empleado a eliminar");
            } else {
                new Mensaje().showModal(Alert.AlertType.WARNING, "Eliminar empleado", getStage(), "Favor consultar el empleado a eliminar");
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
                new Mensaje().showModal(Alert.AlertType.WARNING, "Guardar empleado", getStage(), invalidos);
            } else {

            }

        } catch (Exception ex) {
            Logger.getLogger(EmpleadosController.class.getName()).log(Level.SEVERE,
                    "Error guardando el empleado.", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar empleado", getStage(),
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

        } catch (Exception ex) {
            Logger.getLogger(EmpleadosController.class.getName()).log(Level.SEVERE,
                    "Error cargando el empleado.", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Cragar empleado", getStage(),
                    "Ocurrió un error cargando el empleado.");
        }

    }

}
