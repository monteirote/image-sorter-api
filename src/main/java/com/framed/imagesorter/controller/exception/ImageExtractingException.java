package com.framed.imagesorter.controller.exception;

import com.drew.imaging.ImageProcessingException;

public class ImageExtractingException extends RuntimeException {
    public ImageExtractingException(String message, ImageProcessingException error) {
        super(message, error);
    }
}
