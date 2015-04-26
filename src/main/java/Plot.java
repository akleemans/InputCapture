
import javafx.scene.paint.Color;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static java.awt.Transparency.*;


/**
 * Created by raph on 26/04/2015.
 */
public class Plot {
    private Plot() {};
    private static Plot instance;
    public static Plot getInstance(){
        if(instance == null)
            instance = new Plot();
        return instance;
    }
    public void drawChart(List<Pair> csvData, int width, int height){
        try {
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
            Graphics2D graphics = image.createGraphics();
            //graphics.setBackground(java.awt.Color.yellow);
            graphics.setColor(java.awt.Color.black);
           // graphics.setPaint(java.awt.Paint);

            for (int i = 0; i + 1 < csvData.size(); i++)
                graphics.drawLine(csvData.get(i).x, csvData.get(i).y, csvData.get(i+1).x, csvData.get(i+1).y);

            ImageIO.write(image, "PNG", new File("plot.png"));
        }catch(IOException ex){
            System.out.println("Error writing file");
        }catch(Exception ex){
            System.out.println("Something went wrong");
            System.exit(1);
        }

    }


}
