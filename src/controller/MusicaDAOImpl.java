package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import entity.Musica;

public class MusicaDAOImpl implements MusicaDAO{
	
	private final static String DB_CLASS = "org.mariadb.jdbc.Driver";
    private final static String DB_URL = "jdbc:mariadb://localhost:3307/gravadoradb?allowPublicKeyRetrieval=true";
    private final static String DB_USER = "root";
    private final static String DB_PASS = "alunofatec";

    private Connection con = null;

	public MusicaDAOImpl() throws MusicaException {
		try {
			Class.forName(DB_CLASS);
			con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
		} catch (ClassNotFoundException | SQLException e) {
			throw new MusicaException(e);
		}
		
	}

	@Override
	public void inserir(Musica m) throws MusicaException {
		try { 
            String SQL = """
                    INSERT INTO musicas (id, nome, artista, album, dataLancamento)
                    VALUES (?, ?, ?, ?, ?)
                    """;
            PreparedStatement stm = con.prepareStatement(SQL);
            stm.setLong(1, m.getId());
            stm.setString(2, m.getNome());
            stm.setString(3, m.getArtista());
            stm.setString(4, m.getAlbum());
            java.sql.Date dt = java.sql.Date.valueOf(m.getDataLancamento());
            stm.setDate(5, dt);
            int i = stm.executeUpdate();
        } catch (SQLException e) { 
            throw new MusicaException( e );
        }
		
	}

	@Override
	public void atualizar(Musica m) throws MusicaException {
		try { 
            String SQL = """
                    UPDATE musicas SET nome = ?, artista = ?, album = ?, dataLancamento = ?
                    WHERE id = ?
                    """;
            PreparedStatement stm = con.prepareStatement(SQL);
            stm.setString(1, m.getNome());
            stm.setString(2, m.getArtista());
            stm.setString(3, m.getAlbum());
            java.sql.Date dt = java.sql.Date.valueOf(m.getDataLancamento());
            stm.setDate(4, dt);
            stm.setLong(5, m.getId());
            int i = stm.executeUpdate();
        } catch (SQLException e) { 
            throw new MusicaException( e );
        }  
		
	}

	@Override
	public void remover(Musica m) throws MusicaException {
		try { 
            String SQL = """
                    DELETE FROM musicas WHERE id = ?
                    """;
            PreparedStatement stm = con.prepareStatement(SQL);
            stm.setLong( 1, m.getId() );
            int i = stm.executeUpdate();
        } catch (SQLException e) { 
            throw new MusicaException( e );
        }
	}

	@Override
	public List<Musica> pesquisarPorNome(String nome) throws MusicaException {
		List<Musica> lista = new ArrayList<>();
        try { 
            String SQL = """
                    SELECT * FROM musicas WHERE nome LIKE ?
                    """;
            PreparedStatement stm = con.prepareStatement(SQL);
            stm.setString(1, "%" + nome + "%");
            ResultSet rs = stm.executeQuery();
            while (rs.next()) { 
                Musica m = new Musica();
                m.setId( rs.getLong("id") );
                m.setNome( rs.getString("nome") );
                m.setArtista( rs.getString("artista") );
                m.setAlbum( rs.getString("album") );
                m.setDataLancamento( rs.getDate("dataLancamento").toLocalDate());

                lista.add( m );
            }
        } catch (SQLException e) { 
            throw new MusicaException( e );
        }
        return lista;
	}

}
