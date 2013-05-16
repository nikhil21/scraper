/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.michael.webScraper;

import com.csvreader.CsvReader;
import com.michael.webScraper.VO.SellerVO;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;

/**
 *
 * @author
 */
public class HaverSineUtil {

    public static double distanceOnEarthBetweenPointsInKm(
            double lat1, double lng1, double lat2, double lng2) {
        int r = 6371; // average radius of the earth in km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = r * c;
        return d;
    }
    
    public static ZipCodeVO getCurrentLatAndLong(String zipCode) {
        
        ZipCodeVO zip = new ZipCodeVO();
        /*if(zipCode.isEmpty() || zipCode == null || zipCode.length() !=2) {
            return zip;
        }*/
        //String zipCode = "91601";
        String latitude = "0";
        String longitude = "0";
        try {
            /**
             * JavaCVS api is required in order to read this, it can be found at
             * http://sourceforge.net/projects/javacsv/
                     *
             */
            CsvReader products = new CsvReader("zips.csv");
            /**
             * a cvs containing all the zip codes and latitudes and longitudes
             * can be found at:
             * http://sourceforge.net/projects/zips/files/zips/zips.csv.zip/
                    *
             */
            products.readHeaders();
            int numOfHeaders = products.getHeaderCount();
            System.out.println("Number of headers" + numOfHeaders);
            try {
                while (products.readRecord()) {

                    String lookedupZip = products.get(products.getHeader(0));
                    if (lookedupZip.equals(zipCode)) {
                        latitude = products.get(products.getHeader(2));
                        longitude = products.get(products.getHeader(3));
                    }

                }
                
                System.out.println("latitude >> "+latitude);
                System.out.println("longitude >> "+longitude);
                
                zip.setLatitude(new BigDecimal(latitude.trim()));
                zip.setLongitude(new BigDecimal(longitude.trim()));
            
                // now calculate the distance of the given lat & long
                return zip;
                // with all the results 
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        return zip;
    }
    
     
}
