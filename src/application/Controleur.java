package application;



import java.util.Optional;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

/**
 * Classe qui permet de démarrer le programme. Elle permet aussi, par 
 * l'utilisation d'événements JavaFX, de gérer les modifications au 
 * niveau de l'affichage et au niveau logique en fonction du déroulement 
 * du jeu.
 */
public class Controleur extends Application 
{

	/**
	 * Un pointeur TakeFive pour l'interface
	 */
	private TakeFive vue;
	
	/**
	 * Un pointeur Jeu pour la matrice de caracteres
	 */
	private Jeu jeu;
	
	/**
	 * Un pointeur Joueur pour le joueur 1
	 */
	private Joueur joueur1 = null;
	
	/**
	 * Un pointeur Joueur pour le joueur 2
	 */
	private Joueur joueur2 = null;
	
	/**
	 * Un pointeur Joueur courant pour prendre la référence du joueur à qui 
	 * c'est le tour. Permet de simplifier le code.
	 */
	private Joueur joueurCourant = null;
	
	/**
	 * Un pointeur Stage pour le stage
	 */
	private Stage stage;
	
	/**
	 * Méthode qui instancie l'interface et le jeu, ajoute les écouteurs et 
	 * affiche l'interface de départ du jeu.
	 */
	@Override
	public void start(Stage pStage) 
	{
		stage = pStage;
		vue = new TakeFive();
		jeu = new Jeu();
		ajouterEcouteurs();
		stage.setTitle("TAKE FIVE");
		stage.setScene(vue.scene);
		stage.show();
	}
	
	/**
	 * Méthode qui permet d'ajouter un écouteur d'événement à chacun des boutons
	 * de la grille et à chacun des bouton de la zone du bas.
	 */
	private void ajouterEcouteurs()
	{
		vue.btnCommencer.setOnAction(new EcouteurBoutons());
		vue.btnAnnuler.setOnAction(new EcouteurBoutons());
		vue.btnQuitter.setOnAction(new EcouteurBoutons());
		
		for (int i = 0; i < vue.matBoutons.length; i++)
		{
			for (int j = 0; j < vue.matBoutons.length; j++) 
			vue.matBoutons[i][j].setOnAction(new EcouteurGrilleBouton());
		}
	}
	
	/**
	 * Classe permettant d'implémenter la méthode pour l'écoute des clics sur 
	 * les boutons du bas
	 */
	private class EcouteurBoutons implements EventHandler<ActionEvent>
	{
		@Override
		public void handle(ActionEvent e)
		{
			if (e.getSource()==vue.btnCommencer)
			{
				gererCommencer();
			}
			
			if (e.getSource()==vue.btnAnnuler)
			{
				gererAnnuler();
			}
			
			if (e.getSource()==vue.btnQuitter)
			{
				gererQuitter();
			}
		}
	}
	
	/**
	 * Demande la création des joueurs. Si la création fonctionne correctement,
	 * initialise le joueur courant avec le joueur 1, initialise le jeu et son 
	 * caractere courant ainsi que la matrice de boutons. Initialise aussi les 
	 * zones de texte.
	 */
	public void gererCommencer()
	{
		if (creerJoueurs())
		{
			jeu.initialiser();
			vue.initMatBoutons();
			
			jeu.setCarCourant(Jeu.CAR_JOUEUR_1);
			joueurCourant = joueur1;
			
			vue.txtMessage.setFill(joueurCourant.getCouleur());
			vue.txtMessage.setText("C'est le tour à "+ joueurCourant.getNom());
		}
	}
	
	/**
	 * Permet de saisir les noms et de créer les deux objets Joueur. Utilise la
	 * méthode saisirNom() pour saisir le nom de chacun des joueurs. Créer les
	 * objets "Joueur" seulement si les deux noms ont été correctement saisis.
	 * 
	 * @return boolean, vrai si les deux objets "Joueur" ont été créés correctement.
	 */
	public boolean creerJoueurs()
	{
		boolean ok = false;
		String nom1 = null;
		String nom2 = null;
		
		nom1 = saisirNom("Tapez le nom du premier joueur:");
		nom2 = saisirNom("Tapez le nom du deuxième joueur");
		
		//Si le premier nom a bien été saisi
		if (nom1 != null)
		{
			joueur1 = new Joueur(nom1, TakeFive.COULEUR_JOUEUR_1, Joueur.GAIN_DE_DEPART);
			vue.txtNom1.setText(joueur1.toString());
			
			if (nom2 != null)
			{
				joueur2 = new Joueur(nom2, TakeFive.COULEUR_JOUEUR_2, Joueur.GAIN_DE_DEPART);
				vue.txtNom2.setText(joueur2.toString());
				ok = true;
			}
		}
		return ok;
	}
	
	/**
	 * Permet de saisir et de valider avec feedback (en boucle) le nom d'un 
	 * joueur.
	 * 
	 * @param message, le texte d'invite de saisie
	 * 
	 * @return String, le nom saisi ou null si "cancel"
	 */
	public static String saisirNom(String message)
	{
		Optional<String> nomSaisi = null;
		TextInputDialog dialog = new TextInputDialog();
		
		
		dialog.setTitle("TAKE_FIVE");
		dialog.setHeaderText("Saisir nom");
		dialog.setContentText(message);
		nomSaisi = dialog.showAndWait();
		
		
		//Boucle de saisie
		while (!Joueur.validerNom(nomSaisi.get()))
		{
			dialog.setContentText("ATTENTION: Ce nom n'est pas valide !\nTapez le nom du premier joueur");
			nomSaisi = dialog.showAndWait();
		}
		
		return nomSaisi.get();
	}
	
