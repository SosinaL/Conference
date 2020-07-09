import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import java.util.SimpleTimeZone;

public class AutorDb {
	 
	 public static ArrayList actorsList() {
	        try (Connection con = Database.getConnection()) {
	            Statement stmt=con.createStatement();  
	            ResultSet rs=stmt.executeQuery("select * from autor"); 
	            ArrayList autors = new ArrayList();
	            while(rs.next()) {
	                Autor a = new Autor();
	                a.setAutor_id(rs.getInt("autor_id"));
	                a.setEmri(rs.getString("emri"));
	                a.setEmail(rs.getString("email"));
	                a.setEmail(rs.getString("mbiemri"));
	                autors.add(a);
	            }
	            System.out.println("Autors added to list!");
	            return autors;
	        } catch (Exception ex) {
	            System.out.println("AutorDb> autorsList() : " + ex.getMessage());
	            return null;
	        }
	    }
	 
	 public static ArrayList autorArtikullList(int autor_id) {
		 try (Connection con = Database.getConnection()) {
	            PreparedStatement stmt=con.prepareStatement("SELECT * FROM autor_artikull, artikulli WHERE autor_artikull.aid= artikulli.art_id AND autor_artikull.artikull_id=?");  
	            stmt.setInt(1, autor_id);
	            ResultSet rs=stmt.executeQuery(); 
	            ArrayList artikull = new ArrayList();
	            while(rs.next()) {
	                Artikull m = new Artikull();
	                m.setArtikull_id(rs.getInt("art_id"));
	                m.setTitulli(rs.getString("titulli"));
	                m.setAbstrakti(rs.getString("abstrakti"));
	                m.setEmri_DokElekt(rs.getString("emdokelek"));
	               
	                artikull.add(m);
	            }
	            System.out.println("Autor Artikull added to list!");
	            return artikull;
	        } catch (Exception ex) {
	            System.out.println("AutorDAO-> autorArtikullList() : " + ex.getMessage());
	            return null;
	        }
	 }
	 
	 public static String autorName(int autor_id) {
		 try (Connection con = Database.getConnection()) {
	            PreparedStatement stmt=con.prepareStatement("select * from autor where autor_id=?");  
	            stmt.setInt(1, autor_id);
	            ResultSet rs=stmt.executeQuery();
	            rs.next();
	            return rs.getString("emri");
	        } catch (Exception ex) {
	            System.out.println("AutorDb-> autorName() : " + ex.getMessage());
	            return null;
	        }
	 }
	 
    // Used to delete actor record
    public static void delete(int id){
        try{
        	Connection conn = Database.getConnection();  
            PreparedStatement stmt = conn.prepareStatement("delete from autor where autor_id = "+id);  
            stmt.executeUpdate();  
            System.out.println("Autor deleted successfully");
        }catch(Exception e){
        	System.out.println("AutorDb->delete() : " + e.getMessage());
        }
    }
	
    // Used to save actor record
    public static void save(Autor a){
        int result = 0;
        try{
        	Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement("insert into autor(emri,mbiemri,email) values(?,?,?)");
            stmt.setString(1, a.getEmri());
            stmt.setString(1, a.getMbiemri());
            stmt.setString(1, a.getEmail());
           
            result = stmt.executeUpdate();
            System.out.println("Autor saved successfully!");
            conn.close();
        }catch(Exception e){
        	System.out.println("AutorDb->save() : " + e.getMessage());
        }
    }
    
    // Used to fetch record to update
    public static Autor edit(int id){
        Autor a= null;
        try{
        	Connection conn = Database.getConnection();
            Statement stmt=conn.createStatement();  
            ResultSet rs=stmt.executeQuery("select * from autor where autor_id = "+(id));
            rs.next();
            a = new Autor();
            a.setAutor_id(rs.getInt("autor_id"));
            a.setEmri(rs.getString("emri"));
            a.setMbiemri(rs.getString("mbiemri"));
            a.setEmail(rs.getString("email"));
            System.out.println("Autor data updated!");
            conn.close();
            return a;
        }catch(Exception e){
        	System.out.println("AutorDb->edit() : " + e.getMessage());
        	return null;
        }       
    }

    public static boolean editAutor(Autor a, int id) {
        try (Connection con = Database.getConnection()) {
            PreparedStatement ps = con.prepareStatement("update autor set emri=?, mbiemri=?, email=? where autor_id=?");
            ps.setString(1, a.getEmri());
            ps.setDate(2, Date.valueOf(a.getMbiemri()));
            ps.setDate(2, Date.valueOf(a.getEmail()));
            ps.setInt(3, id);
            System.out.println("Autor updated!");
            int count = ps.executeUpdate();
            return count == 1;
        } catch (Exception ex) {
            System.out.println("AutorDb->editAutor() : " + ex.getMessage());
            return false;
        }
    }
    
  

	public static void saveAssignAutor(int autor_id, String emri, String mbiemri, String artikull) {
		 try{
	        	Connection conn = Database.getConnection();
	            PreparedStatement stmt = conn.prepareStatement("insert into autor_artikull(aid, artikull_id) values(?,?)");
	           
	            stmt.setInt(2, autor_id);
	          
	            int result = stmt.executeUpdate();
	            System.out.println("Autor-Artikull saved successfully!");
	            conn.close();
	        }catch(Exception e){
	        	System.out.println("ActorDb->saveAssignAutor() : " + e.getMessage());       
	        	}
	    }
	
		
		
	}
	    

