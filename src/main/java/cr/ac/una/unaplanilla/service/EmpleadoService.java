/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.unaplanilla.service;

import cr.ac.una.unaplanilla.model.Empleado;
import cr.ac.una.unaplanilla.model.EmpleadoDto;
import cr.ac.una.unaplanilla.util.EntityManagerHelper;
import cr.ac.una.unaplanilla.util.Respuesta;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author USUARIO UNA PZ
 */
public class EmpleadoService {
    
    private EntityManager em = EntityManagerHelper.getInstance().getManager();
    private EntityTransaction et;
    
    public Respuesta getEmpleado(Long id){
        try{
            
            TypedQuery<Empleado> qryEmpleado = em.createNamedQuery("Empleado.findById", Empleado.class);
            qryEmpleado.setParameter("id", id);
            EmpleadoDto empleadoDto = new EmpleadoDto(qryEmpleado.getSingleResult());
            return new Respuesta(true, "", "", "Empleado", empleadoDto);
            
            
        } catch (NoResultException ex) {
            return new Respuesta(false, "No existe un empleado con el id ingresado.", "getEmpleado NoResultException");
        } catch (NonUniqueResultException ex) {
            Logger.getLogger(EmpleadoService.class.getName()).log(Level.SEVERE, "Ocurrio un error al consultar el empleado.", ex);
            return new Respuesta(false, "Ocurrio un error al consultar el empleado.", "getEmpleado NonUniqueResultException");
        } catch (Exception ex) {
            Logger.getLogger(EmpleadoService.class.getName()).log(Level.SEVERE, "Error obteniendo el empleado [" + id + "]", ex);
            return new Respuesta(false, "Error obteniendo el empleado.", "getEmpleado " + ex.getMessage());
        }
    }
    
    public Respuesta guardarEmpleado(EmpleadoDto empleadoDto){
        try {
            et = em.getTransaction ();
            et.begin();

            Empleado empleado;
            if(empleadoDto.getId() != null && empleadoDto.getId() > 0){
                empleado = em.find(Empleado.class, empleadoDto.getId());
                if (empleado == null){
                    return new Respuesta(false, "No se encontró el empleado a modificar.", "guardarEmpleado. NoResultException");
                }
                empleado.actualizar(empleadoDto);
                empleado = em.merge(empleado);
            } else{
                empleado = new Empleado (empleadoDto) ;
                em.persist (empleado) ;
            }
            
            et.commit();
            return new Respuesta(true, "", "", "Empleado", new EmpleadoDto(empleado));
            
        } catch (Exception ex) {
            et.rollback();
            Logger.getLogger(EmpleadoService.class.getName()).log(Level.SEVERE, "Error guardando el empleado", ex);
            return new Respuesta(false, "Error guardando el empleado.", "guardarEmpleado " + ex.getMessage());
        }
    }
    
    public Respuesta eliminarEmpleado(Long id){
        try {
            et = em.getTransaction ();
            et.begin();

            Empleado empleado;
            if(id != null && id > 0){
                empleado = em.find(Empleado.class, id);
                if (empleado == null){
                    return new Respuesta(false, "No se encontró el empleado a eliminar.", "eliminarEmpleado. NoResultException");
                }
                em.remove(empleado);
            } else{
                return new Respuesta(false, "Favor consultar el empleado a eliminar.", "eliminarEmpleado. NoResultException");
            }
            
            et.commit();
            return new Respuesta(true, "", "");
            
        } catch (Exception ex) {
            et.rollback();
            Logger.getLogger(EmpleadoService.class.getName()).log(Level.SEVERE, "Error eliminando el empleado", ex);
            return new Respuesta(false, "Error eliminando el empleado.", "guardarEmpleado " + ex.getMessage());
        }
    }
    
    public Respuesta login(String usuario, String clave) {
        try {
            TypedQuery<Empleado> qryLogin = em.createNamedQuery("Empleado.login", Empleado.class);
            qryLogin.setParameter("usuario", usuario);
            qryLogin.setParameter("clave", clave);
            EmpleadoDto empleadoDto = new EmpleadoDto(qryLogin.getSingleResult());
            return new Respuesta(true, "", "", "Empleado", empleadoDto);

        } catch (NoResultException ex) {
            return new Respuesta(false, "Usuario o clave incorrectos.", "login NoResultException");
        } catch (NonUniqueResultException ex) {
            Logger.getLogger(EmpleadoService.class.getName())
                    .log(Level.SEVERE, "Múltiples resultados en login.", ex);
            return new Respuesta(false, "Usuario o clave incorrectos.", "login NonUniqueResultException");
        } catch (Exception ex) {
            Logger.getLogger(EmpleadoService.class.getName())
                    .log(Level.SEVERE, "Error en login.", ex);
            return new Respuesta(false, "Error al intentar ingresar.", "login " + ex.getMessage());
        }
    }
    
    public Respuesta buscarEmpleados(String cedula, String nombre, String primerApellido, String segundoApellido) {
        try {
            TypedQuery<Empleado> qry = em.createNamedQuery("Empleado.buscar", Empleado.class);
            qry.setParameter("cedula", cedula != null && !cedula.isBlank() ? "%" + cedula + "%" : null);
            qry.setParameter("nombre", nombre != null && !nombre.isBlank() ? "%" + nombre + "%" : null);
            qry.setParameter("primerApellido", primerApellido != null && !primerApellido.isBlank() ? "%" + primerApellido + "%" : null);
            qry.setParameter("segundoApellido", segundoApellido != null && !segundoApellido.isBlank() ? "%" + segundoApellido + "%" : null);

            List<EmpleadoDto> resultado = new ArrayList<>();
            for (Empleado empleado : qry.getResultList()) {
                resultado.add(new EmpleadoDto(empleado));
            }
            return new Respuesta(true, "", "", "Empleados", resultado);

        } catch (Exception ex) {
            Logger.getLogger(EmpleadoService.class.getName()).log(Level.SEVERE, "Error buscando empleados.", ex);
            return new Respuesta(false, "Error buscando empleados.", "buscarEmpleados " + ex.getMessage());
        }
    }
    
}
