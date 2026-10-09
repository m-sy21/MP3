package core;

import java.io.File;
import java.nio.file.Files;
import org.jaudiotagger.audio.AudioFileIO;

/**
 * Retourne la liste des fichiers MP3 trouvés récursivement.
 *
 * @return liste de fichiers MP3
 */
public class ExplorateurDeRepertoire {
	
	/**
     * Chemin du répertoire à analyser.
     */
    private String chemin;
    /**
     * Construit un explorateur à partir d’un chemin.
     *
     * @param chemin chemin du répertoire
     */
    public ExplorateurDeRepertoire(String chemin) {
        this.chemin = chemin;
    } 
    // On enregistre le chemin fourni par l’utilisateur ou par le programme CLI.


    /**
     * Lance l’analyse du répertoire.
     *
     * @return dossier contenant les fichiers MP3 trouvés
     */
    public Dossier analyser() {
        File racine = new File(chemin); 
        if (!racine.isDirectory()) { // vérifie que ce chemin correspond à un vrai dossier
            throw new IllegalArgumentException("Chemin invalide : pas un dossier");
        }

        // Analyse récursive du dossier
        return analyserDossier(racine);
    }


    /**
     * Analyse récursive d’un dossier.
     * Retourne un objet Dossier qui contient :
     * - les fichiers MP3 trouvés
     * - la liste des sous-dossiers analysés
     */
    private Dossier analyserDossier(File dossierFile) {

        // Création d’un objet Dossier correspondant au dossier physique
        Dossier dossier = new Dossier(dossierFile.getAbsolutePath(), dossierFile.getName());

        // Liste des fichiers et sous-dossiers
        File[] contenus = dossierFile.listFiles();
        if (contenus == null) return dossier; // rien à analyser

        for (File f : contenus) {

            if (f.isDirectory()) {
                // Analyse du sous-dossier
                Dossier sousDossier = analyserDossier(f);
                dossier.ajouterSousDossier(sousDossier);
            }

            else if (estMP3(f)) {
                // Création d'un objet FichierMP3 valide
                FichierMP3 fichier = new FichierMP3(f.getName(), f.getAbsolutePath());
                new FichierMP3(f.getName(), f.getAbsolutePath().replace("\\", "/"));
                // Extraction des métadonnées via jaudiotagger
                fichier.chargerMetadonnees();

                // Ajout au dossier courant
                dossier.ajouterFichier(fichier);
            }
        }

        return dossier;
    }


    /**
     * Vérifie si un fichier est réellement un fichier MP3 :
     * 1. Vérifie que c'est un fichier normal
     * 2. Vérifie l'extension (.mp3)
     * 3. Vérifie le type MIME (audio/mpeg)
     * 4. Vérifie que jaudiotagger peut le lire (sécurité)
     */
    private boolean estMP3(File f) {
        try {
            // Vérifier extension
            if (!f.getName().toLowerCase().endsWith(".mp3"))
                return false;

            // Vérifier type MIME
            String mime = Files.probeContentType(f.toPath());
            if (mime == null || !mime.equals("audio/mpeg"))
                return false;

            // Vérifier que jaudiotagger peut lire
            AudioFileIO.read(f);

            return true;

        } catch (Exception e) {
            return false;
        }
    }


}

