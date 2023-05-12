package ibf2022.batch2.csf.backend.repositories;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.zip.ZipEntry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Repository
public class ImageRepository {

	@Autowired
	private AmazonS3 s3Client;

	@Value("${DO_STORAGE_BUCKETNAME}")
	public String bucketName;

	// TODO: Task 3
	// You are free to change the parameter and the return type
	// Do not change the method's name
	public Object upload(File file, String fileName ,String contentType, long size) throws FileNotFoundException {

		// construct metadata to be uploaded to S3
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentType(contentType); 
		metadata.setContentLength(size); 

		// new PutObjectRequest (String bucketName, String key, InputStream input, ObjectMetadata metadata)
		PutObjectRequest putRequest = new PutObjectRequest(bucketName, fileName,
				new FileInputStream(file), metadata);
		putRequest.withCannedAcl(CannedAccessControlList.PublicRead);
		s3Client.putObject(putRequest);
		System.out.println("done upload"); // debug

		return null;
	}
}
