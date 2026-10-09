/**
 * Gère la lecture, l’analyse et l’interprétation des arguments CLI.
 *
 * <p>
 * La classe {@code GestionArgumentCLI} est chargée de lire, analyser
 * et interpréter les arguments passés en ligne de commande à
 * l’application MP3 Playlist en mode console.
 * </p>
 *
 * <p>
 * Elle transforme les arguments textuels fournis par l’utilisateur
 * (options, chemins, formats) en variables simples et accessibles
 * par le reste de l’application ({@code ApplicationCLI}).
 * </p>
 *
 * <p>
 * Cette classe ne réalise aucun traitement métier (analyse MP3,
 * création de playlist ou export) : elle se limite strictement
 * à la gestion des paramètres CLI.
 * </p>
 */
 /** <h2>Options gérées</h2>
 * <ul>
 *   <li><b>-h</b>, <b>--help</b> : affiche l’aide</li>
 *   <li><b>-f &lt;fichier&gt;</b> : analyse un fichier MP3</li>
 *   <li><b>-d &lt;dossier&gt;</b> : analyse un dossier contenant des MP3 (récursif)</li>
 *   <li><b>-o &lt;fichier&gt;</b> : fichier de sortie pour l’export</li>
 *   <li><b>--xspf</b> : export au format XSPF (XML)</li>
 *   <li><b>--jspf</b> : export au format JSPF (JSON)</li>
 *   <li><b>--m3u8</b> : export au format M3U8 (texte UTF-8)</li>
 * </ul>
 *
 * <h2>Normalisation des chemins</h2>
 * <p>
 * Les chemins fournis sont automatiquement nettoyés :
 * </p>
 * <ul>
 *   <li>suppression des guillemets éventuels</li>
 *   <li>uniformisation des séparateurs Windows (\) vers (/)</li>
 * </ul>
 *
 * <h2>Exemples d’utilisation</h2>
 * <pre>
 * java -jar cli.jar -f music.mp3
 * java -jar cli.jar -d music --xspf -o playlist.xspf
 * java -jar cli.jar --help
 * </pre>
 *
 * @author Meriem Ines
 * @version 1.0
 */
package cli;
public class GestionArgumentCLI {

    /** Indique si l’aide (-h ou --help) est demandée */
    public boolean aide = false;

    /** Indique si le mode fichier (-f) est activé */
    public boolean modeFichier = false;

    /** Indique si le mode dossier (-d) est activé */
    public boolean modeDossier = false;

    /** Chemin du fichier ou du dossier à analyser */
    public String chemin;

    /**
     * Format d’export sélectionné :
     * <ul>
     *   <li>"xspf"</li>
     *   <li>"jspf"</li>
     *   <li>"m3u8"</li>
     * </ul>
     */
    public String format = null;

    /** Chemin du fichier de sortie spécifié avec -o */
    public String sortie = null;

    /**
     * Construit un objet {@code GestionArgumentCLI} à partir des arguments
     * fournis en ligne de commande.
     *
     * <p>
     * Le constructeur appelle la méthode d’analyse puis normalise
     * le chemin (suppression des guillemets et uniformisation).
     * </p>
     *
     * @param args arguments passés au programme
     */
    public GestionArgumentCLI(String[] args) {
        analyser(args);

        // NORMALISATION DU CHEMIN
        if (chemin != null) {
            chemin = chemin.trim();

            // Suppression des guillemets éventuels
            if (chemin.startsWith("\"") && chemin.endsWith("\"") && chemin.length() > 1) {
                chemin = chemin.substring(1, chemin.length() - 1);
            }

            // Uniformisation des séparateurs Windows → Java
            chemin = chemin.replace("\\", "/");
        }
    }

    /**
     * Analyse les arguments fournis par l’utilisateur et initialise
     * les attributs de la classe en conséquence.
     *
     * <p>
     * Cette méthode détecte :
     * </p>
     * <ul>
     *   <li>le mode d’exécution (fichier ou dossier)</li>
     *   <li>le chemin à analyser</li>
     *   <li>le format d’export</li>
     *   <li>le fichier de sortie</li>
     * </ul>
     *
     * @param args arguments de la ligne de commande
     */
    private void analyser(String[] args) {

        if (args.length == 0) {
            return; // aucun argument : gestion dans ApplicationCLI
        }

        for (int i = 0; i < args.length; i++) {

            switch (args[i]) {

                case "-h":
                case "--help":
                    aide = true;
                    break;

                case "-f":
                    modeFichier = true;
                    chemin = args[++i];
                    break;

                case "-d":
                    modeDossier = true;
                    chemin = args[++i];
                    break;

                case "-o":
                    sortie = args[++i];
                    break;

                case "--xspf":
                    format = "xspf";
                    break;

                case "--jspf":
                    format = "jspf";
                    break;

                case "--m3u8":
                    format = "m3u8";
                    break;
            }
        }
    }
}