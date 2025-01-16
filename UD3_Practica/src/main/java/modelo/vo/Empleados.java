/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.vo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author hecto
 */
@Entity
@Table(name = "empleados")
@NamedQueries({
    @NamedQuery(name = "Empleados.findAll", query = "SELECT e FROM Empleados e"),
    @NamedQuery(name = "Empleados.findByCodemp", query = "SELECT e FROM Empleados e WHERE e.codemp = :codemp"),
    @NamedQuery(name = "Empleados.findByNomemp", query = "SELECT e FROM Empleados e WHERE e.nomemp = :nomemp"),
    @NamedQuery(name = "Empleados.findBySalario", query = "SELECT e FROM Empleados e WHERE e.salario = :salario")})
public class Empleados implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "codemp")
    private Integer codemp;
    @Basic(optional = false)
    @Column(name = "nomemp")
    private String nomemp;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "salario")
    private Double salario;
    @OneToMany( mappedBy = "empleados")
    private List<Reparaciones> reparacionesList;
    @OneToMany( mappedBy = "empleados")
    private List<Bonificaciones> bonificacionesList;

    public Empleados() {
    }

    public Empleados(Integer codemp) {
        this.codemp = codemp;
    }

    public Empleados(Integer codemp, String nomemp) {
        this.codemp = codemp;
        this.nomemp = nomemp;
    }

    public Integer getCodemp() {
        return codemp;
    }

    public void setCodemp(Integer codemp) {
        this.codemp = codemp;
    }

    public String getNomemp() {
        return nomemp;
    }

    public void setNomemp(String nomemp) {
        this.nomemp = nomemp;
    }

    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

    public List<Reparaciones> getReparacionesList() {
        return reparacionesList;
    }

    public void setReparacionesList(List<Reparaciones> reparacionesList) {
        this.reparacionesList = reparacionesList;
    }

    public List<Bonificaciones> getBonificacionesList() {
        return bonificacionesList;
    }

    public void setBonificacionesList(List<Bonificaciones> bonificacionesList) {
        this.bonificacionesList = bonificacionesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codemp != null ? codemp.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Empleados)) {
            return false;
        }
        Empleados other = (Empleados) object;
        if ((this.codemp == null && other.codemp != null) || (this.codemp != null && !this.codemp.equals(other.codemp))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.nomemp;
    }
    
}
