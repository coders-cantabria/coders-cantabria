/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.template.negocio;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author rmpinedo
 */

    /**
     * Clase para realizar varios reintentos sobre la conexion FTP
     * Se dirige la ejecucion de los steps con el objeto FlowExecutionStatus
     */

public class DeciderReintentosFTP implements JobExecutionDecider {

    
    private int reintentosFTP;

    protected Log log = LogFactory.getLog(DeciderReintentosFTP.class);

    public FlowExecutionStatus decide(JobExecution je, StepExecution se) {

     
        
        if (reintentosFTP <=3) {

            reintentosFTP = reintentosFTP + 1;
            return new FlowExecutionStatus("DECISOR_REINTENTOS");

        } else if (reintentosFTP == 3) {
            return new FlowExecutionStatus("DECISOR_FIN");

        } else {
            return new FlowExecutionStatus("DECISOR_FIN");
        }

    }

}
