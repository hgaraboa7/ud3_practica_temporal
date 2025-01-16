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
@Table(name = "clientes")
@NamedQueries({
    @NamedQuery(name = "Clientes.findAll", query = "SELECT c FROM Clientes c"),
    @NamedQuery(name = "Clientes.findByCodcli", query = "SELECT c FROM Clientes c WHERE c.codcli = :codcli"),
    @NamedQuery(name = "Clientes.findByNomcli", query = "SELECT c FROM Clientes c WHERE c.nomcli = :nomcli"),
    @NamedQuery(name = "Clientes.findByDireccion", query = "SELECT c FROM Clientes c WHERE c.direccion = :direccion"),
    @NamedQuery(name = "Clientes.findByEmail", query = "SELECT c FROM Clientes c WHERE c.email = :email"),
    @NamedQuery(name = "Clientes.findByTfno", query = "SELECT c FROM Clientes c WHERE c.tfno = :tfno")})
public class Clientes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "codcli")
    private Integer codcli;
    @Basic(optional = false)
    @Column(name = "nomcli")
    private String nomcli;
    @Basic(optional = false)
    @Column(name = "direccion")
    private String direccion;
    @Column(name = "email")
    private String email;
    @Column(name = "tfno")
    private String tfno;
    @OneToMany( mappedBy = "codcli")
    private List<Coches> cochesList;

    public Clientes() {
    }

    public Clientes(Integer codcli) {
        this.codcli = codcli;
    }

    public Clientes(Integer codcli, String nomcli, String direccion) {
        this.codcli = codcli;
        this.nomcli = nomcli;
        this.direccion = direccion;
    }
     public Clientes(Integer codcli, String nomcli, String direccion,String email,String tfno) {
        this.codcli = codcli;
        this.nomcli = nomcli;
        this.direccion = direccion;
        this.email=email;
        this.tfno=tfno;
        
    }

    public Integer getCodcli() {
        return codcli;
    }

    public void setCodcli(Integer codcli) {
        this.codcli = codcli;
    }

    public String getNomcli() {
        return nomcli;
    }

    public void setNomcli(String nomcli) {
        this.nomcli = nomcli;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTfno() {
        return tfno;
    }

    public void setTfno(String tfno) {
        this.tfno = tfno;
    }

    public List<Coches> getCochesList() {
        return cochesList;
    }

    public void setCochesList(List<Coches> cochesList) {
        this.cochesList = cochesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codcli != null ? codcli.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Clientes)) {
            return false;
        }
        Clientes other = (Clientes) object;
        if ((this.codcli == null && other.codcli != null) || (this.codcli != null && !this.codcli.equals(other.codcli))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.vo.Clientes[ codcli=" + codcli + " ]";
    }
    
}
