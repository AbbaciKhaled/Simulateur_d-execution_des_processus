public class Processus { //Cette classe sert à ajouter un nouveau type Processus
private int numéroProcessus; //sert à stocker le numéro du processus
public TableauUnités tab;

//Le constructeur
public Processus(int num,int nb)
{
	numéroProcessus=num; //initialiser le numero de processus 
	tab =new TableauUnités(nb); //associe au processus un tableau qui contient le nombre de traitement et E/S qui va faire
}

//Les setters et les getters
public int getNuméroProcessus() {//retourne le numero de processus
	return numéroProcessus;
}
public void setNuméroProcessus(int numéroProcessus) {//modifie le numero de processus
	this.numéroProcessus = numéroProcessus;
}
public TableauUnités getTab() {//retourne le tableu qui contient les taches faites par le processus
	return tab;
}
public void setTab(TableauUnités tab) {//modifie le tableu qui contient les taches faites par le processus
	this.tab = tab;
}
}