/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.template.dao;

import java.util.HashMap;
import com.template.dto.Apellidos;

/**
 *
 * @author rmpinedo
 */
public interface IApellidosDao {

    /**
     * Funcion para insertar un objeto Apellidos
     *
     * @param ap Objeto con los datos de Apellidos
     * @throws Exception
     */
    public void insertarBeanApellidos(Apellidos ap) throws Exception;

    /**
     * Funcion para insertar una lista de objetos  Apellidos
     *
     * @param hm. HashMap con la lista de objetos
     * @throws Exception
     */
    public void insertarListaApellidos(HashMap hm) throws Exception;

    /**
     * Funcion para borrar la tabla Apellidos al inicio del proceso
     *
     * @return numero de registros borrados
     * @throws Exception
     */
    public int deleteBBDD() throws Exception;

}