	/**
	 * Permet d'annuler une partie selon une confirmation. Si oui initialise la
	 * matrice de boutons et la matrice de caracteres.
	 */
	public void gererAnnuler()
	{
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Take Five");
		alert.setHeaderText("Confirmer l'annulation");
		alert.setContentText("Voulez-vous vraiment annuler cette partie?");
		
		Optional<ButtonType> result = alert.showAndWait();
		if(result.get()==ButtonType.OK)
		{
			jeu.initialiser();
			vue.initMatBoutons();
		}
	}
	
	/**
	 * Faire la gestion de la sortie du jeu. Affiche une boîte de confirmation
	 * avec ou sans sommaire du nombre de gains selon que la partie était
	 * commencée ou pas.
	 */
	public void gererQuitter()
	{
		if (joueur1==null)
		{
			System.exit(0);
		}
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Take Five");
		
		if(joueur1.getGain() != 0 || joueur2.getGain() != 0)
		
		alert.setHeaderText("Le joueur 1, "+joueur1.getNom() +" a "
				+ ""+joueur1.getGain()+" partie(s) de gagné(s)"+ "\nLe joueur 2, "+joueur2.getNom() +" a "
				+ ""+joueur2.getGain()+" partie(s) de gagné(s)");
		alert.setContentText("Voulez-vous vraiment quitter le jeu?");
		
		Optional<ButtonType> result = alert.showAndWait();
		if(result.get()==ButtonType.OK)
		{
			System.exit(0);
		}
	}
	
	/**
	 * Classe permettant d'implémenter la méthode pour l'écoute des clics sur 
	 * les boutons de la grille
	 */
	private class EcouteurGrilleBouton implements EventHandler<ActionEvent>
	{
		/**
		 * Appelé lorsque qu'un bouton est cliqué. Cette méthode trouve le
		 * bouton qui a été cliqué, si la couleur du bouton est neutre, modifie
		 * la couleur de ce bouton par la couleur du joueur courant, place le
		 * bon caractère dans la matrice du jeu et appelle la méthode
		 * jouerTour().
		 * 
		 * @param e l'événement produit par le bouton cliqué
		 */
		@Override
		public void handle(ActionEvent e)
		{
			Button btn = (Button) (e.getSource());
			
			//Boucles de recherche du bouton cliqué
			for (int i = 0; i < vue.matBoutons.length; i++) 
			{
				for (int j = 0; j < vue.matBoutons[i].length; j++)
				{
					if (btn == vue.matBoutons[i][j] && jeu.matJeu[i][j]==Jeu.CAR_NEUTRE)
					{						
						if (joueurCourant==joueur1)
						{
							//Changement du caractère de la case cliquée pour celui du joueur 1
							jeu.matJeu[i][j]=Jeu.CAR_JOUEUR_1;
							
							//Changement de la couleur de la case cliquée pour celle du joueur 1 (rouge)
							vue.matBoutons[i][j].setStyle("-fx-background-color: red;");
							
							//Appel de la méthode jouerTour()
							jouerTour(i, j);
						}
						
						else
						{
							//Changement du caractère de la case cliquée pour celui du joueur 2
							jeu.matJeu[i][j]=Jeu.CAR_JOUEUR_2;
							
							//Changement de la couleur de la case cliquée pour celle du joueur 2 (bleu)
							vue.matBoutons[i][j].setStyle("-fx-background-color: blue;");
							
							//Appel de la méthode jouerTour()
							jouerTour(i, j);
						}
					}
				}
			}
		}	
	}
	
	/**
	 * Vérifier s'il y a un gain et faire les actions en conséquence. Sinon 
	 * vérifier si la grille est pleine (partie nulle) et faire les actions en
	 * conséquence. Par la suite modifier le joueur courant et le caractere
	 * courant et afficher les informations au sujet des joueurs et à  qui le
	 * tour.
	 *
	 * @param i la ligne du bouton cliqué
	 * @param j la colonne du bouton cliqué
	 */
	public void jouerTour(int i, int j)
	{
		//Vérifier s'il y a un gain
		if (jeu.verifierGain(i, j))
		{
			joueurCourant.incrementerGain();
			Alert alert = new Alert (AlertType.INFORMATION);
			alert.setTitle("TAKE_FIVE");
			alert.setHeaderText("Partie gagnante");
			alert.setContentText("Bravo " + joueurCourant.getNom() + " ! Tu as gagné cette partie.");
			alert.showAndWait();
			
			jeu.initialiser();
			vue.initMatBoutons();
		}
		
		else
		{
			if(jeu.verifierPleine())
			{
				Alert alert = new Alert (AlertType.INFORMATION);
				alert.setTitle("TAKE_FIVE");
				alert.setHeaderText("Partie nulle");
				alert.setContentText("Partie nulle, meilleure chance la prochaine fois !");
				alert.showAndWait();
				
				jeu.initialiser();
				vue.initMatBoutons();
			}
		}
		
		//Préparer pour le prochain tour
		//Quel est le joueur à jouer
		if (joueurCourant == joueur1)
		{
			joueurCourant = joueur2;
			jeu.setCarCourant(Jeu.CAR_JOUEUR_2);
		}
		
		else
		{
			joueurCourant = joueur1;
			jeu.setCarCourant(Jeu.CAR_JOUEUR_1);
		}
		
		vue.txtNom1.setText(joueur1.toString());
		vue.txtNom2.setText(joueur2.toString());
		
		vue.txtMessage.setFill(joueurCourant.getCouleur());
		vue.txtMessage.setText("C'est le tour à "+ joueurCourant.getNom());
	}
	
	
	public static void main(String[] args) 
	{
		Application.launch(args);
	}
}
