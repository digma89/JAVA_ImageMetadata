package com.app;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.print.attribute.standard.MediaSize.ISO;

import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.ImageWriteException;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.common.ImageMetadata;
import org.apache.commons.imaging.common.RationalNumber;
import org.apache.commons.imaging.formats.jpeg.JpegImageMetadata;
import org.apache.commons.imaging.formats.tiff.TiffField;
import org.apache.commons.imaging.formats.tiff.TiffImageMetadata;
import org.apache.commons.imaging.formats.tiff.constants.ExifTagConstants;
import org.apache.commons.imaging.formats.tiff.constants.Tiff4TagConstants;
import org.apache.commons.imaging.formats.tiff.constants.TiffDirectoryType;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoAscii;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoRational;
import org.apache.commons.imaging.formats.tiff.write.TiffOutputDirectory;
import org.apache.commons.imaging.formats.tiff.write.TiffOutputSet;

public class ImagesImpl implements ImagesService {
	
	UtilitiesServcie utilitiesServcie;
	
	private static final int CAMERA_MAKER = 271;
	private static final int CAMERA_MODEL = 272;
	private static final int F_STOP = 33437;
	private static final int EXPOSURE_TIME = 33434;
	private static final int ISO_SPEED = 34855;
	private static final int EXPOSURE_COMPENSATION = 37380;
	private static final int FOCAL_LENGTH = 37386;
	private static final int MAX_APERTURE = 37381;
	private static final int METERING_MODE = 37383;
	private static final int FLASH_MODE = 37385;
	private static final int DATE_TIME = 306;
	private static final int EXPOSURE_PROGRAM = 34850;
	private static final int SATURATION = 41993;
	private static final int SHARPENESS = 41994;
	private static final int WHITE_BALANCE = 41987;
	private static final int DATE_TIME_ORIGINAL = 36867;
	private static final int DATE_TIME_DIGITIZED = 36868;
	
	//Root Tag Constants 
	final TagInfoAscii EXIF_TAG_MAKER = new TagInfoAscii("Software", 0x10f, -1,TiffDirectoryType.EXIF_DIRECTORY_IFD0);
	final TagInfoAscii EXIF_TAG_MODEL = new TagInfoAscii("Software", 0x110, -1,TiffDirectoryType.EXIF_DIRECTORY_IFD0);
	
	
	public ImagesImpl(){
		utilitiesServcie =new UtilitiesImpl();		
	}

