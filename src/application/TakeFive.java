package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;


/**
 * Classe représentant l'interface du jeu TakeFive
 *
 */
public class TakeFive 
{
	/**
	 * Couleur d'une case qui n'est pas choisie
	 */
	public static final Color COULEUR_NEUTRE=Color.WHITE;
	
	/**
	 * Couleur pour le joueur 1
	 */
	public static final Color COULEUR_JOUEUR_1=Color.RED;
	
	/**
	 * Couleur pour le joueur 2
	 */
	public static final Color COULEUR_JOUEUR_2=Color.BLUE;
	
	/**
	 * Pour les libellés des joueurs et du message
	 */
	private VBox zoneHaut=null;
	
	/**
	 * Pour les libellés des joueurs
	 */
	private BorderPane zoneHautNoms=null;;
	
	/**
	 * Nom et gains du joueur 1
	 */
	protected Text txtNom1=null;
	
	/**
	 * Nom et gains du joueur 2
	 */
	protected Text txtNom2=null;
	
	/**
	 * Message au centre
	 */
	protected Text txtMessage=null;
	
	/**
	 * Pour le message
	 */
	private HBox zoneHautMessage=null;
	
	/**
	 * Pour la grille contenant la matrice de boutons
	 */
	protected GridPane grille = null;
	
	/**
	 * Pour la matrice de boutons
	 */
	protected Button[][] matBoutons=null;
	
	/**
	 * Pour les boutons du bas
	 */
	private HBox zoneBas=null;
	
	/**
	 * Bouton pour commencer une partie
	 */
	protected Button btnCommencer=null;
	
	/**
	 * Bouton pour annuler la partie
	 */
	protected Button btnAnnuler=null;
	
	/**
	 * Bouton pour quitter la partie
	 */
	protected Button btnQuitter=null;
	
	/**
	 * Pour le conteneur racine
	 */
	private BorderPane root;
	
	/**
	 * Pour la scene
	 */
	protected Scene scene;
	
	/**
	 * Constructeur pour l'interface graphique. Instancie et initialise 
	 * l'ensemble des composantes pour que l'interface soit prete pour commencer 
	 * une partie.
	 */
	public TakeFive()
	{
		creerHaut();
		
		//La grille de jeu
		creerMatrice();
		
		creerBas();
		
		root=new BorderPane();
		root.setTop(zoneHaut);
		root.setCenter(grille);
		root.setBottom(zoneBas);
		
		scene = new Scene (root, 650, 485, Color.LIGHTGREY);
		
		scene.getStylesheets().add(
				this.getClass().getResource("style.css").toString());
	}
	
	/**
	 * Méthode qui s'occupe de construire la partie du haut contenant les noms
	 *  des jouers et leurs gains respectifs ainsi que le message.
	 */
	public void creerHaut()
	{
		zoneHaut=new VBox();
		zoneHautNoms = new BorderPane();
		zoneHautMessage = new HBox();
		txtNom1 = new Text();
		txtNom2 = new Text();
		txtNom1.setFill(TakeFive.COULEUR_JOUEUR_1);
		txtNom2.setFill(TakeFive.COULEUR_JOUEUR_2);
		txtMessage = new Text();
		
		
		
		zoneHaut.getChildren().addAll(zoneHautNoms, zoneHautMessage);
		
		//Les libellés pour les informations sur les joueurs
		zoneHautNoms.setLeft(txtNom1);
		zoneHautNoms.setRight(txtNom2);
		
		//Le message
		zoneHautMessage.getChildren().add(txtMessage);
		zoneHautMessage.setAlignment(Pos.CENTER);
	}
	
	/**
	 * Méthode qui s'occupe de construire la partie du centre contenant 
	 * la matrice de boutons contenue dans un GridPane. Les boutons sont de couleur 
	 * blanche et inactifs.
	 */
	public void creerMatrice()
	{
		//Instancier la matrice et la grille
		matBoutons = new Button[Jeu.DIMENSION][Jeu.DIMENSION];
		grille = new GridPane();
		
		//Instancier et configurer chacun des boutons de la matrice et 
		//l'ajouter dans le GridPane
		for (int i = 0; i < matBoutons.length; i++) 
		{
			for (int j = 0; j < matBoutons.length; j++) 
			{
				matBoutons[i][j] = new Button();
				matBoutons[i][j].setStyle("-fx-background-color: white;");
				matBoutons[i][j].setStyle("-fx-background-radius: 5%;");
				matBoutons[i][j].setPrefSize(60,35);
				matBoutons[i][j].setDisable(true);
				grille.add(matBoutons[i][j], i, j);
			}
		}
		
		grille.setPadding(new Insets(5));
		grille.setHgap(5);
		grille.setVgap(5);
	}
	
	/**
	 * Mettre chaque bouton de la matrice de couleur blanche et l'activer
	 */
	public void initMatBoutons()
	{
		for (int i = 0; i < matBoutons.length; i++) 
		{
			for (int j = 0; j < matBoutons.length; j++) 
			{	
				matBoutons[i][j].setStyle("-fx-background-color: white;");
				matBoutons[i][j].setDisable(false);
			}
		}
		//Lors du clic sur commencer, rendre disponible le boutton annuler
		btnAnnuler.setDisable(false);
	}
	
	/**
	 * Méthode qui s'occupe de construire la partie du bas contenant les 3 
	 * boutons. Le bouton Annuler est inactif.
	 */
	public void creerBas()
	{
		zoneBas = new HBox();
		zoneBas.setAlignment(Pos.TOP_CENTER);
		zoneBas.setSpacing(50);
		zoneBas.setPadding(new Insets (0,0,10,0));
		
		btnCommencer = new Button("Commencer");
		btnAnnuler = new Button("Annuler");
		btnQuitter = new Button("Quitter");
		
		zoneBas.getChildren().addAll(btnCommencer, btnAnnuler, btnQuitter);
		zoneBas.setAlignment(Pos.CENTER);
		
		btnAnnuler.setDisable(true);
	}
}
