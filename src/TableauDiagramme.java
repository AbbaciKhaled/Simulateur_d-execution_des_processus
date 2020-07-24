
public class TableauDiagramme { //on utilise cette classe pour representer le diagramme d'execution sous forme d'1 tableau
public int tabCPU[]; //pour stocker la partie CPU de diagramme
public int tabES[]; //pour stocker la partie E/S de diagramme
public static int cptCPU; //utiliser comme compteur pour tabCPU
public static int cptES; //utiliser comme compteur pour tabES

public TableauDiagramme() //constructeur 
{//initialiser les deux tableaux (taille max=1000) et les compteurs:
	tabCPU=new int[1000]; 
	tabES=new int[1000];
	cptCPU=0;
	cptES=0;
	for (int i=0;i<200;i++) //initialiser tous les cases des 2 tableaux a -1
	{
		tabCPU[i]=-1;
		tabES[i]=-1;
	}
}

public void ajouterTabCPU(int x) //pour ajouter un element (numero processus au tableau tabCPU)
{
	if (tabCPU[cptCPU]==-3 && x!=-3)
		cptCPU++;		
	tabCPU[cptCPU]=x;
	cptCPU++;
	
}

public void ajouterTabES(int x) //pour ajouter un element (numero processus au tableau tabES)
{
	tabES[cptES]=x;
	cptES++;
}
public void ajouterTcQuantum(int quantum) //pour ajouter une tc de quantum
{
	int x=cptCPU+quantum;
	if (cptCPU!=0)//
	{
	for (int i=cptCPU;i<cptCPU+quantum;i++)
	{
		if (tabCPU[i]==-3)
			x++;
	}
	tabCPU[x]=-3;
	}
}
}
