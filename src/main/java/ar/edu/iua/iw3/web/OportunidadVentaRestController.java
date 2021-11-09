package ar.edu.iua.iw3.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import ar.edu.iua.iw3.modelo.OportunidadVenta;
import ar.edu.iua.iw3.modelo.dto.OportunidadVentaJsonSerializer;
import ar.edu.iua.iw3.negocio.IOportunidadVentaNegocio;
import ar.edu.iua.iw3.negocio.excepciones.NegocioException;
import ar.edu.iua.iw3.util.JsonUtiles;
import ar.edu.iua.iw3.util.MensajeRespuesta;

@RestController
@RequestMapping("/api/v1/oportunidad-ventas")
public class OportunidadVentaRestController {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IOportunidadVentaNegocio ovNegocio;

	@PostMapping(value = "/integracion", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MensajeRespuesta> recibir(@RequestBody OportunidadVenta oportunidadVenta) {
		try {
			MensajeRespuesta r=ovNegocio.recibir(oportunidadVenta).getMensaje();
			if(r.getCodigo()==0) {
				return new ResponseEntity<MensajeRespuesta>(r, HttpStatus.OK);
			} else {
				return new ResponseEntity<MensajeRespuesta>(r, HttpStatus.BAD_REQUEST);
			}
		} catch (NegocioException e) {
			log.error(e.getMessage(),e );
			MensajeRespuesta r=new MensajeRespuesta(-1,e.getMessage());
			return new ResponseEntity<MensajeRespuesta>(r,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> lista() {
		try {
			String oportunidadVentaStr = JsonUtiles
					.getObjectMapper(OportunidadVenta.class, new OportunidadVentaJsonSerializer(OportunidadVenta.class), "dd-MM-yyyy HH:mm:ss")
					.writeValueAsString(ovNegocio.lista());
			return new ResponseEntity<String>(oportunidadVentaStr, HttpStatus.OK);
		} catch (NegocioException | JsonProcessingException e) {
			return new ResponseEntity<String>(new MensajeRespuesta(500, e.getMessage()).toString(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
