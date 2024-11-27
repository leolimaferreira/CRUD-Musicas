package controller;

import java.util.List;

import controller.MusicaException;
import entity.Musica;

public interface MusicaDAO {
	void inserir( Musica m ) throws MusicaException;
    void atualizar( Musica m ) throws MusicaException;
    void remover( Musica m ) throws MusicaException;
    List<Musica> pesquisarPorNome( String nome ) throws MusicaException;
}
