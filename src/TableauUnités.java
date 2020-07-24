import javax.swing.JOptionPane; //pour utiliser les boites de dialogue
public class TableauUnités {
public Unité[] unit; //tableau d'unité
private int taille; //taille de tableau
public TableauUnités(int taille) //constructeur pour initialiser un tableau d'unité unit avec une taille donner comme parametre
{
	unit=new Unité[taille];
	this.taille=taille;
}
public String switchType(String type){ //si la tache effectué est un tache E/S alors la prochaine sera CPU et inversement
	if (type.equals("CPU"))
		return "E/S";
	return "CPU";
}
public void remplir(String type,int num) //remplir les données de chaque processus dans un tableau de type Unite
{
	for (int i=0;i<taille;i++)
	{
		unit[i]=new Unité();
		unit[i].setTypeUnité(type);
		String l= JOptionPane.showInputDialog("Donner le nombre d'unités "+type+":");
		unit[i].setNbUnité(Integer.parseInt(l));
		type=switchType(type);
        unit[i].setNumeroProcessus(num);
        unit[i].setTempsDebut(0);
        }
}
public void supprimerUnité() //supprimer une Unité de tableau TableauUnité
{
	for (int i=0;i<taille-1;i++)
	{
		unit[i]=unit[i+1];
	}
	taille--;
}
public int getTaille() //retourne la taille
{
	return taille;
}
    
}
