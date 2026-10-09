package cli;

/**
 * Classe principale du mode console (CLI).
 *
 * <p>
 * Cette classe permet :
 * <ul>
 *   <li>d'afficher l'aide du programme (-h)</li>
 *   <li>d'analyser un fichier MP3 (-f)</li>
 *   <li>d'analyser un dossier récursivement (-d)</li>
 *   <li>d'afficher les métadonnées d'un ou plusieurs fichiers MP3</li>
 *   <li>d'exporter une playlist aux formats XSPF, JSPF ou M3U8</li>
 * </ul>
 *
 * <p>
 * Les options sont interprétées via la classe {@link GestionArgumentCLI}.
 *
 * <p>
 * Exemples d'utilisation :
 * <pre>
 * java -jar cli.jar -h
 * java -jar cli.jar -f music.mp3
 * java -jar cli.jar -d .
 * java -jar cli.jar -d ./music --xspf -o playlist.xspf
 * </pre>
 *
 * @author Ines Meriem
 * @version 1.0
 */
/**
 * Notre mode CLI doit comprendre ces commandes :
 * java -jar cli.jar
 * java -jar cli.jar -h
 * java -jar cli.jar -f music.mp3 -o sortie.xspf --xspf
 * java -jar cli.jar -d ./music -o playlist.m3u8 --m3u8
 */

import core.*;
import java.io.File;
import playlist.*;

/**
 * Classe principale de l'application CLI.
 * Elle lit les arguments validés dans GestionArgumentCli,
 * analyse un fichier MP3 ou un dossier,
 * génère une playlist puis l'exporte dans le format choisi.
 */
public class ApplicationCLI {
	/**
 * Point d'entrée du programme en mode console
 * 
 * @param args arguments passés en ligne de commande
 */

    public static void main(String[] args) {

        GestionArgumentCLI opt;

        try {
            // Lecture + validation des arguments
            opt = new GestionArgumentCLI(args);

         // ===== Cas 1 : aucun argument =====
            if (args.length == 0) {
                System.out.println("Erreur : paramètres manquants.");
                System.out.println("Tapez -h ou --help pour afficher l'aide.");
                return;
            }

            // ===== Cas 2 : aide demandée =====
            if (opt.aide) {
                afficherAide();
                return;
            }

        } catch (Exception e) {
            // Gestion des erreurs d'arguments (format, incompatibilité...)
            System.out.println("Erreur arguments : " + e.getMessage());
            System.out.println("Utilisez -h pour l'aide.");
            return;
        }

        try {
            // ------------------------------------------------------
            // 1) Création de la playlist
            // ------------------------------------------------------
            Playlist playlist = new Playlist("PlaylistGenerée");
            
         

            // ======================================================
            // MODE FICHIER MP3
            // ======================================================
            if (opt.modeFichier) {

                String cheminNormalise = opt.chemin.replace("\\", "/");
                String nom = cheminNormalise.substring(cheminNormalise.lastIndexOf("/") + 1);

                // Création de l'objet MP3
                FichierMP3 f = new FichierMP3(nom, opt.chemin);
                /**
                 * Extraction et affichage des métadonnées du fichier MP3.
                 *
                 * <p>
                 * Cette partie est exécutée lorsque l'utilisateur utilise l'option -f
                 * sans spécifier de format d'export.
                 */             
                
                // Extraction métadonnées
                f.chargerMetadonnees();

                
             // AFFICHAGE DES MÉTADONNÉES DANS LA CONSOLE
                System.out.println("\n=== Métadonnées extraites ===");
                System.out.println(f.getMeta());
                System.out.println("=============================\n");
                
                //Si aucun format n'est demandé
                if(opt.format==null) {
                	return;          	
                }
                
            
                playlist.ajouterFichier(f);                
            }
            
            /**
             * Mode dossier.
             *
             * <p>
             * Analyse récursive de tous les fichiers MP3 présents dans le dossier
             * fourni en argument, y compris dans les sous-dossiers.
             */

            // ======================================================
            // MODE DOSSIER
            // ======================================================
            if (opt.modeDossier) {

                ExplorateurDeRepertoire exp = new ExplorateurDeRepertoire(opt.chemin);

                Dossier d = exp.analyser();

                playlist.ajouterFichiers(d.getFichiersRecursif());
            }

            File dossier = new File("C:/Users/laifa/OneDrive/Desktop/MesPlaylists");
            if (!dossier.exists()) {
                dossier.mkdirs(); // crée le dossier s’il n’existe pas
            }
            
            /**
             * Sélection du format d'export de la playlist.
             *
             * <p>
             * Le format est choisi à partir des options fournies par l'utilisateur.
             * Formats supportés : XSPF, JSPF, M3U8.
             *
             * @throws IllegalArgumentException si le format est inconnu
             */


            // ------------------------------------------------------
            // 2) Choix du format d’export
            // ------------------------------------------------------
            FormatPlaylist exporter;
            
            switch (opt.format) {
                case "xspf": exporter = new FormatXSPF(); break;
                case "jspf": exporter = new FormatJSPF(); break;
                case "m3u8": exporter = new FormatM3U8(); break;
                default:
                    throw new IllegalArgumentException("Format inconnu : " + opt.format);
            }

            // ------------------------------------------------------
            // 3) Export final
            // ------------------------------------------------------
            exporter.exporter(playlist, opt.sortie);

            System.out.println("✔ Playlist exportée avec succès → " + opt.sortie);

        } catch (Exception e) {
            System.out.println("ERREUR : " + e.getMessage());
        }
        
    }
    



    // ------------------------------------------------------------------
    // MÉTHODE QUI AFFICHE L’AIDE POUR L’UTILISATEUR
    // ------------------------------------------------------------------
    private static void afficherAide() {
        System.out.println("\n=== AIDE — MP3 Playlist CLI ===\n");
        System.out.println("Usage : java -jar cli.jar [options]\n");
        System.out.println("Options :");
        System.out.println("  -h, --help       : affiche cette aide");
        System.out.println("  -f <fichier>     : analyse un fichier MP3");
        System.out.println("  -d <dossier>     : analyse un dossier contenant des MP3");
        System.out.println("  -o <fichier>     : fichier de sortie obligatoire");
        System.out.println("  --xspf           : export au format XSPF (XML)");
        System.out.println("  --jspf           : export au format JSPF (JSON)");
        System.out.println("  --m3u8           : export au format M3U8 (texte UTF-8)");
        System.out.println();
    }
}
