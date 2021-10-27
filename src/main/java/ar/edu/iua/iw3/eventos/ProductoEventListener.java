package ar.edu.iua.iw3.eventos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import ar.edu.iua.iw3.modelo.Producto;

@Component
public class ProductoEventListener implements ApplicationListener<ProductoEvent> {
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public void onApplicationEvent(ProductoEvent event) {
		if (event.getTipo().equals(ProductoEvent.Tipo.SUBE_PRECIO) && event.getSource() instanceof Producto) {
			manejaEventoSubePrecio((Producto) event.getSource());

		}

	}

	@Autowired
	private JavaMailSender emailSender;

	@Value("${mail.productos.to:magm@iua.edu.ar}")
	private String to;

	private void manejaEventoSubePrecio(Producto producto) {
		String mensaje=String.format("El producto %s, se aumentó de forma desmesurada, el nuevo precio es $%.2f", 
				producto.getDescripcion(), producto.getPrecio());
		log.info("Enviando mensaje '{}'",mensaje);
		try {
			SimpleMailMessage message=new SimpleMailMessage();
			message.setFrom("noreply@iua.edu.ar");
			message.setTo(to);
			message.setSubject("Aumento desmedido de precio del producto "+producto.getDescripcion());
			message.setText(mensaje);
			emailSender.send(message);
			log.trace("Mail enviado a: '{}'",to);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}

}
