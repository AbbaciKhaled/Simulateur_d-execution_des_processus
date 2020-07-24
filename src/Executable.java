import javax.swing.JOptionPane; //permet d'utiliser les boites de dialogues


public class Executable {

public static int maximum(int x,int y) //retourne le max entre deux variables
{
	if (x>y)
		return x;
	else return y;
}
public static int minimum(int x,int y)//retourne le min entre deux variables
{
	if (x>y)
		return y;
	else return x;
}

		public static void main(String[] args) { //le main
			int mode=0; //si mode =1 pour multiprogrammé et mode =2 pour temps partagé
			int nbProcessus=0; //pour stocker le nombre de processus
			String lire; //pour récuperer les valeur tapée par l'utilisateurs (fonctione comme un scanner de la boite de dialogue)
			do{ //refaire cette demande
			lire= JOptionPane.showInputDialog("Veuillez choisir un mode d'éxécution, tapez:\n1-MultiProgrammé\n2-Temps Partagé"); //demander à l'utilisateur de choisir un mode
			mode=Integer.parseInt(lire);		//recuperer la valeur tapée par l'utilisateur et la transformer en un entier
			}while(mode!=1 && mode!=2); //l'utilisateur a tapé la bonne valeur et donc il a choisi un mode


			TableauDiagramme diagTab=new TableauDiagramme(); //pour stocker le diagramme sous forme d'un tableau
			String s=""; //pour mettre les valeurs de temps de réponses et de temps moyen dnas une chaine de caractere afin de les affichés dans une boite de dialogue
			int tc =-1; //si tc =1 on utilise les taches de controles si =2 non
			if (mode==1) //cas multiprogrammé
			{
			    do
			    	{lire= JOptionPane.showInputDialog("Veuillez donner le nombre de processus (nb max=15):"); //demander à l'utilisateur de donner le nombre de processus qui ne dépasse pas 15 processus
				nbProcessus=Integer.parseInt(lire); //recuperer la valeur tapée par l'utilisateur et la transformer en un entier
			    	}while(nbProcessus<=0 || nbProcessus>15);//l'utilisateur a tapé la bonne valeur et donc il a donné le nombre de processus
				Processus[] p=new Processus[nbProcessus]; //instancier un tableau pour stocker les données de chaque processus
			    int[] tabTempsRep=new int [nbProcessus]; //instancier un tableau d'entiers pour stocker le temps de réponse de chaque processus
				
				do {
			    lire= JOptionPane.showInputDialog("Voulez vous utiliser une TC?\nTapez:\n1-OUI\n2-NON"); //demander à l'utilisateur s'il veut utiliser une TC
				tc=Integer.parseInt(lire);  //recuperer la valeur tapée par l'utilisateur et la transformer en un entier
				}while (tc!=2 && tc!=1); //l'utilisateur a tapé la bonne valeur et donc il a donné une réponse par oui ou non
				for(int i=0;i<nbProcessus;i++) //remplir le tableau par les données de chque processus
				{
				    lire= JOptionPane.showInputDialog("Combien de traitement fait le processus numero"+i+"?"); //demander le nombre de traitemnt (tache CPU) fait par le ieme processus
				    int n=Integer.parseInt(lire);	//recuperer la valeur tapée par l'utilisateur et la transformer en un entier
				    lire= JOptionPane.showInputDialog("Combien d'E/S fait le processus numero"+i+"?"); // demander le nombre de tache E/S fait par le Processus
				    n=n+Integer.parseInt(lire);	//recuperer la valeur tapée par l'utilisateur et la transformer en un entier
				    p[i]=new Processus(i,n); //instancier dans la case de ce processus un nouveau objet Processus 
				    do{ 
				    lire= JOptionPane.showInputDialog("Par quel type d'unité commence le processus numero "+i+"?\nTapez:\n1-CPU\n2-E/S"); //demander à l'utilisateur de donner la premiere tache effectuée par le ieme processus
				    }while (Integer.parseInt(lire)!=1 && Integer.parseInt(lire)!=2);
				    if (Integer.parseInt(lire)==1) //faire un test pour la tache avec laquelle commence le processus 
				    {
						p[i].tab.remplir("CPU",i); //la méthode remplir pour stocker les valerus des taches CPU et E/S fait par le processus (voir la classe tableauUnité)
				    }
				    else p[i].tab.remplir("E/S",i);
				}
				File fileES=new File(); //instancier une file E/S
				File fileCPU=new File();  //instancier une file CPU
				Unité cpu=null,es=null; //pour récuperer processus enfiler de fileCPU ou file E/S
				for (int i=0;i<nbProcessus;i++) //inisialiser la file avec les premiers taches effectuée par chaque processus selon leurs ordre de stockage
				{
					if (p[i].tab.unit[0].getTypeUnité().equals("CPU"))
					    fileCPU.enfiler(p[i].tab.unit[0]);
					if (p[i].tab.unit[0].getTypeUnité().equals("E/S"))
						fileES.enfiler(p[i].tab.unit[0]);
						p[i].tab.supprimerUnité(); //supprimer chaque tache -d'un processus- qu'on a enfiler de tableau
				}
				if (tc==1) //cas avec TC on ajoute dans la premiere case de tableauDiagramme diagTab la TC init 
		    	{diagTab.ajouterTabCPU(-3);
		    	 diagTab.cptES++; //on incrimente le compteur de E/S pour qu'il commence de 1
		    	}
				while (!fileES.fileVide() || !fileCPU.fileVide()) //tant que les deux files ne seront pas vide alors on a pas encore terminé de construire le diagramme
				{
					
					if (!fileCPU.fileVide()) //si fileCPU non vide alors il y a un processus qui fait de traitement 
				       {
						cpu=fileCPU.defiler(); //on defile un processus qui fait de traitement

						int n=cpu.getNbUnité(); 
					       diagTab.cptCPU=maximum(diagTab.cptCPU,cpu.getTempsDebut()); //pour assuré qu'il travaille en paralléle avec E/S
					       for (int i=0;i<n;i++) //ajouter les taches CPU dans diagTAB
					         {
					    	    diagTab.ajouterTabCPU(cpu.getNumeroProcessus());
					    	    cpu.setNbUnité(cpu.getNbUnité()-1);
					         }
					       if (p[cpu.getNumeroProcessus()].tab.getTaille()!=0) //si il y a une tache E/S que ce processus va faire
					       {
					    	   if (tc==1) //cas tc=1
					       	   diagTab.ajouterTabCPU(-3); //on ajoute tache demande E/S
					    	   p[cpu.getNumeroProcessus()].tab.unit[0].setTempsDebut(TableauDiagramme.cptCPU);
					    	   fileES.enfiler(p[cpu.getNumeroProcessus()].tab.unit[0]); //en enfile la tache E/S que ce processus doit faire dans la file E/S
					    	   p[cpu.getNumeroProcessus()].tab.supprimerUnité(); //on la supprime de tableau desn données de Processus
					       }
					       else { //sinon dans ce cas le processus est fini alors on stocke son temps de réponse
					    	   tabTempsRep[cpu.getNumeroProcessus()]=diagTab.cptCPU; 
					    	   if (tc==1) //si tc=1 
					       	diagTab.ajouterTabCPU(-3);//alors on ajoute la tache de controle de fin du programme
					       }
				       }
					if (!fileES.fileVide()) //si fileES non vide alors il y a un processus qui fait une E/S 
				       {
						
						es=fileES.defiler(); //on defile un processus qui fait une E/S
						
						int n=es.getNbUnité();
					       diagTab.cptES=maximum(diagTab.cptES,es.getTempsDebut()); ////pour assuré qu'il travaille en paralléle avec CPU
					       for (int i=0;i<n;i++) //ajouter les taches CPU dans diagTAB
					         {
					    	    diagTab.ajouterTabES(es.getNumeroProcessus());
					    	    es.setNbUnité(es.getNbUnité()-1);
					         }
					       if (tc==1) //cas avec tc on ajoute a la fin la tache de controle de fin E/S
					    	{
					       diagTab.tabCPU[diagTab.cptES]=-3;
				    	   diagTab.cptES++;
				    	    }
					       if (p[es.getNumeroProcessus()].tab.getTaille()!=0) //si le processus fait encore des tache de traitement (CPU)
					       {
					    	  p[es.getNumeroProcessus()].tab.unit[0].setTempsDebut(TableauDiagramme.cptES);
					    	   fileCPU.enfiler(p[es.getNumeroProcessus()].tab.unit[0]); //on l'enfile dans fileCPU
					    		p[es.getNumeroProcessus()].tab.supprimerUnité(); //supprimer la tache de tableau de donnés de processus
					       }
					       else { //sinon si c'est la fin de processus
					    	   if(tc==1) tabTempsRep[es.getNumeroProcessus()]=diagTab.cptES-1; //cas avec tc on lui ajoute la tc de fin du programme
					    	   else tabTempsRep[es.getNumeroProcessus()]=diagTab.cptES; //on stock son temps de réponse 
					       }		
				       
				       }

	}
	double tempsMoy=0; //utiliser pour calculer le temps de réponse moyen
	for(int i=0;i<nbProcessus;i++) {
		s+="le temps de réponse du processus n°"+i+" est = "+tabTempsRep[i]+"\n"; //pour afficher les temps de réponse dans une boite de dialogue si l'utilisateur le veut
		tempsMoy+=tabTempsRep[i]; //somme de temps de réponses des processus
	}
	tempsMoy=tempsMoy/nbProcessus;  //calculer le temps de réponse moyen
		s+="le temps de réponse moyen = "+tempsMoy; //pour afficher le temps de réponse moyen si l'utilisateur le veut
	
	
	}


if (mode==2){ 
	//cas Temps partagés, dans ce cas tous ce qu'on a fait dans le multiprogramé se répete sauf qu'on a ajouté le quantum
	//Et donc le TC de quantum ainsi qu'il y a quelque cas en plus qu'on a ajouté pour régler les problémes de parallélisme causé par le quantum
	int quantum=0; //pour stocker la valeur de quantum
	do{
	lire= JOptionPane.showInputDialog("Veuillez fixer la valeur du quantum:"); // on demande à l'utilisateur de taper la valeur de quantum
   quantum=Integer.parseInt(lire); //recuperer la valeur tapée par l'utilisateur et la transformer en un entier
	}while(quantum<0); //refaire jusqu'à ce que la valeur de quantum est positive
	do
 	{lire= JOptionPane.showInputDialog("Veuillez donner le nombre de processus (nb max=15):"); //demander à l'utilisateur de donner le nombre de processus qui ne dépasse pas 15 processus
	nbProcessus=Integer.parseInt(lire); //recuperer la valeur tapée par l'utilisateur et la transformer en un entier
 	}while(nbProcessus<=0 && nbProcessus>15);//l'utilisateur a tapé la bonne valeur et donc il a donné le nombre de processus
	 Processus[] p=new Processus[nbProcessus]; //instancier un tableau pour stocker les données de chaque processus
	 int[] tabTempsRep=new int [nbProcessus]; //instancier un tableau d'entiers pour stocker le temps de réponse de chaque processus
	 do {
		    lire= JOptionPane.showInputDialog("Voulez vous utiliser une TC?\nTapez:\n1-OUI\n2-NON"); //demander à l'utilisateur s'il veut utiliser une TC
			tc=Integer.parseInt(lire);  //recuperer la valeur tapée par l'utilisateur et la transformer en un entier
			}while (tc!=2 && tc!=1); //l'utilisateur a tapé la bonne valeur et donc il a donné une réponse par oui ou non
		for(int i=0;i<nbProcessus;i++) //remplir le tableau par les données de chque processus
		{
		    lire= JOptionPane.showInputDialog("Combien de traitement fait le processus numero"+i+"?"); //demander le nombre de traitemnt (tache CPU) fait par le ieme processus
		    int n=Integer.parseInt(lire);	//recuperer la valeur tapée par l'utilisateur et la transformer en un entier
		    lire= JOptionPane.showInputDialog("Combien d'E/S fait le processus numero"+i+"?"); // demander le nombre de tache E/S fait par le Processus
		    n=n+Integer.parseInt(lire);	//recuperer la valeur tapée par l'utilisateur et la transformer en un entier
		    p[i]=new Processus(i,n); //instancier dans la case de ce processus un nouveau objet Processus 
		    do{ 
		    lire= JOptionPane.showInputDialog("Par quel type d'unité commence le processus numero "+i+"?\nTapez:\n1-CPU\n2-E/S"); //demander à l'utilisateur de donner la premiere tache effectuée par le ieme processus
		    }while (Integer.parseInt(lire)!=1 && Integer.parseInt(lire)!=2);
		    if (Integer.parseInt(lire)==1) //faire un test pour la tache avec laquelle commence le processus 
		    {
				p[i].tab.remplir("CPU",i); //la méthode remplir pour stocker les valerus des taches CPU et E/S fait par le processus (voir la classe tableauUnité)
		    }
		    else p[i].tab.remplir("E/S",i);
		}
		File fileES=new File(); //instancier une file E/S
		File fileCPU=new File();  //instancier une file CPU
		Unité cpu=null,es=null; //pour récuperer processus enfiler de fileCPU ou file E/S
	    boolean es2=false; //utiliser pour assurer le parallélisme entre CPU et E/S
	
	    for (int i=0;i<nbProcessus;i++) //inisialiser la file avec les premiers taches effectuée par chaque processus selon leurs ordre de stockage
		{
			if (p[i].tab.unit[0].getTypeUnité().equals("CPU"))
			    fileCPU.enfiler(p[i].tab.unit[0]);
			if (p[i].tab.unit[0].getTypeUnité().equals("E/S"))
				fileES.enfiler(p[i].tab.unit[0]);
				p[i].tab.supprimerUnité(); //supprimer chaque tache -d'un processus- qu'on a enfiler de tableau
		}
		    
	    if (tc==1) //cas avec TC on ajoute dans la premiere case de tableauDiagramme diagTab la TC init 
    	{diagTab.ajouterTabCPU(-3);
    	 diagTab.cptES++; //on incrimente le compteur de E/S pour qu'il commence de 1
    	}
	while (!fileES.fileVide() || !fileCPU.fileVide()) //tant que les deux files ne seront pas vide alors on a pas encore terminé de construire le diagramme
	{
		
		if (!fileCPU.fileVide()) //si fileCPU non vide alors il y a un processus qui fait de traitement
	       {cpu=fileCPU.defiler(); //défiler un Processus
	       int n=minimum(cpu.getNbUnité(),quantum);
	       if (n>=quantum && tc==1)
	       {
	    	   diagTab.ajouterTcQuantum(quantum);; //ajouter la tache de controle de quantum
	       }
	       diagTab.cptCPU=maximum(diagTab.cptCPU,cpu.getTempsDebut()); //pour assuré qu'il travaille en paralléle avec E/S
	       for (int i=0;i<n;i++) //ajouter les taches CPU dans diagTAB
	         {
	    	    diagTab.ajouterTabCPU(cpu.getNumeroProcessus());
	    	    cpu.setNbUnité(cpu.getNbUnité()-1);
	         }
	       if (cpu.getNbUnité()==0) //donc le processus a fini la tache CPU
	       {
	       if (p[cpu.getNumeroProcessus()].tab.getTaille()!=0) 
	       {
	    	   if (tc==1) //cas avec tc
			       	diagTab.ajouterTabCPU(-3); //on ajoute la tache de demande E/S  
	    	   p[cpu.getNumeroProcessus()].tab.unit[0].setTempsDebut(TableauDiagramme.cptCPU);
	    	   fileES.enfiler(p[cpu.getNumeroProcessus()].tab.unit[0]); //on enfile la tache E/S que ce processus va faire
	    		p[cpu.getNumeroProcessus()].tab.supprimerUnité(); //on la supprime de tableau des données de processus
	       }
	       else { //sinon dans ce cas le processus a fini
	    	   if (tc==1) { //si tc=1 
	    	   diagTab.ajouterTabCPU(-3); //alors on ajoute tc fin processus
	    	   tabTempsRep[cpu.getNumeroProcessus()]=diagTab.cptCPU-1;} //on stocke son temps de réponse
	    	   else  tabTempsRep[cpu.getNumeroProcessus()]=diagTab.cptCPU; //tc !=1 on stocke seulement le temps de reponse
	           }
	       }
	       else //ici c'est le cas ou le processus arréte la tache cpu a cause de quantum
	       { //on fait des traitements on parallele avec la file cas E/S
	    	   if (fileES.fileVide())
	    		   fileCPU.enfiler(cpu);	
	       }
	      if ((diagTab.cptES-diagTab.cptCPU<=quantum && es2==true) || (fileCPU.fileVide() && cpu.getNbUnité()==0 && es2==true))
	       {
				fileCPU.enfiler(p[es.getNumeroProcessus()].tab.unit[0]);  
  				es2=false;
	    		p[es.getNumeroProcessus()].tab.supprimerUnité();

	       }
	       
	    	   if (es2==true)
	    	   {
	    		   if (cpu.getNbUnité()!=0)
 				fileCPU.enfiler(cpu);  
	    	   }
	      
	       }

		if (es2==false)
		{
		if (!fileES.fileVide())
	       {es=fileES.defiler();
	       int n=es.getNbUnité();
	       diagTab.cptES=maximum(diagTab.cptES,es.getTempsDebut());
	       for (int i=0;i<n;i++)
	         {
	    	    diagTab.ajouterTabES(es.getNumeroProcessus());
	    	    es.setNbUnité(es.getNbUnité()-1);
	         }
	       if (tc==1)
	       {
	    	    diagTab.tabCPU[diagTab.cptES]=-3;
	            diagTab.cptES++;
	       }
		       	if (p[es.getNumeroProcessus()].tab.getTaille()!=0)
	       {
	   		  p[es.getNumeroProcessus()].tab.unit[0].setTempsDebut(TableauDiagramme.cptES);
	    	 
	   		 if (fileCPU.fileVide())
	    	  {
	   			  if (cpu!=null && cpu.getNbUnité()!=0)
	   			  {
	   				  if(diagTab.cptES-diagTab.cptCPU>0)
	   				  {
	   		    		  fileCPU.enfiler(cpu);
	   		    		  fileCPU.enfiler(p[es.getNumeroProcessus()].tab.unit[0]);
	   				  }
	   				  else
	   				  {
	   		    		  fileCPU.enfiler(p[es.getNumeroProcessus()].tab.unit[0]);
	   		    		  fileCPU.enfiler(cpu);
	   				  }
	   			  }
	   			  else
	    		  fileCPU.enfiler(p[es.getNumeroProcessus()].tab.unit[0]);
		    		p[es.getNumeroProcessus()].tab.supprimerUnité();
	    	  }
	    	 else
	    	  {
	   		  if (cpu.getNbUnité()==0)
	   		  {
	   			    if (diagTab.cptES-diagTab.cptCPU>quantum)
	   			    	es2=true;
	   			    else
	   			    {
	   				fileCPU.enfiler(p[es.getNumeroProcessus()].tab.unit[0]);
		    		p[es.getNumeroProcessus()].tab.supprimerUnité();

	   			    }
	   		   }
	    	   
	   		  else
	   		  {
	   			  if (diagTab.cptCPU<diagTab.cptES)
	   				{

                       if (diagTab.cptES-diagTab.cptCPU>quantum)
		   			    	es2=true;
		   			    else
		   			    {
	   				  fileCPU.enfiler(cpu);
		   	    	   fileCPU.enfiler(p[es.getNumeroProcessus()].tab.unit[0]);
			    		p[es.getNumeroProcessus()].tab.supprimerUnité();
		   			    }
	   				}
	   			  else
	   			  {

		   			 
	   	    	   fileCPU.enfiler(p[es.getNumeroProcessus()].tab.unit[0]);
	   				  fileCPU.enfiler(cpu);
	  	    		p[es.getNumeroProcessus()].tab.supprimerUnité();

	   			  }
	   		  }
	           }
	    	  }
		       	else {
		    	    if (cpu.getNbUnité()!=0) fileCPU.enfiler(cpu);
		    	    if(tc==1) tabTempsRep[es.getNumeroProcessus()]=diagTab.cptES-1;
		    	    else tabTempsRep[es.getNumeroProcessus()]=diagTab.cptES;
	                   }
	       }
		}
		
}
	
	double tempsMoy=0; //utiliser pour calculer le temps de réponse moyen
	for(int i=0;i<nbProcessus;i++) {
		s+="le temps de réponse du processus n°"+i+" est = "+tabTempsRep[i]+"\n"; //pour afficher les temps de réponse dans une boite de dialogue si l'utilisateur le veut
		tempsMoy+=tabTempsRep[i]; //somme de temps de réponses des processus
	}
	tempsMoy=tempsMoy/nbProcessus;  //calculer le temps de réponse moyen
		s+="le temps de réponse moyen = "+tempsMoy; //pour afficher le temps de réponse moyen si l'utilisateur le veut
	
	
	}
diagTab.ajouterTabES(-2); //ajouter a la fin du tableau TabES la valeur -2 comme indice de fin
if (tc==1 && diagTab.cptCPU<diagTab.cptES) //cas avec tc l'indice de fin de cpu doit étre placé aprés la fin de la derniere E/S (dans le cas ou la derniere tache qui se termine est une E/S)
{
	diagTab.cptCPU=diagTab.cptES;
}
diagTab.ajouterTabCPU(-2); //ajouter a la fin du tableau TabCPU la valeur -2 comme indice de fin

FenetreGraphe fenetre=new FenetreGraphe();  //instancier une nouvelle fenetre pour afficher le diagrame
PanneauGraphe panneau=new PanneauGraphe(diagTab,s); //instancier un nouveau panneau dans lequel le TableauDiagramme tabDiag sera transformé à un diagramme graphique qui sera afficher
fenetre.add(panneau); //associer à la fenetre le panneau  
fenetre.setVisible(true); //rendre la fenetre visible




}
}