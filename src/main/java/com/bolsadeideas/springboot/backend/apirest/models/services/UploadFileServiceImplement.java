package com.bolsadeideas.springboot.backend.apirest.models.services;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.slf4j.Logger;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ch.qos.logback.classic.LoggerContext;

@Service
public class UploadFileServiceImplement implements IUuploadFileService {
	
	LoggerContext context = new LoggerContext();
	Logger log = context.getLogger(UploadFileServiceImplement.class);
	
	private final static String DIRECTORIO_UPLOAD = "uploads";


	@Override
	public Resource cargar(String nombreFoto) throws MalformedURLException {
		
		Path rutaArchivo =getPath(nombreFoto) ;
		log.info(rutaArchivo.toString());
		Resource recurso = new UrlResource(rutaArchivo.toUri());
		
		
		if(!recurso.exists() && !recurso.isReadable()) {
			rutaArchivo = Paths.get("src/static/images").resolve("no-user.png").toAbsolutePath();

			recurso = new UrlResource(rutaArchivo.toUri());
			log.error("Error no se pudo cargar la imagen"+nombreFoto);
		}
		
		return recurso;
	}

	@Override
	public String copiar(MultipartFile archivo) throws IOException {
		String nombreArchivo = UUID.randomUUID().toString() + "_"+ archivo.getOriginalFilename().replace(" ", "");
		
		Path rutaArchivo= getPath(nombreArchivo);
		log.info(rutaArchivo.toString());
		
		Files.copy(archivo.getInputStream(), rutaArchivo);

		return nombreArchivo;
	}

	@Override
	public boolean eliminar(String nombreFoto) {
		if(nombreFoto !=null && nombreFoto.length()>0) {
			Path rutaAnterior = Paths.get("uploads").resolve(nombreFoto).toAbsolutePath();
			File archivoAterior = rutaAnterior.toFile();
			if(archivoAterior.exists() && archivoAterior.canRead()) {
				archivoAterior.delete();
				return true;
			}
		}
		return false;
	}

	@Override
	public Path getPath(String nombreFoto) {
		return Paths.get(DIRECTORIO_UPLOAD).resolve(nombreFoto).toAbsolutePath();
	}

}
