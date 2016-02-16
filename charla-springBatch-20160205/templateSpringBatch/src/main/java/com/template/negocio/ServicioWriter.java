/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.template.negocio;

import com.template.dao.IApellidosDao;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import org.apache.log4j.Logger;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import com.template.dao.impl.ApellidosDaoImpl;
import com.template.dto.Apellidos;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 *
 * @author
 */
public class ServicioWriter implements ItemWriter<Apellidos> {

    private static Logger log = Logger.getLogger(ServicioWriter.class);

    @Autowired
    @Qualifier("IApellidosDao")
    private IApellidosDao apellidosDao;

    /**
     * Variable contador de lineas del fichero. Tiene scope singleton
     */
    private Integer contadorPuntosTraspasados;

    /**
     * Se vuelca el contenido del reader a BBDD
     *
     * @param list Se recibe una lista de Objetos. Se envian al Dao para insertar en BBDD
     * @throws Exception
     */
    public void write(List<? extends Apellidos> list) throws Exception {

        log.info("**** COMIENZO EL WRITER ****");

        try {

            if (contadorPuntosTraspasados == null) {
                contadorPuntosTraspasados = 0;
            }
            contadorPuntosTraspasados = contadorPuntosTraspasados + list.size();

            List<Apellidos> listaApellidos = new ArrayList<Apellidos>();

            if (list != null && !list.isEmpty()) {

                for (Apellidos apellido : list) {

                    if (apellido.getId() != null) {
                        listaApellidos.add(apellido);
                    }
                }

                //Se realiza una insercion de una lista por temas de rendimiento
                if (!listaApellidos.isEmpty()) {
                    log.info("Insertados " + listaApellidos.size() + " registros de " + contadorPuntosTraspasados);
                    insertarLista(listaApellidos);
                } else {
                    log.info("No hay apellidos a insertar");
                }

            }

            //Se limpia el objeto para la siguiente ejecucion. Tiene scope singleton
            listaApellidos.clear();
        } catch (Exception e) {
            log.error("Se ha producido un error insertando");
        }

    }

    /**
     * Insercion de un objeto Apellidos
     *
     * @param apellidos
     */
    private void insertarObjeto(Apellidos apellidos) {

        try {
            apellidosDao.insertarBeanApellidos(apellidos);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ServicioWriter.class.getName()).log(Level.SEVERE, null, ex);
            log.error("Se ha producido un error al insertar" + ex.getMessage());
        }

    }

    /**
     * Funcion que inserta una lista de objetos Apellidos en BBDD
     *
     * @param lista
     */
    private void insertarLista(List<Apellidos> lista) {

        try {
            HashMap hm = new HashMap();
            hm.put("listaApellidos", lista);
            apellidosDao.insertarListaApellidos(hm);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ServicioWriter.class.getName()).log(Level.SEVERE, null, ex);
            log.error("Se ha producido un error al insertar" + ex.getMessage());
        }

    }

}
