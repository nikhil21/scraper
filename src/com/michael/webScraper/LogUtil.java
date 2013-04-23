/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.michael.webScraper;

import com.michael.webScraper.VO.BookVO;
import com.michael.webScraper.VO.SellerVO;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author shweta
 */
public class LogUtil {
    
    //todo : change this path
    public static String FILE_PATH = "/home/shweta/Work/myspace/projects/OtherProjects/scraper";
  
    public static String LOG_FILE = "log.txt";
    public static BufferedWriter log = null;
    public static File file = null;
    
    public static boolean log(BookVO book){
        boolean done =  true;
        try {
            readErrorLogFile();
            addLogHeader();
            log.write(book.toString()+"\n");
            for(SellerVO seller:book.getSellers()){
                log.write(seller.toString()+"\n");
            }
            addLogFooter();
        } catch(Exception e){
            System.out.println("Exception at writing to log file : " + e);
            done = false;
        } finally{
            try {
                log.close();
            } catch (IOException ex) {
                System.out.println("Exception at closing log file : " + ex);
            }
        }
                
        return done;
    }
    
    private static void readErrorLogFile() {
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            URL url = classLoader.getResource("");
            System.out.println(">>>>>>>>>>"+url.getPath());
            FILE_PATH=url.getPath();
            
            
            file = new File(FILE_PATH + File.separator + LOG_FILE);
            log = new BufferedWriter(new FileWriter(file, true));
        } catch (Exception e) {
            System.out.println("Exception at reading log file : " + e);
        }
    } 
    
    private static void addLogHeader() {
        try {
            System.out.println("Appending log header");
            StringBuilder header = new StringBuilder();
            for(int i=0; i<100;i++) header.append("-");
            header.append("\n");
            header.append("Log start : ");
            header.append(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()));
            header.append("\n");
            for(int i=0; i<100;i++) header.append("-");
            header.append("\n");
            log.write(header.toString());
        } catch (Exception e) {
            System.out.println("Exception at writing header in  log file : " + e);
        }
    }

    private static void addLogFooter() {
        try {
            System.out.println("Appending log footer");
            StringBuilder footer = new StringBuilder();
            for(int i=0; i<100;i++) footer.append("-");
            footer.append("\n");
            footer.append("Log end : ");
            footer.append(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()));
            footer.append("\n");
            for(int i=0; i<100;i++) footer.append("-");
            footer.append("\n");
            log.write(footer.toString());
        } catch (Exception e) {
            System.out.println("Exception at writing footer in log file : " + e);
        }
    }
    
}
