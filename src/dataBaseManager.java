import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.List;

public class dataBaseManager {
    public static void main(String args[]){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/finInfo?useUnicode=true&characterEncoding=utf8","root","Aa!15268508919");
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from stockTrend");
            OLSMultipleLinearRegression mlr = new OLSMultipleLinearRegression();

            int i=0;
            while (rs.next()){
                i++;
            }
            double []returnRates = new double[i+1];
            double [][] p = new double[i+1][3];
            Statement st1 = conn.createStatement();
            ResultSet rs1 = st1.executeQuery("select * from stockTrend");
             int z=0;
            while(rs1.next())
            {
                double returnRate = Double.parseDouble(rs1.getString(6));
                returnRates[z]=returnRate;
                z++;
            }
            File file = new File("Parameters.csv");
            FileReader fileReader = new FileReader(file);
            CSVReader csvReader = new CSVReader(fileReader);
            List<String[]> list = csvReader.readAll();
            int j=0;
            for (String[] strs : list ){
                  p[j]=new double[]{Double.parseDouble(strs[0])/100.0,Double.parseDouble(strs[1])/100.0,Double.parseDouble(strs[2])/100.0};
                  j++;
            }
            conn.close();
            mlr.newSampleData(returnRates,p);
            double [] params = mlr.estimateRegressionParameters();
            for (double param:params){
                System.out.print(param+" ");
            }

        } catch (ClassNotFoundException | SQLException | FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvException e) {
            e.printStackTrace();
        }


    }
}
