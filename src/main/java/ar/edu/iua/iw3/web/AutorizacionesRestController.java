package ar.edu.iua.iw3.web;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.iua.iw3.modelo.cuentas.IUserNegocio;
import ar.edu.iua.iw3.modelo.cuentas.User;

@RestController
@RequestMapping(Constantes.URL_AUTH)
public class AutorizacionesRestController extends BaseRestController {

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/admin")
	public ResponseEntity<String> onlyAdmin() {
		return new ResponseEntity<String>("Servicio admin", HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping("/user")
	public ResponseEntity<String> onlyUser() {
		return new ResponseEntity<String>("Servicio user", HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
	@GetMapping("/user-o-admin")
	public ResponseEntity<String> rolUserOAdmin() {
		return new ResponseEntity<String>("Servicio user", HttpStatus.OK);
	}

	@PreAuthorize("#username == authentication.principal.username")
	@GetMapping("/mis-roles")
	public ResponseEntity<String> misRoles(@RequestParam("username") String username) {
		return new ResponseEntity<String>(getUserLogged().getAuthorities().toString(), HttpStatus.OK);
	}

	@GetMapping("/variable")
	public ResponseEntity<String> variable(HttpServletRequest request) {
		if (request.isUserInRole("ROLE_ADMIN")) {
			return new ResponseEntity<String>("Tenés rol admin", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("No tenés rol admin", HttpStatus.OK);
		}
	}

	@PostAuthorize("returnObject.username == #username")
	@GetMapping("/datos-full")
	public User datosFull(@RequestParam("username") String username) {
		return getUserLogged();
	}

	@Autowired
	private IUserNegocio usuarioNegocio;

	@PostFilter("filterObject != authentication.principal.username")
	@GetMapping("/filtrar-actual")
	public List<String> filtrarActual() {
		List<String> r = null;
		try {
			r = usuarioNegocio.lista().stream().map(u -> u.getUsername()).collect(Collectors.toList());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return r;
	}

}
