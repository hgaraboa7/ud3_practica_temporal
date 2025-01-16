/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.vo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author hecto
 */
@Entity
@Table(name = "bonificaciones")
@NamedQueries({
    @NamedQuery(name = "Bonificaciones.findAll", query = "SELECT b FROM Bonificaciones b"),
    @NamedQuery(name = "Bonificaciones.findByCodemp", query = "SELECT b FROM Bonificaciones b WHERE b.bonificacionesPK.codemp = :codemp"),
    @NamedQuery(name = "Bonificaciones.findByMes", query = "SELECT b FROM Bonificaciones b WHERE b.bonificacionesPK.mes = :mes"),
    @NamedQuery(name = "Bonificaciones.findByImportebonificado", query = "SELECT b FROM Bonificaciones b WHERE b.importebonificado = :importebonificado")})
public class Bonificaciones implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected BonificacionesPK bonificacionesPK;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "importebonificado")
    private Double importebonificado;
    @JoinColumn(name = "codemp", referencedColumnName = "codemp", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Empleados empleados;

    public Bonificaciones() {
    }

    public Bonificaciones(BonificacionesPK bonificacionesPK) {
        this.bonificacionesPK = bonificacionesPK;
    }

    public Bonificaciones(int codemp, String mes) {
        this.bonificacionesPK = new BonificacionesPK(codemp, mes);
    }
    public Bonificaciones(int codemp, String mes, Double importebonificado) {
        this.bonificacionesPK = new BonificacionesPK(codemp, mes);
        this.importebonificado=importebonificado;
    }

    public BonificacionesPK getBonificacionesPK() {
        return bonificacionesPK;
    }

    public void setBonificacionesPK(BonificacionesPK bonificacionesPK) {
        this.bonificacionesPK = bonificacionesPK;
    }

    public Double getImportebonificado() {
        return importebonificado;
    }

    public void setImportebonificado(Double importebonificado) {
        this.importebonificado = importebonificado;
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
        hash += (bonificacionesPK != null ? bonificacionesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bonificaciones)) {
            return false;
        }
        Bonificaciones other = (Bonificaciones) object;
        if ((this.bonificacionesPK == null && other.bonificacionesPK != null) || (this.bonificacionesPK != null && !this.bonificacionesPK.equals(other.bonificacionesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.vo.Bonificaciones[ bonificacionesPK=" + bonificacionesPK + " ]";
    }
    
}
