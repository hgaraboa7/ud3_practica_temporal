/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import modelo.vo.Bonificaciones;
import modelo.vo.BonificacionesPK;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author hector.garaboacasas
 */
public class BonificacionesDAO {

    public List<Bonificaciones> getBonificacion(Session session, String mes) {

        Query q = session.createNamedQuery("Bonificaciones.findByMes");
        q.setParameter("mes", mes);
        return (ArrayList<Bonificaciones>) q.list();

    }

    public void insertar(Session session, int codemp, Date fechaf, Double num, Double factMes) {

        String mes = String.valueOf(fechaf.getMonth() + 1);
        
  Query q = session.createQuery(
            "SELECT b FROM Bonificaciones b WHERE b.bonificacionesPK.codemp = :codemp AND b.bonificacionesPK.mes = :mes" );
    q.setParameter("codemp", codemp);
    q.setParameter("mes", mes);

    Bonificaciones bon = (Bonificaciones)q.uniqueResult();
        if (bon == null) {
       
            if (factMes >= 1000.00) {
            bon = new Bonificaciones(codemp, mes, factMes * 0.05);
            session.save(bon);
        }
        
            } else  {
            double nuevoImporte = bon.getImportebonificado() + (num * 0.05);
        bon.setImportebonificado(nuevoImporte);
        session.update(bon);
            }
        
    }

    public double bonificacionAntigua(Session session, Date fechaf, int codemp) {

        Query q = session.createQuery("Select b.importebonificado From Bonificaciones b WHERE b.bonificacionesPK.codemp = :codemp "
                + "AND b.bonificacionesPK.mes = MONTH(:fechaf)");

        q.setParameter("codemp", codemp);
        q.setParameter("fechaf", fechaf);

        return (Double) q.uniqueResult();

    }

}
