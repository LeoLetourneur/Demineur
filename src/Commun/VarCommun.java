package Commun;

/**
 * Classe des constantes et �numarations communes.
 * 
 * @author LETOURNEUR L�o
 * @since 1.0
 */
public class VarCommun {
	
	public static final String MSG_NOUVELLE_PARTIE = "GAME_MSG_NEWGAME";
	public static final String MSG_DECOUVRIR = "GAME_MSG_DISCOVER";
	public static final String MSG_QUITTER = "GAME_MSG_QUIT";
	
	/**
	 * Enum�ration repr�santant le type d'une case.
	 * 
	 */
	public static enum typeCase { 
		EMPTY(0), BOMB(1);
		public final int value;
		typeCase(int valeur){
		    this.value = valeur;
		}
	}
	
	/**
	 * Enum�ration repr�santant l'�tat d'une case.
	 * 
	 */
	public static enum etatCase { 
		COVER(0), DISCOVER(1), FLAG(2), QUESTION(3);
		public final int value;
		etatCase(int valeur){
		    this.value = valeur;
		}
	}
	
	/**
	 * Enum�ration repr�santant l'�tat du jeu.
	 * 
	 */
	public static enum etatJeu { 
		DEBUT(0), ENJEU(1), PERDU(2), GAGNE(3);
		public final int value;
		etatJeu(int valeur){
		    this.value = valeur;
		}
	}
	
	/**
	 * Enum�ration repr�santant le nombre de ligne, colonne et bombe en fonction de la difficult�.
	 * 
	 */
	public static enum nombreLCB { 
		DEBUTANT(9,9,10), INTERMEDIAIRE(16,16,40), DIFFICILE(16,30,99);
		public final int nbLigne;
		public final int nbColonne;
		public final int nbBombe;
		nombreLCB(int nbLigne, int nbColonne, int nbBombe){
		    this.nbLigne = nbLigne;
		    this.nbColonne = nbColonne;
		    this.nbBombe = nbBombe;
		}
	}

	/**
	 * Enum�ration repr�santant les diff�rents th�mes.
	 * 
	 */
	public static enum themeJeu { Mario, Caisse, Disco, Golf, Pacman }
}
