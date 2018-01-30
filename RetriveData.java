


import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import oracle.jdbc.internal.OraclePreparedStatement;

public class RetriveData {
	 PreparedStatement preparedStatement = null;
	 Connection con= null;
     List businessCategoryName = new ArrayList();
     String sql=null;
     Statement statement=null;
	 public List getBusinessCategory() throws SQLException{
	 try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "system");
            String selectTableSQL = "SELECT mainCategoryName FROM mainCategories";
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery(selectTableSQL);
            while (rs.next()) {
            	businessCategoryName.add(rs.getString("mainCategoryName"));
            }
	 	} catch (ClassNotFoundException e) {
	 		e.printStackTrace();
	 	}finally{
	 		if(statement!=null){
	 			statement.close();
	 		}
	 		con.close();
	}
	return businessCategoryName;
	 }
	 
	 
	 public List getSubCategories(List mainCategoriesList, String operation) throws SQLException{
			List businessCategoryList= new ArrayList();
		 try {
	            Class.forName("oracle.jdbc.driver.OracleDriver");
	            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "system");
				ResultSet rs = null;
	            String selectSubCategorySQL ="";
	            if (mainCategoriesList.size()>0){
	            if(operation!= null && operation.equalsIgnoreCase("OR")){
	            	selectSubCategorySQL ="SELECT DISTINCT BUSINESSSUBCATEGORY from businessSubCategory where businessId IN"
	            			+ " (SELECT DISTINCT BUSINESSID FROM businessSubCategory WHERE BUSINESSMAINCATEGORY =  ";
					for (int i = 0; i < mainCategoriesList.size(); i++) {
						if (i == mainCategoriesList.size() - 1) {
							selectSubCategorySQL  = selectSubCategorySQL +"'" +mainCategoriesList.get(i) + "') ORDER BY BUSINESSSUBCATEGORY ASC";
						} else {
							selectSubCategorySQL  = selectSubCategorySQL +"'"+mainCategoriesList.get(i)+"'  OR BUSINESSMAINCATEGORY = ";
						}
					}
					statement = con.createStatement();
					rs = statement.executeQuery(selectSubCategorySQL);
					while (rs.next()) {
    					String businessCategory = rs.getString("BUSINESSSUBCATEGORY");
    					businessCategoryList.add(businessCategory);
    				}
	            }else{
	            	if(operation!= null && operation.equalsIgnoreCase("AND")){
	            		selectSubCategorySQL=selectSubCategorySQL ="SELECT DISTINCT BUSINESSSUBCATEGORY from businessSubCategory where businessId IN ( ";
	            		for (int i = 0; i < mainCategoriesList.size(); i++) {
	    					if (i == mainCategoriesList.size() - 1) {
	    						selectSubCategorySQL = selectSubCategorySQL+ "SELECT DISTINCT  BUSINESSID FROM businessSubCategory WHERE BUSINESSMAINCATEGORY = '"
	    								+ mainCategoriesList.get(i) + "') ORDER BY BUSINESSSUBCATEGORY ASC";
	    					} else {
	    						selectSubCategorySQL = selectSubCategorySQL+ "SELECT  DISTINCT BUSINESSID FROM businessSubCategory WHERE BUSINESSMAINCATEGORY = '"
	    								+ mainCategoriesList.get(i) + "' INTERSECT ";
	    					}
	    				}
	    				statement = con.createStatement();
	    				rs = statement.executeQuery(selectSubCategorySQL);
	    				while (rs.next()) {
	    					String businessCategory = rs.getString("BUSINESSSUBCATEGORY");
	    					businessCategoryList.add(businessCategory);
	    				}
	            	}
	            }
	            }
		 	} catch (ClassNotFoundException e) {
		 		e.printStackTrace();
		 	}finally{
		 		if(statement!=null){
		 			statement.close();
		 		}
		 		con.close();
		}
		return businessCategoryList;
		 
	 }
	 
	 
	


	public List getAttributes(List mainCategoryList, List subCategoryList,String operation) throws SQLException{
		List businessAttributeList= new ArrayList();
		 try {
	            Class.forName("oracle.jdbc.driver.OracleDriver");
	            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "system");
				ResultSet rs = null;
	            String selectAttributeSQL ="";
	            if (subCategoryList.size()>0){
	            if(operation!= null && operation.equalsIgnoreCase("OR")){
	            	selectAttributeSQL ="select DISTINCT ATTRIBUTESNAME || '_' || ATTRIBUTEVALUE AS attribute from"
							+ " businessAttribute where BUSINESSID IN ( ";
	            	selectAttributeSQL = selectAttributeSQL +"SELECT DISTINCT BUSINESSID FROM businessSubCategory WHERE BUSINESSMAINCATEGORY =  ";
					for (int i = 0; i < mainCategoryList.size(); i++) {
						if (i == mainCategoryList.size() - 1) {
							selectAttributeSQL  = selectAttributeSQL +"'" +mainCategoryList.get(i) + "'";
						} else {
							selectAttributeSQL  = selectAttributeSQL +"'"+mainCategoryList.get(i)+"'  OR BUSINESSMAINCATEGORY = ";
						}
					}
					selectAttributeSQL = selectAttributeSQL +" INTERSECT ";
	            	selectAttributeSQL = selectAttributeSQL +"SELECT DISTINCT BUSINESSID FROM businessSubCategory WHERE BUSINESSSUBCATEGORY =  ";
					
					for (int i = 0; i < subCategoryList.size(); i++) {
						if (i == subCategoryList.size() - 1) {
							selectAttributeSQL  = selectAttributeSQL +"'" +subCategoryList.get(i) + "'";
						} else {
							selectAttributeSQL  = selectAttributeSQL +"'"+subCategoryList.get(i)+"'  OR BUSINESSSUBCATEGORY = ";
						}
					}
					System.out.println("selectAttributeSQL"+selectAttributeSQL);
					selectAttributeSQL= selectAttributeSQL+" ) ORDER BY ATTRIBUTESNAME || '_' || ATTRIBUTEVALUE";
					statement = con.createStatement();
					rs = statement.executeQuery(selectAttributeSQL);
					while (rs.next()) {
						String attribute = rs.getString("attribute");
						businessAttributeList.add(attribute);
   				}
	            }else{
	            	if(operation!= null && operation.equalsIgnoreCase("AND")){
	            		selectAttributeSQL ="select DISTINCT ATTRIBUTESNAME || '_' || ATTRIBUTEVALUE AS attribute from"
								+ " businessAttribute where BUSINESSID IN (( ";
		            	for (int i = 0; i < mainCategoryList.size(); i++) {
	    					if (i == mainCategoryList.size() - 1) {
	    						selectAttributeSQL = selectAttributeSQL+ "SELECT DISTINCT  BUSINESSID FROM businessSubCategory WHERE BUSINESSMAINCATEGORY  = '"
	    								+ mainCategoryList.get(i) + "')";
	    					} else {
	    						selectAttributeSQL = selectAttributeSQL+ "SELECT  DISTINCT BUSINESSID FROM businessSubCategory WHERE BUSINESSMAINCATEGORY  = '"
	    								+ mainCategoryList.get(i) + "' INTERSECT ";
	    					}
	    				}
						selectAttributeSQL = selectAttributeSQL +" INTERSECT (";
		            	for (int i = 0; i < subCategoryList.size(); i++) {
	    					if (i == subCategoryList.size() - 1) {
	    						selectAttributeSQL = selectAttributeSQL+ "SELECT DISTINCT  BUSINESSID FROM businessSubCategory WHERE BUSINESSSUBCATEGORY = '"
	    								+ subCategoryList.get(i) + "')";
	    					} else {
	    						selectAttributeSQL = selectAttributeSQL+ "SELECT  DISTINCT BUSINESSID FROM businessSubCategory WHERE BUSINESSSUBCATEGORY = '"
	    								+ subCategoryList.get(i) + "' INTERSECT ";
	    					}
	    				}
						selectAttributeSQL= selectAttributeSQL+") ";
						System.out.println("selectAttributeSQL"+selectAttributeSQL);

						statement = con.createStatement();
						rs = statement.executeQuery(selectAttributeSQL);
						while (rs.next()) {
							String attribute = rs.getString("attribute");
							businessAttributeList.add(attribute);
						}
	            	}
	            }
	          }
		 	} catch (ClassNotFoundException e) {
		 		e.printStackTrace();
		 	}finally{
		 		if(statement!=null){
		 			statement.close();
		 		}
		 		con.close();
		}
		return businessAttributeList;
		 
		
	}


	public List getDayOfTheWeek(List mainCategoryList, List subCategoryList,List attributeList, String operation) throws SQLException {
		List dayOfTheWeekList= new ArrayList();
		 try {
	            Class.forName("oracle.jdbc.driver.OracleDriver");
	            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "system");
				ResultSet rs = null;
	            String selectDaySQL ="";
	        if(mainCategoryList.size()>0){
	            if(operation!= null && operation.equalsIgnoreCase("OR")){
	            	selectDaySQL ="select DISTINCT BUSINESSDAY from businesstiming where BUSINESSID IN ( ";
	            	selectDaySQL = selectDaySQL +"SELECT DISTINCT BUSINESSID FROM businessSubCategory WHERE BUSINESSMAINCATEGORY =  ";
					
		            	for (int i = 0; i < mainCategoryList.size(); i++) {
							if (i == mainCategoryList.size() - 1) {
								selectDaySQL  = selectDaySQL +"'" +mainCategoryList.get(i) + "'";
							} else {
								selectDaySQL  = selectDaySQL +"'"+mainCategoryList.get(i)+"'  OR BUSINESSMAINCATEGORY = ";
							}
						}
						if(subCategoryList.size()>0){
							selectDaySQL = selectDaySQL +" INTERSECT (";
							selectDaySQL = selectDaySQL +"SELECT DISTINCT BUSINESSID FROM businessSubCategory WHERE BUSINESSSUBCATEGORY =  ";
						
						for (int i = 0; i < subCategoryList.size(); i++) {
							if (i == subCategoryList.size() - 1) {
								selectDaySQL  = selectDaySQL+"'" +subCategoryList.get(i) + "')";
							} else {
								selectDaySQL  = selectDaySQL +"'"+subCategoryList.get(i)+"'  OR BUSINESSSUBCATEGORY = ";
							}
						}
						}
						if(attributeList.size()>0){
							selectDaySQL = selectDaySQL +" INTERSECT (";
							selectDaySQL = selectDaySQL +"SELECT DISTINCT BUSINESSID FROM businessAttribute "
									+ "WHERE ATTRIBUTESNAME || '_' || ATTRIBUTEVALUE =  ";
						
						for (int i = 0; i < attributeList.size(); i++) {
							if (i == attributeList.size() - 1) {
								selectDaySQL  = selectDaySQL+"'" +attributeList.get(i) + "')";
							} else {
								selectDaySQL  = selectDaySQL +"'"+attributeList.get(i)+"'  OR ATTRIBUTESNAME || '_' || ATTRIBUTEVALUE = ";
							}
						}
						}	
						
						
						selectDaySQL= selectDaySQL+" ) ORDER BY BUSINESSDAY";
						
					statement = con.createStatement();
					
					rs = statement.executeQuery(selectDaySQL);
					while (rs.next()) {
						String day = rs.getString("BUSINESSDAY");
						dayOfTheWeekList.add(day);
					}
						
	            }else{
	            	if(operation!= null && operation.equalsIgnoreCase("AND")){
		            	selectDaySQL ="select DISTINCT BUSINESSDAY from businesstiming where BUSINESSID IN (( ";
		            	for (int i = 0; i < mainCategoryList.size(); i++) {
	    					if (i == mainCategoryList.size() - 1) {
	    						selectDaySQL = selectDaySQL+ "SELECT DISTINCT  BUSINESSID FROM businessSubCategory WHERE BUSINESSMAINCATEGORY =  '"
	    								+ mainCategoryList.get(i) + "')";
	    					} else {
	    						selectDaySQL = selectDaySQL+ "SELECT  DISTINCT BUSINESSID FROM businessSubCategory WHERE BUSINESSMAINCATEGORY =  '"
	    								+ mainCategoryList.get(i) + "' INTERSECT ";
	    					}
	    				}
						if(subCategoryList.size()>0){

			            	selectDaySQL = selectDaySQL +" INTERSECT (";
			            	for (int i = 0; i < subCategoryList.size(); i++) {
		    					if (i == subCategoryList.size() - 1) {
		    						selectDaySQL = selectDaySQL+ "SELECT DISTINCT  BUSINESSID FROM businessSubCategory WHERE BUSINESSSUBCATEGORY = '"
		    								+ subCategoryList.get(i) + "')";
		    					} else {
		    						selectDaySQL = selectDaySQL+ "SELECT  DISTINCT BUSINESSID FROM businessSubCategory WHERE BUSINESSSUBCATEGORY = '"
		    								+ subCategoryList.get(i) + "' INTERSECT ";
		    					}
		    				}
						}
						if(attributeList.size()>0){
							selectDaySQL = selectDaySQL +" INTERSECT (";
			            	for (int i = 0; i < attributeList.size(); i++) {
		    					if (i == attributeList.size() - 1) {
		    						selectDaySQL = selectDaySQL+ "SELECT DISTINCT BUSINESSID FROM businessAttribute "
								+ "WHERE ATTRIBUTESNAME || '_' || ATTRIBUTEVALUE = '"
		    								+ attributeList.get(i) + "')";
		    					} else {
		    						selectDaySQL = selectDaySQL+ "SELECT DISTINCT BUSINESSID FROM businessAttribute "
								+ "WHERE ATTRIBUTESNAME || '_' || ATTRIBUTEVALUE = '"
		    								+ attributeList.get(i) + "' INTERSECT ";
		    					}
		    				}
						}

						selectDaySQL=selectDaySQL+") ORDER BY BUSINESSDAY";
						
						
		            	statement = con.createStatement();
						rs = statement.executeQuery(selectDaySQL);
						while (rs.next()) {
							String day = rs.getString("BUSINESSDAY");
							dayOfTheWeekList.add(day);
	  				}
	            	}
	            }	
	            }
		 	} catch (ClassNotFoundException e) {
		 		e.printStackTrace();
		 	}finally{
		 		if(statement!=null){
		 			statement.close();
		 		}
		 		con.close();
		}
		return dayOfTheWeekList;
		 
		
	}
	
	public List getFromHours(List mainCategoryList, List subCategoryList,List attributeList, String operation) throws SQLException {
		
		List toList= new ArrayList();
		 try {
	            Class.forName("oracle.jdbc.driver.OracleDriver");
	            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "system");
				ResultSet rs = null;
	            String selectDaySQL ="";
		  if(mainCategoryList.size()>0){
       
	            if(operation!= null && operation.equalsIgnoreCase("OR")){
	            	selectDaySQL ="select DISTINCT OPENTIMING from businesstiming where BUSINESSID IN ( ";
	            	selectDaySQL = selectDaySQL +"SELECT DISTINCT BUSINESSID FROM businessSubCategory WHERE BUSINESSMAINCATEGORY =  ";
					for (int i = 0; i < mainCategoryList.size(); i++) {
						if (i == mainCategoryList.size() - 1) {
							selectDaySQL  = selectDaySQL +"'" +mainCategoryList.get(i) + "'";
						} else {
							selectDaySQL  = selectDaySQL +"'"+mainCategoryList.get(i)+"'  OR BUSINESSMAINCATEGORY = ";
						}
					}
					if(subCategoryList.size()>0){
						selectDaySQL = selectDaySQL +" INTERSECT (";
						selectDaySQL = selectDaySQL +"SELECT DISTINCT BUSINESSID FROM businessSubCategory WHERE BUSINESSSUBCATEGORY =  ";
					
					for (int i = 0; i < subCategoryList.size(); i++) {
						if (i == subCategoryList.size() - 1) {
							selectDaySQL  = selectDaySQL+"'" +subCategoryList.get(i) + "')";
						} else {
							selectDaySQL  = selectDaySQL +"'"+subCategoryList.get(i)+"'  OR BUSINESSSUBCATEGORY = ";
						}
					}
					}
					if(attributeList.size()>0){
						selectDaySQL = selectDaySQL +" INTERSECT (";
						selectDaySQL = selectDaySQL +"SELECT DISTINCT BUSINESSID FROM businessAttribute "
								+ "WHERE ATTRIBUTESNAME || '_' || ATTRIBUTEVALUE =  ";
					
					for (int i = 0; i < attributeList.size(); i++) {
						if (i == attributeList.size() - 1) {
							selectDaySQL  = selectDaySQL+"'" +attributeList.get(i) + "')";
						} else {
							selectDaySQL  = selectDaySQL +"'"+attributeList.get(i)+"'  OR ATTRIBUTESNAME || '_' || ATTRIBUTEVALUE = ";
						}
					}
					}
					
					
					selectDaySQL= selectDaySQL+" ) ORDER BY OPENTIMING ASC";
					statement = con.createStatement();
					rs = statement.executeQuery(selectDaySQL);
					while (rs.next()) {
						double toHours = rs.getDouble("OPENTIMING");
						toList.add(toHours);
  				}
	            }else{
	            	if(operation!= null && operation.equalsIgnoreCase("AND")){
		            	selectDaySQL ="select DISTINCT OPENTIMING from businesstiming where BUSINESSID IN (( ";
		            	for (int i = 0; i < mainCategoryList.size(); i++) {
	    					if (i == mainCategoryList.size() - 1) {
	    						selectDaySQL = selectDaySQL+ "SELECT DISTINCT  BUSINESSID FROM businessSubCategory WHERE BUSINESSMAINCATEGORY  = '"
	    								+ mainCategoryList.get(i) + "')";
	    					} else {
	    						selectDaySQL = selectDaySQL+ "SELECT  DISTINCT BUSINESSID FROM businessSubCategory WHERE BUSINESSMAINCATEGORY  = '"
	    								+ mainCategoryList.get(i) + "' INTERSECT ";
	    					}
	    				}
						if(subCategoryList.size()>0){

			            	selectDaySQL = selectDaySQL +" INTERSECT (";
			            	for (int i = 0; i < subCategoryList.size(); i++) {
		    					if (i == subCategoryList.size() - 1) {
		    						selectDaySQL = selectDaySQL+ "SELECT DISTINCT  BUSINESSID FROM businessSubCategory WHERE BUSINESSSUBCATEGORY = '"
		    								+ subCategoryList.get(i) + "')";
		    					} else {
		    						selectDaySQL = selectDaySQL+ "SELECT  DISTINCT BUSINESSID FROM businessSubCategory WHERE BUSINESSSUBCATEGORY = '"
		    								+ subCategoryList.get(i) + "' INTERSECT ";
		    					}
		    				}
						}
						if(attributeList.size()>0){
							selectDaySQL = selectDaySQL +" INTERSECT (";
			            	for (int i = 0; i < attributeList.size(); i++) {
		    					if (i == attributeList.size() - 1) {
		    						selectDaySQL = selectDaySQL+ "SELECT DISTINCT BUSINESSID FROM businessAttribute "
								+ "WHERE ATTRIBUTESNAME || '_' || ATTRIBUTEVALUE = '"
		    								+ attributeList.get(i) + "')";
		    					} else {
		    						selectDaySQL = selectDaySQL+ "SELECT DISTINCT BUSINESSID FROM businessAttribute "
								+ "WHERE ATTRIBUTESNAME || '_' || ATTRIBUTEVALUE = '"
		    								+ attributeList.get(i) + "' INTERSECT ";
		    					}
		    				}
						}
							
						
		            	selectDaySQL= selectDaySQL+" ) ORDER BY OPENTIMING ASC";
		            	statement = con.createStatement();
						rs = statement.executeQuery(selectDaySQL);
						while (rs.next()) {
							double toHours = rs.getDouble("OPENTIMING");
							toList.add(toHours);
	  				}
	            	}
	            }
		      }
		 	} catch (ClassNotFoundException e) {
		 		e.printStackTrace();
		 	}finally{
		 		if(statement!=null){
		 			statement.close();
		 		}
		 		con.close();
		}
		return toList;
		 
		
	}


	public List getToHours(List mainCategoryList, List subCategoryList,List attributeList, String operation) throws SQLException {
		List toList= new ArrayList();
		 try {
	            Class.forName("oracle.jdbc.driver.OracleDriver");
	            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "system");
				ResultSet rs = null;
	            String selectDaySQL ="";
		        if(mainCategoryList.size()>0){

	            if(operation!= null && operation.equalsIgnoreCase("OR")){
	            	selectDaySQL ="select DISTINCT CLOSETIMING from businesstiming where BUSINESSID IN ( ";
	            	selectDaySQL = selectDaySQL +"SELECT DISTINCT BUSINESSID FROM businessSubCategory WHERE BUSINESSMAINCATEGORY =  ";
					for (int i = 0; i < mainCategoryList.size(); i++) {
						if (i == mainCategoryList.size() - 1) {
							selectDaySQL  = selectDaySQL +"'" +mainCategoryList.get(i) + "'";
						} else {
							selectDaySQL  = selectDaySQL +"'"+mainCategoryList.get(i)+"'  OR BUSINESSMAINCATEGORY = ";
						}
					}
					if(subCategoryList.size()>0){
						selectDaySQL = selectDaySQL +" INTERSECT (";
						selectDaySQL = selectDaySQL +"SELECT DISTINCT BUSINESSID FROM businessSubCategory WHERE BUSINESSSUBCATEGORY =  ";
					
					for (int i = 0; i < subCategoryList.size(); i++) {
						if (i == subCategoryList.size() - 1) {
							selectDaySQL  = selectDaySQL+"'" +subCategoryList.get(i) + "')";
						} else {
							selectDaySQL  = selectDaySQL +"'"+subCategoryList.get(i)+"'  OR BUSINESSSUBCATEGORY = ";
						}
					}
					}
					if(attributeList.size()>0){
						selectDaySQL = selectDaySQL +" INTERSECT (";
						selectDaySQL = selectDaySQL +"SELECT DISTINCT BUSINESSID FROM businessAttribute "
								+ "WHERE ATTRIBUTESNAME || '_' || ATTRIBUTEVALUE =  ";
					
					for (int i = 0; i < attributeList.size(); i++) {
						if (i == attributeList.size() - 1) {
							selectDaySQL  = selectDaySQL+"'" +attributeList.get(i) + "')";
						} else {
							selectDaySQL  = selectDaySQL +"'"+attributeList.get(i)+"'  OR ATTRIBUTESNAME || '_' || ATTRIBUTEVALUE = ";
						}
					}
					}
					
					
					selectDaySQL= selectDaySQL+" ) ORDER BY CLOSETIMING DESC";
					statement = con.createStatement();
					rs = statement.executeQuery(selectDaySQL);
					while (rs.next()) {
						double toHours = rs.getDouble("CLOSETIMING");
						toList.add(toHours);
  				}
	            }else{
	            	if(operation!= null && operation.equalsIgnoreCase("AND")){
		            	selectDaySQL ="select DISTINCT CLOSETIMING from businesstiming where BUSINESSID IN ( (";
		            	for (int i = 0; i < mainCategoryList.size(); i++) {
	    					if (i == mainCategoryList.size() - 1) {
	    						selectDaySQL = selectDaySQL+ "SELECT DISTINCT  BUSINESSID FROM businessSubCategory WHERE BUSINESSMAINCATEGORY = '"
	    								+ mainCategoryList.get(i) + "')";
	    					} else {
	    						selectDaySQL = selectDaySQL+ "SELECT  DISTINCT BUSINESSID FROM businessSubCategory WHERE BUSINESSMAINCATEGORY = '"
	    								+ mainCategoryList.get(i) + "' INTERSECT ";
	    					}
	    				}
						if(subCategoryList.size()>0){

			            	selectDaySQL = selectDaySQL +" INTERSECT (";
			            	for (int i = 0; i < subCategoryList.size(); i++) {
		    					if (i == subCategoryList.size() - 1) {
		    						selectDaySQL = selectDaySQL+ "SELECT DISTINCT  BUSINESSID FROM businessSubCategory WHERE BUSINESSSUBCATEGORY = '"
		    								+ subCategoryList.get(i) + "')";
		    					} else {
		    						selectDaySQL = selectDaySQL+ "SELECT  DISTINCT BUSINESSID FROM businessSubCategory WHERE BUSINESSSUBCATEGORY = '"
		    								+ subCategoryList.get(i) + "' INTERSECT ";
		    					}
		    				}
						}
		            	selectDaySQL= selectDaySQL+" ) ORDER BY CLOSETIMING DESC";
		            	statement = con.createStatement();
						rs = statement.executeQuery(selectDaySQL);
						while (rs.next()) {
							double toHours = rs.getDouble("CLOSETIMING");
							toList.add(toHours);
	  				}
	            	}
	            }
	            }
		 	} catch (ClassNotFoundException e) {
		 		e.printStackTrace();
		 	}finally{
		 		if(statement!=null){
		 			statement.close();
		 		}
		 		con.close();
		}
		return toList;
	}


	public List getLocation(List mainCategoryList, List subCategoryList,List attributeList, String operation) throws SQLException {
		List locationList= new ArrayList();
		 try {
	            Class.forName("oracle.jdbc.driver.OracleDriver");
	            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "system");
				ResultSet rs = null;
	            String selectLocationSQL ="";
		        if(mainCategoryList.size()>0){

	            if(operation!= null && operation.equalsIgnoreCase("OR")){
	            	selectLocationSQL ="SELECT DISTINCT  CITY || '_' || STATE AS location from  business where BUSINESSID IN ( ";
	            	selectLocationSQL = selectLocationSQL +"SELECT DISTINCT BUSINESSID FROM businessSubCategory WHERE BUSINESSMAINCATEGORY =  ";
					for (int i = 0; i < mainCategoryList.size(); i++) {
						if (i == mainCategoryList.size() - 1) {
							selectLocationSQL  = selectLocationSQL +"'" +mainCategoryList.get(i) + "'";
						} else {
							selectLocationSQL  = selectLocationSQL +"'"+mainCategoryList.get(i)+"'  OR BUSINESSMAINCATEGORY = ";
						}
					}
					if(subCategoryList.size()>0){
						selectLocationSQL = selectLocationSQL +" INTERSECT (";
						selectLocationSQL = selectLocationSQL +"SELECT DISTINCT BUSINESSID FROM businessSubCategory WHERE BUSINESSSUBCATEGORY =  ";
					
					for (int i = 0; i < subCategoryList.size(); i++) {
						if (i == subCategoryList.size() - 1) {
							selectLocationSQL  = selectLocationSQL+"'" +subCategoryList.get(i) + "')";
						} else {
							selectLocationSQL  = selectLocationSQL +"'"+subCategoryList.get(i)+"'  OR BUSINESSSUBCATEGORY = ";
						}
					}
					}
					selectLocationSQL= selectLocationSQL+" ) ORDER BY location ASC";
					statement = con.createStatement();
					rs = statement.executeQuery(selectLocationSQL);
					while (rs.next()) {
						String location = rs.getString("location");
						locationList.add(location);
 				}
	            }else{
	            	if(operation!= null && operation.equalsIgnoreCase("AND")){
	            		selectLocationSQL ="SELECT DISTINCT CITY || '_' || STATE AS location from  business where BUSINESSID IN ( ( ";
		            	for (int i = 0; i < mainCategoryList.size(); i++) {
	    					if (i == mainCategoryList.size() - 1) {
	    						selectLocationSQL = selectLocationSQL+ "SELECT DISTINCT  BUSINESSID FROM businessSubCategory WHERE BUSINESSMAINCATEGORY  = '"
	    								+ mainCategoryList.get(i) + "')";
	    					} else {
	    						selectLocationSQL = selectLocationSQL+ "SELECT  DISTINCT BUSINESSID FROM businessSubCategory WHERE BUSINESSMAINCATEGORY = '"
	    								+ mainCategoryList.get(i) + "' INTERSECT ";
	    					}
	    				}
						if(subCategoryList.size()>0){

							selectLocationSQL = selectLocationSQL +" INTERSECT (";
			            	for (int i = 0; i < subCategoryList.size(); i++) {
		    					if (i == subCategoryList.size() - 1) {
		    						selectLocationSQL = selectLocationSQL+ "SELECT DISTINCT  BUSINESSID FROM businessSubCategory WHERE BUSINESSSUBCATEGORY = '"
		    								+ subCategoryList.get(i) + "')";
		    					} else {
		    						selectLocationSQL = selectLocationSQL+ "SELECT  DISTINCT BUSINESSID FROM businessSubCategory WHERE BUSINESSSUBCATEGORY = '"
		    								+ subCategoryList.get(i) + "' INTERSECT ";
		    					}
		    				}
						}
						selectLocationSQL= selectLocationSQL+" ) ORDER BY location ASC";
		            	statement = con.createStatement();
						rs = statement.executeQuery(selectLocationSQL);
						while (rs.next()) {
							String location = rs.getString("location");
							locationList.add(location);
	 				}
	            	}
	            }
		        }
		 	} catch (ClassNotFoundException e) {
		 		e.printStackTrace();
		 	}finally{
		 		if(statement!=null){
		 			statement.close();
		 		}
		 		con.close();
		}
		return locationList;
	}

	public List getBusinessData(List mainCategoryList, List subCategoryList,List attributeList, String operation 
			,double toHours,double fromHours, String dayOfTheWeek,String location) throws SQLException {
		List businessDataList= new ArrayList();
		if(location!=null && location.equalsIgnoreCase("Any Location")){
	    	location = null;
	    }
	    if(dayOfTheWeek!=null && dayOfTheWeek.equalsIgnoreCase("Any Day")){
	    	dayOfTheWeek =  null;
	    }
		 try {
	            Class.forName("oracle.jdbc.driver.OracleDriver");
	            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "system");
				ResultSet rs = null;
	            String businessDataSQL ="";
		        if(mainCategoryList.size()>0){

	            if(operation!= null && operation.equalsIgnoreCase("OR")){
	            	businessDataSQL ="select DISTINCT b.businessID, b.BUSINESSNAME, b.CITY , b.STATE,b.STARS,b.REVIEWCOUNT"
	            			+ " from business b,businesstiming t"
					+ " where b.BUSINESSID IN (";
	            	businessDataSQL = businessDataSQL +"SELECT DISTINCT BUSINESSID FROM businessSubCategory WHERE BUSINESSMAINCATEGORY =  ";
					for (int i = 0; i < mainCategoryList.size(); i++) {
						if (i == mainCategoryList.size() - 1) {
							businessDataSQL  = businessDataSQL +"'" +mainCategoryList.get(i) + "'";
						} else {
							businessDataSQL  = businessDataSQL +"'"+mainCategoryList.get(i)+"'  OR BUSINESSMAINCATEGORY = ";
						}
					}
					if(subCategoryList.size()>0){
						businessDataSQL = businessDataSQL +" INTERSECT ";
						businessDataSQL = businessDataSQL +"SELECT DISTINCT BUSINESSID FROM businessSubCategory WHERE BUSINESSSUBCATEGORY =  ";
					
					for (int i = 0; i < subCategoryList.size(); i++) {
						if (i == subCategoryList.size() - 1) {
							businessDataSQL  = businessDataSQL+"'" +subCategoryList.get(i) + "'";
						} else {
							businessDataSQL  = businessDataSQL +"'"+subCategoryList.get(i)+"'  OR BUSINESSSUBCATEGORY = ";
						}
					}
					}
					if(attributeList.size()>0){
						businessDataSQL = businessDataSQL +" INTERSECT ";
						businessDataSQL = businessDataSQL +"SELECT DISTINCT BUSINESSID FROM businessAttribute "
								+ "WHERE ATTRIBUTESNAME || '_' || ATTRIBUTEVALUE =  ";
					
					for (int i = 0; i < attributeList.size(); i++) {
						if (i == attributeList.size() - 1) {
							businessDataSQL = businessDataSQL+"'" +attributeList.get(i) + "'";
						} else {
							businessDataSQL  = businessDataSQL +"'"+attributeList.get(i)+"'  OR ATTRIBUTESNAME || '_' || ATTRIBUTEVALUE = ";
						}
					}
					}
					businessDataSQL=businessDataSQL+" )";
					
					if(location!=null){
						businessDataSQL= businessDataSQL+" AND b.CITY || '_' || b.STATE = '"+location+"'"; 
					}
					if(dayOfTheWeek!=null){
						businessDataSQL= businessDataSQL+" AND t.BUSINESSDAY = '"+dayOfTheWeek+"'"; 
					}
					if(fromHours!=Double.POSITIVE_INFINITY){
						businessDataSQL= businessDataSQL+ " AND t.OPENTIMING <="+fromHours;
					}
					if(toHours!=Double.POSITIVE_INFINITY){
						businessDataSQL= businessDataSQL+ " AND t.CLOSETIMING  >="+toHours;
					}
					businessDataSQL = businessDataSQL+"  ORDER BY b.BUSINESSNAME ASC ";

					statement = con.createStatement();
					rs = statement.executeQuery(businessDataSQL);
					
					BusinessDataDisplay b;
					
					while (rs.next()) {
							String businessID= rs.getString("BUSINESSID");
							String businessName = rs.getString("BUSINESSNAME");
							String city = rs.getString("CITY");
							String state = rs.getString("STATE");
							double stars = rs.getDouble("STARS");
							double reviewCount = rs.getDouble("reviewCount");
							b= new BusinessDataDisplay(businessID,businessName, city, state, stars,reviewCount);
							businessDataList.add(b);
 				}
	            }else{
	            	if(operation!= null && operation.equalsIgnoreCase("AND")){
	            		businessDataSQL ="select DISTINCT b.businessID, b.BUSINESSNAME, b.CITY , b.STATE,b.STARS,b.REVIEWCOUNT"
		            			+ " from business b,businesstiming t"
						+ " where b.BUSINESSID IN (";
		            	for (int i = 0; i < mainCategoryList.size(); i++) {
	    					if (i == mainCategoryList.size() - 1) {
	    						businessDataSQL = businessDataSQL+ " SELECT DISTINCT  BUSINESSID FROM businessSubCategory WHERE BUSINESSMAINCATEGORY  = '"
	    								+ mainCategoryList.get(i) + "'";
	    					} else {
	    						businessDataSQL = businessDataSQL+ " SELECT  DISTINCT BUSINESSID FROM businessSubCategory WHERE BUSINESSMAINCATEGORY  = '"
	    								+ mainCategoryList.get(i) + "' INTERSECT ";
	    					}
	    				}
						if(subCategoryList.size()>0){

							businessDataSQL = businessDataSQL +" INTERSECT ";
			            	for (int i = 0; i < subCategoryList.size(); i++) {
		    					if (i == subCategoryList.size() - 1) {
		    						businessDataSQL = businessDataSQL+ " SELECT DISTINCT  BUSINESSID FROM businessSubCategory WHERE BUSINESSSUBCATEGORY = '"
		    								+ subCategoryList.get(i) + "'";
		    					} else {
		    						businessDataSQL = businessDataSQL+ " SELECT  DISTINCT BUSINESSID FROM businessSubCategory WHERE BUSINESSSUBCATEGORY = '"
		    								+ subCategoryList.get(i) + "' INTERSECT ";
		    					}
		    				}
						}
						if(attributeList.size()>0){
							businessDataSQL = businessDataSQL +" INTERSECT ";
			            	for (int i = 0; i < attributeList.size(); i++) {
		    					if (i == attributeList.size() - 1) {
		    						businessDataSQL = businessDataSQL+ " SELECT DISTINCT BUSINESSID FROM businessAttribute "
								+ "WHERE ATTRIBUTESNAME || '_' || ATTRIBUTEVALUE = '"
		    								+ attributeList.get(i) + "'";
		    					} else {
		    						businessDataSQL = businessDataSQL+ " SELECT DISTINCT BUSINESSID FROM businessAttribute "
								+ "WHERE ATTRIBUTESNAME || '_' || ATTRIBUTEVALUE = '"
		    								+ attributeList.get(i) + "' INTERSECT ";
		    					}
		    				}
						}
						businessDataSQL=businessDataSQL+" )";
						
						if(location!=null){
							businessDataSQL= businessDataSQL+" AND b.CITY || '_' || b.STATE = '"+location+"'"; 
						}
						if(dayOfTheWeek!=null){
							businessDataSQL= businessDataSQL+" AND t.BUSINESSDAY = '"+dayOfTheWeek+"'"; 
						}
						if(fromHours!=Double.POSITIVE_INFINITY){
							businessDataSQL= businessDataSQL+ " AND t.OPENTIMING <="+fromHours;
						}
						if(toHours!=Double.POSITIVE_INFINITY){
							businessDataSQL= businessDataSQL+ " AND t.CLOSETIMING  >="+toHours;
						}
						businessDataSQL = businessDataSQL+"  ORDER BY b.BUSINESSNAME ASC ";		            	statement = con.createStatement();
						rs = statement.executeQuery(businessDataSQL);
						BusinessDataDisplay b;
						while (rs.next()) {
							String businessID= rs.getString("BUSINESSID");
							String businessName = rs.getString("BUSINESSNAME");
							String city = rs.getString("CITY");
							String state = rs.getString("STATE");
							double stars = rs.getDouble("STARS");
							double reviewCount = rs.getDouble("reviewCount");
							b= new BusinessDataDisplay(businessID,businessName, city, state, stars,reviewCount);
							businessDataList.add(b);
	 				   }
	            	}
	            }
		        }
		 	} catch (ClassNotFoundException e) {
		 		e.printStackTrace();
		 	}finally{
		 		System.out.println("Size"+businessDataList.size());

		 		if(statement!=null){
		 			statement.close();
		 		}
		 		con.close();
		}
		return businessDataList;
	}
	
	public List getReviewData(String businessIDSelectedString)
			throws SQLException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
	    List reviewList= new ArrayList();
	    Set reviewSet = new HashSet();
	    Statement statement=null;
	   
	    String reviewSQL="";
	    try {
	        Class.forName("oracle.jdbc.driver.OracleDriver");
	        con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "system");
	        ResultSet rs = null;
		
			if(businessIDSelectedString!=null && businessIDSelectedString.length()>0){
				reviewSQL ="select  r.REVIEWDATE,r.STARS,r.TEXT, y.USERNAME,r.VOTEUSEFUL from  review r, yelp_user y"
						+ " where BUSINESSID  = '"+businessIDSelectedString+"' AND y.USERID= r.USERID";
				statement = con.createStatement();
				rs = statement.executeQuery(reviewSQL);
				ReviewDataDisplay r;
				while (rs.next()) {
					String reviewDate= rs.getString("REVIEWDATE");
					String stars = rs.getString("STARS");
					String  text = rs.getString("text");
					String userName = rs.getString("USERNAME");
					double voteUseful = rs.getDouble("VOTEUSEFUL");
					r= new ReviewDataDisplay(reviewDate,stars, text, userName, voteUseful);
					reviewSet.add(r);
				}
			
				reviewList= new ArrayList(reviewSet);
			}
	       } catch (ClassNotFoundException e) {
	        	e.printStackTrace();
	        }finally{
	        	statement.close();
	        	con.close();
	        }
	         return  reviewList;
	    }


	
}        
        
	