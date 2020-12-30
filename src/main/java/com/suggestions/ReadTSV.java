package com.suggestions;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import static java.nio.charset.StandardCharsets.UTF_8;
import org.springframework.stereotype.Component;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.suggestions.dto.Cities;

@Component
public class ReadTSV {
	
	List<String> cityName = new ArrayList<String>();
	HashMap<String, Cities> map = new HashMap<String, Cities>();
	AutoComplete autocomplete;
	
	void readFile() {
		BufferedReader TSVFile;
		int count = 0;
		try {
			AmazonS3 s3 = new AmazonS3Client(new BasicAWSCredentials("AKIAJ5BEFZNEKZM3OTYA", "yLNXIChPkulo25Cvw1UGV8Z2rLf5/5NqrcS4K+QZ"));
			S3Object s3object = s3.getObject(new GetObjectRequest(
		            "heroku-chetan", "cities_canada-usa.tsv"));
		    System.out.println(s3object.getObjectMetadata().getContentType());
		    System.out.println(s3object.getObjectMetadata().getContentLength());

		    TSVFile = new BufferedReader(new InputStreamReader(s3object.getObjectContent()));
		   
			String dataRow = TSVFile.readLine(); // Read first line.

			while (dataRow != null) {
				if(count>=1) {

					String fields[] = dataRow.split("\t");
					Cities city = new Cities();
					
					
					cityName.add(fields[1]);
					city.setName(fields[1]);
					city.setLatitude(Float.parseFloat(fields[4]));
					city.setLongitude(Float.parseFloat(fields[5]));
					map.put(fields[1], city);
				}
				count++;
				dataRow = TSVFile.readLine(); // Read next line of data.
			}
			// Close the file once all data has been read.
			TSVFile.close();
			autocomplete = new AutoComplete(cityName, this);
		} catch (FileNotFoundException e) {
			System.out.println("file not exception " + e);
		} catch (IOException e) {
			System.out.println("io exception " + e);
		}
	}

}
