package br.com.jkassner.apiloteria.serviceImpl.lotofacil;

import br.com.jkassner.apiloteria.model.TipoLoteriaDownload;
import br.com.jkassner.apiloteria.serviceImpl.download.DownloadServiceAbstract;
import br.com.jkassner.apiloteria.service.UnzipService;
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
