/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.dao;

import java.util.ArrayList;
import java.util.List;
import modelo.vo.Coches;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author hecto
 */
public class CocheDAO {

    public ArrayList<Coches> getCoches(Session session) {
  
        Query q=session.getNamedQuery("Coches.findAll");
        
       
        return (ArrayList<Coches>) q.list();
    
    }

    public void insertar(Session session, Coches co) {
    
        session.save(co);
    
    }

    public Coches getCoche(Session session, String matricula) {
  
        Query q=session.getNamedQuery("Coches.findByMatricula");
        q.setParameter("matricula", matricula);
        
        return (Coches)q.getSingleResult();
        
        
    }

    public ArrayList<Coches> getCochesCliente(Session session, String nomcli) {
   
    Query q=session.createQuery("SELECT c FROM Coches c WHERE c.codcli.nomcli = :nomcli");
     q.setParameter("nomcli", nomcli);
     
     return (ArrayList)q.list();
     
    
    }

    public void borrar(Session session, Coches coches) {
  
        session.delete(coches);
    
    }
    
}
