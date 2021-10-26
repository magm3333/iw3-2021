package ar.edu.iua.iw3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import ar.edu.iua.iw3.negocio.IGraphBusiness;
import ar.edu.iua.iw3.security.authtoken.IAuthTokenBusiness;

@Configuration
@EnableScheduling
public class ScheduleEvents {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Scheduled(fixedDelay = 5*1000, initialDelay = 3000)
	public void dummy() {
		log.info("Ejecutando tarea");
	}
	
	@Autowired
	private IAuthTokenBusiness atB;
	
	@Scheduled(fixedDelay = 24*60*60*1000)
	public void purgeTokens() {
		try {
			atB.purgeTokens();
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}

	@Autowired
	private IGraphBusiness graphService;
	
	@Scheduled(fixedDelay = 5000, initialDelay = 10000)
	public void estados() {
		graphService.pushGraphData();
	}
	
}
