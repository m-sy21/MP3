package core;

// Importations nécessaires 
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.FieldKey;
/**
 * Représente les métadonnées d'un fichier MP3.
 *
 * <p>
 * Contient les informations musicales principales :
 * titre, artiste, album, année et genre.
 */
public class MetaDonnees {
	

    // Attributs ID3
    private String titre;
    private String artiste;
    private String album;
    private int annee;
    private String genre;
    private byte[] imageCouverture; // image brute (jpeg/png)


    /**
     * Constructeur par défaut
     * Les valeurs seront remplies après extraction du MP3 
     */
    public MetaDonnees() {}

    
    /**
     * Méthode d'extraction des métadonnées depuis le fichier MP3
     * ( Correction : renommée correctement → extraireDepuisFichier)
     */
    public void extraireDepuisFichier1(String chemin) {
        try {
            AudioFile audioFile = AudioFileIO.read(new java.io.File(chemin));
         
            Tag tag = audioFile.getTag();

            if (tag != null) {
                this.titre = tag.getFirst(FieldKey.TITLE);
                this.artiste = tag.getFirst(FieldKey.ARTIST);
                this.album = tag.getFirst(FieldKey.ALBUM);
                this.genre = tag.getFirst(FieldKey.GENRE);
                
             // --- Extraction de l'image de couverture ---
            if (tag.getFirstArtwork() != null) {
                imageCouverture = tag.getFirstArtwork().getBinaryData();
            }


                String an = tag.getFirst(FieldKey.YEAR);
                try {
                    this.annee = (an == null || an.isEmpty()) ? 0 : Integer.parseInt(an);
                } catch (NumberFormatException e) {
                    this.annee = 0;
                }
            }

        } catch (Exception e) {
            System.out.println("Erreur extraction métadonnées : " + e.getMessage());
            System.out.println("DEBUG chemin = " + chemin);

        }
    }
 

    
    // Getters : permettent d’obtenir les valeurs lues
    /**
     * Retourne le titre du morceau.
     *
     * @return titre du morceau
     */
    public String getTitre() { return titre; }
    /**
     * Retourne l’artiste du morceau.
     *
     * @return artiste
     */
    public String getArtiste() { return artiste; }
    /**
     * Retourne l’album du morceau.
     *
     * @return album
     */
    public String getAlbum() { return album; }
    /**
     * Retourne l’album du morceau.
     *
     * @return album
     */
    public int getAnnee() { return annee; }
    /**
     * Retourne une représentation textuelle des métadonnées.
     *
     * @return chaîne de caractères formatée
     */
    public String getGenre() { return genre; }
    /**
     * Affiche l'image
     *
     * @return Cover
     */
    
    public byte[] getImageCouverture() {
        return imageCouverture;
    }

    /**
     * Retourne une représentation textuelle des métadonnées.
     *
     * @return chaîne de caractères formatée
     */
    @Override
    public String toString() {
        return "Titre : " + titre + 
               "\nArtiste : " + artiste +
               "\nAlbum : " + album +
               "\nAnnée : " + annee +
               "\nGenre : " + genre;
    }
}
