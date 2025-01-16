/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.dao;

import controlador.controladorPrincipal;
import static controlador.controladorPrincipal.ventana;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextField;
import modelo.vo.Clientes;
import modelo.vo.Empleados;
import modelo.vo.Reparaciones;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author hecto
 */
public class ReparacionDAO {

    public void insertar(Session session, Reparaciones repara) {

        session.save(repara);
        System.out.println("insertada reparacion");

    }

    public Reparaciones getReparacion(Session session, String matricula) {
        Reparaciones rep = null;

        Query q = session.createQuery("Select r From Reparaciones r WHERE r.reparacionesPK.matricula = :matricula AND r.fechaf is null");
        q.setParameter("matricula", matricula);
        rep = (Reparaciones) q.uniqueResult();

        
        return rep;

//        Reparaciones rep=null;
//        
//        Query q=session.createNamedQuery("Reparaciones.findByMatricula");
//        q.setParameter("matricula", matricula);
//        
//        List<Reparaciones> reparaciones=q.list();
//        
//         
//        for (Reparaciones r : reparaciones) {
//            if (r.getFechaf()==null){
//               return rep=r; 
//            }
//        }
//         return rep;
//        
    }

    public long comprobarNumeroReparaciones(Session session, Empleados emp) {

        Query q = session.createQuery("Select count(*) From Reparaciones r WHERE r.reparacionesPK.codemp = :codemp AND r.fechaf is null");
        q.setParameter("codemp", emp.getCodemp());
        return (long) q.uniqueResult();

        /*
        Query q=session.createNamedQuery("Reparaciones.findByCodemp");
        q.setParameter("codemp", emp.getCodemp());
        
        List<Reparaciones> reparaciones=q.list();
        
         int numRep = 0;
        for (Reparaciones r : reparaciones) {
            if (r.getFechaf() == null) {
                numRep++;
            }
        }
         return numRep;
        
         */
    }

    public void salida(Session session, Reparaciones rep, String importe, String fechaf) {

        rep.setImporte(Double.valueOf(importe));

        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime fechaSalida = LocalDateTime.parse(fechaf, formateador);

        rep.setFechaf(Date.from(fechaSalida.toInstant(ZoneOffset.UTC)));

        session.update(rep);

    }

    public Double comprobarFacturado(Session session, Reparaciones rep) {

        Query q = session.createQuery("Select SUM(r.importe) From Reparaciones r WHERE r.reparacionesPK.codemp = :codemp "
                + "AND MONTH(r.fechaf) = MONTH(:fechaf)");

        q.setParameter("codemp", rep.getReparacionesPK().getCodemp());
        q.setParameter("fechaf", rep.getFechaf());

        return (Double) q.uniqueResult();

    }

    public Double comprobarFacturacionTabla(Session session, Integer codemp, String mes) {

        Query q = session.createQuery("Select SUM(r.importe) From Reparaciones r WHERE r.reparacionesPK.codemp = :codemp "
                + "AND MONTH(r.fechaf) = :fechaf");

        q.setParameter("codemp", codemp);
        q.setParameter("fechaf", mes);

        Double resultado = (Double) q.uniqueResult();

        if (resultado == null) {
            return 0.0;
        }

        return resultado;

    }

    public double getPorgentaje(Session session, Integer codemp, String mes) {

        Query q = session.createQuery("Select SUM(r.importe) From Reparaciones r WHERE r.reparacionesPK.codemp = :codemp "
                + "AND MONTH(r.fechaf) = :fechaf");

        q.setParameter("codemp", codemp);
        q.setParameter("fechaf", mes);

        Double facturacionEmpleado = (Double) q.uniqueResult();

        Query queryTotal = session.createQuery(
                "SELECT SUM(r.importe) FROM Reparaciones r WHERE MONTH(r.fechaf) = :fechaf"
        );
        queryTotal.setParameter("fechaf", mes);

        Double facturacionTotal = (Double) queryTotal.uniqueResult();

        if (facturacionTotal == null || facturacionTotal == 0) {
            return 0.0;
        }

        if (facturacionEmpleado == null) {
            facturacionEmpleado = 0.0;
        }

        return (facturacionEmpleado / facturacionTotal) * 100;

    }

    public List<Reparaciones> getReparacionMes(Session session, String mes) {

        Query q = session.createQuery(
                "SELECT r FROM Reparaciones r WHERE MONTH(r.fechaf) = :fechaf"
        );

        q.setParameter("fechaf", mes);

        return q.list();

    }

    public Double getReparacionCoche(Session session, String matricula) {

        Double rep = 0.0;

        Query q = session.createQuery("Select SUM(r.importe) From Reparaciones r WHERE r.reparacionesPK.matricula = :matricula AND r.fechaf is not null");
        q.setParameter("matricula", matricula);
        rep = (Double) q.uniqueResult();

        if (rep == null) {
            rep = 0.0;
        }

        return rep;

    }

    public long getNumReparacion(Session session, String matricula) {

        long rep = 0;

        Query q = session.createQuery("Select count(*) From Reparaciones r WHERE r.reparacionesPK.matricula = :matricula AND r.fechaf is not null");
        q.setParameter("matricula", matricula);
        rep = (long) q.uniqueResult();

        return rep;

    }

    public long comprobarCliente(Session session, Clientes cli) {
        
        Query q=session.createQuery("Select count(*) From Reparaciones r WHERE r.coches.codcli = :codcli AND r.fechaf is null");
        q.setParameter("codcli", cli.getCodcli());
        
        
        return (long)q.uniqueResult();
    }

     public void borrar(Session session, Reparaciones rep) {
    session.delete(rep);
            }

    public Reparaciones getReparacionIgual(Session session, String matricula, Date fechaEntrada) {
    Reparaciones rep = null;

    Query q = session.createQuery("Select r From Reparaciones r WHERE r.reparacionesPK.matricula = :matricula AND r.reparacionesPK.fechai =:fechaEntrada");
       
    q.setParameter("matricula", matricula);
    q.setParameter("fechaEntrada", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(fechaEntrada));

    rep = (Reparaciones) q.uniqueResult();

    return rep;
}

    
}
