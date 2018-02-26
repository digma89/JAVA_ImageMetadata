package com.app;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.ImageWriteException;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.common.ImageMetadata;
import org.apache.commons.imaging.formats.jpeg.JpegImageMetadata;
import org.apache.commons.imaging.formats.jpeg.exif.ExifRewriter;
import org.apache.commons.imaging.formats.tiff.TiffField;
import org.apache.commons.imaging.formats.tiff.TiffImageMetadata;
import org.apache.commons.imaging.formats.tiff.constants.ExifTagConstants;
import org.apache.commons.imaging.formats.tiff.constants.Tiff4TagConstants;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfo;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoRational;
import org.apache.commons.imaging.formats.tiff.write.TiffOutputDirectory;
import org.apache.commons.imaging.formats.tiff.write.TiffOutputField;
import org.apache.commons.imaging.formats.tiff.write.TiffOutputSet;

import com.drew.metadata.Tag;





public class Main {
	private OutputStream os = null;
	private ImagesService imagesService;
	TiffOutputSet outputSet = null;
	Map<Integer, Object> mapExif = new HashMap<Integer, Object>();
	
	public Main() {
		imagesService = new ImagesImpl();
	}

	// With the file name set the MetaData of the files
	public void setMetaData(String folerSource, String folderResult)
			throws IOException, ImageReadException,
			ImageWriteException {

		ArrayList<String> rawList = new ArrayList<String>();
		ArrayList<String> imageList = new ArrayList<String>();
				
		File folder = new File(folerSource);
		File[] listOfFiles = folder.listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				if (listOfFiles[i].getName().trim().endsWith(".NEF")) {
					rawList.add(listOfFiles[i].getName().trim());
				} else {
					imageList.add(listOfFiles[i].getName().trim());
				}				
			}
		}
		
		
		
		/*File imageFile = new File(folerSource + imageList.get(0));
	//	Metadata metadata = ImageMetadataReader.readMetadata(imageFile);
		System.out.println(imageFile.getName());
		TiffImageMetadata exif = imagesService.readRawMetadata(new File(folerSource + rawList.get(0)));
		System.out.println("service");

		
		TiffOutputSet outputSet = null;
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
		System.out.println(outputSet);
*/
		   // note that metadata might be null if no metadata is found.
		
		//File imageFile = new File(folerSource+ "bb.jpg");
		             // final ImageMetadata metadata = (ImageMetadata) Imaging.getMetadata(imageFile);
		             // final JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;
		            //  if (null != jpegMetadata) {
		                  // note that exif might be null if no Exif metadata is found.
		                 //final TiffImageMetadata exif = jpegMetadata.getExif();
						  File imageFileRaw = new File(folerSource+ "bb.NEF");
		            	  TiffImageMetadata exifRaw = imagesService.readRawMetadata(imageFileRaw);
		            	  mapExif = imagesService.mapExif(exifRaw);
		            	 
		            	  TiffImageMetadata exif = null;
		            	  File imageFile = new File(folerSource+ "bb.jpg");
		            	  exif = imagesService.readJpegMetadata(imageFile);
		                 
		            	  System.out.println(exif);
		            	  exif = imagesService.mapperExifRawToExifJpg(exif, mapExif);
		            	  System.out.println(exif);
		            	  
		            	  if (null != exif) {
		                      // TiffImageMetadata class is immutable (read-only).
		                      // TiffOutputSet class represents the Exif data to write.
		                      //
		                      // Usually, we want to update existing Exif metadata by
		                      // changing
		                      // the values of a few fields, or adding a field.
		                      // In these cases, it is easiest to use getOutputSet() to
		                      // start with a "copy" of the fields read from the image.
		                     outputSet = exif.getOutputSet();
		                     
		                     
		                 }
		           //  }
		                 /* TagInfo t = outputSet.;
		                  TiffField d= exif.findField(t);
		                  TiffOutputField tag_maker = outputSet.findField(271);
		                  System.out.println(tag_maker.);
		                 */
		
		          /*        final org.apache.commons.imaging.formats.tiff.write.TiffOutputDirectory exifDirectory = outputSet.getOrCreateRootDirectory();
		                  //exifDirectory.removeField(ExifTagConstants.EXIF_TAG_SOFTWARE);
		                  //exifDirectory.add(ExifTagConstants.EXIF_TAG_SOFTWARE,"SomeKind");
		                  System.out.println("desc:" +exifDirectory.description());
		                  System.out.println("Item desc:" +exifDirectory.getItemDescription());
		             */
		               /*   TiffOutputDirectory exifDirectory = outputSet.getOrCreateExifDirectory();
		                  List<TiffOutputField> tiffOutPutField = exifDirectory.getFields();   
		                  //System.out.println("Filed list toString:" +tiffOutPutField);
		                  TiffOutputField field = tiffOutPutField.get(0);
		                  System.out.println("toString:" +field.toString());
		                  
		                  
		                  TagInfo fieleeeee = field.tagInfo;
		                  fieleeeee.getDescription();
		                  System.out.println("file desc:" +fieleeeee.getDescription());
		                  System.out.println("file name:" +fieleeeee.name);
		            */      
		                // TagInfo i = Exi
		                //  TiffField f = exif.getFieldValue(tag)
		                  
		                  
		              
		       
		                  
		                  
		               //   fieleeeee.getValue(f);
		// if file does not contain any exif metadata, we create an empty
		// set of exif metadata. Otherwise, we keep all of the other
		// existing tags.
		if (null == outputSet){
		outputSet = new TiffOutputSet();
		System.out.println("Null");
		}
		// os = new FileOutputStream(folerSource + imageList.get(0));
		os = new BufferedOutputStream(new FileOutputStream(folerSource+ "aa1.jpg"));
		File imageFileDst = new File(folerSource + "bb.jpg");
		new ExifRewriter().updateExifMetadataLossless(imageFileDst, os,
				outputSet);
		System.out.println("great?");
		/*XmpWriter.write(os, metadata);*/
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
