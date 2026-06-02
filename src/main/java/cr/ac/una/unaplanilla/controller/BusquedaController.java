/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.unaplanilla.controller;

import cr.ac.una.unaplanilla.model.EmpleadoDto;
import cr.ac.una.unaplanilla.service.EmpleadoService;
import cr.ac.una.unaplanilla.util.AppContext;
import cr.ac.una.unaplanilla.util.Mensaje;
import cr.ac.una.unaplanilla.util.Respuesta;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author USUARIO UNA PZ
 */
public class BusquedaController extends Controller implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private VBox contenedorParametros;
    @FXML
    private TableView<EmpleadoDto> tablaBusqueda;
    @FXML
    private Label labelBusqueda;
    @FXML
    private ScrollPane panelScroll;
    
    private MFXTextField txtCedula;
    private MFXTextField txtNombre;
    private MFXTextField txtPrimerApellido;
    private MFXTextField txtSegundoApellido;

    /**
     * Initializes the controller class.
     */
    public void initialize(URL url, ResourceBundle rb) {
        
        String tipo = (String) AppContext.getInstance().get("busquedaTipo");
        
        if ("Empleados".equals(tipo)) {
            labelBusqueda.setText("Búsqueda de Empleados");
            agregarCamposBusquedaEmpleados();
            configurarTablaEmpleados();
        }
        
    }    

    @FXML
    private void onBtnFiltrar(ActionEvent event) {
        String tipo = (String) AppContext.getInstance().get("busquedaTipo");
        if ("Empleados".equals(tipo)) {
            filtrarEmpleados();
        }
    }

    private void filtrarEmpleados() {
        EmpleadoService service = new EmpleadoService();
        Respuesta respuesta = service.buscarEmpleados(txtCedula.getText(), txtNombre.getText(),
                txtPrimerApellido.getText(), txtSegundoApellido.getText());
        if (respuesta.getEstado()) {
            List<EmpleadoDto> lista = (List<EmpleadoDto>) respuesta.getResultado("Empleados");
            tablaBusqueda.getItems().setAll(lista);
        } else {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Buscar", root.getScene().getWindow(), respuesta.getMensaje());
        }
    }

    @FXML
    private void onBtnAceptar(ActionEvent event) {
        
        EmpleadoDto seleccionado = (EmpleadoDto) tablaBusqueda.getSelectionModel().getSelectedItem();
        
        if (seleccionado == null) {
            new Mensaje().showModal(Alert.AlertType.WARNING, "Selección", root.getScene().getWindow(), "Debe seleccionar un registro.");
            return;
        }
        AppContext.getInstance().set("busquedaId", seleccionado.getId());
        ((Stage) root.getScene().getWindow()).close();
    }

    @FXML
    private void onBtnCancelar(ActionEvent event) {
        ((Stage) root.getScene().getWindow()).close();
    }

    @Override
    public void initialize() {
    }
    
    private void agregarCamposBusquedaEmpleados() {
        contenedorParametros.setPadding(new Insets(10));
        txtCedula = crearCampos("Cédula", "Ingrese cédula");
        txtNombre = crearCampos("Nombre", "Ingrese nombre");
        txtPrimerApellido = crearCampos("Primer Apellido", "Ingrese primer apellido");
        txtSegundoApellido = crearCampos("Segundo Apellido", "Ingrese segundo apellido");
        contenedorParametros.getChildren().addAll(txtCedula, txtNombre, txtPrimerApellido, txtSegundoApellido);
        panelScroll.setFitToWidth(true);
        panelScroll.setContent(contenedorParametros);
    }
    
    private MFXTextField crearCampos(String floatingText, String promptText) {
        
        MFXTextField textField = new MFXTextField();
        textField.setFloatingText(floatingText);
        textField.setPromptText(promptText);
        textField.setPrefWidth(150);

        return textField;
    }
    
    private TableColumn<EmpleadoDto, String> crearColumna(String titulo,
            javafx.util.Callback<TableColumn.CellDataFeatures<EmpleadoDto,
            String>, javafx.beans.value.ObservableValue<String>> valorCelda) {
        
        TableColumn<EmpleadoDto, String> columna = new TableColumn<>(titulo);
        columna.setCellValueFactory(valorCelda);
        columna.setPrefWidth(150);
        return columna;
    }
    
    private void configurarTablaEmpleados() {
        tablaBusqueda.getColumns().setAll(
            crearColumna("Cédula", c -> c.getValue().getCedulaProperty()),
            crearColumna("Nombre", c -> c.getValue().getNombreProperty()),
            crearColumna("Primer Apellido", c -> c.getValue().getPrimerApellidoProperty()),
            crearColumna("Segundo Apellido", c -> c.getValue().getSegundoApellidoProperty())
        );
        tablaBusqueda.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }
    
}
