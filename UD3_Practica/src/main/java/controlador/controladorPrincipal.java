/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import controlador.factory.HibernateUtil;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import modelo.dao.BonificacionesDAO;
import modelo.dao.ClienteDAO;
import modelo.dao.CocheDAO;
import modelo.dao.EmpleadoDAO;
import modelo.dao.ReparacionDAO;
import modelo.vo.Bonificaciones;
import modelo.vo.Clientes;
import modelo.vo.Coches;
import modelo.vo.Empleados;
import modelo.vo.Reparaciones;
import modelo.vo.ReparacionesPK;
import org.hibernate.NonUniqueObjectException;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import vista.Principal;

/**
 *
 * @author acceso a datos
 */
public class controladorPrincipal {

    public static Session session;
//declara los objetos DAO
    public static ClienteDAO cliente;
    public static CocheDAO coche;
    public static ReparacionDAO reparacion;
    public static EmpleadoDAO empleado;

    public static BonificacionesDAO bonificacion;

    public static Principal ventana = new Principal();

    public static String[] cabeceraCoches = {"Matricula", "Modelo", "Marca", "nombre cliente"};
    public static DefaultTableModel modeloTablaCocche = new DefaultTableModel(cabeceraCoches, 0);

    public static String[] cabeceraEmpleados = {"Nombre", "Salario"};
    public static DefaultTableModel modeloTablaEmpleado = new DefaultTableModel(cabeceraEmpleados, 0);

    public static String[] cabeceraFacturacion = {"Nombre", "Facturado", "Bonificado", " % de facturacion"};
    public static DefaultTableModel modeloTablaFacturacion = new DefaultTableModel(cabeceraFacturacion, 0);

    public static String[] cabeceraReparaciones = {"Matricula", "Modelo", "Marca", "Gasto", "Nº Reparaciones"};
    public static DefaultTableModel modeloTablaReparaciones = new DefaultTableModel(cabeceraReparaciones, 0);

    public static DefaultComboBoxModel modelocomboEmpleados = new DefaultComboBoxModel();

    public static void iniciar() {
        ventana.setVisible(true);
        ventana.setLocationRelativeTo(null);

        ventana.getTablaCoches().setModel(modeloTablaCocche);
        ventana.getTablaEmpleados().setModel(modeloTablaEmpleado);
        ventana.getTablaFacturacion().setModel(modeloTablaFacturacion);
        ventana.getTablaReparacionesCoche().setModel(modeloTablaReparaciones);

        ventana.getCbEmpleados().setModel(modelocomboEmpleados);

    }

    public static void iniciaSession() {
        session = HibernateUtil.getSessionFactory().openSession();
        //crear los objetos DAO     
        cliente = HibernateUtil.getClienteDAO();
        coche = HibernateUtil.getCocheDAO();
        reparacion = HibernateUtil.getReparacionDAO();
        empleado = HibernateUtil.getEmpleadoDAO();
        bonificacion = HibernateUtil.getBonificacionesDAO();
    }

    public static void cerrarSession() {
        session.close();
    }

    public static void insertarCliente() {

        comprobarCamposCliente();
        try {

            HibernateUtil.beginTx(session);

            cliente.insertar(session, ventana.getTxtCodCli().getText(),
                    ventana.getTxtNomCli().getText(),
                    ventana.getTxtDireccion().getText(),
                    ventana.getTxtEmail().getText(),
                    ventana.getTxtTfno().getText());
            HibernateUtil.commitTx(session);
            JOptionPane.showMessageDialog(null, "cliente insertado");

        } catch (NoResultException nre) {
            JOptionPane.showMessageDialog(null, "No existe el cliente");
            HibernateUtil.rollbackTx(session);
            return;

        } catch (PersistenceException nuoe) {
            JOptionPane.showMessageDialog(null, "ya existe un cliente con ese codigo");
            HibernateUtil.rollbackTx(session);
            return;

        } catch (Exception ex) {
            Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            HibernateUtil.rollbackTx(session);
            return;
        }

    }

