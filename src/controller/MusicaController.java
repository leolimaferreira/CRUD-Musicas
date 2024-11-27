package controller;

import java.time.LocalDate;

import entity.Musica;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MusicaController {
	
    private ObservableList<Musica> lista = FXCollections.observableArrayList();
    private long contador = 2;

    private LongProperty id = new SimpleLongProperty(0);
    private StringProperty nome = new SimpleStringProperty("");
    private StringProperty artista = new SimpleStringProperty("");
    private StringProperty album = new SimpleStringProperty("");
    private ObjectProperty<LocalDate> dataLancamento = new SimpleObjectProperty<>(LocalDate.now());

    private MusicaDAO musicaDAO = null;
    
	public MusicaController() throws MusicaException {
		musicaDAO = new MusicaDAOImpl();
	}
	
	public Musica paraEntidade() { 
		Musica m = new Musica();
		m.setId(id.get());
		m.setNome(nome.get());
		m.setArtista(artista.get());
		m.setAlbum(album.get());
		m.setDataLancamento(dataLancamento.get());
		return m;
	}
	
	public void excluir( Musica m ) throws MusicaException { 
        musicaDAO.remover( m );
        pesquisarTodos();
    }

    public void limparTudo() { 
        id.set( 0 );
        nome.set( "" );
        artista.set( "");
        album.set("");
        dataLancamento.set(LocalDate.now());
    }

    public void paraTela(Musica m) { 
        if (m != null) {
            id.set( m.getId() );
            nome.set( m.getNome() );
            artista.set( m.getArtista() );
            album.set( m.getAlbum() );
            dataLancamento.set( m.getDataLancamento() );
        }
    }
    
    public void gravar() throws MusicaException { 
        Musica m = paraEntidade();
        if (m.getId() == 0 ) { 
            this.contador += 1;
            m.setId( this.contador );
            musicaDAO.inserir( m );
        } else { 
            musicaDAO.atualizar( m );
        }
        pesquisarTodos();
    }
    
    public void pesquisar() throws MusicaException { 
        lista.clear();
        lista.addAll( musicaDAO.pesquisarPorNome( nome.get() ) );
    }

    public void pesquisarTodos() throws MusicaException { 
        lista.clear();
        lista.addAll( musicaDAO.pesquisarPorNome( "" ) );
    }
    
    public LongProperty idProperty() { 
        return this.id;
    }
    public StringProperty nomeProperty() { 
        return this.nome;
    }
    public StringProperty artistaProperty() { 
        return this.artista;
    }
    public StringProperty albumProperty() { 
        return this.album;
    }
    public ObjectProperty<LocalDate> dataLancamentoProperty() { 
        return this.dataLancamento;
    }

    public ObservableList<Musica> getLista() { 
        return this.lista;
    }
}
