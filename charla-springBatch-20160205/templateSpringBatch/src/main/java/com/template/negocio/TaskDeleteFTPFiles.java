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
import java.util.List;
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
public class TaskDeleteFTPFiles implements Tasklet {

    static Logger log = Logger.getLogger(TaskDeleteFTPFiles.class);

    @Value("${ipFTP}")
    private String ip;

    @Value("${userFTP}")
    private String user;

    @Value("${passFTP}")
    private String pass;

    @Value("${dirFTP}")
    private String dirFTP;

    @Value("${conexionSegura}")
    private String conexionSegura;

    @Override
    public RepeatStatus execute(StepContribution sc, ChunkContext cc) throws Exception {

        log.info("================== INICIO TAREA BORRADO FTP FICHEROS   ================== ");
        Ftp ftp = null;
        try {

            log.info("Se realiza conexion al FTP");
            //Conexion al FTP

            //ftp = new Ftp(ip, user, pass, conexionSegura);
            //ftp.conecta();
            //Listo los ficheros y borro
            //List<String> listaFicheros = ftp.listarCarpeta(dirFTP);
            /*
            for (String fichero : listaFicheros) {
                //ftp.borrarFichero(dirFTP, fichero);
                log.info("Borrado fichero del FTP");
            }*/

        } catch (Exception e) {
            log.error("El fichero  no se ha podido borrar del FTP." + e.getCause().toString());
            throw e;

        } finally {
            //Desconecto del FTP
            if (ftp != null) {
                //ftp.desconecta();
                log.info("Se realiza desconexion del FTP");
            }

        }

        log.info("================== FIN TAREA BORRADO FTP FICHEROS  ================== \n");

        return RepeatStatus.FINISHED;

    }
}
