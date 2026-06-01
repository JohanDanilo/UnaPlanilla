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

public class TipoPlanillaDTO {

    private StringProperty planillasXMes;
    
    private StringProperty id;
    private StringProperty codigo;
    private StringProperty descripcion;
    private StringProperty cantidadPlanillasMes;
    private BooleanProperty activo;
    private ObservableList<EmpleadoDto> empleados;
    private List<EmpleadoDto> empleadosEliminados;
    private Boolean modificado;
    private Long version;

    public TipoPlanillaDTO() {
        this.id = new SimpleStringProperty("");
        this.codigo = new SimpleStringProperty("");
        this.descripcion = new SimpleStringProperty("");
        this.cantidadPlanillasMes = new SimpleStringProperty("");
        this.activo = new SimpleBooleanProperty(true);
        this.modificado = false;
        this.empleados = FXCollections.observableArrayList();
        this.empleadosEliminados = new ArrayList<>();
    }
    
    public TipoPlanillaDTO(TipoPlanilla tipoPlanilla) {
        
        this();
        this.id.set(tipoPlanilla.getTplaId().toString());
        this.codigo.set(tipoPlanilla.getTplaCodigo());
        this.activo.set(tipoPlanilla.getTplaEstado().equals("A"));
        this.descripcion.set(tipoPlanilla.getTplaDescripcion());
        this.cantidadPlanillasMes.set(tipoPlanilla.getTplaPlaxmes().toString());
        this.version = tipoPlanilla.getTplaVersion();
        
    }

    public Long getId() {
        if (this.id.get() != null && !this.id.get().isBlank()) {
            return Long.valueOf(this.id.get());
        } else {
            return null;
        }
    }

    public void setId(Long id) {
        if (id != null) {
            this.id.set(id.toString());
        } else {
            this.id.set("");
        }
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

    public Integer getPlanillasXMes() {
        if (this.planillasXMes.get() != null && !this.planillasXMes.get().isBlank()) {
            return Integer.valueOf(this.planillasXMes.get());
        } else {
            return null;
        }
    }

    public void setPlanillasXMes(Integer planillasXMes) {
        if (planillasXMes != null) {
            this.planillasXMes.set(planillasXMes.toString());
        } else {
            this.planillasXMes.set("");
        }
    }

    public Boolean getActivo() {
        return activo.get();
    }

    public void setActivo(Boolean activo) {
        this.activo.set(activo);
    }

     public ObservableList<EmpleadoDto> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(ObservableList<EmpleadoDto> empleados) {
        this.empleados = empleados;
    }

    public StringProperty getIdProperty() {
        return id;
    }

    public StringProperty getCodigoProperty() {
        return codigo;
    }

    public StringProperty getDescripcionProperty() {
        return descripcion;
    }

    public StringProperty getPlanillasXMesProperty() {
        return planillasXMes;
    }
    
    public StringProperty getCantidadPlanillasMesProperty() {
        return cantidadPlanillasMes;
    }
    
    public Integer getCantidadPlanillasMes() {
        String val = cantidadPlanillasMes.get();
        return (val != null && !val.isBlank()) ? Integer.valueOf(val) : null;
    }

    public void setCantidadPlanillasMes(Integer cantidadPlanillasMes) {
        this.cantidadPlanillasMes.set(cantidadPlanillasMes != null ? cantidadPlanillasMes.toString() : "");
    }

    public BooleanProperty getActivoProperty() {
        return activo;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public List<EmpleadoDto> getEmpleadosEliminados() {
        return empleadosEliminados;
    }

    public void setEmpleadosEliminados(List<EmpleadoDto> empleadosEliminados) {
        this.empleadosEliminados = empleadosEliminados;
    }

    public Boolean getModificado() {
        return modificado;
    }

    public void setModificado(Boolean modificado) {
        this.modificado = modificado;
    }
    
    
    
    @Override
    public int hashCode() {
        return Objects.hashCode(this.id.get());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        final TipoPlanillaDTO other = (TipoPlanillaDTO) obj;
        return Objects.equals(this.id.get(), other.id.get());
    }

    @Override
    public String toString() {
        return "TipoPlanillaDto{" + "id=" + id.get() + ", codigo=" + codigo.get()
                + ", descripcion=" + descripcion.get() + ", cantidadPlanillasMes=" + cantidadPlanillasMes.get() + '}';
    }
}

