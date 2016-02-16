package com.template.negocio;


import org.apache.log4j.Logger;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;


/**
 * Un tasklet es una clase que realiza una acción simple. Permite asi ejecutar
 * y realizar cualquier acción que necesitemos. 
 * Spring Batch provee clases más complejas y potentes que los tasklets, pero
 * son igualmente útiles para realizar algunas acciones.
 */
public class TaskFin implements Tasklet {

    static Logger log = Logger.getLogger(TaskFin.class);
    
 
    
   
    /** Un mensaje a mostrar por pantalla.
     *  Este mensaje será inyectado en el archivo de configuración de Spring.
     */

    /** La ejecución del tasklet. Spring Batch invoca este método para ejecutar
     * la acción.
     * @return el codigo de estado. Si el codigo es "FINISHED" continua la 
     *         ejecución con la siguiente tarea.
     */
    public RepeatStatus execute(StepContribution arg0, ChunkContext arg1) throws Exception {
//        if(Commons.levelError<2)
//        {
//            if(estadisticas.getProperty("Ficheros procesados") > 0 && estadisticas.getProperty("Ficheros insertados") > 0 && estadisticas.getProperty("Ficheros procesados")==estadisticas.getProperty("Ficheros insertados"))
//            {
//                // Actualizamos el estado de la ejecución en la tabla según el resultado de la misma.
//                AbstractFicheros.borrarFicheros(Commons.dirFicherosLocal);
//            }            
//        }
        return RepeatStatus.FINISHED;
    }   

}
