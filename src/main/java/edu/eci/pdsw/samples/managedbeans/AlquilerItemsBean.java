/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.samples.managedbeans;

import edu.eci.pdsw.samples.entities.ItemRentado;
import edu.eci.pdsw.samples.services.ExcepcionServiciosAlquiler;
import edu.eci.pdsw.samples.services.ServiciosAlquiler;
import java.io.Serializable;
import java.util.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author 2106913
 */
@ManagedBean(name = "AlquilerItems")
@SessionScoped
public class AlquilerItemsBean implements Serializable {

    ServiciosAlquiler sp = ServiciosAlquiler.getInstance();
    private ArrayList<String[]> listaPendientes;
    private int id_pelicula;
    private int dias;
    
    @ManagedProperty(value = "#{ClientesBen}")
    private ClientesBean cb;

    public AlquilerItemsBean() {
    }

    public void guardarAlquiler() throws ExcepcionServiciosAlquiler{
        sp.registrarAlquilerCliente((java.sql.Date) Calendar.getInstance().getTime(), cb.getDocumento(), sp.consultarItem(id_pelicula), dias);
        System.out.println("se guarda");
    }
    
    public ArrayList<String[]> getListaPendientes() throws ExcepcionServiciosAlquiler {
        ItemRentado itemTMP=null;
        LocalDate fechaInicial=null;
        LocalDate fechaEntrega=null;
        long diasRestantes=0;
        List<ItemRentado> ItemsRentados= sp.consultarItemsCliente(cb.getClienteSeleccionado().getDocumento());
        
        listaPendientes=new ArrayList<String[]>();
        for(int i=0;i<ItemsRentados.size();i++){
            itemTMP=ItemsRentados.get(i);
            
            fechaInicial=itemTMP.getFechainiciorenta().toLocalDate();
            fechaEntrega=itemTMP.getFechafinrenta().toLocalDate();
            diasRestantes=ChronoUnit.DAYS.between(fechaInicial, fechaEntrega);
            
            String[] tmp={itemTMP.getItem().getNombre(),Integer.toString((int) diasRestantes),Integer.toString((int) sp.consultarMultaAlquiler(itemTMP.getItem().getId(),(java.sql.Date) Calendar.getInstance().getTime()))};
            listaPendientes.add(tmp);
        }
        return listaPendientes;
    }

    public ClientesBean getCb() {
        return cb;
    }

    public void setCb(ClientesBean cb) {
        this.cb = cb;
    }

    public int getId_pelicula() {
        return id_pelicula;
    }

    public void setId_pelicula(int id_pelicula) {
        this.id_pelicula = id_pelicula;
    }
    
    public String getNombreCli(){
        return cb.getClienteSeleccionado().getNombre();
    }

    public int getDias() {
        return dias;
    }

    public void setDias(int dias) {
        this.dias = dias;
    }
}