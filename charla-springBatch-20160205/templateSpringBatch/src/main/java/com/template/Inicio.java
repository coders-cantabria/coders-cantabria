/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.template;

import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

/**
 *
 * @author rmpinedo
 */
public class Inicio {

    static Logger log = Logger.getLogger(Inicio.class);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        long start = System.currentTimeMillis();
        log.info("---- Inicio proceso " + Commons.VERSION + " ----");
        try {
            ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:META-INF/applicationContext.xml", "classpath:META-INF/applicationDataSources.xml");
            ctx.start();
            try {

                LogFactory.getFactory().getInstance(Inicio.class).info(Commons.VERSION);

            } catch (Exception e) {
                e.printStackTrace();
            }
            JobLauncher jobLauncher = (JobLauncher) ctx.getBean("jobLauncher");

            Job job = (Job) ctx.getBean("cargaTemplate");

            JobParametersBuilder builder = new JobParametersBuilder();
            JobExecution jobExecution = jobLauncher.run(job, builder.toJobParameters());

        } catch (Exception e) {
            log.error("---- Error proceso " + Commons.VERSION + " ----\n" + e.toString());
            Commons.setLevelError(2);
        }

        log.info("---- Fin proceso " + Commons.VERSION + " ----");
        long end = System.currentTimeMillis();
        log.info("- Ejecutado en " + Commons.getTimeDifference(start, end));

        if (Commons.levelError == 0) {
            log.info("---- ULTIMALINEAPROCESO CargadorTemplate BIEN");
        } else {
            log.info("---- ULTIMALINEAPROCESO CargadorTemplate MAL");
        }
        System.exit(Commons.levelError);

    }
}
