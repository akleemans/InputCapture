import java.awt.*;
import javax.imageio.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.List;

public class Plot{
    public static int up(int n){
        return (n-2)>=0 ? n-2 : (n-1)>=0 ? n-1 : n;
    }
    public static int down(int n,int limit){
        return (n+2)<=limit ? n+2 : (n+1)<=limit ? n+1 : n;
    }
    public static void draw(Path path, Color color, Graphics2D graphics, int width, int height) throws IOException{
        Charset charset = Charset.forName("UTF-8");
        List<String> lines = Files.readAllLines(path,charset);
        graphics.setColor(color);
        if(color.equals(Color.black)){
            String[] lastCoordinates = lines.get(0).split(",");
            for(int i = 1 ; i < lines.size() ; i++){
                String[] coordinates = lines.get(i).split(",");
                graphics.drawLine(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]), Integer.parseInt(lastCoordinates[0]), Integer.parseInt(lastCoordinates[1]));
                lastCoordinates = coordinates;
            }
        }
        else if(color.equals(Color.red)){
            for(int i = 0 ; i < lines.size() ; i++){
                String[] coordinates = lines.get(i).split(",");
                int upX = up(Integer.parseInt(coordinates[0]));
                int downX = down(Integer.parseInt(coordinates[0]),width);
                int upY = up(Integer.parseInt(coordinates[1]));
                int downY = down(Integer.parseInt(coordinates[1]),height);
                graphics.drawLine(upX,upY,downX,downY);
                graphics.drawLine(upX,downY,downX,upY);
                graphics.drawLine(Integer.parseInt(coordinates[0]),upY,Integer.parseInt(coordinates[0]),downY);
                graphics.drawLine(upX,Integer.parseInt(coordinates[1]),downX,Integer.parseInt(coordinates[1]));
            }
        }
    }

    public static void generatePath(){
        Path pathMovement = Paths.get("positions.csv");
        Path pathClicks = Paths.get("clicks.csv");
        Path pathPng = Paths.get("path.png");

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        BufferedImage image = new BufferedImage((int)width,(int)height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();
        graphics.drawImage(image, 0, 0, null);

        if(Files.notExists(pathMovement)) System.exit(1);
        if(Files.notExists(pathClicks)) System.exit(1);

        try {
            Files.deleteIfExists(pathPng);
            File outputfile = new File("path.png");
            draw(pathMovement, Color.black, graphics, (int)width, (int)height);
            draw(pathClicks, Color.red, graphics, (int)width, (int)height);
            ImageIO.write(image, "png", outputfile);
            graphics.dispose();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}