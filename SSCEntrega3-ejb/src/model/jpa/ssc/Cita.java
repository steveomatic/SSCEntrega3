/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.jpa.ssc;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author Grupo E
 */
@Entity
public class Cita implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(nullable=false)
    private Date fecha;
    
    
    private String comentarios;

    @Column(nullable=false)
    private String tipo_de_cita;

    
    
    @ManyToOne
    @JoinColumn(nullable=false)
    private Ciudadano ciudadano;

    
    @ManyToOne
    @JoinColumn(nullable=false)
    private Profesional profesional;
    
    @OneToMany(mappedBy="id_cita", orphanRemoval=true)
    private List<Intervenciones> intervenciones;
    
    @Column(nullable=false)
    private EstadoCita estado;

    

    
    
    public Cita(){
        
    }
    
    public Ciudadano getCiudadano() {
        return ciudadano;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
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
    
    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }
    
    public String getTipo_de_cita() {
        return tipo_de_cita;
    }

    public void setTipo_de_cita(String tipo_de_cita) {
        this.tipo_de_cita = tipo_de_cita;
    }    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public List<Intervenciones> getIntervenciones() {
        return intervenciones;
    }

    public void setIntervenciones(List<Intervenciones> intervenciones) {
        this.intervenciones = intervenciones;
    }
    
    public EstadoCita getEstado() {
        return estado;
    }
    
    public String getEstadoString() {
        if(estado.equals(EstadoCita.citaPlanificada)){
            return "Cita planificada";
        }else if(estado.equals(EstadoCita.ausencia)){
            return "Ausencia";
        }else if(estado.equals(EstadoCita.noRealizada)){
            return "No realizada";
        }else{
            return "Planificada por otro profesional";
        }
    }

    public void setEstado(EstadoCita estado) {
        this.estado = estado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cita)) {
            return false;
        }
        Cita other = (Cita) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.sercsocial.Cita[ id=" + id + " ]";
    }
    
}
