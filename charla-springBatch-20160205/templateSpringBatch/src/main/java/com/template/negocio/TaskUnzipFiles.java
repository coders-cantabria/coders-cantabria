/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.template.negocio;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import org.apache.commons.io.filefilter.WildcardFileFilter;

import org.apache.log4j.Logger;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import com.template.util.ZipUtil;

/**
 *
 * @author rmpinedo
 */
/**
 * Step que realiza la descompresion del fichero en la carpeta local
 *
 * @author rmpinedo
 */
public class TaskUnzipFiles implements Tasklet {

    static Logger log = Logger.getLogger(TaskUnzipFiles.class);

    @Value("${dirLocal}")
    private String dirLocal;

    @Override
    public RepeatStatus execute(StepContribution sc, ChunkContext cc) throws Exception {

        log.info("================== INICIO TAREA UNZIPFILES   ================== ");
        try {

            File dir = new File(dirLocal);

            String fileName = "*.zip";

            FileFilter fileFilter = new WildcardFileFilter(fileName);
            File[] listaFicheros = dir.listFiles(fileFilter);

            if (listaFicheros == null || listaFicheros.length == 0) {
                log.warn("No encontrado fichero para descomprimir");
            } else {

                for (File ficheroZip : listaFicheros) {

                    FileInputStream fis = new FileInputStream(ficheroZip);
                    log.info("Descomprimiendo fichero zip " + ficheroZip.getName());
                    String dirTxt = ZipUtil.descompressFiles(fis, dirLocal);
                }
            }

        } catch (Exception e) {
            log.error(e.getMessage());
        }

        log.info("==================   FIN TAREA UNZIP_FILES  ==================\n");
        return RepeatStatus.FINISHED;

    }
}
