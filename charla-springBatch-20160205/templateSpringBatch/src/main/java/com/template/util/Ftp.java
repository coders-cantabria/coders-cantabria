/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.template.util;

import com.enterprisedt.net.ftp.FTPException;
import com.enterprisedt.net.ftp.FTPFile;
import com.enterprisedt.net.ftp.FTPTransferType;
import com.enterprisedt.net.ftp.Protocol;
import com.enterprisedt.net.ftp.SecureFileTransferClient;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;


public class Ftp {

    static Logger log = Logger.getLogger(Ftp.class);
    public static int CODE_OK = 0;
    private static SecureFileTransferClient ftpClient;
    private int TIMEOUT = 50000;
    //private static Ftp ftp;
    private String user = null;
    private String pass = null;
    private String ip = null;

    //Constructor
    public Ftp(String sAddress, String sUser, String sPassword, String conexionSegura) throws Exception {
        ftpClient = new SecureFileTransferClient();
        ip = sAddress;
        user = sUser;
        pass = sPassword;

        if (conexionSegura.equals("S")) {
            ftpClient.setProtocol(Protocol.SFTP);
        } else {
            ftpClient.setProtocol(Protocol.FTP);
        }
    }

    public String[] dir() throws Exception {
        return ftpClient.directoryNameList();
    }

    public boolean existsDirectory(String dir) throws Exception {
        return ftpClient.existsDirectory(dir);
    }

    public void conecta() throws Exception {

        if (ip == null) {
            throw new FTPException("ip is null");
        }
        if (user == null) {
            throw new FTPException("user is null");
        }
        if (pass == null) {
            throw new FTPException("pass is null");
        }

        try {
            if (!ftpClient.isConnected()) {
                ftpClient.setRemoteHost(ip);
                ftpClient.setUserName(user);
                ftpClient.setPassword(pass);
                FTPTransferType fTPTransferType = FTPTransferType.BINARY;
                ftpClient.setContentType(fTPTransferType);
                ftpClient.setTimeout(TIMEOUT);
                ftpClient.connect();
                log.info("Conectado correctamente al FTP.");
            }
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public void desconecta() throws Exception {
        try {
            ftpClient.disconnect();
        } catch (Exception e) {
            throw new Exception(e);
        }

    }

    public List<String> listarCarpeta(String carpeta) throws Exception {
        ArrayList lista = new ArrayList();
        FTPFile[] files = null;
        try {
            // Listfiles
            files = ftpClient.directoryList(carpeta);

            for (int iFile = 0; iFile < files.length; iFile++) {
                if (!files[iFile].isDir()) {
                    if (!files[iFile].getName().equals(".") && !files[iFile].getName().equals("..")) {
                        lista.add(files[iFile].getName());
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception(e);
        }

        return lista;
    }

    public int descargarFichero(String dirOrigen, String fichero, String dirLocal) throws Exception {

        int codigoSalida = 0;
        try {
            dirOrigen = ftpClient.getRemoteDirectory() + dirOrigen;
            if (!dirOrigen.endsWith("/")) {
                dirOrigen += "/";
            }
            if (!dirLocal.endsWith("/")) {
                dirLocal += "/";
            }
            String sPathFileOrigen = dirOrigen + fichero;
            String sPathFileDestino = dirLocal + fichero;
            log.info("Descargando fichero " + sPathFileOrigen + " -> " + sPathFileDestino);
            ftpClient.downloadFile(sPathFileDestino, sPathFileOrigen);
            codigoSalida = CODE_OK;
        } catch (FTPException ee) {
            log.error("Error bajando fichero del FTP: " + ee);
            int code = ee.getReplyCode();
            return code;
        }

        return codigoSalida;
    }

    public boolean descargarDirectorioFTP(String directorio_ftp, String directorio_local) {

        
        boolean bCorrecto = false;
        FTPFile[] directorio = null;
        String rutaLocal = "";
        String rutaRemota = "";

        try {

            if (!directorio_ftp.endsWith("/")) {
                directorio_ftp += "/";
            }
            if (!directorio_local.endsWith("/")) {
                directorio_local += "/";
            }

                //Cambio al directorio
                if (!directorio_ftp.equals("")) {
                    ftpClient.changeDirectory(directorio_ftp);
                }

                //Listado directorios
                directorio = ftpClient.directoryList();

                //Recorremos array
                for (int i = 0; i <= directorio.length - 1; i++) {
                    if (!directorio[i].isDir()) {
                        rutaLocal = directorio_local + directorio[i].getName();
                        rutaRemota = directorio_ftp + directorio[i].getName();
                    

                        //Bajamos archivo
                        log.info("Descargado fichero a " + rutaLocal);
                        ftpClient.downloadFile(rutaLocal, rutaRemota);
                        //Una vez copiado se borra
                        //ftpClient.deleteFile(directorio[i].getName());
                    }
                }

                //Descarga correcta
                bCorrecto = true;

            }catch (Exception e1) {
            log.warn("Error en la descarga: " + e1.getMessage() + ". Fichero: " + rutaRemota);
        }finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (Exception e1) {
                    System.out.println("Error en la desconexion: " + e1.getMessage());
                }
            }
        }

            return bCorrecto;
        }
        //Sube un archivo de local al ftp
    public boolean subirFichero(String dirOrigen, String fichero, String dirDestino) throws IOException, FTPException {
        boolean bOk = false;
        try {
            dirDestino = ftpClient.getRemoteDirectory() + dirDestino;
            if (!dirOrigen.endsWith("/")) {
                dirOrigen += "/";
            }
            if (!dirDestino.endsWith("/")) {
                dirDestino += "/";
            }
            String sPathFileOrigen = dirOrigen + fichero;
            String sPathFileDestino = dirDestino + fichero;
            //String[] prueba = ftpClient.directoryNameList();
            log.info("Subiendo fichero " + sPathFileOrigen + " -> " + sPathFileDestino);
            ftpClient.uploadFile(sPathFileOrigen, sPathFileDestino);
            log.info("FTP: Archivo subido correctamente");
            bOk = true;
        } catch (Exception e) {
            log.error("FTP: Error al subir el archivo: " + e.getMessage());
        }
        return bOk;
    }

    public void borrarFichero(String dirOrigen, String fichero) throws Exception {
        // Borrar
        String sPathFileOrigen = dirOrigen + "/" + fichero;
        try {

            if (ftpClient.exists(sPathFileOrigen)) {

                ftpClient.deleteFile(sPathFileOrigen);
                log.info("Borrado el fichero " + sPathFileOrigen);
            } else {
                log.error("No se ha podido encontrar el fichero" + fichero + " en el FTP");
            }

        } catch (Exception e) {
            log.info("No se pudo borrar el fichero " + sPathFileOrigen);
        }
    }
}
