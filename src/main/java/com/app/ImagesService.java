package com.app;

import java.io.File;
import java.util.Map;

import org.apache.commons.imaging.formats.jpeg.JpegImageMetadata;
import org.apache.commons.imaging.formats.tiff.TiffImageMetadata;
import org.apache.sanselan.Sanselan;
import org.apache.sanselan.common.IImageMetadata;


public interface ImagesService  {
	

	/**
	 * Method that reads a file (RAW image file) and return its
	 * exif as TiffImageMetadata
	 * 
	 * @param rawImage  
	 * @return TiffImageMetadata 
	 */
	public TiffImageMetadata readRawMetadata(File rawImage);
	
	
	/**
	 * Method that reads a file (Jpeg image file) and return its
	 * exif as TiffImageMetadata
	 * 
	 * @param JpegImage  
	 * @return TiffImageMetadata 
	 */
	public TiffImageMetadata readJpegMetadata(File jpegImage);
	
	
	/**
	 * Method that maps a exif fields into a hash map with key and value
	 * (key= tag#, value= tag value)
	 * 
	 * @param TiffImageMetadata exif  
	 * @return Map<tag# (Integer),Value (Object)> 
	 */
	public Map<Integer,Object> mapExif(TiffImageMetadata exif);
	
	
	/**
	 * Method that copy important properties from the rawMap to a TiffImageMetadata
	 * 
	 * 
	 * @param TiffImageMetadata tiffImageMetadata, Map<Integer,Object> mapExif
	 * @return TiffImageMetadata
	 */
	public TiffImageMetadata mapperExifRawToExifJpg(TiffImageMetadata tiffImageMetadata, Map<Integer,Object> mapExif);

	}
