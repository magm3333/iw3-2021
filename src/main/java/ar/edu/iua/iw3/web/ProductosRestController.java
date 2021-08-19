package ar.edu.iua.iw3.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.iua.iw3.modelo.Producto;
import ar.edu.iua.iw3.negocio.IProductoNegocio;
import ar.edu.iua.iw3.negocio.excepciones.EncontradoException;
import ar.edu.iua.iw3.negocio.excepciones.NegocioException;
import ar.edu.iua.iw3.negocio.excepciones.NoEncontradoException;

@RestController
public class ProductosRestController {

	@Autowired
	private IProductoNegocio productoNegocio;

	@GetMapping(value="/productos")
	public ResponseEntity<List<Producto>> listado() {
		try {
			return new ResponseEntity<List<Producto>>(productoNegocio.listado(), HttpStatus.OK);
		} catch (NegocioException e) {
			return new ResponseEntity<List<Producto>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value="/productos/{id}")
	public ResponseEntity<Producto> cargar(@PathVariable("id") long id) {
		try {
			return new ResponseEntity<Producto>(productoNegocio.cargar(id), HttpStatus.OK);
		} catch (NegocioException e) {
			return new ResponseEntity<Producto>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NoEncontradoException e) {
			return new ResponseEntity<Producto>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping(value="/productos")
	public ResponseEntity<Producto> agregar(@RequestBody Producto producto) {
		try {
			return new ResponseEntity<Producto>(productoNegocio.agregar(producto), HttpStatus.OK);
		} catch (NegocioException e) {
			return new ResponseEntity<Producto>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (EncontradoException e) {
			return new ResponseEntity<Producto>(HttpStatus.FOUND);
		}
	}
}
