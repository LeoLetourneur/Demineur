
public class Main {
	
	public static void main (String[] args){
	    System.out.println("Démineur par Léo Letourneur");
	    Model model = new Model();
		View view = new View(model);
		Controler controler =  new Controler(model, view);
		view.setVisible(true);
	   }

}
