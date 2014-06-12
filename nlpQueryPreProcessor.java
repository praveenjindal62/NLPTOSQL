
import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class QueryPreProcessor {

    public static String[][] ReplacedContent;
    public static int items;

    public static String[][] getTableColumnData(String dbName, String driver, String userName, String password) {
        String[][] data;

        /*
         * This data correspond to total number of table
         * total number of column available in each table
         */
        String url = "jdbc:mysql://localhost:3306/";
        /*String dbName = "classicmodels";
         String driver = "com.mysql.jdbc.Driver";
         String userName = "root"; 
         String password = "";*/
        String temp = "";
        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, password);
            String[] types = {"TABLE"};
            ResultSet res = conn.getMetaData().getTables(dbName, null, "%", types);
            while (res.next()) {
                String tableName = res.getString(3);
                temp += tableName.toLowerCase();
                ResultSet resColumn = conn.getMetaData().getColumns(dbName, null, tableName, "%");
                //System.out.println(temp);
                while (resColumn.next()) {
                    temp += " " + resColumn.getString(4).toLowerCase();
                    //System.out.print(resColumn.getString(4)+"  ");
                }
                temp += "-";
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String tempTable[] = temp.split("-");
        int table = tempTable.length;
        int[] column = new int[table];
        for (int i = 0; i < table; i++) {
            column[i] = tempTable[i].split(" ").length;
        }

        data = new String[table][];//4 correspond to total number of tables in database

        for (int i = 0; i < table; i++) {
            data[i] = new String[column[i]];
            String[] tempColumn = tempTable[i].split(" ");
            for (int j = 0; j < column[i]; j++) {
                data[i][j] = tempColumn[j];
                //System.out.println(data[i][j]);
            }
        }

        return data;
    }

    public static String preProcessor(String query, String dbName, String driver, String username, String password) {
        ReplacedContent = new String[10][2];
        items = 0;
        String processedQuery = "";
        String[][] data = getTableColumnData(dbName, driver, username, password);
        String[] queryToken = query.split(" ");
        int queryLength = queryToken.length;
        int tindex = 1, cindex = 1;
        for (int i = 0; i < queryLength; i++) {
            String temp = queryToken[i];
            //System.out.println(temp);
            for (int j = 0; j < data.length; j++) {
                boolean st = true;
                for (int k = 0; k < data[j].length; k++) {
                    //System.out.println("##"+data[j][k]);
                    if (temp.compareTo(data[j][k]) == 0) {
                        if (k == 0) {
                            queryToken[i] = "table" + tindex;
                            tindex++;
                            ReplacedContent[items][0] = temp;
                            ReplacedContent[items][1] = queryToken[i];
                        } else {
                            queryToken[i] = "column" + cindex;
                            cindex++;
                            ReplacedContent[items][0] = temp;
                            ReplacedContent[items][1] = queryToken[i];
                        }
                        items++;
                        st = false;
                        break;
                    }
                }
                if (!st) {
                    break;
                }
            }
            processedQuery += queryToken[i] + " ";
        }

        //For Identifying Values
        Pattern pattern = Pattern.compile("'(.*?)'");
        Matcher matcher = pattern.matcher(processedQuery);
        int i = 0;
        while (matcher.find()) {
            String temp = matcher.group(1);
            processedQuery = processedQuery.replace("'" + temp + "'", "value" + i);
            ReplacedContent[items][0] = temp;
            ReplacedContent[items][1] = "value" + i;
            i++;
            items++;
        }

        return processedQuery;
    }

    public static void main(String args[]) {
        String query[] = {"show me phone city customerName of customers",
            "display all employee",
            "list salary of all employee",
            "what are our orders",
            "what are the salary of our employee",
            "tell me fname and lname of all employee"
        };
        System.out.println("Module Start");
        int n = query.length;
        for (int i = 0; i < n; i++) {
            System.out.println(preProcessor(query[i], "classicmodels", "com.mysql.jdbc.Driver", "root", ""));
            for (int j = 0; j < items; j++) {
                System.out.println("######" + ReplacedContent[j][0] + "-->>" + ReplacedContent[j][1]);
            }
        }
    }
}
