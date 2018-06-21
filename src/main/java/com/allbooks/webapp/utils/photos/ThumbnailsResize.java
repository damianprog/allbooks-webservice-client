package com.allbooks.webapp.utils.photos;

import java.awt.image.BufferedImage;
import java.io.IOException;

import org.springframework.stereotype.Component;

import net.coobird.thumbnailator.Thumbnails;

@Component
public class ThumbnailsResize {

	public BufferedImage resize(BufferedImage img, int newW, int newH) throws IOException {
		return Thumbnails.of(img).size(newW, newH).asBufferedImage();
	}
	
}
