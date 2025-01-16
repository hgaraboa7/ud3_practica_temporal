/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.dao;

import java.util.Iterator;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import modelo.vo.Empleados;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author hecto
 */
public class EmpleadoDAO {

    public Empleados getEmpleado(Session session, String nombre) {

        Query q = session.createNamedQuery("Empleados.findByNomemp");
        q.setParameter("nomemp", nombre);

        return (Empleados) q.getSingleResult();
    }

    public void cargarCombo(Session session, DefaultComboBoxModel modelocomboEmpleados) {

        Query q = session.createNamedQuery("Empleados.findAll");

        //iterar para obetener todos o varios
        List<Empleados> listaEmpleados = q.list();

        Iterator it = listaEmpleados.iterator();
        while (it.hasNext()) {
            modelocomboEmpleados.addElement(it.next());
        }

        System.out.println(modelocomboEmpleados.getSelectedItem());
        System.out.println(modelocomboEmpleados.getIndexOf(modelocomboEmpleados.getSelectedItem()));
        
    }

    public List<Empleados> getTodos(Session session) {
   
     Query q = session.createNamedQuery("Empleados.findAll");

       
        return q.list();
        
    
    
    }

}
