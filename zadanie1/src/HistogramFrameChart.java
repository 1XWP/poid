import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

public class HistogramFrameChart implements ActionListener {

	private Obraz obraz;
	private JButton rButton, gButton, bButton, lightButton;;
	DefaultCategoryDataset dataset = new DefaultCategoryDataset();

	public HistogramFrameChart(int[] R, int[] G, int[] B, Obraz obraz) {

		this.obraz = obraz;

		JFreeChart chart = ChartFactory.createBarChart("", "Histogram", "", dataset, PlotOrientation.VERTICAL, false,
				false, false);
		CategoryPlot p = chart.getCategoryPlot();

		chart.getCategoryPlot().setOutlineVisible(false);
		BarRenderer rend = (BarRenderer) chart.getCategoryPlot().getRenderer();
		rend.setShadowVisible(false);

		p.setRangeGridlinePaint(Color.BLACK);
		ChartFrame frame = new ChartFrame("Histogram", chart);

		frame.setLocationRelativeTo(null);
		frame.setSize(500, 350);
		frame.setVisible(true);

		rButton = new JButton("R");
		rButton.setBounds(20, 20, 350, 40);
		rButton.addActionListener(this);
		frame.add(rButton);

		gButton = new JButton("G");
		gButton.setBounds(20, 20, 350, 40);
		gButton.addActionListener(this);
		frame.add(gButton);

		bButton = new JButton("B");
		bButton.setBounds(20, 20, 350, 40);
		bButton.addActionListener(this);
		frame.add(bButton);

		lightButton = new JButton("J");
		lightButton.setBounds(20, 20, 350, 40);
		lightButton.addActionListener(this);
		frame.add(lightButton);
	}

	public void actionPerformed(ActionEvent e) {
		{
			Object source = e.getSource();
			if (source == rButton) {
				for (int i = 0; i < obraz.R.length; i++) {
					dataset.setValue(obraz.R[i], " ", "0" + i);
				}
			}
			if (source == gButton) {
				for (int i = 0; i < obraz.R.length; i++) {
					dataset.setValue(obraz.G[i], " ", "0" + i);
				}
			}
			if (source == bButton) {
				for (int i = 0; i < obraz.R.length; i++) {
					dataset.setValue(obraz.B[i], " ", "0" + i);
				}
			}
			if (source == lightButton) {
				for (int i = 0; i < obraz.R.length; i++) {

					dataset.setValue(0.299 * obraz.R[i] + 0.587 * obraz.G[i] + 0.144 * obraz.B[i], " ", "0" + i);
					obraz.lightTable[i] = (int) (0.299 * obraz.R[i] + 0.587 * obraz.G[i] + 0.144 * obraz.B[i]);
				}
				for (int i2 = 0; i2 < obraz.lightTable.length; i2++) {
					System.out.println(i2 + "  " + obraz.lightTable[i2]);
				}
			}
		}
	}
}