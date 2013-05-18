/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.michael.webScraper;

import Employees.Workers;
import com.csvreader.CsvReader;
import static com.michael.webScraper.DatabaseUtil.conn;
import static com.michael.webScraper.DatabaseUtil.done;
import static com.michael.webScraper.DatabaseUtil.getId;
import static com.michael.webScraper.DatabaseUtil.query;
import com.michael.webScraper.VO.BookVO;
import com.michael.webScraper.VO.SellerVO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
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
     * 
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
        model.addColumn("Dist (in mi)");
        
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

    /**
     * Code to search values from database
     * @param bookName
     */
    private void searchDatabase(String bookName) {                                         
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
        model.addColumn("Dist (in mi)");
        
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
        initData();
        
    }
    
    private void initData() {
        query = "select * from book where id%2=0";
        ArrayList<BookVO> books = DatabaseUtil.getAllBooks(query);
        for(BookVO book : books) {
            choice1.add(book.getBookName());
        }
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
        jLabel2 = new javax.swing.JLabel();
        picLabel = new javax.swing.JLabel();
        jDBSearchButton = new javax.swing.JButton();
        popChoiceButton = new javax.swing.JButton();
        jInitDbButton = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        textUrl1 = new javax.swing.JTextField();
        choice1 = new java.awt.Choice();
        jLabel3 = new javax.swing.JLabel();
        jCurrentZipText = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        buttonGo = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jDBSearchButton.setText("SearchDB");
        jDBSearchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jDBSearchButtonActionPerformed(evt);
            }
        });

        popChoiceButton.setText("All Choices");
        popChoiceButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popChoiceButtonActionPerformed(evt);
            }
        });

        jInitDbButton.setText("InitDB");
        jInitDbButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jInitDbButtonActionPerformed(evt);
            }
        });

        jButton3.setText("jButton3");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton5.setText("LOG FIle");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(79, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(1178, 1178, 1178)
                .addComponent(picLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(173, 173, 173))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton3)
                            .addComponent(jInitDbButton))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jDBSearchButton)
                        .addGap(29, 29, 29)
                        .addComponent(popChoiceButton))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(422, 422, 422)
                        .addComponent(jButton5)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(478, 478, 478)
                        .addComponent(picLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jButton5)
                        .addGap(42, 42, 42)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jInitDbButton)
                            .addComponent(jDBSearchButton)
                            .addComponent(popChoiceButton))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addGap(23, 23, 23)
                        .addComponent(jButton3)))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jLabel1.setText("Book Name:");

        jLabel3.setText("Zip Code");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(jTable1);

        buttonGo.setText("Scrape");
        buttonGo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonGoActionPerformed(evt);
            }
        });

        jButton1.setText("Test");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton4.setText("Log");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton2.setText("Lat-LongTest");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel6.setText("Image");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(buttonGo, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1))
                    .addComponent(jLabel3))
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jButton4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel6)
                                .addGap(105, 105, 105)))
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textUrl1, javax.swing.GroupLayout.PREFERRED_SIZE, 446, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jCurrentZipText, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(62, 62, 62)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(choice1, javax.swing.GroupLayout.PREFERRED_SIZE, 448, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 618, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(24, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 573, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(textUrl1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(choice1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jCurrentZipText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(99, 99, 99)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(buttonGo)
                            .addComponent(jButton1)
                            .addComponent(jButton4)
                            .addComponent(jButton2))
                        .addGap(38, 38, 38)
                        .addComponent(jLabel6)))
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
            jLabel6.setIcon(imageIcon);
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
        model.addColumn("Dist (m)");
        
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

    private void jDBSearchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jDBSearchButtonActionPerformed
        // TODO add your handling code here:
        // get the choice first
        String bookName = choice1.getSelectedItem();
        System.out.println("BookName From Choice "+bookName);
        int index = choice1.getSelectedIndex();
        System.out.println("Book Index From Choice "+index);
        DefaultTableModel model = new DefaultTableModel();
        jTable1.setModel(model);
        // retrieve the last two ids from book table
        DatabaseUtil.establishDatabaseConnection();
        query = "select id from book where id="+(index+1*2);
        int book_id_max = DatabaseUtil.getId(query, 1);
        query = "select id from book where id="+((index+1)*2-1);
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
        model.addColumn("Dist (in mi)");
        
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
            System.out.println("Seller zip location for Amazon "+seller.getZip_location());
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

    }//GEN-LAST:event_jDBSearchButtonActionPerformed

    private void popChoiceButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popChoiceButtonActionPerformed
        // TODO add your handling code here:
        query = "select * from book where id%2=0";
        ArrayList<BookVO> books = DatabaseUtil.getAllBooks(query);
        for(BookVO book : books) {
            choice1.add(book.getBookName());
        }
    }//GEN-LAST:event_popChoiceButtonActionPerformed
    
    /*
     * This button makes the zipcodes table and inserts records from the csv into 
     * this table
     */
    private void jInitDbButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jInitDbButtonActionPerformed
        try {
            // TODO add your handling code here:
            DatabaseUtil.establishDatabaseConnection();
            Workers initDB = new Workers();
            initDB.makeTableIfNotExists();
            //initDB.populateTableFromCSV();
            //File relativeFile = new File(getClass().getResource(File.separator+"resources"+File.separator+"zips.csv").toURI());
            //System.out.println("Absolute Path >> "+relativeFile.getAbsolutePath()); 
        } catch (Exception ex) {
            System.out.println("Exception "+ex);
            Logger.getLogger(WebpageReaderWithAgent.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_jInitDbButtonActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
       
        try {
        new AmazonWebScraper().getImage("111111");  
        } catch(Exception e) {
            System.out.println("Exception "+e);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        BookVO book = new BookVO();
        book.setBookName("nikhil");
        SellerVO seller1 = new SellerVO();
        seller1.setName("seller1");
        SellerVO seller2 = new SellerVO();
        seller1.setName("seller2");
        ArrayList<SellerVO> sellers = new ArrayList<SellerVO>();
        sellers.add(seller1);
        sellers.add(seller2);
        book.setSellers(sellers);
        LogUtil.log(book);
    }//GEN-LAST:event_jButton4ActionPerformed
    
    /**
     * Display log file on click of button
     * @param evt 
     */
    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        FileReader inputFile;
        try {
            inputFile = new FileReader( "log.txt");
            BufferedReader br = new BufferedReader(inputFile);
            jTextArea1.read(br, null);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(WebpageReaderWithAgent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(WebpageReaderWithAgent.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_jButton5ActionPerformed
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
    private java.awt.Choice choice1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JTextField jCurrentZipText;
    private javax.swing.JButton jDBSearchButton;
    private javax.swing.JButton jInitDbButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel picLabel;
    private javax.swing.JButton popChoiceButton;
    private javax.swing.JTextField textUrl1;
    // End of variables declaration//GEN-END:variables
}
