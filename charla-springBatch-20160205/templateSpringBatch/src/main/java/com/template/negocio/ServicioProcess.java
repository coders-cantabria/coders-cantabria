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
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 *
 * @author
 */
public class ServicioProcess implements ItemProcessor<Apellidos, Apellidos> {

    private static Logger log = Logger.getLogger(ServicioProcess.class);

    @Autowired
    @Qualifier("IApellidosDao")
    private IApellidosDao apellidosDao;

    @Override
    public Apellidos process(Apellidos ap_reader) throws Exception {
        log.info("**** COMIENZO EL PROCESS ****");

        Apellidos ap_writer = new Apellidos();
        //Tratamiento de cada apellido
        //Solo paso apellidos largos

        if (ap_reader != null) {
            String nombre = ap_reader.getNombre();
            if (nombre != null && !nombre.equals("")) {
                int tam = nombre.length();

                if (tam > 10) {
                    ap_writer = (Apellidos) ap_reader.clone();
                    log.info("Encontrado apellido largo. Se envia al writer");
                }
            }
        }

        return ap_writer;
    }

}
