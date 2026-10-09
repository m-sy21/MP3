package core;

/**
 * Représente un fichier MP3.
 *
 * <p>
 * Cette classe permet :
 * <ul>
 *   <li>de stocker le chemin du fichier MP3</li>
 *   <li>d'extraire ses métadonnées</li>
 *   <li>d'accéder aux informations musicales</li>
 * </ul>
 */

public class FichierMP3 {

	/**
     * Chemin du fichier MP3.
     *//**
     * Chemin du fichier MP3.
     */
    private String chemin;
    private String nom;
    private int duree;
    /**
     * Métadonnées associées au fichier MP3.
     */
    private MetaDonnees meta; // Composition : un fichier MP3 possède ses métadonnées

    /**
     * Construit un fichier MP3 à partir de son chemin.
     *
     * @param chemin chemin vers le fichier MP3
     */
    public FichierMP3(String nom, String chemin) {
    	this.chemin = chemin.replace("\\", "/");
        this.nom = nom;
        this.duree = 0;           // durée inconnue au début
        this.meta = new MetaDonnees(); // Composition : créé automatiquement
    }
    
    /**
     * Extrait les métadonnées du fichier MP3.
     */
    public void chargerMetadonnees() {
        this.meta.extraireDepuisFichier1(chemin);
    }

    public void lireFichier() {
        System.out.println("Lecture du fichier MP3 : " + nom);
    }

    public String getChemin() {
        return chemin;
    }

    public String getNom() {
        return nom;
    }

    public int getDuree() {
        return duree;
    }

    /**
     * Retourne les métadonnées du fichier MP3.
     *
     * @return métadonnées extraites
     */
    public MetaDonnees getMeta() {
        return meta;
    }

    @Override
    public String toString() {
        return ("FichierMP3 : " + nom +
                "\nChemin : " + chemin +
                "\nDurée : " + duree + "s" +
                "\nMétadonnées : " + meta.toString());
    }
}
