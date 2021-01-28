import javax.swing.*;
/**
 * Klasa do wyświetlania dialogów
 */
public class DialogLibrary {
    /**
     * Metoda wyświetlająca błąd braku pliku
     */
    public static void showNoFileDialog() {
        JOptionPane.showMessageDialog(null,
                "There is no file!",
                "Warning",
                JOptionPane.ERROR_MESSAGE);
    }
    /**
     * Metoda wyświetlająca błąd występowania obrazu
     */
    public static void showImageDialog() {
        JOptionPane.showMessageDialog(null,
                "There is an image!",
                "Warning",
                JOptionPane.ERROR_MESSAGE);
    }
    /**
     * Metoda wyświetlająca błąd braku obrazu
     */
    public static void showNoImageDialog() {
        JOptionPane.showMessageDialog(null,
                "There is no image!",
                "Warning",
                JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Metoda wyświetlająca błąd braku metody
     */
    public static void showNoAlgorytmDialog() {
        JOptionPane.showMessageDialog(null,
                "BUY PREMIUM VERSION :(",
                "Warning",
                JOptionPane.ERROR_MESSAGE);
    }
}
