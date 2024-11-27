package entity;

import java.time.LocalDate;

public class Musica {

	private long id = 0;
	private String nome = "";
	private String artista = "";
	private String album = "";
	private LocalDate dataLancamento = LocalDate.now();
	
	public Musica() {
	}

	public Musica(long id, String nome, String artista, String album, LocalDate dataLancamento) {
		this.id = id;
		this.nome = nome;
		this.artista = artista;
		this.album = album;
		this.dataLancamento = dataLancamento;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getArtista() {
		return artista;
	}

	public void setArtista(String artista) {
		this.artista = artista;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public LocalDate getDataLancamento() {
		return dataLancamento;
	}

	public void setDataLancamento(LocalDate dataLancamento) {
		this.dataLancamento = dataLancamento;
	}
	
}
