/*
 * Commons.java
 *
 * Created on 26 de Marzo de 2008, 09:00
 *
 */
package com.template;

//Parametros constantes al proceso
import com.ibatis.common.resources.Resources;

import java.util.Properties;
import org.apache.log4j.Logger;

public class Commons {

    static Logger log = Logger.getLogger(Commons.class);
   
    public static String VERSION = "templateSpringBatch v01.00 (29/10/2015)";

    public final static String INI_CONFIG = "config/template/cfg/prj.properties";
    // Ubicacion de la configuracion de hibernate (fichero properties)
    public static String SQL_MAP_CONFIG = "ibatis/SqlMapConfig.xml";
    //-------------------------------------------------------------------------
    //Ubicación del directorio de logs
    public static String LOGS_PATH = "./logs/";

    public static int levelError = 0;

    private static Properties props = null;

    public static void setLevelError(int levelError) {
        if (levelError > Commons.levelError) {
            Commons.levelError = levelError;
        }
    }

    
    public static String getProperty(String propertyName) {
        String value = null;
        // Si no se ha cargado el fichero de propiedades, se carga.
        if (props == null) {
            try {
                props = Resources.getResourceAsProperties(INI_CONFIG);
            } catch (Exception e) {
                log.error("Error accediendo al fichero '" + INI_CONFIG + "', excepcion: " + e.toString());
            }
        }
        if (props != null && props.getProperty(propertyName) != null) {
            value = props.getProperty(propertyName);
        }
        return value;
    }

    public static String getTimeDifference(long start, long end) {
        String timeDif = "";
        long milisegundos = end - start;
        long dia, hora, minuto, segundo;
        long restodia, restohora, restominuto, restosegundo;

        dia = milisegundos / 86400000;
        restodia = milisegundos % 86400000;

        hora = restodia / 3600000;
        restohora = milisegundos % 3600000;

        minuto = restohora / 60000;
        restominuto = restohora % 60000;

        segundo = restominuto / 1000;
        restosegundo = restominuto % 1000;

        if (dia == 1) {
            timeDif = "1 día";
        } else if (dia > 1) {
            timeDif = dia + " días";
        }

        if (hora > 0) {
            if (!timeDif.equals("")) {
                timeDif += timeDif + ", ";
            }
            if (hora == 1) {
                timeDif = timeDif + "1 hora";
            } else {
                if (!timeDif.equals("") || hora > 1) {
                    timeDif = timeDif + hora + " horas";
                }
            }
        }

        if (minuto > 0) {
            if (!timeDif.equals("")) {
                timeDif = timeDif + ", ";
            }
            if (minuto == 1) {
                timeDif = timeDif + "1 minuto";
            } else {
                if (!timeDif.equals("") || minuto > 1) {
                    timeDif = timeDif + minuto + " minutos";
                }
            }
        }

        if (segundo > 0) {
            if (!timeDif.equals("")) {
                timeDif = timeDif + ", ";
            }
            if (segundo == 1) {
                timeDif = timeDif + "1 segundo";
            } else {
                if (!timeDif.equals("") || segundo > 1) {
                    timeDif = timeDif + segundo + " segundos";
                }
            }
        }

        if (restosegundo > 0) {
            if (!timeDif.equals("")) {
                timeDif = timeDif + ", ";
            }
            if (restosegundo == 1) {
                timeDif = timeDif + "1 milisegundo";
            } else {
                if (!timeDif.equals("") || restosegundo > 1) {
                    timeDif = timeDif + restosegundo + " milisisegundos";
                }
            }
        }

        return timeDif;
    }
}
