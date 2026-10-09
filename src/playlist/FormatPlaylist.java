package playlist;


/**
 * Interface définissant un format d’export de playlist.
 */
public interface FormatPlaylist {
	/**
     * Exporte une playlist vers un fichier.
     *
     * @param playlist playlist à exporter
     * @param cheminSortie chemin du fichier de sortie
     * @throws Exception en cas d’erreur d’écriture
     */
    void exporter(Playlist playlist, String chemin) throws Exception;
    
}
