package application;
import javafx.scene.paint.Color;

/**
 * Classe repr�sentant un joueur avec nom, gain, et couleur.
 */
public class Joueur 
{
	/**
	 * Nom par d�faut du joueur	
	 */
	public static final String NOM_DEFAUT="Toto";
	
	/**
	 * Longueur minimum d'un nom de joueur
	 */
	public static final int LONGUEUR_NOM_MIN=4;
	
	/**
	 * Longueur maximum d'un nom de joueur
	 */
	public static final int LONGUEUR_NOM_MAX=15;
	
	/**
	 * Gain de d�part
	 */
	public static final int GAIN_DE_DEPART=0;
	
	/**
	 * Couleur par d�faut du joueur
	 */
	public static final Color COULEUR_DEFAUT=Color.WHITE;
	
	/**
	 * Le nom du joueur
	 */
	private String nom="";
	
	/**
	 * Les parties gagn�es par le joueur
	 */
	private int gain=0;
	
	/**
	 * La couleur du joueur
	 */
	private Color couleur=null;
	
	/**
	 * Constructeur avec param�tre nom. Met les gains du joueur � 0 et la couleur par d�faut.
	 *
	 *@param pNom le nom du joueur
	 */
	public Joueur(String pNom)
	{
		nom = pNom;
		gain=GAIN_DE_DEPART;
		couleur=COULEUR_DEFAUT;
	}
	
	/**
	 *Constructeur avec param�tres nom, couleur et gain. 
	 *
	 *@param pNom le nom du joueur
	 *@param pCouleur la couleur du joueur
	 *@param pGain les gains du joueur au d�part
	 */
	public Joueur(String pNom, Color pCouleur, int pGain)
	{
		
		boolean ok = setNom(pNom)&&setCouleur(pCouleur)&&setGain(pGain);
		
		if (!ok)
		{
			setNom(Joueur.NOM_DEFAUT);
			setCouleur(Joueur.COULEUR_DEFAUT);
			setGain(Joueur.GAIN_DE_DEPART);
		}
	}
	
	/**
	 * Obtenir le nom du joueur
	 * 
	 * @return String, le nom du joueur
	 */
	public String getNom()
	{
		return nom;
	}
	
	/**
	 * Modifier le nom du joueur.
	 * 
	 * @param pNom le nouveau nom du joueur
	 * 
	 * @return boolean, vrai si le nom a �t� modifi�
	 */
	public boolean setNom(String pNom)
	{
		boolean ok=false;
		
		if (validerNom(pNom))
		{
			ok=true;
			nom=pNom;
		}
		
		return ok;
	}
	
	/**
	 * Valider le nom du joueur (n'est pas null et poss�de une longueur valide)
	 * 
	 * @param pNom le nom � valider
	 * 
	 * @return boolean, vrai si le nom est valide
	 */
	public static boolean validerNom(String pNom)
	{
		return (pNom.length()<=LONGUEUR_NOM_MAX && pNom.length()>=LONGUEUR_NOM_MIN && pNom.length()!=0);
	}
	
	/**
	 * Obtenir la valeur des gains du joueur.
	 * 
	 * @return int, la valeur des gains
	 */
	public int getGain()
	{
		return gain;
	}
	
	/**
	 * Modifier la valeur des gains du joueur
	 * 
	 * @param pGain la nouvelle valeur des gains du joueur
	 * 
	 * @return boolean, vrai si la valeur a �t� modifi�e
	 */
	public boolean setGain(int pGain)
	{
		boolean ok=validerGain(pGain);
		
		if (ok)
		{
			gain=pGain;
		}
		return ok;
	}
	
	/**
	 * Valider la valeur des gains du joueur. Doit �tre positive, 0 inclus.
	 * 
	 * @param pGain la valeur � valider
	 * 
	 * @return boolean, vrai si la valeur est valide
	 */
	public static boolean validerGain(int pGain)
	{
		return (pGain>=0);
	}
	
	/**
	 * Obtenir la couleur du joueur
	 * 
	 * @return Color, la couleur du joueur
	 */
	public Color getCouleur()
	{
		return couleur;
	}
	
	/**
	 * Modifier la valeur de la couleur du joueur
	 * 
	 * @param pCouleur la nouvelle couleur
	 * 
	 * @return boolean, vrai si la couleur a �t� modifi�e
	 */
	public boolean setCouleur(Color pCouleur)
	{
		boolean ok = validerCouleur(pCouleur);
		
		if (ok)
		{
			couleur=pCouleur;
		}
		
		return ok;
	}
	
	/**
	 * Valider la couleur du joueur
	 * 
	 * @param pCouleur la couleur � valider
	 * 
	 * @return boolean, vrai si le pointeur de l'objet n'est pas nul
	 */
	public static boolean validerCouleur(Color pCouleur)
	{
		return (pCouleur != null);
	}
	
	/**
	 * Incr�mente les gains de 1
	 */
	public void incrementerGain()
	{
		gain++;
	}
	
	/**
	 * Construit une chaine de caract�re repr�sentant l'�tat de l'objet Joueur
	 * 
	 * @return String, la chaine construite contenant nom et gains
	 */
	public String toString()
	{
		return this.getNom() + ": " + this.getGain() + " Gain(s)";
	}
	
	/**
	 * Point d'entr�e de la classe Joueur.
	 * 
	 * @param args un tableau de "string", les arguments
	 */
	public static void main(String[]args)
	{
		Joueur joueur1 = new Joueur("David");
		System.out.println(joueur1);
		joueur1.incrementerGain();
		System.out.println(joueur1);
	}
}
