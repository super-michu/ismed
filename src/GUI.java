import java.awt.*;
import java.awt.event.ActionListener;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * Klasa interfeju GUI
 */
public class GUI extends JFrame implements ActionListener, ChangeListener {

    private final JPanel mainPanel, selectPanel, modificationPanel, imagePanel;
    private final JButton openFile, saveFile,renewImage, filter, thresholding;
    private final JSlider brightness, contrast;
    private ModImage modImage;
    private Image orginalImage;
    private int counter = 0;
    private int counterFile = 0 ;
    /**
     * Konstruktor interfejsu GUI
     * Generacja Paneli, Przycisków i Suwaków oraz rozmieszczenie ich w layoucie
     */
    public GUI() {

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setPreferredSize(new Dimension(1000,700));
        this.getContentPane().add(mainPanel);
        openFile = new JButton("Open a File");
        this.openFile.addActionListener(this);
        saveFile = new JButton("Save a File");
        this.saveFile.addActionListener(this);
        renewImage = new JButton("Renew a File");
        this.renewImage.addActionListener(this);

        imagePanel = new JPanel(new BorderLayout());
        mainPanel.add(new JScrollPane(this.imagePanel),BorderLayout.CENTER);

        selectPanel = new JPanel();
        selectPanel.add(openFile);
        selectPanel.add(saveFile);
        selectPanel.add(renewImage);
        selectPanel.setBorder(BorderFactory.createTitledBorder("Actions:"));
        mainPanel.add(selectPanel, BorderLayout.PAGE_START);

        modificationPanel = new JPanel(new GridBagLayout());
        modificationPanel.setBorder(BorderFactory.createTitledBorder("Modificator:"));

        JLabel label1 = new JLabel("B:");
        this.modificationPanel.add(label1);
        this.brightness = new JSlider(-255, 255);
        this.brightness.setPaintTrack(true);
        this.brightness.setPaintTicks(true);
        this.brightness.setPaintLabels(true);
        this.brightness.setMajorTickSpacing(255);
        this.brightness.setMinorTickSpacing(5);
        this.brightness.addChangeListener(this);
        this.brightness.setName("Change Brightness");
        modificationPanel.add(brightness);

        JLabel label2 = new JLabel("C:");
        this.modificationPanel.add(label2);
        this.contrast = new JSlider(-100, 100);
        this.contrast.setPaintTrack(true);
        this.contrast.setPaintTicks(true);
        this.contrast.setPaintLabels(true);
        this.contrast.setMajorTickSpacing(100);
        this.contrast.setMinorTickSpacing(20);
        this.contrast.addChangeListener(this);
        this.contrast.setName("Change Contrast");
        modificationPanel.add(contrast);

        filter = new JButton("Median Filter");
        this.filter.addActionListener(this);
        modificationPanel.add(filter);

        thresholding = new JButton("Thresholding");
        this.thresholding.addActionListener(this);
        modificationPanel.add(thresholding);
        mainPanel.add(modificationPanel, BorderLayout.SOUTH);

        setFrame();
    }
    /**
     * Kontrukcja ramki aplikacji
     */
    private void setFrame() {
        setTitle("DICOM Modificator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    /**
     * Obsługa przycisków interfejsu
     * @param e event wywołany po użyciu danego przycisku
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Open a File":
                if(counterFile == 0){
                    openFile();
                }
                else {
                    DialogLibrary.showImageDialog();
                }
                break;
            case "Save a File":
                saveFile();
                break;
            case "Renew a File":
                if(counterFile == 0){
                    DialogLibrary.showNoImageDialog();
                }
                else{
                    modImage.renewImage();
                }
                break;
            case "Median Filter":
                DialogLibrary.showNoAlgorytmDialog();
                break;
            case "Thresholding":
                DialogLibrary.showNoAlgorytmDialog();
                break;
        }
    }
    /**
     * Obsługa suwaków
     * @param e event po zmianie wartości na poszczególnych suwakach
     */
    @Override
    public void stateChanged(ChangeEvent e) {
        if (modImage.displayImage != null) {
            JSlider slider = (JSlider) e.getSource();
            switch (slider.getName()) {
                case "Change Brightness":
                    modImage.changeBrightness(slider.getValue());
                    break;
                case "Change Contrast":
                    modImage.changeContrast(slider.getValue());
                    break;
            }
        }
    }
    /**
     * Metoda do wczytywania obrazu ze ścieżki pliku
     */
    private void openFile(){
        JFileChooser fileChooser = new JFileChooser(".\\resources");
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("DICOM images", "dcm"));

        if(fileChooser.showOpenDialog(null)==JFileChooser.APPROVE_OPTION){
            File file = fileChooser.getSelectedFile();
            try {
                this.orginalImage = new Image();
                this.orginalImage.setBorder(BorderFactory.createTitledBorder("Orginal:"));
                imagePanel.add(orginalImage,BorderLayout.WEST);
                this.modImage = new ModImage();
                this.modImage.setBorder(BorderFactory.createTitledBorder("Modificated:"));
                imagePanel.add(modImage,BorderLayout.EAST);
                this.orginalImage.setImage(ImageIO.read(file));
                this.modImage.setImage(ImageIO.read(file));
                counterFile ++;
                counterFile ++;
            } catch (IOException exception) {
                DialogLibrary.showNoFileDialog();
            }
        }
    }
    /**
     * Metoda do zapisywania obrazu w formacie JPG
     */
    private void saveFile(){
        try {
            ImageIO.write(this.modImage.getImage(), "jpg", new File(".\\resources\\DICOM"+ counter++ +
                    "_"+ new SimpleDateFormat("yyyy-MM-dd-HH-mm'.jpg'").format(new Date())));
        } catch (Exception exception) {
            DialogLibrary.showNoImageDialog();
        }
    }
}