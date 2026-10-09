package core;

import java.util.ArrayList;
import java.util.List;

/**
 * Représente un dossier contenant des fichiers MP3.
 *
 * <p>
 * Cette classe permet de stocker une collection de fichiers MP3
 * issus d’une exploration récursive.
 * </p>
 */
public class Dossier {

    // === Attributs ===
    private String chemin;                     // chemin complet sur le disque
    private String nom;                        // nom du dossier (ex : "Music")
    private List<FichierMP3> fichiersMP3;      // liste des fichiers MP3 contenus directement dans ce dossier
    private List<Dossier> sousDossiers;        // liste des sous-dossiers

    // === Constructeur ===
    //  Correction : ordre des paramètres cohérent (chemin, nom)
    public Dossier(String chemin, String nom) {
        this.chemin = chemin;
        this.nom = nom;
        this.fichiersMP3 = new ArrayList<>();
        this.sousDossiers = new ArrayList<>();
    }

    // === Méthodes métier ===

    /** Ajoute un fichier MP3 à la liste des fichiers du dossier. */
    public void ajouterFichier(FichierMP3 f) {
        if (f != null) { // Evite d'ajouter un fichier null
            fichiersMP3.add(f);
        }
    }

    /** Ajoute un sous-dossier à la liste des sous-dossiers. */
    public void ajouterSousDossier(Dossier d) {
        if (d != null) {
            sousDossiers.add(d);
        }
    }

    // === Getters ===

    public String getChemin() {
        return chemin;
    }

    public String getNom() {
        return nom;
    }

    public List<Dossier> getSousDossiers() {
        return sousDossiers;
    }

    public List<FichierMP3> getFichiersMP3() {
        return fichiersMP3;
    }
    
    /**
     * Retourne la liste des fichiers MP3 trouvés récursivement.
     *
     * @return liste de fichiers MP3
     */
    public List<FichierMP3> getFichiersRecursif() {

        List<FichierMP3> resultat = new ArrayList<>();

        // 1) On ajoute les fichiers MP3 du dossier courant
        resultat.addAll(this.fichiersMP3);

        // 2) On parcourt les sous-dossiers et on récupère aussi leurs fichiers
        for (Dossier sous : this.sousDossiers) {
            resultat.addAll(sous.getFichiersRecursif());
        }

        return resultat;
    }

    // === toString ===

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(); // Pour éviter la concaténation lente dans les boucles

        sb.append("Dossier : ").append(nom)
          .append(" (").append(chemin).append(")\n"); // Affichage du nom et du chemin

        sb.append("Sous-dossiers (").append(sousDossiers.size()).append(") :\n");
        for (Dossier d : sousDossiers) {
            sb.append("  - ").append(d.getNom()).append("\n"); // Liste des sous-dossiers
        }

        sb.append("Fichiers MP3 (").append(fichiersMP3.size()).append(") :\n");
        for (FichierMP3 f : fichiersMP3) {
            sb.append("  - ").append(f.getNom()).append("\n"); // Liste des fichiers MP3
        }

        return sb.toString();
    }
}
