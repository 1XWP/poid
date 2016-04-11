import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.DefaultXYDataset;

public class HistogramFrameChart_2 implements ActionListener {

	private Obraz obraz;

	public HistogramFrameChart_2(int[] R, int[] G, int[] B, final Obraz obraz) {

		this.obraz = obraz;
		JFrame f = new JFrame("Histogram druga wersja");

		final DefaultXYDataset dataset = new DefaultXYDataset();
		JFreeChart chart = ChartFactory.createXYLineChart("Histogram", "", "", dataset, PlotOrientation.VERTICAL, true,
				true, false);
		chart.setBackgroundPaint(Color.white);
		XYPlot plot = (XYPlot) chart.getPlot();
		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinesVisible(true);
		plot.setDomainGridlinePaint(Color.lightGray);
		plot.setRangeGridlinePaint(Color.lightGray);
		ChartPanel chartPanel = new ChartPanel(chart, false);

		JButton rButton = new JButton("R");
		rButton.setBounds(0, 300, 80, 40);
		rButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				double[][] series = new double[2][obraz.R.length];
				for (int i = 0; i < obraz.R.length; i++) {
					series[0][i] = (double) i;
					series[1][i] = obraz.R[i];
				}
				dataset.removeSeries("GREEN");
				dataset.removeSeries("BLUE");
				dataset.addSeries("RED", series);
			}
		});

		JButton gButton = new JButton("G");
		gButton.setBounds(80, 300, 80, 40);
		gButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				double[][] series = new double[2][obraz.G.length];
				for (int i = 0; i < obraz.G.length; i++) {
					series[0][i] = (double) i;
					series[1][i] = obraz.G[i];
				}
				dataset.removeSeries("RED");
				dataset.removeSeries("BLUE");
				dataset.removeSeries("BRIGHTNESS");
				dataset.addSeries("GREEN", series);
			}
		});

		JButton bButton = new JButton("B");
		bButton.setBounds(160, 300, 80, 40);
		bButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				double[][] series = new double[2][obraz.B.length];
				for (int i = 0; i < obraz.B.length; i++) {
					series[0][i] = (double) i;
					series[1][i] = obraz.B[i];
				}
				dataset.removeSeries("RED");
				dataset.removeSeries("GREEN");
				dataset.removeSeries("BRIGHTNESS");
				dataset.addSeries("BLUE", series);
			}
		});

		JButton rgbButton = new JButton("RGB");
		rgbButton.setBounds(240, 300, 80, 40);
		rgbButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				double[][] seriesR = new double[2][obraz.R.length];
				double[][] seriesG = new double[2][obraz.G.length];
				double[][] seriesB = new double[2][obraz.B.length];

				for (int i = 0; i < obraz.B.length; i++) {
					seriesR[0][i] = (double) i;
					seriesR[1][i] = obraz.R[i];
					seriesG[0][i] = (double) i;
					seriesG[1][i] = obraz.G[i];
					seriesB[0][i] = (double) i;
					seriesB[1][i] = obraz.B[i];
				}
				dataset.removeSeries("RED");
				dataset.removeSeries("GREEN");
				dataset.removeSeries("BLUE");
				dataset.removeSeries("BRIGHTNESS");
				dataset.addSeries("RED", seriesR);
				dataset.addSeries("BLUE", seriesB);
				dataset.addSeries("GREEN", seriesG);
			}
		});

		JButton brightnessButton = new JButton("brightness");
		brightnessButton.setBounds(320, 300, 120, 40);
		brightnessButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				double[][] series = new double[2][obraz.B.length];
				for (int i = 0; i < obraz.B.length; i++) {
					series[0][i] = (double) i;
					series[1][i] = (0.299 * obraz.R[i] + 0.587 * obraz.G[i] + 0.144 * obraz.B[i]);
					obraz.lightTable[i] = (int) (0.299 * obraz.R[i] + 0.587 * obraz.G[i] + 0.144 * obraz.B[i]);
				}

				dataset.removeSeries("RED");
				dataset.removeSeries("GREEN");
				dataset.removeSeries("BLUE");
				dataset.addSeries("BRIGHTNESS", series);
			}
		});

		f.setLayout(null);
		f.add(chartPanel);
		chartPanel.setBounds(0, 0, 500, 300);
		f.setLocationRelativeTo(null);
		f.add(rButton);
		f.add(gButton);
		f.add(bButton);
		f.add(rgbButton);
		f.add(brightnessButton);
		f.pack();
		f.setSize(520, 400);
		f.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub		
	}
}