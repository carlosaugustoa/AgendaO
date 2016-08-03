package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Contato;

public class ContatoDAO {

    private DataBase db;
    private PreparedStatement ps;
    private ResultSet rs;
    private String sql;
    
    public ContatoDAO() {
        db = new DataBase();
    }
    
    public boolean insert(Contato contato) {
        if (db.open()) {
            sql = "INSERT INTO tb_contatos (con_nome, con_fone) VALUES (?, ?)";
            try {
                ps = db.connection.prepareStatement(sql);
                ps.setString(1, contato.getNome());
                ps.setString(2, contato.getFone());
                if (ps.executeUpdate() == 1) {
                    ps.close();
                    db.close();
                    return true;
                }
            } catch (SQLException error) {
                System.out.println("ERRO: " + error.toString());
            }
        }
        db.close();
        return false;
    }
    
    public boolean delete(Contato contato) {
        if (db.open()) {
            sql = "DELETE FROM tb_contatos WHERE con_id = ?";
            try {
                ps = db.connection.prepareStatement(sql);
                ps.setInt(1, contato.getId());
                if (ps.executeUpdate() == 1) {
                    ps.close();
                    db.close();
                    return true;
                }
            } catch (SQLException error) {
                System.out.println("ERRO: " + error.toString());
            }
        }
        db.close();
        return false;
    }
    
    public boolean update(Contato contato) {
        if (db.open()) {
            sql = "UPDATE tb_contatos SET con_nome = ?, con_fone = ? WHERE con_id = ?";
            try {
                ps = db.connection.prepareStatement(sql);
                ps.setString(1, contato.getNome());
                ps.setString(2, contato.getFone());
                ps.setInt(3, contato.getId());
                if (ps.executeUpdate() == 1) {
                    ps.close();
                    db.close();
                    return true;
                }
            } catch (SQLException error) {
                System.out.println("ERRO: " + error.toString());
            }
        }
        db.close();
        return false;
    }
    
    public List<Contato> selectAll() {
        if (db.open()) {
            List<Contato> contatos = new ArrayList();
            sql = "SELECT * FROM tb_contatos";
            try {
                ps = db.connection.prepareStatement(sql);
                rs = ps.executeQuery();
                while (rs.next()) {
                    Contato contato = new Contato();
                    contato.setId(rs.getInt(1));
                    contato.setNome(rs.getString(2));
                    contato.setFone(rs.getString(3));
                    contatos.add(contato);
                }
                rs.close();
                ps.close();
                db.close();
                return contatos;
            } catch (SQLException error) {
                System.out.println("ERRO: " + error.toString());
            }
        }
        db.close();
        return null;
    }
    
    public List<Contato> selectFilter(String filter) {
        if (db.open()) {
            List<Contato> contatos = new ArrayList();
            String filtro = "%" + filter + "%";
            sql = "SELECT * FROM tb_contatos WHERE con_nome LIKE ? OR con_fone LIKE ?";
            try {
                ps = db.connection.prepareStatement(sql);
                ps.setString(1, filtro);
                ps.setString(2, filtro);
                rs = ps.executeQuery();
                while (rs.next()) {
                    Contato contato = new Contato();
                    contato.setId(rs.getInt(1));
                    contato.setNome(rs.getString(2));
                    contato.setFone(rs.getString(3));
                    contatos.add(contato);
                }
                rs.close();
                ps.close();
                db.close();
                return contatos;
            } catch (SQLException error) {
                System.out.println("ERRO: " + error.toString());
            }
        }
        db.close();
        return null;
    }
    
    public Contato select(int id) {
        if (db.open()) {
            Contato contato = new Contato();
            sql = "SELECT * FROM tb_contatos WHERE con_id = ?";
            try {
                ps = db.connection.prepareStatement(sql);
                ps.setInt(1, id);
                rs = ps.executeQuery();
                if (rs.next()) {
                    contato.setId(rs.getInt(1));
                    contato.setNome(rs.getString(2));
                    contato.setFone(rs.getString(3));
                    rs.close();
                    ps.close();
                    db.close();
                    return contato;
                }
            } catch (SQLException error) {
                System.out.println("ERRO: " + error.toString());
            }
        }
        db.close();
        return null;
    }
    
}

        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
    