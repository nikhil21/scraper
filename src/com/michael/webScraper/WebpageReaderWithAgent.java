/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.michael.webScraper;

import com.csvreader.CsvReader;
import static com.michael.webScraper.DatabaseUtil.conn;
import static com.michael.webScraper.DatabaseUtil.done;
import static com.michael.webScraper.DatabaseUtil.getId;
import static com.michael.webScraper.DatabaseUtil.query;
import com.michael.webScraper.VO.SellerVO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;
import org.apache.commons.lang3.StringEscapeUtils;

/**
 *
 * @author nikhil
 */
public class WebpageReaderWithAgent extends javax.swing.JFrame {

    private static String webpage = null;
    public static final String USER_AGENT = "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.9.2) Gecko/20100115 Firefox/3.6";

    /**
     * Code to show values from database
     * @param evt 
     */
    private void showValuesFromDatabase() {                                         
        // TODO add your handling code here:
        DefaultTableModel model = new DefaultTableModel();
        jTable1.setModel(model);
        // retrieve the last two ids from book table
        DatabaseUtil.establishDatabaseConnection();
        query = "select max(id) from book";
        int book_id_max = DatabaseUtil.getId(query, 1);
        query = "select max(id) - 1 from book";
        int book_id_max_but_one = DatabaseUtil.getId(query, 1);
        System.out.println("New Book ID : " + book_id_max);

        // populate the SellerVO list using the ids fetched
        // first max id
        query = "select * from seller where book_id=" + book_id_max;
        ArrayList<SellerVO> sellerList1 = DatabaseUtil.getAllSellers(query);
        // one but max id
        query = "select * from seller where book_id=" + book_id_max_but_one;
        ArrayList<SellerVO> sellerList2 = DatabaseUtil.getAllSellers(query);
        //commit

        // Then start adding the rows
        model.addColumn("Source");
        model.addColumn("Seller Name");
        model.addColumn("Price");
        model.addColumn("Rating");
        model.addColumn("Seller Location");
        model.addColumn("Dist (in kms)");
        
        // get the current lat & long, defaulting to 0 latitude and 0 longitude
         ZipCodeVO currentLocation = HaverSineUtil.getCurrentLatAndLong(jCurrentZipText.getText());
        
        for (SellerVO seller : sellerList1) {
            double distance = 0.0;
            System.out.println("Seller zip location for abe "+seller.getZip_location());
            ZipCodeVO sellerLocation = DatabaseUtil.getZipCodeDetailsFromName(seller.getZip_location()); 
            if(sellerLocation != null) {
                distance = HaverSineUtil.distanceOnEarthBetweenPointsInKm(
                        sellerLocation.getLatitude().doubleValue(),
                        sellerLocation.getLongitude().doubleValue(),
                        currentLocation.getLatitude().doubleValue(),
                        currentLocation.getLongitude().doubleValue());
            }
            model.addRow(new Object[]{"ABE", seller.getName(), seller.getPrice(), 
                seller.getRating(), seller.getZip_location(), distance});
        }
        for (SellerVO seller : sellerList2) {
            System.out.println("Seller zip location for abe "+seller.getZip_location());
            double distance = 0.0;
            ZipCodeVO sellerLocation = DatabaseUtil.getZipCodeDetailsFromName(seller.getZip_location()); 
            if(sellerLocation != null) {
                distance = HaverSineUtil.distanceOnEarthBetweenPointsInKm(
                        sellerLocation.getLatitude().doubleValue(),
                        sellerLocation.getLongitude().doubleValue(),
                        currentLocation.getLatitude().doubleValue(),
                        currentLocation.getLongitude().doubleValue());
            }
            model.addRow(new Object[]{"Amazon", seller.getName(), 
                seller.getPrice(), 
                seller.getRating(),
                seller.getZip_location(), distance});
        }

        //jTable1.setVisible(true);
    }                                        

    public static InputStream getURLInputStream(String sURL) throws Exception {
        try {


            URLConnection oConnection = (new URL(sURL)).openConnection();
            oConnection.setRequestProperty("User-Agent", USER_AGENT);
            return oConnection.getInputStream();
        } catch (Exception e) {
            System.out.println("Exception is " + e);
            return null;
        }
    }

    public static BufferedReader read(String url) throws Exception {
        //InputStream content = (InputStream)uc.getInputStream();
//        BufferedReader in = new BufferedReader (new InputStreamReader
//    (content));
        InputStream content = (InputStream) getURLInputStream(url);
        return new BufferedReader(new InputStreamReader(content));
    } // read

    public static BufferedReader read2(String url) throws Exception {
        return new BufferedReader(
                new InputStreamReader(
                new URL(url).openStream()));
    } // read

    public static void fetchData(String url) throws Exception {

        System.out.println("Url Enterd is " + url);

        if (url == null) {
            System.out.println("No URL inputted.");
            System.exit(1);
        } // any inputs?

        webpage = url;
        System.out.println("Contents of the following URL: " + webpage + "\n");

        BufferedReader reader = read(webpage);
        String line = reader.readLine();

        while (line != null) {
            System.out.println(line);
            line = reader.readLine();
        } // while

    } // fetchData

    /**
     * Creates new form WebpageReaderWithAgent
     */
    public WebpageReaderWithAgent() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        buttonGo = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        textUrl1 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        picLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jCurrentZipText = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Book Name:");

