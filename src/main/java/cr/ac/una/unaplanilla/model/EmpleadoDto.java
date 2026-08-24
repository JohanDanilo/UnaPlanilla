package cr.ac.una.unaplanilla.model;

import jakarta.json.bind.annotation.JsonbTransient;
import java.time.LocalDate;
import java.util.Objects;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author cbcar
 */
public class EmpleadoDto {

    private StringProperty id;
    private StringProperty nombre;
    private StringProperty primerApellido;
    private StringProperty segundoApellido;
    private StringProperty cedula;
    private ObjectProperty<String> genero;
    private StringProperty correo;
    private BooleanProperty administrador;
    private StringProperty usuario;
    private StringProperty clave;
    private ObjectProperty<LocalDate> fechaIngreso;
    private ObjectProperty<LocalDate> fechaSalida;
    private BooleanProperty activo;
    private Long version;
    private Boolean modificado;
    

    public EmpleadoDto() {
        this.id = new SimpleStringProperty("");
        this.nombre = new SimpleStringProperty("");
        this.primerApellido = new SimpleStringProperty("");
        this.segundoApellido = new SimpleStringProperty("");
        this.cedula = new SimpleStringProperty("");
        this.genero = new SimpleObjectProperty("M");
        this.correo = new SimpleStringProperty("");
        this.administrador = new SimpleBooleanProperty(false);
        this.usuario = new SimpleStringProperty("");
        this.clave = new SimpleStringProperty("");
        this.fechaIngreso = new SimpleObjectProperty<>(LocalDate.now());
        this.fechaSalida = new SimpleObjectProperty<>();
        this.activo = new SimpleBooleanProperty(true);
        this.modificado = false;
    }

    public Long getId() {
        if (this.id.get() != null && !this.id.get().isBlank()) {
            return Long.valueOf(this.id.get());
        } else {
            return null;
        }
    }

    public void setId(Long id) {
        this.id.set(id.toString());
    }

    public String getNombre() {
        return nombre.get();
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    public String getPrimerApellido() {
        return primerApellido.get();
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido.set(primerApellido);
    }

    public String getSegundoApellido() {
        return segundoApellido.get();
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido.set(segundoApellido);
    }

    public String getCedula() {
        return cedula.get();
    }

    public void setCedula(String cedula) {
        this.cedula.set(cedula);
    }

    public String getGenero() {
        return genero.get();
    }

    public void setGenero(String genero) {
        this.genero.set(genero);
    }

    public String getCorreo() {
        return correo.get();
    }

    public void setCorreo(String correo) {
        this.correo.set(correo);
    }

    public Boolean getAdministrador() {
        return administrador.get();
    }

    public void setAdministrador(Boolean administrador) {
        this.administrador.set(administrador);
    }

    public String getUsuario() {
        return usuario.get();
    }

    public void setUsuario(String usuario) {
        this.usuario.set(usuario);
    }

    public String getClave() {
        return clave.get();
    }

    public void setClave(String clave) {
        this.clave.set(clave);
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso.get();
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso.set(fechaIngreso);
    }

    public LocalDate getFechaSalida() {
        return fechaSalida.get();
    }

    public void setFechaSalida(LocalDate fechaSalida) {
        this.fechaSalida.set(fechaSalida);
    }

    public Boolean getActivo() {
        return activo.get();
    }

    public void setActivo(Boolean activo) {
        this.activo.set(activo);
    }

    @JsonbTransient
    public StringProperty getIdProperty() {
        return id;
    }

    @JsonbTransient
    public StringProperty getNombreProperty() {
        return nombre;
    }

    @JsonbTransient
    public StringProperty getPrimerApellidoProperty() {
        return primerApellido;
    }

    @JsonbTransient
    public StringProperty getSegundoApellidoProperty() {
        return segundoApellido;
    }

    @JsonbTransient
    public StringProperty getCedulaProperty() {
        return cedula;
    }

    @JsonbTransient
    public ObjectProperty<String> getGeneroProperty() {
        return genero;
    }

    @JsonbTransient
    public StringProperty getCorreoProperty() {
        return correo;
    }

    @JsonbTransient
    public BooleanProperty getAdministradorProperty() {
        return administrador;
    }

    @JsonbTransient
    public StringProperty getUsuarioProperty() {
        return usuario;
    }

    @JsonbTransient
    public StringProperty getClaveProperty() {
        return clave;
    }

    @JsonbTransient
    public ObjectProperty<LocalDate> getFechaIngresoProperty() {
        return fechaIngreso;
    }

    @JsonbTransient
    public ObjectProperty<LocalDate> getFechaSalidaProperty() {
        return fechaSalida;
    }

    @JsonbTransient
    public BooleanProperty getActivoProperty() {
        return activo;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Boolean getModificado() {
        return modificado;
    }

    public void setModificado(Boolean modificado) {
        this.modificado = modificado;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EmpleadoDto other = (EmpleadoDto) obj;
        return Objects.equals(this.id.get(), other.id.get());
    }

    @Override
    public String toString() {
        return "EmpleadoDto{" + "id=" + id + ", nombre=" + nombre + ", primerApellido=" + primerApellido + ", cedula=" + cedula + '}';
    }

}
