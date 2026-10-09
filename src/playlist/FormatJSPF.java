package playlist;

import java.io.FileWriter;
import java.util.List;
import core.FichierMP3;
import core.MetaDonnees;

/**
 * Implémentation du format JSPF pour l’export de playlists.
 */
public class FormatJSPF implements FormatPlaylist {
	/**
     * Exporte une playlist au format JSPF.
     *
     * @param playlist playlist à exporter
     * @param cheminSortie chemin du fichier JSPF
     * @throws Exception en cas d’erreur
     */
    @Override
    public void exporter(Playlist playlist, String chemin) {

        // try-with-resources : gestion automatique de la fermeture du fichier
        try (FileWriter writer = new FileWriter(chemin)) {

            writer.write("{\n");
            writer.write("  \"playlist\": {\n");
            writer.write("    \"title\": \"" + escape(playlist.getNom()) + "\",\n");
            writer.write("    \"track\": [\n");

            // On récupère la liste des fichiers MP3 de la playlist
            List<FichierMP3> fichiers = playlist.getFichiers();

            // Boucle classique pour gérer la virgule finale correctement
            for (int i = 0; i < fichiers.size(); i++) {

                FichierMP3 f = fichiers.get(i);
                MetaDonnees meta = f.getMeta(); // Récupération des métadonnées

                writer.write("      {\n");
                writer.write("        \"location\": \"" +
                        f.getChemin().replace("\\", "/") + "\",\n");
                writer.write("        \"title\": \"" + escape(meta.getTitre()) + "\",\n");
                writer.write("        \"creator\": \"" + escape(meta.getArtiste()) + "\",\n");
                writer.write("        \"album\": \"" + escape(meta.getAlbum()) + "\"\n");
                writer.write("      }");

                // Virgule pour tous sauf le dernier
                if (i < fichiers.size() - 1)
                    writer.write(",");

                writer.write("\n");
            }

            writer.write("    ]\n");
            writer.write("  }\n");
            writer.write("}\n");

            System.out.println("Playlist exportée en JSPF → " + chemin);

        } catch (Exception e) {
            // Gestion de toute erreur d'écriture ou d'accès aux données
            System.out.println("Erreur export JSPF : " + e.getMessage());
        }
    }

    /**
     * Permet d'échapper les guillemets et caractères spéciaux pour un JSON propre.
     */
    private String escape(String s) {
        if (s == null) return "";
        return s.replace("\"", "\\\""); // évite de casser le JSON
    }
}
