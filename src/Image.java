import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
/**
 * Klasa dla obrazu orginalnego
 */
public class Image extends JPanel {
    private BufferedImage image = null;
    private int width = 500;
    private int height = 500;

    /**
     * Metoda do ustawiania obrazu w panelu
     * @param image obraz wyświetlany
     */
    public void setImage(BufferedImage image) {
        if ((this.image=image)!=null){
            this.width=this.image.getWidth();
            this.height=this.image.getHeight();
        }
        this.setPreferredSize(new Dimension(width,height));
        this.setSize(this.getPreferredSize());

        this.revalidate();
        this.repaint();
    }
    /**
     * Metoda wyświetlania obrazu
     * @param g obraz orginalny
     */
    @Override
    public synchronized void paintComponent(Graphics g){
        double      x=1.0,y=1.0;

        Graphics2D graphics2D = (Graphics2D)g;
        super.paintComponent(graphics2D);

        if (this.image!=null){
            graphics2D.drawImage(this.image,new AffineTransformOp(AffineTransform.getScaleInstance(x,y),AffineTransformOp.TYPE_BILINEAR),0,0);
        }
    }
}
