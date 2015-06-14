package Commun;

public class VarCommun {
	
	public static final String MSG_NOUVELLE_PARTIE = "GAME_MSG_NEWGAME";
	public static final String MSG_DECOUVRIR = "GAME_MSG_DISCOVER";
	public static final String MSG_QUITTER = "GAME_MSG_QUIT";
	
	public static enum typeCase { 
		EMPTY(0), BOMB(1);
		public final int value;
		typeCase(int valeur){
		    this.value = valeur;
		}
	}
	
	public static enum etatCase { 
		COVER(0), DISCOVER(1), FLAG(2), QUESTION(3);
		public final int value;
		etatCase(int valeur){
		    this.value = valeur;
		}
	}
	
	public static enum etatJeu { 
		DEBUT(0), ENJEU(1), PERDU(2), GAGNE(3);
		public final int value;
		etatJeu(int valeur){
		    this.value = valeur;
		}
	}
	
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

	public static enum themeJeu { Mario, Caisse, Disco, Golf, Pacman }
}
