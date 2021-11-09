package ar.edu.iua.iw3.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import ar.edu.iua.iw3.modelo.Producto;
import ar.edu.iua.iw3.modelo.Rubro;
import ar.edu.iua.iw3.modelo.dto.ProductoJsonSerializer;
import ar.edu.iua.iw3.negocio.IProductoNegocio;
import ar.edu.iua.iw3.negocio.excepciones.EncontradoException;
import ar.edu.iua.iw3.negocio.excepciones.NegocioException;
import ar.edu.iua.iw3.negocio.excepciones.NoEncontradoException;
import ar.edu.iua.iw3.util.JsonUtiles;
import ar.edu.iua.iw3.util.MensajeRespuesta;

@RestController
public class ProductosRestController {

	@Autowired
	private IProductoNegocio productoNegocio;

	// curl http://localhost:8080/productos

	@GetMapping(value = "/productos",  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> listado() {

		try {
			String productoStr = JsonUtiles
					.getObjectMapper(Producto.class, new ProductoJsonSerializer(Producto.class), null)
					.writeValueAsString(productoNegocio.listado());
			return new ResponseEntity<String>(productoStr, HttpStatus.OK);
		} catch (NegocioException | JsonProcessingException e) {
			return new ResponseEntity<String>(new MensajeRespuesta(500, e.getMessage()).toString(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// curl http://localhost:8080/productos/1

	@GetMapping(value = "/productos/{id}")
	public ResponseEntity<Producto> cargar(@PathVariable("id") long id) {
		try {
			return new ResponseEntity<Producto>(productoNegocio.cargar(id), HttpStatus.OK);
		} catch (NegocioException e) {
			return new ResponseEntity<Producto>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NoEncontradoException e) {
			return new ResponseEntity<Producto>(HttpStatus.NOT_FOUND);
		}
	}

	// curl -X POST http://localhost:8080/productos -H "Content-Type:
	// application/json" -d
	// '{"id":2,"descripcion":"Leche","enStock":false,"precio":104.7,"rubro":{"id":1,"rubro":"Alimentos"},"descripcionExtendida":"Se
	// trata de leche larga vida"}'

	@PostMapping(value = "/productos")
	public ResponseEntity<String> agregar(@RequestBody Producto producto) {
		try {
			Producto respuesta = productoNegocio.agregar(producto);
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("location", "/productos/" + respuesta.getId());
			return new ResponseEntity<String>(responseHeaders, HttpStatus.CREATED);
		} catch (NegocioException e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (EncontradoException e) {
			return new ResponseEntity<String>(HttpStatus.FOUND);
		}
	}

	// curl -X PUT http://localhost:8080/productos -H "Content-Type:
	// application/json" -d
	// '{"id":2,"descripcion":"Leche","enStock":false,"precio":55,"rubro":{"id":1,"rubro":"Alimentos"},"descripcionExtendida":"Se
	// trata de leche larga vida"}' -v
	@PutMapping(value = "/productos")
	public ResponseEntity<String> modificar(@RequestBody Producto producto) {
		try {
			productoNegocio.modificar(producto);
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch (NegocioException e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NoEncontradoException e) {
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
	}

	// curl -X DELETE http://localhost:8080/productos/11 -v

	@DeleteMapping(value = "/productos/{id}")
	public ResponseEntity<String> eliminar(@PathVariable("id") long id) {
		try {
			productoNegocio.eliminar(id);
			return new ResponseEntity<String>(HttpStatus.OK);
		} catch (NegocioException e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NoEncontradoException e) {
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(value = "/rubros")
	public ResponseEntity<List<Rubro>> listadoRubros() {
		try {
			return new ResponseEntity<List<Rubro>>(productoNegocio.listadoRubros(), HttpStatus.OK);
		} catch (NegocioException e) {
			return new ResponseEntity<List<Rubro>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
