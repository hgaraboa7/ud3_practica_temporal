/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.vo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author hecto
 */
@Entity
@Table(name = "reparaciones")
@NamedQueries({
    @NamedQuery(name = "Reparaciones.findAll", query = "SELECT r FROM Reparaciones r"),
    @NamedQuery(name = "Reparaciones.findByCodemp", query = "SELECT r FROM Reparaciones r WHERE r.reparacionesPK.codemp = :codemp"),
    @NamedQuery(name = "Reparaciones.findByMatricula", query = "SELECT r FROM Reparaciones r WHERE r.reparacionesPK.matricula = :matricula"),
    @NamedQuery(name = "Reparaciones.findByImporte", query = "SELECT r FROM Reparaciones r WHERE r.importe = :importe"),
    @NamedQuery(name = "Reparaciones.findByFechai", query = "SELECT r FROM Reparaciones r WHERE r.reparacionesPK.fechai = :fechai"),
    @NamedQuery(name = "Reparaciones.findByFechaf", query = "SELECT r FROM Reparaciones r WHERE r.fechaf = :fechaf")})
public class Reparaciones implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ReparacionesPK reparacionesPK;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "importe")
    private Double importe;
    @Column(name = "fechaf")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaf;
    @JoinColumn(name = "matricula", referencedColumnName = "matricula", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Coches coches;
    @JoinColumn(name = "codemp", referencedColumnName = "codemp", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Empleados empleados;

    public Reparaciones() {
    }

    public Reparaciones(ReparacionesPK reparacionesPK) {
        this.reparacionesPK = reparacionesPK;
    }

    public Reparaciones(int codemp, String matricula, Date fechai) {
        this.reparacionesPK = new ReparacionesPK(codemp, matricula, fechai);
    }
    /*
    Constructor creado por mi
    */
    public Reparaciones(ReparacionesPK reparacionesPK, Double importe, Date fechaf) {
        this.reparacionesPK = reparacionesPK;
        this.fechaf=fechaf;
        this.importe=importe;
    }
    
    public ReparacionesPK getReparacionesPK() {
        return reparacionesPK;
    }

    public void setReparacionesPK(ReparacionesPK reparacionesPK) {
        this.reparacionesPK = reparacionesPK;
    }

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

    public Date getFechaf() {
        return fechaf;
    }

    public void setFechaf(Date fechaf) {
        this.fechaf = fechaf;
    }

    public Coches getCoches() {
        return coches;
    }

    public void setCoches(Coches coches) {
        this.coches = coches;
    }

    public Empleados getEmpleados() {
        return empleados;
    }

    public void setEmpleados(Empleados empleados) {
        this.empleados = empleados;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (reparacionesPK != null ? reparacionesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reparaciones)) {
            return false;
        }
        Reparaciones other = (Reparaciones) object;
        if ((this.reparacionesPK == null && other.reparacionesPK != null) || (this.reparacionesPK != null && !this.reparacionesPK.equals(other.reparacionesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.vo.Reparaciones[ reparacionesPK=" + reparacionesPK + " ]";
    }
    
}
