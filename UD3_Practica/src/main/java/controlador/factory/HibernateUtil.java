package controlador.factory;

import modelo.dao.BonificacionesDAO;
import modelo.dao.ClienteDAO;
import modelo.dao.CocheDAO;
import modelo.dao.EmpleadoDAO;
import modelo.dao.ReparacionDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static final SessionFactory sessionFactory;

    static {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();

        } catch (Throwable ex) {
            System.err.println("Session Factory could not be created." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * *************** PARA GESTIONAR LAS TRANSACCIONES **************
     */
    public static Transaction beginTx(Session s) {

        if (s.getTransaction() == null || !s.getTransaction().isActive()) {
            return s.beginTransaction();
        }
        return sessionFactory.getCurrentSession().getTransaction();
    }

    public static void commitTx(Session s) {
        if (s.getTransaction().isActive()) {
            s.getTransaction().commit();
        }
    }

    public static void rollbackTx(Session s) {
        if (s.getTransaction().isActive()) {
            s.getTransaction().rollback();
        }
    }

    /**
     * ********************** INCORPORA LOS MÉTODOS PARA CREAR LOS OBJETOS DAO *******
     */

    public static ClienteDAO getClienteDAO() {
        return new ClienteDAO();
    }

    public static CocheDAO getCocheDAO() {
        return new CocheDAO();
    }

    public static ReparacionDAO getReparacionDAO() {
        return new ReparacionDAO();
    }
    
    public static EmpleadoDAO getEmpleadoDAO() {
        return new EmpleadoDAO();
    }
    public static BonificacionesDAO getBonificacionesDAO() {
        return new BonificacionesDAO();
    }
}
