package boundary;

import java.time.LocalDate;

import controller.MusicaController;
import controller.MusicaException;
import entity.Musica;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class MusicaBoundary extends Application{
	
	private Label lblId = new Label("");
    private TextField txtNome = new TextField();
    private TextField txtArtista = new TextField();
    private TextField txtAlbum = new TextField();
    private DatePicker dateLancamento = new DatePicker();

    private MusicaController control = null;

    private TableView<Musica> tableView = new TableView<>();

	@Override
	public void start(Stage stage) throws Exception {
		try { 
            control = new MusicaController();
            ligacoes();
            gerarColunas();
        } catch (MusicaException e ) { 
            new Alert(AlertType.ERROR, "Erro ao iniciar o sistema", ButtonType.OK).showAndWait();
        }
		BorderPane panePrincipal = new BorderPane();
        GridPane paneForm = new GridPane();

        Button btnGravar = new Button("Gravar");
        btnGravar.setOnAction( e -> { 
            try { 
                control.gravar();
            } catch (MusicaException err) { 
                new Alert(AlertType.ERROR, "Erro ao gravar a musica", ButtonType.OK).showAndWait();
            }
            tableView.refresh();
        });
        Button btnPesquisar = new Button("Pesquisar");
        btnPesquisar.setOnAction( e -> { 
            try { 
                control.pesquisar();
            } catch (MusicaException err) { 
                new Alert(AlertType.ERROR, "Erro ao pesquisar por nome", ButtonType.OK).showAndWait();
            }});

        Button btnNovo = new Button("*");
        btnNovo.setOnAction( e -> control.limparTudo() );
        
        paneForm.add(new Label("Id: "), 0, 0);
        paneForm.add(lblId, 1, 0);
        paneForm.add(new Label("Nome: "), 0, 1);
        paneForm.add(txtNome, 1, 1);
        paneForm.add(btnNovo, 2, 1);
        paneForm.add(new Label("Artista: "), 0, 2);
        paneForm.add(txtArtista, 1, 2);
        paneForm.add(new Label("Album: "), 0, 3);
        paneForm.add(txtAlbum, 1, 3);
        paneForm.add(new Label("Lancamento: "), 0, 4);
        paneForm.add(dateLancamento, 1, 4);

        paneForm.add(btnGravar, 0, 5);
        paneForm.add(btnPesquisar, 1, 5);

        panePrincipal.setTop( paneForm );
        panePrincipal.setCenter(tableView);

        Scene scn = new Scene(panePrincipal, 600, 400);
        stage.setScene(scn);
        stage.setTitle("Gravadora de Musicas");
        stage.show();
	}
	
	public void gerarColunas() { 
        TableColumn<Musica, Long> col1 = new TableColumn<>("Id");
        col1.setCellValueFactory( new PropertyValueFactory<Musica, Long>("id") );

        TableColumn<Musica, String> col2 = new TableColumn<>("Nome");
        col2.setCellValueFactory( new PropertyValueFactory<Musica, String>("nome"));

        TableColumn<Musica, String> col3 = new TableColumn<>("Artista");
        col3.setCellValueFactory( new PropertyValueFactory<Musica, String>("artista"));

        TableColumn<Musica, String> col4 = new TableColumn<>("Album");
        col4.setCellValueFactory( new PropertyValueFactory<Musica, String>("album"));

        TableColumn<Musica, LocalDate> col5 = new TableColumn<>("Lancamento");
        col5.setCellValueFactory( new PropertyValueFactory<Musica, LocalDate>("dataLancamento"));

        tableView.getSelectionModel().selectedItemProperty()
            .addListener( (obs, antigo, novo) -> {
                if (novo != null) { 
                    System.out.println("Nome: " + novo.getNome());
                    control.paraTela(novo);
                }
            });
        Callback<TableColumn<Musica, Void>, TableCell<Musica, Void>> cb = 
            new Callback<>() {
                @Override
                public TableCell<Musica, Void> call(
                    TableColumn<Musica, Void> param) {
                    TableCell<Musica, Void> celula = new TableCell<>() { 
                        final Button btnApagar = new Button("Apagar");

                        {
                            btnApagar.setOnAction( e -> {
                                Musica musica = tableView.getItems().get( getIndex() );
                                try { 
                                    control.excluir( musica ); 
                                } catch (MusicaException err) { 
                                    new Alert(AlertType.ERROR, "Erro ao excluir a musica", ButtonType.OK).showAndWait();
                                }
                            });
                        }

                        @Override
                        public void updateItem( Void item, boolean empty) {                             
                            if (!empty) { 
                                setGraphic(btnApagar);
                            } else { 
                                setGraphic(null);
                            }
                        }
                        
                    };
                    return celula;            
                } 
            };

        TableColumn<Musica, Void> col6 = new TableColumn<>("Ação");
        col6.setCellFactory(cb);

        tableView.getColumns().addAll(col1, col2, col3, col4, col5, col6);
        tableView.setItems( control.getLista() );
    }
	
	public void ligacoes() { 
    	if (control == null) {
            throw new IllegalStateException("Controle não foi inicializado.");
        }
        control.idProperty().addListener((obs, antigo, novo) -> {
            lblId.setText(String.valueOf(novo));
        });
        Bindings.bindBidirectional(control.nomeProperty(), txtNome.textProperty());
        Bindings.bindBidirectional(control.artistaProperty(), txtArtista.textProperty());
        Bindings.bindBidirectional(control.albumProperty(), txtAlbum.textProperty());
        Bindings.bindBidirectional(control.dataLancamentoProperty(), dateLancamento.valueProperty());
    }

    public static void main(String[] args) {
        Application.launch(MusicaBoundary.class, args);
    }
}
