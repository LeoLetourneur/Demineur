package Commun;

public class VarCommun {

	public final static int NB_CASE = 15;
	public final static int NB_BOMBE = 15;
	
	public static enum typeCase { 
		EMPTY("empty",0), BOMB("bombe",1);
		public final String name;
		public final int value;
		  
		typeCase(String name, int valeur){
		    this.name = name;
		    this.value = valeur;
		}
	}
	
	public static enum etatCase { 
		COVER("cover",0), DISCOVER("discover",1), FLAG("flag",2), QUESTION("question",3);
		public final String name;
		public final int value;
		  
		etatCase(String name, int valeur){
		    this.name = name;
		    this.value = valeur;
		}
	}

}