    public static void comprobarCliente() {
        try {
            Clientes cli;
            HibernateUtil.beginTx(session);
            cli = cliente.comprobar(session, ventana.getTxtNomCli().getText());
            System.out.println("existe");

            ventana.getTxtCodCli().setText(String.valueOf(cli.getCodcli()));
            ventana.getTxtNomCli().setText(cli.getNomcli());
            ventana.getTxtDireccion().setText(cli.getDireccion());
            ventana.getTxtEmail().setText(cli.getEmail());
            ventana.getTxtTfno().setText(cli.getTfno());

            ventana.getBtnAltaCli().setEnabled(false);
            ventana.getBtnBajaCli().setEnabled(true);
            ventana.getBtnModifCli().setEnabled(true);
            ventana.getTxtCodCli().setEnabled(false);
            HibernateUtil.commitTx(session);
        } catch (NoResultException nre) {
            System.out.println("no existe");
            ventana.getBtnAltaCli().setEnabled(true);
            ventana.getBtnBajaCli().setEnabled(false);
            ventana.getBtnModifCli().setEnabled(false);
            ventana.getTxtCodCli().setEnabled(true);
            return;

        } catch (Exception ex) {
            Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            HibernateUtil.rollbackTx(session);
            return;
        }
    }

    public static void modificarCliente() {
        comprobarCamposCliente();

        try {

            Clientes cli = cliente.comprobar(session, ventana.getTxtNomCli().getText());
            HibernateUtil.beginTx(session);
            cliente.modificar(session, cli,
                    ventana.getTxtDireccion().getText(),
                    ventana.getTxtEmail().getText(),
                    ventana.getTxtTfno().getText());

            HibernateUtil.commitTx(session);
        } catch (Exception ex) {
            Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            HibernateUtil.rollbackTx(session);
            return;
        }

    }

    public static void bajaCliente() {
        try {
            HibernateUtil.beginTx(session);
            Clientes cli = cliente.comprobar(session, ventana.getTxtNomCli().getText());

            long a;

            List<Coches> listaCoches = new ArrayList();
            List<Reparaciones> listaReparaciones = new ArrayList();

            // 
            if (cli != null) {
                if ((a = (reparacion.comprobarCliente(session, cli))) == 0) {

                    listaCoches = cli.getCochesList();

                    for (Coches co : listaCoches) {

                        listaReparaciones = co.getReparacionesList();

                        for (Reparaciones rep : listaReparaciones) {

                            reparacion.borrar(session, rep);

                        }

                        coche.borrar(session, co);

                    }

                    cliente.borrar(session, cli);
                } else {
                    JOptionPane.showMessageDialog(null, "no se puede borrar, tiene " + a + " reparaciones activas");
                    return;
                }

            } else {
                JOptionPane.showMessageDialog(null, "no existe el cliente");
                return;
            }

            HibernateUtil.commitTx(session);

        } catch (Exception ex) {
            Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            HibernateUtil.rollbackTx(session);
            return;
        }
    }

    public static void comprobarCamposCliente() {
        if (ventana.getTxtCodCli().getText().isBlank() || ventana.getTxtDireccion().getText().isBlank()
                || ventana.getTxtEmail().getText().isBlank() || ventana.getTxtNomCli().getText().isBlank()
                || ventana.getTxtTfno().getText().isBlank()) {
            JOptionPane.showMessageDialog(null, "Faltan datos cliente");
            return;
        }
    }

