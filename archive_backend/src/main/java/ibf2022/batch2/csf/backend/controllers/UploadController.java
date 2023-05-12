package ibf2022.batch2.csf.backend.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ibf2022.batch2.csf.backend.models.Bundle;
import ibf2022.batch2.csf.backend.repositories.ArchiveRepository;
import ibf2022.batch2.csf.backend.repositories.ImageRepository;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;

@CrossOrigin(origins = "*")
@RestController
public class UploadController {

	@Autowired
	private ImageRepository imgRepo;

	@Autowired
	private ArchiveRepository arRepo;

	// TODO: Task 2, Task 3, Task 4
	// POST /upload
	// Content-Type: multipart/form-data
	// Accept: application/json
	@PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> upload(@RequestPart MultipartFile archive,
			@RequestPart String name, @RequestPart String title, @RequestPart String comments) throws IOException {
		
	
		// System.out.println(archive.getContentType());// application/x-zip-compressed

		try{
			Bundle b = new Bundle();
			b.setBundleId(UUID.randomUUID().toString().substring(0, 8));
			b.setDate(new Date());
			b.setName(name);
			b.setTitle(title);
			if(comments.equals("nil")){
				b.setComments(null);
			}else{
				b.setComments(comments);
			}
	
			// expand zip zrchive
			InputStream is = archive.getInputStream();
	
			byte[] buffer = new byte[1024];
			ZipInputStream zis = new ZipInputStream(is);
			ZipEntry zipEntry = zis.getNextEntry();
	
			while (zipEntry != null) {
	
				// set file content type
				StringTokenizer tk = new StringTokenizer(zipEntry.getName(), ".");
				int count = 0;
				String contentType = "";
				String filenameExt = "";
				while (tk.hasMoreTokens()) {
					if (count == 1) {
						filenameExt = tk.nextToken();
						contentType = "image/" + filenameExt;
						break;
					} else {
						filenameExt = tk.nextToken();
						count++;
					}
				}
	
				// write to a temp file 
				File file = new File("temp");
				file.createNewFile();
				FileOutputStream fos = new FileOutputStream(file);
				int len;
				while ((len = zis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}
				fos.close();
	
				String url = imgRepo.upload(file, zipEntry.getName(),contentType,zipEntry.getSize());
				b.getUrls().add(url);
	
				zipEntry = zis.getNextEntry();
	
			}
	
			zis.closeEntry();
			zis.close();
	
			arRepo.recordBundle(b);

			JsonObject payload = Json.createObjectBuilder().add("bundleId", b.getBundleId()).build();

			return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(payload.toString());
	
		}catch(Exception ex){

			JsonObject payload = Json.createObjectBuilder().add("error", ex.getMessage()).build();

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON).body(payload.toString());
	
		}

		

	}

	// TODO: Task 5
	@GetMapping(path = "/bundle/{bundleId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getBundle(@PathVariable String bundleId){

		try{
			Document doc = arRepo.getBundleByBundleId(bundleId);

			if(doc != null){

				return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(doc.toJson().toString());

			}else{
				JsonObject payload = Json.createObjectBuilder().add("error", bundleId +" not found").build();
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON).body(payload.toString());
			}
		
		
		}catch(Exception ex){

			JsonObject payload = Json.createObjectBuilder().add("error", ex.getMessage()).build();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON).body(payload.toString());

		}


		
	}

	// TODO: Task 6
	@GetMapping(path = "/bundles", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getBundle(){

		try{
			List<Document> docs = arRepo.getBundles();


			if(docs.size() > 0){
				JsonArrayBuilder arrB = Json.createArrayBuilder();

				for(Document d : docs){

					JsonObject jo = Json.createObjectBuilder()
										.add("bundleId", d.getString("bundleId"))
										.add("date", d.getDate("date").toString())
										.add("title", d.getString("title"))
										.build();
					arrB.add(jo);

				}
				JsonArray arr = arrB.build();
				System.out.println(" in controller: " + arr);
				return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(arr.toString());

			}else{
				JsonObject payload = Json.createObjectBuilder().add("error", "no result").build();
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON).body(payload.toString());

			}

		}catch(Exception ex){

			JsonObject payload = Json.createObjectBuilder().add("error", ex.getMessage()).build();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON).body(payload.toString());

		}

	}


}
