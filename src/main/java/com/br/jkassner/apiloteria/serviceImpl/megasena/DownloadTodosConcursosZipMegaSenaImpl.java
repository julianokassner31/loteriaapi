package com.br.jkassner.apiloteria.serviceImpl.megasena;

import com.br.jkassner.apiloteria.serviceImpl.download.DownloadServiceAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.jkassner.apiloteria.service.UnzipService;
import com.br.jkassner.apiloteria.model.TipoLoteriaDownload;

@Service("downloadTodosConcursosZipMegaSena")
public class DownloadTodosConcursosZipMegaSenaImpl extends DownloadServiceAbstract {

	@Autowired
	UnzipService unzipService;
	
	@Override
	public String getUri() {
		return TipoLoteriaDownload.MEGASENA_TODOS.getUri();
	}
	
	@Override
	protected String getContent() {
		
		return unzipService.unzipFile(is);
		
	}
}
