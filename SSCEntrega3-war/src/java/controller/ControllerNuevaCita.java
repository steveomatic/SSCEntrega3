/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import ejb.CrearCitaEJB;
import exceptions.CiudadanoNotFoundException;
import exceptions.CrearCitaException;
import exceptions.ProfesionalNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import model.jpa.ssc.Cita;
import model.jpa.ssc.Ciudadano;
import model.jpa.ssc.EstadoCita;
import model.jpa.ssc.Profesional;

/**
 *
 * @author haritz
 */
@ManagedBean(name="ctrNuevaCita")
@RequestScoped
public class ControllerNuevaCita implements Serializable {
    
    @EJB
    private CrearCitaEJB crearCitaBean;

    @Inject
    private ControladorCita controladorCita; //Para poder pasarle el id a ver cita.
    
    @Inject
    private ControllerLogin login;
    
    private String DNICiudadano;
    private Ciudadano ciudadano;
    
    private String DNIProfesional;
    private Profesional profesional;
    
    
    private Date fecha;
    private String tipoCita;
    private String detalleGestion;
        

    @PostConstruct
    public void addDefaultValue(){
        if(!login.isIsAdmin())
            setDNIProfesional(login.getDni());
    }
    
    public String getDNICiudadano() {
        return DNICiudadano;
    }

    public void setDNICiudadano(String DNICiudadano) {
        this.DNICiudadano = DNICiudadano;
    }

    public String getDNIProfesional() {
        return DNIProfesional;
    }

    public void setDNIProfesional(String DNIProfesional) {
        this.DNIProfesional = DNIProfesional;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getTipoCita() {
        return tipoCita;
    }

    public void setTipoCita(String tipoCita) {
        this.tipoCita = tipoCita;
    }

    public String getDetalleGestion() {
        return detalleGestion;
    }

    public void setDetalleGestion(String detalleGestion) {
        this.detalleGestion = detalleGestion;
    }
    
    public CrearCitaEJB getCrearCitaBean() {
        return crearCitaBean;
    }

    public void setCrearCitaBean(CrearCitaEJB crearCitaBean) {
        this.crearCitaBean = crearCitaBean;
    }

    public ControladorCita getControladorCita() {
        return controladorCita;
    }

    public void setControladorCita(ControladorCita controladorCita) {
        this.controladorCita = controladorCita;
    }

    public Ciudadano getCiudadano() {
        return ciudadano;
    }

    public void setCiudadano(Ciudadano ciudadano) {
        this.ciudadano = ciudadano;
    }

    public Profesional getProfesional() {
        return profesional;
    }

    public void setProfesional(Profesional profesional) {
        this.profesional = profesional;
    }
    
    public ControllerLogin getLogin() {
        return login;
    }

    public void setLogin(ControllerLogin login) {
        this.login = login;
    }
    
    
    public String persistCita() throws IOException{
        try {
            ciudadano = crearCitaBean.getCiudadano(DNICiudadano);
            profesional = crearCitaBean.getProfesional(DNIProfesional);       
           //Convierto aquí la fecha a sql.date
            if(fecha == null){
                FacesContext ctx = FacesContext.getCurrentInstance();
                ctx.addMessage("calendario", new FacesMessage("Introduzca una fecha."));
                return null;
            }
            
            if(tipoCita==null || tipoCita.equals("")){
                FacesContext ctx = FacesContext.getCurrentInstance();
                ctx.addMessage("calendario", new FacesMessage("Introduzca un tipo de cita."));
                return null;
            }
            
            java.sql.Date date = new java.sql.Date(fecha.getTime());

            Cita cita = new Cita();
            //Creo la cita ahora.
            cita.setCiudadano(ciudadano);
            cita.setProfesional(profesional);
            cita.setComentarios(detalleGestion);
            cita.setFecha(date);
            cita.setTipo_de_cita(tipoCita);
            //TODO ESTADO CITA
            if(!login.isIsAdmin() && !login.getDni().equals(getDNIProfesional()))
                cita.setEstado(EstadoCita.planificadaPorOtroProfesional);
            else
                cita.setEstado(EstadoCita.citaPlanificada);

            crearCitaBean.setCita(cita);
            return controladorCita.browsePage(cita.getId());
        } catch (CiudadanoNotFoundException e) {
            FacesContext ctx = FacesContext.getCurrentInstance();
            String error = "No se encuentra al ciudadano con DNI " + e.getMessage() +
                    " en la base de datos. Es posible que esté mal escrito o"
                    + " que no se haya dado de alta aún";
            ctx.addMessage(DNICiudadano, new FacesMessage(error));
            
            return null;
            
            //Logger.getLogger(ControllerNuevaCita.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProfesionalNotFoundException e) {
            String error = "No se encuentra al profesional con DNI " + e.getMessage()
                    + " en la base de datos. Es posible que esté mal escrito o"
                    + " no esté dado de alta aún.";
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(DNIProfesional, new FacesMessage(error));            
            return null;
        } catch (CrearCitaException ex) {
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(null, new FacesMessage(ex.getMessage()));            
            return null;
        }

    }

}