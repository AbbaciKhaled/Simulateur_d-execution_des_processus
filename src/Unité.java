public class Unité { //Cette classe sert à instancier d’un nouveau type d'unité CPU ou E/S
  private int numeroProcessus; //représente le numéro du processus au quel on va ajouter l'unité
  private String typeUnité; //représente le type d'Unité CPU ou E/S
  private int nbUnité; //sert à stoker le nombre des Unités CPU ou E/S d’un processus
  private int tempsDebut; //sert à sauvegarder le temps du début de l’une des Unités d’un processus donné (l'instant ou cette tache va commencer)
  
  //Les setters et les getters
  
public String getTypeUnité() {
	return typeUnité;
}
public void setTypeUnité(String typeUnité) {
	this.typeUnité = typeUnité;
}
public int getNbUnité() {
	return nbUnité;
}
public void setNbUnité(int nbUnité) {
	this.nbUnité = nbUnité;
}
public int getNumeroProcessus() {
	return numeroProcessus;
}
public void setNumeroProcessus(int num) {
	this.numeroProcessus = num;
}
public int getTempsDebut() {
	return tempsDebut;
}
public void setTempsDebut(int tempsDebut) {
	this.tempsDebut = tempsDebut;
}

}