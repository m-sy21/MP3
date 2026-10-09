package gui;

import core.*;
import playlist.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.io.File;

/**
 * Interface graphique de l'application MP3 Playlist.
 *
 * <p>
 * Cette classe implémente le mode graphique (GUI) du projet MP3 Playlist.
 * Elle permet à l'utilisateur de sélectionner des fichiers MP3,
 * analyser des dossiers récursivement, visualiser les métadonnées
 * et générer des playlists exportables.
 * </p>
 * </p>
 *
 * <p>
 * Elle agit comme une couche de présentation et s'appuie exclusivement
 * sur les classes métier du projet (core, playlist).
 * </p>
 *
 * <p>
 * </p>
 */
public class ApplicationGUI extends JFrame {

    private static final long serialVersionUID = 1L;
    
   // Composants Swing 
    
    /** Modèle de données pour la liste des fichiers MP3 */
    private DefaultListModel<FichierMP3> modeleListe;

    /** Liste graphique des fichiers MP3 */
    private JList<FichierMP3> listeMP3;

    /** Zone d'affichage des métadonnées */
    private JTextArea zoneMetadonnees;

    /** Zone d'affichage de la couverture d'album */
    private JLabel labelCouverture;
    
    //Constructeur
    
    /**
     * Construit la fenêtre principale de l'application graphique.
     *
     * <p>
     * Initialise les propriétés de la fenêtre et crée
     * l'ensemble des composants Swing.
     * </p>
     */
    public ApplicationGUI() {
        setTitle("MP3 Playlist - Mode Graphique");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initialiserInterface();
    }

