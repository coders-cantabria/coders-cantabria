package com.template.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipUtil {

    private static final int BUFFER_SIZE = 4096;

    public static void compressFiles(Set<File> listFiles, String destZipFile) throws FileNotFoundException, IOException {

        ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(destZipFile));

        for (File file : listFiles) {
            if (file.isDirectory()) {
                addFolderToZip(file, file.getName(), zos);
            } else {
                addFileToZip(file, zos);
            }
        }

        zos.flush();
        zos.close();

    }

    private static void addFileToZip(File file, ZipOutputStream zos) throws FileNotFoundException, IOException {

        zos.putNextEntry(new ZipEntry(file.getName()));

        @SuppressWarnings("resource")
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));

        @SuppressWarnings("unused")
        long bytesRead = 0;
        byte[] bytesIn = new byte[BUFFER_SIZE];
        int read = 0;

        while ((read = bis.read(bytesIn)) != -1) {
            zos.write(bytesIn, 0, read);
            bytesRead += read;
        }
        bis.close();
        zos.closeEntry();
    }

    private static void addFolderToZip(File folder, String parentFolder, ZipOutputStream zos) throws FileNotFoundException, IOException {

        for (File file : folder.listFiles()) {
            if (file.isDirectory()) {
                addFolderToZip(file, parentFolder + "/" + file.getName(), zos);
                continue;
            }

            zos.putNextEntry(new ZipEntry(parentFolder + "/" + file.getName()));

            @SuppressWarnings("resource")
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));

            @SuppressWarnings("unused")
            long bytesRead = 0;
            byte[] bytesIn = new byte[BUFFER_SIZE];
            int read = 0;

            while ((read = bis.read(bytesIn)) != -1) {
                zos.write(bytesIn, 0, read);
                bytesRead += read;
            }
            bis.close();
            zos.closeEntry();

        }
    }

    public static String descompressFiles(FileInputStream ficheroZip, String destZipFile) throws FileNotFoundException, IOException {


        ZipInputStream zis = new ZipInputStream(new BufferedInputStream(ficheroZip));
        BufferedOutputStream dest = null;
        int count;
        String destinoFinal = "";
        byte data[] = new byte[ BUFFER_SIZE ];
        ZipEntry entry;
        while ((entry = zis.getNextEntry()) != null) {

            if (!entry.isDirectory()) {
                String entryName = entry.getName();

                if (!destZipFile.endsWith("/")){
                    destZipFile = destZipFile + File.separator;
                }
                destinoFinal = destZipFile + entryName;
                FileOutputStream fos = new FileOutputStream(destinoFinal);
                dest = new BufferedOutputStream(fos, BUFFER_SIZE);
                while ((count = zis.read(data, 0, BUFFER_SIZE)) != -1) {
                    dest.write(data, 0, count);
                }
                dest.flush();
                dest.close();
            }

        }

        zis.close();

        return destinoFinal;

    }
}
