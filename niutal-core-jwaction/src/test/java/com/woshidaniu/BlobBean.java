package com.woshidaniu;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.jdbc.driver.MyOracleClobInputStream;

public class BlobBean
{

   Connection conn ;
   /**
    *构造方法,创建Connection对象，并且在数据库中添加一个表。
    */
   public BlobBean()throws Exception
   {
   	
   		DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
       conn = DriverManager.getConnection("jdbc:oracle:thin:@10.71.32.37:1521:devdb", "niutal_javajw", "niutal_javajw");
    }
    
    /**
     *写入Blob数据到数据库
     */
	public void addBlob(String fileName)throws Exception
	{
	        
	        conn.setAutoCommit(false);
	        
	        
	        
	        PreparedStatement ps    = conn.prepareStatement("insert into jw_xjgl_xszpb(xh_id,rxqzp) values ('3043021072',?)");
	        FileInputStream fStream = new FileInputStream(fileName);
	        ps.setBinaryStream(1, fStream, (int) fileName.length());
	        ps.execute();
	        
          /*  Statement stmt = conn.createStatement();
            stmt.execute("insert into jw_xjgl_xszpb(xh_id,rxqzp) values ('3043021072',empty_blob())");
            ResultSet rset = stmt.executeQuery("SELECT rxqzp FROM jw_xjgl_xszpb FOR UPDATE");
         //   BLOB blob = null;
            CLOB clob = null;
            while (rset.next()) {
            	clob = ((OracleResultSet) rset).getCLOB(1);
                System.out.println(clob.length());
            }
            File binaryFile = new File(fileName);
            System.out.println(fileName+"'s length = " + binaryFile.length());
            FileInputStream instream = new FileInputStream(binaryFile);
            OutputStream outstream = clob.getAsciiOutputStream();
            	//clob.getBinaryOutputStream();
            int chunk = clob.getChunkSize();
            System.out.println("chunk size = " + chunk);
            byte[] buffer = new byte[chunk];
            int length = -1;
            while ((length = instream.read(buffer)) != -1)
                outstream.write(buffer, 0, length);*/
	        fStream.close();
           
            conn.commit();

        }
	
	/**
     * 通过员工的empId取得图片信息；
     * @param userId
     */
    public void getPhoto(String path){
        System.out.println("----getPhoto");
       
        PreparedStatement ps = null;
        ResultSet rs = null;
        
      
        try {
            ps = conn.prepareStatement("select  rxqzp from jw_xjgl_xszpb where xh_id=? ");
            ps.setString(1, "3043021072");
            rs = ps.executeQuery();
           /// OracleBlobInputStream fStream = null;
            MyOracleClobInputStream  fStream = null;
            while(rs.next()){
                fStream = (MyOracleClobInputStream)rs.getBinaryStream("rxqzp");
            }
            FileOutputStream fOutputStream = new FileOutputStream(path);
            byte[] buffer = new byte[1024];
            int byteRead = 0;
            while((byteRead = fStream.read(buffer)) != -1){
                fOutputStream.write(buffer, 0, byteRead);
            }
            fOutputStream.close();
            fStream.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
           // try {
            	//conn.close();
            //} catch (SQLException e) {
               // e.printStackTrace();
           // }
        }
        
        System.out.println("get photo is success!");
    }
	
	 public static void main(String[] args) throws Exception{
		 ///BlobBean b = new BlobBean();
		// b.addBlob("C:/Users/Public/Pictures/Sample Pictures/41021119880506702X.jpg");
		/// b.getPhoto("D:/majun.jpg");
		 
		  
	}
	
}