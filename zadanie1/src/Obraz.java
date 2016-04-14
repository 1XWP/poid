import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Obraz extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	public BufferedImage image;
	public int[][] result;
	public int width;
	public int height;
	public BufferedImage image2;
	public int[] lightTable = new int[256];
	public int[][] complexR = new int[512][512];

	public double[] dbR;
	public double[] dbI;

	public double[][] dbRresult;
	public double[][] dbGresult;
	public double[][] dbBresult;

	public double[][] dbIresult;
	public double[][] dbRresultBACK;
	public double[][] dbIresultBACK;

	Random random = new Random();

	boolean DP, GP;

	private JButton negativeButton, resetButton, arithmeticButton, saveButton, contrastButton
	,lightenButton, filterO3, filterS6, medianButton, h3Button, histogramButton2; 	

	private JTextField arithmeticF, medianF, splotF;

	int[] R = new int[256];
	int[] G = new int[256];
	int[] B = new int[256];
	int[] J = new int[256];

	JPanel panel = new JPanel();
	JFrame f = new JFrame("JPanel");

	Icon podglad;
	JLabel podgladLabel;

	public Obraz() {

		panel.setLayout(null);

		resetButton = new JButton("reset");
		resetButton.setBounds(0, 0, 100, 30);
		resetButton.addActionListener(this);
		panel.add(resetButton);

		saveButton = new JButton("save");
		saveButton.setBounds(0, 30, 100, 30);
		saveButton.addActionListener(this);
		panel.add(saveButton);

		negativeButton = new JButton("negative");
		negativeButton.setBounds(0, 60, 100, 30);
		negativeButton.addActionListener(this);
		panel.add(negativeButton);

		contrastButton = new JButton("contrast");
		contrastButton.setBounds(0, 90, 100, 30);
		contrastButton.addActionListener(this);
		panel.add(contrastButton);

		lightenButton = new JButton("lighten");
		lightenButton.setBounds(0, 120, 100, 30);
		lightenButton.addActionListener(this);
		panel.add(lightenButton);
		
		arithmeticButton = new JButton("arithmetic");
		arithmeticButton.setBounds(0, 150, 100, 30);
		arithmeticButton.addActionListener(this);
		panel.add(arithmeticButton);

		arithmeticF = new JTextField(2);
		arithmeticF.setBounds(100, 150, 30, 30);
		panel.add(arithmeticF);

		medianButton = new JButton("median");
		medianButton.setBounds(0, 180, 100, 30);
		medianButton.addActionListener(this);
		panel.add(medianButton);

		medianF = new JTextField(2);
		medianF.setBounds(100, 180, 30, 30);
		panel.add(medianF);

		filterO3 = new JButton("o3");
		filterO3.setBounds(0, 210, 100, 30);
		filterO3.addActionListener(this);
		panel.add(filterO3);

		filterS6 = new JButton("s6");
		filterS6.setBounds(0, 240, 100, 30);
		filterS6.addActionListener(this);
		panel.add(filterS6);

		splotF = new JTextField(2);
		splotF.setBounds(100, 240, 30, 30);
		panel.add(splotF);

		h3Button = new JButton("h3");
		h3Button.setBounds(0, 270, 100, 30);
		h3Button.addActionListener(this);
		panel.add(h3Button);

		/*
		histogramButton = new JButton("histogram");
		histogramButton.setBounds(0, 300, 100, 30);
		histogramButton.addActionListener(this);
		panel.add(histogramButton);
*/
		histogramButton2 = new JButton("histogram");
		histogramButton2.setBounds(0, 330, 100, 30);
		histogramButton2.addActionListener(this);
		panel.add(histogramButton2);

		FileDialog fd = new FileDialog(f, "Wczytaj Obrazek", FileDialog.LOAD);
		fd.setLocationRelativeTo(null);
		fd.setVisible(true);
		String katalog = fd.getDirectory();
		String plik = fd.getFile();
		File imageFile = new File(katalog + plik);

		try {
			image = ImageIO.read(imageFile);
			width = image.getWidth();
			height = image.getHeight();

			result = convertTo2DUsingGetRGB(image);
			image2 = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

			for (int i = 0; i < width; i++)
				for (int j = 0; j < height; j++) {
					image2.setRGB(i, j, result[j][i]);
				}
		} catch (IOException e) {
			System.err.println("Blad odczytu obrazka");
			e.printStackTrace();
		}

		podglad = new ImageIcon(image2);
		podgladLabel = new JLabel(podglad);
		podgladLabel.setBounds(100, 10, width, height);

		if (height < 350) {
			f.setSize(width + 180, 520);
		} else
			f.setSize(width + 180, height + 200);

		panel.add(podgladLabel);
		f.add(panel);
		pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

	/*
	 * Funkcja przetwarza wczytany obrazek w tablice 2d, ¿eby mo¿na by³o dostaæ
	 * adres ka¿dego piksela
	 */
	static int[][] convertTo2DUsingGetRGB(BufferedImage image) {
		int width = image.getWidth();
		int height = image.getHeight();
		int[][] result = new int[height][width];

		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				result[row][col] = image.getRGB(col, row);
			}
		}
		return result;
	}

	private static int getR(int in) {
		return (int) ((in << 8) >> 24) & 0xff;
	}

	private static int getG(int in) {
		return (int) ((in << 16) >> 24) & 0xff;
	}

	private static int getB(int in) {
		return (int) ((in << 24) >> 24) & 0xff;
	}

	private static int toRGB(int r, int g, int b) {
		return (int) ((((r << 8) | g) << 8) | b);
	}

	public void negative() {
		int tmp;
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				tmp = 255 - result[j][i];
				image2.setRGB(i, j, tmp);
			}
		}
		result = convertTo2DUsingGetRGB(image2);
	}

	public void original() {
		result = convertTo2DUsingGetRGB(image);
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				image2.setRGB(i, j, result[j][i]);
			}
		}
	}

	public void raleigh(int min, int max) {//H3

		int[] R = new int[256];
		int[] G = new int[256];
		int[] B = new int[256];

		double tmp;
		for (int i = 0; i < 256; i++) {
			R[i] = 0;
			G[i] = 0;
			B[i] = 0;
		}

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				R[getR(result[j][i])]++;
				G[getG(result[j][i])]++;
				B[getB(result[j][i])]++;
				J[(int) (0.299 * getR(result[j][i]) + 0.587 * getG(result[j][i]) + 0.114 * getB(result[j][i]))]++;
			}
		}

		for (int i = 0; i < 256; i++) {
			int suma = 0;
			for (int j = 0; j < i; j++)
				suma += J[j];
			tmp = (50 + Math.pow((2 * Math.pow(0.5, 2) * Math.pow(Math.log(suma / width * height), -1)), 0.5));
			System.out.println(tmp);
		}
	}

	public void contrast(double value) {
		double R;
		double G;
		double B;
		int tmpR, tmpG, tmpB;
		int tmp;
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				R = value * (getR(result[j][i]) - 127) + 127;
				G = value * (getG(result[j][i]) - 127) + 127;
				B = value * (getB(result[j][i]) - 127) + 127;

				if (R > 255)
					tmpR = 255;
				else if (R < 0)
					tmpR = 0;
				else
					tmpR = (int) R;

				if (G > 255)
					tmpG = 255;
				else if (G < 0)
					tmpG = 0;
				else
					tmpG = (int) G;

				if (B > 255)
					tmpB = 255;
				else if (B < 0)
					tmpB = 0;
				else
					tmpB = (int) B;
				tmp = toRGB(tmpR, tmpG, tmpB);
				image2.setRGB(i, j, tmp);
			}
		}
	}

	public void lighten(int value) {
		int R, G, B, tmpR, tmpG, tmpB;
		int tmp;
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				R = getR(result[j][i]) + value;
				G = getG(result[j][i]) + value;
				B = getB(result[j][i]) + value;

				if (R > 255)
					tmpR = 255;
				else if (R < 0)
					tmpR = 0;
				else
					tmpR = R;

				if (G > 255)
					tmpG = 255;
				else if (G < 0)
					tmpG = 0;
				else
					tmpG = G;

				if (B > 255)
					tmpB = 255;
				else if (B < 0)
					tmpB = 0;
				else
					tmpB = B;

				tmp = toRGB(tmpR, tmpG, tmpB);
				image2.setRGB(i, j, tmp);
			}
		}
	}

	public void filtracjaNieliniowa() {//O3
		int tmp, tmpR, tmpG, tmpB;

		for (int i = 1; i < width - 1; i++) {
			for (int j = 1; j < height - 1; j++) {

				int SxR = (getR(result[j + 1][i - 1]) + 2 * getR(result[j + 1][i]) + getR(result[j + 1][i + 1]))
						- (getR(result[j - 1][i - 1]) + 2 * getR(result[j - 1][i]) + getR(result[j - 1][i + 1]));
				int SyR = (getR(result[j - 1][i - 1]) + 2 * getR(result[j][i - 1]) + getR(result[j + 1][i - 1]))
						- (getR(result[j - 1][i + 1]) + 2 * getR(result[j][i + 1]) + getR(result[j + 1][i + 1]));
				tmpR = (int) Math.sqrt(Math.pow(SxR, 2) + Math.pow(SyR, 2));

				int SxG = (getG(result[j + 1][i - 1]) + 2 * getG(result[j + 1][i]) + getG(result[j + 1][i + 1]))
						- (getG(result[j - 1][i - 1]) + 2 * getG(result[j - 1][i]) + getG(result[j - 1][i + 1]));
				int SyG = (getG(result[j - 1][i - 1]) + 2 * getG(result[j][i - 1]) + getG(result[j + 1][i - 1]))
						- (getG(result[j - 1][i + 1]) + 2 * getG(result[j][i + 1]) + getG(result[j + 1][i + 1]));
				tmpG = (int) Math.sqrt(Math.pow(SxG, 2) + Math.pow(SyG, 2));

				int SxB = (getB(result[j + 1][i - 1]) + 2 * getB(result[j + 1][i]) + getB(result[j + 1][i + 1]))
						- (getB(result[j - 1][i - 1]) + 2 * getB(result[j - 1][i]) + getB(result[j - 1][i + 1]));
				int SyB = (getB(result[j - 1][i - 1]) + 2 * getB(result[j][i - 1]) + getB(result[j + 1][i - 1]))
						- (getB(result[j - 1][i + 1]) + 2 * getB(result[j][i + 1]) + getB(result[j + 1][i + 1]));
				tmpB = (int) Math.sqrt(Math.pow(SxB, 2) + Math.pow(SyB, 2));
				tmp = toRGB(tmpR, tmpG, tmpB);

				image2.setRGB(i, j, tmp);
			}
		}
		result = convertTo2DUsingGetRGB(image2);
	}

	int[][] w1 = { { -1, 2, -1 }, { -1, 2, -1 }, { -1, 2, -1 } };
	int[][] w2 = { { -1, -1, -1 }, { 2, 2, 2 }, { -1, -1, -1 } };
	int[][] w3 = { { -1, -1, 2 }, { -1, 2, -1 }, { 2, -1, -1 } };
	int[][] w4 = { { 2, -1, -1 }, { -1, 2, -1 }, { -1, -1, 2 } };

	public void splot(int v) {//S6
		int tmp, tmpR, tmpG, tmpB;

		for (int i = 1; i < width - 1; i++) {
			for (int j = 1; j < height - 1; j++) {

				double red = 0.0, green = 0.0, blue = 0.0;

				for (int filterX = 0; filterX < 3; filterX++)
					for (int filterY = 0; filterY < 3; filterY++) {
						int imageX = (j - 3 / 2 + filterX + width) % width;
						int imageY = (i - 3 / 2 + filterY + height) % height;
						if (v == 1) {
							red += getR(result[imageX][imageY]) * w1[filterX][filterY];
							green += getG(result[imageX][imageY]) * w1[filterX][filterY];
							blue += getB(result[imageX][imageY]) * w1[filterX][filterY];
						}
						if (v == 2) {
							red += getR(result[imageX][imageY]) * w2[filterX][filterY];
							green += getG(result[imageX][imageY]) * w2[filterX][filterY];
							blue += getB(result[imageX][imageY]) * w2[filterX][filterY];
						}
						if (v == 3) {
							red += getR(result[imageX][imageY]) * w3[filterX][filterY];
							green += getG(result[imageX][imageY]) * w3[filterX][filterY];
							blue += getB(result[imageX][imageY]) * w3[filterX][filterY];
						}
						if (v == 4) {
							red += getR(result[imageX][imageY]) * w4[filterX][filterY];
							green += getG(result[imageX][imageY]) * w4[filterX][filterY];
							blue += getB(result[imageX][imageY]) * w4[filterX][filterY];
						}
					}

				if (red < 0)
					tmpR = 0;
				else if (red > 255)
					tmpR = 255;
				else
					tmpR = (int) red;

				if (green < 0)
					tmpG = 0;
				else if (green > 255)
					tmpG = 255;
				else
					tmpG = (int) green;

				if (blue < 0)
					tmpB = 0;
				else if (blue > 255)
					tmpB = 255;
				else
					tmpB = (int) blue;
				tmp = toRGB(tmpR, tmpG, tmpB);
				image2.setRGB(i, j, tmp);
			}
		}
		result = convertTo2DUsingGetRGB(image2);
	}

	public void saveToFile() {
		File outputfile = new File("saved.jpg");
		try {
			ImageIO.write(image2, "jpg", outputfile);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public void generateHistogram() {
		result = convertTo2DUsingGetRGB(image2);

		for (int i = 0; i < 256; i++) {
			R[i] = 0;
			G[i] = 0;
			B[i] = 0;
			J[i] = 0;
		}

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				R[getR(result[j][i])]++;
				G[getG(result[j][i])]++;
				B[getB(result[j][i])]++;
				J[(int) (0.299 * getR(result[j][i]) + 0.587 * getG(result[j][i]) + 0.114 * getB(result[j][i]))]++;
			}
		}
		new HistogramFrameChart(R, G, B, this);
	}

	public void generateHistogram_2() {
		result = convertTo2DUsingGetRGB(image2);

		for (int i = 0; i < 256; i++) {
			R[i] = 0;
			G[i] = 0;
			B[i] = 0;
		}

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				R[getR(result[j][i])]++;
				G[getG(result[j][i])]++;
				B[getB(result[j][i])]++;
			}
		}
		new HistogramFrameChart_2(R, G, B, this);
	}

	public void arithmetic(int mask) {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				image2.setRGB(i, j, arithmeticAlogithm(i, j, mask));
			}
		}
		result = convertTo2DUsingGetRGB(image2);
	}

	public int arithmeticAlogithm(int x, int y, int m) {

		int R = 0;
		int G = 0;
		int B = 0;
		int tmp;
		int licznik = 0;

		for (int i = x - m / 2; i < x + m / 2; i++) {
			for (int j = y - m / 2; j < y + m / 2; j++) {
				if (i >= 0 && j >= 0 && i < width && j < height) {
					R += getR(result[j][i]);
					G += getG(result[j][i]);
					B += getB(result[j][i]);
					licznik++;
				}
			}
		}

		R = R / (licznik);
		G = G / (licznik);
		B = B / (licznik);
		tmp = toRGB(R, G, B);

		return tmp;
	}

	public void median(int mask) {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				image2.setRGB(i, j, medianAlogithm(i, j, mask));
			}
		}
		result = convertTo2DUsingGetRGB(image2);
	}

	public int medianAlogithm(int x, int y, int m) {

		int R = 0;
		int G = 0;
		int B = 0;
		int tmp;
		int licznik = 0;

		for (int i = x - m / 2; i < x + m / 2; i++) {
			for (int j = y - m / 2; j < y + m / 2; j++) {
				if (i >= 0 && j >= 0 && i < width && j < height) {
					licznik++;
				}
			}
		}

		int[] RT = new int[licznik];
		int[] GT = new int[licznik];
		int[] BT = new int[licznik];

		licznik = 0;

		for (int i = x - m / 2; i < x + m / 2; i++) {
			for (int j = y - m / 2; j < y + m / 2; j++) {
				if (i >= 0 && j >= 0 && i < width && j < height) {
					RT[licznik] = getR(result[j][i]);
					GT[licznik] = getG(result[j][i]);
					BT[licznik] = getB(result[j][i]);
					licznik++;
				}
			}
		}

		Arrays.sort(RT);
		Arrays.sort(GT);
		Arrays.sort(BT);

		if (licznik % 2 == 0) { // parzysta

			if (licznik == 1) {
				R = (RT[0] + RT[0] + 1) / 2;
				G = (GT[0] + GT[0] + 1) / 2;
				B = (BT[0] + BT[0] + 1) / 2;
			} else {
				R = (RT[licznik / 2] + RT[licznik / 2] + 1) / 2;
				G = (GT[licznik / 2] + GT[licznik / 2] + 1) / 2;
				B = (BT[licznik / 2] + BT[licznik / 2] + 1) / 2;
			}

			tmp = toRGB(R, G, B);

			return tmp;
		} else {
			if (licznik == 1) {
				R = RT[0];
				G = GT[0];
				B = BT[0];
			} else {
				R = RT[(licznik + 1) / 2];
				G = GT[(licznik + 1) / 2];
				B = BT[(licznik + 1) / 2];
			}

			tmp = toRGB(R, G, B);

			return tmp;
		}
	}

	public void h3() {
		int R = 0;
		int G = 0;
		int B = 0;
		int tmp2 = 0;
		int YTable, UTable, VTable;

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				YTable = (int) ((0.257 * getR(result[j][i])) - (0.504 * getG(result[j][i]))
						- (0.098 * getB(result[j][i])) + 128);
				VTable = (int) ((0.439 * getR(result[j][i])) - (0.368 * getG(result[j][i]))
						- (0.071 * getB(result[j][i])) + 128);
				UTable = (int) (-(0.148 * getR(result[j][i])) - (0.291 * getG(result[j][i]))
						- (0.439 * getB(result[j][i])) + 128);
				int suma = 0;
				for (int k = 0; k < 256; k++) {
					suma += lightTable[k];
				}

				double tmp = (1
						+ Math.pow((2 * Math.pow(0.1, 2) * Math.pow(Math.log(suma / width * height), -1)), 0.5));

				R = (int) (1 * (tmp) + 1.13983 * (VTable));
				G = (int) (1 * (tmp) - 0.39465 * (VTable) - 0.58060 * (UTable));
				B = (int) (1 * (tmp) + 2.03211 * (UTable));

				if (R < 0)
					R = 0;
				else if (R > 255)
					R = 255;

				if (G < 0)
					G = 0;
				else if (G > 255)
					G = 255;

				if (B < 0)
					B = 0;
				else if (B > 255)
					B = 255;

				tmp2 = toRGB(R, G, B);
				image2.setRGB(i, j, tmp2);
			}
		}
	}

	////////////////////////////////////////////////////
	// //
	// Przyciski //
	// //
	////////////////////////////////////////////////////

	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();	

		if (source == negativeButton) {
			negative();
			panel.repaint();
		}
		if (source == resetButton) {
			original();
			panel.repaint();
		}
		if (source == arithmeticButton) {
			int mask = Integer.parseInt(arithmeticF.getText());
			if (mask > 1) {
				arithmetic(mask);
				panel.repaint();
			}
		}
		if (source == medianButton) {
			int mask = Integer.parseInt(medianF.getText());
			if (mask > 1) {
				median(mask);
				panel.repaint();
			}
		}
		if (source == filterO3) {
			filtracjaNieliniowa();
			panel.repaint();
		}
		if (source == filterS6) {
			splot(Integer.parseInt(splotF.getText()));
			panel.repaint();
		}
		if (source == saveButton) {
			saveToFile();
		}
		if (source == contrastButton) {
			new PopupContrast(this);
		}

		if (source == lightenButton) {
			new PopupLighten(this);
		}
/*
		if (source == histogramButton) {
			generateHistogram();
		}*/
		if (source == histogramButton2) {
			generateHistogram_2();
		}
		if (source == h3Button) {
			h3();
			panel.repaint();
		}
	}
}