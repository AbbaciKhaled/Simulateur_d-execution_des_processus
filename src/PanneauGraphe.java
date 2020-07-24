import java.awt.Color; //Importer la classe Color pour modifier les couleurs des textes et des boutons ….
import java.awt.Graphics; //pour afficher le graphe sous forme d'un réctangle pour chaque unité
import java.awt.Graphics2D; //pour que le graphe soit  en 2D
import javax.swing.JOptionPane; //pour utiliser les boites de dialogue
import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener; //ce package et celui au-dessus concernent l’utilisation de la méthode actionPerformed héritée de la classe actionListener.
import javax.swing.ImageIcon; //Pour inserer des images dans le panneau.
import javax.swing.JButton; //Pour inserer des boutons dans le panneau.
import javax.swing.JPanel; //car en utilise une classe héritée de la classe JPanel.


public class PanneauGraphe extends JPanel implements ActionListener {
private TableauDiagramme diagTab; //pour récuperer le tableau dans lequel le diagramme est stocker sous forme d'1 tableau 
String tpsRep; //pour récupper la chaine de caractere qui contient le message indiquant les valeurs des temps de réponses et de temps moyen
private JButton bt_tps; //utiliser pour afficher les temps de réponses et le temps moyen dans une boite de dialogue
private JButton bt_quitter; //récupper la chaine de caractere qui contient le message indiquant les valeurs des temps de réponses et de temps moyen

public PanneauGraphe(TableauDiagramme diagTab,String s)
{	//le seul constructeur de cette classe
	super(); 
	this.setLayout(null); //on utilise cette méthode pour placer des composés dans notre panneau
	this.diagTab=diagTab; //récuperer le tableau dans lequel le diagramme est stocker sous forme d'1 tableau
	tpsRep=s; //récupper la chaine de caractere qui contient le message indiquant les valeurs des temps de réponses et de temps moyen
	bt_tps=new JButton(); //instancier le bouton bt_tps
	bt_tps.setText("Temps De Réponses"); //donner un titre au bouton bt_tps
	bt_tps.setBounds(880, 520, 180, 20); //redimonsionner le bouton bt_tps
	bt_tps.addActionListener(this); //associer au bouton la méthode addActionListenner pour qu’il puisse se réagir aux actions utilisateurs.
	add(bt_tps); //afficher le boutons sur le panneau
	//tous ce que on a fait pour bt_tps, on le refait pour bt_quitter
	bt_quitter=new JButton();
	bt_quitter.setText("Quitter");
	bt_quitter.setBounds(880, 550, 180, 20);
	bt_quitter.addActionListener(this);
	add(bt_quitter);
}
protected void paintComponent(Graphics g) //avec cette méthode on transforme le tableau qui contient le diagramme sous forme d'1 tableau vers un diagramme graphique
{
	
	Graphics2D graphe = (Graphics2D) g; //on le force (avec un cast) à etre en 2D
	
	int i=0;
	int posDebut=144; //la position ou le diagramme commence a s'affiché
	graphe.drawImage(new ImageIcon("images/arriere_plan.jpg").getImage(), 0, 0, null); //on met une arrier plan a la fenetre (elle contient les axes x et y)
	
	while(diagTab.tabES[i]!=-2) //on transforme le tabES vers un graphe ou chaque unité sera représenté se forme d'un petit rectangle
	{
		if(diagTab.tabES[i]!=-1)
		{
		graphe.setColor(couleur(diagTab.tabES[i])); //on donne une couleur au rectangle selon le numero de processus on utilisant la fonction couleur
		graphe.fillRect(posDebut, 170, 20, 20); //on affiche le rectangle dans sa position
		}
		posDebut+=20; //on incremente posDebut pour que la prochaine unité s'affichera aprés la dérniere unité qu'on a affiché
		i++;
	}
	// on refait la meme chose tabCPU
	i=0;
    posDebut=144;
	while(diagTab.tabCPU[i]!=-2)
	{
		if(diagTab.tabCPU[i]!=-1)
		{
		graphe.setColor(couleur(diagTab.tabCPU[i]));
		graphe.fillRect(posDebut, 300, 20, 20);
		}
		
		posDebut+=20;
		i++;
	}
}

public int taille(int[] tab,int indice) //permet de savoir combien d'unité (CPU ou E/S) fait par un processus
{
	int taille=1;
	int val=tab[indice];
	indice++;
	while (tab[indice]==val)
	{
		taille++;
		indice++;
	}
	return taille;
}
public Color couleur(int x) //retourne une couleur selon le numero de processus qui est passé en parametre
{
	switch(x)
	{
	case 0:return Color.RED; 
	case 1:return Color.GREEN;
	case 2:return Color.YELLOW;
	case 3:return Color.BLUE;
	case 4:return Color.WHITE;
	case 5:return Color.ORANGE; 
	case 6:return Color.MAGENTA;
	case 7:return Color.CYAN;
	case 8:return new Color(1,102,149);
	case 9:return Color.DARK_GRAY;
	case 10:return Color.PINK; 
	case 11:return Color.lightGray;
	case 12:return new Color(76,70,8);
	case 13:return new Color(18,88,9);
	case 14:return new Color(207,255,17);
	case 15:return new Color(95,138,141);
	case -3:return Color.BLACK;
	default: return null;
	}
}
@Override
public void actionPerformed(ActionEvent e) {
	if(e.getSource()==bt_tps) //afficher les temps de réponses dans une boite de dialogue
	{
		JOptionPane.showMessageDialog(null, tpsRep);
	}
	if(e.getSource()==bt_quitter) //quitter le programme
	{
		System.exit(0);
	}
	
}

}
