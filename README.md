Projet à réaliser : une application de gestion de base de données de livres (CRUD)

- Reprendre l'exercice 3 mais en traitant l'application sous forme de liste (ListActivity).Tous les livres de la bibliothèque sont présents dans la liste, on peut les trier par id, isbn, titre ou auteurs.
- Un click sur un élément de la liste ouvrira une page web donnant le descriptif complet du livre : tester par exemple :http://books.google.fr/books?isbn=9782746064607"
- Le Menu Quitter quitte l'application
- Le Menu Ajouter ouvre une nouvelle activité qui permet la saisie d'un nouveau livre qui après validation sera ajouté à la bibliothèque. La saisie du seul ISBN devrait être suffisante car il existe de nombreux sites internet qui sont capables de vous donner les renseignements sur un livre en leur fournissant l'ISBN (international standard book number) sous forme d'un fichier texte ordinaire ou xml.
- Essayer par exemple "http://www.worldcat.org/isbn/782746064607?page=endnote"
- Le Menu Modifier ouvre une nouvelle activité (après avoir sélectionné un livre) qui permettra de modifier les informations de ce livre
- Le Menu Supprimer devra permettre de supprimer 0 ,1 ou plusieurs livres : une solution consiste à ouvrir une nouvelle activité sous forme de liste à sélection multiple.
- On pourra mettre en place un Menu Scan qui devra permettre la saisie d'un livre par le code barre qui se trouve en général en quatrième de couverture. Il nécessite l'installation de l'application Barcode Scanner. Il ne sera vraiment utilisable que sur un smartphone ou une tablette possédant deux caméras (ce n'est pas le cas de la nexus 7)
- En cliquant sur More apparaîtrons les autres options que vous jugerez utile et notamment une recherche, sauvegarde et restauration de la base de données.