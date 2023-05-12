package ibf2022.batch2.csf.backend.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.StringTokenizer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ibf2022.batch2.csf.backend.repositories.ArchiveRepository;
import ibf2022.batch2.csf.backend.repositories.ImageRepository;

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
	// public ResponseEntity<String> upload(
	public ResponseEntity<String> upload(@RequestPart MultipartFile archive,
			@RequestPart String name, @RequestPart String title, @RequestPart String comments) throws IOException {
		System.out.println(">> in controller upload()");
		System.out.println(archive.getOriginalFilename());
		System.out.println(archive.getContentType());// application/x-zip-compressed

		// expand zip zrchive
		InputStream is = archive.getInputStream();

		byte[] buffer = new byte[1024];
		ZipInputStream zis = new ZipInputStream(is);
		ZipEntry zipEntry = zis.getNextEntry();

		while (zipEntry != null) {

			System.out.println(">> zipENtry: " + zipEntry.getName());
			zipEntry.getSize();

			// set file content type
			StringTokenizer tk = new StringTokenizer(zipEntry.getName(), ".");
			int count = 0;
			String contentType = "";
			String filenameExt = "";
			while (tk.hasMoreTokens()) {
				if (count == 1) {
					filenameExt = tk.nextToken();
					System.out.println(filenameExt); // debug
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

			imgRepo.upload(file, zipEntry.getName(),contentType,zipEntry.getSize());

			zipEntry = zis.getNextEntry();

		}

		zis.closeEntry();
		zis.close();

		arRepo.recordBundle();


		return null;
	}

	// TODO: Task 5

	// TODO: Task 6

}