	public TiffImageMetadata readRawMetadata(File rawImage) {
		TiffImageMetadata exif = null;
		try {
			exif = (TiffImageMetadata) Imaging.getMetadata(rawImage);
		} catch (ImageReadException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return exif;
	}

	public TiffImageMetadata readJpegMetadata(File jpegImage) {		
		TiffImageMetadata exif = null;
		try {
			final ImageMetadata metadata = (ImageMetadata) Imaging.getMetadata(jpegImage);
			final JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;
			if (null != jpegMetadata) {
				// note that exif might be null if no Exif metadata is found.
				exif = jpegMetadata.getExif();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ImageReadException e) {
			e.printStackTrace();
		}
		return exif;
	}
	
	public Map<Integer,Object> mapExif(TiffImageMetadata exif){
	Map<Integer, Object> exifMap = new HashMap<Integer, Object>();
	List<TiffField> tiffFieldList = exif.getAllFields();	
	try {
		for (TiffField tiffField : tiffFieldList) {
			exifMap.put(tiffField.getTag(), tiffField.getValue());
		}
		
	} catch (ImageReadException e) {		
		e.printStackTrace();
	}
	return exifMap;
	}

	
	public TiffOutputSet mapperExifRawToExifJpg(TiffOutputSet tiffOutputSet, Map<Integer, Object> mapExif) {
		
		TiffOutputDirectory exifDirectory;
		try {
			// Example of how to add a field/tag to the output set.
            //
            // Note that you should first remove the field/tag if it already
            // exists in this directory, or you may end up with duplicate
            // tags. See above.
            //
            // Certain fields/tags are expected in certain Exif directories;
            // Others can occur in more than one directory (and often have a
            // different meaning in different directories).
            //
            // TagInfo constants often contain a description of what
            // directories are associated with a given tag.
			exifDirectory = tiffOutputSet.getOrCreateExifDirectory();
			
			if(mapExif.containsKey(F_STOP) && mapExif.get(F_STOP) != null ){
				exifDirectory.removeField(ExifTagConstants.EXIF_TAG_FNUMBER);
				exifDirectory.add(ExifTagConstants.EXIF_TAG_FNUMBER, RationalNumber.valueOf(utilitiesServcie.getStringNumberWhenParenthesis(mapExif.get(F_STOP).toString())));
			}
			
			if(mapExif.containsKey(EXPOSURE_TIME) && mapExif.get(EXPOSURE_TIME) != null ){
				exifDirectory.removeField(ExifTagConstants.EXIF_TAG_EXPOSURE_TIME);
				exifDirectory.add(ExifTagConstants.EXIF_TAG_EXPOSURE_TIME, RationalNumber.valueOf(utilitiesServcie.getStringNumberWhenParenthesis(mapExif.get(EXPOSURE_TIME).toString())));
			}
			
			if(mapExif.containsKey(ISO_SPEED) && mapExif.get(ISO_SPEED) != null ){
				exifDirectory.removeField(ExifTagConstants.EXIF_TAG_ISO);
				exifDirectory.add(ExifTagConstants.EXIF_TAG_ISO, (Short)mapExif.get(ISO_SPEED));
			}
			
			if(mapExif.containsKey(EXPOSURE_COMPENSATION) && mapExif.get(EXPOSURE_COMPENSATION) != null ){
				exifDirectory.removeField(ExifTagConstants.EXIF_TAG_EXPOSURE_COMPENSATION);
				exifDirectory.add(ExifTagConstants.EXIF_TAG_EXPOSURE_COMPENSATION, RationalNumber.valueOf(utilitiesServcie.getStringNumberWhenParenthesis(mapExif.get(EXPOSURE_COMPENSATION).toString())));
			}
			
			if(mapExif.containsKey(FOCAL_LENGTH) && mapExif.get(FOCAL_LENGTH) != null ){
				exifDirectory.removeField(ExifTagConstants.EXIF_TAG_FOCAL_LENGTH);
				exifDirectory.add(ExifTagConstants.EXIF_TAG_FOCAL_LENGTH, RationalNumber.valueOf(utilitiesServcie.getStringNumberWhenParenthesis(mapExif.get(FOCAL_LENGTH).toString())));
				System.out.println(RationalNumber.valueOf(utilitiesServcie.getStringNumberWhenParenthesis(mapExif.get(FOCAL_LENGTH).toString())));	
			}
			
			if(mapExif.containsKey(MAX_APERTURE) && mapExif.get(MAX_APERTURE) != null ){
				exifDirectory.removeField(ExifTagConstants.EXIF_TAG_MAX_APERTURE_VALUE);
				exifDirectory.add(ExifTagConstants.EXIF_TAG_MAX_APERTURE_VALUE, RationalNumber.valueOf(utilitiesServcie.getStringNumberWhenParenthesis(mapExif.get(MAX_APERTURE).toString())));
			}
			
			if(mapExif.containsKey(METERING_MODE) && mapExif.get(METERING_MODE) != null ){
				exifDirectory.removeField(ExifTagConstants.EXIF_TAG_METERING_MODE);
				exifDirectory.add(ExifTagConstants.EXIF_TAG_METERING_MODE, (Short)mapExif.get(METERING_MODE));
			}
			
			if(mapExif.containsKey(FLASH_MODE) && mapExif.get(FLASH_MODE) != null ){			
				exifDirectory.removeField(ExifTagConstants.EXIF_TAG_FLASH);
				exifDirectory.add(ExifTagConstants.EXIF_TAG_FLASH, (Short)mapExif.get(FLASH_MODE));
			}	
			
		    if(mapExif.containsKey(EXPOSURE_PROGRAM) && mapExif.get(EXPOSURE_PROGRAM) != null ){
				exifDirectory.removeField(ExifTagConstants.EXIF_TAG_EXPOSURE_PROGRAM);
				exifDirectory.add(ExifTagConstants.EXIF_TAG_EXPOSURE_PROGRAM, (Short)mapExif.get(EXPOSURE_PROGRAM));				
		    }
		    
			if(mapExif.containsKey(SATURATION) && mapExif.get(SATURATION) != null ){
				exifDirectory.removeField(ExifTagConstants.EXIF_TAG_SATURATION_1);
				exifDirectory.add(ExifTagConstants.EXIF_TAG_SATURATION_1, (Short)mapExif.get(SATURATION));
			}
			
			if(mapExif.containsKey(SHARPENESS) && mapExif.get(SHARPENESS) != null ){
				exifDirectory.removeField(ExifTagConstants.EXIF_TAG_SHARPNESS_1);
				exifDirectory.add(ExifTagConstants.EXIF_TAG_SHARPNESS_1, (Short)mapExif.get(SHARPENESS));
			}
			
			if(mapExif.containsKey(WHITE_BALANCE) && mapExif.get(WHITE_BALANCE) != null ){
				exifDirectory.removeField(ExifTagConstants.EXIF_TAG_WHITE_BALANCE_1);
				exifDirectory.add(ExifTagConstants.EXIF_TAG_WHITE_BALANCE_1, (Short)mapExif.get(WHITE_BALANCE));
			}
			
			if(mapExif.containsKey(DATE_TIME_ORIGINAL) && mapExif.get(DATE_TIME_ORIGINAL) != null ){			
				exifDirectory.removeField(ExifTagConstants.EXIF_TAG_DATE_TIME_ORIGINAL);
				exifDirectory.add(ExifTagConstants.EXIF_TAG_DATE_TIME_ORIGINAL, (String)mapExif.get(DATE_TIME_ORIGINAL));
			}
			
			if(mapExif.containsKey(DATE_TIME_DIGITIZED) && mapExif.get(DATE_TIME_DIGITIZED) != null ){
				exifDirectory.removeField(ExifTagConstants.EXIF_TAG_DATE_TIME_DIGITIZED);
				exifDirectory.add(ExifTagConstants.EXIF_TAG_DATE_TIME_DIGITIZED, (String)mapExif.get(DATE_TIME_DIGITIZED));
			}
			
	        	
			//ROOT DIRECTORY
			if(mapExif.containsKey(CAMERA_MAKER) && mapExif.get(CAMERA_MAKER) != null ){				
				exifDirectory = tiffOutputSet.getOrCreateRootDirectory();
		        exifDirectory.removeField(EXIF_TAG_MAKER);
		        exifDirectory.add(EXIF_TAG_MAKER, (String)mapExif.get(CAMERA_MAKER));
			}
			if(mapExif.containsKey(CAMERA_MODEL) && mapExif.get(CAMERA_MODEL) != null ){				
				exifDirectory = tiffOutputSet.getOrCreateRootDirectory();
		        exifDirectory.removeField(EXIF_TAG_MODEL);
		        exifDirectory.add(EXIF_TAG_MODEL, (String)mapExif.get(CAMERA_MODEL));
			}
	        

			
		} catch (ImageWriteException e1) {
			e1.printStackTrace();
		}
		return tiffOutputSet;
	}

}