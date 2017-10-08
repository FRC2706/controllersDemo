import javax.swing.*; 
import java.awt.event.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.*;

public class TargetPanel extends JPanel {
	private int mouseX = 50;
    private int mouseY = 50;
    private int squareW = 20;
    private int squareH = 20;
    
    private Controller Xcontroller;
    private Controller Ycontroller;
    
    private BufferedImage img = null;

	/** CONSTRUCTOR **/
    public TargetPanel(Controller Xcontroller, Controller Ycontroller) {
		
		this.Xcontroller = Xcontroller;
		this.Ycontroller = Ycontroller;

        setBorder(BorderFactory.createLineBorder(Color.black));

        addMouseMotionListener(new MouseAdapter() {
            public void mouseMoved(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
                //repaint();
            }
            
//            public void mouseDagged(MouseEvent e) {
//                mouseX = e.getX();
//                mouseY = e.getY();
//            }
            
        });

		try {
			img = ImageIO.read(new File("target2.jpg"));
		} catch (IOException e) {
		}

        
        // TODO: wtf? This doesn't do anything.
        this.setPreferredSize( new Dimension(200,200) );
        this.setMinimumSize(new Dimension(100,100) );
        this.setMaximumSize(new Dimension(200,200) );

    } // end contructor
    
    public void setXcontroller( Controller newContr ) {
    	Xcontroller = newContr;
    }
    
    public void setYcontroller( Controller newContr ) {
    	Ycontroller = newContr;
	}
    
    
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);       
       // g.drawString("This is my custom Panel!",10,20);
       // g.setColor(Color.RED);
       // g.fillRect(squareX,squareY,squareW,squareH);
      //  g.setColor(Color.BLACK);
      //  g.drawRect(squareX,squareY,squareW,squareH);
      
      Xcontroller.setSetpoint(mouseX);
      Ycontroller.setSetpoint(mouseY);
      g.drawImage( img, (int) Xcontroller.getValue()-14, (int) Ycontroller.getValue()-14, null);
    }  
}
