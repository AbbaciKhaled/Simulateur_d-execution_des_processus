import javax.swing.JFrame; //pour importer la classe JFrame définie par défaut.

public class FenetreGraphe extends JFrame {	//Est une sous classe héritée de la classe JFrame, on va l’utiliser pour créer la fenêtre de notre interface graphique.
	public FenetreGraphe() // le suel constructeur de cette classe
	{
		super(); //inisialisé tous les attributs de la super classe JFrame
		setTitle("Diagramme d’exécution!"); //doner un titre à la fenetre "Jeu du pendu!"
		setSize(1300,650); //redimonsionner la fenetre
		setResizable(false); //pour que la fenetre ne peut pas etre redimonsionner 
		setLocationRelativeTo(null); //pour que la fentre sera affiché en milieu de l'écran
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	  	//pour qu'on pourra fermer le programme lorsque la fenetre est fermée
	}
}
