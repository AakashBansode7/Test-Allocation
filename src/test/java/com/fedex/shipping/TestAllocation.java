package com.fedex.shipping;
import java.io.*;
import java.sql.*;
import java.util.Properties;

public class TestAllocation {
    public static String CYCLE = ReadPropertiesFile("CYCLE").trim();
    static String BP = ReadPropertiesFile("BP").trim();

    public static String VERSION = ReadPropertiesFile("VERSION").trim();

    public static String Device = ReadPropertiesFile("Device").trim();

    public static String GRTITG = ReadPropertiesFile("GRTITG").trim();

    public static String GTMITG = ReadPropertiesFile("GTMITG").trim();

    public static String CAITG = ReadPropertiesFile("CAITG").trim();

    public static String FLAVOUR = ReadPropertiesFile("FLAVOUR").trim();

    public static String GRTADT = ReadPropertiesFile("GRTADT").trim();

    public static String GTMADT = ReadPropertiesFile("GTMADT").trim();

    public static String CAADT = ReadPropertiesFile("CAADT").trim();

    public static String ReadPropertiesFile(String KeyName) {
        Properties prop = new Properties();
        FileInputStream input = null;
        String KeyValue = null;

        try {
            input = new FileInputStream("E:\\Ashwini\\Aakash\\Configuration.properties");
            prop.load(input);

            KeyValue = prop.getProperty(KeyName);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return KeyValue;
    }

    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
        // Open a connection
        Connection connection = null;
        ResultSet ag = null;

        FileWriter fw = new FileWriter("E:\\Ashwini\\Aakash\\Test_data.txt");
        BufferedWriter bw = new BufferedWriter(fw);
        System.out.println("File creation  OK");
        bw.write(BP + "\n\n\n" + FLAVOUR + "\n\n\n");
        // Extract data from result set
        int count=0;

        Class.forName("oracle.jdbc.driver.OracleDriver");
        System.out.println("DRIVER_LOAD OK");
        connection = DriverManager.getConnection("jdbc:oracle:thin:@T000305-scan.ute.iaas.fedex.com:1526/CMMSHP_PERM_01_CSHIP_S1.ute.iaas.fedex.com", "CSHIP_DBO", "fT6xEFj9wcTtdogXlY87CSHIPDBO");
        System.out.println("CONNECTION  OK");

        // To fetch Nafta Tins
        bw.write(BP + "\n\n\n" + FLAVOUR + "\n\n\n");
        switch (FLAVOUR) {
            case (("ILGD")):
                switch (BP) {
                    case (("R")):
                        System.out.println("Nafta GRT");
                        PreparedStatement stmt = connection.prepareStatement("SELECT DISTINCT TEST_ID_CHAR FROM " + CYCLE + "_L3" + BP + Device + VERSION + FLAVOUR + "_BL " + " where (ITGNBR IN(" + GRTITG + ") AND CIFLG='Y') AND  (TEST_ID NOT IN(select distinct test_id from " + CYCLE + "_L3" + BP + Device + VERSION + FLAVOUR + "_RSLT )) ");
                        System.out.println(stmt);
                        ag = stmt.executeQuery();
                        bw.write("Nafta:-  Test_ID IN(");

                        while (ag.next()) {

                            long t = ag.getLong(1);
                            System.out.print(t + ",");

                            try {
                                long testid = ag.getLong(1);
                                bw.write(String.valueOf(testid));
                                System.out.print("  ");
                                bw.append(",");
                                count++;
                            } catch (Exception e) {
                                System.out.println("Error ......... writing to a file..... :P" + e);
                            }


                        }
                        bw.write("  ) \n\n\n");
                        System.out.println("\n");
                        break;


                    case (("C")):

                        System.out.println("Nafta Canada");
                        PreparedStatement stmt1 = connection.prepareStatement("SELECT DISTINCT TEST_ID_CHAR FROM  " + CYCLE + "_L3" + BP + Device + VERSION + FLAVOUR + "_BL " + " where ITGNBR IN (" + CAITG + ")  AND (CIFLG='Y') AND ( USMCACERTITY IS NULL)  AND  (TEST_ID NOT IN(select distinct test_id from " + CYCLE + "_L3" + BP + Device + VERSION + FLAVOUR + "_RSLT ))");
                        System.out.println(stmt1);
                        ag = stmt1.executeQuery();
                        bw.write("Nafta:-  Test_ID IN(");
                        count=0;
                        while (ag.next()) {

                            long t = ag.getLong(1);
                            System.out.print(t + ",");

                            try {
                                long testid = ag.getLong(1);
                                bw.write(String.valueOf(testid));
                                System.out.print("  ");
                                bw.append(",");
                                count++;
                            } catch (Exception e) {
                                System.out.println("Error ......... writing to a file..... :P" + e);
                            }


                        }
                        bw.write("  ) \n\n\n");
                        System.out.println("\n");
                        break;
                    case (("B")):

                        System.out.println("Nafta GTM");
                        PreparedStatement stmt2 = connection.prepareStatement("SELECT DISTINCT TEST_ID_CHAR FROM " + CYCLE + "_L3" + BP + Device + VERSION + FLAVOUR + "_BL " + "WHERE  ITGNBR IN(" + GTMITG + ") AND (CIFLG='Y') AND  (TEST_ID NOT IN(select distinct test_id from " + CYCLE + "_L3" + BP + Device + VERSION + FLAVOUR + "_RSLT ))");
                        System.out.println(stmt2);
                        ag = stmt2.executeQuery();
                        bw.write("Nafta:-  Test_ID IN(");
                        while (ag.next()) {

                            long t = ag.getLong(1);
                            System.out.print(t + ",");

                            try {
                                long testid = ag.getLong(1);
                                bw.write(String.valueOf(testid));
                                System.out.print("  ");
                                bw.append(",");
                                bw.flush();
                                count++;
                            } catch (Exception e) {
                                System.out.println("Error ......... writing to a file..... :P" + e);
                            }


                        }
                        bw.write("  ) \n\n\n");
                        System.out.println("\n");
                }

                System.out.println("TD");
                PreparedStatement stmt = connection.prepareStatement("SELECT DISTINCT  GRPIND FROM " + CYCLE + "_L3" + BP + Device + VERSION + FLAVOUR + "_BL " + " where NATBIND='Y' AND  (TEST_ID NOT IN(select distinct test_id from " + CYCLE + "_L3" + BP + Device + VERSION + FLAVOUR + "_RSLT ))");
                ag = stmt.executeQuery();
                bw.write("TD:-  GRPIND IN('");
                while (ag.next()) {

                    String t = ag.getString(1);
                    System.out.print(t + ",");
                    try {
                        String GRPIND = ag.getString(1);
                        bw.write(String.valueOf(GRPIND.trim()));
                        System.out.print("  ");
                        bw.append("','");
                    } catch (Exception e) {
                        System.out.println("Error ......... writing to a file..... :P" + e);
                    }


                }
                bw.write("  )\n\n\n");
                //}
                System.out.println("Regular:- ");
                PreparedStatement stmt1 = connection.prepareStatement("SELECT DISTINCT TEST_ID FROM " + CYCLE + "_L3" + BP + Device + VERSION + FLAVOUR + "_BL " + "WHERE  (GRPIND IS NULL OR GRPIND='0') AND  (TEST_ID NOT IN(select distinct test_id from " + CYCLE + "_L3" + BP + Device + VERSION + FLAVOUR + "_RSLT ))");
                ag = stmt1.executeQuery();
                bw.write("REG:-  TEST_ID IN(");
                while (ag.next()) {

                    String t = ag.getString(1);
                    System.out.print(t + " , ");
                    try {
                        String TEST_ID = ag.getString(1);
                        bw.write(String.valueOf(TEST_ID));
                        System.out.print("  ");
                        bw.append(",");
                        count++;
                    } catch (Exception e) {
                        System.out.println("Error ......... writing to a file..... :P" + e);
                    }


                }
                bw.write("  )");


                System.out.println("QUERY  OK");

                bw.newLine();
                bw.newLine();
                bw.newLine();
                bw.close();
                break;
            case (("ILEP")):
                bw.write("ADT\n\n");
                if (BP.equalsIgnoreCase("R")) {
                    System.out.println("ADT Regular:- ");
                    PreparedStatement stmt3 = connection.prepareStatement("SELECT DISTINCT TEST_ID FROM " + CYCLE + "_L3" + BP + Device + VERSION + FLAVOUR + "_BL WHERE  ("+ GRTADT +"AND (GRPIND IS NULL)) AND  (TEST_ID NOT IN(select distinct test_id from " + CYCLE + "_L3" + BP + Device + VERSION + FLAVOUR + "_RSLT ))");
                    ag = stmt3.executeQuery();
                    bw.write("ADT REG:-  TEST_ID IN(");
                    while (ag.next()) {

                        String t = ag.getString(1);
                        System.out.print(t + " , ");
                        try {
                            String TEST_ID = ag.getString(1);
                            bw.write(String.valueOf(TEST_ID));
                            System.out.print("  ");
                            bw.append(",");
                            count++;
                        } catch (Exception e) {
                            System.out.println("Error ......... writing to a file..... :P" + e);
                        }


                    }
                    bw.write(")");


                    System.out.println("ADT TD:- ");
                    PreparedStatement stmt4 = connection.prepareStatement("SELECT DISTINCT GRPIND FROM " + CYCLE + "_L3" + BP + Device + VERSION + FLAVOUR + "_BL WHERE  (("+GRTADT+") AND (NATBIND='Y')) AND  (TEST_ID NOT IN(select distinct test_id from " + CYCLE + "_L3" + BP + Device + VERSION + FLAVOUR + "_RSLT ))");
                    ag = stmt4.executeQuery();
                    bw.write("\n\n\nADT TD:-  GRPIND  IN('");
                    while (ag.next()) {

                        String t = ag.getString(1);
                        System.out.print(t + " , ");
                        try {
                            String TEST_ID = ag.getString(1);
                            bw.write(String.valueOf(TEST_ID));
                            System.out.print("  ");
                            bw.append("','");
                        } catch (Exception e) {
                            System.out.println("Error ......... writing to a file..... :P" + e);
                        }


                    }
                    bw.write(")");


                    System.out.println("\n\n\nADT IPD:- ");
                    PreparedStatement stmt5 = connection.prepareStatement("SELECT DISTINCT GRPIND FROM " + CYCLE + "_L3" + BP + Device + VERSION + FLAVOUR + "_BL WHERE  (("+GRTADT+") AND (svctypcd in('17','18','84'))) AND  (TEST_ID NOT IN(select distinct test_id from " + CYCLE + "_L3" + BP + Device + VERSION + FLAVOUR + "_RSLT ))");
                    ag = stmt5.executeQuery();
                    bw.write("\n\n\nADT IPD:-  GRPIND  IN('");
                    while (ag.next()) {

                        String t = ag.getString(1);
                        System.out.print(t + " , ");
                        try {
                            String TEST_ID = ag.getString(1);
                            bw.write(String.valueOf(TEST_ID));
                            System.out.print("  ");
                            bw.append("','");
                        } catch (Exception e) {
                            System.out.println("Error ......... writing to a file..... :P" + e);
                        }


                    }
                    bw.write(")");
                }

                if (BP.equalsIgnoreCase("C")) {
                    System.out.println("ADT Regular:- ");
                    PreparedStatement stmt6 = connection.prepareStatement("SELECT DISTINCT TEST_ID FROM " + CYCLE + "_L3" + BP + Device + VERSION + FLAVOUR + "_BL WHERE (("+CAADT+") AND (GRPIND IS NULL OR GRPIND='0')) AND  (TEST_ID NOT IN(select distinct test_id from " + CYCLE + "_L3" + BP + Device + VERSION + FLAVOUR + "_RSLT ))");
                    ag = stmt6.executeQuery();
                    bw.write("ADT REG:-  TEST_ID IN(");
                    while (ag.next()) {

                        String t = ag.getString(1);
                        System.out.print(t + ",");
                        try {
                            String TEST_ID = ag.getString(1);
                            bw.write(String.valueOf(TEST_ID));
                            System.out.print("  ");
                            bw.append(",");
                            count++;
                        } catch (Exception e) {
                            System.out.println("Error ......... writing to a file..... :P" + e);
                        }


                    }
                    bw.write(")");


                    System.out.println("ADT TD:- ");
                    PreparedStatement stmt7 = connection.prepareStatement("SELECT DISTINCT GRPIND FROM " + CYCLE + "_L3" + BP + Device + VERSION + FLAVOUR + "_BL WHERE   (("+CAADT+") AND (NATBIND='Y'))  AND  (TEST_ID NOT IN(select distinct test_id from " + CYCLE + "_L3" + BP + Device + VERSION + FLAVOUR + "_RSLT ))");
                    ag = stmt7.executeQuery();
                    bw.write("\n\n\nADT TD:-  GRPIND  IN('");
                    while (ag.next()) {

                        String t = ag.getString(1);
                        System.out.print(t + " , ");
                        try {
                            String TEST_ID = ag.getString(1);
                            bw.write(String.valueOf(TEST_ID));
                            System.out.print("  ");
                            bw.append("','");
                        } catch (Exception e) {
                            System.out.println("Error ......... writing to a file..... :P" + e);
                        }


                    }
                    bw.write(")");


                    System.out.println("\n\n\nADT IPD:- ");
                    PreparedStatement stmt8 = connection.prepareStatement("SELECT DISTINCT GRPIND FROM " + CYCLE + "_L3" + BP + Device + VERSION + FLAVOUR + "_BL WHERE   (("+CAADT+") AND (svctypcd in('17','18','84')))  AND (TEST_ID NOT IN(select distinct test_id from " + CYCLE + "_L3" + BP + Device + VERSION + FLAVOUR + "_RSLT ))");
                    ag = stmt8.executeQuery();
                    bw.write("\n\n\nADT IPD:-  GRPIND  IN('");
                    while (ag.next()) {

                        String t = ag.getString(1);
                        System.out.print(t + " , ");
                        try {
                            String TEST_ID = ag.getString(1);
                            bw.write(String.valueOf(TEST_ID));
                            System.out.print("  ");
                            bw.append("','");
                        } catch (Exception e) {
                            System.out.println("Error ......... writing to a file..... :P" + e);
                        }


                    }
                    bw.write(")");
                }


                if (BP.equalsIgnoreCase("B")) {
                    System.out.println("ADT Regular:- ");
                    PreparedStatement stmt6 = connection.prepareStatement("SELECT DISTINCT TEST_ID FROM " + CYCLE + "_L3" + BP + Device + VERSION + FLAVOUR + "_BL WHERE (("+GTMADT+") AND (GRPIND IS NULL OR GRPIND='0')) AND  (TEST_ID NOT IN(select distinct test_id from " + CYCLE + "_L3" + BP + Device + VERSION + FLAVOUR + "_RSLT ))");
                    ag = stmt6.executeQuery();
                    bw.write("ADT REG:-  TEST_ID IN(");
                    while (ag.next()) {

                        String t = ag.getString(1);
                        System.out.print(t + " , ");
                        try {
                            String TEST_ID = ag.getString(1);
                            bw.write(String.valueOf(TEST_ID));
                            System.out.print("  ");
                            bw.append(",");
                            count++;
                        } catch (Exception e) {
                            System.out.println("Error ......... writing to a file..... :P" + e);
                        }


                    }
                    bw.write(")");


                    System.out.println("ADT TD:- ");
                    PreparedStatement stmt7 = connection.prepareStatement("SELECT DISTINCT GRPIND FROM " + CYCLE + "_L3" + BP + Device + VERSION + FLAVOUR + "_BL WHERE (("+GTMADT+") AND (NATBIND='Y')) AND  (TEST_ID NOT IN(select distinct test_id from " + CYCLE + "_L3" + BP + Device + VERSION + FLAVOUR + "_RSLT ))");
                    ag = stmt7.executeQuery();
                    bw.write("\n\n\nADT TD:-  GRPIND  IN('");
                    while (ag.next()) {

                        String t = ag.getString(1);
                        System.out.print(t + " , ");
                        try {
                            String TEST_ID = ag.getString(1);
                            bw.write(String.valueOf(TEST_ID));
                            System.out.print("  ");
                            bw.append("','");
                        } catch (Exception e) {
                            System.out.println("Error ......... writing to a file..... :P" + e);
                        }


                    }
                    bw.write(")");


                    System.out.println("\n\n\nADT IPD:- ");
                    PreparedStatement stmt8 = connection.prepareStatement("SELECT DISTINCT GRPIND FROM " + CYCLE + "_L3" + BP + Device + VERSION + FLAVOUR + "_BL WHERE (("+GTMADT+") AND (svctypcd in('17','18','84'))) AND  (TEST_ID NOT IN(select distinct test_id from " + CYCLE + "_L3" + BP + Device + VERSION + FLAVOUR + "_RSLT ))");
                    ag = stmt8.executeQuery();
                    bw.write("\n\n\nADT IPD:-  GRPIND  IN('");
                    while (ag.next()) {

                        String t = ag.getString(1);
                        System.out.print(t + " , ");
                        try {
                            String TEST_ID = ag.getString(1);
                            bw.write(String.valueOf(TEST_ID));
                            System.out.print("  ");
                            bw.append("','");
                        } catch (Exception e) {
                            System.out.println("Error ......... writing to a file..... :P" + e);
                        }


                    }
                    bw.write(")");
                }


                System.out.println("ILEP Regular:- ");
                PreparedStatement stmt6 = connection.prepareStatement("SELECT DISTINCT TEST_ID FROM " + CYCLE + "_L3" + BP + Device + VERSION + FLAVOUR + "_BL WHERE  (GRPIND IS NULL OR GRPIND='0') AND (TEST_ID NOT IN(select distinct test_id from " + CYCLE + "_L3" + BP + Device + VERSION + FLAVOUR + "_RSLT ))");
                ag = stmt6.executeQuery();
                bw.write("\n\n\nILEP REG:-  TEST_ID IN(");
                while (ag.next()) {

                    String t = ag.getString(1);
                    System.out.print(t + " , ");
                    try {
                        String TEST_ID = ag.getString(1);
                        bw.write(String.valueOf(TEST_ID));
                        System.out.print("  ");
                        bw.append(",");
                        count++;
                    } catch (Exception e) {
                        System.out.println("Error ......... writing to a file..... :P" + e);
                    }


                }
                bw.write(")");


                System.out.println("ILEP TD:- ");
                PreparedStatement stmt7 = connection.prepareStatement("SELECT DISTINCT GRPIND FROM " + CYCLE + "_L3" + BP + Device + VERSION + FLAVOUR + "_BL WHERE  (NATBIND='Y')  AND (TEST_ID NOT IN(select distinct test_id from " + CYCLE + "_L3" + BP + Device + VERSION + FLAVOUR + "_RSLT ))");
                ag = stmt7.executeQuery();
                bw.write("\n\n\nILEP TD:-  GRPIND  IN('");
                while (ag.next()) {

                    String t = ag.getString(1);
                    System.out.print(t + " , ");
                    try {
                        String TEST_ID = ag.getString(1);
                        bw.write(String.valueOf(TEST_ID));
                        System.out.print("  ");
                        bw.append("','");
                    } catch (Exception e) {
                        System.out.println("Error ......... writing to a file..... :P" + e);
                    }


                }
                bw.write(")");


                System.out.println("\n\n\nILEP IPD:- ");
                PreparedStatement stmt8 = connection.prepareStatement("SELECT DISTINCT GRPIND FROM " + CYCLE + "_L3" + BP + Device + VERSION + FLAVOUR + "_BL WHERE (svctypcd in('17','18','84')) AND (TEST_ID NOT IN(select distinct test_id from " + CYCLE + "_L3" + BP + Device + VERSION + FLAVOUR + "_RSLT ))");
                ag = stmt8.executeQuery();
                bw.write("\n\n\nILEP IPD:-  GRPIND  IN('");
                while (ag.next()) {

                    String t = ag.getString(1);
                    System.out.print(t + " , ");
                    try {
                        String TEST_ID = ag.getString(1);
                        bw.write(String.valueOf(TEST_ID));
                        System.out.print("  ");
                        bw.append("','");
                    } catch (Exception e) {
                        System.out.println("Error ......... writing to a file..... :P" + e);
                    }


                }
                bw.write(")");
                bw.close();
                break;


            case (("DMGD")):
                if (BP.equalsIgnoreCase("C")) {
                    System.out.println("DMGD Regular:- ");
                    PreparedStatement stmt9 = connection.prepareStatement("SELECT DISTINCT TEST_ID FROM " + CYCLE + "_L3" + BP + Device + VERSION + FLAVOUR + "_BL WHERE (TEST_ID NOT IN(select distinct test_id from " + CYCLE + "_L3" + BP + Device + VERSION + FLAVOUR + "_RSLT ))");
                    ag = stmt9.executeQuery();
                    bw.write("\n\n\nDMGD REG:-  TEST_ID IN(");
                    while (ag.next()) {

                        String t = ag.getString(1);
                        System.out.print(t + " , ");
                        try {
                            String TEST_ID = ag.getString(1);
                            bw.write(String.valueOf(TEST_ID));
                            System.out.print("  ");
                            bw.append(",");
                            count++;
                        } catch (Exception e) {
                            System.out.println("Error ......... writing to a file..... :P" + e);
                        }


                    }
                    bw.write(")");
                } else {
                    System.out.println("DMGD Regular:- ");
                    PreparedStatement stmt9 = connection.prepareStatement("SELECT DISTINCT TEST_ID FROM " + CYCLE + "_L3" + BP + Device + VERSION + FLAVOUR + "_BL WHERE  (SP_flg ='N' or SP_FLG is null) AND  (TEST_ID NOT IN(select distinct test_id from " + CYCLE + "_L3" + BP + Device + VERSION + FLAVOUR + "_RSLT ))");
                    ag = stmt9.executeQuery();
                    bw.write("\n\n\nDMGD REG:-  TEST_ID IN(");
                    while (ag.next()) {

                        String t = ag.getString(1);
                        System.out.print(t + " , ");
                        try {
                            String TEST_ID = ag.getString(1);
                            bw.write(String.valueOf(TEST_ID));
                            System.out.print("  ");
                            bw.append(",");
                            count++;
                        } catch (Exception e) {
                            System.out.println("Error ......... writing to a file..... :P" + e);
                        }


                    }
                    bw.write(")");


                    System.out.println("DMGD SMARTPOST(FXG):- ");
                    PreparedStatement stmt10 = connection.prepareStatement("SELECT DISTINCT TEST_ID FROM " + CYCLE + "_L3" + BP + Device + VERSION + FLAVOUR + "_BL WHERE  SP_FLG='Y' and (SP_PKUP_CARRIER='FXG' or SP_PKUP_CARRIER='G') AND (TEST_ID NOT IN(select distinct test_id from " + CYCLE + "_L3" + BP + Device + VERSION + FLAVOUR + "_RSLT ))");
                    ag = stmt10.executeQuery();
                    bw.write("\n\n\nDMGD SMARTRPOST(FXG):-  TEST_ID IN(");
                    while (ag.next()) {

                        String t = ag.getString(1);
                        System.out.print(t + " , ");
                        try {
                            String TEST_ID = ag.getString(1);
                            bw.write(String.valueOf(TEST_ID));
                            System.out.print("  ");
                            bw.append(",");
                            count++;
                        } catch (Exception e) {
                            System.out.println("Error ......... writing to a file..... :P" + e);
                        }


                    }
                    bw.write(")");


                    System.out.println("DMGD SMARTPOST(FXSP):- ");
                    PreparedStatement stmt11 = connection.prepareStatement("SELECT DISTINCT TEST_ID FROM " + CYCLE + "_L3" + BP + Device + VERSION + FLAVOUR + "_BL WHERE  SP_FLG='Y' and (SP_PKUP_CARRIER='FXSP' or SP_PKUP_CARRIER='S') AND (TEST_ID NOT IN(select distinct test_id from " + CYCLE + "_L3" + BP + Device + VERSION + FLAVOUR + "_RSLT ))");
                    ag = stmt11.executeQuery();
                    bw.write("\n\n\nDMGD SMARTRPOST(FXSP):-  TEST_ID IN(");
                    while (ag.next()) {

                        String t = ag.getString(1);
                        System.out.print(t + " , ");
                        try {
                            String TEST_ID = ag.getString(1);
                            bw.write(String.valueOf(TEST_ID));
                            System.out.print("  ");
                            bw.append(",");
                            count++;
                        } catch (Exception e) {
                            System.out.println("Error ......... writing to a file..... :P" + e);
                        }


                    }
                    bw.write(")");
                }

                bw.close();
                break;


            case ("DMEP"):
                System.out.println("DMEP DangGoods:- ");
                PreparedStatement stmt11 = connection.prepareStatement("SELECT DISTINCT TEST_ID FROM " + CYCLE + "_L3" + BP + Device + VERSION + FLAVOUR + "_BL WHERE  (DANGGOODSFLG='A' or DANGGOODSFLG='I') AND (TEST_ID NOT IN(select distinct test_id from " + CYCLE + "_L3" + BP + Device + VERSION + FLAVOUR + "_RSLT ))");
                ag = stmt11.executeQuery();
                bw.write("\n\n\nDMGD DangGoods:-  TEST_ID IN(");
                while (ag.next()) {

                    String t = ag.getString(1);
                    System.out.print(t + " , ");
                    try {
                        String TEST_ID = ag.getString(1);
                        bw.write(String.valueOf(TEST_ID));
                        System.out.print("  ");
                        bw.append(",");
                        count++;
                    } catch (Exception e) {
                        System.out.println("Error ......... writing to a file..... :P" + e);
                    }


                }
                bw.write(")");


                System.out.println("DMEP Regular:- ");
                PreparedStatement stmt12 = connection.prepareStatement("SELECT DISTINCT TEST_ID FROM " + CYCLE + "_L3" + BP + Device + VERSION + FLAVOUR + "_BL WHERE (DANGGOODSFLG is null or DANGGOODSFLG ='N') AND  (TEST_ID NOT IN(select distinct test_id from " + CYCLE + "_L3" + BP + Device + VERSION + FLAVOUR + "_RSLT ))");
                ag = stmt12.executeQuery();
                bw.write("\n\n\nDMEP REG:-  TEST_ID IN(");
                while (ag.next()) {

                    String t = ag.getString(1);
                    System.out.print(t + " , ");
                    try {
                        String TEST_ID = ag.getString(1);
                        bw.write(String.valueOf(TEST_ID));
                        System.out.print("  ");
                        bw.append(",");
                        count++;
                    } catch (Exception e) {
                        System.out.println("Error ......... writing to a file..... :P" + e);
                    }


                }
                bw.write(")");

                bw.close();
                break;
        }
    }
}