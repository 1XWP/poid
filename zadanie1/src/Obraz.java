import java.awt.FileDialog;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
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

	private JButton negativeButton, resetButton, arithmeticButton, saveButton, contrastButton, lightenButton, filterO3,
			filterS6, medianButton, h3Button, histogramButton2, FFTButton, phaseButton, magnitudeButton, IFFTButton,
			dolnoprzepustowyButton, gornoprzepustowyButton, pasmowozaporowyButton, edgeDettectionButton,
			segmentationButton, shiftButton;

	private JTextField arithmeticF, medianF, splotF, DPF, GPF, PZF1, PZF2, EDF1, EDF2, EDF3, segmentationF;

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
		 * histogramButton = new JButton("histogram");
		 * histogramButton.setBounds(0, 300, 100, 30);
		 * histogramButton.addActionListener(this); panel.add(histogramButton);
		 */
		histogramButton2 = new JButton("histogram");
		histogramButton2.setBounds(0, 330, 100, 30);
		histogramButton2.addActionListener(this);
		panel.add(histogramButton2);
		// *************************************************<ZAD2
		FFTButton = new JButton("FFT");
		FFTButton.setBounds(0, 360, 100, 30);
		FFTButton.addActionListener(this);
		panel.add(FFTButton);

		phaseButton = new JButton("widmo fazowe");
		phaseButton.setBounds(0, 390, 100, 30);
		phaseButton.addActionListener(this);
		panel.add(phaseButton);

		magnitudeButton = new JButton("widmo mocy");
		magnitudeButton.setBounds(0, 420, 100, 30);
		magnitudeButton.addActionListener(this);
		panel.add(magnitudeButton);

		dolnoprzepustowyButton = new JButton("DP");
		dolnoprzepustowyButton.setBounds(0, 450, 100, 30);
		dolnoprzepustowyButton.addActionListener(this);
		panel.add(dolnoprzepustowyButton);

		DPF = new JTextField(2);
		DPF.setBounds(100, 450, 30, 30);
		panel.add(DPF);

		gornoprzepustowyButton = new JButton("GP");
		gornoprzepustowyButton.setBounds(0, 480, 100, 30);
		gornoprzepustowyButton.addActionListener(this);
		panel.add(gornoprzepustowyButton);

		GPF = new JTextField(2);
		GPF.setBounds(100, 480, 30, 30);
		panel.add(GPF);

		pasmowozaporowyButton = new JButton("PZ");
		pasmowozaporowyButton.setBounds(0, 510, 100, 30);
		pasmowozaporowyButton.addActionListener(this);
		panel.add(pasmowozaporowyButton);

		PZF1 = new JTextField(2);
		PZF1.setBounds(100, 510, 30, 30);
		panel.add(PZF1);

		PZF2 = new JTextField(2);
		PZF2.setBounds(130, 510, 30, 30);
		panel.add(PZF2);

		edgeDettectionButton = new JButton("ED");
		edgeDettectionButton.setBounds(0, 540, 100, 30);
		edgeDettectionButton.addActionListener(this);
		panel.add(edgeDettectionButton);

		EDF1 = new JTextField(2);
		EDF1.setBounds(100, 540, 30, 30);
		panel.add(EDF1);

		EDF2 = new JTextField(2);
		EDF2.setBounds(130, 540, 30, 30);
		panel.add(EDF2);

		EDF3 = new JTextField(2);
		EDF3.setBounds(160, 540, 30, 30);
		panel.add(EDF3);

		IFFTButton = new JButton("IFFT");
		IFFTButton.setBounds(0, 570, 100, 30);
		IFFTButton.addActionListener(this);
		panel.add(IFFTButton);

		segmentationButton = new JButton("seg");
		segmentationButton.setBounds(0, 600, 100, 30);
		segmentationButton.addActionListener(this);
		panel.add(segmentationButton);

		segmentationF = new JTextField(2);
		segmentationF.setBounds(100, 600, 30, 30);
		panel.add(segmentationF);

		shiftButton = new JButton("shift");
		shiftButton.setBounds(150, 600, 100, 30);
		shiftButton.addActionListener(this);
		panel.add(shiftButton);
		// *************************ZAD2>

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

	public void raleigh(int min, int max) {// H3

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

	public void filtracjaNieliniowa() {// O3
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

	// ***********<ZAD2
	int[][] dp = { { 1, 2, 1 }, { 2, 4, 2 }, { 1, 2, 1 } };
	int[][] gp = { { 0, -1, 0 }, { -1, 5, -1 }, { 0, -1, 0 } };
	int[][] dk = { { 1, 1, 1 }, { 0, 0, 0 }, { -1, -1, -1 } };//detekcja krawêdzi
	// *********ZAD2>

	public void splot(int v) {// S6
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
						// **********<ZAD2
						if (v == 5) {
							red += getR(result[imageX][imageY]) * dp[filterX][filterY] / 16;
							green += getG(result[imageX][imageY]) * dp[filterX][filterY] / 16;
							blue += getB(result[imageX][imageY]) * dp[filterX][filterY] / 16;
						}
						if (v == 6) {
							red += getR(result[imageX][imageY]) * gp[filterX][filterY];
							green += getG(result[imageX][imageY]) * gp[filterX][filterY];
							blue += getB(result[imageX][imageY]) * gp[filterX][filterY];
						}
						if (v == 7) {
							red += getR(result[imageX][imageY]) * dk[filterX][filterY];
							green += getG(result[imageX][imageY]) * dk[filterX][filterY];
							blue += getB(result[imageX][imageY]) * dk[filterX][filterY];
						}
						// ***************ZAD2>
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

	// **********************************************ZAD2>
	int nextX;
	int nextY;
	int tmp[][] = null;
	int start;
	int mask = 1;
	int colorMask = 0;
	Queue<Point> pointToCheck = new LinkedList<Point>();

	public void segmentation(int threshold) {
		tmp = new int[width][height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				tmp[i][j] = 0;
			}
		}
		nextX = 0;
		nextY = 0;
		start = result[nextX][nextY];
		colorMask = losujKolor();
		while (nextPixel()) {
			for (int x = nextX - 1; x < nextX + 2; x++) {
				for (int y = nextY - 1; y < nextY + 2; y++) {
					if (x >= 0 && y >= 0 && x < width && y < height) {
						if (tmp[x][y] == 0) {
							if (checkPixels(result[x][y], start, threshold)) {
								tmp[x][y] = colorMask;
								pointToCheck.add(new Point(x, y));
							}
						}
					}
				}
			}
			nextX = -1;
			nextY = -1;
		}

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				image2.setRGB(i, j, tmp[j][i]);
			}
		}

		result = convertTo2DUsingGetRGB(image2);
	}

	public int losujKolor() {
		int randomR = random.nextInt(255);
		int randomG = random.nextInt(255);
		int randomB = random.nextInt(255);
		return toRGB(randomR, randomG, randomB);
	}

	public boolean checkPixels(int color, int start, int threshold) {
		if (Math.abs(getR(color) - getR(start)) <= threshold && Math.abs(getG(color) - getG(start)) <= threshold
				&& Math.abs(getB(color) - getB(start)) <= threshold) {
			return true;
		} else {
			return false;
		}
	}

	public boolean nextPixel() {
		if (nextX != -1) {
			return true;
		} else {
			if (pointToCheck.size() > 0) {
				Point p = pointToCheck.peek();
				pointToCheck.remove();
				nextX = p.x;
				nextY = p.y;
				return true;
			} else {
				Random rand = new Random();
				int x = rand.nextInt(width - 1);
				int y = rand.nextInt(height - 1);
				int range = width * height;
				while (tmp[x][y] != 0 && range > 0) {
					x++;
					range--;
					if (x >= width) {
						x = 0;
						y = (y + 1) % height;
					}
				}
				if (range == 0) {
					return false;
				} else {
					nextX = x;
					nextY = y;
					start = result[x][y];
					colorMask = losujKolor();
					return true;
				}
			}
		}
	}

	public void FFT(double[] dbR, double[] dbI) {

		int lenght = dbR.length;
		double sortR[] = new double[lenght];
		double sortI[] = new double[lenght];

		for (int i = 0; i < lenght; i++) {
			int j = findIndex(i, lenght);
			sortR[i] = dbR[j];
			sortI[i] = dbI[j];
		}

		int blockNr, bfNr, m;
		double firstR, firstI, secondR, secondI;

		for (int N = 2; N <= lenght; N = 2 * N) {
			blockNr = lenght / N;
			bfNr = N / 2;
			for (int i = 0; i < blockNr; i++) {
				for (int j = 0; j < bfNr; j++) {
					m = i * N + j;

					// (R1 + R2*cos(2pim/N) + I2*sin(2pim/N)) + (I1 -
					// R2*sin(2pim/N) + I2*cos(2pim/N))j
					firstR = sortR[m] + sortR[m + (N / 2)] * Math.cos(2 * Math.PI * m / N)
							+ sortI[m + (N / 2)] * Math.sin(2 * Math.PI * m / N);
					firstI = sortI[m] - sortR[m + (N / 2)] * Math.sin(2 * Math.PI * m / N)
							+ sortI[m + (N / 2)] * Math.cos(2 * Math.PI * m / N);

					// (R1 - R2*cos(2pim/N) - I2*sin(2pim/N)) + (I1 +
					// R2*sin(2pim/N) - I2*cos(2pim/N))j
					secondR = sortR[m] - sortR[m + (N / 2)] * Math.cos(2 * Math.PI * m / N)
							- sortI[m + (N / 2)] * Math.sin(2 * Math.PI * m / N);
					secondI = sortI[m] + sortR[m + (N / 2)] * Math.sin(2 * Math.PI * m / N)
							- sortI[m + (N / 2)] * Math.cos(2 * Math.PI * m / N);

					sortR[m] = firstR;
					sortI[m] = firstI;
					sortR[m + (N / 2)] = secondR;
					sortI[m + (N / 2)] = secondI;
				}
			}
		}
		for (int i = 0; i < lenght; i++) {
			dbR[i] = (sortR[i] / lenght);
			dbI[i] = (sortI[i] / lenght);
		}
	}

	public void IFFT(double[] dbR, double[] dbI) {

		int lenght = dbR.length;
		double sortR[] = new double[lenght];
		double sortI[] = new double[lenght];

		for (int i = 0; i < lenght; i++) {
			int j = findIndex(i, lenght);
			sortR[i] = dbR[j];
			sortI[i] = dbI[j];
		}

		int blockNr, bfNr, m;
		double firstR, firstI, secondR, secondI;

		for (int N = 2; N <= lenght; N = 2 * N) {
			blockNr = lenght / N;
			bfNr = N / 2;
			for (int i = 0; i < blockNr; i++) {
				for (int j = 0; j < bfNr; j++) {
					m = i * N + j;

					// (R1 + R2*cos(2pim/N) - I2*sin(2pim/N)) + (I1 +
					// R2*sin(2pim/N) + I2*cos(2pim/N))j
					firstR = sortR[m] + sortR[m + (N / 2)] * Math.cos(2 * Math.PI * m / N)
							- sortI[m + (N / 2)] * Math.sin(2 * Math.PI * m / N);
					firstI = sortI[m] + sortR[m + (N / 2)] * Math.sin(2 * Math.PI * m / N)
							+ sortI[m + (N / 2)] * Math.cos(2 * Math.PI * m / N);

					// (R1 - R2*cos(2pim/N) + I2*sin(2pim/N)) + (I1 -
					// R2*sin(2pim/N) - I2*cos(2pim/N))j
					secondR = sortR[m] - sortR[m + (N / 2)] * Math.cos(2 * Math.PI * m / N)
							+ sortI[m + (N / 2)] * Math.sin(2 * Math.PI * m / N);
					secondI = sortI[m] - sortR[m + (N / 2)] * Math.sin(2 * Math.PI * m / N)
							- sortI[m + (N / 2)] * Math.cos(2 * Math.PI * m / N);

					sortR[m] = firstR;
					sortI[m] = firstI;
					sortR[m + (N / 2)] = secondR;
					sortI[m + (N / 2)] = secondI;
				}
			}
		}
		for (int i = 0; i < lenght; i++) {
			dbR[i] = sortR[i];
			dbI[i] = sortI[i];
		}
	}

	private int findIndex(int index, int n) {
		int newIndex = 0;
		int bitsNr = (int) (Math.log(n) / Math.log(2));

		int bit;
		for (int i = 0; i < bitsNr; i++) {
			bit = index % 2;
			index = index >> 1;
			newIndex = newIndex << 1;
			newIndex += bit;
		}
		return newIndex;
	}

	public void revertQuarters(double[][] resultR) {
		int halfWidth = resultR.length / 2;
		int halfHeight = resultR[0].length / 2;

		for (int i = 0; i < halfWidth; i++) {
			for (int j = 0; j < halfHeight; j++) {
				// drua z czwartaÂ…
				double tmp = resultR[i][j];
				resultR[i][j] = resultR[halfHeight + i][halfWidth + j];
				resultR[halfHeight + i][halfWidth + j] = tmp;

				// pierwsza z trzeciaÂ…
				tmp = resultR[i][halfWidth + j];
				resultR[i][halfWidth + j] = resultR[halfHeight + i][j];
				resultR[halfHeight + i][j] = tmp;
			}
		}
	}

	public void phase(double[][] dbR, double[][] dbI) {
		int[][] resultR = new int[dbR.length][dbR.length];
		double max = 0, min = 0, x = 0;
		for (int i = 0; i < dbR.length; i++) {
			for (int j = 0; j < dbR.length; j++) {
				max = Math.max(max, Math.atan(dbI[i][j] / dbR[i][j]));
				min = Math.min(min, Math.atan(dbI[i][j] / dbR[i][j]));
			}
		}
		for (int i = 0; i < dbR.length; i++) {
			for (int j = 0; j < dbR.length; j++) {
				x = Math.atan(dbI[i][j] / dbR[i][j]);
				resultR[i][j] = (int) ((x - min) * 255 / (max - min) + 0);
			}
		}
		int tmp2;
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				tmp2 = toRGB(resultR[i][j], resultR[i][j], resultR[i][j]);
				image2.setRGB(i, j, tmp2);
			}
		}
	}

	public void magnitude(double[][] dbR, double[][] dbI) {
		int[][] resultR = new int[dbR.length][dbR.length];
		double max = 0, min = 0, x = 0;
		for (int i = 0; i < dbR.length; i++) {
			for (int j = 0; j < dbR.length; j++) {
				x = Math.sqrt(Math.pow(dbR[i][j], 2) + Math.pow(dbI[i][j], 2));
				max = Math.max(max, x);
				min = Math.min(min, x);
			}
		}

		for (int i = 0; i < dbR.length; i++) {
			for (int j = 0; j < dbR.length; j++) {
				x = Math.abs(Math.sqrt(Math.pow(dbR[i][j], 2) + Math.pow(dbI[i][j], 2)));
				resultR[i][j] = (int) (60 * Math.log10((1 + 1000000 * x) / (1 + max)));
			}
		}

		int tmp2;
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {

				if (resultR[i][j] > 255) {
					resultR[i][j] = 255;
				}

				tmp2 = toRGB(resultR[i][j], resultR[i][j], resultR[i][j]);
				image2.setRGB(i, j, tmp2);
			}
		}
	}

	public void DP(double d) {

		int x_center = width / 2;
		int y_center = height / 2;

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {

				if ((Math.pow((i - x_center), 2) + Math.pow((j - y_center), 2)) <= Math.pow(d, 2)) {
				} else {
					dbRresult[i][j] = 0;
					dbIresult[i][j] = 0;
				}
			}
		}

		drawThis();
		panel.repaint();
	}

	public void GP(double d) {

		int x_center = width / 2;
		int y_center = height / 2;

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {

				if ((Math.pow((i - x_center), 2) + Math.pow((j - y_center), 2)) <= Math.pow(d, 2)) {
					dbRresult[i][j] = 0;
					dbIresult[i][j] = 0;
				}
			}
		}
		drawThis();
		panel.repaint();
	}

	public void PZ(double d1, double d2) {

		int x_center = width / 2;
		int y_center = height / 2;

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if ((Math.pow((i - x_center), 2) + Math.pow((j - y_center), 2)) <= Math.pow(d1, 2)
						& (Math.pow((i - x_center), 2) + Math.pow((j - y_center), 2)) >= Math.pow(d2, 2)) {
					dbRresult[i][j] = 0;
					dbIresult[i][j] = 0;
				}
			}
		}
		drawThis();
		panel.repaint();
	}

	public void ED(double d1, double d2, double alpha) {

		DP = false;

		int x_center = width / 2;
		int y_center = height / 2;

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {

				if ((Math.pow((i - x_center), 2) + Math.pow((j - y_center), 2)) <= Math.pow(d1, 2)) {
				} else {
					dbRresult[i][j] = 0;
					dbIresult[i][j] = 0;
				}

				if ((Math.pow((i - x_center), 2) + Math.pow((j - y_center), 2)) <= Math.pow(d2, 2)) {
					dbRresult[i][j] = 0;
					dbIresult[i][j] = 0;
				}
			}
		}

		double alpha2 = 0;

		if (alpha == 90) {
			alpha2 = 0;
		} else if (alpha < 90 && alpha > 0) {
			alpha2 = 90 - alpha;
		} else if (alpha > 90) {
			alpha2 = alpha - 90;
			alpha2 = -alpha2;
		}

		int dl = 22560;
		double cosAlpha = Math.cos(Math.toRadians(alpha2));

		int dl2 = (int) (dl / cosAlpha);
		int XX = (int) (dl2 * Math.cos(Math.toRadians(alpha2)));
		int YY = (int) (dl2 * Math.sin(Math.toRadians(alpha2)));

		for (int x = 256; x < width; x++) {
			for (int y = 0; y < height; y++) {

				int Ax = 256;
				int Ay = -22560;
				int Bx = 256;
				int By = 256;
				int Cx = 256 + XX;
				int Cy = 256 - YY;

				int q1 = (Ax - Bx) * (y - Ay) - (Ay - By) * (x - Ax);
				int q2 = (Bx - Cx) * (y - By) - (By - Cy) * (x - Bx);
				int q3 = (Cx - Ax) * (y - Cy) - (Cy - Ay) * (x - Cx);

				if (q1 > 0 && q2 > 0 && q3 > 00) {
					dbRresult[x][y] = 0;
					dbIresult[x][y] = 0;
				}
			}
		}

		for (int x = 0; x < 256; x++) {
			for (int y = 0; y < height; y++) {

				int Ax = 256;
				int Ay = 25120;
				int Bx = 256;
				int By = 256;
				int Cx = 256 - XX;
				int Cy = 256 + YY;
				int q1 = (Ax - Bx) * (y - Ay) - (Ay - By) * (x - Ax);
				int q2 = (Bx - Cx) * (y - By) - (By - Cy) * (x - Bx);
				int q3 = (Cx - Ax) * (y - Cy) - (Cy - Ay) * (x - Cx);

				if (q1 > 0 && q2 > 0 && q3 > 00) {
					dbRresult[x][y] = 0;
					dbIresult[x][y] = 0;
				}
			}
		}
		drawThis();
		panel.repaint();
	}

	public void shift_phase() {
		int N = dbRresult.length;

		System.out.println(N);

		double k = 0.9;
		double l = 0.1;
		for (int n = 0; n < N; n++) {
			for (int m = 0; m < N; m++) {

				double inExp;

				inExp = (-n * k * 2 * Math.PI) / N + (-m * l * 2 * Math.PI) / N + (k + l) * Math.PI;
				dbRresult[n][m] = Math.cos(inExp) * dbRresult[n][m] + Math.sin(inExp) * dbIresult[n][m];
				dbIresult[n][m] = Math.cos(inExp) * dbIresult[n][m] + Math.sin(inExp) * dbIresult[n][m];
			}
		}

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {

				image2.setRGB(i, j,
						toRGB(getR((int) dbRresult[i][j]), getR((int) dbRresult[i][j]), getR((int) dbRresult[i][j])));
			}
		}

		panel.repaint();
	}

	public void drawThis() {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				image2.setRGB(i, j,
						toRGB(getR((int) dbRresult[i][j]), getR((int) dbRresult[i][j]), getR((int) dbRresult[i][j])));
			}
		}
		result = convertTo2DUsingGetRGB(image2);
	}
	// ************************************************************ZAD2>
	
	////////////////////////////////////////////////////
	// //
	// Przyciski //
	// //
	////////////////////////////////////////////////////

	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		

		if (source == FFTButton) {

			DP = false;
			GP = false;

			dbRresult = new double[width][height];
			dbIresult = new double[width][height];
			for (int i = 0; i < width; i++) {
				for (int j = 0; j < height; j++) {

					dbRresult[i][j] = result[i][j];
					dbIresult[i][j] = 0.0;
				}
				FFT(dbRresult[i], dbIresult[i]);
			}

			for (int i = 0; i < height; i++) {
				double[] tmpR = new double[width];
				double[] tmpI = new double[width];
				for (int j = 0; j < width; j++) {
					tmpR[j] = dbRresult[j][i];
					tmpI[j] = dbIresult[j][i];
				}
				FFT(tmpR, tmpI);
				for (int j = 0; j < width; j++) {
					dbRresult[j][i] = tmpR[j];
					dbIresult[j][i] = tmpI[j];
				}
			}
			revertQuarters(dbRresult);
			revertQuarters(dbIresult);

			drawThis();
			panel.repaint();
		}

		if (source == IFFTButton) {
			dbRresultBACK = new double[width][height];
			dbIresultBACK = new double[width][height];

			revertQuarters(dbRresult);
			revertQuarters(dbIresult);

			for (int i = 0; i < width; i++) {
				for (int j = 0; j < height; j++) {

					dbRresultBACK[i][j] = dbRresult[i][j];
					dbIresultBACK[i][j] = dbIresult[i][j];
				}
				IFFT(dbRresultBACK[i], dbIresultBACK[i]);
			}

			for (int i = 0; i < height; i++) {
				double[] tmpR = new double[width];
				double[] tmpI = new double[width];
				for (int j = 0; j < width; j++) {
					tmpR[j] = dbRresultBACK[j][i];
					tmpI[j] = dbIresultBACK[j][i];
				}
				IFFT(tmpR, tmpI);
				for (int j = 0; j < width; j++) {
					dbRresultBACK[j][i] = tmpR[j];
					dbIresultBACK[j][i] = tmpI[j];
				}
			}

			int tmp2;
			for (int i = 0; i < width; i++) {
				for (int j = 0; j < height; j++) {

					if (dbRresultBACK[j][i] < 0)
						dbRresultBACK[j][i] = -dbRresultBACK[j][i];

					if (DP) {
						dbRresultBACK[j][i] = -dbRresultBACK[j][i];
					}

					tmp2 = toRGB(getR((int) dbRresultBACK[j][i]), getR((int) dbRresultBACK[j][i]),
							getR((int) dbRresultBACK[j][i]));
					image2.setRGB(i, j, tmp2);
				}
			}
			panel.repaint();
		}

		if (source == phaseButton) {
			phase(dbRresult, dbIresult);
			panel.repaint();
		}

		if (source == magnitudeButton) {
			magnitude(dbRresult, dbIresult);
			panel.repaint();
		}

		if (source == dolnoprzepustowyButton) {
			double D = Double.parseDouble(DPF.getText());
			DP = true;
			DP(D);
		}

		if (source == gornoprzepustowyButton) {
			double D = Double.parseDouble(GPF.getText());
			GP = true;
			GP(D);
		}

		if (source == pasmowozaporowyButton) {
			double D1 = Double.parseDouble(PZF1.getText());
			double D2 = Double.parseDouble(PZF2.getText());
			DP = true;
			PZ(D1, D2);
		}

		if (source == edgeDettectionButton) {
			double D1 = Double.parseDouble(EDF1.getText());
			double D2 = Double.parseDouble(EDF2.getText());
			double alpha = Double.parseDouble(EDF3.getText());
			GP = true;
			ED(D1, D2, alpha);
		}

		if (source == segmentationButton) {
			int threshold = Integer.parseInt(segmentationF.getText());
			segmentation(threshold);
			panel.repaint();
		}

		if (source == shiftButton) {
			shift_phase();
		}
		//********************ZAD2>
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
		 * if (source == histogramButton) { generateHistogram(); }
		 */
		if (source == histogramButton2) {
			generateHistogram_2();
		}
		if (source == h3Button) {
			h3();
			panel.repaint();
		}
	}
}