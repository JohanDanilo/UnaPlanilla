package cr.ac.una.unaplanilla.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author cbcar
 */
public class TipoPlanillaDto {

    private StringProperty id;
    private StringProperty codigo;
    private StringProperty descripcion;
    private StringProperty planillaPorMes;
    private Integer anoUltimaPlanilla;
    private Integer mesUltimaPlanilla;
    private Integer numeroUltimaPlanilla;
    private BooleanProperty activo;
    private Long version;
    private ObservableList<EmpleadoDto> empleados;
    private List<EmpleadoDto> empleadosEliminados;
    private Boolean modificado;

    public TipoPlanillaDto() {
        this.id = new SimpleStringProperty("");
        this.codigo = new SimpleStringProperty("");
        this.descripcion = new SimpleStringProperty("");
        this.planillaPorMes = new SimpleStringProperty("");
        this.activo = new SimpleBooleanProperty(true);
        this.modificado = false;
        empleados = FXCollections.observableArrayList();
        empleadosEliminados = new ArrayList<>();
    }

    public Long getId() {
        if (this.id.get() != null && !this.id.get().isEmpty()) {
            return Long.valueOf(this.id.get());
        } else {
            return null;
        }
    }

    public void setId(Long id) {
        this.id.set(id.toString());
    }

    public String getCodigo() {
        return codigo.get();
    }

    public void setCodigo(String codigo) {
        this.codigo.set(codigo);
    }

    public String getDescripcion() {
        return descripcion.get();
    }

    public void setDescripcion(String descripcion) {
        this.descripcion.set(descripcion);
    }

    public Integer getPlanillaPorMes() {
        if (this.planillaPorMes.get() != null && !this.planillaPorMes.get().isEmpty()) {
            return Integer.valueOf(this.planillaPorMes.get());
        } else {
            return null;
        }
    }

    public void setPlanillaPorMes(Integer planillaPorMes) {
        this.planillaPorMes.set(planillaPorMes.toString());
    }

    public Integer getAnoUltimaPlanilla() {
        return anoUltimaPlanilla;
    }

    public void setAnoUltimaPlanilla(Integer anoUltimaPlanilla) {
        this.anoUltimaPlanilla = anoUltimaPlanilla;
    }

    public Integer getMesUltimaPlanilla() {
        return mesUltimaPlanilla;
    }

    public void setMesUltimaPlanilla(Integer mesUltimaPlanilla) {
        this.mesUltimaPlanilla = mesUltimaPlanilla;
    }

    public Integer getNumeroUltimaPlanilla() {
        return numeroUltimaPlanilla;
    }

    public void setNumeroUltimaPlanilla(Integer numeroUltimaPlanilla) {
        this.numeroUltimaPlanilla = numeroUltimaPlanilla;
    }

    public Boolean getActivo() {
        return activo.get();
    }

    public void setActivo(Boolean activo) {
        this.activo.set(activo);
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

    // TODO
    public StringProperty getIdProperty() {
        return id;
    }

    // TODO
    public StringProperty getCodigoProperty() {
        return codigo;
    }

    // TODO
    public StringProperty getDescripcionProperty() {
        return descripcion;
    }

    // TODO
    public StringProperty getPlanillaPorMesProperty() {
        return planillaPorMes;
    }

    // TODO
    public BooleanProperty getActivoProperty() {
        return activo;
    }

    public ObservableList<EmpleadoDto> getEmpleados() {
        return empleados;
    }

    // TODO
    public void setEmpleados(ObservableList<EmpleadoDto> empleados) {
        this.empleados = empleados;
    }

    public List<EmpleadoDto> getEmpleadosEliminados() {
        return empleadosEliminados;
    }

    public void setEmpleadosEliminados(List<EmpleadoDto> empleadosEliminados) {
        this.empleadosEliminados = empleadosEliminados;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.id);
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
        final TipoPlanillaDto other = (TipoPlanillaDto) obj;
        return Objects.equals(this.id.get(), other.id.get());
    }

    @Override
    public String toString() {
        return "TipoPlanillaDto{" + "codigo=" + codigo + ", descripcion=" + descripcion + '}';
    }

}
