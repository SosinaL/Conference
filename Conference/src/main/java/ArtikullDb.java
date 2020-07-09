import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArtikullDb {
	 
	 public static ArrayList artikullList() {
	        try (Connection con = Database.getConnection()) {
	            Statement stmt=con.createStatement();  
	            ResultSet rs=stmt.executeQuery("select * from artikull"); 
	            ArrayList artikull = new ArrayList();
	            while(rs.next()) {
	                Artikull art = new Artikull();
	                art.setArtikull_id(rs.getInt("art_id"));
	                art.setTitulli(rs.getString("titulli"));
	                art.setAbstrakti(rs.getString("abstrakti"));
	                art.setEmri_DokElekt(rs.getString("emdokelek"));
	                artikull.add(art);
	            }
	            System.out.println("Artikull added to list!");
	            return artikull;
	        } catch (Exception ex) {
	            System.out.println("ArtikullDb-> artikullList() : " + ex.getMessage());
	            return null;
	        }
	    }
	 
	 public static ArrayList artikullAutorsList(int artikull_id) {
		 try (Connection con = Database.getConnection()) {
	            PreparedStatement stmt=con.prepareStatement("select * from autor_artikull,autor where autor_artikull.aid= autor.autor_id and autor_artikull.artikull_id=?");  
	            stmt.setInt(1, artikull_id);
	            ResultSet rs=stmt.executeQuery(); 
	            ArrayList autors = new ArrayList();
	            while(rs.next()) {
	                Autor a = new Autor();
	                a.setAutor_id(rs.getInt("autor_id"));
	                a.setEmri(rs.getString("emri"));
	                a.setMbiemri(rs.getString("mbiemri"));
	                a.setEmail(rs.getString("email"));
	                autors.add(a);
	            }
	            System.out.println("Artikull Autors added to list!");
	            return autors;
	        } catch (Exception ex) {
	            System.out.println("ArtikullDb-> artikullAutorsList() : " + ex.getMessage());
	            return null;
	        }
	 }
	 
	 public static String ArtikullTitull(int art_id) {
		 try (Connection con = Database.getConnection()) {
	            PreparedStatement stmt=con.prepareStatement("select * from artikulli where art_id=?");  
	            stmt.setInt(1, art_id);
	            ResultSet rs=stmt.executeQuery();
	            rs.next();
	            return rs.getString("titulli");
	        } catch (Exception ex) {
	            System.out.println("ArtikullDb-> artikullAutorsList() : " + ex.getMessage());
	            return null;
	        }
	 }
	 
	 public static ArrayList artikullShqyrtuesList(int artikull_id) {
		 try (Connection con = Database.getConnection()) {
	            PreparedStatement stmt=con.prepareStatement("select * from shqyrtues_artikull,shqyrtues where shqyrtues_artikull.shq_id= shqyrtues.shqyrtues_id and shqyrtues_artikull.art_id=?");  
	            stmt.setInt(1, artikull_id);
	            ResultSet rs=stmt.executeQuery(); 
	            ArrayList directors = new ArrayList();
	            while(rs.next()) {
	                Shqyrtues sh = new Shqyrtues();
	                sh.setShqyrtues_id(rs.getInt("shq_id"));
	               
	                sh.setEmri(rs.getString("emri"));
	                sh.setMbiemri(rs.getString("mbiemri"));
	                sh.setInsitucioni(rs.getString("institucioni"));
	                sh.setTemat(rs.getString("temat"));
		             
	                directors.add(sh);
	            }
	            System.out.println("Shqyrtuesit e artikujve added to list!");
	            return directors;
	        } catch (Exception ex) {
	            System.out.println("ArtikullDb-> artikullShqyrtuesList() : " + ex.getMessage());
	            return null;
	        }
	 }
	 
	 // Used to save movie record
    public static void save(Artikull a){
        int result = 0;
        try{
        	Connection conn = Database.getConnection();
            PreparedStatement stmt = conn.prepareStatement("insert into artikulli(titulli,abstrakti,emdokelek) values(?,?,?)");
            stmt.setString(1, a.getTitulli());
            stmt.setString(2, a.getAbstrakti());
            stmt.setString(3, a.getEmri_DokElekt());
            
            result = stmt.executeUpdate();
            System.out.println("Artikull saved successfully!");
            conn.close();
        }catch(Exception e){
        	System.out.println("ArtikullDb->save() : " + e.getMessage());
        }
    }
    
    // Used to fetch record to update
    public static Artikull edit(int artikull_id){
        Artikull art = null;
        try{
        	Connection conn = Database.getConnection();
            Statement stmt=conn.createStatement();  
            ResultSet rs=stmt.executeQuery("select * from artikulli where art_id = "+(artikull_id));
            rs.next();
            art = new Artikull();
            
            art.setArtikull_id(rs.getInt("art_id"));
            art.setTitulli(rs.getString("titulli"));
            art.setAbstrakti(rs.getString("abstrakti"));
            art.setEmri_DokElekt(rs.getString("emdokelek"));
            System.out.println("Artikull data updated!");
            conn.close();
            return art;
        }catch(Exception e){
        	System.out.println("ArtikullDb->edit() : " + e.getMessage());
        	return null;
        }       
    }
    
    // Used to delete movie record
    public static void delete(int id){
        try{
        	Connection conn = Database.getConnection();  
            PreparedStatement stmt = conn.prepareStatement("delete from artikulli where art_id = "+id);  
            stmt.executeUpdate();  
            System.out.println("Artikull deleted successfully");
        }catch(Exception e){
        	System.out.println("ArtikullDb->delete() : " + e.getMessage());
        }
    }

    public static boolean editArtikull(Artikull a, int id) {
        try (Connection con = Database.getConnection()) {
            PreparedStatement ps = con.prepareStatement("update artikulli set titulli=?, abstrakti=?, emdokelek=? where art_id=?");
            ps.setString(1, a.getTitulli());
            ps.setString(2, a.getAbstrakti());
            ps.setString(3, a.getEmri_DokElekt());
            ps.setInt(4, id);
            System.out.println("Artikull updated!");
            int count = ps.executeUpdate();
            return count == 1;
        } catch (Exception ex) {
            System.out.println("ArtikullDb->editArtikull() : " + ex.getMessage());
            return false;
        }
    }
}
