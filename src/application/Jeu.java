package application;

/**
 * Classe représentant un jeu avec matrice 10x10 et caractère du joueur courant (R ou B)
 *
 */
public class Jeu 
{
	/**
	 * Dimension de la grille de jeu DIMENSION X DIMENSION
	 */
	public static final int DIMENSION = 10;
	
	/**
	 * Nombre de cases identiques consécutives pour faire un gain
	 */
	public static final int NB_POUR_GAIN=5;
	
	/**
	 * Caractère d'une case qui n'est pas choisie
	 */
	public static final char CAR_NEUTRE=' ';
	
	/**
	 * Caractère associé au joeur 1 (R pour rouge)
	 */
	public static final char CAR_JOUEUR_1='R';
	
	/**
	 * Caractère associé au joueur 2 (B pour bleu)
	 */
	public static final char CAR_JOUEUR_2='B';
	
	/**
	 * Le caractère que doit prendre la case de la matrice lors d'un clique.
	 * Change selon le joueur courant du jeu!
	 */
	private char carCourant;
	
	/**
	 * Un tableau 2D de caractères pour former la grille en mémoire
	 */
	protected char[][] matJeu=null;
	
	/**
	 * Création d'un jeu avec sa matrice de caractère initialisée avec des 
	 * espaces partout et le caractère courant initialisé à espace.
	 */
	public Jeu()
	{
		matJeu = new char[DIMENSION][DIMENSION];
		setCarCourant(CAR_NEUTRE);
		initialiser();
	}
	
	/**
	 * Méthode accessoire...
	 * 
	 * Permet de mettre un caractère désiré dans une des cases de la matrice
	 * Utilisé pour faire des tests unitaires
	 * 
	 * @param pLig la ligne du caractère
	 * @param pCol la colonne du caractère
	 * @param pCarac le caractère désiré
	 */
	private void assignerCase(int pLig, int pCol, char pCarac)
	{
		boolean CaracValide = setCarCourant(pCarac);
		
		if (CaracValide)
		{
			matJeu[pLig][pCol]=pCarac;
		}
		else
		{
			System.out.println("Caractère invalide");
		}
	}
	
	/**
	 * Initialiser la matrice de caractères en mettant le caractère neutre
	 * (espace) partout.
	 */
	public void initialiser()
	{
		for(int i=0;i<matJeu.length;i++)
		{
			for (int j = 0; j < matJeu[i].length; j++) 
			{
				matJeu[i][j]=CAR_NEUTRE;
			}
				
		}
	}
	
	/**
	* Retourne la valeur du caractère courant
	* 
	* @return char, le caractère courant
	*/
	public char getCarCourant()
	{
		return carCourant;
	}
	
	/**
	 * Modifier la valeur du caractère courant
	 * 
	 * @param pCarCourant le nouveau caractère
	 * 
	 * @return boolean, vrai si la valeur a été mise à jour
	 */
	public boolean setCarCourant(char pCarCourant)
	{
		boolean CaracValide=validerCarCourant(pCarCourant);
		
		if (CaracValide)
		{
			carCourant=pCarCourant;
		}
		
		return CaracValide;
	}
	
	/**
	 * Valider le caractère courant. DOit être neutre ou celui du joueur1 ou celui du joueur2
	 * 
	 * @param pCarCourant le caractère à valider
	 * 
	 * @return boolean, vrai si valide.
	 */
	public static boolean validerCarCourant(char pCarCourant)
	{
		return (pCarCourant==CAR_NEUTRE || pCarCourant==CAR_JOUEUR_1 || pCarCourant==CAR_JOUEUR_2);
	}
	
	/**
	 * Vérifier s'il reste des cases à sélectionner dans la matrice (s'il reste
	 * des cases de couleur neutre)
	 * 
	 * @return boolean, vrai s'il n'y a plus de cases disponibles
	 */
	public boolean verifierPleine()
	{
		boolean matricePleine=true;
		
		for (int i = 0; i < matJeu.length; i++) 
		{
			for (int j = 0; j < matJeu[i].length; j++) 
			{
				if (matJeu[i][j]==CAR_NEUTRE)
				{
					matricePleine=false;
				}
			}
		}
		
		return matricePleine;
	}
	
	/**
	 * Vérifier s'il y a NB_POUR_GAIN cases ayant le même caractère que le 
	 * caractère courant sur une ligne horizontale à partir des indices i et j.
	 * Est appelée par verifierGain()
	 * 
	 * @param i la ligne du bouton cliqué
	 * @param j la colonne du bouton cliqué
	 * 
	 * @return boolean vrai si NB_POUR_GAIN cases de suite sur la même ligne ont 
	 * le même caractère que le caractère courant
	 */
	private boolean verifierCinqHorizontal(int i, int j)
	{
		int compteur=1;
		char carCourant=matJeu[i][j];
		
		//Vers la droite
		for (int x=1; j+x<matJeu[i].length && matJeu[i][j+x]==carCourant; x++)
		{
				compteur++;
		}
		
		//Vers la gauche
		for (int x=1; j-x>=0 && matJeu[i][j-x]==carCourant;x++)
		{
			compteur++;
		}
		
		return (compteur>=NB_POUR_GAIN);
	}
	
