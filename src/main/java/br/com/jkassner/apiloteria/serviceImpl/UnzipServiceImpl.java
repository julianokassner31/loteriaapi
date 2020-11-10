package br.com.jkassner.apiloteria.serviceImpl;

import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.springframework.stereotype.Service;

import br.com.jkassner.apiloteria.PathUtils;
import br.com.jkassner.apiloteria.service.UnzipService;

@Service
public class UnzipServiceImpl implements UnzipService {

	@Override
	public String unzipFile(InputStream input) {
		
		StringBuilder content = new StringBuilder();
		
		try {

			ZipInputStream zis = new ZipInputStream(input);
			ZipEntry zipEntry = zis.getNextEntry();
			
			while (zipEntry != null) {
				
				content.append(PathUtils.convertInputStreamToString(zis));
			
				zipEntry = zis.getNextEntry();
			}
			
			zis.closeEntry();
			zis.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return content.toString();
	}
}
