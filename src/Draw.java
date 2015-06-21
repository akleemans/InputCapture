import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Draw {

	private int width;
	private int height;
	private final String configPath = "resources/draw.conf";
	private boolean drawing = false;
	private Color color;
	private final String pathDestinationFile = "path.png";
	private final String buttonDestinationFile = "buttonDiagram.png";
	
	private void readConfig() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(this.configPath));
		String line;
		line = br.readLine();
		if(line.split(":")[1].equalsIgnoreCase("yes") || line.split(":")[1].equalsIgnoreCase(" yes")) {
			this.drawing = true;
		} else {
			this.drawing = false;
			br.close();
			return;
		}
		
		line = br.readLine();
		try {
			this.width = Integer.parseInt(line.split(":")[1]);
			line = br.readLine();
			this.height = Integer.parseInt(line.split(":")[1]);
			line = br.readLine();
			char[] color = line.split("#")[1].toCharArray();
			StringBuffer red = new StringBuffer();
			red.append(color[0]);
			red.append(color[1]);
			StringBuffer green = new StringBuffer();
			green.append(color[2]);
			green.append(color[3]);
			StringBuffer blue = new StringBuffer();
			blue.append(color[4]);
			blue.append(color[5]);
			this.color = new Color(Integer.parseInt(red.toString(), 16), Integer.parseInt(green.toString(), 16), Integer.parseInt(blue.toString(), 16));
		} catch(NumberFormatException e) {
			System.out.println("There are errors in config file " + this.configPath);
			this.drawing = false;
		}
		br.close();
	}
	
	protected void drawPath(List<String> positions) {
		try {
			this.readConfig();
			if(this.drawing == false) return;

			
			BufferedImage bi = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_ARGB);
			Graphics2D paint = bi.createGraphics();
			paint.setColor(this.color);
			
			String before = null;
			for(String position : positions) {
				if(before != null) {
					paint.drawLine(Integer.parseInt(before.split(",")[0]), Integer.parseInt(before.split(",")[1]), Integer.parseInt(position.split(",")[0]), Integer.parseInt(position.split(",")[1]));
				}
				before = position;
			}
			
			ImageIO.write(bi, "png", new File(this.pathDestinationFile));
			bi.flush();
			System.err.println(this.pathDestinationFile + "created");
		} catch(IOException e) {
			System.err.println("IO problem during drawing");
		}
	}
	
	/** Draws diagram of pressed keys frequency */
	protected void drawFrequencyDiagram(List<Counter> counting) {
		try {
			this.readConfig();
			if(this.drawing == false) return;

			int margins = 30;
			
			BufferedImage bi = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_ARGB);
			Graphics2D paint = bi.createGraphics();
			paint.setColor(this.color);
			
			//finding max and number of seconds
			int max = 0;
			int numberOfSeconds = 0;
			for(Counter second : counting) {
				if(second.getResult() > max) max = second.getResult();
				numberOfSeconds = second.getSecond();
			}
			numberOfSeconds++; //as first second is count as zero
			
			//Counting width and height of one second
			int widthOfSecond = (this.width - 2 * margins)/numberOfSeconds;
			int heightOfSecond = (this.height - 2 * margins)/max;
			
			int xZero = margins;
			int yZero = this.height - margins;
			
			//drawing
			for(Counter second : counting) {
				int xNow = second.getSecond() * widthOfSecond + xZero;
				int yNow = yZero - second.getResult() * heightOfSecond;

				paint.fillRect(xNow, yNow, widthOfSecond, yZero - yNow);
			}
			
			//draw bottom line
			paint.drawLine(margins, yZero, this.width - margins, yZero);
			
			//draw scale
			paint.setColor(new Color(0, 0, 0));	//black
			for(int i = 0; i < numberOfSeconds; i++) {
				paint.fillRect(xZero + i * widthOfSecond - margins/20, yZero - margins/2, margins/10, margins);
			}
			paint.setColor(this.color);
			
			ImageIO.write(bi, "png", new File(this.buttonDestinationFile));
			bi.flush();
			System.err.println(this.buttonDestinationFile + "created");
		} catch(IOException e) {
			System.err.println("IO problem during drawing");

		}
	}
		
}