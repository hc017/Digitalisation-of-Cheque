
package com.raven.createAccount;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;

public class MyButtonDesign extends JButton{

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColorOver() {
        return colorOver;
    }

    public void setColorOver(Color colorOver) {
        this.colorOver = colorOver;
    }

    public Color getColorClick() {
        return colorClick;
    }

    public void setColorClick(Color colorClick) {
        this.colorClick = colorClick;
    }

    public Color getBorderColor() {
        return BorderColor;
    }

    
    public void setBorderColor(Color BorderColor) {
        this.BorderColor = BorderColor;
    }
    
    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
    
    public MyButtonDesign()
    {
        // init color
        setColor(new Color(48, 122, 184));
        colorOver = new Color(56, 188, 221);
        colorClick = new Color(87, 162, 175);
        BorderColor = new Color(0, 96, 162);
        setContentAreaFilled(false);
        
        addMouseListener(new MouseAdapter(){
           public void mouseEntered(MouseEvent ev)
           {
               setBackground(colorOver);
               over = true;
           }
           
           public void mouseExited(MouseEvent ev)
           {
               setBackground(color);
               over = true;
           }
           
           public void mousePressed(MouseEvent ev)
           {
               setBackground(colorClick);
              
           }
           
           public void mouseReleased(MouseEvent ev)
           {
               if(over)
               {
                   setBackground(colorOver);
               }
               else{
                   setBackground(color);
               }
           }
        });
    }
    private boolean over;
    private Color color;
    private Color colorOver;
    private Color colorClick;
    private Color BorderColor;
    private int radius = 0;

    protected void paintComponent(Graphics grap)
    {
        Graphics2D g2 = (Graphics2D) grap;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // paint border
        g2.setColor(BorderColor);
        g2.fillRoundRect(0,0,getWidth(), getHeight(), radius, radius);
        g2.setColor(getBackground());
        // border set 2 fix
        g2.fillRoundRect(2, 2, getWidth() - 4, getHeight() - 4, radius, radius);
        super.paintComponent(grap);
    }
    
}
