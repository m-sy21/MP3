package playlist;

import java.io.FileWriter;
import core.FichierMP3;
import core.MetaDonnees;
/**
 * Implémentation du format XSPF pour l’export de playlists.
 */
public class FormatXSPF implements FormatPlaylist {
	/**
     * Exporte une playlist au format XSPF.
     *
     * @param playlist playlist à exporter
     * @param cheminSortie chemin du fichier XSPF
     * @throws Exception en cas d’erreur
     */
    @Override
    public void exporter(Playlist playlist, String chemin) {
    	
    	//ça garantit que les fichiers se ferment automatiquement
        try (FileWriter writer = new FileWriter(chemin)) { 

        	//Ecriture de l'en-tete XML standard
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");//creation de fichier xml
            writer.write("<playlist version=\"1\" xmlns=\"http://xspf.org/ns/0/\">\n");//la balise principale
            
            //balise contenant toutes les pistes
            writer.write("<trackList>\n");

            for (FichierMP3 f : playlist.getFichiers()) {//pour chaque fichier MP3 de la playlist
            	
            	//recuperation des métadonnées associées
                MetaDonnees meta = f.getMeta();

                writer.write("  <track>\n");//ouvre une balise track
                
                //chemin normalisé (important pour XML et Windows)
                writer.write("    <location>file:///" + f.getChemin().replace("\\", "/") + "</location>\n");
                
                //titre,artist et album
                writer.write("    <title>" + meta.getTitre() + "</title>\n");
                writer.write("    <creator>" + meta.getArtiste() + "</creator>\n");
                writer.write("    <album>" + meta.getAlbum() + "</album>\n");
                
                writer.write("  </track>\n");//ferme le track
            }

            writer.write("</trackList>\n");//ferme tracklist
            writer.write("</playlist>\n");//ferme proprement la playlist XML
            
         // Image de couverture de la playlist (optionnelle)
            String cheminCover = "file:///C:/Users/laifa/OneDrive/Desktop/cover.jpg";
            writer.write("<image>" + cheminCover + "</image>\n");

            
            
            System.out.println("Playlist exportée en XSPF → " + chemin);

        } catch (Exception e) {
        	
        	//Capture de toute erreur d'écriture
            System.out.println("Erreur export XSPF : " + e.getMessage());
        }
    }
}
