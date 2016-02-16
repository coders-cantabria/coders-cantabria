/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.template.negocio;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileReader;
import org.apache.commons.io.filefilter.WildcardFileFilter;

import org.apache.log4j.Logger;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import com.template.util.Ftp;
import com.template.util.ZipUtil;

/**
 *
 * @author rmpinedo
 */
public class TaskAccesoFTP implements Tasklet {

    static Logger log = Logger.getLogger(TaskAccesoFTP.class);

    @Value("${ipFTP}")
    private String ip;

    @Value("${userFTP}")
    private String user;

    @Value("${passFTP}")
    private String pass;

    @Value("${dirFTP}")
    private String dirFTP;

    @Value("${dirLocal}")
    private String dirLocal;

    @Value("${conexionSegura}")
    private String conexionSegura;

    @Override
    public RepeatStatus execute(StepContribution sc, ChunkContext cc) throws Exception {

        log.info("================== INICIO TAREA ACCESO Y DESCARGA FTP   ================== ");
        Ftp ftp = null;
        try {

            //Conexion al FTP
            ftp = new Ftp(ip, user, pass, conexionSegura);
            ftp.conecta();

            boolean estadoDescarga = false;
            //Descargo a mi carpeta local el fichero OK.txt

            estadoDescarga = ftp.descargarDirectorioFTP(dirFTP, dirLocal);

            //Si existe y se ha podido descargar, descargo a local los ficheros zip que contiene
            if (!estadoDescarga) {
                log.warn("El fichero  no se ha podido descargar");
            }

        } catch (Exception e) {
            log.error("El fichero  no se ha podido descargar." +  e.getCause().toString());
            throw e;
            
        } finally {
            //Desconecto del FTP
            if (ftp != null) {
                ftp.desconecta();
            }

        }

        log.info("================== FIN TAREA ACCESO Y DESCARGA FTP   ================== \n");

        return RepeatStatus.FINISHED;

    }
}
