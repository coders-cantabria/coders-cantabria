/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.template.negocio;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import org.apache.log4j.Logger;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;
import com.template.dto.Apellidos;

/**
 *
 * @author rmpinedo
 */
public class Apellidos_Mapper implements FieldSetMapper<Apellidos> {

    private static Logger log = Logger.getLogger(Apellidos_Mapper.class);

    /**
     * Funcion para convertir cada linea en un objeto de tipo Apellidos
     *
     * @param fs contiene los campos de una linea
     * @return Devuelve el objeto
     * @throws BindException
     */
    public Apellidos mapFieldSet(FieldSet fs) throws BindException {

        Apellidos bean = new Apellidos();

        try {

            if (fs.getFieldCount() == 5) {

                if (fs != null) {

                    if (fs.readString("ID") != null && !fs.readString("ID").equals("")) {
                        bean.setId(Long.parseLong(fs.readString("ID")));
                    } else {
                        bean.setId(new Long(0));
                    }
                    bean.setCodigo(fs.readString("CODIGO"));
                    bean.setNombre(fs.readString("NOMBRE"));
                }

            } else {
                log.warn("No se ha insertado la linea por tener " + fs.getFieldCount() + " campos");
            }

        } catch (Exception e) {
            log.error("Error en el mapeo de la linea ");
        }

        return bean;
    }

}
