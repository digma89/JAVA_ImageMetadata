package com.app;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.ImageWriteException;
import org.apache.commons.imaging.formats.jpeg.exif.ExifRewriter;
import org.apache.commons.imaging.formats.tiff.TiffImageMetadata;
import org.apache.commons.imaging.formats.tiff.write.TiffOutputSet;

public class Main {
	
	//Inject service
	private ImagesService imagesService;
	private final String EXTRA_MESSAGE ="c";
	private final String RAW_IMAGES_FORMAT = ".NEF";
	private final String IMAGE_FORMAT = ".jpg";
	private TiffImageMetadata exif = null;
	private TiffOutputSet outputSet = null;
	private OutputStream os = null;
	private Map<Integer, Object> mapExif = new HashMap<Integer, Object>();
	private File imageFile = null;

	public Main() {
		imagesService = new ImagesImpl();
	}

	// With the file name set the MetaData of the files
	public void setMetaData(String folerSource, String folderResult)
			throws IOException, ImageReadException, ImageWriteException {

		ArrayList<String> rawList = new ArrayList<String>();
		ArrayList<String> imageList = new ArrayList<String>();

		File folder = new File(folerSource);
		File[] listOfFiles = folder.listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				if (listOfFiles[i].getName().trim().endsWith(RAW_IMAGES_FORMAT)) {
					rawList.add(listOfFiles[i].getName().trim());
				} else {
					imageList.add(listOfFiles[i].getName().trim());
				}
			}
		}

		for (String rawImage : rawList) {
			//Take metadata from raw file and save the tag and value in a hashmap 
			File imageFileRaw = new File(folerSource + rawImage);
			TiffImageMetadata exifRaw = imagesService.readRawMetadata(imageFileRaw);
			mapExif = imagesService.mapExif(exifRaw);
	
			String nameOfImage = rawImage.substring(0,rawImage.indexOf("."));
						
			//Read metadata form jpg 
			imageFile = new File(folerSource + nameOfImage +IMAGE_FORMAT);
			
			if(imageFile.exists()){
				exif = imagesService.readJpegMetadata(imageFile);
		
				if (exif != null) {
					// TiffImageMetadata class is immutable (read-only).
					// TiffOutputSet class represents the Exif data to write.
					//
					// Usually, we want to update existing Exif metadata by
					// changing
					// the values of a few fields, or adding a field.
					// In these cases, it is easiest to use getOutputSet() to
					// start with a "copy" of the fields read from the image.
					outputSet = exif.getOutputSet();
				} else {
					// if file does not contain any exif metadata, we create an empty
					// set of exif metadata. Otherwise, we keep all of the other
					// existing tags.
					outputSet = new TiffOutputSet();
				}
		
				//copy metadata from raw hasmap to jpeg 
				outputSet = imagesService.mapperExifRawToExifJpg(outputSet, mapExif);
		
				//create a new file
				os = new BufferedOutputStream(new FileOutputStream(folerSource + nameOfImage +EXTRA_MESSAGE +IMAGE_FORMAT));
				
				/*imageFile should be the image that you need to copy into a new file and set the metadata, 
				 * not necessary the image that you read the metadata at the beginning
				 */
				new ExifRewriter().updateExifMetadataLossless(imageFile, os,outputSet);
			}
		}
		
		System.out.println("great?");
		os.close();
		os = null;

		if (os != null) {
			try {
				os.close();
			} catch (IOException e) {
			}
		}
	}

}
