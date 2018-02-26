package com.app;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoRational;
import org.apache.commons.imaging.formats.tiff.write.TiffOutputDirectory;

public class ImagesImpl implements ImagesService {
	
	UtilitiesServcie utilitiesServcie;
	
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

	public TiffImageMetadata mapperExifRawToExifJpg(TiffImageMetadata tiffImageMetadata, Map<Integer, Object> mapExif) {
		
		TiffOutputDirectory exifDirectory;
		try {
			exifDirectory = tiffImageMetadata.getOutputSet().getOrCreateRootDirectory();
			exifDirectory.removeField(ExifTagConstants.EXIF_TAG_FNUMBER);
			exifDirectory.add(ExifTagConstants.EXIF_TAG_FNUMBER, RationalNumber.valueOf(utilitiesServcie.getStringNumberWhenParenthesis(mapExif.get(33437).toString())));
		} catch (ImageWriteException e1) {
			e1.printStackTrace();
		}
		return tiffImageMetadata;
	}

}