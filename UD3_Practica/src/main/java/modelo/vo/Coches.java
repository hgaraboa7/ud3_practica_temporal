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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author hecto
 */
@Entity
@Table(name = "coches")
@NamedQueries({
    @NamedQuery(name = "Coches.findAll", query = "SELECT c FROM Coches c"),
    @NamedQuery(name = "Coches.findByMatricula", query = "SELECT c FROM Coches c WHERE c.matricula = :matricula"),
    @NamedQuery(name = "Coches.findByMarca", query = "SELECT c FROM Coches c WHERE c.marca = :marca"),
    @NamedQuery(name = "Coches.findByModelo", query = "SELECT c FROM Coches c WHERE c.modelo = :modelo")})
public class Coches implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "matricula")
    private String matricula;
    @Basic(optional = false)
    @Column(name = "marca")
    private String marca;
    @Basic(optional = false)
    @Column(name = "modelo")
    private String modelo;
    @JoinColumn(name = "codcli", referencedColumnName = "codcli")
    @ManyToOne(optional = false)
    private Clientes codcli;
    @OneToMany( mappedBy = "coches")
    private List<Reparaciones> reparacionesList;

    public Coches() {
    }

    public Coches(String matricula) {
        this.matricula = matricula;
    }

    public Coches(String matricula, String marca, String modelo) {
        this.matricula = matricula;
        this.marca = marca;
        this.modelo = modelo;
    }
    public Coches(String matricula, String marca, String modelo, Clientes codcli) {
        this.matricula = matricula;
        this.marca = marca;
        this.modelo = modelo;
        this.codcli=codcli;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Clientes getCodcli() {
        return codcli;
    }

    public void setCodcli(Clientes codcli) {
        this.codcli = codcli;
    }

    public List<Reparaciones> getReparacionesList() {
        return reparacionesList;
    }

    public void setReparacionesList(List<Reparaciones> reparacionesList) {
        this.reparacionesList = reparacionesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (matricula != null ? matricula.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Coches)) {
            return false;
        }
        Coches other = (Coches) object;
        if ((this.matricula == null && other.matricula != null) || (this.matricula != null && !this.matricula.equals(other.matricula))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.vo.Coches[ matricula=" + matricula + " ]";
    }
    
}
