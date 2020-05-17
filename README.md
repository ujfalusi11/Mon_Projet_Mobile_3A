# Projet Mobile 3A - COVID-19
![image description](https://www.medeflyonrhone.fr/wp-content/uploads/2020/04/coronavirus-7.png)

Ce projet est une application android qui affiche les différentes informations relatives au covid 19 par pays. Pour chaque pays on peut visualiser l'état d'avancement des cas en temps réel à l'aide d'appels webservice à une API rest.

## Description

L'objectif du projet est d'afficher une liste à l'aide d'un Recyclerview et de voir le détail de chaque élément de la liste.
### Prerequisites

What things you need to install the software and how to install them
* Installation Android Studio  
* Récupération du lien de l'Api Covid-19 

### Consignes respectées:

* Architecture:
  * MVC
  * Singletons
  * Principes SOLID
* Gitflow
* CI/CD
* Affichage d'une liste dans un recyclerView
* Autres Fonctionnalités : 
  * list sorting
  * Stockage des données en cache
  * Barre de recherche
  * Affichage des pays par ordre de décroissance du nombre total de cas actifs 

## Fonctionnalités

### Page d'accueil
* Ecran d'accueil montrant la liste des pays avec le nombre total de cas actifs et le nombre total de cas rétablis
* La liste est rangée par ordres de grandeur suivant le total des cas actifs

![Alt text](/images/home.png "Optional Title")

### Barre de recherche
* Une barre de recherche permet de rechercher un pays par son nom

![Alt text](/images/searchBar.png "Optional Title")

* La vue s'actualise en fonction des lettres tapées au clavier

![Alt text](/images/searchBar2.png "Optional Title")


### Détail d'un pays
* Lorsqu'on clique sur un pays l'élément s'affiche comme suit:

![Alt text](/images/detail.png "Optional Title")

* La vue s'actualise en fonction des lettres tapées au clavier

![Alt text](/images/searchBar2.png "Optional Title")