        buttonGo.setText("Scrape");
        buttonGo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonGoActionPerformed(evt);
            }
        });

        jLabel6.setText("Image");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jButton1.setText("Test");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Lat-LongTest");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel3.setText("Zip Code");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 573, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(599, 599, 599))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(127, 127, 127)
                        .addComponent(buttonGo, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38)
                        .addComponent(jButton1)
                        .addGap(76, 76, 76)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(textUrl1, javax.swing.GroupLayout.PREFERRED_SIZE, 446, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jCurrentZipText, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addComponent(picLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(173, 173, 173))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(textUrl1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(jCurrentZipText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addComponent(jLabel2))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jButton2)
                                    .addComponent(jButton1)
                                    .addComponent(buttonGo))
                                .addGap(20, 20, 20)
                                .addComponent(jLabel6))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(424, 424, 424)
                        .addComponent(picLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(116, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonGoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonGoActionPerformed
        // TODO add your handling code here:
        // first get the URL
        //String url = textAuthorNameAmazon.getText();
        String url = textUrl1.getText();
        System.out.println("The url is >>> " + url);
        try {
            // call the webScraper From Here
            //fetchData(url);
            AmazonWebScraper scraper = new AmazonWebScraper();
            String resUrl = scraper.resolveURL(url);
            String isbnNo = scraper.findISBNNo(resUrl);
            // now we can go to the page and fetch details
            scraper.showDetails(isbnNo);

            scraper.getImage(isbnNo);
            String imageLocation = scraper.getImageLocation(isbnNo);
            //String imageLocation = "/home/nikhil/NetBeansProjects/testWebScraperHtmlUnit/image1.jpg";
            // get the image from the file System
            BufferedImage image = null;
            try {
                image = ImageIO.read(new File(imageLocation));
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }

            ImageIcon imageIcon = new ImageIcon(image);
            // show image on the label
            picLabel.setIcon(imageIcon);
            //fetchData(resUrl);

            //ABE.com
            AbeWebScraper abe = new AbeWebScraper();
            abe.showDetails(isbnNo);

            // show details from the database
            showValuesFromDatabase();
            
        } catch (Exception e) {
            System.out.println("Exception Caught !!");
        }
    }//GEN-LAST:event_buttonGoActionPerformed
    /**
     * Code to show values from database
     * @param evt 
     */
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        DefaultTableModel model = new DefaultTableModel();
        jTable1.setModel(model);
        // retrieve the last two ids from book table
        DatabaseUtil.establishDatabaseConnection();
        query = "select max(id) from book";
        int book_id_max = DatabaseUtil.getId(query, 1);
        query = "select max(id) - 1 from book";
        int book_id_max_but_one = DatabaseUtil.getId(query, 1);
        System.out.println("New Book ID : " + book_id_max);

        // populate the SellerVO list using the ids fetched
        // first max id
        query = "select * from seller where book_id=" + book_id_max;
        ArrayList<SellerVO> sellerList1 = DatabaseUtil.getAllSellers(query);
        // one but max id
        query = "select * from seller where book_id=" + book_id_max_but_one;
        ArrayList<SellerVO> sellerList2 = DatabaseUtil.getAllSellers(query);
        //commit

        // Then start adding the rows
        model.addColumn("Source");
        model.addColumn("Seller Name");
        model.addColumn("Price");
        model.addColumn("Rating");
        model.addColumn("Seller Location");
        model.addColumn("Dist (in kms)");
        
        // get the current lat & long, defaulting to 0 latitude and 0 longitude
         ZipCodeVO currentLocation = HaverSineUtil.getCurrentLatAndLong(jCurrentZipText.getText());
        
        for (SellerVO seller : sellerList1) {
            double distance = 0.0;
            System.out.println("Seller zip location for abe "+seller.getZip_location());
            ZipCodeVO sellerLocation = DatabaseUtil.getZipCodeDetailsFromName(seller.getZip_location()); 
            if(sellerLocation != null) {
                distance = HaverSineUtil.distanceOnEarthBetweenPointsInKm(
                        sellerLocation.getLatitude().doubleValue(),
                        sellerLocation.getLongitude().doubleValue(),
                        currentLocation.getLatitude().doubleValue(),
                        currentLocation.getLongitude().doubleValue());
            }
            model.addRow(new Object[]{"ABE", seller.getName(), seller.getPrice(), 
                seller.getRating(), seller.getZip_location(), distance});
        }
        for (SellerVO seller : sellerList2) {
            System.out.println("Seller zip location for abe "+seller.getZip_location());
            double distance = 0.0;
            ZipCodeVO sellerLocation = DatabaseUtil.getZipCodeDetailsFromName(seller.getZip_location()); 
            if(sellerLocation != null) {
                distance = HaverSineUtil.distanceOnEarthBetweenPointsInKm(
                        sellerLocation.getLatitude().doubleValue(),
                        sellerLocation.getLongitude().doubleValue(),
                        currentLocation.getLatitude().doubleValue(),
                        currentLocation.getLongitude().doubleValue());
            }
            model.addRow(new Object[]{"Amazon", seller.getName(), 
                seller.getPrice(), 
                seller.getRating(),
                seller.getZip_location(), distance});
        }

        //jTable1.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * Extract Latitude & Longitude From The Given Zip Code
     * @param evt 
     */
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        String zipCode = "91601";
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
                
                // now calculate the distance of the given lat & long
                // with all the results 
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    
    }//GEN-LAST:event_jButton2ActionPerformed
/**
 * @param args the command line arguments
 */
public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                

}
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(WebpageReaderWithAgent.class  

.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } 

catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(WebpageReaderWithAgent.class  

.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } 

catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(WebpageReaderWithAgent.class  

.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } 

catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(WebpageReaderWithAgent.class  

.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new WebpageReaderWithAgent().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonGo;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JTextField jCurrentZipText;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel picLabel;
    private javax.swing.JTextField textUrl1;
    // End of variables declaration//GEN-END:variables
}