    /**
     * Initialise l'interface graphique complète.
     *
     * <p>
     * Cette méthode crée :
     * </p>
     * <ul>
     *   <li>les boutons d'action</li>
     *   <li>la liste des fichiers MP3</li>
     *   <li>la zone d'affichage des métadonnées</li>
     *   <li>la zone d'affichage de la couverture</li>
     *   <li>les boutons d'export</li>
     * </ul>
     */
    private void initialiserInterface() {
    	
    	//Conteneur principal
        setLayout(new BorderLayout());

        //Haut : Boutons
        JPanel panneauHaut = new JPanel();
        JButton boutonFichier = new JButton("Choisir un fichier MP3");
        JButton boutonDossier = new JButton("Choisir un dossier");
        JButton boutonSelectionMorceaux = new JButton("Sélectionner des morceaux");

        panneauHaut.add(boutonSelectionMorceaux);
        panneauHaut.add(boutonFichier);
        panneauHaut.add(boutonDossier);
        add(panneauHaut, BorderLayout.NORTH);

        //Centre : liste + infos
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setResizeWeight(0.5);
        
        //Liste mp3
        modeleListe = new DefaultListModel<>();
        listeMP3 = new JList<>(modeleListe);
        splitPane.setLeftComponent(new JScrollPane(listeMP3));
        
        //Panneau droit 
        JPanel panneauDroit = new JPanel(new BorderLayout());

        zoneMetadonnees = new JTextArea();
        zoneMetadonnees.setEditable(false);
        zoneMetadonnees.setFont(new Font("Monospaced", Font.PLAIN, 12));
        panneauDroit.add(new JScrollPane(zoneMetadonnees), BorderLayout.CENTER);

        //Cover
        labelCouverture = new JLabel("Aucune cover", SwingConstants.CENTER);
        labelCouverture.setPreferredSize(new Dimension(200, 200));
        panneauDroit.add(labelCouverture, BorderLayout.SOUTH);

        splitPane.setRightComponent(panneauDroit);
        add(splitPane, BorderLayout.CENTER);

        //Bas : export
        JPanel panneauBas = new JPanel();
        JButton boutonXSPF = new JButton("Exporter XSPF");
        JButton boutonJSPF = new JButton("Exporter JSPF");
        JButton boutonM3U8 = new JButton("Exporter M3U8");
        JButton boutonVider = new JButton("Vider la liste");

        panneauBas.add(boutonXSPF);
        panneauBas.add(boutonJSPF);
        panneauBas.add(boutonM3U8);
        panneauBas.add(boutonVider);
        add(panneauBas, BorderLayout.SOUTH);

        //Événements
        // Sélection dans la liste
        listeMP3.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    afficherInfosMP3();
                }
            }
        });
        
        //Choisir fichier
        boutonFichier.addActionListener(e -> choisirFichierMP3());
        
        //Choisir dossier
        boutonDossier.addActionListener(e -> choisirDossier());
        
        //Sélectionner des morceaux : ajout manuel
        boutonSelectionMorceaux.addActionListener(e -> choisirFichierMP3());
        
        //Export
        boutonXSPF.addActionListener(e -> exporterPlaylist(new FormatXSPF(), "xspf"));
        boutonJSPF.addActionListener(e -> exporterPlaylist(new FormatJSPF(), "jspf"));
        boutonM3U8.addActionListener(e -> exporterPlaylist(new FormatM3U8(), "m3u8"));
        
        //Vider
        boutonVider.addActionListener(e -> {
            modeleListe.clear();
            zoneMetadonnees.setText("");
            labelCouverture.setIcon(null);
            labelCouverture.setText("Aucune cover");
        });
    }

    /**
     * Permet à l'utilisateur de sélectionner un fichier MP3.
     *
     * <p>
     * Le fichier est transformé en {@link FichierMP3},
     * ses métadonnées sont chargées et il est ajouté à la liste.
     * </p>
     */
    private void choisirFichierMP3() {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File fichier = chooser.getSelectedFile();
            FichierMP3 mp3 = new FichierMP3(fichier.getName(), fichier.getAbsolutePath());
            mp3.chargerMetadonnees();
            modeleListe.addElement(mp3);
        }
    }

    /**
     * Analyse un dossier contenant des fichiers MP3 de manière récursive.
     *
     * <p>
     * Tous les fichiers MP3 trouvés sont ajoutés à la liste.
     * </p>
     */
    private void choisirDossier() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File dossier = chooser.getSelectedFile();
            ExplorateurDeRepertoire exp = new ExplorateurDeRepertoire(dossier.getAbsolutePath());
            Dossier d = exp.analyser();

            for (FichierMP3 f : d.getFichiersRecursif()) {
                modeleListe.addElement(f);
            }
        }
    }

    /**
     * Affiche les métadonnées et la couverture du MP3 sélectionné.
     */
    private void afficherInfosMP3() {
        FichierMP3 fichier = listeMP3.getSelectedValue();
        if (fichier == null) return;

        MetaDonnees meta = fichier.getMeta();

        zoneMetadonnees.setText(
                "Titre   : " + meta.getTitre() + "\n" +
                "Artiste : " + meta.getArtiste() + "\n" +
                "Album   : " + meta.getAlbum() + "\n" +
                "Année   : " + meta.getAnnee() + "\n" +
                "Genre   : " + meta.getGenre()
        );

        if (meta.getImageCouverture() != null) {
            Image img = new ImageIcon(meta.getImageCouverture())
                    .getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            labelCouverture.setIcon(new ImageIcon(img));
            labelCouverture.setText("");
        } else {
            labelCouverture.setIcon(null);
            labelCouverture.setText("Aucune cover");
        }
    }

    /**
     * Exporte la playlist courante vers un fichier.
     *
     * @param format format d'export (XSPF, JSPF, M3U8)
     * @param extension extension du fichier généré
     */
    private void exporterPlaylist(FormatPlaylist format, String extension) {
        JFileChooser chooser = new JFileChooser();
        
        //Nom par défaut proposé (modifiable par l'utilisateur)
        chooser.setSelectedFile(new File("ma_playlist." + extension));

        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                Playlist playlist = new Playlist("Playlist GUI");
                for (int i = 0; i < modeleListe.size(); i++) {
                    playlist.ajouterFichier(modeleListe.get(i));
                }
                format.exporter(playlist, chooser.getSelectedFile().getAbsolutePath());
                JOptionPane.showMessageDialog(this, "Export réussi !");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    //Main
    /**
     * Point d'entrée du mode graphique.
     *
     * @param args non utilisés
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ApplicationGUI().setVisible(true));
    }
}