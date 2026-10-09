# MP3 Playlist – Projet Java POO 

## Présentation
Ce projet a été réalisé dans le cadre du module de Programmation Orientée Objet en Java (L2 Informatique).  
Il consiste à développer une application permettant d’analyser des fichiers MP3 et de générer des playlists, en mode console et en mode graphique.

---

## Auteurs
- SIMYA Meriem – Groupe projet C05  
- LAIFAOUI Ines – Groupe projet C05

---

## Fonctionnalités

### Mode console (CLI)
- Analyse d’un fichier MP3
- Analyse récursive d’un dossier contenant des fichiers MP3
- Affichage des métadonnées
- Génération de playlists aux formats :
  - XSPF
  - JSPF
  - M3U8
- Reconnaitre bien les faux fichiers MP3

### Mode graphique (GUI)
- Sélection d’un fichier MP3
- Sélection d’un dossier (analyse récursive)
- Affichage des métadonnées
- Création et gestion de playlists
- Export de playlists (XSPF, JSPF, M3U8)
- Ajout de fichiers à une playlist existante

---

## Utilisation

### Lancement du mode console
```bash
java -jar cli.jar -h
```

### Lancement du mode graphique
```bash
java -jar gui.jar