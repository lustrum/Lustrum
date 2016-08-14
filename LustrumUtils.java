package lustrum;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;


public class LustrumUtils {
    
    public boolean login(String username, String password)
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection(
						"jdbc:mysql://localhost/Lustrum", "root", "");
				Statement st = con.createStatement();
				ResultSet resultset;
				resultset = st
						.executeQuery("select password from userDetails where username='"
								+ username + "'");
				if (resultset.next()) {
					String pass = resultset.getString(1);
                                         System.out.println("pass: "+pass);
                                        if (password.equals(resultset.getString(1)))
                                            return true;
                                        else
                                            return false;
                                }
                                else
                                    return false;
        
        }
        catch(Exception e)
        {
           System.out.println("Exception");
           e.printStackTrace();
           return false;
        }
    }
    
    public boolean createSensorData()
    {
        
    try
			{
				
				Class.forName("com.mysql.jdbc.Driver");
	        	Connection conn = DriverManager.getConnection(
	        			"jdbc:mysql://localhost/lustrum", "root", "");
	    		
	        	/// logic to retrieve sensor data and generate notification
	        	Statement st = conn.createStatement();
				ResultSet resultset;
				
				
				
				resultset = st
						.executeQuery("select * from sensorData");
				if (resultset.next()) {
					st = conn.createStatement();
				      String sql = "DELETE FROM sensorData ";
				      st.executeUpdate(sql);
				}
				
				
				
				
				
				PreparedStatement pstatement = null;

				
				for(int i = 0;i<100;i++)
				{
					double temp[] = generateSensorData();
					String queryString = "INSERT INTO sensorData(SpeedofCar,Rain,HearRate,SleepHours,Temperature,id) VALUES (?,?,?,?,?,?)";
					pstatement = conn.prepareStatement(queryString);
					pstatement.setDouble(1,temp[0]);
					pstatement.setDouble(2, temp[1]);
					pstatement.setDouble(3, temp[2]);
					pstatement.setDouble(4, temp[3]);
					pstatement.setDouble(5, temp[4]);
					
					pstatement.setInt(6, i);

					int updateQuery = pstatement.executeUpdate();
                                        
                                     
				}
				return true;
			}
			catch(Exception e)
			{	
				e.printStackTrace();
				return false;
			}
		
	}
	
	double[] generateSensorData()
	{
		double [] temp = new double[5];
		temp[0] = generateRandomSpeedofCar();
		temp[1] = generateRandomRain();
		temp[2] = generaterandomHeartRate();
		temp[3] = generateRandomSleepHours();
		temp[4] = generateRandomTemperature();
                System.out.println(temp[1]);
		return temp;
	}
	
	 int generateRandomInt(int n)
	{
		Random r = new Random();
		return r.nextInt(n);
	}
	
	double generateRandomDouble(int n)
	{
		Random r = new Random();
		int a = (int) (r.nextFloat()*n * 100);
		double f = a/100.00;
		return f;
	}
	
	double generateRandomSpeedofCar()
	{

		return generateRandomDouble(200);
	}
	
	 double generateRandomRain()
	{
		return generateRandomInt(2);
	}
	
	double generaterandomHeartRate()
	{
		return generateRandomDouble(200);
	}
	
	 double generateRandomSleepHours()
	{
		return generateRandomDouble(12);
	}

	 double generateRandomTemperature()
	{
		return generateRandomDouble(50);
	}
        
    public boolean createAccount(String username, String password)
    {
        try {
		
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection(
				"jdbc:mysql://localhost/lustrum", "root", "");
		Statement st = con.createStatement();
		PreparedStatement pstatement = null;
		String queryString = "INSERT INTO userDetails(username,password) VALUES (?, ?)";
		pstatement = con.prepareStatement(queryString);
		pstatement.setString(1, username);
		pstatement.setString(2, password);



		int updateQuery = pstatement.executeUpdate();


		con.close();
		return true;
	} catch (Exception e) {
		e.printStackTrace();
		return false;
	}
    }
}