	/**
	 * Vérifier s'il y a NB_POUR_GAIN cases ayant le même caractère que le 
	 * caractère courant sur une ligne verticale (colonne) à partir des indices 
	 * i et j. Est appelée par verifierGain()
	 * 
	 * @param i la ligne du bouton cliqué
	 * @param j la colonne du bouton cliqué
	 * 
	 * @return boolean vrai si NB_POUR_GAIN cases de suite sur la même colonne ont 
	 * le même caractère que le caractère courant
	 */
	private boolean verifierCinqVertical(int i, int j)
	{
		int compteur=1;
		char carCourant=matJeu[i][j];
		
		//Vers le bas
		for (int x=1; i+x<matJeu.length && matJeu[i+x][j]==carCourant; x++)
		{
				compteur++;
		}
		
		//Vers le haut
		for (int x=1; i-x>=0 && matJeu[i-x][j]==carCourant;x++)
		{
			compteur++;
		}
		
		return (compteur>=NB_POUR_GAIN);
	}
	
	/**
	 * Vérifier s'il y a NB_POUR_GAIN cases ayant le même caractère que le 
	 * caractère courant sur une diagonale gauche/gauche à partir des indices 
	 * i et j. Est appelée par verifierGain()
	 * 
	 * @param i la ligne du bouton cliqué
	 * @param j la colonne du bouton cliqué
	 * 
	 * @return boolean vrai si NB_POUR_GAIN cases de suite sur la diagonale gauche/droite ont 
	 * le même caractère que le caractère courant
	 */
	private boolean verifierCinqDiagonaleGD(int i, int j)
	{
		int compteur=1;
		char carCourant=matJeu[i][j];
		
		//Vers le bas et droite
		for (int x=1; i+x<matJeu.length && j+x<matJeu[i].length && matJeu[i+x][j+x]==carCourant; x++)
		{
				compteur++;
		}
		
		//Vers le haut et gauche
		for (int x=1; i-x>=0 && j-x>=0 && matJeu[i-x][j-x]==carCourant;x++)
		{
			compteur++;
		}
		
		return (compteur>=NB_POUR_GAIN);
	}
	
	/**
	 * Vérifier s'il y a NB_POUR_GAIN cases ayant le même caractère que le 
	 * caractère courant sur une diagonale droite/gauche à partir des indices 
	 * i et j. Est appelée par verifierGain()
	 * 
	 * @param i la ligne du bouton cliqué
	 * @param j la colonne du bouton cliqué
	 * 
	 * @return boolean vrai si NB_POUR_GAIN cases de suite sur la diagonale droite/gauche ont 
	 * le même caractère que le caractère courant
	 */
	private boolean verifierCinqDiagonaleDG(int i, int j)
	{
		int compteur=1;
		char carCourant=matJeu[i][j];
		
		//Vers le haut et droite
		for (int x=1; i-x>=0 && j+x<matJeu[i].length && matJeu[i-x][j+x]==carCourant; x++)
		{
				compteur++;
		}
		
		//Vers le bas et gauche
		for (int x=1; i+x<matJeu.length && j-x>=0 && matJeu[i+x][j-x]==carCourant;x++)//Réviser, mais celui ci devrait etre OK
		{
			compteur++;
		}
		//Revérier la logique pour quand les boucles for pogne al limites des tableaux, ca marche pas comme je l'ai écrit là
		return (compteur>=NB_POUR_GAIN);
	}
	
	/**
	 * Méthode qui fait l'ensemble des validations pour voir s'il y a un gain.
	 * 
	 * Vérifier s'il y a NB_POUR_GAIN cases ayant le même caractère que le 
	 * caractère courant dans les différentes directions: horizontale, 
	 * verticale, et diagonales. Appelle les méthodes nécessaires pour faire le 
	 * travail.
	 * 
	 * @param i la ligne du bouton cliqué
	 * @param j la colonne du bouton cliqué
	 * 
	 * @return boolean, vrai s'il a un gain
	 */
	
	public boolean verifierGain(int i, int j)
	{
		return (verifierCinqHorizontal(i,j) || verifierCinqVertical(i, j) || verifierCinqDiagonaleGD(i, j) || verifierCinqDiagonaleDG(i, j));
	}
	
	/**
	 * Retourne la matrice sous forme de dessin
	 * 
	 * Chaque case est représenté par [] avec à l'intérieur soit un espace,
	 * soit un R soit un B Exemple pour une 3x3:[][R][B] [B][][R] [B][R][]
	 * 
	 * @return String, dessin de la matrice
	 */
	public String toString()
	{
		String chaine="";
		
		for (int i=0; i<matJeu.length;i++)
		{
			for (int j=0;j<matJeu[i].length;j++)
			{
				chaine+="["+matJeu[i][j]+"]";
			}
			chaine+="\n";
		}
		return chaine;
	}
	
	public static void main(String[] args) 
	{
		Jeu jeu1 = new Jeu();
		System.out.println(jeu1);
		jeu1.assignerCase(1, 1, 'B');
		jeu1.assignerCase(1, 2, 'B');
		jeu1.assignerCase(1, 3, 'B');
		jeu1.assignerCase(1, 4, 'B');
		jeu1.assignerCase(1, 5, 'B');
		System.out.println(jeu1);
		System.out.println(jeu1.verifierGain(1, 1));
	}
}
