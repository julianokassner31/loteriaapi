package com.br.jkassner.apiloteria.serviceImpl.lotofacil;

import com.br.jkassner.apiloteria.model.TipoLoteriaDownload;
import com.br.jkassner.apiloteria.service.UnzipService;
import com.br.jkassner.apiloteria.serviceImpl.download.DownloadServiceAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("downloadTodosConcursosZipLotoFacil")
public class DownloadTodosConcursosZipLotoFacilImpl extends DownloadServiceAbstract {

	@Autowired
	UnzipService unzipService;
	
	@Override
	public String getUri() {
		return TipoLoteriaDownload.LOTOFACIL_TODOS.getUri();
	}
	
	@Override
	protected String getContent() {
		
		return unzipService.unzipFile(is);
	}
}
