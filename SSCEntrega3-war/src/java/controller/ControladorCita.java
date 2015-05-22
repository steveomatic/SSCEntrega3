/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import ejb.InfoCitaEJB;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import model.jpa.ssc.Cita;
import model.jpa.ssc.Ciudadano;
import model.jpa.ssc.Intervenciones;
import model.jpa.ssc.Profesional;

import javax.enterprise.context.RequestScoped;
import model.jpa.ssc.EstadoCita;

/**
 *
 * @author Álvaro
 */

@Named(value="controladorCita")
@RequestScoped
public class ControladorCita {
    
    @EJB
    private InfoCitaEJB infoCitaEJB;
    
    
    //Datos de la cita
    private Date fecha;
    private String comentarios;
    private String tipo_de_cita;
    private Ciudadano ciudadano;
    private Profesional profesional;
    private List<Intervenciones> intervenciones;
    private EstadoCita estado;
    
    //Id obtenido al crear una cita o al pinchar en ver cita
    private Long id;
    
    
    public ControladorCita(){
        
    }
    
    public String browsePage(Long id){
        if(id == null) return "index.xhtml";
        this.id=id;
        fecha = infoCitaEJB.getFecha(id);
        comentarios = infoCitaEJB.getComentarios(id);
        tipo_de_cita = infoCitaEJB.getTipo_de_cita(id);
        ciudadano = infoCitaEJB.getCiudadano(id);
        profesional = infoCitaEJB.getProfesional(id);
        intervenciones = infoCitaEJB.getIntervenciones(id);
        estado = infoCitaEJB.getEstado(id);
        
        return "info_cita.xhtml";
    }
        
    
    public void initialize(Cita c){
       
    }

    public Long getId() {
        return id;
    }

    public Date getFecha() {
        return fecha;
    }

    public String getComentarios() {
        return comentarios;
    }

    public String getTipo_de_cita() {
        return tipo_de_cita;
    }

    public Ciudadano getCiudadano() {
        return ciudadano;
    }

    public Profesional getProfesional() {
        return profesional;
    }

    public List<Intervenciones> getIntervenciones() {
        return intervenciones;
    }

    public EstadoCita getEstado(){
        return estado;
    }
    
    public void setEstado(String est){
        infoCitaEJB.setEstado(id, estado);
    }
}
