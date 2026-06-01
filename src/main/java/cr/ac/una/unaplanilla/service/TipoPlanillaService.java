/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.unaplanilla.service;

import cr.ac.una.unaplanilla.model.Empleado;
import cr.ac.una.unaplanilla.model.EmpleadoDto;
import cr.ac.una.unaplanilla.model.TipoPlanilla;
import cr.ac.una.unaplanilla.model.TipoPlanillaDTO;
import cr.ac.una.unaplanilla.util.EntityManagerHelper;
import cr.ac.una.unaplanilla.util.Respuesta;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.TypedQuery;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author USUARIO UNA PZ
 */
public class TipoPlanillaService {

    private EntityManager em = EntityManagerHelper.getInstance().getManager();
    private EntityTransaction et;

    public Respuesta getTipoPlanilla(Long id) {
        try {

            TypedQuery<TipoPlanilla> qryTipoPlanilla = em.createNamedQuery("TipoPlanilla.findByTplaId", TipoPlanilla.class);
            qryTipoPlanilla.setParameter("tplaId", id);
            TipoPlanilla tipoPlanilla = qryTipoPlanilla.getSingleResult();
            TipoPlanillaDTO tipoPlanillaDto = new TipoPlanillaDTO(tipoPlanilla);

            for (Empleado empleado : tipoPlanilla.getEmpleadoList()) {
                tipoPlanillaDto.getEmpleados().add(new EmpleadoDto(empleado));
            }

            return new Respuesta(true, "", "", "TipoPlanilla", tipoPlanillaDto);

        } catch (NoResultException ex) {
            return new Respuesta(false, "No existe un tipo de planilla con el id ingresado.", "getTipoPlanilla NoResultException");
        } catch (NonUniqueResultException ex) {
            Logger.getLogger(TipoPlanillaService.class.getName()).log(Level.SEVERE, "Ocurrio un error al consultar el tipo de planilla.", ex);
            return new Respuesta(false, "Ocurrio un error al consultar el tipo de planilla.", "getTipoPlanilla NonUniqueResultException");
        } catch (Exception ex) {
            Logger.getLogger(TipoPlanillaService.class.getName()).log(Level.SEVERE, "Error obteniendo el tipo de planilla [" + id + "]", ex);
            return new Respuesta(false, "Error obteniendo el tipo de planilla.", "getTipoPlanilla " + ex.getMessage());
        }
    }

    public Respuesta guardarTipoPlanilla(TipoPlanillaDTO tipoPlanillaDto) {
        try {
            et = em.getTransaction();
            et.begin();

            TipoPlanilla tipoPlanilla;
            if (tipoPlanillaDto.getId() != null && tipoPlanillaDto.getId() > 0) {
                tipoPlanilla = em.find(TipoPlanilla.class, tipoPlanillaDto.getId());
                if (tipoPlanilla == null) {
                    et.rollback();
                    return new Respuesta(false, "No se encontró el tipo de planilla a modificar.", "guardarTipoPlanilla. NoResultException");
                }
                tipoPlanilla.actualizar(tipoPlanillaDto);

                for (EmpleadoDto empleadoEliminado : tipoPlanillaDto.getEmpleadosEliminados()) {
                    tipoPlanilla.getEmpleadoList().removeIf(e -> e.getId().equals(empleadoEliminado.getId()));
                }

                if (!tipoPlanillaDto.getEmpleados().isEmpty()) {
                    for (EmpleadoDto empleadoDto : tipoPlanillaDto.getEmpleados()) {
                        if (empleadoDto.getModificado()) {
                            Empleado empleado = em.find(Empleado.class, empleadoDto.getId());
                            empleado.getTiposPlanilla().add(tipoPlanilla);
                            tipoPlanilla.getEmpleadoList().add(empleado);
                        }

                    }
                }

                tipoPlanilla = em.merge(tipoPlanilla);
            } else {
                tipoPlanilla = new TipoPlanilla(tipoPlanillaDto);
                em.persist(tipoPlanilla);
            }

            et.commit();
            return new Respuesta(true, "", "", "TipoPlanilla", new TipoPlanillaDTO(tipoPlanilla));

        } catch (Exception ex) {
            et.rollback();
            Logger.getLogger(EmpleadoService.class.getName()).log(Level.SEVERE, "Error guardando el tipo de planilla.", ex);
            return new Respuesta(false, "Error guardando el tipo de planilla.", "guardarTipoPlanilla " + ex.getMessage());
        }
    }

    public Respuesta eliminarTipoPlanilla(Long id) {
        try {
            et = em.getTransaction();
            et.begin();

            TipoPlanilla tipoPlanilla;
            if (id != null && id > 0) {
                tipoPlanilla = em.find(TipoPlanilla.class, id);
                if (tipoPlanilla == null) {
                    return new Respuesta(false, "No se encontró el tipoPlanilla a eliminar.", "eliminarTipoPlanilla. NoResultException");
                }
                em.remove(tipoPlanilla);
            } else {
                return new Respuesta(false, "Favor consultar el tipoPlanilla a eliminar.", "eliminarTipoPlanilla. NoResultException");
            }

            et.commit();
            return new Respuesta(true, "", "");

        } catch (Exception ex) {
            et.rollback();
            Logger.getLogger(EmpleadoService.class.getName()).log(Level.SEVERE, "Error eliminando el tipoPlanilla", ex);
            return new Respuesta(false, "Error eliminando el tipoPlanilla.", "eliminarTipoPlanilla " + ex.getMessage());
        }
    }

}