    public static void mostrarCoches() {

        try {
            List<Coches> listaCoches = new ArrayList();
            HibernateUtil.beginTx(session);

            listaCoches = coche.getCoches(session);
            modeloTablaCocche.setRowCount(0);
            for (Coches co : listaCoches) {
                Object[] fila = {co.getMatricula(), co.getModelo(), co.getMarca(), co.getCodcli().getNomcli()};
                modeloTablaCocche.addRow(fila);
            }

            ventana.getTablaCoches().setModel(modeloTablaCocche);

            TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modeloTablaCocche);
            ventana.getTablaCoches().setRowSorter(sorter);

            HibernateUtil.commitTx(session);
        } catch (Exception ex) {
            Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            HibernateUtil.rollbackTx(session);
            return;
        }

    }

    public static void altaCoche() {

        if (ventana.getTxtMatricula().getText().isBlank() || ventana.getTxtModelo().getText().isBlank()
                || ventana.getTxtMarca().getText().isBlank() || ventana.getTxtPropietario().getText().isBlank()) {
            JOptionPane.showMessageDialog(null, "Faltan datos coche");
            return;
        }
        try {
            Coches co;

            HibernateUtil.beginTx(session);

            co = new Coches(ventana.getTxtMatricula().getText(),
                    ventana.getTxtMarca().getText(),
                    ventana.getTxtModelo().getText(),
                    cliente.comprobar(session, ventana.getTxtPropietario().getText()));

            coche.insertar(session, co);

            JOptionPane.showMessageDialog(null, "coche insertado");
            HibernateUtil.commitTx(session);
        } catch (NoResultException nre) {

            JOptionPane.showMessageDialog(null, "el cliente no existe");
            HibernateUtil.rollbackTx(session);
            return;

        } catch (PersistenceException nuoe) {
            JOptionPane.showMessageDialog(null, "ya existe un coche con esa matricula");
            HibernateUtil.rollbackTx(session);
            return;
        } catch (Exception ex) {
            Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            HibernateUtil.rollbackTx(session);
            return;
        }

    }

    public static void bajaCoche() {

    }

    public static void comprobarCoche() {

        try {
            Coches co;
            HibernateUtil.beginTx(session);

            co = coche.getCoche(session, ventana.getTxtMatricula().getText());

            ventana.getBtnInsertCoche().setEnabled(false);
            ventana.getBtnBajaCoche().setEnabled(true);

            HibernateUtil.commitTx(session);

        } catch (NoResultException nre) {
            System.out.println("no existe");

            ventana.getBtnInsertCoche().setEnabled(true);
            ventana.getBtnBajaCoche().setEnabled(false);

            return;

        } catch (Exception ex) {

            Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            HibernateUtil.rollbackTx(session);
            return;
        }

    }

    public static void entradaReparacion() {
        try {
            ReparacionesPK pkReparacion;
            Reparaciones repara;
            Empleados emp = null;

            if (ventana.getTxtRepMatricula().getText().isBlank()
                    || ventana.getTxtRepFechaEnt().getText().isBlank()
                    || ventana.getTxtImporte().getText().isBlank()) {

                JOptionPane.showMessageDialog(null, "Faltan datos para crear Reparacion");
                return;
            }

            HibernateUtil.beginTx(session);

            emp = (Empleados) ventana.getCbEmpleados().getSelectedItem();

            if (reparacion.comprobarNumeroReparaciones(session, emp) >= 3) {

                JOptionPane.showMessageDialog(null, "el empleado ya ha alcanzado el numero de reparaciones activas maxima");
                return;
            }

            DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime fechaEntrada = LocalDateTime.parse(ventana.getTxtRepFechaEnt().getText(), formateador);

            pkReparacion = new ReparacionesPK(emp.getCodemp(), ventana.getTxtRepMatricula().getText(),
                    Date.from(fechaEntrada.toInstant(ZoneOffset.UTC)));

            if (ventana.getTxtRepFechaSal().getText().isBlank()) {

                repara = new Reparaciones(pkReparacion, Double.parseDouble(ventana.getTxtImporte().getText()), null);

                reparacion.insertar(session, repara);

                JOptionPane.showMessageDialog(null, "insertada Reparacion correctamente");

            } else {

                LocalDateTime fechaSalida = LocalDateTime.parse(ventana.getTxtRepFechaSal().getText(), formateador);

                if (fechaSalida.isAfter(fechaEntrada)) {

                    repara = new Reparaciones(pkReparacion, Double.parseDouble(ventana.getTxtImporte().getText()),
                            Date.from(fechaSalida.toInstant(ZoneOffset.UTC)));

                    reparacion.insertar(session, repara);

                    actualizarBonificaciones(repara);

                    JOptionPane.showMessageDialog(null, "insertada Reparacion correctamente");
                } else {
                    JOptionPane.showMessageDialog(null, "la fecha de salida no puede ser anterior a la fecha de entrada");
                    return;
                }
            }

            HibernateUtil.commitTx(session);

        } catch (DateTimeParseException dtpe) {

            JOptionPane.showMessageDialog(null, "error en la fecha");
            return;

        } catch (NumberFormatException nfe) {

            JOptionPane.showMessageDialog(null, "error en el importe");
            return;
        } catch (NonUniqueObjectException nuoe) {

            JOptionPane.showMessageDialog(null, "El coche ya tuvo una reparacion en esa fecha y hora");

            HibernateUtil.rollbackTx(session);
            return;

        } catch (ConstraintViolationException p) {

            JOptionPane.showMessageDialog(null, "El coche ya tuvo una reparacion en esa fecha y hora");

            HibernateUtil.rollbackTx(session);
            return;

        } catch (Exception ex) {

            Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            HibernateUtil.rollbackTx(session);
            return;

        }

    }

    public static void cargarTablaBonificaciones() {

        try {
            List<Bonificaciones> listaBonificaciones = new ArrayList();
            HibernateUtil.beginTx(session);

            listaBonificaciones = bonificacion.getBonificacion(session, String.valueOf(ventana.getCbMesEmpleados().getSelectedIndex() + 1));
            modeloTablaEmpleado.setRowCount(0);
            for (Bonificaciones bon : listaBonificaciones) {
                Object[] fila = {bon.getEmpleados().getNomemp(), bon.getEmpleados().getSalario() + bon.getImportebonificado()};
                modeloTablaEmpleado.addRow(fila);
            }

            ventana.getTablaEmpleados().setModel(modeloTablaEmpleado);

            HibernateUtil.commitTx(session);
        } catch (Exception ex) {
            Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            HibernateUtil.rollbackTx(session);
            return;
        }

    }

  public static void comprobarReparacion() {
    Reparaciones rep = null;
    Reparaciones rep2 = null;
    Coches co = null;
System.out.println("✅ MÉTODO comprobarReparacion() HA SIDO LLAMADO");
    if (ventana.getTxtRepFechaEnt().getText().isBlank()) {
        JOptionPane.showMessageDialog(null, "Fecha vacía");
        
        return;
    }

    try {
        HibernateUtil.beginTx(session);

        // Verificar si el coche existe
       try {
    co = coche.getCoche(session, ventana.getTxtRepMatricula().getText());
    if (co == null) {
        JOptionPane.showMessageDialog(null, "No existe el coche");
        return;
    }
} catch (NoResultException nre) {
    JOptionPane.showMessageDialog(null, "Error buscando el coche.");
    return;
}

        // Convertir la fecha de texto a Date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date fechaEntrada = sdf.parse(ventana.getTxtRepFechaEnt().getText());

        // Verificar si ya existe una reparación en esa fecha
        if ((rep2 = reparacion.getReparacionIgual(session, ventana.getTxtRepMatricula().getText(), fechaEntrada)) != null) {

            if (rep2.getFechaf() == null) {
                JOptionPane.showMessageDialog(null, "El coche ya tiene una reparación activa");

                // Mostrar la fecha en el JTextField con el formato correcto
                ventana.getTxtImporte().setText(String.valueOf(rep2.getImporte()));
                ventana.getTxtRepFechaEnt().setText(sdf.format(rep2.getReparacionesPK().getFechai()));

                modelocomboEmpleados.setSelectedItem(rep2.getEmpleados());

                ventana.getBtnEntrada().setEnabled(false);
                ventana.getTxtRepFechaSal().setEnabled(true);
                ventana.getTxtRepFechaEnt().setEnabled(false);
                ventana.getCbEmpleados().setEnabled(false);
                ventana.getBtnSalida().setEnabled(true);

                HibernateUtil.commitTx(session);
                return;

            } else {
                JOptionPane.showMessageDialog(null, "El coche ya tuvo una reparación en esa fecha y hora");
                ventana.getBtnEntrada().setEnabled(false);
                HibernateUtil.commitTx(session);
                return;
            }
        }else{
             System.out.println("No se encontró una reparación con la misma fecha.");
        }

        // Verificar si hay una reparación activa
        if ((rep = reparacion.getReparacion(session, ventana.getTxtRepMatricula().getText())) == null) {
            System.out.println("El coche esta disponible para reparar");

            ventana.getBtnEntrada().setEnabled(true);
            ventana.getTxtRepFechaSal().setEnabled(true);
            ventana.getTxtRepFechaEnt().setEnabled(true);
            ventana.getCbEmpleados().setEnabled(true);
            ventana.getBtnSalida().setEnabled(false);

            HibernateUtil.commitTx(session);
            return;
        }

        // Si ya tiene una reparación activa
        if (rep.getFechaf() == null) {
            JOptionPane.showMessageDialog(null, "El coche ya tiene una reparación activa");

            // Mostrar la fecha en el JTextField con el formato correcto
            ventana.getTxtImporte().setText(String.valueOf(rep.getImporte()));
            ventana.getTxtRepFechaEnt().setText(sdf.format(rep.getReparacionesPK().getFechai()));

            modelocomboEmpleados.setSelectedItem(rep.getEmpleados());

            ventana.getBtnEntrada().setEnabled(false);
            ventana.getTxtRepFechaSal().setEnabled(true);
            ventana.getTxtRepFechaEnt().setEnabled(false);
            ventana.getCbEmpleados().setEnabled(false);
            ventana.getBtnSalida().setEnabled(true);

            HibernateUtil.commitTx(session);
            return;
        }

        System.out.println("El coche está disponible para reparar");

        ventana.getBtnEntrada().setEnabled(true);
        ventana.getTxtRepFechaSal().setEnabled(true);
        ventana.getTxtRepFechaEnt().setEnabled(true);
        ventana.getCbEmpleados().setEnabled(true);
        ventana.getBtnSalida().setEnabled(false);
        HibernateUtil.commitTx(session);

    } catch (ParseException e) {
        JOptionPane.showMessageDialog(null, "Formato de fecha inválido. yyyy-MM-dd HH:mm:ss");
        HibernateUtil.rollbackTx(session);
    } catch (NonUniqueObjectException nuoe) {
        JOptionPane.showMessageDialog(null, "El coche ya tuvo una reparación en esa fecha y hora");
        HibernateUtil.rollbackTx(session);
    } catch (Exception ex) {
        Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        HibernateUtil.rollbackTx(session);
    }
}

    public static void llenarComboEmpleados() {

        modelocomboEmpleados.removeAllElements();

        HibernateUtil.beginTx(session);

        try {

            empleado.cargarCombo(session, modelocomboEmpleados);

            HibernateUtil.commitTx(session);

        } catch (Exception ex) {

            Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            HibernateUtil.rollbackTx(session);
            return;

        }
    }

    public static void salidaReparacion() {

        Reparaciones rep = null;

        try {

            if (ventana.getTxtImporte().getText().isBlank()
                    || ventana.getTxtRepFechaSal().getText().isBlank()) {

                JOptionPane.showMessageDialog(null, "Faltan datos ");
                return;

            }

            HibernateUtil.beginTx(session);

            rep = reparacion.getReparacion(session, ventana.getTxtRepMatricula().getText());

            reparacion.salida(session, rep, ventana.getTxtImporte().getText(), ventana.getTxtRepFechaSal().getText());

            HibernateUtil.commitTx(session);

            ventana.getBtnSalida().setEnabled(false);

            actualizarBonificaciones(rep);

        } catch (DateTimeParseException dtpe) {

            JOptionPane.showMessageDialog(null, "error en la fecha");
            return;

        } catch (NumberFormatException nfe) {

            JOptionPane.showMessageDialog(null, "error en el importe");
            return;

        } catch (Exception ex) {
            Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            HibernateUtil.rollbackTx(session);
            return;
        }

    }

    public static void actualizarBonificaciones(Reparaciones rep) {

//        
        try {
            HibernateUtil.beginTx(session);

            Double factMes = reparacion.comprobarFacturado(session, rep);

            bonificacion.insertar(session, rep.getReparacionesPK().getCodemp(),
                    rep.getFechaf(), rep.getImporte(), factMes);
            HibernateUtil.commitTx(session);
        } catch (Exception ex) {
            Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            HibernateUtil.rollbackTx(session);

        }

    }

    public static void facturacion() {

        try {
            List<Object[]> filas = new ArrayList<>();

            List<Bonificaciones> listaBonificaciones = new ArrayList();

            HibernateUtil.beginTx(session);

            listaBonificaciones = bonificacion.getBonificacion(session, String.valueOf(ventana.getCbMesFacturacion().getSelectedIndex() + 1));

            List<Empleados> listaEmpleados = empleado.getTodos(session);

            modeloTablaFacturacion.setRowCount(0);

            for (Empleados rep : listaEmpleados) {

                double factMes = reparacion.comprobarFacturacionTabla(session, rep.getCodemp(),
                        String.valueOf(ventana.getCbMesFacturacion().getSelectedIndex() + 1));

                double porcentaje = reparacion.getPorgentaje(session, rep.getCodemp(),
                        String.valueOf(ventana.getCbMesFacturacion().getSelectedIndex() + 1));

                try {
                    Bonificaciones bon = listaBonificaciones.get(rep.getCodemp() - 1);

                    Object[] fila = {rep.getNomemp(), factMes, bon.getImportebonificado(), porcentaje + " %"};
                    filas.add(fila);
                } catch (IndexOutOfBoundsException io) {

                    Object[] fila = {rep.getNomemp(), factMes, 0, porcentaje + " %"};
                    filas.add(fila);

                }

            }

            // Ordenar filas por facturacion descendente
            for (int i = 0; i < filas.size() - 1; i++) {
                for (int j = i + 1; j < filas.size(); j++) {
                    if ((Double) filas.get(i)[1] < (Double) filas.get(j)[1]) {

                        Object[] temp = filas.get(i);
                        filas.set(i, filas.get(j));
                        filas.set(j, temp);
                    }
                }
            }

            for (Object[] fila : filas) {
                modeloTablaFacturacion.addRow(fila);
            }

            ventana.getTablaFacturacion().setModel(modeloTablaFacturacion);

            HibernateUtil.commitTx(session);
        } catch (Exception ex) {
            Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            HibernateUtil.rollbackTx(session);

        }

    }

    public static void cargarCochesReparaciones() {
        try {
            ArrayList<Coches> listaCoches = new ArrayList<>();

            double importeTotal = 0.0;
            long vecesReparado = 0;

            HibernateUtil.beginTx(session);

            listaCoches = coche.getCochesCliente(session, ventana.getTxtNombreTablaClientes().getText());
            ventana.getTxtNumCoches().setText(String.valueOf(listaCoches.size()));
            modeloTablaReparaciones.setRowCount(0);
            for (Coches coc : listaCoches) {

                importeTotal = reparacion.getReparacionCoche(session, coc.getMatricula());
                vecesReparado = reparacion.getNumReparacion(session, coc.getMatricula());
                Object[] fila = {coc.getMatricula(), coc.getModelo(), coc.getMarca(), importeTotal, vecesReparado};
                modeloTablaReparaciones.addRow(fila);

            }

            HibernateUtil.commitTx(session);

        } catch (Exception ex) {
            Logger.getLogger(controladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            HibernateUtil.rollbackTx(session);

        }

    }

}
