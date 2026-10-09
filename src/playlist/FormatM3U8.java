package playlist;

import java.io.FileWriter;
import java.util.List;
import core.FichierMP3;
import core.MetaDonnees;

/**
 * Implémentation du format M3U8 pour l’export de playlists.
 */
public class FormatM3U8 implements FormatPlaylist {

	/**
     * Exporte une playlist au format XSPF.
     *
     * @param playlist playlist à exporter
     * @param cheminSortie chemin du fichier M3U8
     * @throws Exception en cas d’erreur
     */
    @Override
    public void exporter(Playlist playlist, String chemin) {

        // try-with-resources → fermeture automatique du fichier même en cas d'erreur.
        try (FileWriter writer = new FileWriter(chemin)) {

            // En-tête M3U standard obligatoire
            writer.write("#EXTM3U\n");

            // Récupération des fichiers MP3 de la playlist
            List<FichierMP3> fichiers = playlist.getFichiers();

            for (FichierMP3 f : fichiers) {

                // Récupération des métadonnées (titre, artiste…)
                MetaDonnees meta = f.getMeta();

                // Ligne d'information #EXTINF
                writer.write("#EXTINF:-1," 
                             + meta.getArtiste() 
                             + " - " 
                             + meta.getTitre() 
                             + "\n");

                // Chemin du fichier (normalisation / → support Windows / Linux)
                writer.write(f.getChemin().replace("\\", "/") + "\n");
            }

            // Message informatif (facultatif)
            System.out.println("Playlist exportée en M3U8 → " + chemin);

        } catch (Exception e) {

            // En cas d'erreur d'écriture
            System.out.println("Erreur export M3U8 : " + e.getMessage());
        }
    }
}