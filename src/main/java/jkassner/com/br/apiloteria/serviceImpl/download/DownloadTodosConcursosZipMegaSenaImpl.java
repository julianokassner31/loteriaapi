package jkassner.com.br.apiloteria.serviceImpl.download;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jkassner.com.br.apiloteria.service.UnzipService;
import jkassner.com.br.apiloteria.serviceImpl.TipoLoteria;

@Service("downloadTodosConcursosZipMegaSena")
public class DownloadTodosConcursosZipMegaSenaImpl extends DownloadServiceAbstract{

	@Autowired
	UnzipService unzipService;
	
	@Override
	public String getUri() {
		return TipoLoteria.MEGASENA_TODOS.getUri();
	}
	
	@Override
	protected String getContent() {
		
		return unzipService.unzipFile(is);
		
	}
}
