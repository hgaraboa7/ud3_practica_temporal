/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.dao;

import modelo.vo.Clientes;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author hecto
 */
public class ClienteDAO {

    public void insertar(Session session, String codcli, String nomcli, String direccion, String email, String tfno) {
    
        Clientes cli=new Clientes(Integer.valueOf(codcli),nomcli,direccion,email,tfno);
        session.save(cli);
        
    }

    public Clientes comprobar(Session session, String nomCli) {
    
        Query q=session.createNamedQuery("Clientes.findByNomcli");
        q.setParameter("nomcli", nomCli);
        Clientes cli=(Clientes)q.getSingleResult();
        return cli;
    }

    public void modificar(Session session, Clientes cli, String dir, String mail, String tfno) {
 
        cli.setDireccion(dir);
        cli.setEmail(mail);
        cli.setTfno(tfno);
        
      session.update(cli);
        
        
    }

    public void borrar(Session session, Clientes cli) {
  
    session.delete(cli);
    
    }
    
    
    
}
