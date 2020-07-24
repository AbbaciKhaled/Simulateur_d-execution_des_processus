public class File { //Cette classe sert à l’implémentation d’une fille d’Unités
private int tete;
private int queue;
Unité[] file;
public File()
{
	
	tete=-1;
	queue=-1;
	file=new Unité[100];
}
public void enfiler(Unité element) //enfiler element en tête de file si cette dernière est vide et en queue de file sinon
{
	if (queue<100)
	{
		if (tete==-1)
			tete++;
		queue++;
		file[queue]=element;
	}
}
public Unité defiler() //sert à incrémenter tete puis retourner le contenu de la file à l’indice tete-1
{Unité u;
	if (tete==queue)
	u=file[queue];
	else u=file[tete];
    tete++;
	return u;
}
public Unité teteFile() //sert à retourner l’Unité en tête de file
{
	return file[tete];
}
public boolean fileVide() //sert à retourner true si la file est vide et false sinon
{
	if (tete>queue || tete==-1) //tester si la file est vide
		return true;
	return false;
}
}