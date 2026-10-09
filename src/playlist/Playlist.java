package playlist;

import java.util.ArrayList;
import java.util.List;
import core.FichierMP3;

/**
 * Représente une playlist de fichiers MP3.
 *
 * Elle permet d’ajouter des morceaux et de les exporter
 * dans différents formats.
 */
public class Playlist {
	
	/**
     * Nom de la playlist.
     */
    private String nom;
    /**
     * Liste des fichiers MP3 de la playlist.
     */
    private List<FichierMP3> fichiers;

    /**
     * Construit une playlist avec un nom.
     *
     * @param nom nom de la playlist
     */
    public Playlist(String nom) {
        this.nom = nom;
        this.fichiers = new ArrayList<>();
    }
    
    /**
     * Ajoute plusieurs fichiers MP3 à la playlist.
     *
     * @param fichiers liste de fichiers MP3
     */
    public void ajouterFichier(FichierMP3 fichier) {
        if (fichier != null) {
            fichiers.add(fichier);
        }
    }
    
    /**
     * Ajoute plusieurs fichiers MP3 à la playlist.
     * (Utilisé lorsque l'on explore un dossier complet)
     */
    public void ajouterFichiers(List<FichierMP3> fichiers) {
        if (fichiers != null) {
            for (FichierMP3 f : fichiers) {
                this.ajouterFichier(f); // évite les doublons de logique
            }
        }
    }

    /**
     * Supprime un fichier de la playlist.
     */
    public void supprimerFichier(FichierMP3 fichier) {
        fichiers.remove(fichier);
    }

    /**
     * Retourne la liste des fichiers de la playlist.
     *
     * @return liste de fichiers MP3
     */
    public List<FichierMP3> getFichiers() {
        return fichiers;
    }

    public String getNom() {
        return nom;
    }

    /**
     * Exportation de la playlist via un format (XSPF, JSPF, M3U8).
     */
    public void exporter(FormatPlaylist format, String chemin) throws Exception {
        format.exporter(this, chemin);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Playlist : " + nom + "\n");
        for (FichierMP3 f : fichiers) {
            sb.append(" - ").append(f.getNom()).append("\n");
        }
        return sb.toString();
    }
}
