import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
/**
 * Klasa służąca do wyświetlania i modyfikacji obrazu DICOM
 */
public class ModImage extends JPanel {

    public BufferedImage sourceImage = null;
    public BufferedImage displayImage = null;
    private int width;
    private int height;
    private int currentBrightnessOffset = 0;
    private float currentScaleFactor = 1.0f;
    private RescaleOp rescale;
    private int[][]tab_pix;
    /**
     * Metoda do uzyskiwania obrazu
     * @return displayImage zwraca obraz modyfikowany
     */
    public BufferedImage getImage() {
        return displayImage;
    }
    /**
     * Metoda do ustawiania obrazu w panelu
     * @param image obraz pobrany z pliku
     */
    public void setImage(BufferedImage image) {
        if ((this.sourceImage=image)!=null){
            this.width=this.sourceImage.getWidth();
            this.height=this.sourceImage.getHeight();
        }
        this.setPreferredSize(new Dimension(width,height));
        this.setSize(this.getPreferredSize());
        displayImage=sourceImage;
        this.revalidate();
        this.repaint();
    }
    /**
     * Metoda do zmiany jasności obrazu
     * @param brightnessValue wartość jasności ze slidera brightness
     */
    public void changeBrightness(int brightnessValue) {
        currentBrightnessOffset = brightnessValue;
        rescale = new RescaleOp(currentScaleFactor, currentBrightnessOffset, null);
        displayImage = rescale.filter(sourceImage, null);
        this.repaint();
    }
    /**
     * Metoda do modyfikacji kontrastu obrazu
     * @param contrastValue wartość konrastu ze slidera contrast
     */
    public void changeContrast(int contrastValue) {
        currentScaleFactor = ((float) contrastValue + 100) / 100;
        rescale = new RescaleOp(currentScaleFactor, currentBrightnessOffset, null);
        displayImage = rescale.filter(sourceImage, null);
        this.repaint();
    }
    /**
     * Metoda przywracająca obraz bazowy na panel
     */
    public void renewImage() {
        displayImage = sourceImage;
        this.repaint();
    }
    /**
     * Metoda progowania, niedopracowana
     */
    public void tresholding(){
        Color c1 = new Color (255,255,255);
        int white = c1.getRGB();
        Color c2 = new Color (0,0,0);
        int black = c2.getRGB();

        for(int i=0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int pixel = displayImage.getRGB(j,i);
                if(pixel > 100){
                    displayImage.setRGB(j,i,white);
                }else{
                    displayImage.setRGB(j,i,black);
                }
            }
        }this.repaint();
    }
    /**
     * Metoda służąca do wyświetlenia obrazu na panelu
     * @param g obraz wyświetlany na panelu
     */
    @Override
    public synchronized void paintComponent(Graphics g){
        double      x=1.0,y=1.0;

        Graphics2D graphics2D = (Graphics2D)g;
        super.paintComponent(graphics2D);

        if (this.displayImage!=null){
            graphics2D.drawImage(this.displayImage,new AffineTransformOp(AffineTransform.getScaleInstance(x,y),AffineTransformOp.TYPE_BILINEAR),0,0);
        }
    }
}
