package application;

/**
 * Classe repr�sentant un jeu avec matrice 10x10 et caract�re du joueur courant (R ou B)
 *
 */
public class Jeu 
{
	/**
	 * Dimension de la grille de jeu DIMENSION X DIMENSION
	 */
	public static final int DIMENSION = 10;
	
	/**
	 * Nombre de cases identiques cons�cutives pour faire un gain
	 */
	public static final int NB_POUR_GAIN=5;
	
	/**
	 * Caract�re d'une case qui n'est pas choisie
	 */
	public static final char CAR_NEUTRE=' ';
	
	/**
	 * Caract�re associ� au joeur 1 (R pour rouge)
	 */
	public static final char CAR_JOUEUR_1='R';
	
	/**
	 * Caract�re associ� au joueur 2 (B pour bleu)
	 */
	public static final char CAR_JOUEUR_2='B';
	
	/**
	 * Le caract�re que doit prendre la case de la matrice lors d'un clique.
	 * Change selon le joueur courant du jeu!
	 */
	private char carCourant;
	
	/**
	 * Un tableau 2D de caract�res pour former la grille en m�moire
	 */
	protected char[][] matJeu=null;
	
	/**
	 * Cr�ation d'un jeu avec sa matrice de caract�re initialis�e avec des 
	 * espaces partout et le caract�re courant initialis� � espace.
	 */
	public Jeu()
	{
		matJeu = new char[DIMENSION][DIMENSION];
		setCarCourant(CAR_NEUTRE);
		initialiser();
	}
	
	/**
	 * M�thode accessoire...
	 * 
	 * Permet de mettre un caract�re d�sir� dans une des cases de la matrice
	 * Utilis� pour faire des tests unitaires
	 * 
	 * @param pLig la ligne du caract�re
	 * @param pCol la colonne du caract�re
	 * @param pCarac le caract�re d�sir�
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
			System.out.println("Caract�re invalide");
		}
	}
	
	/**
	 * Initialiser la matrice de caract�res en mettant le caract�re neutre
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
	* Retourne la valeur du caract�re courant
	* 
	* @return char, le caract�re courant
	*/
	public char getCarCourant()
	{
		return carCourant;
	}
	
	/**
	 * Modifier la valeur du caract�re courant
	 * 
	 * @param pCarCourant le nouveau caract�re
	 * 
	 * @return boolean, vrai si la valeur a �t� mise � jour
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
	 * Valider le caract�re courant. DOit �tre neutre ou celui du joueur1 ou celui du joueur2
	 * 
	 * @param pCarCourant le caract�re � valider
	 * 
	 * @return boolean, vrai si valide.
	 */
	public static boolean validerCarCourant(char pCarCourant)
	{
		return (pCarCourant==CAR_NEUTRE || pCarCourant==CAR_JOUEUR_1 || pCarCourant==CAR_JOUEUR_2);
	}
	
	/**
	 * V�rifier s'il reste des cases � s�lectionner dans la matrice (s'il reste
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
	 * V�rifier s'il y a NB_POUR_GAIN cases ayant le m�me caract�re que le 
	 * caract�re courant sur une ligne horizontale � partir des indices i et j.
	 * Est appel�e par verifierGain()
	 * 
	 * @param i la ligne du bouton cliqu�
	 * @param j la colonne du bouton cliqu�
	 * 
	 * @return boolean vrai si NB_POUR_GAIN cases de suite sur la m�me ligne ont 
	 * le m�me caract�re que le caract�re courant
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
	 * V�rifier s'il y a NB_POUR_GAIN cases ayant le m�me caract�re que le 
	 * caract�re courant sur une ligne verticale (colonne) � partir des indices 
	 * i et j. Est appel�e par verifierGain()
	 * 
	 * @param i la ligne du bouton cliqu�
	 * @param j la colonne du bouton cliqu�
	 * 
	 * @return boolean vrai si NB_POUR_GAIN cases de suite sur la m�me colonne ont 
	 * le m�me caract�re que le caract�re courant
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
	 * V�rifier s'il y a NB_POUR_GAIN cases ayant le m�me caract�re que le 
	 * caract�re courant sur une diagonale gauche/gauche � partir des indices 
	 * i et j. Est appel�e par verifierGain()
	 * 
	 * @param i la ligne du bouton cliqu�
	 * @param j la colonne du bouton cliqu�
	 * 
	 * @return boolean vrai si NB_POUR_GAIN cases de suite sur la diagonale gauche/droite ont 
	 * le m�me caract�re que le caract�re courant
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
	 * V�rifier s'il y a NB_POUR_GAIN cases ayant le m�me caract�re que le 
	 * caract�re courant sur une diagonale droite/gauche � partir des indices 
	 * i et j. Est appel�e par verifierGain()
	 * 
	 * @param i la ligne du bouton cliqu�
	 * @param j la colonne du bouton cliqu�
	 * 
	 * @return boolean vrai si NB_POUR_GAIN cases de suite sur la diagonale droite/gauche ont 
	 * le m�me caract�re que le caract�re courant
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
		for (int x=1; i+x<matJeu.length && j-x>=0 && matJeu[i+x][j-x]==carCourant;x++)//R�viser, mais celui ci devrait etre OK
		{
			compteur++;
		}
		//Rev�rier la logique pour quand les boucles for pogne al limites des tableaux, ca marche pas comme je l'ai �crit l�
		return (compteur>=NB_POUR_GAIN);
	}
	
	/**
	 * M�thode qui fait l'ensemble des validations pour voir s'il y a un gain.
	 * 
	 * V�rifier s'il y a NB_POUR_GAIN cases ayant le m�me caract�re que le 
	 * caract�re courant dans les diff�rentes directions: horizontale, 
	 * verticale, et diagonales. Appelle les m�thodes n�cessaires pour faire le 
	 * travail.
	 * 
	 * @param i la ligne du bouton cliqu�
	 * @param j la colonne du bouton cliqu�
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
	 * Chaque case est repr�sent� par [] avec � l'int�rieur soit un espace,
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
