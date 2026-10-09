package gui;

import javax.swing.JFileChooser;
import java.io.File;

/**
 * Classe responsable de la sélection graphique (GUI) de fichiers et dossiers.
 * 
 * Rôle : remplacer les arguments du mode CLI par une sélection via fenêtre.
 * - Permet de sélectionner un fichier MP3
 * - Permet de sélectionner un dossier contenant des MP3
 * 
 * Cette classe ne fait PAS d'analyse, ne crée PAS de playlist :
 * elle se limite à l'interaction utilisateur (principe de responsabilité unique).
 */
public class SelectionFichierGUI {

    /**
     * Ouvre une fenêtre pour sélectionner un fichier (idéalement un MP3).
     * 
     * @return le fichier sélectionné, ou null si l'utilisateur annule
     */
    public File selectionnerFichierMP3() {

        JFileChooser selecteur = new JFileChooser();
        selecteur.setFileSelectionMode(JFileChooser.FILES_ONLY);

        int resultat = selecteur.showOpenDialog(null);

        if (resultat == JFileChooser.APPROVE_OPTION) {
            return selecteur.getSelectedFile();
        }
        return null;
    }

    /**
     * Ouvre une fenêtre pour sélectionner un dossier.
     * 
     * @return le dossier sélectionné, ou null si l'utilisateur annule
     */
    public File selectionnerDossier() {

        JFileChooser selecteur = new JFileChooser();
        selecteur.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int resultat = selecteur.showOpenDialog(null);

        if (resultat == JFileChooser.APPROVE_OPTION) {
            return selecteur.getSelectedFile();
        }
        return null;
    }

    /**
     * Ouvre une fenêtre pour choisir un emplacement de sauvegarde.
     * 
     * @param nomParDefaut nom proposé par défaut (ex: "playlist.xspf")
     * @return le fichier choisi (chemin de sortie), ou null si annulation
     */
    public File selectionnerFichierDeSortie(String nomParDefaut) {

        JFileChooser selecteur = new JFileChooser();
        selecteur.setSelectedFile(new File(nomParDefaut));

        int resultat = selecteur.showSaveDialog(null);

        if (resultat == JFileChooser.APPROVE_OPTION) {
            return selecteur.getSelectedFile();
        }
        return null;
    }
}
