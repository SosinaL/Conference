import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ShqyrtuesDb {
	 
	 public static ArrayList shqyrtuesList() {
	        try (Connection con = Database.getConnection()) {
	            Statement stmt=con.createStatement();  
	            ResultSet rs=stmt.executeQuery("select * from shqyrtues"); 
	            ArrayList shqyrtues = new ArrayList();
	            while(rs.next()) {
	                Shqyrtues sh = new Shqyrtues();
	                sh.setShqyrtues_id(rs.getInt("shqyrtues_id"));
	                sh.setEmri(rs.getString("emri"));
	                sh.setMbiemri(rs.getString("mbiemri"));
	                
	                shqyrtues.add(sh);
	            }
	            System.out.println("Autors added to list!");
	            return shqyrtues;
	        } catch (Exception ex) {
	            System.out.println("ShqyrtuesDb-> shqyrtuesList() : " + ex.getMessage());
	            return null;
	        }
	    }
	 
	 
	 
	 public static String shqyrtuesName(int shqyrtues_id) {
		 try (Connection con = Database.getConnection()) {
	            PreparedStatement stmt=con.prepareStatement("select * from shqyrtues where shqyrtues_id=?");  
	            stmt.setInt(1, shqyrtues_id);
	            ResultSet rs=stmt.executeQuery();
	            rs.next();
	            return rs.getString("emri");
	        } catch (Exception ex) {
	            System.out.println("ShqyrtuesDb-> shqyrtuesName() : " + ex.getMessage());
	            return null;
	        }
	 }
	 
	// Used to delete shqyrtues record
    public static void delete(int id){
        try{
        	Connection conn = Database.getConnection();  
            PreparedStatement stmt = conn.prepareStatement("delete from shqyrtues where shqyrtues_id = "+id);  
            stmt.executeUpdate();  
            System.out.println("Shqyrtues deleted successfully");
        }catch(Exception e){
        	System.out.println("ShqyrtuesDb->delete() : " + e.getMessage());
        }
    }
		
    // Used to save director record
    public static void save(Shqyrtues sh){
        int result = 0;
        try{
        	Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement("insert into shqyrtues(emri,mbiemri,email,nrtel,insitucioni,temat) values(?,?,?,?,?,?)");
            stmt.setString(1, sh.getEmri());
            stmt.setString(2, sh.getMbiemri());
            stmt.setString(3, sh.getEmail());
            stmt.setInt(4, sh.getNrTel());
            stmt.setString(5, sh.getInstitucioni());
            stmt.setString(6, sh.getTemat());
            
            result = stmt.executeUpdate();
            System.out.println("Shqyrtues saved successfully!");
            conn.close();
        }catch(Exception e){
        	System.out.println("ShqyrtuesDb->save() : " + e.getMessage());
        }
    }
    
    // Used to fetch record to update
    public static Shqyrtues edit(int id){
       Shqyrtues sh= null;
        try{
        	Connection conn = Database.getConnection();
            Statement stmt=conn.createStatement();  
            ResultSet rs=stmt.executeQuery("select * from shqyrtues where shqyrtues_id = "+(id));
            rs.next();
            sh = new Shqyrtues();
            sh.setShqyrtues_id(rs.getInt("shqyrtues_id"));
            sh.setEmri(rs.getString("emri"));
            sh.setMbiemri(rs.getString("datelindja"));
            System.out.println("Director data updated!");
            conn.close();
            return sh;
        }catch(Exception e){
        	System.out.println("DirectorDAO->edit() : " + e.getMessage());
        	return null;
        }       
    }

    public static boolean editShqyrtues(Shqyrtues sh, int id) {
        try (Connection con = Database.getConnection()) {
            PreparedStatement ps = con.prepareStatement("update shqyrtues set emri=?, mbiemri=?, email=?, institucioni=?,temat=? where shqyrtues_id=?");
            ps.setString(1, sh.getEmri());
            ps.setString(2, sh.getMbiemri());
            ps.setString(3, sh.getEmail());
            ps.setString(4, sh.getInstitucioni());
            ps.setString(5, sh.getTemat());
            
            ps.setInt(6, id);
            System.out.println("Shqyrtues updated!");
            int count = ps.executeUpdate();
            return count == 1;
        } catch (Exception ex) {
            System.out.println("Shqyrtues->editShqyrtues() : " + ex.getMessage());
            return false;
        }
    }
    
    public static void saveShqyrtues(int shqyrtues_id, String role, int art_id){
        try{
        	Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement("insert into artikull_shqyrtues(shq_id, art_id, roli) values(?,?,?)");
            stmt.setInt(1, shqyrtues_id);
            stmt.setInt(2, art_id);
            stmt.setString(3, role);
            int result = stmt.executeUpdate();
            System.out.println("Artikull-Autor saved successfully!");
            conn.close();
        }catch(Exception e){
        	System.out.println("ShqyrtuesDb->saveShqyrtues() : " + e.getMessage());
        }
    }



	

	

}
