package com.br.jkassner.apiloteria.serviceImpl;

import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.springframework.stereotype.Service;

import com.br.jkassner.apiloteria.PathUtils;
import com.br.jkassner.apiloteria.service.UnzipService;

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
