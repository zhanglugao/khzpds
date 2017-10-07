package com.khzpds.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ImageHelper {
    public static BufferedImage rotate(final BufferedImage bufferedimage,
                                            final int degree) {
        int w = bufferedimage.getWidth();
        int h = bufferedimage.getHeight();
        int type = bufferedimage.getColorModel().getTransparency();
        BufferedImage img;
        Graphics2D graphics2d;
        (graphics2d = (img = new BufferedImage(w, h, type))
                .createGraphics()).setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2d.rotate(Math.toRadians(degree), w / 2, h / 2);
        graphics2d.drawImage(bufferedimage, 0, 0, null);
        graphics2d.dispose();
        return img;
    }

    public static void rotateImage(String srcPath,int degree,String destPath) throws IOException{
        File f=new File(srcPath);
        if(!f.exists()){
            throw new RuntimeException("源文件不存在");
        }
        BufferedImage src = ImageIO.read(new FileInputStream(f));
        BufferedImage image=rotate(src,degree);
        ImageIO.write(image, "jpg", new File(destPath));
    }

}
