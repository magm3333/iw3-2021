package ar.edu.iua.iw3.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.iua.iw3.negocio.IGraphBusiness;

@RestController
@RequestMapping("/api/v1/graph")
public class GraphRestController {

	@Autowired
	private IGraphBusiness graphService;

	@GetMapping("/push")
	public void push() {
		graphService.pushGraphData();
	}

}
